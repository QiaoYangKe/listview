package com.hngymt.almes.pda.client.models.machine_tasks;

import java.util.List;

public class GetMachineTasksOutput {
    private int totalCount;
    private List<MachineTask> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<MachineTask> getItems() {
        return items;
    }

    public void setItems(List<MachineTask> items) {
        this.items = items;
    }
}
