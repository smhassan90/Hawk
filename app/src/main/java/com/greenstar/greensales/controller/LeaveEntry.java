package com.greenstar.greensales.controller;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LeaveEntry extends AppCompatActivity implements View.OnClickListener {
    AppDatabase db = null;

    Button btnLeaveRequest;
    EditText etDate;
    EditText etReason;

    LinearLayout llParentLayout;

    ProgressDialog dialog = null;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leave_entry);

        db = AppDatabase.getAppDatabase(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llParentLayout = findViewById(R.id.llParentLayout);

        btnLeaveRequest = findViewById(R.id.btnLeaveRequest);
        btnLeaveRequest.setOnClickListener(this);

        etDate = findViewById(R.id.etDate);
        etReason = findViewById(R.id.etReason);

        etDate.setOnClickListener(this);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Leave Request");
        dialog.setMessage("Requesting. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLeaveRequest) {
            if (isValid()) {
                dialog.show();
                saveData();

                Toast.makeText(this, "Leave Request added. Please sync when you have internet", Toast.LENGTH_LONG).show();
                finish();
            }
            dialog.dismiss();
        }else if(v.getId()==R.id.etDate){
            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show();
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void saveData(){
        try{
            com.greenstar.greensales.model.LeaveEntry leaveEntry = new com.greenstar.greensales.model.LeaveEntry();
            String date = "";
            String reason = "";

            date = etDate.getText()!=null?etDate.getText().toString():"";
            reason = etReason.getText()!=null?etReason.getText().toString():"";
            leaveEntry.setId(Util.getNextID(this,Codes.LeaveEntries));
            leaveEntry.setDate(date);
            leaveEntry.setReason(reason);
            leaveEntry.setSaveTime(new Date().toString());

            db.getLeaveEntryDAO().insert(leaveEntry);

            Toast.makeText(this,"Successfully Saved",Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(this,"Something went wrong while saving the request.",Toast.LENGTH_LONG).show();
        }finally {
            Intent myIntent = new Intent(this, Menu.class);
            startActivity(myIntent);
        }
    }

    private boolean isValid(){
        boolean isValid = false;

        if(etReason.getText()==null || etReason.getText().toString().equals("")){
            Toast.makeText(this,"Please enter reason/category of leave.",Toast.LENGTH_SHORT).show();
        }else if(etDate.getText()==null || etDate.getText().toString().equals("")){
            Toast.makeText(this,"Please provide the customer name.",Toast.LENGTH_SHORT).show();
        }else{
            isValid = true;
        }

        return isValid;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }
}
