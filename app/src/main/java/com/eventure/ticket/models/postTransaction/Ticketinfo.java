
package com.eventure.ticket.models.postTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticketinfo {

    @SerializedName("DurationSlotID")
    @Expose
    private String durationSlotID;
    @SerializedName("Tickets")
    @Expose
    private String tickets;
    @SerializedName("Amount")
    @Expose
    private String amount;

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
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
