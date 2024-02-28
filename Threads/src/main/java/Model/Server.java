package Model;

import GUI.SimulationFrame;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable, Comparable<Server>{
    private int id;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private SimulationFrame frame;
    private static boolean active = true;
    private static AtomicInteger averageWaitingTime;
    private static int averageServiceTime = 0;
    private static int servedClients = 0;

    public Server(int id, SimulationFrame frame){
        this.id = id;
        this.frame = frame;
        tasks = new ArrayBlockingQueue<Task>(50);
        waitingPeriod = new AtomicInteger(0);
        averageWaitingTime = new AtomicInteger(0);
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        averageWaitingTime.addAndGet(waitingPeriod.get());
        waitingPeriod.addAndGet(newTask.getServiceTime());
        frame.setServiceTime(id, waitingPeriod.get());
        frame.addToQueue(id, newTask);
    }

    public void run(){
        while (active){
            if (!tasks.isEmpty()){
                int currentPeriod = tasks.peek().getServiceTime();
                int serviceTime = tasks.peek().getServiceTime();
                while (currentPeriod-- > 0) {
                    try {
                        frame.setServiceTime(id, waitingPeriod.get());
                        Thread.sleep(1000);
                        tasks.peek().setServiceTime(tasks.peek().getServiceTime() - 1);
                        waitingPeriod.decrementAndGet();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                averageServiceTime += serviceTime;
                servedClients++;
                tasks.poll();
                frame.setServiceTime(id, waitingPeriod.get());
                frame.removeFromQueue(id);
            }
        }
    }

    public String toString() {
        if (tasks.isEmpty()){
            return "";
        }
        String s = "";
        for (Task t : tasks) {
            s += "(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + "); ";
        }
        s += "\n";
        return s;
    }

    @Override
    public int compareTo(Server s) {
        return this.waitingPeriod.get() - s.waitingPeriod.get();
    }
    public static void disable(){
        active = false;
    }
    public static int getAverageWaitingTime(){
        return averageWaitingTime.get();
    }
    public int getId() {
        return id;
    }
    public BlockingQueue<Task> getTasks(){
        return tasks;
    }
    public int getWaitingPeriod(){
        return waitingPeriod.get();
    }


    public static float getAverageServiceTime() {
        return (float)averageServiceTime;
    }

    public void setAverageServiceTime(int averageServiceTime) {
        this.averageServiceTime = averageServiceTime;
    }

    public static int getServedClients() {
        return servedClients;
    }

    public void setServedClients(int servedClients) {
        this.servedClients = servedClients;
    }
}
