package com.example.mobtask.presentation.ui.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import com.example.mobtask.R;
import com.example.mobtask.network.domain.dao.MobDao;
import com.example.mobtask.network.domain.database.AppDatabase;
import com.example.mobtask.presentation.adapter.MobAdapter;
import com.example.mobtask.presentation.model.Mob;
import com.example.mobtask.util.IProcessFilter;

import java.util.List;

public class ShowActivity extends AppCompatActivity implements IProcessFilter {
    RecyclerView recview;
    IProcessFilter mCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        getRoomData();
    }

    private void getRoomData() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "room_db").allowMainThreadQueries().build();
        MobDao mobDao = db.mobDao();

        recview=findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        List<Mob> list= mobDao.getAllMobList();
        MobAdapter adapter=new MobAdapter(list, this, mCallback);
        recview.setAdapter(adapter);
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

    private void saveEditedMobData() {
    }

    private void saveCurrentBobData() {
    }
}