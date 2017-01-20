package com.example.android.salesmonitor.activitiy;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.salesmonitor.R;
import com.example.android.salesmonitor.SalesMonitorApp;
import com.example.android.salesmonitor.domain.SaleActivity;
import com.example.android.salesmonitor.exception.SaleMonitorException;
import com.example.android.salesmonitor.manage.DataStoreManager;
import com.example.android.salesmonitor.util.GraphGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaveSaleActivity extends AppCompatActivity {
    private DataStoreManager dataStoreManager;

    @BindView(R.id.autoTxtSale)
    public AutoCompleteTextView txtSaleActivityName;
//    private DatePicker dateSaleActivity;
    @BindView(R.id.timeStartSaleActivity)
    public TimePicker timeStartSaleActivity;
    @BindView(R.id.timeEndSaleActivity)
    public TimePicker timeEndSaleActivity;
    @BindView(R.id.date_picker_text)
    public EditText datePickerText;
    private DatePickerDialog datePickerDialog;

    private int saleActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        if (this.getIntent().hasExtra("title")) {// this.getIntent().getExtras().containsKey("title")) {
            setTitle(this.getIntent().getExtras().getString("title"));
        } else {
            setTitle("Add Sale Activity");
        }
        ButterKnife.bind(this);

        dataStoreManager = SalesMonitorApp.getInstance().getDataStoreManager();

        timeStartSaleActivity.setIs24HourView(true);
        timeEndSaleActivity.setIs24HourView(true);

        datePickerDialog = new DatePickerDialog(this);
        datePickerText.setText("" + datePickerDialog.getDatePicker().getDayOfMonth() + " / " + datePickerDialog.getDatePicker().getMonth() + " / " + datePickerDialog.getDatePicker().getYear());
        datePickerText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerDialog.show();
                } else {
                    datePickerDialog.hide();
                }
            }
        });
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datePickerText.setText("" + dayOfMonth + " / " + month + " / " + year);

            }
        });
        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                datePickerText.clearFocus();
                timeStartSaleActivity.requestFocus();
            }
        });

        List<String> saleNames = new ArrayList<>();
        saleNames.addAll(dataStoreManager.getSaleNames());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, saleNames);
        txtSaleActivityName.setAdapter(adapter);

        if (this.getIntent().hasExtra("saleActivityId")) {//this.getIntent().getExtras().containsKey("saleActivityId")) {
            saleActivityId = this.getIntent().getExtras().getInt("saleActivityId");
            SaleActivity saleActivity = dataStoreManager.getSaleActivityById(saleActivityId);
            if (saleActivity != null) {
                completeForm(saleActivity);
            }
        } else {
            saleActivityId = 0;
        }
    }

    public void saveSaleActivity(View view) {
        try {
            String saleName = txtSaleActivityName.getText().toString();
            if (saleName.length() <= 0) {
                throw new SaleMonitorException("A sale name is required");
            }
            DatePicker datePicker = datePickerDialog.getDatePicker();
            GregorianCalendar beginMoment = new GregorianCalendar(
                    datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timeStartSaleActivity.getHour(), timeStartSaleActivity.getMinute());
            GregorianCalendar endMoment = new GregorianCalendar(
                    datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timeEndSaleActivity.getHour(), timeEndSaleActivity.getMinute());
            if (!beginMoment.before(endMoment)) {
                throw new SaleMonitorException("The start time must be before the end time");
            }
//            dataStoreManager.addSaleActivity(saleName, beginMoment, endMoment);
            SaleActivity saleActivity = new SaleActivity(saleActivityId, saleName, beginMoment, endMoment);
            dataStoreManager.saveSaleActivity(saleActivity);
            Toast toasty = Toast.makeText(getApplicationContext(), "Sale activity saved", Toast.LENGTH_SHORT);
            toasty.show();
//            finish();
            returnToParentActivity();

//            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//            sendIntent.setType("plain/text");
//            sendIntent.setData(Uri.parse("sergiu.c.nistor@gmail.com"));
//            sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "New Sales Activity");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, saleActivity.toString());
//            startActivity(sendIntent);

        } catch (SaleMonitorException e) {
            Toast toasty = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
            toasty.show();
        }
    }

    private void completeForm(SaleActivity saleActivity) {
        txtSaleActivityName.setText(saleActivity.getSaleName());
//        dateSaleActivity
        datePickerDialog.getDatePicker()
                .updateDate(
                saleActivity.getBeginMoment().get(Calendar.YEAR),
                saleActivity.getBeginMoment().get(Calendar.MONTH),
                saleActivity.getBeginMoment().get(Calendar.DAY_OF_MONTH)
        );
        datePickerText.setText("" + datePickerDialog.getDatePicker().getDayOfMonth() + " / " + datePickerDialog.getDatePicker().getMonth() + " / " + datePickerDialog.getDatePicker().getYear());
        timeStartSaleActivity.setHour(saleActivity.getBeginMoment().get(Calendar.HOUR_OF_DAY));
        timeStartSaleActivity.setMinute(saleActivity.getBeginMoment().get(Calendar.MINUTE));
        timeEndSaleActivity.setHour(saleActivity.getEndMoment().get(Calendar.HOUR_OF_DAY));
        timeEndSaleActivity.setMinute(saleActivity.getEndMoment().get(Calendar.MINUTE));

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.chart);
        GraphGenerator.populateGraphWithOneSale(getBaseContext(), frameLayout, saleActivity.getSaleName());
    }

    public void deleteSaleActivity(View view) {
        dataStoreManager.deleteSaleActivityById(saleActivityId);
        returnToParentActivity();
    }

    private void returnToParentActivity() {
        if (getParent() != null) {
            getParent().setResult(1);
        }
        finish();
    }
}
