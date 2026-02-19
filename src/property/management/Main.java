package property.management;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PropertyManagementSystem system= new PropertyManagementSystem();
        Property p1 = new Property(1,"Flat-101","Sector-21",500000.0,PropertyType.FLAT,PropertyPurpose.SELL);
        system.addProperty(p1);
        system.showAllProperties();
        VisitRequest v1 = new VisitRequest(1,"Ambani",p1, LocalDate.now());
        system.scheduleVisit(v1);
        system.processNextVisit();
        system.showAllProperties();
    }
}