package com.hfad.machineparts2;

import com.google.firebase.Timestamp;

public class PurchaseOrderPOJO {
    private String customerName;
   private String gaugeType;
    private String iterationID;
    private long measurementValue;
    private String partNumber;
    private String planID;
    //private String userID;
    private Timestamp timestamp;


    public com.google.firebase.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public PurchaseOrderPOJO() {
    }

    public PurchaseOrderPOJO(String customerName, String gaugeType, String iterationID, long measurementValue, String partNumber, String planID, /*String userID,**/ com.google.firebase.Timestamp timestamp) {
        this.customerName = customerName;
        this.gaugeType = gaugeType;
        this.iterationID = iterationID;
        this.measurementValue = measurementValue;
        this.partNumber = partNumber;
        this.planID = planID;
      //  this.userID = userID;
        this.timestamp=timestamp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGaugeType() {
        return gaugeType;
    }

    public void setGaugeType(String gaugeType) {
        this.gaugeType = gaugeType;
    }

    public String getIterationID() {
        return iterationID;
    }

    public void setIterationID(String iterationID) {
        this.iterationID = iterationID;
    }

    public long getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(long measurementValue) {
        this.measurementValue = measurementValue;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    /*public String getUserID() {
        return userID;
    }**/

    /*public void setUserID(String userID) {
        this.userID = userID;
    }**/
}
