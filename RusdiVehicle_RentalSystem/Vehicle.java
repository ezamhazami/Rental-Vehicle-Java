public abstract class Vehicle
{
    // Data members
    protected String brand;
    protected String name;
    protected int year;
    protected String availability;
    
    // Constructor
    public Vehicle(String brand,String name,int year,String availability) {
        this.brand = brand;
        this.name = name;
        this.year = year;
        this.availability = availability;
    }
    
    // Accessor
    public String getBrand() {
        return brand;
    }
    
    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
    
    public String getAvailability() {
        return availability;
    }

    // Mutator
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setAvailability(String Availability) {
        this.availability = availability;
    }
    
    // toString
    public String toString() {
        return "\nVehicle Details:" + "\nBrand: " + brand + "\nName: " + name 
        + "\nYear: " + year + "\nAvailability: " + availability;
    }
}
