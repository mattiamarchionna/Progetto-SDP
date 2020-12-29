package gateway.beans;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Statistica implements Comparable{
    Long timestamp;
    Double value;

    public Statistica(){}

    public Statistica(Long time, Double value){
        this.timestamp = time;
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object s) {
        long timestamp = ((Statistica) s).getTimestamp();
        return this.timestamp.compareTo(timestamp);
    }
}
