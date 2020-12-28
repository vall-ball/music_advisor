package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class SimpleHandler implements HttpHandler {
    String query;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        query = exchange.getRequestURI().getQuery();
        String request;
        if(query != null && query.contains("code")) {
            //ACCESS_CODE = query.substring(5);
            //System.out.println("code received " + quer
            request = "Got the code. Return back to your program.";
        } else {
            request = "Authorization code not found. Try again.";
        }
        exchange.sendResponseHeaders(200, request.length());
        exchange.getResponseBody().write(request.getBytes());
        exchange.getResponseBody().close();
    }

    public String getQuery() {
        return query;
    }
}
