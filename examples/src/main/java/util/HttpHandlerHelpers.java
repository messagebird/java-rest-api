package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Some useful helpers for JRE built-in HTTP server
 */
public abstract class HttpHandlerHelpers {

    protected void sendResponse(HttpExchange he, int status, String message) throws IOException {
        byte[] response = message.getBytes(
                StandardCharsets.UTF_8);
        he.sendResponseHeaders(status, response.length);
        OutputStream os = he.getResponseBody();
        os.write(response);
        os.close();
    }

    protected Map<String, String> parseQuery(String query) {
        Map<String, String> queryParams = new HashMap<>();
        for (String param: query.split("&")) {
            String[] paramParts = param.split("=", 2);
            if (paramParts.length == 1) {
                queryParams.put(paramParts[0], "");
            } else if (paramParts.length == 2) {
                queryParams.put(paramParts[0], paramParts[1]);
            }
        }
        return queryParams;
    }

    protected byte[] readAllBytes(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        List<byte[]> byteBuffers = new ArrayList<>();
        int totalSize = 0;

        for (int bytesRead = is.read(buffer); bytesRead >= 0; ) {
            totalSize += bytesRead;
            byteBuffers.add(Arrays.copyOf(buffer, bytesRead));
            bytesRead = is.read(buffer);
        }
        is.close();

        byte[] resultBuffer = new byte[totalSize];
        int resultIdx = 0;
        for (byte[] nextPart: byteBuffers) {
            for (byte b: nextPart) {
                resultBuffer[resultIdx] = b;
                resultIdx += 1;
            }
        }
        return resultBuffer;
    }

    protected void printRequest(String method, String uri, String body) {
        System.err.println("\t" + method + " " + uri);
        if (!body.isEmpty()) {
            System.err.println("\t" + body);
        }
    }
}
