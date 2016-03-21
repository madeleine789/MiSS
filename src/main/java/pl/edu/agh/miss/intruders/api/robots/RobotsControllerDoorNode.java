package pl.edu.agh.miss.intruders.api.robots;

import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.api.Robot;

import java.util.List;

/** Graph node representing a door.
 *
 * Apart from walking within the graph, the IntruderController may
 * control the actions of the robots in particular DoorNode.
 */
public interface RobotsControllerDoorNode extends DoorNode {
    /** Get an array of the other doors in the room */
    List<DoorEdge> getEdges();
    /** Get the corresponding door object in the other room ("passing through") */
    RobotsControllerDoorNode getTheOtherSide();

    /** Move a robot to through an edge. */
    void move(Robot robot, DoorEdge edge);
    /** Make a robot pass thorugh the door. */
    void passThrough(Robot robot);
}
