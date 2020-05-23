package com.hngymt.almes.pda.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hngymt.almes.pda.client.models.machine_tasks.StartTaskInput;
import com.hngymt.almes.pda.client.models.production_equipments.GetAllProductionEquipmentListInput;
import com.hngymt.almes.pda.client.models.production_groups.GetGroupInfoByMemberOutput;
import com.hngymt.almes.pda.client.models.production_equipments.ProductionEquipment;
import com.hngymt.almes.pda.client.models.machine_tasks.MachineTask;
import com.hngymt.almes.pda.client.utils.ServiceCallback;
import com.hngymt.almes.pda.client.utils.ServiceHttpClient;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity {
    Spinner spinner;
    Button button;
    EditText tvCardNo,tvProcessStep,tvWorkCenter,tvGroupName,tvGroupLeader;
    AlertDialog.Builder builder;
    Gson gson = new Gson();
    MachineTask machineTask;
    List<ProductionEquipment> equipments = new ArrayList<ProductionEquipment>();
    GetAllProductionEquipmentListInput input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setBtnBack();
        Bundle extras = this.getIntent().getExtras();
        spinner = findViewById(R.id.sp_sb);
        button = findViewById(R.id.bt_product);
        tvCardNo = findViewById(R.id.et_pch);
        tvProcessStep = findViewById(R.id.et_gx);
        tvWorkCenter = findViewById(R.id.et_gzzx);
        tvGroupName = findViewById(R.id.et_bz);
        tvGroupLeader = findViewById(R.id.et_bzz);
        builder = new AlertDialog.Builder(this);
        if(extras!=null && extras.getSerializable("task") != null) {
            machineTask = (MachineTask) extras.getSerializable("task");
            getProductInfo();
        }
        initEquipments();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("温馨提示");
                builder.setMessage("确定要继续吗");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                ServiceCallback callBack = new ServiceCallback() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        super.onSuccess(resultObj);
                        ProductActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductActivity.this,((ProductionEquipment)spinner.getSelectedItem()).getName() + "开工成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProductActivity.this,MachineTaskActivity.class));
                            }
                        });
                    }
                };
                ServiceHttpClient.getInstance().Post("/api/services/app/EquipmentTask/StartTask",new StartTaskInput(machineTask.getId(),machineTask.getCardNo(),((ProductionEquipment)spinner.getSelectedItem()).getId()), Object.class, ProductActivity.this, callBack);
                    }
                });
                builder.create().show();
            }
        });
    }

    public void initEquipments() {
        ServiceCallback callBack = new ServiceCallback() {
            @Override
            public void onSuccess(Object resultObj) {
                super.onSuccess(resultObj);
                equipments = gson.fromJson(gson.toJson(resultObj), new TypeToken<List<ProductionEquipment>>() {}.getType());
                ArrayAdapter<ProductionEquipment> adapter = new ArrayAdapter<ProductionEquipment>(ProductActivity.this, android.R.layout.simple_list_item_1, equipments);
                spinner.setAdapter(adapter);
                ProductActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductionEquipment equipment1 = new ProductionEquipment(machineTask.getEquipmentId(),machineTask.getEquipmentName());
                        int location = equipments.indexOf(equipment1);
                        spinner.setSelection(location);
                    }
                });
            }
        };
        ServiceHttpClient.getInstance().Get("/api/services/app/ProductionEquipment/GetAllList",input, ArrayList.class, ProductActivity.this, callBack);
    }

    public void getProductInfo() {
        if(machineTask != null) {
            tvCardNo.setText(machineTask.getCardNo());
            tvProcessStep.setText(machineTask.getFinalProcessItemName());
            tvWorkCenter.setText(machineTask.getWorkCenterName());
            input = new GetAllProductionEquipmentListInput(machineTask.getWorkCenterId());
            ServiceCallback callBack = new ServiceCallback() {
                @Override
                public void onSuccess(Object resultObj) {
                    super.onSuccess(resultObj);
                    final GetGroupInfoByMemberOutput output = (GetGroupInfoByMemberOutput) resultObj;
                    if(output == null) {
                        return;
                    }
                    ProductActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvGroupName.setText(output.getName());
                            tvGroupLeader.setText(output.getLeaderName());
                        }
                    });
                }
            };
            ServiceHttpClient.getInstance().Get("/api/services/app/ProductionGroup/GetGroupInfoByMember",null, GetGroupInfoByMemberOutput.class, ProductActivity.this, callBack);
        }
    }
}
