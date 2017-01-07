package com.peter.util.data.serializable.json.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 1/7/2017.
 */
public class Prices implements Serializable{
    private List<Instrument> prices = new ArrayList<>();

    public List<Instrument> addInstrument(Instrument instrument){
        prices.add(instrument);
        return prices;
    }

    public List<Instrument> getPrices() {
        return prices;
    }

    public void setPrices(List<Instrument> prices) {
        this.prices = prices;
    }
}
