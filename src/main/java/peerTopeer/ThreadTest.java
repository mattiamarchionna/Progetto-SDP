package peerTopeer;

import org.codehaus.jettison.json.JSONException;

public class ThreadTest extends Thread {
    private String id;
    private String port;
    private String ip;

    public ThreadTest(String id, String port, String ip){
        this.id = id;
        this.port = port;
        this.ip = ip;
    }

    public void run(){
        try {
            MainNodo.main(new String[]{this.id, this.port, this.ip});
        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
