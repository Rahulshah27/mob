package com.example.mobtask.presentation.ui.mob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobtask.R;
import com.example.mobtask.network.domain.dao.MobDao;
import com.example.mobtask.network.domain.database.AppDatabase;
import com.example.mobtask.presentation.model.Mob;
import com.example.mobtask.presentation.ui.show.ShowActivity;
import com.example.mobtask.util.IProcessFilter;

public class MainActivity extends AppCompatActivity implements IProcessFilter {

    EditText etName, etNumber, etEmail;
    Button btnSubmit, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnShow = findViewById(R.id.btnShow);
        saveEditedMobRecord(2);
        btnShow.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ShowActivity.class)));
    }

    @Override
    public void saveEditedMobRecord(long viewType) {
        try {
            if(viewType == 1)
                saveEditedMobData();
            else
                saveCurrentBobData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCurrentBobData() {
        //final String position = getIntent().getExtras().getString("position");
        btnSubmit.setOnClickListener(view -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "room_db").allowMainThreadQueries().build();
            MobDao mobDao = db.mobDao();
            String name = etName.getText().toString();
            String number = etNumber.getText().toString();
            String email = etEmail.getText().toString();
            if (name.isEmpty()){
                etName.setError("This field cannot be blank");
            }
            else if (number.length() != 10){
                etNumber.setError("Number should be 10 digits");
            }
            else if (!email.contains("@")){
                etEmail.setError("Please enter proper email address");
            }
            else {
                mobDao.insertRecord(new Mob(0, name, number,email));
                etName.setText("");
                etNumber.setText("");
                etEmail.setText("");
                Toast.makeText(getApplicationContext(), "Record Inserted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveEditedMobData() {
        btnSubmit.setOnClickListener(view -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "room_db").allowMainThreadQueries().build();
            MobDao mobDao = db.mobDao();
            String name = etName.getText().toString();
            String number = etNumber.getText().toString();
            String email = etEmail.getText().toString();
            if (name.isEmpty()){
                etName.setError("This field cannot be blank");
            }
            else if (number.length() != 10){
                etNumber.setError("Number should be 10 digits");
            }
            else if (!email.contains("@")){
                etEmail.setError("Please enter proper email address");
            }
            else {
                mobDao.updateRecord(new Mob(0, name, number,email));
                etName.setText("");
                etNumber.setText("");
                etEmail.setText("");
                Toast.makeText(getApplicationContext(), "Record Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}