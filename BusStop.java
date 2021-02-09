//a Bus stop object contains a queue of riders,
//a RiderEvent to add passengers to it,
//a boolean value of whether it is serviced by express buses,
//and a name String associated with it.
public class BusStop{
    public String name;
    public int stopNum;
    public Q2 queue = new Q2();
    public boolean isExpress = false;
    public RiderEvent rE;

    public BusStop(int whichStop){
        this.stopNum = whichStop;
        rE = new RiderEvent(this.stopNum);
        if (this.stopNum == 0){
            this.name = "Ramp B";
            this.isExpress = true;
        }
        else if (this.stopNum == 1 || this.stopNum == 29){
            this.name = "4th & Nicollet";//mirrored stops will have the same name
            this.isExpress = true;
        }
        else if (this.stopNum == 2 || this.stopNum == 28){
            this.name = "Anderson Hall";
            if (whichStop == 28){//mirrored stops don't necessarily mirror isExpress values
                this.isExpress = true;
            }
        }
        else if (this.stopNum == 3 || this.stopNum == 27){
            this.name = "Jones Hall";
        }
        else if (this.stopNum == 4 || this.stopNum == 26){
            this.name = "Kasota Circle";
            if (this.stopNum == 4){
                this.isExpress = true;
            }
        }
        else if (this.stopNum == 5 || this.stopNum == 25){
            this.name = "Como & Eustis";
        }
        else if (this.stopNum == 6 || this.stopNum == 24){
            this.name = "Como & Cleveland";
            if (this.stopNum == 24){
                this.isExpress = true;
            }
        }
        else if (this.stopNum == 7 || this.stopNum == 23){
            this.name = "Como & Snelling";
        }
        else if (this.stopNum == 8 || this.stopNum == 22){
            this.name = "Como & Hamline";
            if (this.stopNum == 8){
                this.isExpress = true;
            }
        }
        else if (this.stopNum == 9 || this.stopNum == 21){
            this.name = "Maryland & Dale";
        }
        else if (this.stopNum == 10 || this.stopNum == 20){
            this.name = "Maryland & Rice";
            if (this.stopNum == 20){
                this.isExpress = true;
            }
        }
        else if (this.stopNum == 11 || this.stopNum == 19){
            this.name = "Front & Lexington";
        }
        else if (this.stopNum == 12 || this.stopNum == 18){
            this.name = "Front & Dale";
            if (this.stopNum == 12){
                this.isExpress = true;
            }
        }
        else if (this.stopNum == 13 || this.stopNum == 17){
            this.name = "Capitol";
        }
        else if (this.stopNum == 14 || this.stopNum == 16){
            this.name = "Cedar";
            this.isExpress = true;
        }
        else{
            this.name = "Union Depot";
            this.isExpress = true;
        }
    }//BusStop constructor

    public String getName(){
        return this.name;
    }//getName

}//BusStop class
