package property.management;

import java.time.LocalDate;//earlier string now chatgpt told abt date

enum VisitStatus{
    SCHEDULED,
    COMPLETED,
    CANCELLED
}
//fifo
public class VisitRequest {
    private int visitId;
    private String buyerName;
    //isse m direct access kr skungi propertu ka association
    private Property property;
    private LocalDate visitDate;
    private VisitStatus status;

    public VisitRequest(int visitId,String buyerName,Property property,LocalDate visitDate){
        this.visitId=visitId;
        this.buyerName=buyerName;
        this.property=property;
        this.visitDate=visitDate;
        this.status=VisitStatus.SCHEDULED;
    }
    public void setStatus(VisitStatus status){
        this.status=status;
    }
    //ised to chnge property status queue m process krte wqt
    public Property getProperty(){
        return property;
    }

    @Override
    public String toString(){
        return "Visit ID:"+visitId+
                ", Buyer: "+buyerName+
                ", Property ID: "+property.getPropertyNumber()+
                ", Date: "+visitDate+
                ", Status: "+status;
    }

}
