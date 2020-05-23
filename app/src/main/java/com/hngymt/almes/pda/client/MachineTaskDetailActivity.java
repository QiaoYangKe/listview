package com.hngymt.almes.pda.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hngymt.almes.pda.client.models.machine_tasks.GetMachineTasksOutput;
import com.hngymt.almes.pda.client.models.machine_tasks.MachineTask;
import com.hngymt.almes.pda.client.utils.ServiceCallback;
import com.hngymt.almes.pda.client.utils.ServiceHttpClient;

import java.util.HashMap;

public class MachineTaskDetailActivity extends BaseActivity {
    TextView tv_detail;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_task_detail);
        setBtnBack();
        tv_detail = findViewById(R.id.tv_detail);
        Bundle extras = this.getIntent().getExtras();
        if(extras!=null && extras.getSerializable("task") != null) {
            MachineTask task = (MachineTask) extras.getSerializable("task");
            tv_detail.setText(task.toString());
        }
    }
}
