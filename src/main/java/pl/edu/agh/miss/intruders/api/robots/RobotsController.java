package pl.edu.agh.miss.intruders.api.robots;

import java.util.List;

import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.DoorNode;

/** Interface implemented to control the robots
 */
public interface RobotsController {
    void init(List<DoorNode> doorNodes);
    void update();
    void reduceProbabilities(Config config);
}
