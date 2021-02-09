//Simulator is the main class which contains the driver for all classes.
//a Priority Queue is used to execute events in order until the time designated in the while loop
//an array of BusStops is used to emulate the 30 stops on the 3 route.

import java.util.Scanner;
public class Simulator {
    public static PQ agenda = new PQ();//agenda gets passed to other classes
    public static BusStop[] stopArray = new BusStop[30];
    public static Statistics stat = new Statistics();
    public static int statCount = 0;//counter for how many times stats get added
    public static int intvl;
    public static int busyIntvl;

    public static void main(String[] args) {
        for(int i = 0; i < 30; i++){//populates stopARray with BusStop objects
            stopArray[i] = new BusStop(i);//the BusStop takes in a stop number, which is incremented
        }
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the interval you would like riders to spawn at: ");
        intvl = Integer.parseInt(s.nextLine());
        busyIntvl = intvl / 2; //to be used later for downtown stops

        System.out.println("Enter the number of STANDARD buses you would like to run: ");
        int howManyS = Integer.parseInt(s.nextLine());
        Bus[] standardBusArray = new Bus[howManyS];//creates an array of standard buses to be used in the simulation
        int allStops = 30; //need to make sure to do integer division to find spacing between buses, so I declared it as an integer to be sure
        if(howManyS > 0) {
            int standardSpacing = allStops / howManyS;//finds spacing between standard buses
            for (int i = 0; i < howManyS; i++) {
                int busStart = (standardSpacing * i);//first bus will start at 0, next at standardSpacing, and so on
                standardBusArray[i] = new Bus(false);//new NON-EXPRESS bus
                agenda.add(new BusEvent(busStart, standardBusArray[i]), 0);//creates BusEvent object with this stop, bus, and at time = 0. it will reschedule itself when done running
                System.out.println("Added STANDARD bus at stop: " + busStart);
            }
        }

        System.out.println("Enter the number of EXPRESS buses you would like to run: ");
        int howManyE = Integer.parseInt(s.nextLine());
        Bus[] expressBusArray = new Bus[howManyE];//creates an array of express buses
        int[] expressStops = {0, 1, 4, 8, 12, 14, 15, 16, 20, 24, 28, 29};//every possible express stop
        if (howManyE > 0) {
            int expressSpacing = allStops / howManyE;
            for (int i = 0; i < howManyE; i++) {
                int busStart = (expressSpacing * i);//this number might not be a valid express stop. we need to test it
                boolean flag = false; //flag to test
                while (!flag){
                    for (int j = 0; j < expressStops.length; j++){//iterates through all express stop options
                        if (busStart == expressStops[j]){
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        busStart++;//increments busStop by 1 and tries again
                    }
                }
                expressBusArray[i] = new Bus(true);//new EXPRESS bus
                agenda.add(new BusEvent(busStart, expressBusArray[i]), 0);//adds BusEvent with valid busStart, new bus, and time = 0 - will reschedule itself
                System.out.println("Added EXPRESS bus at stop: " + busStart);
            }
        }

        for(int i = 0; i < 30; i++){
            stopArray[i].rE.setInterval(intvl);//sets the interval for RiderEvents
        }
        stopArray[0].rE.setInterval(busyIntvl); //downtown stops get riders twice as frequently
        stopArray[1].rE.setInterval(busyIntvl);
        stopArray[14].rE.setInterval(busyIntvl);
        stopArray[15].rE.setInterval(busyIntvl);
        stopArray[16].rE.setInterval(busyIntvl);
        stopArray[29].rE.setInterval(busyIntvl);

        for (int i = 0; i < 30; i++){
            agenda.add(stopArray[i].rE, 0);//adds all RiderEvents to agenda to start them running
        }

        while (agenda.getCurrentTime() <= 12 * 3600) {//simulation runs for 12 hours
            agenda.remove().run();
            if (agenda.getCurrentTime() % 10 == 0 ){//collects statistics every ten seconds
                for (int i = 0; i < standardBusArray.length; i++){
                    stat.updateSCapacity(standardBusArray[i]);
                }
                for (int i = 0; i < expressBusArray.length; i++){
                    stat.updateECapacity(expressBusArray[i]);
                }
                for (int i = 0; i < 30; i++){
                    stat.updateMaxQueueLength(stopArray[i].queue.length(), i);
                }
                statCount++;//increments statCounter
            }
        }

        stat.computeStats(statCount, (double) howManyS, (double) howManyE);//computes our stats with how many buses of each type
        stat.printStats();//prints stats to console

    }//main

}//Simulator class
