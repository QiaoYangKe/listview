package com.hngymt.almes.pda.client.models.machine_tasks;

public class StartTaskInput {
    private String id;
    private String cardNo;
    private String equipmentId;

    public StartTaskInput(String id, String cardNo, String equipmentId) {
        this.id = id;
        this.cardNo = cardNo;
        this.equipmentId = equipmentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
}
