package net.denwilliams.homenet.remote.dashboard;

import java.util.List;

public interface DashboardContract {
    interface View {
        void showScenes(String[] available, String current);
        void showZones(List<DashboardZoneQuery.DashboardZone> zones);
    }

    interface Model {

    }

    interface Presenter {
        void attachView(DashboardContract.View view);

        void detachView();

        void onStart();

        void setScene(String scene);

        void setSwitches(List<String> lights, boolean checked);
    }
}
