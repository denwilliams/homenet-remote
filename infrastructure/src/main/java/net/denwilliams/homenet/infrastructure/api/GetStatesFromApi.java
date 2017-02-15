package net.denwilliams.homenet.infrastructure.api;

import net.denwilliams.homenet.domain.Callback;
import net.denwilliams.homenet.domain.State;
import net.denwilliams.homenet.domain.query.GetStatesQuery;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GetStatesFromApi implements GetStatesQuery {
    private HomenetApi mHomenetApi;

    public GetStatesFromApi(HomenetApi homenetApi) {
        mHomenetApi = homenetApi;
    }

    @Override
    public void getStates(final Callback<List<State>> callback) {
        mHomenetApi.listStates().enqueue(new retrofit2.Callback<List<net.denwilliams.homenet.infrastructure.api.State>>() {
            @Override
            public void onResponse(Call<List<net.denwilliams.homenet.infrastructure.api.State>> call, Response<List<net.denwilliams.homenet.infrastructure.api.State>> response) {
                List<State> states = GetStatesFromApi.this.<State>mapList(response.body());
                callback.onSuccess(states);
            }

            @Override
            public void onFailure(Call<List<net.denwilliams.homenet.infrastructure.api.State>> call, Throwable t) {
                callback.onFail(t);
            }
        });
    }

    @Override
    public void getState(String stateId, final Callback<State> callback) {
        mHomenetApi.getState(stateId).enqueue(new retrofit2.Callback<net.denwilliams.homenet.infrastructure.api.State>() {
            @Override
            public void onResponse(Call<net.denwilliams.homenet.infrastructure.api.State> call, Response<net.denwilliams.homenet.infrastructure.api.State> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<net.denwilliams.homenet.infrastructure.api.State> call, Throwable t) {
                callback.onFail(t);
            }
        });
    }

    private <T> List<T> mapList(List<? extends T> input) {
        int size = input.size();
        List<T> output = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            output.add(input.get(i));
        }
        return output;
    }
}
