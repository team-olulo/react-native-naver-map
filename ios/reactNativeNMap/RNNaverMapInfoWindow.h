//
//  RNNaverMapInfoWindow.h
//  react-native-nmap
//
//  Created by 이영준 on 2022/12/07.
//

#ifndef RNNaverMapInfoWindow_H
#define RNNaverMapInfoWindow_H

#import <React/RCTBridge.h>
#import <React/RCTComponent.h>
#import <NMapsMap/NMFInfoWindow.h>
#import <NMapsMap/NMFOverlayImage.h>
#import <UIKit/UIKit.h>

#import "RCTConvert+NMFMapView.h"

@class RNNaverMapMarker;

@interface RNNaverMapInfoWindow : NSObject<NMFOverlayImageDataSource>

@property (nonatomic, assign) BOOL isVisible;
@property (nonatomic, copy) NSString *text;
@property (nonatomic, assign) CGFloat textSize;
@property (nonatomic, strong) UIColor *color;
@property (nonatomic, assign) BOOL multiline;
@property (nonatomic, strong) UIColor *backgroundColor;
@property (nonatomic, assign) CGFloat backgroundOpacity;
@property (nonatomic, assign) CGFloat maxWidth;
@property (nonatomic, assign) CGFloat paddingHorizental;
@property (nonatomic, assign) CGFloat paddingVertical;
@property (nonatomic, assign) CGFloat cornerRadius;
@property (nonatomic, assign) NSInteger zIndex;
@property (nonatomic, assign) NSInteger globalZIndex;
@property (nonatomic, assign) CGFloat borderWidth;
@property (nonatomic, strong) UIColor *borderColor;

@property (nonatomic, weak) RNNaverMapMarker *marker;
@property (nonatomic, strong) NMFInfoWindow *realInfoWindow;

@property (nonatomic, strong) UIView *view;
@property (nonatomic, strong) UIView *backgroundView;
@property (nonatomic, strong) UILabel *label;
@property (nonatomic, strong) NSLayoutConstraint *widthContaraint;
@property (nonatomic, strong) NSLayoutConstraint *leftContaraint;
@property (nonatomic, strong) NSLayoutConstraint *rightContaraint;
@property (nonatomic, strong) NSLayoutConstraint *topContaraint;
@property (nonatomic, strong) NSLayoutConstraint *bottomContaraint;

- (void)setIsVisible:(BOOL) value;
- (void)setText:(NSString *) value;
- (void)setTextSize:(CGFloat) value;
- (void)setColor:(UIColor *) value;
- (void)setMultiline:(BOOL) value;
- (void)setBackgroundColor:(UIColor *) value;
- (void)setMaxWidth:(CGFloat) value;
- (void)setPaddingHorizental:(CGFloat) value;
- (void)setPaddingVertical:(CGFloat) value;
- (void)setCornerRadius:(CGFloat) value;
- (void)setZIndex:(NSInteger) value;
- (void)setGlobalZIndex:(NSInteger) value;
- (void)applyZIndex:(NSInteger) value;
- (void)applyZIndexIfNeeded:(NSInteger) value;
- (void)setBorderWidth:(CGFloat) value;
- (void)setBorderColor:(UIColor *) value;

- (instancetype)initWithMarker: (RNNaverMapMarker *) marker;
- (void)open;
- (void)close;
- (void)refresh;
- (void)destroy;

@end

#endif
