package net.denwilliams.homenet.infrastructure.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State implements net.denwilliams.homenet.domain.State {

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("state")
    private String mState;

    @Expose
    @SerializedName("available")
    private String[] mAvailable;

    public State() {
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getState() {
        return mState;
    }

    @Override
    public String[] getAvailable() {
        return mAvailable;
    }
}
