package com.hngymt.almes.pda.client.models.machine_tasks;

public class GetCompletedRecoredInput {
    private String id;

    public GetCompletedRecoredInput() {}

    public GetCompletedRecoredInput(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
