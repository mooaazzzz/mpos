package com.eventure.ticket.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticketinfo {

    @SerializedName("DurationSlotId")
    @Expose
    private String durationSlotID;
    @SerializedName("No_of_tickets")
    @Expose
    private String tickets;
    @SerializedName("Amount")
    @Expose
    private String Amount;

    public String getDurationSlotID() {
        return durationSlotID;
    }

    public void setDurationSlotID(String durationSlotID) {
        this.durationSlotID = durationSlotID;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
