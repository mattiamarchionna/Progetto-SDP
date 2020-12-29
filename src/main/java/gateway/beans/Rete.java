package gateway.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rete {

    @XmlElement(name="nodo")
    private List<Nodo> nodeList;
    private static Rete instance;

    public Rete() {
        nodeList = new ArrayList<Nodo>();
    }

    //singleton
    public synchronized static Rete getInstance(){
        if(instance==null)
            instance = new Rete();
        return instance;
    }

    public synchronized List<Nodo> getNodesList() {
        return new ArrayList<Nodo>(nodeList);
    }

    public synchronized void add(Nodo n){  // gestire eccezione nel caso in cui è già presente un nodo con lo stesso id del Nodo n che vogliamo inserire
        nodeList.add(n);
    }

    public synchronized void remove(Nodo n){
        nodeList.remove(n);
    }

    public synchronized void update(int index, Nodo n){
        nodeList.set(index, n);
    }


    public Nodo getById(int id){
        List<Nodo> nodesCopy = getNodesList();
        for(Nodo n: nodesCopy)
            if(n.getId() == id) return n;
        return null;
    }

    public boolean existsId(int id){
        List<Nodo> nodesCopy = getNodesList();
        for(Nodo n: nodesCopy)
            if(n.getId() == id)
                return true;
        return false;
    }
}
