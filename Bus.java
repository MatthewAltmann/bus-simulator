//The Bus class creates an array of Riders,
//contains a boolean value designating whether it's an express bus,
//and performs various functions to mutate the Rider array.

public class Bus{
    public Rider[] bus = new Rider[50];//bus has 50 seats on it
    public boolean isExpress;

    public Bus(boolean isE){
        this.isExpress = isE;
    }//Bus constructor

    public void add(Rider r){//adds rider to bus array at first empty seat, unless bus array is full
        if (actualLength(bus) < 50) {
            for (int i = 0; i < 50; i++) {
                if (bus[i] == null) {
                    bus[i] = r;
                    break;
                }
            }
        }
        else{
            System.out.println("Bus is full!");
        }
    }//add

    public Rider[] removeAtStop(int stop){//at each stop, this function is called to disembark riders
        Rider[] departures = new Rider[50];
        for (int i = 0; i < 50; i++) {//iterates through every passenger
            if (bus[i] != null) {
                if (bus[i].destination == stop)//asks them if they would like to get off
                    departures[i] = bus[i];
                    bus[i] = null;
                }
            }
        }
        return departures;//it is important to return the array, so deboarding time can be calculated with .actualLength()
    }//removeAtStop

    public int actualLength(Object[] a){//regular .length() would always return 50
        int size = 0;
        for (int i = 0; i < a.length; i++){//increments size if the "seat" is occupied
            if (a[i] != null){
                size++;
            }
        }
        return size;
    }//actualLength

    public boolean isFull(){//used in adding passengers - breaks while loop when true
        if (actualLength(bus) < 50){
            return false;
        }
        else{
            return true;
        }
    }//isFill

    public Rider[] getBus(){//accessor for bus array
        return bus;
    }//getBus

}//Bus class
