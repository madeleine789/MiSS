package pl.edu.agh.miss.intruders.api.robots;

import java.util.List;

import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Room;

/** Interface implemented to control the robots
 */
public interface RobotsController {
    void init(List<DoorNode> doorNodes, List<Room> rooms);
    void update();
    void reduceProbabilities(Config config);
}
