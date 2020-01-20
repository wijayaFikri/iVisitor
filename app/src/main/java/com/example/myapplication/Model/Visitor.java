package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Visitor {
    @SerializedName("visitor_name")
    private String visitorName;
    @SerializedName("visiting_reason")
    private String visitingReason;
    @SerializedName("is_accepted")
    private String isAccepted;
    @SerializedName("visit_date")
    private String visitDate;
    @SerializedName("clock_in")
    private String clockIn;
    @SerializedName("clock_out")
    private String clockOut;
    @SerializedName("visitor_ktp")
    private String visitorKtp;

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitingReason() {
        return visitingReason;
    }

    public void setVisitingReason(String visitingReason) {
        this.visitingReason = visitingReason;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }

    public String getVisitorKtp() {
        return visitorKtp;
    }

    public void setVisitorKtp(String visitorKtp) {
        this.visitorKtp = visitorKtp;
    }
}
