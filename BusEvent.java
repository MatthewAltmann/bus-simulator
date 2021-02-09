//BusEvent is the mechanism behind adding passengers from bus stop queues,
//removing passengers at their desired destination,
//and rescheduling the bus to the correct next stop\
import java.util.Random;
public class BusEvent implements Event {//contains run method
    public int stop;
    private Bus bus;
    Random r = new Random();

    public BusEvent(int stop, Bus b){//needs to pass in a bus to call functions on
        this.stop = stop;
        bus = b;
    }//BusEvent constructor

    public void run(){
        int deboardTime = 0;
        int boardTime = 0;
        deboardTime += 2 * (bus.actualLength(bus.removeAtStop(this.stop))); //two seconds for every passenger that deboards

        if (!bus.isExpress){//standard bus case
            System.out.println("Standard Bus Dumped " + (deboardTime / 2) + " riders at stop " + this.stop + " at time = " + Simulator.agenda.getCurrentTime());
            bus.removeAtStop(this.stop);
            while (!bus.isFull() && Simulator.stopArray[this.stop].queue.length() > 0){//stops when queue empty OR bus full
                Rider r = (Rider) Simulator.stopArray[this.stop].queue.remove(); //"pops" from queue and casts to Rider
                double waitTime = r.getWaitTime(Simulator.agenda.getCurrentTime());
                Simulator.stat.updateMaxWait(waitTime, this.stop);
                bus.add(r);
                boardTime += 3;//three seconds for every passenger that boards
            }
            System.out.println("Standard Bus Added " + (boardTime / 3) + " passengers at stop " + this.stop);
            if(this.stop == 29){
                this.stop = 0;//bus returns to first stop
            }
            else{
                stop++;//bus drives to next stop
            }
            Simulator.agenda.add(new BusEvent(this.stop, bus), deboardTime + boardTime + 240);//reschedules BusEvent with time spent at stop and travel time between stops
        }

        else{//express bus case
            System.out.println("Express Bus Dumped " + (deboardTime / 2) + " riders at stop " + this.stop + " at time = " + Simulator.agenda.getCurrentTime());
            bus.removeAtStop(this.stop);
            Q2 tempQueue = new Q2();
            while (Simulator.stopArray[this.stop].queue.length() > 0) {//empties bus stop queue into temporary queue
                Rider r = ((Rider) Simulator.stopArray[this.stop].queue.remove());
                tempQueue.add(r);
            }
            while (tempQueue.length() > 0){
                if(bus.isFull()) {//stops testing passengers and fills bus stop queue with temporary queue
                    System.out.println("Bus is full!");
                    while (tempQueue.length() > 0) {
                        Simulator.stopArray[this.stop].queue.add(tempQueue.remove());
                    }
                    break;
                }
                Rider r = ((Rider) tempQueue.remove());//pops first rider in tempQueue
                if (r.wantsExpress){//adds to bus if they want an express bus
                    bus.add(r);
                    boardTime += 3;//three seconds for each passenger added
                }
                else{
                    Simulator.stopArray[this.stop].queue.add(r);//if they aren't going to an express stop, adds them back to the line to wait for a standard bus
                }
            }
            System.out.println("Express Bus added " + boardTime / 3 + " riders at stop " + this.stop + " at time = " + Simulator.agenda.getCurrentTime());
            if (this.stop == 28 || this.stop == 0 || this.stop == 14 || this.stop == 15){//the express stops are not very kindly spaced.
                this.stop++;
            }
            else if (this.stop == 12){
                this.stop += 2;
            }
            else if (this.stop == 1){
                this.stop += 3;
            }
            else if (this.stop == 29){
                this.stop = 0;
            }
            else{
                this.stop += 4;
            }

            Simulator.agenda.add(new BusEvent(this.stop, bus), deboardTime + boardTime + 240);//reschedules BusEvent with time spent at stop + travel time
        }
    }//run

}//BusEvent class
