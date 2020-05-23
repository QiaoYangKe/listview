package com.hngymt.almes.pda.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hngymt.almes.pda.client.models.machine_tasks.GetCompletedRecordItemOutput;
import com.hngymt.almes.pda.client.models.machine_tasks.MachineTask;
import com.hngymt.almes.pda.client.models.pipes.Pipe;
import com.hngymt.almes.pda.client.models.production_equipments.ProductionEquipment;
import com.hngymt.almes.pda.client.utils.ServiceCallback;
import com.hngymt.almes.pda.client.utils.ServiceHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompleteUpdateFormActivity extends BaseActivity {
    Spinner spinner,spinnerType;
    List<Pipe> pipes;
    Gson gson = new Gson();
    int position;
    GetCompletedRecordItemOutput output;
    EditText etProductNo,etThick,etWidth,etLength,etGross,etNet,etNote,etSelfResult,
            etAfterSurface,etAfterPlant,etFilmWeight,etPipWeight,etSplices,etTare;
    List<String> productTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_update_form);
        Bundle extras = this.getIntent().getExtras();
        setBtnBack();
        spinner = findViewById(R.id.sp_pipe_type);
        spinnerType = findViewById(R.id.sp_p_type);
        if(extras!=null && extras.getSerializable("output") != null) {
            output = (GetCompletedRecordItemOutput) extras.getSerializable("output");
            loadData();
        }
        if(extras!=null && extras.getSerializable("position") != null) {
            position = extras.getInt("position");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_update, menu);
        MenuItem cancelItem = menu.findItem(R.id.cancel_product);
        MenuItem saveItem = menu.findItem(R.id.save_product);
        cancelItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return false;
            }
        });
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                output.setThick(etThick.getText().toString());
                output.setWidth(etWidth.getText().toString());
                output.setLength(etLength.getText().toString());
                output.setGrossWeight(Double.parseDouble(etGross.getText().toString()));
                output.setNetWeight(Double.parseDouble(etNet.getText().toString()));
                output.setTareWeight(Double.parseDouble(etTare.getText().toString()));
                output.setNote(etNote.getText().toString());
                output.setMachinedSurface(etSelfResult.getText().toString());
                output.setMachinedVersion(etAfterPlant.getText().toString());
                output.setSelfTest(etSelfResult.getText().toString());
                output.setPieceNumber(Double.parseDouble(etSplices.getText().toString()));
                output.setProcessCompletedType(spinnerType.getSelectedItemPosition());
                output.setPipeId(((Pipe)spinner.getSelectedItem()).getId());
                Intent intent = new Intent();
                intent.putExtra("item", output);
                intent.putExtra("position", position);
                setResult(1, intent);
                finish();
                return true;
            }
        });
        return true;
    }

    public void loadData() {
        etProductNo= findViewById(R.id.et_product_no);
        etThick= findViewById(R.id.et_thick);
        etWidth= findViewById(R.id.et_width);
        etLength= findViewById(R.id.et_length);
        etGross= findViewById(R.id.et_gross);
        etNet= findViewById(R.id.et_net);
        etNote= findViewById(R.id.et_note);
        etSelfResult= findViewById(R.id.et_result);
        etAfterSurface= findViewById(R.id.et_surface);
        etAfterPlant= findViewById(R.id.et_plant);
        etFilmWeight= findViewById(R.id.et_film_weight);
        etPipWeight= findViewById(R.id.et_pipe_weight);
        etSplices= findViewById(R.id.et_splice);
        etTare = findViewById(R.id.et_tare);

        etProductNo.setText(output.getProductNo());
        etThick.setText(output.getThick());
        etWidth.setText(output.getWidth());
        etLength.setText(output.getLength());
        etTare.setText(String.valueOf(output.getTareWeight()));
        etGross.setText(String.valueOf(output.getGrossWeight()));
        etNet.setText(String.valueOf(output.getNetWeight()));
        etNote.setText(output.getNote());
        etSelfResult.setText(output.getSelfTest());
        etAfterSurface.setText(output.getMachinedSurface());
        etAfterPlant.setText(output.getMachinedVersion());
        etFilmWeight.setText(String.valueOf(output.getFilmWeight()));
        etPipWeight.setText(String.valueOf(output.getPipeWeight()));
        etSplices.setText(String.valueOf(output.getPieceNumber()));
        initPipes();
        initProductType();
    }

    public void initPipes() {
        ServiceCallback callBack = new ServiceCallback() {
            @Override
            public void onSuccess(Object resultObj) {
                super.onSuccess(resultObj);
                pipes = gson.fromJson(gson.toJson(resultObj), new TypeToken<List<Pipe>>() {}.getType());
                ArrayAdapter<Pipe> adapter = new ArrayAdapter<Pipe>(CompleteUpdateFormActivity.this, android.R.layout.simple_list_item_1, pipes);
                spinner.setAdapter(adapter);
                CompleteUpdateFormActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Pipe pipe = new Pipe(output.getPipeId());
                        int location = pipes.indexOf(pipe);
                        spinner.setSelection(location);
                    }
                });
            }
        };
        ServiceHttpClient.getInstance().Get("/api/services/app/Pipe/GetAll",null, ArrayList.class, CompleteUpdateFormActivity.this, callBack);
    }

    public void initProductType() {
        productTypes = new ArrayList<>();
        productTypes.add("卷材");
        productTypes.add("片材");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CompleteUpdateFormActivity.this, android.R.layout.simple_list_item_1, productTypes);
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(output.getProcessCompletedType());
    }
}
