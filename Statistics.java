//Statistics class creates an object that can have its methods called to produce three stats:
//Average bus capacity, queue lengths, and wait times

public class Statistics {
    private int sBusCapacityTotal;
    private int eBusCapacityTotal;
    private double avgSBusCapacity;
    private double avgEBusCapacity;
    private int[] maxQueueLengths;
    private double[] maxWaitTimes;
    private double averageMaxWaitTime;
    private int riderCount;
    private int averageWaitTime;

    public Statistics(){
        this.sBusCapacityTotal = 0;
        this.eBusCapacityTotal = 0;
        this.avgSBusCapacity = 0;
        this.avgEBusCapacity = 0;
        this.maxQueueLengths = new int[30];//arrays because each stop has a different max value
        this.maxWaitTimes = new double[30];
        for (int i = 0; i < 30; i++){
            this.maxQueueLengths[i] = 0;
            this.maxWaitTimes[i] = 0;
        }
        this.averageWaitTime = 0;
        this.riderCount = 0;
    }//Statistics constructor

    public void updateSCapacity(Bus b){
        this.sBusCapacityTotal += b.actualLength(b.getBus());
    }//updateSCapacity

    public void updateECapacity(Bus b){
        this.eBusCapacityTotal += b.actualLength(b.getBus());
    }//updateECapacity

    public void updateMaxQueueLength(int currentLength, int stopNum){
        if (currentLength > maxQueueLengths[stopNum]){
            maxQueueLengths[stopNum] = currentLength;
        }
    }//updateMaxQueueLength

    public void updateMaxWait(double currentWT, int stopNum){
        if (currentWT > maxWaitTimes[stopNum]){
            maxWaitTimes[stopNum] = currentWT;
        }
        this.averageWaitTime += currentWT;
        this.riderCount++;
    }//updateMaxWait

    public void computeStats(int timesCalled, double howManyStandard, double howManyExpress){
        if (howManyStandard != 0) {//catch divide by 0 error
             this.avgSBusCapacity = (this.sBusCapacityTotal / (double) timesCalled) / howManyStandard;
        }
        if (howManyExpress != 0) {//catch divide by 0 error
            this.avgEBusCapacity = (this.eBusCapacityTotal / (double) timesCalled) / howManyExpress;
        }
        double totalTime = 0;
        for (int i = 0; i < 30; i++){
            totalTime += maxWaitTimes[i];
        }
        this.averageMaxWaitTime = totalTime / 30;
        if(this.riderCount != 0){//catch divide by 0 error
          this.averageWaitTime = this.averageWaitTime / this.riderCount;
        }
    }//computeStats

    public void printStats(){
        System.out.println("\n\n***************Simulation Statistics***************");
        System.out.print("Average Standard Bus Capacity was: ");
        System.out.format("%.3f", this.avgSBusCapacity);
        System.out.println();
        System.out.print("Average Express Bus Capacity was: ");
        System.out.format("%.3f", this.avgEBusCapacity);
        System.out.println();
        for (int i = 0; i < Simulator.stopArray.length; i++){
            System.out.println("Max Queue Length at " + Simulator.stopArray[i].getName() + " was: " + this.maxQueueLengths[i]);
            System.out.println("Max Wait Time at " + Simulator.stopArray[i].getName() + " was: " + this.maxWaitTimes[i]);
        }
        System.out.println();
        System.out.println("Average Max Wait Time across all stops was: " + this.averageMaxWaitTime);
        System.out.println("Average Wait Time across all stops was: " + this.averageWaitTime);
    }//printStats
    
}//Statistics class
