package com.hngymt.almes.pda.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hngymt.almes.pda.client.utils.ServiceHttpClient;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnSawing;
    Button btnMilling;
    Button btnMachineTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ServiceHttpClient.getInstance().hasToken()) {

        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        btnMachineTask = findViewById(R.id.btnMachineTask);
        btnSawing = findViewById(R.id.btnSawing);
        btnMilling = findViewById(R.id.btnMilling);
    }

    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btnMachineTask:
                startActivity(new Intent(getApplicationContext(), MachineTaskActivity.class));
                break;
            case R.id.btnMilling:
                startActivity(new Intent(getApplicationContext(), MillingActivity.class));
                break;
            case R.id.btnSawing:
                startActivity(new Intent(getApplicationContext(), SawingActivity.class));
                break;
            default:
                break;
        }
    }
}
