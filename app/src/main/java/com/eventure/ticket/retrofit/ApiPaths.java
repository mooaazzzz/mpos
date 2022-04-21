package com.eventure.ticket.retrofit;


import com.eventure.ticket.models.DataModel;
import com.eventure.ticket.models.DayClosing;
import com.eventure.ticket.models.GetRideDetailsModel;
import com.eventure.ticket.models.Search;
import com.eventure.ticket.models.loginModel.LoginModel;
import com.eventure.ticket.models.postTransaction.PostTransaction;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiPaths {

    String tail = "";

//    @FormUrlEncoded
//    @POST(tail + "Account/Register")
//    Call<Register> register(@Field("Name") String Name, @Field("Type") String Type, @Field("Email") String email, @Field("Password") String password, @Field("ConfirmPassword") String confirmPassword, @Field("ReferralCode") String referralCode, @Field("Phone") String Phone);
//
//    @GET(tail + "WorkoutRequest/LoadPaymentModeAttendees?")
//    Call<LoadPaymentModeAttendees> loadPaymentModeAttendees(@Header("Authorization") String accessToken, @Query("WorkoutRequestId") int workoutRequestId, @Query("WorkoutRequestOfferId") int workoutRequestOfferId);

    @GET("User/GetLogin/{username}/{password}")
    Call<LoginModel> login(@Path(value = "username", encoded = true) String username, @Path(value = "password", encoded = true) String password);

    @POST("Ride/MultipleTransaction?")
//    Call<List<DataModel>> postTransaction(@Query("DurationSlotId") int DurationSlotId, @Query("LoginId") int LoginId,  @Query("TotalPaid") int TotalPaid, @Query("PaymentSource") String PaymentSource, @Query("CustomerPhone") String CustomerPhone, @Query("CustomerName") String CustomerName, @Query("nooftickets") int nooftickets, @Query("Gender") String Gender, @Query("Nationality") String Nationality, @Query("Age") String Age, @Query("CustomerEmail") String customerEmail);
    Call<List<DataModel>> postTransaction(@Query("JsonObject") String jsonObject);
//    Call<List<DataModel>> postTransaction(@Body JsonObject jsonObject);
//    Call<PostTransaction> postTransaction(@Body JsonObject jsonObject);


    @POST("Ride/Dclosing?")
    Call<List<DayClosing>> dayClosing(@Query("RideId") int rideId, @Query("LoginId") int loginId, @Query("Petty_Cash") int petty_Cash, @Query("Cash_TotalPaid") int cash_TotalPaid, @Query("Card_TotalPaid") int card_TotalPaid, @Query("Debit_TotalPaid") int Debit_TotalPaid, @Query("CloseDate") String CloseDate, @Query("Reprint") int Reprint,@Query("Location") String location,@Query("CashReceipt") String CashReceipt);

    @GET("Ride/GetRideDetails")
    Call<List<GetRideDetailsModel>> getRideDetail();

    @GET("User/GetSearch/{search}")
    Call<List<Search>> search(@Path(value = "search", encoded = true) String username);

}
