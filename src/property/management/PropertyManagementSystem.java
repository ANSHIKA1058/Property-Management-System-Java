package property.management;

import java.util.*;
public class PropertyManagementSystem {
    private List<Property> properties;
    private Queue<VisitRequest> visitQueue;
    public PropertyManagementSystem(){
        properties = new ArrayList<>();
        visitQueue=new LinkedList<>();
    }
    public void addProperty(Property property){
        properties.add(property);
    }
    public void showAllProperties(){
        for(Property p: properties){
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


}
