package com.example.mobtask.presentation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mobtask.R;
import com.example.mobtask.network.domain.dao.MobDao;
import com.example.mobtask.network.domain.database.AppDatabase;
import com.example.mobtask.presentation.model.Mob;
import com.example.mobtask.presentation.ui.mob.MainActivity;
import com.example.mobtask.util.IProcessFilter;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MobAdapter extends RecyclerView.Adapter<MobAdapter.VH>
{
    List<Mob> mobList;
    Context context;
    IProcessFilter mCallback;

    public MobAdapter(List<Mob> mobList, Context context, IProcessFilter mCallback) {
        this.mobList = mobList;
        this.context = context;
        this.mCallback = mCallback;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rv_items_rcud,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(mobList.get(position).getName());
        holder.tvNumber.setText(mobList.get(position).getNumber());
        holder.tvEmail.setText(mobList.get(position).getEmail());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(holder.tvName.getContext(),
                        AppDatabase.class, "room_db").allowMainThreadQueries().build();
                MobDao mobDao = db.mobDao();
                mobDao.deleteById(mobList.get(position).getUid());
                mobList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("name",mobList.get(position).name);
                intent.putExtra("number",mobList.get(position).number);
                intent.putExtra("email",mobList.get(position).email);
                intent.putExtra("position", holder.getAdapterPosition());
                context.startActivity(intent);
                mCallback.saveEditedMobRecord(1);
            }
        });
    }

    private void updateMobItem(Mob mob){
        if (mob == null)
            return;
        for (Mob item : mobList){
            if (item.uid == mob.uid){
                int position = mobList.indexOf(mob);
                mobList.set(position, mob);
                notifyItemChanged(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mobList.size();
    }

    class VH extends RecyclerView.ViewHolder
    {
        TextView tvName, tvEmail, tvNumber;
        ImageButton ivDelete, ivUpdate;
        public VH(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvNumber=itemView.findViewById(R.id.tv_number);
            tvEmail=itemView.findViewById(R.id.tv_email);
            ivDelete=itemView.findViewById(R.id.m_delete1);
            ivUpdate=itemView.findViewById(R.id.m_update);
        }
    }
}

