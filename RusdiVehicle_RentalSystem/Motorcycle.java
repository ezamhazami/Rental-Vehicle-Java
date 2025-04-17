public class Motorcycle extends Vehicle
{
    private int cc; // Engine capacity in cc
    
    // Constructor
    public Motorcycle(String brand, String name, int year,String availability, int cc) {
        super(brand, name, year, availability);
        this.cc = cc;
    }
    
    // Accessor
    public int getCc() {
        return cc;
    }
    
    // Mutator
    public void setCc(int cc) {
        this.cc = cc;
    }
    
    // Method to determain motorcycle price per day based on it's cc
    public double detPrice() {
       if(cc>0 && cc<=150) {
            return 40;
        }
        else if(cc>150 && cc<=250) {
            return 80;
        }
        else {
            return 0;
        }
    }
    
    // toString
    public String toString() {
        return super.toString() + "\nCC Motorcycle: " + cc 
                + "\nPrice per day: RM" + detPrice() + "0";
    }
} 
