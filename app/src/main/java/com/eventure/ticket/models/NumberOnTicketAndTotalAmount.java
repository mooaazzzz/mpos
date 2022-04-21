package com.eventure.ticket.models;

public class NumberOnTicketAndTotalAmount {

    int numberOfTicket;
    int totalAmount;
    int slotId;


    public NumberOnTicketAndTotalAmount(int numberOfTicket, int totalAmount, int slotId) {
        this.numberOfTicket = numberOfTicket;
        this.totalAmount = totalAmount;
        this.slotId = slotId;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getNumberOfTicket() {
        return numberOfTicket;
    }


    public int getTotalAmount() {
        return totalAmount;
    }

}
