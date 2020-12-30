package com.hfad.machineparts2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MachineRecordActivity extends AppCompatActivity {
    private int id,i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_record);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id = intent.getIntExtra("position", 0);// I get the position of the the machine selected
        i = id + 1;// I added one to it because it is zero based counting and our machine ID starts from 1

        BottomNavigationView bottomNav = findViewById(R.id.bottomnav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
    }
    public int getMyData() {
        // This method will be used to get the position of the machine selected to the inspectionRecyclerFragment
        return i;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.print:
                    selectedFragment = new PrintFragment();
                    break;
                case R.id.inspecton_sheet:
                    selectedFragment = new InspectionRecyclerFragment();
                    break;
                case R.id.notes:
                    selectedFragment = new NotesFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}