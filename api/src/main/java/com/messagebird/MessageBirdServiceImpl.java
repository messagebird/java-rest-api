package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.ErrorReport;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

/**
 * Implementation of MessageBirdService
 * Sends and receives JSON objects from the Messagebird platform
 *
 * Created by rvt on 1/5/15.
 */
public class MessageBirdServiceImpl implements MessageBirdService {
    private static final String NOT_AUTHORISED_MSG = "You are not authorised for the MessageBird service, please check your access key.";
    private static final String FAILED_DATA_RESPONSE_CODE = "Failed to retrieve data from MessageBird service with response code ";
    private static final String ACCESS_KEY_MUST_BE_SPECIFIED = "Access key must be specified";
    private static final String SERVICE_URL_MUST_BE_SPECIFIED = "Service URL must be specified";
    private static final String REQUEST_VALUE_MUST_BE_SPECIFIED = "Request value must be specified";
    private static final String REQUEST_TYPE_MUST_BE_SET_TO_GET_OR_POST = "Request type must be set to GET, POST or DELETE";
    private static final List<String> REQUESTMETHODS = Arrays.asList(new String[]{"GET", "POST", "DELETE"});
    private final String accessKey;
    private final String serviceUrl = "https://rest.messagebird.com";
    private final String clientVersion = "1.2.1";
    private final String userAgentString = "MessageBird/Java ApiClient/" + clientVersion;
    private Proxy proxy = null;

    public MessageBirdServiceImpl(final String accessKey, final String serviceUrl) {
        if (accessKey == null) {
            throw new IllegalArgumentException(ACCESS_KEY_MUST_BE_SPECIFIED);
        }
        if (serviceUrl == null || serviceUrl.length() == 0) {
            throw new IllegalArgumentException(SERVICE_URL_MUST_BE_SPECIFIED);
        }
        this.accessKey = accessKey;
    }

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
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (offset!=null) map.put("offset", String.valueOf(offset));
        if (limit!=null) map.put("limit", String.valueOf(limit));
        try {
            return getJsonData(request+"?"+getPathVariables(map), null, "GET", clazz);
        } catch (NotFoundException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public <R, P> R sendPayLoad(String request, P payLoad, Class<R> clazz) throws UnauthorizedException, GeneralException {
        try {
            return getJsonData(request, payLoad, "POST", clazz);
        } catch (NotFoundException e) {
            throw new GeneralException(e);
        }
    }


    public <T, P> T getJsonData(final String request, final P payload, final String requestType, final Class<T> clazz) throws UnauthorizedException, GeneralException, NotFoundException {
        if (request == null) {
            throw new IllegalArgumentException(REQUEST_VALUE_MUST_BE_SPECIFIED);
        }
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        int responseCode = -1;
        try {
            connection = getConnection(serviceUrl + request, payload, requestType);
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                inputStream = connection.getInputStream();
                final ObjectMapper mapper = new ObjectMapper();
                // If we as new properties, we don't want the system to fail, we rather want to ignore them
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(inputStream, clazz);
            } else if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
               return null; // no content doesn't mean an error
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                final List<ErrorReport> errorReport = getErrorReport(connection.getErrorStream());
                throw new UnauthorizedException(NOT_AUTHORISED_MSG, errorReport);
            } else if (responseCode >= 400 && responseCode < 500) { // Any code in the 400 range will have a list of error codes attached
                final List<ErrorReport> errorReport = getErrorReport(connection.getErrorStream());
                if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    throw new NotFoundException(errorReport);
                }
                throw new GeneralException(FAILED_DATA_RESPONSE_CODE + responseCode, responseCode, errorReport);
            } else {
                throw new GeneralException(FAILED_DATA_RESPONSE_CODE + responseCode, responseCode);
            }
        } catch (IOException e) {
            throw new GeneralException(e);
        } finally {
            saveClose(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Create a HttpURLConnection connection object
     *
     * @param serviceUrl  URL that needs to be requested
     * @param postData    PostDATA, must be not null for requestType is POST
     * @param requestType Request type POST requests without a payload will generate a exception
     * @return
     * @throws IOException
     */
    public <P> HttpURLConnection getConnection(final String serviceUrl, final P postData, final String requestType) throws IOException {
        if (requestType == null || !REQUESTMETHODS.contains(requestType)) {
            throw new IllegalArgumentException(REQUEST_TYPE_MUST_BE_SET_TO_GET_OR_POST);
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

        if ("POST".equals(requestType)) {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

            final String json = mapper.writeValueAsString(postData);
            connection.getOutputStream().write(json.getBytes("UTF-8"));
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

    /**
     * Get the MessageBird error report data
     *
     * @param inputStream, this would usually be the errorStream from the connection
     * @return will be null when no data was found or there was a error receicing the dataset
     */
    private List<ErrorReport> getErrorReport(final InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        List<ErrorReport> errorReport = null;
        try {
            node = mapper.readValue(inputStream, JsonNode.class);
            errorReport = Arrays.asList(mapper.readValue(node.get("errors"), ErrorReport[].class));
        } catch (IOException e) {
            // we will ignore this and simply leave the error report null
        }
        // In some conditions you might get a 404 but with a valid response
        if (errorReport != null && errorReport.size() == 0) {
            errorReport = null;
        }
        return errorReport;
    }

    /**
     * Get the used access key
     * @return
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * get the used service URL
     * @return
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * Get the client version
     * @return
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * Get the user agent string
     * @return
     */
    public String getUserAgentString() {
        return userAgentString;
    }

    /**
     * Enable proxy support
     * example:
     * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
     * messageBirdService.setProxy(proxy);
     * @param proxy
     */
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * Get the proxy object if set
     * @return
     */
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Safe-close a input stream
     * @param is
     */
    private void saveClose(final InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Build a path variable for GET requests
     * @param map
     * @return
     */
    private String getPathVariables(final Map<String, Object> map) {
        final StringBuilder bpath = new StringBuilder();
        for (Map.Entry<String, Object> param : map.entrySet()) {
            if (bpath.length() > 1) {
                bpath.append("&");
            }
            try {
                bpath.append(URLEncoder.encode(param.getKey(), "UTF-8") + "=" + URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        return bpath.toString();
    }


}
