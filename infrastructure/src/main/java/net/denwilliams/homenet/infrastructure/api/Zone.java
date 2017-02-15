package net.denwilliams.homenet.infrastructure.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zone {
    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("presence")
    private boolean mPresence;

    @Expose
    @SerializedName("parent")
    private String mParent;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean isPresence() {
        return mPresence;
    }

    public String getParent() {
        return mParent;
    }
}
