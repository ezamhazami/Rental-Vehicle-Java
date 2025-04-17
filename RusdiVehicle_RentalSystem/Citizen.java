class Citizen extends Customer
{
    private String citizenId;
    
    // Constructor
    public Citizen(String name,String noPhone,String email,String password,String citizenId){
        super(name,noPhone,email,password);
        this.citizenId=citizenId;
    }
    
    // Accessor
    public String getcitizenID(){
        return citizenId;
    }
    
    // Mutator
    public void setcitizenId(String citizenId){
        this.citizenId=citizenId;
    }
    
    // toString
    public String toString(){
        return "name : "+getName()+ "\nnumber phone: "+getNoPhone()+"\nnemail: "+getEmail()+ "\npassword: "+getPassword()+"\ncitizenId: "+citizenId;
    }
    
}