package com.eventure.ticket.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {
    @SerializedName("rideName")
    @Expose
    private String rideName;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("totalPaid")
    @Expose
    private String totalPaid;
    @SerializedName("paymentSource")
    @Expose
    private String paymentSource;
    @SerializedName("checkIn")
    @Expose
    private String checkIn;
    @SerializedName("checkOut")
    @Expose
    private String checkOut;
    @SerializedName("customerPhone")
    @Expose
    private String customerPhone;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("finalPaid")
    @Expose
    private String finalPaid;
    @SerializedName("finalTicket")
    @Expose
    private String finalTicket;

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFinalPaid() {
        return finalPaid;
    }

    public void setFinalPaid(String finalPaid) {
        this.finalPaid = finalPaid;
    }

    public String getFinalTicket() {
        return finalTicket;
    }

    public void setFinalTicket(String finalTicket) {
        this.finalTicket = finalTicket;
    }
}
