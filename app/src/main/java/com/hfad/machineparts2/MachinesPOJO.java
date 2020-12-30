package com.hfad.machineparts2;

public class MachinesPOJO {
    private String machineID;



    private String currentPO;

    public MachinesPOJO(String machineID, String currentPO) {
        this.machineID = machineID;
        this.currentPO = currentPO;

    }

    public String getCurrentPO() {
        return currentPO;
    }

    public void setCurrentPO(String currentPO) {
        this.currentPO = currentPO;
    }

    public MachinesPOJO(String machineID) {
        this.machineID = machineID;
    }

    public MachinesPOJO() {
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }
}

