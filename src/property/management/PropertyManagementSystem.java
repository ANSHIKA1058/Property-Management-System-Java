package property.management;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class PropertyManagementSystem {

    private List<Property> properties;
    private List<Visit> visits = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    private List<Owner> owners = new ArrayList<>();
    private List<Dealer> dealers = new ArrayList<>();

    public PropertyManagementSystem() {

        properties = new ArrayList<>();


        loadOwnersFromDB();
        loadDealersFromDB();
        loadPropertiesFromDB();
        loadVisitsFromDB();
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
    public List<Visit> getVisits() {
        return visits;
    }
    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    //Add property
    public void addProperty(Property property) {
        if (findDealer(property.getDealerId()) == null) {
            System.out.println("Dealer not found");
            return;
        }

        if (findOwner(property.getOwnerId()) == null) {
            System.out.println("Owner not found");
            return;
        }
        properties.add(property);
        System.out.println("Property added successfully");
    }

    //Search property
    public Property searchProperty(String propertyNumber) {
        for (Property p : properties) {
            if (p.getPropertyNumber().equals(propertyNumber)) {
                return p;
            }
        }
        return null;
    }

    //update property status
    public void updateProperty(String propertyNumber, PropertyStatus status) {
        Property p = searchProperty(propertyNumber);
        if (p != null) {
            p.setStatus(status);
            System.out.println("Property updated");
        } else {
            System.out.println("Property not found");
        }
    }

    // delte propert
    public void deleteProperty(String propertyNumber) {
        Property p = searchProperty(propertyNumber);
        if (p == null) {
            System.out.println("Property not found");
            return;
        }

        System.out.println(p);

        System.out.println("Confirm delete (Y/N): ");
        String choice = sc.next();

        if (choice.equalsIgnoreCase("Y")) {
            properties.remove(p);
            System.out.println("Deleted");
        } else {
            System.out.println("Cancelled");
        }
    }

    //show avaliable properties
    public void showAvailable() {
        boolean found = false;

        for (Property p : properties) {

            if (p.getStatus() == PropertyStatus.AVAILABLE) {

                System.out.println(p);
                found = true;

            }
        }

        if (!found) {
            System.out.println("No available properties");
        }
    }

    public void searchByLocation(String location) {
        boolean found = false;
        for (Property p : properties) {
            if (p.getLocation().equalsIgnoreCase(location)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No property found at this location");
        }
    }

    //show all properties
    public void showAll() {
        if (properties.isEmpty()) {
            System.out.println("No properties available");
            return;
        }

        for (Property p : properties) {
            System.out.println(p);
        }
    }


    public void finalizeDeal(Property property) {
        if (property.getStatus() != PropertyStatus.AVAILABLE) {
            System.out.println("Deal cannot be finalized. Property not available.");
            return;
        }

        if (property.getPurpose() == PropertyPurpose.SELL) {
            property.setStatus(PropertyStatus.SOLD);
        } else {
            property.setStatus(PropertyStatus.RENTED);
        }

        System.out.println("Deal Finalized Successfully.");
    }

    /// serchByPriceRange()
    public void searchByPriceRange(double min, double max) {
        boolean found = false;
        for (Property p : properties) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No property found in this price range");
        }
    }


    // show dealer properties
    public void showDealerProperties(int dealerId) {
        boolean found = false;
        for (Property p : properties) {
            if (p.getDealerId() == dealerId) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Dealer has no properties");
        }
    }


    //owner method
    public void addOwner(Owner owner) {
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
    public Owner findOwner(int ownerId) {
        for (Owner o : owners) {
            if (o.getOwnerId() == ownerId) {
                return o;
            }
        }
        return null;
    }

    // dealer method
    public void addDealer(Dealer dealer) {
        dealers.add(dealer);
        System.out.println("Dealer added successfully");
    }


    //show dealers
    public void showDealers() {
        if (dealers.isEmpty()) {
            System.out.println("No dealers found");
            return;
        }
        for (Dealer d : dealers) {
            System.out.println(d);
        }
    }

    //Dealer search method
    public Dealer findDealer(int dealerId) {
        for (Dealer d : dealers) {
            if (d.getDealerId() == dealerId) {
                return d;
            }
        }
        return null;

    }

    public void addPropertyToDB(Property p) {

        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO Property " +
                    "(property_number, location, price, type, purpose, status, dealer_id, owner_id, description) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, p.getPropertyNumber());
            ps.setString(2, p.getLocation());
            ps.setLong(3, (long) p.getPrice());
            ps.setString(4, p.getType().toString());
            ps.setString(5, p.getPurpose().toString());
            ps.setString(6, p.getStatus().toString());
            ps.setInt(7, p.getDealerId());
            ps.setInt(8, p.getOwnerId());
            ps.setString(9, p.getDescription());

            ps.executeUpdate();

            System.out.println("Property saved to DATABASE ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addOwnerToDB(Owner o) {
        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO Owner(name, email, phone) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, o.getName());
            ps.setString(2, o.getEmail());
            ps.setString(3, o.getPhone());

            ps.executeUpdate();

            System.out.println("Owner saved to DB");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addDealerToDB(Dealer d) {
        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO Dealer(name, phone, email , password) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, d.getName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getEmail());
            ps.setString(4, d.getPassword());

            ps.executeUpdate();

            System.out.println("Dealer saved to DB");

        } catch (Exception e) {
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
                    "SELECT dealer_id, name, phone, email ,password FROM Dealer";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            dealers.clear();

            while (rs.next()) {

                Dealer dealer = new Dealer(
                        rs.getInt("dealer_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("password")
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


    // Load properties from database
    public void loadPropertiesFromDB() {

        try {

            Connection con = DatabaseConnection.getConnection();

            String query =
                    "SELECT property_id, property_number, location, price, " +
                            "type, purpose, status, dealer_id, owner_id, description " +
                            "FROM Property";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            properties.clear();

            while (rs.next()) {

                Property property = new Property(
                        rs.getInt("property_id"),
                        rs.getString("property_number"),
                        rs.getString("location"),
                        rs.getLong("price"),
                        PropertyType.valueOf(rs.getString("type")),
                        PropertyPurpose.valueOf(rs.getString("purpose")),
                        rs.getInt("dealer_id"),
                        rs.getInt("owner_id"),
                        rs.getString("description")
                );

                property.setStatus(
                        PropertyStatus.valueOf(
                                rs.getString("status")
                        )
                );

                properties.add(property);
            }

            System.out.println(
                    "Properties loaded from database: "
                            + properties.size()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePropertyFromDB(int propertyId) {

        try {

            Connection con = DatabaseConnection.getConnection();

            String query =
                    "DELETE FROM Property WHERE property_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, propertyId);

            ps.executeUpdate();

            System.out.println("Property deleted from DATABASE");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void updatePropertyStatusInDB(
            int propertyId,
            PropertyStatus status
    ) {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String query =
                    "UPDATE Property SET status = ? " +
                            "WHERE property_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    status.toString()
            );

            ps.setInt(
                    2,
                    propertyId
            );

            ps.executeUpdate();

            System.out.println(
                    "Property status updated in DATABASE"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    // =====================================================
// VISIT MANAGEMENT
// =====================================================
    public boolean addVisitToDB(Visit visit) {

        try {

            Connection con = DatabaseConnection.getConnection();

            String query =
                    "INSERT INTO Visit " +
                            "(property_id, client_name, client_phone, visit_date, status) " +
                            "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(
                            query,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            ps.setInt(1, visit.getPropertyId());
            ps.setString(2, visit.getClientName());
            ps.setString(3, visit.getClientPhone());
            ps.setString(4, visit.getVisitDate());
            ps.setString(5, visit.getStatus().toString());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                visit.setVisitId(rs.getInt(1));
            }

            System.out.println("Visit saved to DATABASE ✅");

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

// =====================================================
// LOAD VISITS FROM DATABASE
// =====================================================

    public void loadVisitsFromDB() {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String query =
                    "SELECT v.visit_id, " +
                            "v.property_id, " +
                            "p.property_number, " +
                            "p.location, " +
                            "v.client_name, " +
                            "v.client_phone, " +
                            "v.visit_date, " +
                            "v.status " +
                            "FROM Visit v " +
                            "JOIN Property p " +
                            "ON v.property_id = p.property_id";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            visits.clear();

            while (rs.next()) {

                Visit visit =
                        new Visit(
                                rs.getInt("visit_id"),
                                rs.getInt("property_id"),
                                rs.getString("property_number"),
                                rs.getString("location"),
                                rs.getString("client_name"),
                                rs.getString("client_phone"),
                                rs.getString("visit_date"),
                                VisitStatus.valueOf(
                                        rs.getString("status")
                                )
                        );

                visits.add(visit);
            }

            System.out.println(
                    "Visits loaded from database: "
                            + visits.size()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


// =====================================================
// UPDATE VISIT STATUS
// =====================================================

    public void updateVisitStatusInDB(
            int visitId,
            VisitStatus status
    ) {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String query =
                    "UPDATE Visit SET status = ? " +
                            "WHERE visit_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    status.toString()
            );

            ps.setInt(
                    2,
                    visitId
            );

            ps.executeUpdate();

            // Update memory also
            for (Visit visit : visits) {

                if (visit.getVisitId() == visitId) {

                    visit.setStatus(status);
                    break;
                }
            }

            System.out.println(
                    "Visit status updated in DATABASE"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


// =====================================================
// DELETE VISIT
// =====================================================

    public void deleteVisitFromDB(int visitId) {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            String query =
                    "DELETE FROM Visit WHERE visit_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(
                    1,
                    visitId
            );

            ps.executeUpdate();

            visits.removeIf(
                    visit ->
                            visit.getVisitId() == visitId
            );

            System.out.println(
                    "Visit deleted from DATABASE"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
