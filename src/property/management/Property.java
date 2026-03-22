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
    private long price;
    private PropertyType type;
    private PropertyPurpose purpose;
    private PropertyStatus status;
    private String propertyNumber;
    private int dealerId;
    private int ownerId;
    private String description;

    public Property(int propertyId,String propertyNumber,String location,long price,PropertyType type,PropertyPurpose purpose,int dealerId,int ownerId,String description){
        this.propertyId=propertyId;
        this.propertyNumber=propertyNumber;
        this.location=location;
        this.price=price;
        this.type=type;
        this.purpose=purpose;
        this.status=PropertyStatus.AVAILABLE;
        this.dealerId=dealerId;
        this.ownerId=ownerId;
        this.description=description;

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
    public long getPrice(){
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
    public int getDealerId(){return dealerId;}
    public int getOwnerId(){return ownerId;}
    public String getDescription(){return description;}


    @Override
    public String toString() {
        return
                "Property Id: " + propertyId +
                        ", Property No: " + propertyNumber +
                        ", Location: " + location +
                        ", Price: " + String.format("%,.0f", price) +
                        ", Type: " + type +
                        ", Purpose: " + purpose +
                        ", Status: " + status +
                        ", Dealer: " + dealerId +
                        ", Owner: "+ownerId+
                        ", Description: "+description;
    }

}

