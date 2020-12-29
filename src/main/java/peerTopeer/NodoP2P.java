package peerTopeer;

import com.google.gson.Gson;
import com.subscribe.grpc.SubscribeServiceGrpc;
import com.subscribe.grpc.SubscribeServiceOuterClass.ACK;
import com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import gateway.beans.Nodo;
import io.grpc.*;
import com.subscribe.grpc.SubscribeServiceOuterClass.NodoId;
import io.grpc.stub.StreamObserver;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.subscribe.grpc.SubscribeServiceGrpc.SubscribeServiceBlockingStub;
import com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest;
import com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse;
import simulatore.BufferPM10;
import simulatore.Measurement;
import simulatore.PM10Simulator;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class NodoP2P {
    private int gateway_port;
    private ArrayList<Nodo> ring;
    private int id, port;
    private String ipAddress;
    private volatile boolean hasToken;
    private Nodo nextInRing;
    BufferPM10 buffer;
    private PM10Simulator simulator;
    private volatile boolean hasInsertMeasure;
    private volatile boolean hasFirst; // flag che mi dice se sono il primo nodo ad aver effettuato l'accesso alla rete.
    private volatile boolean hasSent; // flag che mi dice se il token è stato avviato nella rete oppure no.



    public boolean isHasInsertMeasure() {
        return hasInsertMeasure;
    }

    public void setHasInsertMeasure(boolean hasInsertMeasure) {
        this.hasInsertMeasure = hasInsertMeasure;
    }


    public ArrayList<Nodo> getRing() {
        synchronized (this.ring) {
            return ring;
        }
    }

    public boolean isHasSent() {
        return hasSent;
    }

    public void setHasSent(boolean hasSent) {
        this.hasSent = hasSent;
    }

    public NodoP2P(int id, int port, String ipAddress, int gateway_port){
        this.gateway_port = gateway_port;
        this.id = id;
        this.port = port;
        this.ipAddress = ipAddress;
        this.hasToken = false;
        this.nextInRing = null;
        this.buffer = new BufferPM10(this);
        this.buffer.start();
        this.simulator = new PM10Simulator(this.buffer);
        this.simulator.start();
        this.hasInsertMeasure = false;
        this.hasFirst = false;
        this.hasSent = false;
    }

    public void addNodeToList(Nodo n) {
        synchronized (this.ring) {
            if (!ring.contains(n))
                this.ring.add(n);
        }
    }

    public void removeNodeFromList(Nodo n) {
        synchronized (this.ring) {
            this.ring.removeIf(t -> t.getId() == n.getId());
        }
    }

    public int getId() {
        return this.id;
    }

    public void orderRing() {
        synchronized (this.ring) {
            Collections.sort(this.ring);
        }
    }

    public ArrayList<Nodo> getNodes(Client c) throws JSONException {
        ArrayList<Nodo> listNodes = new ArrayList<>();
        WebResource r = c.resource("http://localhost:" + this.gateway_port + "/nodes/");
        JSONObject jsonObject = r.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).get(JSONObject.class);
        JSONArray response = jsonObject.getJSONArray("nodo");
        for (int i = 0; i < response.length(); i++) {
            JSONObject rec = response.getJSONObject(i);
            int id = rec.getInt("id");
            String ip = rec.getString("ipAddress");
            int port = rec.getInt("port");
            listNodes.add(new Nodo(id, port, ip));
        }
        return listNodes;
    }

    public int getGateway_port() {
        return gateway_port;
    }

    public boolean isHasFirst() {
        return hasFirst;
    }

    public void setHasFirst(boolean hasFirst) {
        this.hasFirst = hasFirst;
    }

    public void subscribeToNetwork(Client c) throws JSONException{
        WebResource r = c.resource("http://localhost:" + this.gateway_port + "/nodes/add/");
        Gson gson = new Gson();
        String input = gson.toJson(new Nodo(this.id, this.port, this.ipAddress));
        ClientResponse response = r.accept("application/json").type("application/json").post(ClientResponse.class, input);
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        this.ring = new ArrayList<>(getNodes(new Client()));

        if (response.getStatus() == 403) {
            throw new RuntimeException("Non è stato possibile accedere alla rete in quanto è già presente un nodo avente ID " + this.id);
        } else if (response.getStatus() != 200) {
            throw new RuntimeException("HTTP error code : " + response.getStatus());
        }

        System.out.println("Accesso alla rete avvenuto con successo! [" + ts + "]\n\n***** NODI CONNESSI ALL'INTERNO DELLA RETE *****:");

        if (this.getRing().size() == 1) {
            this.setHasToken(true);
            this.setHasFirst(true);
        }
        else{ // ci permette di gestire il caso in cui due nodi entrano contemporaneamente per primi nella rete
            ArrayList<Nodo> cc = new ArrayList<>(this.getRing());
            int min = 2147483647;
            for(Nodo n: cc){
                if(n.getId() < min) min = n.getId();
            }
            if(min == this.getId()) {
                System.out.println("\n----------------------------------------------");
                System.out.println("Verifico che il token non si sia perso...");
                System.out.println("----------------------------------------------\n");
                asyncGetToken();
            }
        }
        orderRing();
        ArrayList<Nodo> copy;
        copy = new ArrayList<>(this.getRing());

        for (Nodo n : copy) {
            System.out.println("------------------------------------------");
            System.out.println("ID: " + n.getId() + "  PORT: " + n.getPort() + "  IP-ADDRESS: " + n.getIpAddress());
        }
        System.out.println("------------------------------------------");

        getNextNode();
        if (this.nextInRing != null)
            System.out.println("Nodo successivo all'interno dell'anello: " + this.nextInRing.toString() + ".");

        if (this.getRing().size() == 1)
            System.out.println("Attendo altri nodi...");
    }


    public boolean containsNodeWithId(int id) {
        ArrayList<Nodo> copy;
        synchronized (this.ring) {
            copy = new ArrayList<>(this.ring);
        }
        for (Nodo n : copy) {
            if (n.getId() == id) return true;
        }
        return false;
    }

    public void unsubscribeToNetwork(Client c) {
        try {
            ArrayList<Nodo> copy;
            copy = new ArrayList<>(this.getRing());
            for (Nodo n : copy) {
                if (n.getId() != this.id) {
                    final ManagedChannel channel = ManagedChannelBuilder.forTarget(n.getIpAddress() + ":" + n.getPort()).usePlaintext(true).build();
                    SubscribeServiceBlockingStub stub = SubscribeServiceGrpc.newBlockingStub(channel);
                    SubscribeRequest request = SubscribeRequest.newBuilder().setId(this.id).setPort(this.port).setIp(this.ipAddress).build();
                    SubscribeResponse response = stub.unsubscribeFrom(request);
                    channel.shutdown();
                }
            }
            WebResource r = c.resource("http://localhost:" + this.gateway_port + "/nodes/remove/" + this.id);
            r.delete();
        } catch (StatusRuntimeException ex) {
            System.out.println("Riprovo a disconnettermi...");
            unsubscribeToNetwork(c);
        }
    }

    public void sendInfNode() { // Comunico agli altri nodi la mia entrata nella rete
        ArrayList<Nodo> copy;
        synchronized (this.ring) {
            copy = new ArrayList<>(this.ring);
        }
        for (Nodo n : copy) {
            if (n.getId() != this.id) {
                try {
                    final ManagedChannel channel = ManagedChannelBuilder.forTarget(n.getIpAddress() + ":" + n.getPort()).usePlaintext(true).build();
                    SubscribeServiceBlockingStub stub = SubscribeServiceGrpc.newBlockingStub(channel);
                    SubscribeRequest request = SubscribeRequest.newBuilder().setId(this.id).setPort(this.port).setIp(this.ipAddress).build();
                    SubscribeResponse response = stub.subscribeTo(request);
                    channel.shutdown();
                    if (!response.getAck()) {
                        this.unsubscribeToNetwork(new Client());
                        throw new RuntimeException("La comunicazione con il nodo " + n.getId() + " è fallita! Sei stato disconnesso!");
                    }
                }catch(StatusRuntimeException ex){
                    System.out.println("Errore! Il nodo con cui comunicare non è connesso alla rete.");
                    this.removeNodeFromList(n);
                }
            }
        }
    }

    public Nodo getNextInRing() {
       synchronized (this.nextInRing) {
            return nextInRing;
       }
    }

    public void getNextNode() {
        synchronized (this.ring) {
            if (this.ring.size() == 1)
                this.nextInRing = null;
            else {
                for (int i = 0; i < this.ring.size(); i++) {
                    if (ring.get(i).getId() == this.id) {
                        int index = ((i + 1) % this.ring.size());
                        this.nextInRing = new Nodo(this.ring.get(index).getId(), this.ring.get(index).getPort(), this.ring.get(index).getIpAddress());
                    }
                }
            }
        }
    }


    public boolean getHasToken() {
        return hasToken;
    }

    public void setHasToken(boolean hasToken) {
        this.hasToken = hasToken;
    }

    public void sendTokenToNext(ArrayList<Measurement> ms) {
        ManagedChannel channel = null;
        try {
            SubscribeServiceBlockingStub stub;
            TokenSend.Builder request;
            channel = ManagedChannelBuilder.forTarget(this.getNextInRing().getIpAddress() + ":" + this.getNextInRing().getPort()).usePlaintext(true).build();
            stub = SubscribeServiceGrpc.newBlockingStub(channel);
            request = TokenSend.newBuilder();
            for (Measurement m : ms) {
                request.addMeasure(TokenSend.Measure.newBuilder().setValue(m.getValue()).setSender(Integer.parseInt(m.getId())).setTimestamp(m.getTimestamp()).build());
            }
            TokenSend token = request.build();
            this.setHasToken(false);
            ACK response = stub.insertMeasureToken(token);
            channel.shutdown();
        } catch (NullPointerException | StatusRuntimeException ex) {
            if (this.getRing().size() > 1) {
                channel.shutdown();
                sendTokenToNext(ms);
            } else {
                this.setHasToken(true);
            }
        }
    }


    public void asyncGetToken(){
        boolean[] f = new boolean[1];
        f[0] = false;
        for (Nodo n : this.getRing()) {
            if(n.getId() != this.getId()) {
                final ManagedChannel channel = ManagedChannelBuilder.forTarget(n.getIpAddress() + ":" + n.getPort()).usePlaintext(true).build();
                SubscribeServiceGrpc.SubscribeServiceStub stub = SubscribeServiceGrpc.newStub(channel);
                NodoId request = NodoId.newBuilder().setId(this.getId()).build();
                stub.hasTokenRPC(request, new StreamObserver<ACK>() {
                    boolean flag = false;
                    public void onNext(ACK response) {
                        flag = (flag && response.getAck());
                    }
                    public void onError(Throwable throwable) {
                        System.out.println("Errore nella comunicazione: " + throwable.getMessage());
                    }
                    public void onCompleted() {
                        if (flag)
                            f[0] = true;
                        channel.shutdownNow();
                    }
                });
            }
        }
        if(!f[0]){
            this.setHasFirst(true);
            this.setHasInsertMeasure(false);
            this.setHasToken(true);
        }
    }

}

