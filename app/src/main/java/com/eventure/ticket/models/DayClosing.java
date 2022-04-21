package com.eventure.ticket.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayClosing {

    @SerializedName("rideName")
    @Expose
    private String rideName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("agentName")
    @Expose
    private String agentName;
    @SerializedName("cashReceived")
    @Expose
    private String cashReceived;
    @SerializedName("creditReceived")
    @Expose
    private String creditReceived;
    @SerializedName("debitReceived")
    @Expose
    private String debitReceived;
    @SerializedName("shortageCash")
    @Expose
    private String shortageCash;
    @SerializedName("shortageCredit")
    @Expose
    private String shortageCredit;
    @SerializedName("shortageDebit")
    @Expose
    private String shortageDebit;
    @SerializedName("closeDate")
    @Expose
    private String closeDate;
    @SerializedName("retMsg")
    @Expose
    private String retMsg;
    @SerializedName("tranNo")
    @Expose
    private Integer tranNo;

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCashReceived() {
        return cashReceived;
    }

    public void setCashReceived(String cashReceived) {
        this.cashReceived = cashReceived;
    }

    public String getCreditReceived() {
        return creditReceived;
    }

    public void setCreditReceived(String creditReceived) {
        this.creditReceived = creditReceived;
    }

    public String getDebitReceived() {
        return debitReceived;
    }

    public void setDebitReceived(String debitReceived) {
        this.debitReceived = debitReceived;
    }

    public String getShortageCash() {
        return shortageCash;
    }

    public void setShortageCash(String shortageCash) {
        this.shortageCash = shortageCash;
    }

    public String getShortageCredit() {
        return shortageCredit;
    }

    public void setShortageCredit(String shortageCredit) {
        this.shortageCredit = shortageCredit;
    }

    public String getShortageDebit() {
        return shortageDebit;
    }

    public void setShortageDebit(String shortageDebit) {
        this.shortageDebit = shortageDebit;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Integer getTranNo() {
        return tranNo;
    }

    public void setTranNo(Integer tranNo) {
        this.tranNo = tranNo;
    }

}