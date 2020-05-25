package com.taurunium.currencyconverter.utils;

import com.taurunium.currencyconverter.R;

import java.util.HashMap;

public class Constants {

    public static String baseCurrency = "EUR";

    public static boolean isUpdateAvailable = true;

    //CURRENCY CODES
    public static final String AUD = "AUD";
    public static final String BGN = "BGN";
    public static final String BRL = "BRL";
    public static final String CAD = "CAD";
    public static final String CHF = "CHF";
    public static final String CNY = "CNY";
    public static final String CZK = "CZK";
    public static final String DKK = "DKK";
    public static final String GBP = "GBP";
    public static final String HKD = "HKD";
    public static final String HRK = "HRK";
    public static final String HUF = "HUF";
    public static final String IDR = "IDR";
    public static final String ILS = "ILS";
    public static final String INR = "INR";
    public static final String ISK = "ISK";
    public static final String JPY = "JPY";
    public static final String KRW = "KRW";
    public static final String MXN = "MXN";
    public static final String MYR = "MYR";
    public static final String NOK = "NOK";
    public static final String NZD = "NZD";
    public static final String PHP = "PHP";
    public static final String PLN = "PLN";
    public static final String RON = "RON";
    public static final String RUB = "RUB";
    public static final String SEK = "SEK";
    public static final String SGD = "SGD";
    public static final String THB = "THB";
    public static final String USD = "USD";
    public static final String ZAR = "ZAR";
    public static final String EUR = "EUR";

    public static HashMap<String, Integer> flagsMap = new HashMap<>();

    public static void createFlagsMap(){
        flagsMap.put(Constants.EUR, R.drawable.eu_flag);
        flagsMap.put(Constants.AUD, R.drawable.aud);
        flagsMap.put(Constants.BGN, R.drawable.bgn);
        flagsMap.put(Constants.BRL, R.drawable.brl);
        flagsMap.put(Constants.CAD, R.drawable.cad);
        flagsMap.put(Constants.CHF, R.drawable.chf);
        flagsMap.put(Constants.CNY, R.drawable.cny);
        flagsMap.put(Constants.CZK, R.drawable.czk);
        flagsMap.put(Constants.DKK, R.drawable.dkk);
        flagsMap.put(Constants.GBP, R.drawable.gbp);
        flagsMap.put(Constants.HKD, R.drawable.hkd);
        flagsMap.put(Constants.HRK, R.drawable.hrk);
        flagsMap.put(Constants.HUF, R.drawable.huf);
        flagsMap.put(Constants.IDR, R.drawable.idr);
        flagsMap.put(Constants.ILS, R.drawable.ils);
        flagsMap.put(Constants.INR, R.drawable.inr);
        flagsMap.put(Constants.ISK, R.drawable.isk);
        flagsMap.put(Constants.JPY, R.drawable.jpy);
        flagsMap.put(Constants.KRW, R.drawable.krw);
        flagsMap.put(Constants.MXN, R.drawable.mxn);
        flagsMap.put(Constants.MYR, R.drawable.myr);
        flagsMap.put(Constants.NOK, R.drawable.nok);
        flagsMap.put(Constants.NZD, R.drawable.nzd);
        flagsMap.put(Constants.PHP, R.drawable.php);
        flagsMap.put(Constants.PLN, R.drawable.pln);
        flagsMap.put(Constants.RON, R.drawable.ron);
        flagsMap.put(Constants.RUB, R.drawable.rub);
        flagsMap.put(Constants.SEK, R.drawable.sek);
        flagsMap.put(Constants.SGD, R.drawable.sgd);
        flagsMap.put(Constants.THB, R.drawable.thb);
        flagsMap.put(Constants.USD, R.drawable.usd);
        flagsMap.put(Constants.ZAR, R.drawable.zar);
    }

    public static int getFlag(String countryCode){
        return flagsMap.get(countryCode);
    }

}
