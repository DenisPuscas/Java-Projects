package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.util.*;

public class Scheduler {
    private PriorityQueue<Server> servers;
    private int noServers;

    public Scheduler(int noServers, SimulationFrame frame){
        this.noServers = noServers;
        servers = new PriorityQueue<>();
        for (int i = 0; i < noServers; i++){
            Server s = new Server(i, frame);
            servers.add(s);
            Thread t = new Thread(s);
            t.start();
        }
    }
    public void dispatchTask(Task t){
        if (servers.peek() != null) {
//            System.out.println("C: " + servers.peek().getId() + " T: " + t.getID() + " " + t.getArrivalTime() + " " + t.getServiceTime() + " "+ servers.peek().getWaitingPeriod());
            Server aux = servers.poll();
            aux.addTask(t);
            servers.add(aux);
        }
    }

    public int peakHour(){
        int number = 0;
        for (Server s : servers){
            number += s.getTasks().size();
        }
        return number;
    }

    public String toString(){
        String s = "";
        String[] data = new String[noServers];
        for (Server server : servers){
            data[server.getId()] = server.toString();
        }
        for (int i = 0; i < noServers; i++) {
            s += "Queue " + i + ": " + ((data[i].equals(""))? "closed\n" : data[i]);
        }
        return s;
    }

    public boolean isEmpty(){
        for (Server s : servers){
            if (!s.getTasks().isEmpty())
                return false;
        }
        return true;
    }

}
