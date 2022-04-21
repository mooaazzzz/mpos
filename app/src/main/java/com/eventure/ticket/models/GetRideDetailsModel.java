package com.eventure.ticket.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRideDetailsModel {

    @SerializedName("rideId")
    @Expose
    private Integer rideId;
    @SerializedName("rideName")
    @Expose
    private String rideName;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("durationName")
    @Expose
    private String durationName;
    @SerializedName("ridePrice")
    @Expose
    private String ridePrice;

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
    }

    public String getRidePrice() {
        return ridePrice;
    }

    public void setRidePrice(String ridePrice) {
        this.ridePrice = ridePrice;
    }

}