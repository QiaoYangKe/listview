package com.hngymt.almes.pda.client.models.production_equipments;

import java.util.Objects;

public class ProductionEquipment {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public ProductionEquipment() {
    }

    public ProductionEquipment(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name == null ? "":name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionEquipment that = (ProductionEquipment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
