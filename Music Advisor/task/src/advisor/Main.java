package advisor;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String spotifyAccessServerPoint;
        if (args.length != 0) {
            spotifyAccessServerPoint = args[1];
        } else {
            spotifyAccessServerPoint = "https://accounts.spotify.com";
        }

        Menu menu = new Menu(spotifyAccessServerPoint);
        menu.process();

    }
}
