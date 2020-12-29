package analista;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import gateway.beans.Statistica;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Analista {
    public static void main(String[] args) {
        Scanner c = new Scanner(System.in);
        String digit;
        System.out.println("------------------------------------------------------------------------------------------------------------\n" +
                "Digitare:\n" +
                "- 1 - se desideri ricevere il numero di nodi presenti nella rete.\n" +
                "- 2 - se desideri riceve le ultime n statistiche di quartiere.\n" +
                "- 3 - se desideri ricevere la media delle ultime n statistiche prodotte dal quartiere.\n" +
                "- 4 - se desideri ricevere la deviazione standard delle ultime n statistiche prodotte dal quartiere.\n" +
                "- 5 - se desideri ricevere il numero delle statistiche generate.\n" +
                "- quit - se desideri uscire dall'applicazione.\n" +
                "------------------------------------------------------------------------------------------------------------");
        while(true){
            System.out.print("Cosa desideri fare? ");
            digit = c.next();
            switch (digit) {
                case "1":
                    getNumberOfNode();
                    break;
                case "2":
                    getNstatistics();
                    break;
                case "3":
                    getNmedia();
                    break;
                case "4":
                    getNdevStd();
                    break;
                case "5":
                    System.out.println("Statistiche generate: " + getNumberStatistics() + ".");
                    break;
                case "quit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Inserire un valore valido...");
            }
        }
    }

    private static void getNumberOfNode(){
        Client c = new Client();
        int gateway_port = 1337;
        WebResource r = c.resource("http://localhost:" + gateway_port + "/nodes/numbers");
        String response = r.accept(MediaType.TEXT_PLAIN).get(String.class);
        System.out.println("Numero nodi connessi alla rete: " + response + ".");
    }

    private static int getNumberStatistics(){
        Client c = new Client();
        int gateway_port = 1337;
        WebResource r = c.resource("http://localhost:" + gateway_port + "/statistics/number/");
        String response = r.accept(MediaType.TEXT_PLAIN).get(String.class);
        return Integer.parseInt(response);
    }

    private static void getNstatistics() {
        Scanner s = new Scanner(System.in);
        int gateway_port = 1337;
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true); // Una funzione ResourceConfig, che consente di abilitare la funzionalità di mappatura JSON / POJO in Jersey.
        Client c = Client.create(clientConfig);
        System.out.print("Inserisci il numero delle ultime n statistiche che vuoi visionare: ");
        int n = s.nextInt();
        WebResource r = c.resource("http://localhost:" + gateway_port + "/statistics/" + n);
        ClientResponse response = r.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        List<Statistica> listObject = response.getEntity(new GenericType<List<Statistica>>(){});
        if(n != listObject.size()) System.out.println("Il numero che hai inserito è maggiore delle statistiche presenti nel gateway. Statistiche generate: " + listObject.size() + ".");
        else{
        for(int i = 0; i < listObject.size(); i++) {
            Statistica rec = listObject.get(i);
            long timestamp = rec.getTimestamp();
            double value = rec.getValue();
            System.out.println("--------------------------------|-------------------------------------");
            if(String.valueOf(value).length() > 16)
                System.out.println((i + 1) + ". VALORE: " + String.valueOf(value).substring(0, 17) + "      DATA: " + new Date(new Timestamp(timestamp).getTime()));
            else
                System.out.println((i + 1) + ". VALORE: " + String.valueOf(value).substring(0, 15) + "00      DATA: " + new Date(new Timestamp(timestamp).getTime()));
        }
            System.out.println("--------------------------------|-------------------------------------");
        }
    }

    private static void getNmedia(){
        Scanner s = new Scanner(System.in);
        int gateway_port = 1337;
        Client c = new Client();
        int number_st = getNumberStatistics();
        System.out.print("Inserisci il numero delle ultime n statistiche di cui fare la media: ");
        int n = s.nextInt();
        if(n > number_st){
            System.out.println("Le statistiche totali presenti nel sistema sono " + number_st + ". Inserire un numero compreso tra 1 e " + number_st + ".");
        }
        else{
            WebResource r = c.resource("http://localhost:" + gateway_port + "/statistics/media/" + n);
            String response = r.accept(MediaType.TEXT_PLAIN).get(String.class);
            System.out.println("Media delle ultime " + n + " statistiche: " + response);
        }
    }

    private static void getNdevStd(){
        Scanner s = new Scanner(System.in);
        int gateway_port = 1337;
        Client c = new Client();
        int number_st = getNumberStatistics();
        System.out.print("Inserisci il numero delle ultime n statistiche di cui calcolare la deviazione standard: ");
        int n = s.nextInt();
        if(n > number_st){
            System.out.println("Le statistiche totali presenti nel sistema sono " + number_st + ". Inserire un numero compreso tra 1 e " + number_st + ".");
        }
        else{
            WebResource r = c.resource("http://localhost:" + gateway_port + "/statistics/dev_standard/" + n);
            String response = r.accept(MediaType.TEXT_PLAIN).get(String.class);
            System.out.println("Deviazione standard delle ultime " + n + " statistiche: " + response);
        }
    }
}
