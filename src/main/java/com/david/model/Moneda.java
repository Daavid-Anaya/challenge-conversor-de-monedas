package com.david.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Moneda {
    @SerializedName("result")
    public String result;
    @SerializedName("time_last_update_utc")
    public String timeLastUpdateUtc;
    @SerializedName("base_code")
    public String baseCode;
    @SerializedName("conversion_rates")
    public Map<String, Double> rates;
}
