package com.hngymt.almes.pda.client.models.machine_tasks;

import java.io.Serializable;
import java.util.Objects;

public class GetCompletedRecordItemOutput implements Serializable {
    private String id;

    private String completedRecordId;

    private String productNo;

    private int processCompletedType;

    private String thick;

    private String width;

    private String length;

    private double grossWeight;

    private double tareWeight;

    private double netWeight;

    private String pipeId;

    private String filmId;

    private double pipeWeight;

    private double filmWeight;

    private double pieceNumber;

    private String note;

    private String selfTest;

    private String machinedSurface;

    private String machinedVersion;

    private String def1;
    private String def2;
    private String def3;
    private String def4;
    private String def5;
    private String def6;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetCompletedRecordItemOutput that = (GetCompletedRecordItemOutput) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompletedRecordId() {
        return completedRecordId;
    }

    public void setCompletedRecordId(String completedRecordId) {
        this.completedRecordId = completedRecordId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getProcessCompletedType() {
        return processCompletedType;
    }

    public void setProcessCompletedType(int processCompletedType) {
        this.processCompletedType = processCompletedType;
    }

    public String getThick() {
        return thick;
    }

    public void setThick(String thick) {
        this.thick = thick;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(double tareWeight) {
        this.tareWeight = tareWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public String getPipeId() {
        return pipeId;
    }

    public void setPipeId(String pipeId) {
        this.pipeId = pipeId;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public double getPipeWeight() {
        return pipeWeight;
    }

    public void setPipeWeight(double pipeWeight) {
        this.pipeWeight = pipeWeight;
    }

    public double getFilmWeight() {
        return filmWeight;
    }

    public void setFilmWeight(double filmWeight) {
        this.filmWeight = filmWeight;
    }

    public double getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(double pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSelfTest() {
        return selfTest;
    }

    public void setSelfTest(String selfTest) {
        this.selfTest = selfTest;
    }

    public String getMachinedSurface() {
        return machinedSurface;
    }

    public void setMachinedSurface(String machinedSurface) {
        this.machinedSurface = machinedSurface;
    }

    public String getMachinedVersion() {
        return machinedVersion;
    }

    public void setMachinedVersion(String machinedVersion) {
        this.machinedVersion = machinedVersion;
    }

    public String getDef1() {
        return def1;
    }

    public void setDef1(String def1) {
        this.def1 = def1;
    }

    public String getDef2() {
        return def2;
    }

    public void setDef2(String def2) {
        this.def2 = def2;
    }

    public String getDef3() {
        return def3;
    }

    public void setDef3(String def3) {
        this.def3 = def3;
    }

    public String getDef4() {
        return def4;
    }

    public void setDef4(String def4) {
        this.def4 = def4;
    }

    public String getDef5() {
        return def5;
    }

    public void setDef5(String def5) {
        this.def5 = def5;
    }

    public String getDef6() {
        return def6;
    }

    public void setDef6(String def6) {
        this.def6 = def6;
    }

    @Override
    public String toString() {
        return "GetCompletedRecordItemOutput{" +
                "id='" + id + '\'' +
                ", completedRecordId='" + completedRecordId + '\'' +
                ", productNo='" + productNo + '\'' +
                ", processCompletedType=" + processCompletedType +
                ", thick='" + thick + '\'' +
                ", width='" + width + '\'' +
                ", length='" + length + '\'' +
                ", grossWeight=" + grossWeight +
                ", tareWeight=" + tareWeight +
                ", netWeight=" + netWeight +
                ", pipeId='" + pipeId + '\'' +
                ", filmId='" + filmId + '\'' +
                ", pipeWeight=" + pipeWeight +
                ", filmWeight=" + filmWeight +
                ", pieceNumber=" + pieceNumber +
                ", note='" + note + '\'' +
                ", selfTest='" + selfTest + '\'' +
                ", machinedSurface='" + machinedSurface + '\'' +
                ", machinedVersion='" + machinedVersion + '\'' +
                ", def1='" + def1 + '\'' +
                ", def2='" + def2 + '\'' +
                ", def3='" + def3 + '\'' +
                ", def4='" + def4 + '\'' +
                ", def5='" + def5 + '\'' +
                ", def6='" + def6 + '\'' +
                '}';
    }
}
