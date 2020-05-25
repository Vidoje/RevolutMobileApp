package com.taurunium.currencyconverter.utils;

import com.taurunium.currencyconverter.models.Currency;

import java.util.ArrayList;

import androidx.recyclerview.widget.DiffUtil;

public class MyDiffCallback extends DiffUtil.Callback {
    private ArrayList<Currency> oldList;
    private ArrayList<Currency> newList;

    public MyDiffCallback(ArrayList<Currency> oldList, ArrayList<Currency> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition==newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition)==newList.get(newItemPosition);
    }
}
