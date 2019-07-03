package com.messagebird;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.ErrorReport;
import com.messagebird.objects.PagedPaging;

/**
 * Implementation of MessageBirdService
 * Sends and receives JSON objects from the Messagebird platform
 * <p>
 * Created by rvt on 1/5/15.
 */
public class MessageBirdServiceImpl implements MessageBirdService {

    private static final String NOT_AUTHORISED_MSG = "You are not authorised for the MessageBird service, please check your access key.";
    private static final String FAILED_DATA_RESPONSE_CODE = "Failed to retrieve data from MessageBird service with response code ";
    private static final String ACCESS_KEY_MUST_BE_SPECIFIED = "Access key must be specified";
    private static final String SERVICE_URL_MUST_BE_SPECIFIED = "Service URL must be specified";
    private static final String REQUEST_VALUE_MUST_BE_SPECIFIED = "Request value must be specified";
    private static final String REQUEST_METHOD_NOT_ALLOWED = "Request method %s is not allowed.";
    private static final String CAN_NOT_ALLOW_PATCH = "Can not set HttpURLConnection.methods field to allow PATCH.";

    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_PATCH = "PATCH";
    private static final String METHOD_POST = "POST";

    private static final List<String> REQUEST_METHODS = Arrays.asList(METHOD_DELETE, METHOD_GET, METHOD_PATCH, METHOD_POST);
    private static final List<String> REQUEST_METHODS_WITH_PAYLOAD = Arrays.asList(METHOD_PATCH, METHOD_POST);
    private static final String[] PROTOCOL_LISTS = new String[]{"http://", "https://"};
    private static final List<String> PROTOCOLS = Arrays.asList(PROTOCOL_LISTS);

    // Used when the actual version can not be parsed.
    private static final double DEFAULT_JAVA_VERSION = 0.0;

    // Indicates whether we've overridden HttpURLConnection's behaviour to
    // allow PATCH requests yet. Also see docs on allowPatchRequestsIfNeeded().
    private static boolean isPatchRequestAllowed = false;

    private final String accessKey;
    private final String serviceUrl;
    private final String clientVersion = "2.2.0";
    private final String userAgentString;
    private Proxy proxy = null;

    public MessageBirdServiceImpl(final String accessKey, final String serviceUrl) {
        if (accessKey == null) {
            throw new IllegalArgumentException(ACCESS_KEY_MUST_BE_SPECIFIED);
        }
        if (serviceUrl == null || serviceUrl.length() == 0) {
            throw new IllegalArgumentException(SERVICE_URL_MUST_BE_SPECIFIED);
        }
        this.accessKey = accessKey;
        this.serviceUrl = serviceUrl;
        this.userAgentString = determineUserAgentString();
    }

    private String determineUserAgentString() {
        double javaVersion = DEFAULT_JAVA_VERSION;
        try {
            javaVersion = getVersion();
        } catch (GeneralException e) {
            // Do nothing: leave the version at its default.
        }

        return String.format("MessageBird Java/%s ApiClient/%s", javaVersion, clientVersion);
    }

    /**
     * Initiate service with default serviceUrl.
     *
     * @param accessKey developer access key
     */
    public MessageBirdServiceImpl(final String accessKey) {
        this(accessKey, "https://rest.messagebird.com");
    }

    @Override
    public <R> R requestByID(String request, String id, Class<R> clazz) throws UnauthorizedException, GeneralException, NotFoundException {
        String path = "";
        if (id != null) {
            path = "/" + id;
        }
        return getJsonData(request + path, null, "GET", clazz);
    }

    @Override
    public <R> R requestByID(String request, String id, Map<String, Object> params, Class<R> clazz) throws UnauthorizedException, GeneralException, NotFoundException {
        String path = "";
        if (id != null) {
            path = "/" + id;
        }
        // Make rest of GET request
        String queryParams = "";
        if (!params.isEmpty()) {
            queryParams = "?" + getPathVariables(params);
        }
        return getJsonData(request + path + queryParams, null, "GET", clazz);
    }

    @Override
    public void deleteByID(String request, String id) throws UnauthorizedException, GeneralException, NotFoundException {
        getJsonData(request + "/" + id, null, "DELETE", null);
    }

