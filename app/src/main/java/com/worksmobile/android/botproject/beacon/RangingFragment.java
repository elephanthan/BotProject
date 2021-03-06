package com.worksmobile.android.botproject.beacon;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;

import java.util.ArrayList;

public abstract class RangingFragment extends Fragment {
    protected RECOBeaconManager mRecoManager;
    protected ArrayList<RECOBeaconRegion> mRegions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecoManager = RECOBeaconManager.getInstance(getActivity().getApplicationContext(), SettingInfo.SCAN_RECO_ONLY, SettingInfo.ENABLE_BACKGROUND_RANGING_TIMEOUT);
        mRegions = this.generateBeaconRegion();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private ArrayList<RECOBeaconRegion> generateBeaconRegion() {
        ArrayList<RECOBeaconRegion> regions = new ArrayList<RECOBeaconRegion>();

        RECOBeaconRegion recoRegion;

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_COMMUTE_A, SettingInfo.RECO_IDENTIFIER_COMMUTE_A);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_COMMUTE_B, SettingInfo.RECO_IDENTIFIER_COMMUTE_B);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_1, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_1);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_2, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_2);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_3, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_3);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        recoRegion = new RECOBeaconRegion(SettingInfo.RECO_UUID, SettingInfo.RECO_MAJOR_INTRODUCE_LOCATION, SettingInfo.RECO_MINOR_INTRODUCE_LOCATION_4, SettingInfo.RECO_IDENTIFIER_INTRODUCE_LOCATION_4);
        recoRegion.setRegionExpirationTimeMillis(SettingInfo.mRegionExpirationTime);
        regions.add(recoRegion);

        return regions;
    }

    protected abstract void start(ArrayList<RECOBeaconRegion> regions);
    protected abstract void stop(ArrayList<RECOBeaconRegion> regions);
}