package com.github.quadflask.react.navermap;

import android.content.Context;

import androidx.annotation.NonNull;

import com.naver.maps.map.overlay.Overlay;

public class ClickableRNNaverMapFeature<T extends Overlay> extends RNNaverMapFeature<T> implements Overlay.OnClickListener {
    private RNNaverMapView map;
    protected RNNaverMapView getMap() {
        return map;
    }

    public ClickableRNNaverMapFeature(EventEmittable emitter, Context context) {
        super(emitter, context);
    }

    @Override
    public void addToMap(RNNaverMapView map) {
        super.addToMap(map);
        feature.setOnClickListener(this);
        this.onFeatureAppended();
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        emitEvent("onClick", null);
        return true;
    }

    public void onFeatureAppended() {
        //
    }
}
