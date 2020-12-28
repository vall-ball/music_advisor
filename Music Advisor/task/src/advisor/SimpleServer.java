package advisor;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleServer {

    private HttpServer httpServer;
    String query = null;
    public SimpleServer(int port, String context, HttpHandler handler) throws IOException {

        try {
            //Create HttpServer which is listening on the given port
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            //Create a new context for the given context and handler
            httpServer.createContext(context, handler);
            //Create a default executor
            //httpServer.setExecutor(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Start.
     */
    public void start() {
        System.out.println("Server started");
        this.httpServer.start();
    }

    public void stop() {
        this.httpServer.stop(10);
    }

}

