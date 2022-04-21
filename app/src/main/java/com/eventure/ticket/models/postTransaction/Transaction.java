
package com.eventure.ticket.models.postTransaction;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("Logid")
    @Expose
    private String logid;
    @SerializedName("PaymentSource")
    @Expose
    private String paymentSource;
    @SerializedName("CustomerPhone")
    @Expose
    private String customerPhone;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Nationality")
    @Expose
    private String nationality;
    @SerializedName("Ticketinfo")
    @Expose
    private List<Ticketinfo> ticketinfo = null;
    @SerializedName("TotalTickets")
    @Expose
    private String totalTickets;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Ticketinfo> getTicketinfo() {
        return ticketinfo;
    }

    public void setTicketinfo(List<Ticketinfo> ticketinfo) {
        this.ticketinfo = ticketinfo;
    }

    public String getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(String totalTickets) {
        this.totalTickets = totalTickets;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
