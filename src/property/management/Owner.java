package property.management;

import java.util.ArrayList;

public class Owner {
    private int ownerId;
    private String name;
    private String phone;
    private String email;


    public Owner(int ownerId,String name,String phone,String email){
        this.ownerId=ownerId;
        this.name=name;
        this.phone=phone;
        this.email=email;
    }
    public int getOwnerId(){
        return ownerId;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){return phone;}
    public String getEmail(){
        return email;
    }
    @Override
    public String toString(){
        return "Owner ID: "+ownerId+
                ", Name: "+name+
                ", Phone: "+phone+
                ", Email: "+email;
    }
}
