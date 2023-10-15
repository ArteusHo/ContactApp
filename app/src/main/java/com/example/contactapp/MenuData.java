package com.example.contactapp;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuData extends ViewModel {

    // this is the sizing for the recycler view
    public static final int WIDTH = 30;
    public static final int HEIGHT = 10;
    public MutableLiveData<Integer> option;
    public MenuData(){
        option = new MediatorLiveData<Integer>();
        option.setValue(0);
    }

    public int getOption(){
        return option.getValue();
    }

    public void setOption(int Option){
        option.setValue(Option);
    }

}
