package http.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest implements Request {

    private final InputStream input;
    private final String request;
    private final String uriWithParam;
    private final String type;
    private final Map<String, String> parameterMap;

    public HttpRequest(InputStream input) {
        this.input = input;
        this.request = convertInputStreamToString(input);
        this.uriWithParam = parseUri(request);
        this.parameterMap = parseParameterMap(request);
        this.type = parseType(request);
    }

    private String parseType(String request) {
        if (request.isEmpty()) {
            return "";
        }

        int index1 = 0;
        if (index1 != -1) {
            int index2 = request.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return request.substring(index1, index2);
            }
        }

        return "";
    }

    private String convertInputStreamToString(InputStream in)
    {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(in));

        StringBuilder fullRequest = new StringBuilder();

        try {
            while (true) {
                String line =  br.readLine();
                System.out.println(line);
                if ((line == null) || line.isEmpty()) {
                    break;
                }

                fullRequest.append(line);
                fullRequest.append("\r\n");                
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return fullRequest.toString();
    }

    private String parseUri(String requestString) {

        if (requestString.isEmpty()) {
            return "";
        }

        int index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            int index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }

        return "";
    }

    private Map<String, String> parseParameterMap(String request) {
        Map<String, String> requestMap = new HashMap<>();
        if (request.isEmpty()) {
            return requestMap;
        }

        int startIndex = request.indexOf('?');
        if (startIndex != -1) {
            System.out.println(request.indexOf(' '));
            System.out.println(request.indexOf(' ', startIndex+1));
            int endIndex = request.indexOf(' ', startIndex+ 1);

            while (endIndex > startIndex)
            {
                int equallyIndex = request.indexOf('=', startIndex+1);
                int ampersandIndex = request.indexOf('&', equallyIndex+1);
                System.out.println(ampersandIndex);

                if (ampersandIndex == -1 || ampersandIndex > endIndex) ampersandIndex = endIndex;
                if (equallyIndex == -1) return requestMap;

                String key = request.substring(startIndex+1, equallyIndex);
                System.out.println(key);
                String value = request.substring(equallyIndex+1, ampersandIndex);
                System.out.println(value);
                requestMap.put(key, value);

                startIndex = ampersandIndex;
            }

        }

        return requestMap;
    }

    @Override
    public String getURI() {
        return uriWithParam;
    }

    @Override
    public String getParameter(String name) {
        return parameterMap.get(name);
    }

    @Override
    public Set<String> getParameterNames() {
        return parameterMap.keySet();
    }

    @Override
    public Collection<String> getParameterValues() {
        return parameterMap.values();
    }

    @Override
    public String getRequestAsText() {
        return request;
    }

    public String getType() { return type;}

}
