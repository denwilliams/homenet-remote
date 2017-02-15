package net.denwilliams.homenet.infrastructure.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomenetApiClient {
    public static HomenetApi build() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.12:3210/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(HomenetApi.class);
    }
}
