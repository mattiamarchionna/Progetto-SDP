package peerTopeer;

import com.sun.jersey.api.client.Client;
import io.grpc.*;
import org.codehaus.jettison.json.JSONException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainNodo {
    public static void main(String[] args) throws JSONException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        /*System.out.print("- Inserisci ID: ");
        int id = scan.nextInt();*/
        int id = Integer.parseInt(args[0]); // test
        /*System.out.print("- Inserisci PORTA: ");
        int port = scan.nextInt();*/
        int port = Integer.parseInt(args[1]); // test
        /*System.out.print("- Inserisci INDIRIZZO IP: ");
        String ip = scan.next();*/
        String ip = args[2]; // test
        int gateway_port = 1337;

        Client c = new Client();

        NodoP2P n = new NodoP2P(id, port, ip, gateway_port);

        n.subscribeToNetwork(c);

        Server server = null;
        try {
            server = ServerBuilder.forPort(port).addService(new SubscribeServiceImpl(id, n)).build();
            server.start();
        } catch (IOException e) {
            System.out.println("Error server");
        }


        try {
            n.sendInfNode();
        } catch (StatusRuntimeException ex) {
            throw new RuntimeException("Non è stato possibile comunicare con gli altri nodi della rete. Riprova!");
        }


        while (true) {
            String message = scan.next();
            if (message.equals("quit") || message.equals("q")) {
                System.out.println("Disconnessione in corso...");
                n.unsubscribeToNetwork(c);
                server.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("Disconnessione dalla rete avvenuta con successo!");
                System.exit(0);
            }
        }

    }
}
