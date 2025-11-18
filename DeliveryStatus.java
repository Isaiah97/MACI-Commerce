package model;

public enum DeliveryStatus {
    NOT_SHIPPED("Package has not been shipped"),
    IN_TRANSIT("Package is in transit"),
    DELIVERED("Package has been delivered"),
    FAILED_TO_UPDATE("Tracking is unavailable");

    private final String message;

    DeliveryStatus (String msg){
        this.message = msg;
    }

    public String getMessage(){
        return message;
    }


}
