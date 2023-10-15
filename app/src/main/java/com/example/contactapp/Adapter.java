package com.example.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<MenuDataVH>{


    // TODO: add sql into here and throw it into here
    // change how u get data and it should run with the rest
    List<PersonClass> data;
    public Adapter (List<PersonClass> data) {this.data = data; }
    @NonNull
    @Override
    public MenuDataVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View newView = layoutInflater.inflate(R.layout.grid_cell, parent, false);
        int size = parent.getMeasuredHeight() / MenuData.HEIGHT + 1;
        ViewGroup.LayoutParams lp = newView.getLayoutParams();
        MenuDataVH newMenuDataVH = new MenuDataVH(newView, parent);
        return newMenuDataVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuDataVH holder, int position) {
        int row = position % MenuData.HEIGHT;
        int col = position / MenuData.WIDTH;
        PersonClass singledata = data.get(position);
        holder.name.setText(singledata.toString());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "this needa go to edit screen" + singledata, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
