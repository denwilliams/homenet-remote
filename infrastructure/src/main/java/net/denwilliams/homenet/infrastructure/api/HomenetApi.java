package net.denwilliams.homenet.infrastructure.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HomenetApi {
    @GET("states")
    Call<List<State>> listStates();

    @GET("states/{state}")
    Call<State> getState(@Path("state") String stateKey);

    @PUT("states/{state}")
    Call<State> setState(@Path("state") String stateKey, @Body SetState request);

    @PUT("switches/{switchId}")
    Call<Switch> setSwitch(@Path("switchId") String switchId, @Body SetSwitch request);

    @GET("switches")
    Call<List<Switch>> getSwitches();

    @GET("zones")
    Call<List<Zone>> getZones();

    @GET("instances")
    Call<List<Instance>> getInstances();

    class SetState {
        public SetState() {
        }

        public SetState(String state) {
            this.state = state;
        }

        public String state;
    }

    class SetSwitch {
        public SetSwitch() {
        }

        public SetSwitch(Boolean value) {
            this.value = value;
        }

        public Boolean value;
    }
}

