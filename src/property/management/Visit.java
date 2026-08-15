package property.management;

public class Visit {

    private int visitId;
    private int propertyId;
    private String propertyNumber;
    private String location;
    private String clientName;
    private String clientPhone;
    private String visitDate;
    private VisitStatus status;

    public Visit(
            int visitId,
            int propertyId,
            String propertyNumber,
            String location,
            String clientName,
            String clientPhone,
            String visitDate,
            VisitStatus status
    ) {
        this.visitId = visitId;
        this.propertyId = propertyId;
        this.propertyNumber = propertyNumber;
        this.location = location;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.visitDate = visitDate;
        this.status = status;
    }

    public int getVisitId() {
        return visitId;
    }
    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getPropertyNumber() {
        return propertyNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }
}