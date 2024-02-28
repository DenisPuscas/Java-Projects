package Model;

public class Task implements Comparable<Task>{
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getID(){
        return ID;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getServiceTime(){
        return serviceTime;
    }
    public void setServiceTime(int time){serviceTime = time;}
    @Override
    public int compareTo(Task t) {return this.arrivalTime - t.arrivalTime;}

    public String toString(){
        return "ID: " + ID + " AT: " + arrivalTime + " ST: " + serviceTime;
    }
}
