package com.github.quadflask.react.navermap;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.naver.maps.map.overlay.InfoWindow;

public class RNNaverMapInfoWindow {
    boolean isVisible = false;
    String text;
    Double textSize;
    Integer color;
    boolean multiline = false;
    Integer backgroundColor;
    Double backgroundOpacity = 1d;
    Integer maxWidth;
    Integer paddingHorizental = 0;
    Integer paddingVertical = 0;
    Integer cornerRadius = 8;
    Integer zIndex;
    Integer globalZIndex;
    Integer borderColor;
    Integer borderWidth;

    InfoWindow.ViewAdapter adapter;
    TextView textView;
    RelativeLayout textViewContainer;

    private InfoWindow infoWindow;
    private RNNaverMapMarker marker;

    public RNNaverMapInfoWindow() {
        this.infoWindow = new InfoWindow();
    }

    public RNNaverMapInfoWindow(RNNaverMapMarker marker) {
        this.marker = marker;
        this.infoWindow = new InfoWindow();
        this.infoWindow.setOnClickListener(marker);

        this.createAdapter();
    }

    public RNNaverMapInfoWindow setIsVisible(boolean value) {
        this.isVisible = value;
        return this;
    }

    public RNNaverMapInfoWindow setText(String value) {
        if (value == null) return this;

        this.text = value;
        return this;
    }

    public RNNaverMapInfoWindow setTextSize(Double value) {
        if (value == null) return this;

        this.textSize = value;
        return this;
    }

    public RNNaverMapInfoWindow setColor(Integer value) {
        if (value == null) return this;

        this.color = value;
        return this;
    }

    public RNNaverMapInfoWindow setMultiline(boolean value) {
        this.multiline = value;
        return this;
    }

    public RNNaverMapInfoWindow setBackgroundColor(Integer value) {
        if (value == null) return this;

        this.backgroundColor = value;
        return this;
    }

    public RNNaverMapInfoWindow setBackgroundOpacity(Double value) {
        if (value == null) return this;

        this.backgroundOpacity = value;
        return this;
    }

    public RNNaverMapInfoWindow setMaxWidth(Integer value) {
        if (value == null) return this;

        this.maxWidth = value;
        return this;
    }

    public RNNaverMapInfoWindow setPaddingHorizental(Integer value) {
        if (value == null) return this;

        this.paddingHorizental = value;
        return this;
    }

    public RNNaverMapInfoWindow setPaddingVertical(Integer value) {
        if (value == null) return this;

        this.paddingVertical = value;
        return this;
    }

    public RNNaverMapInfoWindow setCornerRadius(Integer value) {
        if (value == null) return this;

        this.cornerRadius = value;
        return this;
    }

    public RNNaverMapInfoWindow setZIndex(Integer value) {
        if (value == null) return this;

        this.zIndex = value;
        this.applyZIndex(value);
        return this;
    }

    public RNNaverMapInfoWindow setGlobalZIndex(Integer value) {
        if (value == null) return this;

        this.globalZIndex = value;
        this.infoWindow.setGlobalZIndex(value);
        return this;
    }

    public void applyZIndex(int value) {
        this.infoWindow.setZIndex(value);
    }

    public RNNaverMapInfoWindow setBorderColor(Integer value) {
        if (value == null) return this;

        this.borderColor = value;
        return this;
    }

    public RNNaverMapInfoWindow setBorderWidth(Integer value) {
        if (value == null) return this;

        this.borderWidth = value;
        return this;
    }

    public void open() {
        if (this.marker == null) return;

        this.infoWindow.open(this.marker.feature);
    }

    public void refresh() {
        this.infoWindow.invalidate();
    }

    public  void close() {
        this.infoWindow.close();
    }

    public void createAdapter() {
        if (this.marker == null) return;

        this.adapter = new InfoWindow.ViewAdapter() {
            @NonNull
            @Override
            public View getView(@NonNull InfoWindow infoWindow) {
                if (textViewContainer == null) {
                    textViewContainer = new RelativeLayout(marker.getContext());
                }

                if (textView == null) {
                    textView = new TextView(marker.getContext());
                    textViewContainer.addView(textView);
                }

                float pixelRatio = marker.getContext().getResources().getDisplayMetrics().density;

                if (text != null) textView.setText(text);
                if (textSize != null) textView.setTextSize(textSize.floatValue());
                if (color != null) textView.setTextColor(color);
                textView.setSingleLine(!multiline);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                if (maxWidth != null) {
                    Double maxWidthWithPadding = maxWidth.doubleValue() + paddingHorizental * 2;
                    textView.setMaxWidth(((Double)(maxWidthWithPadding * pixelRatio)).intValue());
                }
                int pixelsInPaddingHorizental = ((Double)(paddingHorizental.doubleValue() * pixelRatio)).intValue();
                int pixelsInPaddingVertical = ((Double)(paddingVertical.doubleValue() * pixelRatio)).intValue();
                textViewContainer.setPadding(pixelsInPaddingHorizental, pixelsInPaddingVertical, pixelsInPaddingHorizental, pixelsInPaddingVertical);

                int pixelsInRadius = ((Double)(cornerRadius.doubleValue() * pixelRatio)).intValue();

                if(backgroundColor != null) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(backgroundColor);
                    gd.setCornerRadius(pixelsInRadius);

                    if (borderWidth != null && borderColor != null) {
                        gd.setStroke(borderWidth, borderColor);
                    }
                    gd.setAlpha((int)(backgroundOpacity * 255));
                    textViewContainer.setBackground(gd);
                }

                return  textViewContainer;
            }
        };

        this.infoWindow.setAdapter(this.adapter);
    }

    public void destroy() {
        this.marker = null;
        if (this.infoWindow != null) return;
        this.infoWindow.setOnClickListener(null);
        this.infoWindow.setAdapter(null);
        this.infoWindow = null;
    }
}