    @Override
    public <R> R requestList(String request, Integer offset, Integer limit, Class<R> clazz) throws UnauthorizedException, GeneralException {
        Map<String, Object> map = new LinkedHashMap<>();
        if (offset != null) map.put("offset", String.valueOf(offset));
        if (limit != null) map.put("limit", String.valueOf(limit));
        try {
            return getJsonData(request + "?" + getPathVariables(map), null, "GET", clazz);
        } catch (NotFoundException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public <R> R requestList(String request, PagedPaging pagedPaging, Class<R> clazz) throws UnauthorizedException, GeneralException {
        Map<String, Object> map = new LinkedHashMap<>();
        if (pagedPaging.getPage() != null) map.put("page", String.valueOf(pagedPaging.getPage()));
        if (pagedPaging.getPageSize() != null) map.put("perPage", String.valueOf(pagedPaging.getPageSize()));

        try {
            return getJsonData(request + "?" + getPathVariables(map), null, "GET", clazz);
        } catch (NotFoundException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public <R, P> R sendPayLoad(String request, P payload, Class<R> clazz) throws UnauthorizedException, GeneralException {
        return this.sendPayLoad("POST", request, payload, clazz);
    }

    @Override
    public <R, P> R sendPayLoad(String method, String request, P payload, Class<R> clazz) throws UnauthorizedException, GeneralException {
        if (!REQUEST_METHODS_WITH_PAYLOAD.contains(method)) {
            throw new IllegalArgumentException(String.format(REQUEST_METHOD_NOT_ALLOWED, method));
        }

        try {
            return getJsonData(request, payload, method, clazz);
        } catch (NotFoundException e) {
            throw new GeneralException(e);
        }
    }


    public <T, P> T getJsonData(final String request, final P payload, final String requestType, final Class<T> clazz) throws UnauthorizedException, GeneralException, NotFoundException {
        if (request == null) {
            throw new IllegalArgumentException(REQUEST_VALUE_MUST_BE_SPECIFIED);
        }

        String url = request;
        if (!isURLAbsolute(url)) {
            url = serviceUrl + url;
        }

        final APIResponse apiResponse = doRequest(requestType, url, payload);

        final String body = apiResponse.getBody();
        final int status = apiResponse.getStatus();

        if (status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_CREATED) {
            final ObjectMapper mapper = new ObjectMapper();

            // If we as new properties, we don't want the system to fail, we rather want to ignore them
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                return mapper.readValue(body, clazz);
            } catch (IOException ioe) {
                throw new GeneralException(ioe);
            }
        } else if (status == HttpURLConnection.HTTP_NO_CONTENT) {
            return null; // no content doesn't mean an error
        } else if (status == HttpURLConnection.HTTP_UNAUTHORIZED) {
            final List<ErrorReport> errorReport = getErrorReportOrNull(body);
            throw new UnauthorizedException(NOT_AUTHORISED_MSG, errorReport);
        } else if (status >= 400 && status < 500) { // Any code in the 400 range will have a list of error codes attached
            final List<ErrorReport> errorReport = getErrorReportOrNull(body);
            if (status == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new NotFoundException(errorReport);
            }
            throw new GeneralException(FAILED_DATA_RESPONSE_CODE + status, status, errorReport);
        } else {
            throw new GeneralException(FAILED_DATA_RESPONSE_CODE + status, status);
        }
    }

    /**
     * Actually sends a HTTP request and returns its body and HTTP status code.
     *
     * @param method  HTTP method.
     * @param url     Absolute URL.
     * @param payload Payload to JSON encode for the request body. May be null.
     * @param <P>     Type of the payload.
     * @return APIResponse containing the response's body and status.
     */
    <P> APIResponse doRequest(final String method, final String url, final P payload) throws GeneralException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        if (METHOD_PATCH.equalsIgnoreCase(method)) {
            // It'd perhaps be cleaner to call this in the constructor, but
            // we'd then need to throw GeneralExceptions from there. This means
            // it wouldn't be possible to declare AND initialize _instance_
            // fields of MessageBirdServiceImpl at the same time. This method
            // already throws this exception, so now we don't have to pollute
            // our public API further.
            allowPatchRequestsIfNeeded();
        }

        try {
            connection = getConnection(url, payload, method);
            int status = connection.getResponseCode();

            if (APIResponse.isSuccessStatus(status)) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            return new APIResponse(readToEnd(inputStream), status);
        } catch (IOException ioe) {
            throw new GeneralException(ioe);
        } finally {
            saveClose(inputStream);

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * By default, HttpURLConnection does not support PATCH requests. We can
     * however work around this with reflection. Many thanks to okutane on
     * StackOverflow: https://stackoverflow.com/a/46323891/3521243.
     */
    private synchronized static void allowPatchRequestsIfNeeded() throws GeneralException {
        if (isPatchRequestAllowed) {
            // Don't do anything if we've run this method before. We're in a
            // synchronized block, so return ASAP.
            return;
        }

        try {
            // Ensure we can access the fields we need to set.
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            methodsField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            Object noInstanceBecauseStaticField = null;

            // Determine what methods should be allowed.
            String[] existingMethods = (String[]) methodsField.get(noInstanceBecauseStaticField);
            String[] allowedMethods = getAllowedMethods(existingMethods);

            // Override the actual field to allow PATCH.
            methodsField.set(noInstanceBecauseStaticField, allowedMethods);

            // Set flag so we only have to run this once.
            isPatchRequestAllowed = true;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new GeneralException(CAN_NOT_ALLOW_PATCH);
        }
    }

    /**
     * Appends PATCH to the provided array.
     *
     * @param existingMethods Methods that are, and must be, allowed.
     * @return New array also containing PATCH.
     */
    private static String[] getAllowedMethods(String[] existingMethods) {
        int listCapacity = existingMethods.length + 1;

        List<String> allowedMethods = new ArrayList<>(listCapacity);

        allowedMethods.addAll(Arrays.asList(existingMethods));
        allowedMethods.add(METHOD_PATCH);

        return allowedMethods.toArray(new String[0]);
    }

    /**
     * Reads the stream until it has no more bytes and returns a UTF-8 encoded
     * string representation.
     *
     * @param inputStream Stream to read from.
     * @return UTF-8 encoded string representation of stream's contents.
     */
    private String readToEnd(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

        return scanner.hasNext() ? scanner.next() : "";
    }

    /**
     * Attempts determining whether the provided URL is an absolute one, based on the scheme.
     *
     * @param url provided url
     * @return boolean
     */
    private boolean isURLAbsolute(String url) {
        for (String protocol : PROTOCOLS) {
            if (url.startsWith(protocol)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Create a HttpURLConnection connection object
     *
     * @param serviceUrl  URL that needs to be requested
     * @param postData    PostDATA, must be not null for requestType is POST
     * @param requestType Request type POST requests without a payload will generate a exception
     * @return base class
     * @throws IOException io exception
     */
    public <P> HttpURLConnection getConnection(final String serviceUrl, final P postData, final String requestType) throws IOException {
        if (requestType == null || !REQUEST_METHODS.contains(requestType)) {
            throw new IllegalArgumentException(String.format(REQUEST_METHOD_NOT_ALLOWED, requestType));
        }

        if (postData == null && "POST".equals(requestType)) {
            throw new IllegalArgumentException("POST detected without a payload, please supply a payload with a POST request");
        }

        final URL restService = new URL(serviceUrl);
        final HttpURLConnection connection;
        if (proxy != null) {
            connection = (HttpURLConnection) restService.openConnection(proxy);
        } else {
            connection = (HttpURLConnection) restService.openConnection();
        }
        connection.setDoInput(true);
        connection.setRequestProperty("Accept", "application/json");
        connection.setUseCaches(false);
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Authorization", "AccessKey " + accessKey);
        connection.setRequestProperty("User-agent", userAgentString);

        if ("POST".equals(requestType) || "PATCH".equals(requestType)) {
            connection.setRequestMethod(requestType);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);

            // Specifically set the date format for POST requests so scheduled
            // messages and other things relying on specific date formats don't
            // fail when sending.
            DateFormat df = getDateFormat();
            mapper.setDateFormat(df);

            final String json = mapper.writeValueAsString(postData);
            connection.getOutputStream().write(json.getBytes(String.valueOf(StandardCharsets.UTF_8)));
        } else if ("DELETE".equals(requestType)) {
            // could have just used rquestType as it is
            connection.setDoOutput(false);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "text/plain");
        } else {
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");
        }

        return connection;
    }

    private DateFormat getDateFormat() {
        double javaVersion = DEFAULT_JAVA_VERSION;
        try {
            javaVersion = getVersion();
        } catch (GeneralException e) {
            // Do nothing: leave the version at its default.
        }

        if (javaVersion > 1.6) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        }
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
    }

    private double getVersion() throws GeneralException {
        String version = System.getProperty("java.version");

        try {
            int pos = version.indexOf('.');
            pos = version.indexOf('.', pos + 1);

            return Double.parseDouble(version.substring(0, pos));
        } catch (RuntimeException e) {
            // Thrown if the index is out of bounds, or when we can't parse a
            // double for some reason.
            throw new GeneralException(e);
        }
    }

    /**
     * Get the MessageBird error report data.
     *
     * @param body Raw request body.
     * @return Error report, or null if the body can not be deserialized.
     */
    private List<ErrorReport> getErrorReportOrNull(final String body) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readValue(body, JsonNode.class);
            ErrorReport[] errors = objectMapper.readValue(jsonNode.get("errors").toString(), ErrorReport[].class);

            List<ErrorReport> result = Arrays.asList(errors);

            if (result.isEmpty()) {
                return null;
            }

            return result;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get the used access key
     *
     * @return String
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * get the used service URL
     *
     * @return String
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * Get the client version
     *
     * @return String
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * Get the user agent string
     *
     * @return String
     */
    public String getUserAgentString() {
        return userAgentString;
    }

    /**
     * Enable proxy support
     * example:
     * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
     * messageBirdService.setProxy(proxy);
     *
     * @param proxy proxy
     */
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * Get the proxy object if set
     *
     * @return Proxy
     */
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Safe-close a input stream
     *
     * @param is input stream
     */
    private void saveClose(final InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    /**
     * Build a path variable for GET requests
     *
     * @param map map for getting path variables
     * @return String
     */
    private String getPathVariables(final Map<String, Object> map) {
        final StringBuilder bpath = new StringBuilder();
        for (Map.Entry<String, Object> param : map.entrySet()) {
            if (bpath.length() > 1) {
                bpath.append("&");
            }
            try {
                bpath.append(URLEncoder.encode(param.getKey(), String.valueOf(StandardCharsets.UTF_8))).append("=").append(URLEncoder.encode(String.valueOf(param.getValue()), String.valueOf(StandardCharsets.UTF_8)));
            } catch (UnsupportedEncodingException exception) {
                // Do nothing
            }
        }
        return bpath.toString();
    }
}
