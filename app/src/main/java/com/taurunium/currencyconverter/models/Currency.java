package com.taurunium.currencyconverter.models;

public class Currency {
    private String name;
    private int flagURI;
    private double value;

    public Currency() {
    }

    public Currency(String name, double value, int flag) {
        this.name = name;
        this.value = value;
        this.flagURI = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlagURI() {
        return flagURI;
    }

    public void setFlagURI(int flagURI) {
        this.flagURI = flagURI;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
