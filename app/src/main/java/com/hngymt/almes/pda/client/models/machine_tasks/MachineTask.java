package com.hngymt.almes.pda.client.models.machine_tasks;

import java.io.Serializable;

public class MachineTask implements Serializable {
    private String id;

    private int state;

    private String finalProcessItemName;

    private String specifications;

    private String orderNos;

    private String cardNo;

    private String thirdPartyOrderNos;

    private String blankProductNo;

    private String alloyGradeName;

    private String alloyTemperName;

    private String parameter;

    private String claim;

    private String useName;

    private String workCenterName;

    private String workCenterId;

    private String equipmentId;

    private String processCompletedRecordId;

    private String equipmentName;

    private String productNames;

    private String netWeight;

    private String grossWeight;

    private String processNote;

    private String specialNote;

    private String packingNote;

    private String completedUserId;

    private String note;

    @Override
    public String toString() {
        return  "任务ID：" + id + '\n' +
                "状态：" + (state == 3 ? "生产":"派工") + '\n'+
                "工序名称：" + finalProcessItemName + '\n' +
                "卡片规格：" + specifications + '\n' +
                "合同编号：" + orderNos + '\n' +
                "卡片编号：" + cardNo + '\n' +
                "小订单号：" + thirdPartyOrderNos + '\n' +
                "卷号：" + blankProductNo + '\n' +
                "合金牌号：" + alloyGradeName + '\n' +
                "合金状态：" + alloyTemperName + '\n' +
                "加工参数：" + parameter + '\n' +
                "加工要求：" + claim + '\n' +
                "用途：" + useName + '\n' +
                "工作中心：" + workCenterName + '\n' +
                "设备名称：" + equipmentName + '\n' +
                "产品名称：" + productNames + '\n' +
                "净重：" + netWeight + '\n' +
                "毛重：" + grossWeight + '\n' +
                "加工要求：" + processNote + '\n' +
                "特殊要求：" + specialNote + '\n' +
                "包装要求：" + packingNote + '\n' +
                "备注：" + note + '\n';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFinalProcessItemName() {
        return finalProcessItemName;
    }

    public void setFinalProcessItemName(String finalProcessItemName) {
        this.finalProcessItemName = finalProcessItemName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getOrderNos() {
        return orderNos;
    }

    public void setOrderNos(String orderNos) {
        this.orderNos = orderNos;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getThirdPartyOrderNos() {
        return thirdPartyOrderNos;
    }

    public void setThirdPartyOrderNos(String thirdPartyOrderNos) {
        this.thirdPartyOrderNos = thirdPartyOrderNos;
    }

    public String getBlankProductNo() {
        return blankProductNo;
    }

    public void setBlankProductNo(String blankProductNo) {
        this.blankProductNo = blankProductNo;
    }

    public String getAlloyGradeName() {
        return alloyGradeName;
    }

    public void setAlloyGradeName(String alloyGradeName) {
        this.alloyGradeName = alloyGradeName;
    }

    public String getAlloyTemperName() {
        return alloyTemperName;
    }

    public void setAlloyTemperName(String alloyTemperName) {
        this.alloyTemperName = alloyTemperName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getWorkCenterName() {
        return workCenterName;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getProcessCompletedRecordId() {
        return processCompletedRecordId;
    }

    public void setProcessCompletedRecordId(String processCompletedRecordId) {
        this.processCompletedRecordId = processCompletedRecordId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getProductNames() {
        return productNames;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getProcessNote() {
        return processNote;
    }

    public void setProcessNote(String processNote) {
        this.processNote = processNote;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getPackingNote() {
        return packingNote;
    }

    public void setPackingNote(String packingNote) {
        this.packingNote = packingNote;
    }

    public String getCompletedUserId() {
        return completedUserId;
    }

    public void setCompletedUserId(String completedUserId) {
        this.completedUserId = completedUserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
