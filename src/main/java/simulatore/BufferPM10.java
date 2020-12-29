package simulatore;

import peerTopeer.NodoP2P;

import java.util.ArrayList;
import java.util.Date;

public class BufferPM10 extends Thread implements Buffer {

    ArrayList<Measurement> buffer = new ArrayList<>();
    ArrayList<Measurement> lastStatistics = new ArrayList<Measurement>();
    NodoP2P nodo;

    public BufferPM10(NodoP2P n) {
        this.nodo = n;
    }

    @Override
    public synchronized void addMeasurement(Measurement m) {
        buffer.add(m);
        if (buffer.size() == 12) {
            notify();
        }
    }

    public synchronized Measurement take(){
        if(lastStatistics.size() > 0) {
            Measurement statistic = this.lastStatistics.get(0);
            this.lastStatistics.remove(0);
            return statistic;
        }
        else return null;
    }

    public synchronized void run() {
        while(true){
            double sum_value, media = 0;
            Date date;

            while (buffer.size() < 12) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (buffer.size() == 12) {
                sum_value = 0;
                ArrayList<Measurement> copyM = new ArrayList<>(buffer);
                for (Measurement m : copyM) {
                    sum_value += m.getValue();
                }

                media = sum_value / 12;
                date = new Date();

                this.lastStatistics.add(new Measurement(String.valueOf(nodo.getId()), "pm10", media, date.getTime()));
                notify();

                ArrayList<Measurement> temp = new ArrayList<>(buffer.subList(6, 12)); // sliding windows

                this.buffer.clear();
                this.buffer = new ArrayList<>(temp);
            }
        }
    }
}

