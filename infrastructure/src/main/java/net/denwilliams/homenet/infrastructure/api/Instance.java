package net.denwilliams.homenet.infrastructure.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instance {
    @Expose
    @SerializedName("key")
    private String mKey;

    @Expose
    @SerializedName("class")
    private String mClass;

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("zone")
    private String mZone;

    public String getKey() {
        return mKey;
    }

    public String getClassId() {
        return mClass;
    }

    public String getId() {
        return mId;
    }

    public String getZone() {
        return mZone;
    }
}
