package property.management;

public class Dealer {
    private int dealerId;
    private String name;
    private String phone;
    private String email;

    public Dealer(int dealerId,String name,String phone,String email){
        this.dealerId=dealerId;
        this.name=name;
        this.phone=phone;
        this.email=email;
    }
    public int getDealerId(){
        return dealerId;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public String getEmail(){
        return email;
    }
    @Override
    public String toString() {
        return dealerId + " - " + name;
    }
}
