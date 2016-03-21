package pl.edu.agh.miss.intruders.api.intruder;

/** Interface implemented to control the intruder
 */
public interface IntruderController extends pl.edu.agh.miss.intruders.api.Door {
    void init(DoorNode door);
    void update();
}
