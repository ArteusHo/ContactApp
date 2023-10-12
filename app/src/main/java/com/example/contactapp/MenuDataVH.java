package com.example.contactapp;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class MenuDataVH extends RecyclerView.ViewHolder{

    public TextView name;
    public Button edit;

    public MenuDataVH(@NonNull View itemView, ViewGroup parent)
    {
        super (itemView);
        int size = parent.getMeasuredHeight() / MenuData.HEIGHT + 1;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.height = size;
        lp.width = size;

        name = itemView.findViewById(R.id.person);
        edit = itemView.findViewById(R.id.editbutton);
    }


}