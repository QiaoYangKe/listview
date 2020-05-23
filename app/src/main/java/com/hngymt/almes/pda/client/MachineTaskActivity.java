package com.hngymt.almes.pda.client;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hngymt.almes.pda.client.adapter.ListViewAdepter;
import com.hngymt.almes.pda.client.models.machine_tasks.GetMachineTasksInput;
import com.hngymt.almes.pda.client.models.machine_tasks.GetMachineTasksOutput;
import com.hngymt.almes.pda.client.models.machine_tasks.MachineTask;
import com.hngymt.almes.pda.client.utils.ServiceCallback;
import com.hngymt.almes.pda.client.utils.ServiceHttpClient;
import com.hngymt.almes.pda.client.widgets.LoadMoreListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MachineTaskActivity extends BaseActivity {
    SearchView mSearchView;
    androidx.appcompat.widget.SearchView.SearchAutoComplete mEdit;
    LoadMoreListView listView;
    ListViewAdepter simpleAdapter;
    List<HashMap<String, Object>> data;
    Gson gson;
    GetMachineTasksInput input = new GetMachineTasksInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_task);
        setBtnBack();
        initView();
        loadData();
        listView.setAdapter(simpleAdapter);
        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.getCount() - 1 != position) {
                   // TODO 点击事件
                }
            }
        });
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });


        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "查看详情");
                menu.add(0, 1, 1, "开工");
                menu.add(0, 2, 2, "完工");
            }
        });

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        if(input.getPage() == 1) {
            data.clear();
        }
        ServiceCallback callBack = new ServiceCallback() {
            @Override
            public void onSuccess(Object resultObj) {
                super.onSuccess(resultObj);
                final GetMachineTasksOutput output = (GetMachineTasksOutput) resultObj;
                if(output == null || output.getItems() == null) {
                    return;
                }
                for (int i = 0; i < output.getItems().size(); i++) {
                    String str = "<font color='#CC6600'>派工</font>";
                    if(output.getItems().get(i).getState() == 3) {
                        str =  "<font color='#FF0000'>生产</font>";
                    }
                    String text1 = str + "\t " +  output.getItems().get(i).getBlankProductNo() + "\t " + output.getItems().get(i).getCardNo() + "\t " + output.getItems().get(1).getProductNames() + "\t "+ output.getItems().get(i).getAlloyGradeName();
                    String text2 = "工序:" + output.getItems().get(i).getFinalProcessItemName() + "\t " + output.getItems().get(i).getParameter() + "\t " + output.getItems().get(i).getClaim();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("text1", text1);
                    map.put("text2", text2);
                    map.put("text3", output.getItems().get(i));
                    data.add(map);
                }
                MachineTaskActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter.notifyDataSetChanged();
                        listView.setLoadCompleted(data.size() == output.getTotalCount());
                        input.setPage(input.getPage() + 1);
                    }
                });
            }
        };
        ServiceHttpClient.getInstance().Get("/api/services/app/EquipmentTask/GetMachineTasks",input, GetMachineTasksOutput.class, MachineTaskActivity.this, callBack);
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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int position = menuInfo.position;
        HashMap<String, Object> map = (HashMap<String, Object>) simpleAdapter.getItem(position);
        MachineTask task = (MachineTask) map.get("text3");
        switch (item.getItemId()) {
            case 0:
                //TODO 查看详情
                startActivity(new Intent(MachineTaskActivity.this,MachineTaskDetailActivity.class).putExtra("task",task));
                break;
            case 1:
                if(task.getState() == 3) {
                    Toast.makeText(getApplicationContext(),"不能重复开工",Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(new Intent(MachineTaskActivity.this,ProductActivity.class).putExtra("task",task));
                break;
            case 2:
                if(task.getState() == 2) {
                    Toast.makeText(getApplicationContext(),"请先开工",Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(new Intent(MachineTaskActivity.this,CompleteActivity.class).putExtra("task",task));
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_machine_task, menu);
        final MenuItem item = menu.findItem(R.id.action_settings);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setIconifiedByDefault(false);
        mEdit = mSearchView.findViewById(R.id.search_src_text);
        mSearchView.setQueryHint("输入批号...");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String str) {
                input.setCardNo(str);
                input.setPage(1);
                mSearchView.clearFocus();
                // TODO 查询
                loadData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();
                input.setCardNo(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
