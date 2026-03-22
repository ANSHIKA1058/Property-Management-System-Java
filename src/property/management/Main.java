package property.management;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;
import java.sql.Connection;




//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Connection con = DatabaseConnection.getConnection();
        if(con!=null){
            System.out.println("Database Connected");
        }else{
            System.out.println("Connection Failed");
        }
        PropertyManagementSystem system= new PropertyManagementSystem();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=================Property Management System ========================");
            System.out.println("1 Add Property");
            System.out.println("2 Show All Properties");
            System.out.println("3 Search by Location");
            System.out.println("4 Search by Price");
            System.out.println("5 Show Available");
            System.out.println("6 Delete Property");
            System.out.println("7 Dealer Properties");
            System.out.println("8 Exit");
            System.out.println("9 Update Property");
            System.out.println("10 Add owner");
            System.out.println("11 Show owners");
            System.out.println("12 Add Dealer");
            System.out.println("13 Show Dealers");
            System.out.println("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Property ID: ");
                    int id = sc.nextInt();

                    System.out.println("Property Number: ");
                    String number = sc.next();

                    System.out.println("Location: ");
                    String location= sc.next();

                    System.out.println("Price: ");
                    long price = sc.nextLong();

                    System.out.println("Select Property Type:");
                    System.out.println("1 FLAT");
                    System.out.println("2 VILLA");
                    System.out.println("3 PLOT");
                    System.out.println("4 SHOP");
                    int typeChoice = sc.nextInt();
                    PropertyType type = null;
                    if(typeChoice==1){
                        type= PropertyType.FLAT;
                    }else if(typeChoice==2){
                        type = PropertyType.VILLA;
                    }else if(typeChoice==3){
                        type = PropertyType.PLOT;
                    }else if(typeChoice==4){
                        type= PropertyType.SHOP;
                    }


                    System.out.println("Purpose:");
                    System.out.println("1 SELL");
                    System.out.println("2 RENT");
                    int purposeChoice = sc.nextInt();
                    PropertyPurpose purpose = null;
                    if(purposeChoice==1){
                        purpose= PropertyPurpose.SELL;
                    }else if(purposeChoice==2){
                        purpose=PropertyPurpose.RENT;
                    }

                    System.out.println("Dealer ID: ");
                    int dealer = sc.nextInt();

                    System.out.println("Owner ID: ");
                    int owner = sc.nextInt();

                    sc.nextLine();
                    System.out.println("Description: ");
                    String desc =sc.nextLine();

                    Property p = new Property(
                            id,
                            number,
                            location,
                            price,
                            type,
                            purpose,
                            dealer,
                            owner,
                            desc
                    );
                    system.addProperty(p);
                    break;


                case 2:
                    system.showAll();
                    break;

                case 3:
                    System.out.println("Enter Location: ");
                    String loc = sc.next();
                    system.searchByLocation(loc);
                    break;

                case 4:
                    System.out.println("Min price: ");
                    long min = sc.nextLong();

                    System.out.print("Max price: ");
                    long max= sc.nextLong();

                    system.searchByPriceRange(min,max);
                    break;

                case 5:
                    system.showAvailable();
                    break;

                case 6:
                    System.out.print("Enter property number : ");
                    String del = sc.next();
                    system.deleteProperty(del);
                    break;

                case 7:
                    System.out.println("Enter dealer ID: ");
                    int d = sc.nextInt();
                    system.showDealerProperties(d);
                    break;

                case 8:
                    System.out.println("Exiting...");
                    return;


                case 9:
                    //update
                    System.out.println("Enter property number: ");
                    String num = sc.next();

                    System.out.println("1 AVAILABLE");
                    System.out.println("2 SOLD");
                    System.out.println("3 RENTED");

                    System.out.print("Choose status: ");
                    int statusChoice = sc.nextInt();

                    PropertyStatus status= null;
                    if(statusChoice==1){
                        status = PropertyStatus.AVAILABLE;
                    } else if (statusChoice==2) {
                        status = PropertyStatus.SOLD;
                    }else if(statusChoice==3){
                        status= PropertyStatus.RENTED;
                    }
                    system.updateProperty(num,status);
                    break;

                case 10:
                    System.out.println("Owner ID: ");
                    int oid = sc.nextInt();

                    System.out.println("Name: ");
                    String name = sc.next();

                    System.out.println("Phone: ");
                    String phone = sc.next();

                    System.out.println("Email: ");
                    String email = sc.next();

                    Owner ownerObj = new Owner(oid, name, phone ,email);
                    system.addOwner(ownerObj);
                    break;


                case 11:
                    system.showOwners();
                    break;

                case 12:
                    System.out.println("Dealer ID: ");
                    int did = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Name: ");
                    String dname= sc.nextLine();
                    System.out.println("Phone: ");
                    String dphone = sc.next();
                    System.out.println("Email: ");
                    String demail = sc.next();

                    Dealer dealerObj = new Dealer(did, dname, dphone ,demail);
                    system.addDealer(dealerObj);

                    break;

                case 13:
                    system.showDealers();
                    break;

                default:
                    System.out.println("Invalid choice");



            }
        }
    }
}