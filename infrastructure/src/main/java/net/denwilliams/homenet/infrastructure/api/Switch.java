package net.denwilliams.homenet.infrastructure.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Switch implements net.denwilliams.homenet.domain.Switch {
    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("value")
    private String mValue;

    public Switch() {
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getValue() {
        return mValue;
    }
}
