package com.sysirohub.academicapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllSlot {

    @SerializedName("slot_id")
    @Expose
    private String slotId;
    @SerializedName("display_time")
    @Expose
    private String displayTime;
    @SerializedName("s_start")
    @Expose
    private String sStart;
    @SerializedName("s_end")
    @Expose
    private String sEnd;

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getsStart() {
        return sStart;
    }

    public void setsStart(String sStart) {
        this.sStart = sStart;
    }

    public String getsEnd() {
        return sEnd;
    }

    public void setsEnd(String sEnd) {
        this.sEnd = sEnd;
    }

}