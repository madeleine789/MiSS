package pl.edu.agh.miss.intruders.api.intruder;

import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.DoorEdge;

import java.util.List;

/** Graph node representing a door.
 *
 * Apart from walking within the graph, the IntruderController may set
 * per-door probabilities for such events as:
 *   - intruder moving to another door inside the room
 *   - intruder passing though the door
 *   - intruder staying in place.
 *
 * The probability of above events is calculated out of the weights
 * set by the corresponding methods.
 * The weights may be any non-negative integer numbers. Actual
 * probability of any of the events is computed as:
 *     weight-of-the-event / sum(all-weights-in-this-node)
 * At least one of the weights must be a positive number.
 */
public interface IntruderControllerDoorNode extends DoorNode {
    /** Get an array of the other doors in the room */
    List<IntruderControllerDoorEdge> getEdges();
    /** Get the corresponding door object in the other room ("passing through") */
    IntruderControllerDoorNode getTheOtherSide();

    /** Get the probability of the intruder passing through the door. */
    int getPassThroughProbability();
    /** Set the probability of the intruder passing through the door. */
    void setPassThroughProbability(int weight);

    /** Get the probability of the intruder staying in place for the next time tick. */
    int getStayProbability();
    /** Set the probability of the intruder staying in place for the next time tick. */
    void setStayProbability(int weight);
}
