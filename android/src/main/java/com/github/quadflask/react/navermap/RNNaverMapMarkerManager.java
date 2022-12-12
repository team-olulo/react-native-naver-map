package com.github.quadflask.react.navermap;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.InfoWindow;

import static com.github.quadflask.react.navermap.ReactUtil.parseAlign;
import static com.github.quadflask.react.navermap.ReactUtil.parseColorString;
import static com.github.quadflask.react.navermap.ReactUtil.toNaverLatLng;

public class RNNaverMapMarkerManager extends EventEmittableViewGroupManager<RNNaverMapMarker> {
    private static final Align DEFAULT_CAPTION_ALIGN = Align.Bottom;

    private final DisplayMetrics metrics;

    private static final int FUNC_OPEN_INFO_WINDOW = 1;

    public RNNaverMapMarkerManager(ReactApplicationContext reactContext) {
        super(reactContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            metrics = new DisplayMetrics();
            ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay()
                    .getRealMetrics(metrics);
        } else {
            metrics = reactContext.getResources().getDisplayMetrics();
        }
    }

    @Override
    String[] getEventNames() {
        return new String[]{
                "onClick"
        };
    }

    @NonNull
    @Override
    public String getName() {
        return "RNNaverMapMarker";
    }

    @NonNull
    @Override
    protected RNNaverMapMarker createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new RNNaverMapMarker(this, reactContext);
    }

    @ReactProp(name = "coordinate")
    public void setCoordinate(RNNaverMapMarker view, ReadableMap map) {
        view.setCoordinate(toNaverLatLng(map));
    }

    @ReactProp(name = "anchor")
    public void setAnchor(RNNaverMapMarker view, ReadableMap map) {
        // should default to (0.5, 1) (bottom middle)
        float x = map != null && map.hasKey("x") ? (float) map.getDouble("x") : 0.5f;
        float y = map != null && map.hasKey("y") ? (float) map.getDouble("y") : 1.0f;
        view.setAnchor(x, y);
    }

    @ReactProp(name = "image")
    public void setImage(RNNaverMapMarker view, @Nullable String source) {
        view.setImage(source);
    }

    @ReactProp(name = "pinColor", defaultInt = Color.RED, customType = "Color")
    public void setPinColor(RNNaverMapMarker view, int pinColor) {
        view.setIconTintColor(pinColor);
    }

    @ReactProp(name = "rotation", defaultFloat = 0.0f)
    public void setMarkerRotation(RNNaverMapMarker view, float rotation) {
        view.setRotation(rotation);
    }

    @ReactProp(name = "flat", defaultBoolean = false)
    public void setFlat(RNNaverMapMarker view, boolean flat) {
        view.setFlat(flat);
    }

    @ReactProp(name = "width", defaultFloat = 64)
    public void setWidth(RNNaverMapMarker view, float width) {
        int widthInScreenPx = Math.round(metrics.density * width);
        view.setWidth(widthInScreenPx);
    }

    @ReactProp(name = "height", defaultFloat = 64)
    public void setHeight(RNNaverMapMarker view, float height) {
        int heightInScreenPx = Math.round(metrics.density * height);
        view.setHeight(heightInScreenPx);
    }

    @ReactProp(name = "animated", defaultBoolean = false)
    public void setAnimated(RNNaverMapMarker view, boolean animated) {
        view.setAnimated(animated);
    }

    @ReactProp(name = "easing", defaultInt = -1)
    public void setEasing(RNNaverMapMarker view, int easingFunction) {
        view.setEasing(easingFunction);
    }

    @ReactProp(name = "duration", defaultInt = 500)
    public void setDuration(RNNaverMapMarker view, int duration) {
        view.setDuration(duration);
    }

    @ReactProp(name = "alpha", defaultFloat = 1f)
    public void setAlpha(RNNaverMapMarker view, float alpha) {
        view.setAlpha(alpha);
    }

    @ReactProp(name = "zIndex", defaultInt = 0)
    public void setZIndex(RNNaverMapMarker view, int zIndex) {
        view.setZIndex(zIndex);
    }

    @ReactProp(name = "caption")
    public void setCaption(RNNaverMapMarker view, ReadableMap map) {
        if (map == null || !map.hasKey("text")) {
            view.removeCaption();
            return;
        }
        String text = map.getString("text");
        int textSize = map.hasKey("textSize") ? map.getInt("textSize") : 16;
        int color = map.hasKey("color") ? parseColorString(map.getString("color")) : Color.BLACK;
        int haloColor = map.hasKey("haloColor") ? parseColorString(map.getString("haloColor")) : Color.WHITE;
        Align align = map.hasKey("align") ? parseAlign(map.getInt("align")) : DEFAULT_CAPTION_ALIGN;

        view.setCaption(text, textSize, color, haloColor, align);
    }

    @ReactProp(name = "info")
    public void setInfo(RNNaverMapMarker view, ReadableMap map) {
        if (map == null || !map.hasKey("text")) {
            view.removeInfoWindow();
            return;
        }

        boolean isVisible = map.hasKey("visible") ? map.getBoolean("visible") : true;
        String text = map.hasKey("text") ? map.getString("text") : null;
        Double textSize = map.hasKey("textSize") ? map.getDouble("textSize") : null;
        int color = map.hasKey("color") ? parseColorString(map.getString("color")) : null;
        boolean multiline = map.hasKey("multiline") ? map.getBoolean("multiline") : false;
        int maxWidth = map.hasKey("maxWidth") ? map.getInt("maxWidth") : null;
        int backgroundColor = map.hasKey("backgroundColor") ? parseColorString(map.getString("backgroundColor")) : null;
        int paddingHorizental = map.hasKey("paddingHorizental") ? map.getInt("paddingHorizental") : 0;
        int paddingVertical = map.hasKey("paddingVertical") ? map.getInt("paddingVertical") : 0;
        int cornerRadius = map.hasKey("cornerRadius") ? map.getInt("cornerRadius") : 8;

        RNNaverMapInfoWindow infoWindow = view.getInfoWindow();
        boolean hasInfoWindow = false;
        if (infoWindow == null) {
            infoWindow = new RNNaverMapInfoWindow(view);
        } else {
            hasInfoWindow = true;
        }

        infoWindow.setIsVisible(isVisible)
                .setText(text)
                .setTextSize(textSize)
                .setColor(color)
                .setMultiline(multiline)
                .setBackgroundColor(backgroundColor)
                .setMaxWidth(maxWidth)
                .setPaddingHorizental(paddingHorizental)
                .setPaddingVertical(paddingVertical)
                .setCornerRadius(cornerRadius);

        if (hasInfoWindow) {
            if (isVisible && !infoWindow.isVisible) {
                infoWindow.open();
            } else if (infoWindow.isVisible && !isVisible) {
                infoWindow.close();
            } else {
                infoWindow.refresh();
            }
            return;
        }

        view.setInfoWindow(infoWindow);
    }

    @Override
    public java.util.Map<String, Integer> getCommandsMap() {
        return MapBuilder.<String, Integer>builder()
                .put("openInfo", FUNC_OPEN_INFO_WINDOW)
                .build();
    }

    @Override
    public void receiveCommand(@NonNull RNNaverMapMarker view, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case FUNC_OPEN_INFO_WINDOW:
                view.openInfo();
                break;
        }
    }

    @Override
    public void addView(RNNaverMapMarker parent, View child, int index) {
        parent.setCustomView(child, index);
    }

    @Override
    public void removeView(RNNaverMapMarker parent, View view) {
        parent.removeInfoWindow();
        parent.removeCustomView(view);
    }
}
