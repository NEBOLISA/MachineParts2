package com.hfad.machineparts2;

import com.google.firebase.Timestamp;

public class NotesPOJO {


    private String note;
    private int machine;
   private Timestamp creation;
    private String customerName;
   private String partNumber;
    private int userID;

    public NotesPOJO(String note, int machine, Timestamp creation, String customerName, String partNumber,  int userID) {
        this.note = note;
        this.machine = machine;
        this.creation = creation;
        this.customerName = customerName;
        this.partNumber = partNumber;
        this.userID = userID;
    }

    public NotesPOJO() {
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public int getMachine() {
        return machine;
    }

    public void setMachine(int machine) {
        this.machine = machine;
    }
    public Timestamp getCreation() {
        return creation;
    }

    public void setCreation(Timestamp creation) {
        this.creation = creation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
