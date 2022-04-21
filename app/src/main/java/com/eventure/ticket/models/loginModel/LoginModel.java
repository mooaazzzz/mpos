
package com.eventure.ticket.models.loginModel;

import java.util.List;

import com.eventure.ticket.models.PagesModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("login")
    @Expose
    private List<LoginData> login = null;
    @SerializedName("age")
    @Expose
    private List<Age> age = null;
    @SerializedName("gender")
    @Expose
    private List<Gender> gender = null;
    @SerializedName("natioanlity")
    @Expose
    private List<Natioanlity> natioanlity = null;
    @SerializedName("paySouce")
    @Expose
    private List<PaySouce> paySouce = null;
    @SerializedName("pages")
    @Expose
    private List<PagesModel> pagesModel = null;

    public List<LoginData> getLogin() {
        return login;
    }

    public void setLogin(List<LoginData> login) {
        this.login = login;
    }

    public List<Age> getAge() {
        return age;
    }

    public void setAge(List<Age> age) {
        this.age = age;
    }

    public List<Gender> getGender() {
        return gender;
    }

    public void setGender(List<Gender> gender) {
        this.gender = gender;
    }

    public List<Natioanlity> getNatioanlity() {
        return natioanlity;
    }

    public void setNatioanlity(List<Natioanlity> natioanlity) {
        this.natioanlity = natioanlity;
    }

    public List<PaySouce> getPaySouce() {
        return paySouce;
    }

    public void setPaySouce(List<PaySouce> paySouce) {
        this.paySouce = paySouce;
    }

    public List<PagesModel> getPagesModel() {
        return pagesModel;
    }

    public void setPagesModel(List<PagesModel> pagesModel) {
        this.pagesModel = pagesModel;
    }
}
