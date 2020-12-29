package gateway.beans;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Nodo implements Comparable{
    private int id;
    private int port;
    private String ipAddress;

    public Nodo(){}

    public Nodo(int id, int port, String ipAddress){
        this.id = id;
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString(){
        return id + " " + port + " " + ipAddress;
    }

    @Override
    public int compareTo(Object n) {
        int id = ((Nodo)n).getId();
        return this.id - id;
    }
}
