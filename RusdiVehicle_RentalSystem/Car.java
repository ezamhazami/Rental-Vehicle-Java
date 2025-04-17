public class Car extends Vehicle
{
    private String type; // Type of car: sedan, SUV, MPV
    
    // Constructor
    public Car(String brand, String name, int year, String availability, String type) {
        super(brand, name, year, availability);
        this.type = type;
    }
    
    // Accessor
    public String getType() {
        return type;
    }
    
    // Mutator
    public void setType(String type) {
        this.type = type;
    }
    
    // Method to determain car price per day based on it's type
    public double detPrice() {
        if(type.equalsIgnoreCase("sedan")) {
            return 100;
        }
        else if(type.equalsIgnoreCase("SUV")) {
            return 150;
        }
        else if(type.equalsIgnoreCase("MPV")) {
            return 200;
        }
        else {
            return 0;
        }
    }
    
    // toString 
    public String toString() {
        return super.toString() + "\nType: " + type 
                + "\nPrice per day: RM" + detPrice() + "0";
    }
}
