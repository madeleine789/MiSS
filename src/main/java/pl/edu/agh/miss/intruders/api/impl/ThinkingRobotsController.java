package pl.edu.agh.miss.intruders.api.impl;

import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;

import java.util.*;

public class ThinkingRobotsController  implements RobotsController {

    private List<DoorNode> doorNodes;

    private List<Room> rooms;

    @Override
    public void init(List<DoorNode> doorNodes, List<Room> rooms) {
        this.doorNodes = doorNodes;
        this.rooms = rooms;
    }

    @Override
    public void update() {
        Map<DoorNode, List<Robot>> robotsOnTheOtherSide = new HashMap<>();
        for (DoorNode node : doorNodes) {
            // get robots to distribute
            List<DoorEdge> edges = node.getEdges();
            List<Robot> robotsByTheDoor = new LinkedList<>();

            edges.stream().filter(edge -> edge.getDestination().equals(node) && !edge.getRobotsQueue().isEmpty())
                    .forEach(edge->robotsByTheDoor.addAll(edge.getRobotsQueue().poll()));
            robotsByTheDoor.addAll(node.getRobotsFromTheOtherSide());
            // prepare lists to be distributed
            List<DoorEdge> outEdges = new LinkedList<>();
            node.getEdges().stream().filter(edge -> edge.getSource().equals(node)).forEach(outEdges::add);

            List<List<Robot>> listsToDistribute = new LinkedList<>();
            for (int i = 0;i < outEdges.size() + 1; i++) {
                listsToDistribute.add(new LinkedList<>());
            }
            int i = 0;
            while (!robotsByTheDoor.isEmpty()) {
                listsToDistribute.get(i).add(robotsByTheDoor.remove(0));
                i = (i+1) % (outEdges.size()+1);
            }

            Collections.sort(outEdges, (e1, e2) ->
                    Float.compare(e1.getDestination().getProbability(), e2.getDestination().getProbability()));
            Collections.reverse(outEdges);
            // distribute lists
            outEdges.forEach(edge -> edge.getRobotsQueue().add(listsToDistribute.remove(0)));
            robotsOnTheOtherSide.put(node, listsToDistribute.remove(0));
        }
        // update other side
        robotsOnTheOtherSide.keySet().forEach(node -> node.setRobotsFromTheOtherSide(robotsOnTheOtherSide.get(node)));
    }

    @Override
    public void reduceProbabilities(Config config) {
        for (Room room : rooms) {
            System.out.println(room);
            for (DoorNode node : room.getDoorNodes()) {
                for (Robot robot : node.getRobots()) {
                    for (DoorNode nodeToUpdate : room.getDoorNodes()) {
                        for (DoorEdge edge : nodeToUpdate.getEdges()) {
                            Queue<Float> newIntruderProb = new LinkedList<>();
                            edge.getIntruderQueue().forEach(prob->newIntruderProb.add(config.getNewProbability(prob, robot)));
                            edge.setIntrudersQueue(newIntruderProb);
                        }
                        nodeToUpdate.setProbability(config.getNewProbability(nodeToUpdate.getProbability(), robot));
                    }
                }
            }
        }
    }
}
