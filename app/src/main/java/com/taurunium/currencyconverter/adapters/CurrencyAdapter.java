package com.taurunium.currencyconverter.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taurunium.currencyconverter.R;
import com.taurunium.currencyconverter.models.Currency;
import com.taurunium.currencyconverter.utils.Constants;
import com.taurunium.currencyconverter.utils.MyDiffCallback;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyHolder>{

    private Context context;
    private int size;
    private ArrayList<Currency> currencies = new ArrayList<>();
    private LayoutInflater inflater;
    private OnCurrencyItemListener mListener;

    public CurrencyAdapter(Context context, ArrayList<Currency> tmpCurrencies, OnCurrencyItemListener ltn){
        this.context = context;
        this.currencies = tmpCurrencies;
        this.mListener = ltn;

        size = this.currencies.size();
        inflater = LayoutInflater.from(context);
    }

    public void insertData(ArrayList<Currency> insertList){
        MyDiffCallback diffCallback = new MyDiffCallback(currencies, insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        currencies.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateData(ArrayList<Currency> newList){
        MyDiffCallback diffCallback = new MyDiffCallback(currencies, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        currencies.clear();
        currencies.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_currency,parent,false);
        return new MyHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvCurrencyTitle.setText("" + currencies.get(position).getName());
        holder.etCurrencyValue.setText(String.format("%.5f", currencies.get(position).getValue()));

        Picasso.get().load(currencies.get(position).getFlagURI()).placeholder(R.drawable.ic_launcher_foreground).into(holder.civFlag);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvCurrencyTitle;
        private EditText etCurrencyValue;
        private CircleImageView civFlag;
        private OnCurrencyItemListener onItemListener;

        public MyHolder(View itemView, OnCurrencyItemListener onItemListener){
            super(itemView);

            tvCurrencyTitle = (TextView)itemView.findViewById(R.id.tvCurrencyTitle);
            etCurrencyValue = (EditText)itemView.findViewById(R.id.etCurrencyValue);
            civFlag = (CircleImageView)itemView.findViewById(R.id.civFlag);

            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View view) {
            onItemListener.onCurrencyClicked(getAdapterPosition());
        }
    }

    public interface OnCurrencyItemListener{
        void onCurrencyClicked(int position);
    }
}
