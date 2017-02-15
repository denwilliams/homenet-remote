package net.denwilliams.homenet.remote.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.denwilliams.homenet.infrastructure.api.HomenetApi;
import net.denwilliams.homenet.infrastructure.api.HomenetApiClient;
import net.denwilliams.homenet.remote.R;

import java.util.List;

// TODO: air conditioners, diagrams, power switches, power metering, sound, button

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private static final int LAYOUT_ID = R.layout.activity_dashboard;

    private DashboardContract.Presenter mDashboardPresenter;
    private LinearLayout mScenes;
    private RecyclerView mZones;

    public DashboardActivity() {
        mDashboardPresenter = new DashboardPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mScenes = (LinearLayout) findViewById(R.id.lay_scenes);
        mZones = (RecyclerView) findViewById(R.id.rv_zones);
        mZones.setLayoutManager(new LinearLayoutManager(this));
        mZones.setHasFixedSize(true);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        HomenetApi api = HomenetApiClient.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDashboardPresenter.attachView(this);
        mDashboardPresenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDashboardPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showScenes(String[] available, String current) {
        mScenes.removeAllViews();
        Context context = new ContextThemeWrapper(this, R.style.SceneButton);
        for (String scene : available) {
            Button btn = new Button(context);
            btn.setText(scene);
            if (scene.equals(current)) btn.setTextColor(getResources().getColor(R.color.colorAccent));
            mScenes.addView(btn);
            btn.setOnClickListener(getSceneClickController(scene));
        }
    }

    @Override
    public void showZones(List<DashboardZoneQuery.DashboardZone> zones) {
        mZones.setAdapter(new ZonesAdapter(zones));
    }

    private View.OnClickListener getSceneClickController(final String scene) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDashboardPresenter.setScene(scene);
            }
        };
    }

    private class ZoneViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private TextView mName;
        private ToggleButton mLightsToggle;
        private ToggleButton mLocksToggle;
        private DashboardZoneQuery.DashboardZone mZone;

        public ZoneViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.txt_name);
            mLightsToggle = (ToggleButton) itemView.findViewById(R.id.tgl_lights);
            mLocksToggle = (ToggleButton) itemView.findViewById(R.id.tgl_lock);

            mLocksToggle.setOnCheckedChangeListener(this);
            mLightsToggle.setOnCheckedChangeListener(this);
        }

        public void bind(DashboardZoneQuery.DashboardZone zone) {
            mZone = zone;
            mName.setText(zone.getZone().getName());
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            if (compoundButton == mLightsToggle) {
                mDashboardPresenter.setSwitches(mZone.getLights(), checked);
                Log.d(TAG, "mLightsToggle" + checked);
            } else if (compoundButton == mLocksToggle) {
                mDashboardPresenter.setSwitches(mZone.getLocks(), checked);
                Log.d(TAG, "mLocksToggle" + checked);
            }
        }
    }

    private class ZonesAdapter extends RecyclerView.Adapter<ZoneViewHolder> {
        private List<DashboardZoneQuery.DashboardZone> mZones;

        public ZonesAdapter(List<DashboardZoneQuery.DashboardZone> zones) {
            mZones = zones;
        }

        @Override
        public ZoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_zone, parent, false);
            return new ZoneViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ZoneViewHolder holder, int position) {
            DashboardZoneQuery.DashboardZone zone = mZones.get(position);
            holder.bind(zone);
        }

        @Override
        public int getItemCount() {
            return mZones.size();
        }
    }


}
