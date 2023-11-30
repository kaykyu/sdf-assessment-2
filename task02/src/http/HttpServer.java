package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;


public class HttpServer {

    private static Integer PORT;
    private static String root; 

    public static String getRoot() {
        return root;
    }

    public static void main(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Invalid command prompts");
            System.exit(0);
        }

        PORT = Integer.parseInt(args[0]);
        root = args[1];

        while (true) {

            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println("Listening on port %d, waiting for connection".formatted(PORT));
                Socket client = server.accept();
                System.out.println("Client connection accepted");

                InputStream is = client.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String line = br.readLine();

                if (line.startsWith("GET")) {
                    String request = line.split(" ")[1];

                    File resource = new File("%s%s".formatted(root, request));
                    System.out.println(resource.getAbsolutePath());
                    HttpWriter writer = new HttpWriter(client.getOutputStream());

                    if (!resource.exists()) {
                        writer.writeString("HTTP/1.1 404 Not Found");
                        writer.writeString("Content-Type: text/html");
                        writer.writeString();
                        writer.writeString("Resource %s not found".formatted(request.substring(1)));
                        writer.flush();
                    } else {
                        writer.writeString("HTTP/1.1 200 OK");
                        writer.writeString("Content-Type: text/html");
                        writer.writeString();
                        writer.writeBytes(Files.readAllBytes(resource.toPath()));
                        writer.flush();
                    }
                }

                client.close();

            } catch (Exception e) {
                System.out.println("Error");
            }
        }    
    }
}
