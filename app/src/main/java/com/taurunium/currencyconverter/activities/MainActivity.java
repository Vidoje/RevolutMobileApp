package com.taurunium.currencyconverter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.taurunium.currencyconverter.R;
import com.taurunium.currencyconverter.adapters.CurrencyAdapter;
import com.taurunium.currencyconverter.interfaces.APIService;
import com.taurunium.currencyconverter.models.Currency;
import com.taurunium.currencyconverter.models.MainModel;
import com.taurunium.currencyconverter.utils.Constants;

import retrofit2.converter.gson.GsonConverterFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.OnCurrencyItemListener {

    public static final String TAG = "MainActivity";

    //UI variables
    private RecyclerView rvCurrencies;
    private EditText etCurrencyValue;
    private TextView tvCurrencyTitle;
    private CircleImageView civFlag;

    //other variables
    private ArrayList<Currency> currenciesList = new ArrayList<>();
    private ArrayList<Currency> tmpList;
    private boolean isDataInserted = false;
    private CurrencyAdapter rvAdapter;
    private int baseValue = 100;

    //retrofit variables
    private Retrofit retrofit;
    private APIService apiService;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.createFlagsMap();
        initUI();
        initAPICall();
    }


    //initializing UI elements
    private void initUI(){
        rvCurrencies = (RecyclerView)findViewById(R.id.rvCurrencies);
        civFlag = (CircleImageView)findViewById(R.id.civFlag);
        tvCurrencyTitle = (TextView)findViewById(R.id.tvCurrencyTitle);

        etCurrencyValue = (EditText)findViewById(R.id.etCurrencyValue);
        etCurrencyValue.setText(""+baseValue);

        etCurrencyValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    baseValue = Integer.parseInt(charSequence.toString());
                }catch (Exception e){
                    Log.e(TAG, "Error:"+e);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        rvCurrencies.setLayoutManager(linearLayoutManager);

    }


    //initializing Retrofit API call
    private void initAPICall(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        apiService = retrofit.create(APIService.class);


        //Every X seonds we will call API to get new data
        disposable = Observable.interval(1000, 2000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::callCurrenciesEndpoint, this::onError);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (disposable.isDisposed()) {
            disposable = Observable.interval(1000, 2000,
                    TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::callCurrenciesEndpoint, this::onError);
        }
    }

    private void callCurrenciesEndpoint(Long aLong) {
        Observable<MainModel> observable = apiService.getCurrencies(Constants.baseCurrency);
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(this::handleResults, this::handleError);
    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }


    private void handleResults(MainModel model) {
        if(model.getRates().size()>0){
            fillRecyclerView(model.getRates());
        }else{
            Toast.makeText(this, "Currency list is empty.", Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        Toast.makeText(this, "Error:"+t.getMessage(), Toast.LENGTH_LONG).show();
    }


    private void fillRecyclerView(HashMap<String, Double> map){

        tmpList = new ArrayList<>();

        for(Map.Entry<String, Double> entry : map.entrySet()){
            String key = entry.getKey();
            Double value = entry.getValue()*baseValue;

            int tmpFlagId = Constants.getFlag(key);

            Currency tmpCurrency = new Currency(key, value, tmpFlagId);
            currenciesList.add(tmpCurrency);
            tmpList.add(tmpCurrency);
        }

        if(!isDataInserted){
            isDataInserted = true;
            ArrayList<Currency> newcurrenciesList = new ArrayList<>();

            rvAdapter=new CurrencyAdapter(MainActivity.this, currenciesList, this);
            rvCurrencies.setAdapter(rvAdapter);

            for(int i=0;i<tmpList.size();i++){
                newcurrenciesList.add(tmpList.get(i));
            }
            rvAdapter.insertData(newcurrenciesList);
        }else{

            ArrayList<Currency> newcurrenciesList = new ArrayList<>();

            for(int i=0;i<tmpList.size();i++){
                newcurrenciesList.add(tmpList.get(i));
            }
            rvAdapter.updateData(newcurrenciesList);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        disposable.dispose();
    }

    @Override
    public void onCurrencyClicked(int position) {
        Currency tmpCurrencyObject = tmpList.get(position);
        Constants.baseCurrency = tmpCurrencyObject.getName();
        baseValue = 100;
        tvCurrencyTitle.setText(""+tmpCurrencyObject.getName());
        etCurrencyValue.setText(""+100);
        Picasso.get().load(tmpCurrencyObject.getFlagURI()).placeholder(R.drawable.ic_launcher_foreground).into(civFlag);

    }




}
