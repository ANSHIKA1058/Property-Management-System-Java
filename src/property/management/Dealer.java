package property.management;

public class Dealer {

    private int dealerId;
    private String name;
    private String phone;
    private String email;
    private String password;

    // Constructor without password
    public Dealer(int dealerId, String name, String phone, String email) {
        this.dealerId = dealerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Constructor with password
    public Dealer(int dealerId, String name, String phone, String email, String password) {
        this.dealerId = dealerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public int getDealerId() {
        return dealerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Dealer ID: " + dealerId +
                ", Name: " + name +
                ", Phone: " + phone +
                ", Email: " + email;
    }
}