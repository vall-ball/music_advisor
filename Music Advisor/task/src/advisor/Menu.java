package advisor;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Menu {
    final static Scanner scanner = new Scanner(System.in);
    public String spotifyAccessServerPoint;
    public String query;
    boolean haveAccess = false;

    Menu(String spotifyAccessServerPoint) {
        this.spotifyAccessServerPoint = spotifyAccessServerPoint;
    }

    public void process() throws IOException, InterruptedException {
        while (true) {
        String choice = scanner.nextLine();
        switch (choice) {
            case "new":
                if (!haveAccess) {
                    System.out.println("Please, provide access for application.");
                } else {
                    System.out.println("---NEW RELEASES---");
                    System.out.println("Mountains [Sia, Diplo, Labrinth]");
                    System.out.println("Runaway [Lil Peep]");
                    System.out.println("The Greatest Show [Panic! At The Disco]");
                    System.out.println("All Out Life [Slipknot]");
                }
                break;
            case "featured":
                if (!haveAccess) {
                    System.out.println("Please, provide access for application.");
                } else {
                    System.out.println("---FEATURED---");
                    System.out.println("Mellow Morning");
                    System.out.println("Wake Up and Smell the Coffee");
                    System.out.println("Monday Motivation");
                    System.out.println("Songs to Sing in the Shower");
                }
                break;
            case "categories":
                if (!haveAccess) {
                    System.out.println("Please, provide access for application.");
                } else {
                    System.out.println("---CATEGORIES---");
                    System.out.println("Top Lists");
                    System.out.println("Pop");
                    System.out.println("Mood");
                    System.out.println("Latin");
                }
                break;
            case "playlists Mood":
                if (!haveAccess) {
                    System.out.println("Please, provide access for application.");
                } else {
                    System.out.println("---MOOD PLAYLISTS---");
                    System.out.println("Walk Like A Badass");
                    System.out.println("Rage Beats");
                    System.out.println("Arab Mood Booster");
                    System.out.println("Sunday Stroll");
                }
                break;
            case "exit":
                System.out.println("---GOODBYE!---");
                return;
            case "auth":
                auth();
                break;
            default:
                if (!haveAccess) {
                    System.out.println("Please, provide access for application.");
                } else {
                    System.out.println("---MOOD PLAYLISTS---");
                    System.out.println("Mountains [Sia, Diplo, Labrinth]");
                    System.out.println("Runaway [Lil Peep]");
                    System.out.println("The Greatest Show [Panic! At The Disco]");
                    System.out.println("All Out Life [Slipknot]");
                }
                break;
            }

        }
    }

    public void auth() throws IOException, InterruptedException {
        SimpleHandler simpleHandler = new SimpleHandler();
        SimpleServer simpleServer = new SimpleServer(8080, "/", simpleHandler);
        simpleServer.start();
        boolean flag = true;
            System.out.println("use this link to request the access code:");
            System.out.println(spotifyAccessServerPoint + "/authorize?client_id=ee24700319a14241b46f961e39ba4374&redirect_uri=http://localhost:8080&response_type=code");
            System.out.println("waiting for code...");
            while (query == null) {
                Thread.sleep(10);
                query = simpleHandler.getQuery();
            }
            //System.out.println(query);

            if (query.contains("code=")) {
                simpleServer.stop();
                System.out.println("Got the code. Return back to your program.");
                String code = query.split("=")[1];
                //System.out.println(code);
                HttpClient client = HttpClient.newBuilder().build();
                String s = "grant_type=authorization_code&" +
                        "code=" + code +
                        "&redirect_uri=http://localhost:8080" +
                        "&client_id=ee24700319a14241b46f961e39ba4374" +
                        "&&client_secret=57cf2d1a6f274d5bae7e2434c3c1213c";
               // System.out.println(s);
                HttpRequest req = HttpRequest.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .uri(URI.create(spotifyAccessServerPoint + "/api/token"))
                        .POST(HttpRequest.BodyPublishers.
                                ofString(s))
                        .build();
                //System.out.println(req.bodyPublisher().get().contentLength());
                HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
                if (response.body().contains("access_token")) {
                    System.out.println("response:");
                    System.out.println(response.body());
                    System.out.println("---SUCCESS---");
                    flag = false;
                    haveAccess = true;
                }
            } else {
                System.out.println("Not found authorization code. Try again.");
            }
        //System.out.println("code received");

        //simpleServer.stop();

    }
}
