package com.hfad.machineparts2;

public class InspectionCriteriaPOJO {
   private String customerName;
   private String description;
   private String gaugeType;
   private String partNumber;
   private long maximum;
   private long minimal;
   private long nominal;

    public InspectionCriteriaPOJO(String customerName, String description, String gaugeType, String partNumber, long maximum, long minimal, long nominal, long planId, long userID) {
        this.customerName = customerName;
        this.description = description;
        this.gaugeType = gaugeType;
        this.partNumber = partNumber;
        this.maximum = maximum;
        this.minimal = minimal;
        this.nominal = nominal;
        this.planId = planId;
        this.userID = userID;
    }

    public long getMaximum() {
        return maximum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public long getMinimal() {
        return minimal;
    }

    public void setMinimal(long minimal) {
        this.minimal = minimal;
    }

    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    private long planId;
   private long userID;

    public InspectionCriteriaPOJO() {
    }



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGaugeType() {
        return gaugeType;
    }

    public void setGaugeType(String gaugeType) {
        this.gaugeType = gaugeType;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
