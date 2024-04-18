//
//  RNNaverMapMarkerManager.m
//
//  Created by flask on 18/04/2019.
//  Copyright © 2019 flask. All rights reserved.
//

#import "RNNaverMapMarkerManager.h"
#import "RNNaverMapMarker.h"

#import <React/RCTUIManager.h>
#import <NMapsMap/NMFCameraCommon.h>
#import <NMapsGeometry/NMGLatLng.h>

#import "RCTConvert+NMFMapView.h"

#define isNull(value) value == nil || [value isKindOfClass:[NSNull class]]

@implementation RNNaverMapMarkerManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  RNNaverMapMarker *marker = [RNNaverMapMarker new];
  marker.bridge = self.bridge;
  return marker;
}

RCT_CUSTOM_VIEW_PROPERTY(caption, NSDictionary, RNNaverMapMarker)
{
  NSDictionary *dic = [RCTConvert NSDictionary:json];
  NSString *text = [RCTConvert NSString:dic[@"text"]];
  CGFloat textSize = [RCTConvert CGFloat:dic[@"textSize"]];
  UIColor *color = [RCTConvert UIColor:dic[@"color"]];
  UIColor *haloColor = [RCTConvert UIColor:dic[@"haloColor"]];
  CGFloat offset = [RCTConvert CGFloat:dic[@"offset"]];
  CGFloat requestedWidth = [RCTConvert CGFloat:dic[@"requestedWidth"]];
  double minZoom = dic[@"minZoom"] ? [RCTConvert double:dic[@"minZoom"]] : NMF_MIN_ZOOM;
  double maxZoom = dic[@"maxZoom"] ? [RCTConvert double:dic[@"maxZoom"]] : NMF_MAX_ZOOM;
  NMFAlignType *align = [RCTConvert NMFAlignType:dic[@"align"]];
  NSMutableArray<NMFAlignType *> *alignTypes = [NSMutableArray arrayWithCapacity: 1];

  [alignTypes addObject: align];
  [view setCaptionText: text];
  [view setCaptionTextSize:textSize];
  [view setCaptionColor:color];
  [view setCaptionHaloColor:haloColor];
  [view setCaptionOffset:offset];
  [view setCaptionRequestedWidth:requestedWidth];
  [view setCaptionMinZoom:minZoom];
  [view setCaptionMaxZoom:maxZoom];
  [view setCaptionAligns:alignTypes];
}

RCT_CUSTOM_VIEW_PROPERTY(subCaption, NSDictionary, RNNaverMapMarker)
{
  NSDictionary *dic = [RCTConvert NSDictionary:json];
  NSString *text = [RCTConvert NSString:dic[@"text"]];
  CGFloat textSize = [RCTConvert CGFloat:dic[@"textSize"]];
  UIColor *color = [RCTConvert UIColor:dic[@"color"]];
  UIColor *haloColor = [RCTConvert UIColor:dic[@"haloColor"]];
  CGFloat requestedWidth = [RCTConvert CGFloat:dic[@"requestedWidth"]];
  double minZoom = dic[@"minZoom"] ? [RCTConvert double:dic[@"minZoom"]] : NMF_MIN_ZOOM;
  double maxZoom = dic[@"maxZoom"] ? [RCTConvert double:dic[@"maxZoom"]] : NMF_MAX_ZOOM;
    
  [view setSubCaptionText: text];
  [view setSubCaptionTextSize:textSize];
  [view setSubCaptionColor:color];
  [view setSubCaptionHaloColor:haloColor];
  [view setSubCaptionRequestedWidth:requestedWidth];
  [view setSubCaptionMinZoom:minZoom];
  [view setSubCaptionMaxZoom:maxZoom];
}

