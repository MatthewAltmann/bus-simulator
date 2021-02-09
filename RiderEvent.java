//RiderEvent is the mechanism behind scheduling and rescheduling
//riders to arrive at different stops at semi-random intervals

import java.util.Random;
public class RiderEvent implements Event{//contains run method

    private int stop;
    private int interval;
    private Random r = new Random();
    private double[]arrivalPercents = {.75, .75, .5, .5, .5, .2, .2, .2, .2, 0, 0, -.2, -.2, -.2, -.2, -.5, -.5, -.5, -.75, -.75};
//^^^controls frequency of arrival distribution^^^
    public RiderEvent(int whichStop){
        this.stop = whichStop;
        this.interval = Simulator.intvl;
        if (this.stop == 0 || this.stop == 1 || this.stop == 14 || this.stop == 15 || this.stop == 16 || this.stop == 29){
           this.interval = Simulator.busyIntvl; //downtown stops get riders a lot more frequently
        }
    }//RiderEvent

    public void setInterval(int i){//interval mutator
        this.interval = i;
    }//setInterval

    public void run(){
        Rider rider = new Rider(Simulator.agenda.getCurrentTime(), this.stop);//creates new rider
        Simulator.stopArray[this.stop].queue.add(rider);//adds to bus stop queue
        int modifierIndex = r.nextInt(20);//picks random index for modifier
        double modifiedInterval = this.interval + (this.interval * arrivalPercents[modifierIndex]);//modifies interval
        Simulator.agenda.add(new RiderEvent(this.stop), modifiedInterval);//reschedules next passenger arrival with modifiedInterval
//        System.out.println("RiderEvent Stop: " + this.stop + ", Time is: " + Simulator.agenda.getCurrentTime() + ", Next passenger in " + modifiedInterval);
//        System.out.println("and he/she wants to go to: " + rider.destination);
    }//run
    
}//RiderEvent class
