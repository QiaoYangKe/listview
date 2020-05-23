package com.hngymt.almes.pda.client.models.machine_tasks;

import com.hngymt.almes.pda.client.models.BasePageQuery;

public class GetMachineTasksInput extends BasePageQuery {
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
