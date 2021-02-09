//Rider object are given an origin by RiderEvent (passed in through parameter)
//a destination is generated for them in the constructor - which decides whether they want express or not
//the rider tracks its spawn time for stat collecting purposes
import java.util.Random;
public class Rider{
    private int[] eastboundStops = {1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 14, 15, 15};//these arrays have duplicates
    private int[] westboundStops = {16, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 29, 0, 0};//because some stops are twice as popular
    private int origin;
    public int destination;
    double arrivalTime;
    boolean wantsExpress = false;
    private Random r = new Random();

    public Rider(double currentTime, int spawnedAt){
        this.arrivalTime = currentTime;
        this.origin = spawnedAt;

        if (this.origin < 15){//if the rider is headed east
         int destIndex = r.nextInt(eastboundStops.length);//random selection from destination possibilities
         while (eastboundStops[destIndex] <= this.origin){//the rider cannot go backwards or to the same stop they started at
             destIndex = r.nextInt(eastboundStops.length);//so we reroll destindex
         }
         this.destination = eastboundStops[destIndex];//sets rider destination to the array value chosen
        }
        else{//the rider is heading west
            int destIndex = r.nextInt(westboundStops.length);
            while (westboundStops[destIndex] <= this.origin && destIndex < 16){//0 is a possibility here so we must catch that edge case
                destIndex = r.nextInt(westboundStops.length);
            }
            this.destination = westboundStops[destIndex];
        }

        if (this.destination == 1 || this.destination == 14 || this.destination == 15 || this.destination == 29 || (this.destination % 4 == 0)){
            this.wantsExpress = true;//important when deciding whether a rider will get on an express bus
        }//if they dont want an express stop but get on an express bus, they will ride it forever
    }//Rider constructor

    public double getWaitTime(double currentTime) {
        return currentTime - this.arrivalTime;//calculates how long the rider waited at the stop (done when boarding rider)
    }//getWaitTime

    public boolean equals(Rider other){//created to search bus array for specific rider
        if (this.arrivalTime == other.arrivalTime){//no two riders can get on the bus at the same time
            return true;
        }
        else{
            return false;
        }//this method is not implemented in any other method
    }//equals
    
}//Rider class
