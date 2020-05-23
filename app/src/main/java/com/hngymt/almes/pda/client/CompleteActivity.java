package com.hngymt.almes.pda.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hngymt.almes.pda.client.adapter.ListViewAdepter;
import com.hngymt.almes.pda.client.models.machine_tasks.GetCompletedRecordItemOutput;
import com.hngymt.almes.pda.client.models.machine_tasks.GetCompletedRecordOutput;
import com.hngymt.almes.pda.client.models.machine_tasks.GetCompletedRecoredInput;
import com.hngymt.almes.pda.client.models.machine_tasks.MachineTask;
import com.hngymt.almes.pda.client.models.machine_tasks.StartTaskInput;
import com.hngymt.almes.pda.client.models.production_equipments.ProductionEquipment;
import com.hngymt.almes.pda.client.models.production_groups.GetGroupInfoByMemberOutput;
import com.hngymt.almes.pda.client.utils.ServiceCallback;
import com.hngymt.almes.pda.client.utils.ServiceHttpClient;
import com.hngymt.almes.pda.client.widgets.LoadMoreListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CompleteActivity extends BaseActivity {
    List<HashMap<String, Object>> data;
    LoadMoreListView listView;
    ListViewAdepter simpleAdapter;
    Gson gson;
    MachineTask machineTask;
    AlertDialog.Builder builder;
    GetCompletedRecoredInput input = new GetCompletedRecoredInput();
    GetCompletedRecordOutput output;
    EditText etProcessStep,etWorkCenter,etEquipment,etAlloyGrade,etAlloyTempter,etCardNo,etProductNo,etDc,
            etOrderNo,etGross,etNet,etWorkGroup,etWorkGroupLeader,etParamter,etClaim,etIncomingStution,etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        Bundle extras = this.getIntent().getExtras();
        setBtnBack();
        initView();
        builder = new AlertDialog.Builder(this);
        listView.setAdapter(simpleAdapter);
        if(extras!=null && extras.getSerializable("task") != null) {
            machineTask = (MachineTask) extras.getSerializable("task");
            loadData();
        }
        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = data.get(position);
                GetCompletedRecordItemOutput item = (GetCompletedRecordItemOutput) map.get("text3");
                startActivityForResult(new Intent(CompleteActivity.this,CompleteUpdateFormActivity.class).putExtra("output", item).putExtra("position",position),1);

            }
        });
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "增加");
                menu.add(0, 1, 1, "删除");
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int position = menuInfo.position;
        HashMap<String, Object> map = (HashMap<String, Object>) simpleAdapter.getItem(position);
        GetCompletedRecordItemOutput task = (GetCompletedRecordItemOutput) map.get("text3");
        switch (item.getItemId()) {
            case 0:
                add();
                break;
            case 1:
                remove(map);
                //Toast.makeText(getApplicationContext(),"删除",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        MenuItem saveItem = menu.findItem(R.id.save_complete);
        MenuItem enterItem = menu.findItem(R.id.return_complete);
        output.setIncomingStuation(etDc.getText().toString());
        output.setNote(etNote.getText().toString());
        output.setDc(etDc.getText().toString());
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                complete("/api/services/app/EquipmentTask/UpdateCompletedRecord");
                return true;
            }
        });
        enterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                complete("/api/services/app/EquipmentTask/CompletedTask");
                return true;
            }
        });
        return true;
    }
    public void complete(final String url) {
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
                        CompleteActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"操作成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CompleteActivity.this,MachineTaskActivity.class));
                            }
                        });
                    }
                };
                ServiceHttpClient.getInstance().Post(url,output, Object.class, CompleteActivity.this, callBack);
              }
        });
        builder.create().show();
    }
    public void initView() {
        listView = findViewById(R.id.complete_products);
        data = new ArrayList<>();
        simpleAdapter = new ListViewAdepter(
                getApplicationContext(),
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"text1", "text2", "text3"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        this.registerForContextMenu(listView);
        gson = new Gson();
    }

    public void loadData() {
        etProcessStep = findViewById(R.id.et_c_gx);
        etProductNo = findViewById(R.id.et_c_jh);
        etOrderNo = findViewById(R.id.et_c_htbh);
        etWorkCenter = findViewById(R.id.et_c_gzzx);
        etAlloyGrade = findViewById(R.id.et_c_hjph);
        etAlloyTempter = findViewById(R.id.et_c_hjzt);
        etCardNo = findViewById(R.id.et_c_ph);
        etGross = findViewById(R.id.et_c_llmz);
        etNet = findViewById(R.id.et_c_lljz);
        etWorkGroup = findViewById(R.id.et_c_bz);
        etWorkGroupLeader = findViewById(R.id.et_c_bzz);
        etParamter = findViewById(R.id.et_c_gxcs);
        etClaim = findViewById(R.id.et_c_jgyq);
        etEquipment = findViewById(R.id.et_c_sb);
        etIncomingStution = findViewById(R.id.et_c_llqk);
        etNote = findViewById(R.id.et_c_beiz);
        etDc = findViewById(R.id.et_c_dc);

        etProcessStep.setText(machineTask.getFinalProcessItemName());
        etWorkCenter.setText(machineTask.getWorkCenterName());
        etAlloyGrade.setText(machineTask.getAlloyGradeName());
        etAlloyTempter.setText(machineTask.getAlloyTemperName());
        etCardNo.setText(machineTask.getCardNo());
        etGross.setText(machineTask.getGrossWeight());
        etNet.setText(machineTask.getNetWeight());
        etOrderNo.setText(machineTask.getOrderNos());
        etParamter.setText(machineTask.getParameter());
        etClaim.setText(machineTask.getClaim());
        etEquipment.setText(machineTask.getEquipmentName());
        etProductNo.setText(machineTask.getBlankProductNo());
        ServiceCallback groupCallBack = new ServiceCallback() {
            @Override
            public void onSuccess(Object resultObj) {
                super.onSuccess(resultObj);
                final GetGroupInfoByMemberOutput output = (GetGroupInfoByMemberOutput) resultObj;
                if(output == null) {
                    return;
                }
                CompleteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etWorkGroup.setText(output.getName());
                        etWorkGroupLeader.setText(output.getLeaderName());
                    }
                });
            }
        };
        ServiceHttpClient.getInstance().Get("/api/services/app/ProductionGroup/GetGroupInfoByMember",null, GetGroupInfoByMemberOutput.class, CompleteActivity.this, groupCallBack);

        if(machineTask.getProcessCompletedRecordId() != null && !"".equals(machineTask.getProcessCompletedRecordId())) {
            input.setId(machineTask.getProcessCompletedRecordId());
            ServiceCallback callBack = new ServiceCallback() {
                @Override
                public void onSuccess(Object resultObj) {
                    super.onSuccess(resultObj);
                    output = (GetCompletedRecordOutput) resultObj;
                    if(output == null) {
                        return;
                    }
                    for(GetCompletedRecordItemOutput item : output.getItems()) {
                        drawableList(item);
                    }
                    CompleteActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            simpleAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
            ServiceHttpClient.getInstance().Get("/api/services/app/EquipmentTask/GetCompletedRecord",input, GetCompletedRecordOutput.class, CompleteActivity.this, callBack);
        } else {
            output = new GetCompletedRecordOutput();
            output.setEquipmentTadkId(machineTask.getId());
            output.setItems(new ArrayList<GetCompletedRecordItemOutput>());
            GetCompletedRecordItemOutput item = new GetCompletedRecordItemOutput();
            item.setProductNo(machineTask.getCardNo() + "-1");
            output.getItems().add(item);
            drawableList(item);
            CompleteActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    simpleAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void add() {
        GetCompletedRecordItemOutput item = new GetCompletedRecordItemOutput();
        item.setProductNo(machineTask.getCardNo() + "-" +(data.size()+1));
        item.setCompletedRecordId(machineTask.getProcessCompletedRecordId());
        output.getItems().add(item);
        drawableList(item);
        simpleAdapter.notifyDataSetChanged();
    }

    public void drawableList(GetCompletedRecordItemOutput item) {
        HashMap<String, Object> map = new HashMap<>();
        String text1 = item.getProductNo() + "\t " + (item.getProcessCompletedType() ==1? "片材":"卷材") + "\t " + item.getThick() + "*" + item.getWidth()+ "*" + item.getLength() + "\t 净重："+ item.getNetWeight();
        String text2 = item.getNote() == null? "" : item.getNote();
        map.put("text1", text1);
        map.put("text2", text2);
        map.put("text3", item);
        data.add(map);
    }

    public void remove(HashMap<String, Object> map) {
        if(data.size() == 1) {
            Toast.makeText(getApplicationContext(),"至少留一个",Toast.LENGTH_SHORT).show();
            return;
        }
        GetCompletedRecordItemOutput item = (GetCompletedRecordItemOutput) map.get("text3");
        output.getItems().remove(item);
        data.remove(map);
        simpleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            assert data != null;
            GetCompletedRecordItemOutput item = (GetCompletedRecordItemOutput) data.getSerializableExtra("item");
            int position = data.getIntExtra("position",0);
            HashMap<String, Object> map = new HashMap<>();
            Log.d("TAG", item.toString());
            String text1 = item.getProductNo() + "\t " + (item.getProcessCompletedType() ==1? "片材":"卷材") + "\t " + item.getThick() + "*" + item.getWidth()+ "*" + item.getLength() + "\t "+ item.getNetWeight();
            String text2 = item.getNote() == null? "" : item.getNote();
            map.put("text1", text1);
            map.put("text2", text2);
            map.put("text3", item);
            this.data.set(position,map);
            simpleAdapter.notifyDataSetChanged();
        }
    }

}
