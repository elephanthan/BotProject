package com.worksmobile.android.botproject.beacon;

public class WorksBeacon {
    private String uuid;
    private int major;
    private int minor;
    private int beaconSignal;
    private double distance;

    public WorksBeacon(String uuid, int major, int minor, int beaconSignal, double distance) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.beaconSignal = beaconSignal;
        this.distance = Math.round(distance*100d) / 100d;;
    }
}
