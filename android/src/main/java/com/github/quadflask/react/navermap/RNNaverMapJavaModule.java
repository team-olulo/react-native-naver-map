package com.github.quadflask.react.navermap;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNNaverMapJavaModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "RNNaverMapView";

    public RNNaverMapJavaModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void getMetersPerDp(final double latitude, final double zoom, final Promise promise) {
        promise.resolve(RNNaverMapViewContainer.getMetersPerDp(latitude, zoom));
    }
}

