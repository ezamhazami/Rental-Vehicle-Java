public class Customer
{
    private String name;
    private String noPhone;
    private String email;
    private String password;
    
    //constructor
    public Customer(String name,String noPhone,String email,String password){
        this.name=name;
        this.noPhone=noPhone;
        this.email=email;
        this.password=password;
    }
    
    //setter
    public void setName(String name){
        this.name=name;
    }
    public void setnoPhone(String noPhone){
        this.noPhone=noPhone;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    
    //getter
    public String getName(){return name; }
    public String getNoPhone(){return noPhone;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    
    //tostring
    public String toString(){
        return "\nname: "+name+ "\nnumber phone: "+noPhone+"\nemail: "+email+ "\npassword: "+password;
    }
    
}