RCT_CUSTOM_VIEW_PROPERTY(info, NSDictionary, RNNaverMapMarker)
{
    BOOL isInfoWindowExist = view.infoWindow != nil;
    
  NSDictionary *dic = [RCTConvert NSDictionary:json];
    
  BOOL visible = [RCTConvert BOOL:dic[@"visible"]];
  NSString *text = [RCTConvert NSString:dic[@"text"]];
  CGFloat textSize = [RCTConvert CGFloat:dic[@"textSize"]];
  UIColor *color = [RCTConvert UIColor:dic[@"color"]];
  BOOL multiline = [RCTConvert BOOL:dic[@"multiline"]];
  UIColor *backgroundColor = [RCTConvert UIColor:dic[@"backgroundColor"]];
    CGFloat backgroundOpacity = [RCTConvert CGFloat: dic[@"backgroundOpacity"]];
  CGFloat maxWidth = [RCTConvert CGFloat:dic[@"maxWidth"]];
  CGFloat paddingHorizental = [RCTConvert CGFloat:dic[@"paddingHorizental"]];
  CGFloat paddingVertical = [RCTConvert CGFloat:dic[@"paddingVertical"]];
  CGFloat cornerRadius = [RCTConvert CGFloat:dic[@"cornerRadius"]];
    NSInteger zIndex = [RCTConvert NSInteger: dic[@"zIndex"]];
    NSInteger globalZIndex = [RCTConvert NSInteger: dic[@"globalZIndex"]];
    NSInteger offset = [RCTConvert NSInteger: dic[@"offset"]];
    NSDictionary *styleDic = [RCTConvert NSDictionary: dic[@"style"]];
    NSInteger borderWidth = [RCTConvert NSInteger: styleDic[@"borderWidth"]];
    UIColor *borderColor = [RCTConvert UIColor: styleDic[@"borderColor"]];
    
    if(!visible && !isInfoWindowExist) {
        return;
    }
    
    RNNaverMapInfoWindow *infoWindow = isInfoWindowExist ? view.infoWindow : [[RNNaverMapInfoWindow alloc] initWithMarker: view];

    BOOL isVisibleChanged = infoWindow.isVisible != visible;
    [infoWindow setIsVisible:visible];
    [infoWindow setText: text];
    [infoWindow setTextSize: textSize];
    [infoWindow setColor: color];
    [infoWindow setMultiline: multiline];
    [infoWindow setBackgroundColor: backgroundColor];
    [infoWindow setBackgroundOpacity: backgroundOpacity];
    [infoWindow setMaxWidth: maxWidth];
    [infoWindow setPaddingHorizental: paddingHorizental];
    [infoWindow setPaddingVertical: paddingVertical];
    [infoWindow setCornerRadius: cornerRadius];
    [infoWindow setOffset: offset];
    [infoWindow setBorderWidth: borderWidth];
    [infoWindow setBorderColor: borderColor];
    
    if (!isNull(dic[@"zIndex"])) {
        [infoWindow setZIndex: zIndex];
    }
    if (!isNull(dic[@"globalZIndex"])) {
        [infoWindow setGlobalZIndex: globalZIndex];
    }
    
  if (isInfoWindowExist) {
      if (isVisibleChanged && visible) {
          [infoWindow open];
      } else if(isVisibleChanged && !visible) {
          [infoWindow close];
      } else {
          [infoWindow refresh];
      }
      return;
  }

  [view setInfoWindow: infoWindow];
}

RCT_EXPORT_VIEW_PROPERTY(coordinate, NMGLatLng)
RCT_EXPORT_VIEW_PROPERTY(width, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(height, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(zIndex, NSInteger)
RCT_EXPORT_VIEW_PROPERTY(hidden, BOOL)
RCT_EXPORT_VIEW_PROPERTY(angle, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(flatEnabled, BOOL)
RCT_EXPORT_VIEW_PROPERTY(iconPerspectiveEnabled, BOOL)
RCT_EXPORT_VIEW_PROPERTY(isHideCollidedSymbols, BOOL)
RCT_EXPORT_VIEW_PROPERTY(isHideCollidedMarkers, BOOL)
RCT_EXPORT_VIEW_PROPERTY(isHideCollidedCaptions, BOOL)
RCT_EXPORT_VIEW_PROPERTY(isForceShowIcon, BOOL)
RCT_EXPORT_VIEW_PROPERTY(rotation, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(image, NSString)
RCT_EXPORT_VIEW_PROPERTY(pinColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(alpha, CGFloat)
RCT_EXPORT_VIEW_PROPERTY(anchor, CGPoint)
RCT_EXPORT_VIEW_PROPERTY(onClick, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(ignoreTouch, BOOL)

@end
