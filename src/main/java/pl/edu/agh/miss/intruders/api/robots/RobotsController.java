package pl.edu.agh.miss.intruders.api.robots;

/** Interface implemented to control the robots
 */
public interface RobotsController {
    void init(DoorNode door);
    void update();
}
