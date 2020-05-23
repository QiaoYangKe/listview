package com.hngymt.almes.pda.client.models.machine_tasks;

import java.io.Serializable;
import java.util.List;

public class GetCompletedRecordOutput implements Serializable {
    private String id;

    private String equipmentTadkId;

    private String incomingStuation;

    private String note;

    private String dc;

    private List<GetCompletedRecordItemOutput> items;

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentTadkId() {
        return equipmentTadkId;
    }

    public void setEquipmentTadkId(String equipmentTadkId) {
        this.equipmentTadkId = equipmentTadkId;
    }

    public String getIncomingStuation() {
        return incomingStuation;
    }

    public void setIncomingStuation(String incomingStuation) {
        this.incomingStuation = incomingStuation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<GetCompletedRecordItemOutput> getItems() {
        return items;
    }

    public void setItems(List<GetCompletedRecordItemOutput> items) {
        this.items = items;
    }
}
