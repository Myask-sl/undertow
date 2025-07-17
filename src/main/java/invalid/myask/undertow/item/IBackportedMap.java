package invalid.myask.undertow.item;

public interface IBackportedMap {
    default boolean renderLikeVanillaMapInFrame() {return false;}
    default boolean hidePlayers() {return false;}
    default boolean renderMoreStuff() {return false;}
    default boolean isLocked() {return false;} //TODO: implement
}
