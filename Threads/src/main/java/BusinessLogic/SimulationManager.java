package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable{
    private int timeLimit;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minProcessingTime;
    private int maxProcessingTime;
    private int numberOfServers;
    private int numberOfClients;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private Queue<Task> generatedTasks;
    private int currentTime = 0;
    private AtomicInteger peakHour;

    public SimulationManager(){
        generatedTasks = new PriorityQueue<>();
        frame = new SimulationFrame();
        peakHour = new AtomicInteger(-1);

        frame.addNextListener(new NextListener());
    }

    private void generateNRandomTasks(){
        Random rand = new Random();
        for (int i = 0; i < numberOfClients; i++){
            generatedTasks.add(new Task(i + 1,
                    rand.nextInt(minArrivalTime, maxArrivalTime),
                    rand.nextInt(minProcessingTime, maxProcessingTime)));
        }
//        for (Task t : generatedTasks){
//            System.out.println(t.toString());
//        }
    }

    @Override
    public void run() {
        try {
            FileWriter writer= new FileWriter("log.txt");
            int nrClients = -1;
            while (currentTime < timeLimit && (!generatedTasks.isEmpty() || !scheduler.isEmpty())) {
                while (generatedTasks.peek() != null && generatedTasks.peek().getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(generatedTasks.poll());
                }
                int aux = scheduler.peakHour();
                if (aux > nrClients){
                    nrClients = aux;
                    peakHour.set(currentTime);
                }
                writer.write("Time " + currentTime + "\nWaiting clients: " + tasksToString() + scheduler.toString() + "\n");
                frame.setTime(currentTime);
                currentTime++;
                Thread.sleep(1000);
            }
            Server.disable();
            frame.setTime(currentTime);
            writer.write("Average waiting time: " + (float)Server.getAverageWaitingTime() / numberOfClients +
                    "\nAverage service time: " + Server.getAverageServiceTime() / Server.getServedClients() +
                    "\nPeak hour: " + peakHour.get());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String tasksToString(){
        String s = "";
        for (Task t : generatedTasks){
            s += "(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + "); ";
        }
        s += "\n";
        return s;
    }

    public static void main(String[] args){
        SimulationManager main = new SimulationManager();
    }

    class NextListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                numberOfClients   = Integer.parseInt(frame.getClientsInput());
                numberOfServers   = Integer.parseInt(frame.getQueueInput());
                timeLimit         = Integer.parseInt(frame.getTimeInput());
                minArrivalTime    = Integer.parseInt(frame.getMinArrivalInput());
                maxArrivalTime    = Integer.parseInt(frame.getMaxArrivalInput());
                minProcessingTime = Integer.parseInt(frame.getMinServiceInput());
                maxProcessingTime = Integer.parseInt(frame.getMaxServiceInput());

                if (numberOfClients < 1 || numberOfServers < 1 || timeLimit < 1 || minArrivalTime < 0 || maxArrivalTime < 0 ||
                        minProcessingTime < 0 || maxProcessingTime < 0 || minArrivalTime == maxArrivalTime || minProcessingTime == maxProcessingTime)
                    throw new Exception();

                int aux;
                if (maxArrivalTime < minArrivalTime){
                    aux = minArrivalTime;
                    minArrivalTime = maxArrivalTime;
                    maxArrivalTime = aux;
                }
                if (maxProcessingTime < minProcessingTime){
                    aux = minProcessingTime;
                    minProcessingTime = maxProcessingTime;
                    maxProcessingTime = aux;
                }
                frame.createSimulationFrame(numberOfServers);
                frame.switchContent();
                generateNRandomTasks();
                scheduler = new Scheduler(numberOfServers, frame);
                start();

            } catch (Exception err) {
                frame.error();
            }
        }
    }

    public void start(){
        Thread t = new Thread(this);
        t.start();
    }


}
