import { ExtendedPosition, AbstractConfigurablePlugin, utils, PluginConstructor, Viewer, TypedEvent } from '@photo-sphere-viewer/core';

/**
 * Definition of keypoints for automatic rotation, can be a position object, a marker id or an configuration object
 */
type AutorotateKeypoint = ExtendedPosition | string | {
    position?: ExtendedPosition;
    /**
     * use the position and tooltip of a marker
     */
    markerId?: string;
    /**
     * pause the animation when reaching this point, will display the tooltip if available
     */
    pause?: number;
    /**
     * optional tooltip
     */
    tooltip?: string | {
        content: string;
        position?: string;
    };
};
type AutorotatePluginConfig = {
    /**
     * Delay after which the automatic rotation will begin, in milliseconds
     * @default 2000
     */
    autostartDelay?: number;
    /**
     * Restarts the automatic rotation if the user is idle for `autostartDelay`.
     * @default true
     */
    autostartOnIdle?: boolean;
    /**
     * Speed of the automatic rotation. Can be a negative value to reverse the rotation.
     * @default '2rpm'
     */
    autorotateSpeed?: string | number;
    /**
     * Vertical angle at which the automatic rotation is performed.
     * @default viewer `defaultPitch`
     */
    autorotatePitch?: number | string;
    /**
     * Zoom level at which the automatic rotation is performed.
     * @default current zoom level
     */
    autorotateZoomLvl?: number;
    /**
     * List of positions to visit
     */
    keypoints?: AutorotateKeypoint[];
    /**
     * Start from the closest keypoint instead of the first keypoint
     * @default true
     */
    startFromClosest?: boolean;
};
type UpdatableAutorotatePluginConfig = Omit<AutorotatePluginConfig, 'keypoints'>;

type ParsedAutorotatePluginConfig = Omit<AutorotatePluginConfig, 'autorotateSpeed' | 'autorotatePitch'> & {
    autorotateSpeed?: number;
    autorotatePitch?: number;
};
/**
 * Adds an automatic rotation of the panorama
 */
declare class AutorotatePlugin extends AbstractConfigurablePlugin<AutorotatePluginConfig, ParsedAutorotatePluginConfig, UpdatableAutorotatePluginConfig, AutorotatePluginEvents> {
    static readonly id = "autorotate";
    static readonly VERSION: string;
    static readonly configParser: utils.ConfigParser<AutorotatePluginConfig, ParsedAutorotatePluginConfig>;
    static readonly readonlyOptions: Array<keyof AutorotatePluginConfig>;
    private readonly state;
    private keypoints;
    private video?;
    private markers?;
    static withConfig(config: AutorotatePluginConfig): [PluginConstructor, any];
    constructor(viewer: Viewer, config: AutorotatePluginConfig);
    /**
     * Changes the keypoints
     * @throws {@link PSVError} if the configuration is invalid
     */
    setKeypoints(keypoints: AutorotateKeypoint[] | null): void;
    /**
     * Checks if the automatic rotation is enabled
     */
    isEnabled(): boolean;
    /**
     * Starts the automatic rotation
     */
    start(): void;
    /**
     * Stops the automatic rotation
     */
    stop(): void;
    /**
     * Starts or stops the automatic rotation
     */
    toggle(): void;
    /**
     * Launches the standard animation
     */
    private __animate;
    /**
     * Resets all the curve variables
     */
    private __reset;
    /**
     * Automatically starts if the delay is reached
     * Performs keypoints animation
     */
    private __beforeRender;
    private __shiftKeypoints;
    private __incrementIdx;
    private __showTooltip;
    private __hideTooltip;
    private __nextPoint;
    private __nextStep;
    private __nextFrame;
    private __findMinIndex;
    private __onKeyPress;
}

/**
 * @event Triggered when the automatic rotation is enabled/disabled
 */
declare class AutorotateEvent extends TypedEvent<AutorotatePlugin> {
    readonly autorotateEnabled: boolean;
    static readonly type = "autorotate";
    type: 'autorotate';
}
type AutorotatePluginEvents = AutorotateEvent;

type events_AutorotateEvent = AutorotateEvent;
declare const events_AutorotateEvent: typeof AutorotateEvent;
type events_AutorotatePluginEvents = AutorotatePluginEvents;
declare namespace events {
  export { events_AutorotateEvent as AutorotateEvent, type events_AutorotatePluginEvents as AutorotatePluginEvents };
}

export { type AutorotateKeypoint, AutorotatePlugin, type AutorotatePluginConfig, type UpdatableAutorotatePluginConfig, events };
