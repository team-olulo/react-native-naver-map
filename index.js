var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import React, { Component } from 'react';
import { findNodeHandle, Image, NativeModules, Platform, processColor, requireNativeComponent, UIManager, } from 'react-native';
// import { NativeModules } from 'react-native';
// module.exports = NativeModules.ToastExample;
const RNNaverMapView = requireNativeComponent('RNNaverMapView');
// @ts-ignore
const RNNaverMapViewTexture = Platform.select({
    android: () => requireNativeComponent('RNNaverMapViewTexture'),
    ios: () => RNNaverMapView
})();
const RNNaverMapMarker = requireNativeComponent('RNNaverMapMarker');
const RNNaverMapPathOverlay = requireNativeComponent('RNNaverMapPathOverlay');
const RNNaverMapPolylineOverlay = requireNativeComponent('RNNaverMapPolylineOverlay');
const RNNaverMapCircleOverlay = requireNativeComponent('RNNaverMapCircleOverlay');
const RNNaverMapPolygonOverlay = requireNativeComponent('RNNaverMapPolygonOverlay');
export var TrackingMode;
(function (TrackingMode) {
    TrackingMode[TrackingMode["None"] = 0] = "None";
    TrackingMode[TrackingMode["NoFollow"] = 1] = "NoFollow";
    TrackingMode[TrackingMode["Follow"] = 2] = "Follow";
    TrackingMode[TrackingMode["Face"] = 3] = "Face";
})(TrackingMode || (TrackingMode = {}));
export var MapType;
(function (MapType) {
    MapType[MapType["Basic"] = 0] = "Basic";
    MapType[MapType["Navi"] = 1] = "Navi";
    MapType[MapType["Satellite"] = 2] = "Satellite";
    MapType[MapType["Hybrid"] = 3] = "Hybrid";
    MapType[MapType["Terrain"] = 4] = "Terrain";
})(MapType || (MapType = {}));
export var LayerGroup;
(function (LayerGroup) {
    LayerGroup["LAYER_GROUP_BUILDING"] = "building";
    LayerGroup["LAYER_GROUP_TRANSIT"] = "transit";
    LayerGroup["LAYER_GROUP_BICYCLE"] = "bike";
    LayerGroup["LAYER_GROUP_TRAFFIC"] = "ctt";
    LayerGroup["LAYER_GROUP_CADASTRAL"] = "landparcel";
    LayerGroup["LAYER_GROUP_MOUNTAIN"] = "mountain";
})(LayerGroup || (LayerGroup = {}));
export var Gravity;
(function (Gravity) {
    Gravity[Gravity["NO_GRAVITY"] = 0] = "NO_GRAVITY";
    Gravity[Gravity["AXIS_SPECIFIED"] = 1] = "AXIS_SPECIFIED";
    Gravity[Gravity["AXIS_PULL_BEFORE"] = 2] = "AXIS_PULL_BEFORE";
    Gravity[Gravity["AXIS_PULL_AFTER"] = 4] = "AXIS_PULL_AFTER";
    Gravity[Gravity["AXIS_X_SHIFT"] = 0] = "AXIS_X_SHIFT";
    Gravity[Gravity["AXIS_Y_SHIFT"] = 4] = "AXIS_Y_SHIFT";
    Gravity[Gravity["TOP"] = 48] = "TOP";
    Gravity[Gravity["BOTTOM"] = 80] = "BOTTOM";
    Gravity[Gravity["LEFT"] = 3] = "LEFT";
    Gravity[Gravity["RIGHT"] = 5] = "RIGHT";
    Gravity[Gravity["CENTER_VERTICAL"] = 16] = "CENTER_VERTICAL";
    Gravity[Gravity["CENTER_HORIZONTAL"] = 1] = "CENTER_HORIZONTAL";
})(Gravity || (Gravity = {}));
export var Align;
(function (Align) {
    Align[Align["Center"] = 0] = "Center";
    Align[Align["Left"] = 1] = "Left";
    Align[Align["Right"] = 2] = "Right";
    Align[Align["Top"] = 3] = "Top";
    Align[Align["Bottom"] = 4] = "Bottom";
    Align[Align["TopLeft"] = 5] = "TopLeft";
    Align[Align["TopRight"] = 6] = "TopRight";
    Align[Align["BottomRight"] = 7] = "BottomRight";
    Align[Align["BottomLeft"] = 8] = "BottomLeft";
})(Align || (Align = {}));
export const NaverMapGlobalIndexes = {
    infoWindow: 400000,
    location: 300000,
    marker: 200000,
    arrow: 100000,
    /** 경로선 */
    path: -100000,
    /** 셰이프(폴리곤, 폴리라인, 서클) */
    shape: -200000,
    /** 지상 */
    ground: -300000
};
export var NaverMapMoveReason;
(function (NaverMapMoveReason) {
    NaverMapMoveReason[NaverMapMoveReason["idle"] = 1] = "idle";
    NaverMapMoveReason[NaverMapMoveReason["api"] = 0] = "api";
    NaverMapMoveReason[NaverMapMoveReason["gesture"] = -1] = "gesture";
    NaverMapMoveReason[NaverMapMoveReason["control"] = -2] = "control";
    NaverMapMoveReason[NaverMapMoveReason["tracking"] = -3] = "tracking";
})(NaverMapMoveReason || (NaverMapMoveReason = {}));
const RNNaverMapViewModule = NativeModules.RNNaverMapView;
export default class NaverMapView extends Component {
    constructor() {
        super(...arguments);
        this.resolveRef = (ref) => {
            this.ref = ref;
            this.nodeHandle = findNodeHandle(ref);
        };
        this.getMetersPerDp = (latitude, zoom) => __awaiter(this, void 0, void 0, function* () {
            var _a;
            const _getMetersPerDp = Platform.select({
                android: () => RNNaverMapViewModule === null || RNNaverMapViewModule === void 0 ? void 0 : RNNaverMapViewModule.getMetersPerDp(latitude, zoom),
                ios: () => RNNaverMapViewModule === null || RNNaverMapViewModule === void 0 ? void 0 : RNNaverMapViewModule.getMetersPerDp(this.nodeHandle, latitude, zoom)
            });
            return (_a = _getMetersPerDp === null || _getMetersPerDp === void 0 ? void 0 : _getMetersPerDp()) !== null && _a !== void 0 ? _a : 1;
        });
        this.animateToCoordinate = (coord) => {
            this.dispatchViewManagerCommand('animateToCoordinate', [coord]);
        };
        this.animateToPosition = (coord, zoom) => {
            this.dispatchViewManagerCommand('animateToPosition', [coord, zoom]);
        };
        this.animateToTwoCoordinates = (c1, c2) => {
            this.dispatchViewManagerCommand('animateToTwoCoordinates', [c1, c2]);
        };
        this.animateToCoordinates = (coords, bounds) => {
            this.dispatchViewManagerCommand("animateToCoordinates", [coords, bounds]);
        };
        this.animateToRegion = (region) => {
            this.dispatchViewManagerCommand('animateToRegion', [region]);
        };
        this.setLocationTrackingMode = (mode) => {
            this.dispatchViewManagerCommand('setLocationTrackingMode', [mode]);
        };
        this.setLayerGroupEnabled = (group, enabled) => {
            this.dispatchViewManagerCommand('setLayerGroupEnabled', [group, enabled]);
        };
        this.showsMyLocationButton = (show) => {
            this.dispatchViewManagerCommand('showsMyLocationButton', [show]);
        };
        this.dispatchViewManagerCommand = (command, arg) => {
            // TODO:
            // @ts-ignore
            return Platform.select({
                // TODO:
                // @ts-ignore
                android: () => UIManager.dispatchViewManagerCommand(
                // TODO:
                // @ts-ignore
                this.nodeHandle, 
                // TODO:
                // @ts-ignore
                UIManager.getViewManagerConfig('RNNaverMapView').Commands[command], arg),
                ios: () => NativeModules[`RNNaverMapView`][command](this.nodeHandle, ...arg),
            })();
        };
        this.handleOnCameraChange = (event) => this.props.onCameraChange && this.props.onCameraChange(event.nativeEvent);
        this.handleOnMapClick = (event) => this.props.onMapClick && this.props.onMapClick(event.nativeEvent);
    }
    render() {
        const { onInitialized, center, tilt, bearing, mapPadding, logoMargin, nightMode, useTextureView, } = this.props;
        const ViewClass = useTextureView ? RNNaverMapViewTexture : RNNaverMapView;
        return React.createElement(ViewClass
        // TODO:
        // @ts-ignore
        , Object.assign({ 
            // TODO:
            // @ts-ignore
            ref: this.resolveRef }, this.props, { onInitialized: onInitialized, center: center, mapPadding: mapPadding, logoMargin: logoMargin, tilt: tilt, bearing: bearing, nightMode: nightMode, onCameraChange: this.handleOnCameraChange, onMapClick: this.handleOnMapClick }));
    }
}
export class Marker extends Component {
    render() {
        var _a, _b, _c;
        return React.createElement(RNNaverMapMarker, Object.assign({}, this.props, { 
            // TODO
            // @ts-ignore
            image: getImageUri(this.props.image), caption: this.props.caption && Object.assign(Object.assign({}, this.props.caption), { textSize: (_a = this.props.caption.textSize) !== null && _a !== void 0 ? _a : 12, color: parseColor(this.props.caption.color), haloColor: parseColor(this.props.caption.haloColor) }), subCaption: this.props.subCaption && Object.assign(Object.assign({}, this.props.subCaption), { textSize: (_b = this.props.subCaption.textSize) !== null && _b !== void 0 ? _b : 12, color: parseColor(this.props.subCaption.color), haloColor: parseColor(this.props.subCaption.haloColor) }), info: this.props.info && Object.assign(Object.assign({}, this.props.info), { color: parseColor(this.props.info.color), backgroundColor: parseColor(this.props.info.backgroundColor), style: ((_c = this.props.info) === null || _c === void 0 ? void 0 : _c.style) && Object.assign(Object.assign({}, this.props.info.style), { borderColor: parseColor(this.props.info.style.borderColor) }) }) }));
    }
}
export class Circle extends Component {
    render() {
        return React.createElement(RNNaverMapCircleOverlay, Object.assign({}, this.props));
    }
}
export class Polyline extends Component {
    render() {
        return React.createElement(RNNaverMapPolylineOverlay, Object.assign({}, this.props));
    }
}
export class Polygon extends Component {
    render() {
        // @ts-ignore
        return Platform.select({
            android: () => React.createElement(RNNaverMapPolygonOverlay, Object.assign({}, this.props)),
            ios: () => React.createElement(RNNaverMapPolygonOverlay, Object.assign({}, this.props, { 
                // TODO:
                // @ts-ignore
                coordinates: {
                    exteriorRing: this.props.coordinates,
                    interiorRings: this.props.holes,
                } }))
        })();
    }
}
export class Path extends Component {
    render() {
        return React.createElement(RNNaverMapPathOverlay, Object.assign({}, this.props, { 
            // TODO:
            // @ts-ignore
            pattern: getImageUri(this.props.pattern) }));
    }
}
function getImageUri(src) {
    let imageUri = null;
    if (src) {
        let image = Image.resolveAssetSource(src) || { uri: null };
        imageUri = image.uri;
    }
    return imageUri;
}
function parseColor(color) {
    if (color && Platform.OS === 'ios')
        // TODO:
        // @ts-ignore
        return processColor(color);
    return color;
}
