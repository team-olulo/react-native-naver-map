//
//  RNNaverMapInfoWindow.m
//  react-native-nmap
//
//  Created by 이영준 on 2022/12/07.
//

#import "RNNaverMapInfoWindow.h"
#import "RNNaverMapMarker.h"

#define DEFAULT_INFO_WINDOW_WIDTH 100
#define DEFAULT_INFO_WINDOW_HEIGHT 44
#define DEFAULT_INFO_WINDOW_OFFSET -10

@implementation RNNaverMapInfoWindow

- (instancetype)init
{
    self = [super init];
    _realInfoWindow = [NMFInfoWindow new];
    [self createView];
    _realInfoWindow.dataSource = self;
    _zIndex = NSIntegerMin;
    _globalZIndex = NSIntegerMin;
    _borderWidth = 0;
    _offset = 0;
    
    return self;
}

- (instancetype)initWithMarker: (RNNaverMapMarker *)marker
{
    self = [self init];
    _marker = marker;
    [self setTouchHandler];
    
    return self;
}

- (void)setTouchHandler
{
    __block RNNaverMapMarker *marker = _marker;

    _realInfoWindow.touchHandler = ^BOOL(NMFOverlay *overlay) {
        if (!marker || marker.onClick == NULL)
            return false;

        marker.onClick(@{});

        return true;
    };
}

- (void)setIsVisible:(BOOL)value {
    _isVisible = value;
}

- (void)setText:(NSString *)value {
    _text = value;
}

- (void)setTextSize:(CGFloat)value {
    _textSize = value;
}

- (void)setColor:(UIColor *)value {
    _color = value;
}

- (void)setMultiline:(BOOL)value {
    _multiline = value;
}

- (void)setBackgroundColor:(UIColor *)value {
    _backgroundColor = value;
}

- (void)setBackgroundOpacity:(CGFloat)value {
    _backgroundOpacity = value;
}

- (void)setMaxWidth:(CGFloat)value {
    _maxWidth = value;
}

- (void)setPaddingHorizental:(CGFloat)value {
    _paddingHorizental = value;
}

- (void)setPaddingVertical:(CGFloat)value {
    _paddingVertical = value;
}

- (void)setCornerRadius:(CGFloat)value {
    _cornerRadius = value;
}

- (void)setZIndex:(NSInteger)value {
    _zIndex = value;
    [self applyZIndex: value];
}


- (void)setGlobalZIndex:(NSInteger)value {
    _globalZIndex = value;
    [_realInfoWindow setGlobalZIndex: value];
}

- (void)applyZIndex:(NSInteger)value {
    [_realInfoWindow setZIndex: value];
}

- (void)applyZIndexIfNeeded:(NSInteger)value {
    if (value == NSIntegerMin) return;
        
    [self applyZIndex: value];
}

- (void)setOffset:(NSInteger)value {
    _offset = value;
    [self applyOffset: value];
}

- (void)applyOffset:(NSInteger)value {
    _realInfoWindow.offsetY = DEFAULT_INFO_WINDOW_OFFSET + value;
}

- (void)applyOffsetIfNeeded {
    [self applyOffset: _offset];
}

- (void)setBorderWidth:(CGFloat)value {
    _borderWidth = value;
}

- (void)setBorderColor:(UIColor *)value {
    _borderColor = value;
}

- (void)open {
    if (_marker) {
        [_realInfoWindow openWithMarker:_marker.realMarker];
        [self applyOffsetIfNeeded];
    }
}

- (void)close {
    [_realInfoWindow close];
}

- (void)refresh {
    [_realInfoWindow invalidate];
}

- (void)createView {
    if (_view) return;
    
    _view = [[UIView alloc] initWithFrame: CGRectMake(0, 0, 100, 44)];
    _view.translatesAutoresizingMaskIntoConstraints = false;
    _view.clipsToBounds = true;
    _view.backgroundColor = [UIColor colorWithWhite: 0 alpha: 0];
    
    _backgroundView = [[UIView alloc] initWithFrame: CGRectMake(_borderWidth, _borderWidth, DEFAULT_INFO_WINDOW_WIDTH - _borderWidth * 2, DEFAULT_INFO_WINDOW_HEIGHT - _borderWidth * 2)];
    _backgroundView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    _backgroundView.clipsToBounds = true;
    [_view addSubview:_backgroundView];
    
    _label = [[UILabel alloc] initWithFrame: CGRectZero];
    _label.translatesAutoresizingMaskIntoConstraints = false;
    [_view addSubview:_label];
}

- (void)updateView {
    if (!_view) return;
    
    _view.opaque = false;
    [_backgroundView setBackgroundColor: _backgroundColor];
    RCTLogTrace(@"InfoWindow updateView backgroundColor %@", _backgroundColor.description);
    [_backgroundView setAlpha: _backgroundOpacity];
    _backgroundView.layer.cornerRadius = _cornerRadius;
    
    _backgroundView.layer.borderWidth = _borderWidth;
    RCTLogTrace(@"InfoWindow updateView borderWidth %f", _borderWidth);
    
    if (_borderColor) {
        _backgroundView.layer.borderColor = _borderColor.CGColor;
    } else {
        _backgroundView.layer.borderColor = nil;
    }
    RCTLogTrace(@"InfoWindow updateView borderWidth %@", _borderColor);
    
    _label.text = _text;
    _label.font = [_label.font fontWithSize:_textSize];
    _label.textColor = _color;
    _label.numberOfLines = _multiline ? 0 : 1;
    _view.layer.cornerRadius = _cornerRadius;
    
    if (!_widthContaraint) {
        _widthContaraint = [_view.widthAnchor constraintLessThanOrEqualToConstant: _maxWidth];
        [_widthContaraint setActive: true];
    } else {
        _widthContaraint.constant = _maxWidth;
    }
    
    if (!_leftContaraint) {
        _leftContaraint = [_label.leftAnchor constraintEqualToAnchor: _view.leftAnchor constant: _paddingHorizental];
        [_leftContaraint setActive: true];
    }else {
        _leftContaraint.constant = _paddingHorizental;
    }
    
    if (!_rightContaraint) {
        _rightContaraint = [_label.rightAnchor constraintEqualToAnchor: _view.rightAnchor constant: -_paddingHorizental];
        [_rightContaraint setActive: true];
    }else {
        _rightContaraint.constant = _paddingHorizental;
    }
    
    if (!_topContaraint) {
        _topContaraint = [_label.topAnchor constraintEqualToAnchor: _view.topAnchor constant: _paddingVertical];
        [_topContaraint setActive: true];
    }else {
        _topContaraint.constant = _paddingVertical;
    }
    
    if (!_bottomContaraint) {
        _bottomContaraint = [_label.bottomAnchor constraintEqualToAnchor: _view.bottomAnchor constant: -_paddingVertical];
        [_bottomContaraint setActive: true];
    }else {
        _bottomContaraint.constant = _paddingVertical;
    }
    
    [_view layoutIfNeeded];
    [_label layoutIfNeeded];
}

- (void)destroy {
    _marker = nil;
    _realInfoWindow.touchHandler = nil;
    _realInfoWindow = nil;
}

- (nonnull UIView *)viewWithOverlay:(nonnull NMFOverlay *)overlay {
    [self updateView];
    
    return _view;
}

@end
