import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    // Data members
    private String tenantEmail;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private String rentedVehicle;

    // Constructor
    public Rental(String tenantEmail, LocalDate rentDate, LocalDate returnDate, String rentedVehicle) {
        this.tenantEmail = tenantEmail;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentedVehicle = rentedVehicle;
    }

    // Accessor methods
    public String getTenantEmail() {
        return tenantEmail;
    }
    
    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public String getRentedVehicle() {
        return rentedVehicle;
    }

    // Mutator methods
    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }
    
    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public void setRentedVehicle(String rentedVehicle) {
        this.rentedVehicle = rentedVehicle;
    }

    // Method to calculate total rental days
    public long calculateTotalDays() {
        return ChronoUnit.DAYS.between(rentDate, returnDate);
    }

    // toString method to display rental details
    public String toString() {
        return "\nRental Details:\n" + 
               "Rental Email: " + tenantEmail + "\n" +
               "Rent Date: " + rentDate + "\n" +
               "Return Date: " + returnDate + "\n" +
               "Rented Vehicle: " + rentedVehicle + "\n" +
               "Total Days: " + calculateTotalDays();
    }
}
