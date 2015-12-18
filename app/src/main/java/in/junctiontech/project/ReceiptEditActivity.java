package in.junctiontech.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.junctiontech.project.employeeproject.Expense;
import in.junctiontech.project.employeeproject.Receipt;

public class ReceiptEditActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;
    private ListView edit_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_edit);
        referenceInitialization();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update() {
        PMSDatabase pmsDatabase = PMSDatabase.getInstance(this);

        List<Receipt> receiptList = pmsDatabase.getReceiptList();

        if (receiptList != null) {
            receiptList.add(0,new Receipt());
        }
        else {
            receiptList = new ArrayList<>(1);
            receiptList.add(0, new Receipt());
        }
            if (customAdapter == null) {
                Log.d("ADAPTER", "CREATED");
                customAdapter = new CustomAdapter(this, receiptList);
                edit_listview.setAdapter(customAdapter);
            } else {
                Log.d("ADAPTER", "MODIFIED");
                customAdapter.clear();
                customAdapter.addAll(receiptList);
                customAdapter.notifyDataSetChanged();
            }


        /*} else {
            edit_listview.setAdapter(new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, new String[]{"No Receipt List In Database"}));

        }*/
    }

    private void referenceInitialization() {
        edit_listview = (ListView) findViewById(R.id.receipt_edit_listView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edit_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Receipt receipt = customAdapter.getItem(position);
                Intent intent = new Intent(ReceiptEditActivity.this, ReceiptActivity.class);
                intent.putExtra("PROJECT_ID", receipt.getProject_id());
                intent.putExtra("TASK_ID", receipt.getTask_id());
                intent.putExtra("KEY", receipt.getKey());
                if(receipt.getProject_id()!=null)
                    intent.putExtra("DATA", "Edit");
                else
                    intent.putExtra("DATA", "Add");

                startActivity(intent);

                //   Utility.showToast(ExpenseEditActivity.this,((Expense)parent.getItemAtPosition(position)).getDate() );
            }
        });
/*
        edit_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                // Toast.makeText(ProjectActivity.this,position+"",Toast.LENGTH_LONG).show();
                task = customAdapter.getItem(position);
                return false;
            }
        });
        registerForContextMenu(lv);*/


    }

    private class CustomAdapter extends ArrayAdapter<Receipt> { // TODO see <String> and think
        private final Context context;
        private List<Receipt> receiptList;

        public CustomAdapter(Context context, List<Receipt> receiptList) {
            super(context, R.layout.edit_receipt_list, receiptList);
            this.context = context;
            this.receiptList = receiptList;
        }

        @Override
        public Receipt getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // super.getView(position, convertView, parent);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = myLayoutInflater.inflate(R.layout.edit_receipt_list, null);
                holder = new ViewHolder();
                holder.edit_receipt_projectId = (TextView) convertView.findViewById(R.id.edit_receipt_projectId);
                holder.edit_receipt_taskId = (TextView) convertView.findViewById(R.id.edit_receipt_taskId);
                holder.edit_receipt_description = (TextView) convertView.findViewById(R.id.edit_receipt_description);
                holder.edit_receipt_date = (TextView) convertView.findViewById(R.id.edit_receipt_date);
                holder.edit_receipt_material = (TextView) convertView.findViewById(R.id.edit_receipt_material);
                holder.edit_receipt_textview = (TextView) convertView.findViewById(R.id.edit_receipt_textview);
                holder.edit_receipt_linearlayout = (LinearLayout) convertView.findViewById(R.id.edit_receipt_linearlayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            Receipt receipt = receiptList.get(position);
            if(receipt.getProject_id()!=null) {
                holder.edit_receipt_textview.setVisibility(View.INVISIBLE);
                holder.edit_receipt_linearlayout.setVisibility(View.VISIBLE);
                holder.edit_receipt_projectId.setText(receipt.getProject_id());
                holder.edit_receipt_taskId.setText(receipt.getTask_id());
                holder.edit_receipt_description.setText(receipt.getDescription());
                holder.edit_receipt_date.setText(receipt.getDate());
                holder.edit_receipt_material.setText(receipt.getMaterial());
            }else {
                holder.edit_receipt_textview.setVisibility(View.VISIBLE);
                holder.edit_receipt_linearlayout.setVisibility(View.INVISIBLE);
            }


            return convertView;
        }

        public class ViewHolder {
            private TextView edit_receipt_projectId, edit_receipt_taskId, edit_receipt_description,
                    edit_receipt_date, edit_receipt_material,edit_receipt_textview;
            private LinearLayout edit_receipt_linearlayout;
            // private things are access easily in method of same class
        }
    }

}
