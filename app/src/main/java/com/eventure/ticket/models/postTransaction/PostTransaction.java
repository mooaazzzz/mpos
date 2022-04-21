
package com.eventure.ticket.models.postTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostTransaction {

    @SerializedName("Transaction")
    @Expose
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
