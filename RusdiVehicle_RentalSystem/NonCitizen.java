class NonCitizen extends Customer 
{
    private String passportNumber;

    // Constructor
    public NonCitizen(String name, String noPhone, String email, String password, String passportNumber) {
        super(name, noPhone, email, password);
        this.passportNumber = passportNumber;
    }

    // Accessor
    public String getPassportNumber() {
        return passportNumber;
    }
    
    // Mutator
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
    
    // toString
    public String toString(){
        return "name : "+getName()+ "\nnumber phone: "+getNoPhone()+"\nemail: "+getEmail()+ "\npassword: "+getPassword()+"\npassportNumber: "+passportNumber;
    }
}