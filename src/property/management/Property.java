package property.management;

enum PropertyStatus{
    AVAILABLE,
    SOLD,
    RENTED
}
enum PropertyPurpose{
    SELL,
    RENT
}
enum PropertyType{
    FLAT,
    VILLA,
    PLOT,
    SHOP
}

public class Property {
    private final int propertyId;
    private String location;
    private double price;
    private PropertyType type;
    private PropertyPurpose purpose;
    private PropertyStatus status;
    private String propertyNumber;

    public Property(int propertyId,String propertyNumber,String location,double price,PropertyType type,PropertyPurpose purpose){
        this.propertyId=propertyId;
        this.propertyNumber=propertyNumber;
        this.location=location;
        this.price=price;
        this.type=type;
        this.purpose=purpose;
        this.status=PropertyStatus.AVAILABLE;
    }

    //encapsulation
    public int getPropertyId(){
        return propertyId;
    }
    public String getPropertyNumber(){
        return propertyNumber;
    }
    public String getLocation(){
        return location;
    }
    public double getPrice(){
        return price;
    }
    public PropertyType getType(){
        return type;
    }
    public PropertyPurpose getPurpose(){
        return purpose;
    }
    public PropertyStatus getStatus(){
        return status;
    }
    public void setStatus(PropertyStatus status){
        this.status=status;
    }

    @Override
    public String toString(){
        return "ID: "+propertyId+
                ", Property No: "+propertyNumber+
                ", Location: "+location+
                ", Price: "+price+
                ", Type: "+type+
                ", Purpose: "+purpose+
                ", Status: "+status;
    }

}

