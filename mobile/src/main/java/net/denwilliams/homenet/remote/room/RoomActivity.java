package net.denwilliams.homenet.remote.room;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.denwilliams.homenet.remote.R;

public class RoomActivity extends AppCompatActivity implements RoomContracts.View {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
    }
}
