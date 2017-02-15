package net.denwilliams.homenet.remote.dashboard;

import net.denwilliams.homenet.infrastructure.api.HomenetApi;
import net.denwilliams.homenet.infrastructure.api.Instance;
import net.denwilliams.homenet.infrastructure.api.Zone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DashboardZoneQuery {
    private HomenetApi mApi;
    private Listener mListener;
    private List<Zone> mZones;
    private List<Instance> mInstances;

    public DashboardZoneQuery(HomenetApi api, Listener listener) {
        mApi = api;
        mListener = listener;
    }

    public void execute() {
        getZones();
        getInstances();
    }

    private void checkComplete() {
        if (mZones == null || mInstances == null) return;

        mListener.onComplete(getDashboardZones());
    }

    private List<DashboardZone> getDashboardZones() {
        List<DashboardZone> zones = new ArrayList<>();
        for (Zone zone: mZones) {
            DashboardZone dbz = new DashboardZone(zone);

            for (Instance instance: mInstances) {
                if (zone.getId().equals(instance.getZone())) {
                    switch (instance.getClassId()) {
                        case "light":
                            dbz.addLight(instance.getKey());
                            break;
                        case "lock":
                            dbz.addLock(instance.getKey());
                            break;
                    }
                }
            }
            zones.add(dbz);
        }
        return zones;
    }

    private void getZones() {
        mApi.getZones().enqueue(new retrofit2.Callback<List<Zone>>() {
            @Override
            public void onResponse(Call<List<Zone>> call, Response<List<Zone>> response) {
                mZones = response.body();
                checkComplete();
            }

            @Override
            public void onFailure(Call<List<Zone>> call, Throwable t) {

            }
        });
    }

    private void getInstances() {
        mApi.getInstances().enqueue(new retrofit2.Callback<List<Instance>>() {
            @Override
            public void onResponse(Call<List<Instance>> call, Response<List<Instance>> response) {
                mInstances = response.body();
                checkComplete();
            }

            @Override
            public void onFailure(Call<List<Instance>> call, Throwable t) {

            }
        });
    }

//    private void getLightSwitches() {
//        mApi.getSwitches().enqueue(new retrofit2.Callback<List<Switch>>() {
//            @Override
//            public void onResponse(Call<List<Switch>> call, Response<List<Switch>> response) {
//                List<Switch> lights = filterSwitches(response.body(), PREFIX_LIGHT);
//            }
//
//            @Override
//            public void onFailure(Call<List<Switch>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private List<Switch> filterSwitches(List<Switch> switches, String prefix) {
//        List<Switch> filtered = new ArrayList<>();
//
//        for (Switch sw: switches) {
//            if (sw.getId().startsWith(prefix)) filtered.add(sw);
//        }
//
//        return filtered;
//    }

    public interface Listener {
        void onComplete(List<DashboardZone> zones);
    }

    public class DashboardZone {
        private Zone mZone;
        private List<String> mLights;
        private List<String> mLocks;

        public DashboardZone(Zone zone) {
            mZone = zone;
            mLights = new ArrayList<>();
            mLocks = new ArrayList<>();
        }

        public void addLight(String switchId) {
            mLights.add(switchId);
        }

        public void addLock(String switchId) {
            mLocks.add(switchId);
        }

        public Zone getZone() {
            return mZone;
        }

        public List<String> getLights() {
            return mLights;
        }

        public List<String> getLocks() {
            return mLocks;
        }
    }
}
