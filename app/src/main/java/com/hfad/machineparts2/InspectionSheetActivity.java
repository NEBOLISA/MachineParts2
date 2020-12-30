package com.hfad.machineparts2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class InspectionSheetActivity extends AppCompatActivity {
    private String name,partNumber,description;
    private int id,i;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("position",0);
        i=id+1;

        name = intent.getStringExtra("customer");
        partNumber = intent.getStringExtra("number");
        description=intent.getStringExtra("description");
        setContentView(R.layout.activity_inspection_sheet);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Toast.makeText(InspectionSheetActivity.this,getName() + getNumber(),Toast.LENGTH_LONG).show();
    }
    public int getId(){
       return i;
    }
    public String getDescription() {
        // This method]will be used to get the name of customer for the selected recyclerview in the fragment
        return description;
    }
    public String getName() {
        // This method will be used to get the name of customer for the selected recyclerview in the fragment
        return name;
    }
    public String getNumber() {
        // This method will be used to get the partnumber of the selected recycler view in the fragment
        return partNumber;
    }

    private void showDialog()
    {
        progressDialog = new ProgressDialog(InspectionSheetActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving..");
        progressDialog.show();
    }
    private void hideDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}