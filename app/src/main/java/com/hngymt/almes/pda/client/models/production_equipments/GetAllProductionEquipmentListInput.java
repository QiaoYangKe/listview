package com.hngymt.almes.pda.client.models.production_equipments;

public class GetAllProductionEquipmentListInput {
    private String workCenterId;

    public GetAllProductionEquipmentListInput() {}

    public GetAllProductionEquipmentListInput(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
    }
}
