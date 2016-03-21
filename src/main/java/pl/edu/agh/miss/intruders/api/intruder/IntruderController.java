package pl.edu.agh.miss.intruders.api.intruder;

/** Interface implemented to control the intruder
 */
public interface IntruderController {
    void init(IntruderControllerDoorNode doorNode);
    void update();
}
