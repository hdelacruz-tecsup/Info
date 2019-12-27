package com.hdelacruz.info.services;

import com.hdelacruz.info.models.Info;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    String API_BASE_URL = "http://192.168.43.38:8080";

    @GET("/info")
    Call<List<Info>> findAll();


    @FormUrlEncoded
    @POST("/info")
    Call<Info> createInfo(@Field("dia_if") String dia_if,
                                  @Field("area_if") String area_if,
                                  @Field("trabajador_if")String trabajador_if,
                                  @Field("descripcion_if")String descripcion_if,
                                  @Field("fecha_ini") String fecha_ini,
                                  @Field("fecha_fin") String fecha_fin);

    @Multipart
    @POST("/info")
    Call<Info> createInfo(@Part("dia_if") RequestBody dia_if,
                          @Part("area_if")RequestBody area_if,
                          @Part("trabajador_if") RequestBody trabajador_if,
                          @Part("descripcion_if") RequestBody descripcion_if,
                          @Part("fecha_ini") RequestBody fecha_ini,
                          @Part("fecha_fin") RequestBody fecha_fin,
                          @Part MultipartBody.Part imagen);
}
