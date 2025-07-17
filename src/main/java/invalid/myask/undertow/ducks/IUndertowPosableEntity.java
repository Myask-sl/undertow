package invalid.myask.undertow.ducks;

import invalid.myask.undertow.network.PoseChangeMessage;

public interface IUndertowPosableEntity {
    int FULL_TILT = 10;

    int undertow$getTilt();
    int undertow$incTilt(int max);
    int undertow$decTilt(int min);
    int undertow$getFlyingTicks();
    int undertow$resetFlyingTicks();
    int undertow$incFlyingTicks();
    int undertow$getMaxFlyTicks();
    void undertow$setMaxFlyTicks(int newz);
    double undertow$getFlyThrust();
    void undertow$setFlyThrust(double newDV);
    float undertow$getPrevFracTick();
    void undertow$setPrevFracTick(float newz);

    boolean undertow$isSpearPosing();
    boolean undertow$isSwimming();
    void undertow$setSwimming(boolean art);
    default boolean undertow$isCrawling() {return false;}
    default void undertow$setCrawling(boolean art) {};
    boolean undertow$rippingTide();
    void undertow$setTideRipping(boolean art);
    default boolean undertow$forcingSneak() {return false;}
    void undertow$forceToSneak(boolean art);

    void undertow$setSmallPoseSizeEtC(float h, float w, float eH, float yOff);
    float undertow$getSmallWidth();
    float undertow$getSmallHeight();
    float undertow$getSmallEyeHeight();
    float undertow$getSmallYOffset();
    float undertow$getSneakYOffset();

    double undertow$getCurrentYDifference();

    float undertow$getOldWidth();
    float undertow$getOldHeight();
    float undertow$getOldEyeHeight();
    float undertow$getOldYOffset();

    boolean undertow$forcingMovePacket();//TODO: delete

    void undertow$setHandRendering(); //prevents the next call of ModelBiped##setRotationAngles on this entity
    boolean undertow$clearHandRendering(); //returns if above set flag, clears flag

    default int undertow$smallPosed() { //1 for sneak
        return (undertow$isSwimming() || undertow$isCrawling() || undertow$rippingTide()) ? 2 : 0;
    }

    void undertow$eatPacket(PoseChangeMessage message);
}
