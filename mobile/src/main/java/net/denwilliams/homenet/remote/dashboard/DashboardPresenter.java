package net.denwilliams.homenet.remote.dashboard;

import android.util.Log;

import net.denwilliams.homenet.domain.Callback;
import net.denwilliams.homenet.domain.State;
import net.denwilliams.homenet.domain.query.GetStatesQuery;
import net.denwilliams.homenet.infrastructure.api.GetStatesFromApi;
import net.denwilliams.homenet.infrastructure.api.HomenetApi;
import net.denwilliams.homenet.infrastructure.api.HomenetApiClient;
import net.denwilliams.homenet.infrastructure.api.Switch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DashboardPresenter implements DashboardContract.Presenter, DashboardZoneQuery.Listener {
    private static final String TAG = DashboardPresenter.class.getSimpleName();
    private static final String STATE_SCENE = "scene";
    private static final String PREFIX_LIGHT = "light.";

    private DashboardContract.View mView;
    private HomenetApi mApi;
    private GetStatesQuery mGetStatesQuery;

    public DashboardPresenter() {
        mApi = HomenetApiClient.build();
        mGetStatesQuery = new GetStatesFromApi(mApi);
    }

    @Override
    public void attachView(DashboardContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onStart() {
        mGetStatesQuery.getState(STATE_SCENE, new Callback<State>() {
            @Override
            public void onSuccess(State result) {
                mView.showScenes(result.getAvailable(), result.getState());
            }

            @Override
            public void onFail(Throwable t) {

            }
        });
        new DashboardZoneQuery(mApi, this).execute();
//        getZones();
//        getLightSwitches();

//        mGetStatesQuery.getStates(new Callback<List<State>>() {
//            @Override
//            public void onSuccess(List<State> result) {
//                Log.d(TAG, "RESULTS " + result.size());
//            }
//
//            @Override
//            public void onFail(Throwable t) {
//                Log.e(TAG, "RESULTS " + t.getMessage());
//            }
//        });
    }

    private List<Switch> filterSwitches(List<Switch> switches, String prefix) {
        List<Switch> filtered = new ArrayList<>();

        for (Switch sw: switches) {
            if (sw.getId().startsWith(prefix)) filtered.add(sw);
        }

        return filtered;
    }

    @Override
    public void setScene(String scene) {
        mApi.setState(STATE_SCENE, new HomenetApi.SetState(scene)).enqueue(new retrofit2.Callback<net.denwilliams.homenet.infrastructure.api.State>() {
            @Override
            public void onResponse(Call<net.denwilliams.homenet.infrastructure.api.State> call, Response<net.denwilliams.homenet.infrastructure.api.State> response) {
                onStart();
            }

            @Override
            public void onFailure(Call<net.denwilliams.homenet.infrastructure.api.State> call, Throwable t) {

            }
        });
    }

    @Override
    public void setSwitches(List<String> switches, boolean checked) {
        for (String switchKey: switches) {
            mApi.setSwitch(switchKey, new HomenetApi.SetSwitch(checked)).enqueue(new retrofit2.Callback<Switch>() {
                @Override
                public void onResponse(Call<Switch> call, Response<Switch> response) {
                    Log.d(TAG, "CODE: " +  response.body());
                }
                @Override
                public void onFailure(Call<Switch> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });
        }
    }

    @Override
    public void onComplete(List<DashboardZoneQuery.DashboardZone> zones) {
        mView.showZones(zones);
    }
}
