package pl.edu.agh.miss.intruders.api.intruder;

import java.util.List;

import pl.edu.agh.miss.intruders.api.DoorNode;

/** Interface implemented to control the intruder
 */
public interface IntruderController {
    void init(List<DoorNode> doorNodes);
    void update();
}
