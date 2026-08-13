package property.management;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;;

public class PropertyManagementSystem {

    private List<Property> properties;
    private Queue<VisitRequest> visitQueue;
    private Scanner sc = new Scanner(System.in);

    private List<Owner> owners = new ArrayList<>();
    private List<Dealer> dealers = new ArrayList<>();

    public PropertyManagementSystem(){

        properties = new ArrayList<>();
        visitQueue = new LinkedList<>();

        loadOwnersFromDB();
        loadDealersFromDB();
    }

    public List<Dealer> getDealers() {
        return dealers;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public List<Property> getProperties() {
        return properties;
    }

    //Add property
    public void addProperty(Property property){
       if(findDealer(property.getDealerId())==null){
           System.out.println("Dealer not found");
           return;
       }

       if(findOwner(property.getOwnerId())==null){
           System.out.println("Owner not found");
           return;
       }
       properties.add(property);
        System.out.println("Property added successfully");
    }

    //Search property
    public Property searchProperty(String propertyNumber){
        for(Property p:properties){
            if(p.getPropertyNumber().equals(propertyNumber)){
                return p;
            }
        }
        return null;
    }

    //update property status
    public void updateProperty(String propertyNumber,PropertyStatus status){
        Property p = searchProperty(propertyNumber);
        if(p!=null){
            p.setStatus(status);
            System.out.println("Property updated");
        }else{
            System.out.println("Property not found");
        }
    }

    // delte propert
    public void deleteProperty(String propertyNumber){
        Property p = searchProperty(propertyNumber);
        if(p==null){
            System.out.println("Property not found");
            return;
        }

        System.out.println(p);

        System.out.println("Confirm delete (Y/N): ");
        String choice = sc.next();

        if(choice.equalsIgnoreCase("Y")){
            properties.remove(p);
            System.out.println("Deleted");
        }
        else{
            System.out.println("Cancelled");
        }
    }

    //show avaliable properties
    public void showAvailable() {
        boolean found=false;

        for(Property p:properties){

            if(p.getStatus()==PropertyStatus.AVAILABLE){

                System.out.println(p);
                found=true;

            }
        }

        if(!found){
            System.out.println("No available properties");
        }
    }
    public void searchByLocation(String location){
        boolean found= false;
        for(Property p:properties){
            if(p.getLocation().equalsIgnoreCase(location)){
                System.out.println(p);
                found=true;
            }
        }
        if(!found){
            System.out.println("No property found at this location");
        }
    }

    //show all properties
    public void showAll(){
        if(properties.isEmpty()){
            System.out.println("No properties available");
            return;
        }

        for(Property p : properties){
            System.out.println(p);
        }
    }
    public void scheduleVisit(VisitRequest visit){
        visitQueue.add(visit);
    }

    public void processNextVisit(){
        VisitRequest visit = visitQueue.poll();
        if(visit==null){
            System.out.println("No visits scheduled.");
            return;
        }
        visit.setStatus(VisitStatus.COMPLETED);
        Property property= visit.getProperty();
        System.out.println("Visit Completed");
        System.out.println(visit);
    }
    public void finalizeDeal(Property property){
        if(property.getStatus() != PropertyStatus.AVAILABLE){
            System.out.println("Deal cannot be finalized. Property not available.");
            return;
        }

        if(property.getPurpose()==PropertyPurpose.SELL){
            property.setStatus(PropertyStatus.SOLD);
        }else{
            property.setStatus(PropertyStatus.RENTED);
        }

        System.out.println("Deal Finalized Successfully.");
    }

    /// serchByPriceRange()
    public void searchByPriceRange(double min,double max){
        boolean found =false;
        for(Property p:properties){
            if(p.getPrice()>=min && p.getPrice()<=max){
                System.out.println(p);
                found=true;
            }
        }
        if(!found){
            System.out.println("No property found in this price range");
        }
    }


    // show dealer properties
    public void showDealerProperties(int dealerId){
        boolean found = false;
        for(Property p:properties){
            if(p.getDealerId()==dealerId){
                System.out.println(p);
                found=true;
            }
        }
        if(!found){
            System.out.println("Dealer has no properties");
        }
    }


    //owner method
    public void addOwner(Owner owner){
        owners.add(owner);
        System.out.println("Owner added successfully");
    }


    //show owners
    public void showOwners() {
        if (owners.isEmpty()) {
            System.out.println("No owners found");
            return;
        }
        for (Owner o : owners) {
            System.out.println(o);
        }
    }


    //owner search method
    public Owner findOwner(int ownerId){
        for(Owner o: owners){
            if(o.getOwnerId()==ownerId){
                return o;
            }
        }
        return null;
    }

        // dealer method
        public void addDealer(Dealer dealer){
           dealers.add(dealer);
            System.out.println("Dealer added successfully");
        }


        //show dealers
    public void showDealers(){
        if(dealers.isEmpty()){
            System.out.println("No dealers found");
            return;
        }
        for(Dealer d:dealers){
            System.out.println(d);
        }
    }

    //Dealer search method
    public Dealer findDealer(int dealerId){
        for(Dealer d:  dealers) {
            if (d.getDealerId() == dealerId) {
                return d;
            }
        }
     return null;

    }
    public void addPropertyToDB(Property p){

        try{
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO Property " +
                    "(property_number, location, price, type, purpose, status, dealer_id, owner_id, description) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, p.getPropertyNumber());
            ps.setString(2, p.getLocation());
            ps.setLong(3, (long)p.getPrice());
            ps.setString(4, p.getType().toString());
            ps.setString(5, p.getPurpose().toString());
            ps.setString(6, p.getStatus().toString());
            ps.setInt(7, p.getDealerId());
            ps.setInt(8, p.getOwnerId());
            ps.setString(9, p.getDescription());

            ps.executeUpdate();

            System.out.println("Property saved to DATABASE ✅");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void addOwnerToDB(Owner o){
        try{
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO Owner(name, email, phone) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, o.getName());
            ps.setString(2, o.getEmail());
            ps.setString(3, o.getPhone());

            ps.executeUpdate();

            System.out.println("Owner saved to DB");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void addDealerToDB(Dealer d){
        try{
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO Dealer(name, phone, email) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, d.getName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getEmail());

            ps.executeUpdate();

            System.out.println("Dealer saved to DB");

        }catch(Exception e){
            e.printStackTrace();
        }
    }




    // Load owners from database
    public void loadOwnersFromDB() {

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "SELECT owner_id, name, phone, email FROM Owner";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            owners.clear();

            while (rs.next()) {

                Owner owner = new Owner(
                        rs.getInt("owner_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );

                owners.add(owner);
            }

            System.out.println(
                    "Owners loaded from database: "
                            + owners.size()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Load dealers from database
    public void loadDealersFromDB() {

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "SELECT dealer_id, name, phone, email FROM Dealer";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            dealers.clear();

            while (rs.next()) {

                Dealer dealer = new Dealer(
                        rs.getInt("dealer_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );

                dealers.add(dealer);
            }

            System.out.println(
                    "Dealers loaded from database: "
                            + dealers.size()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
