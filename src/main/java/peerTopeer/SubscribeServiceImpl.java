package peerTopeer;

import com.google.gson.Gson;
import com.subscribe.grpc.SubscribeServiceGrpc;
import com.subscribe.grpc.SubscribeServiceGrpc.SubscribeServiceImplBase;
import com.subscribe.grpc.SubscribeServiceOuterClass.NodoId;
import com.subscribe.grpc.SubscribeServiceOuterClass.ACK;
import com.subscribe.grpc.SubscribeServiceOuterClass.TokenSend;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import gateway.beans.Nodo;
import gateway.beans.Statistica;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeRequest;
import com.subscribe.grpc.SubscribeServiceOuterClass.SubscribeResponse;
import simulatore.Measurement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class SubscribeServiceImpl extends SubscribeServiceImplBase {
    private int id;
    private NodoP2P nodo;

    public SubscribeServiceImpl(int id, NodoP2P n){
        super();
        this.id = id;
        this.nodo = n;
    }

    @Override
    public void subscribeTo(SubscribeRequest request, StreamObserver<SubscribeResponse> responseObserver) { // quando un nodo intende iscriversi utilizza questo servizio di ogni nodo (lato server)
        int id_n = request.getId();
        String ip = request.getIp();
        int port = request.getPort();
        Nodo n = new Nodo(id_n, port, ip);
        if(!nodo.containsNodeWithId(n.getId())) {

            Date date= new Date();
            Timestamp ts = new Timestamp(date.getTime());

            nodo.addNodeToList(n);
            nodo.orderRing();
            nodo.getNextNode();

            System.out.println("Nodo " + id_n + " in ascolto sulla porta " + port + " aggiunto alla rete P2P! [" + ts + "]");
            System.out.println("Nodo successivo all'interno dell'anello: " + nodo.getNextInRing());

        }

        SubscribeResponse response = SubscribeResponse.newBuilder().setAck(true).setId(this.id).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        // avvio del token. Queste condizioni sono necessarie altrimenti potrebbero essere avviati più token nell'anello.
        if(nodo.getHasToken() && nodo.isHasFirst() && (!nodo.isHasSent())) {
            nodo.setHasSent(true);
            ArrayList<Measurement> ms = new ArrayList<>();
            if (!nodo.isHasInsertMeasure()) {
                Measurement m = nodo.buffer.take();
                if (m != null) {
                    ms.add(m);
                }
            }
            //nodo.setHasToken(false);
            nodo.sendTokenToNext(ms);
        }
    }

    @Override
    public void unsubscribeFrom(SubscribeRequest request, StreamObserver<SubscribeResponse> responseObserver) {
        try {
            int id_n = request.getId();
            String ip = request.getIp();
            int port = request.getPort();
            Nodo n = new Nodo(id_n, port, ip);

            nodo.removeNodeFromList(n);
            nodo.getNextNode();

            System.out.println("Nodo " + id_n + " rimosso dalla rete P2P.");

            if(nodo.getRing().size() > 1)
                System.out.println("Nodo successivo all'interno dell'anello: " + nodo.getNextInRing() + ".");
            else
                System.out.println("Sei l'unico nodo nell'anello. In attesa di altri nodi...");

            SubscribeResponse response = SubscribeResponse.newBuilder().setAck(true).setId(this.id).build();
            responseObserver.onNext(response);
            if(nodo.getRing().size() == 1){
                nodo.setHasFirst(true);
                nodo.setHasToken(true);
                nodo.setHasSent(false);
            }
            System.out.println();
            responseObserver.onCompleted();
        } catch(StatusRuntimeException ex){
            System.out.println("Errore nella connessione!");
        }
    }

    @Override
    public void insertMeasureToken(TokenSend request, StreamObserver<ACK> responseObserver) {
        nodo.setHasToken(true);
        //System.out.println(request.getMeasureCount());
        // >= nel caso in cui un nodo sia uscito prima che è stata inviata la statistica globale ed ha già inserito quella locale nel token
        if(request.getMeasureCount() >= nodo.getRing().size()) {
            double sum = 0.0;
            for (TokenSend.Measure m : request.getMeasureList()) {
                sum += m.getValue();
            }

            Client c = new Client();
            double global_st = sum / request.getMeasureCount();
            WebResource r = c.resource("http://localhost:" + nodo.getGateway_port() + "/statistics/add/");
            Gson gson = new Gson();
            String input = gson.toJson(new Statistica(new Date().getTime(), global_st));

            ClientResponse response2 = r.accept("application/json").type("application/json").post(ClientResponse.class, input);

            /*StringBuilder senders = new StringBuilder();
            for(TokenSend.Measure measure_ : request.getMeasureList()){
                senders.append(" ").append(measure_.getSender());
            }*/

            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("Ho inviato la statistica globale al gateway. Media finale = " + global_st);
            System.out.println("-----------------------------------------------------------------------------------------\n");

            nodo.setHasInsertMeasure(false);

            ArrayList<Nodo> copy = new ArrayList<>(nodo.getRing());
            for (Nodo n : copy) {
                int id_n = nodo.getId();
                if (n.getId() != id_n) {
                    sendMessageToNode(n.getIpAddress(), n.getPort(), n.getId()); // comunico che ho inviato la statistica globale al gateway
                }
            }

            ArrayList<Measurement> ms = new ArrayList<>();
            ACK response = ACK.newBuilder().setAck(true).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            nodo.sendTokenToNext(ms);
        }
        else {
            ArrayList<Measurement> ms = new ArrayList<>();
            for (TokenSend.Measure m : request.getMeasureList()) {
                Measurement msr = new Measurement(String.valueOf(m.getSender()), "pm10", m.getValue(), m.getTimestamp());
                ms.add(msr);
            }

            ACK response = ACK.newBuilder().setAck(true).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            if(!nodo.isHasInsertMeasure()) {
                Measurement m = nodo.buffer.take();
                if (m != null) {
                    ms.add(m);
                    nodo.setHasInsertMeasure(true);
                }
            }
            nodo.sendTokenToNext(ms);
        }
    }


    @Override
    public void sendConfirmGlobal(NodoId request, StreamObserver<ACK> responseObserver){
        nodo.setHasInsertMeasure(false);
        System.out.println("Il nodo " + request.getId() + " ha inviato la statistica globale al gateway!");
        ACK response = ACK.newBuilder().setAck(true).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void hasTokenRPC(NodoId request, StreamObserver<ACK> responseObserver){
        ACK response;
        if(nodo.getHasToken())
            response = ACK.newBuilder().setAck(true).build();
        else
            response = ACK.newBuilder().setAck(false).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    public void sendMessageToNode(String ip, int port, int id){
        ManagedChannel channel = ManagedChannelBuilder.forTarget(ip + ":" + port).usePlaintext(true).build();
        SubscribeServiceGrpc.SubscribeServiceBlockingStub stub = SubscribeServiceGrpc.newBlockingStub(channel);
        NodoId req = NodoId.newBuilder().setId(nodo.getId()).build();
        try {
            ACK response1 = stub.sendConfirmGlobal(req);
            channel.shutdown();
        } catch (StatusRuntimeException ex) {
            channel.shutdown();
            sendMessageToNode(ip, port, id);
        }
    }




}
