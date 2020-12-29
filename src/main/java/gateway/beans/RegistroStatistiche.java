package gateway.beans;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RegistroStatistiche {

    @XmlElement(name="statistica")
    private List<Statistica> statisticList;
    private static RegistroStatistiche instance;

    public RegistroStatistiche() {
        statisticList = new ArrayList<Statistica>();
    }

    //singleton
    public synchronized static RegistroStatistiche getInstance(){
        if(instance==null)
            instance = new RegistroStatistiche();
        return instance;
    }

    public synchronized List<Statistica> getStatisticList() {
        return new ArrayList<Statistica>(statisticList);
    }

    public synchronized void add(Statistica s){  // gestire eccezione nel caso in cui è già presente un nodo con lo stesso id del Nodo n che vogliamo inserire
        statisticList.add(s);
    }

    public synchronized void remove(Statistica s){
        statisticList.remove(s);
    }

    public List<Statistica> getNstatistic(int n){
        List<Statistica> copy = getStatisticList();
        if(n > copy.size()) n = copy.size();
        return new ArrayList<Statistica>(copy.subList(copy.size() - n, copy.size()));
    }

    public double dev_standard(int n){
        List<Statistica> copy = getStatisticList();
        if(n > copy.size()) n = copy.size();
        List<Statistica> statsCopy = copy.subList(copy.size() - n, copy.size());
        double sum = 0.0;
        for(Statistica s: statsCopy){
            sum += s.getValue();
        }
        double media = sum/n;
        sum = 0.0;
        for(Statistica s : statsCopy){
            sum += Math.pow((s.getValue() - media), 2);
        }
        return (sum / (n-1));
    }

    public double media(int n){
        List<Statistica> copy = getStatisticList();
        if(n > copy.size()) n = copy.size();
        List<Statistica> statsCopy = copy.subList(copy.size() - n, copy.size());
        double sum = 0.0;
        for(Statistica s: statsCopy){
            sum += s.getValue();
        }
        return (sum/n);
    }
}
