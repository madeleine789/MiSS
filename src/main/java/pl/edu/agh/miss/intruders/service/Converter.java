package pl.edu.agh.miss.intruders.service;

import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.impl.*;
import pl.edu.agh.miss.intruders.model.RosonBuilding;

import java.util.*;
import java.util.function.Function;

public class Converter {
    private static RosonBuilding building;


    public static Building rosonToSimulation(RosonBuilding building) {
        Converter.building = building;
        return rosonToSimulationConverter.apply(building);
    }

    public static RosonBuilding simulationToRoson(Building building) {
        return Converter.building;
    }

    private static Function<RosonBuilding, SampleBuilding> rosonToSimulationConverter = building -> {
        List<DoorNode> doorNodes = new LinkedList<>();
        Map<String, DoorNode> doors = new HashMap<>();
        List<Room> rooms = new LinkedList<>();
        building.getEdges().stream()
                .filter(edge -> building.isGate(edge.getNodeFromId()) && building.isGate(edge.getNodeToId()) && !doors
                        .containsKey(edge.getNodeFromId()) && !doors.containsKey(edge.getNodeToId()))
                .forEach(edge -> {

            DoorNode start = new SampleDoorNode();
            DoorNode end = new SampleDoorNode();
            DoorEdge startEnd = new SampleDoorEdge();
            DoorEdge endStart = new SampleDoorEdge();

            startEnd.setLength((int) edge.getCost() * 10);
            endStart.setLength((int) edge.getCost() * 10);
            startEnd.setProbability(1f);
            endStart.setProbability(1f);
            startEnd.setDestination(end);
            startEnd.setSource(start);
            endStart.setDestination(start);
            endStart.setSource(end);

            start.addEdge(startEnd);
            start.addEdge(endStart);
            end.addEdge(startEnd);
            end.addEdge(endStart);

            startEnd.setIntrudersQueue(generateQueue());
            endStart.setIntrudersQueue(generateQueue());

            start.setName(edge.getNodeFromId());
            start.setProbability((float) building.getNode(edge.getNodeFromId()).getProbability());
            end.setName(edge.getNodeToId());
            end.setProbability((float) building.getNode(edge.getNodeToId()).getProbability());
            end.setTheOtherSide(start);
            start.setTheOtherSide(end);
            doors.put(edge.getNodeFromId(), start);
            doors.put(edge.getNodeToId(), end);
        });

        building.getSpaces().values().stream().forEach(node -> {
            Room room = new SampleRoom();
            node.getIncidentNodes().stream().filter(n -> building.isGate(node.getNodeId())).forEach(n -> room.addNode
                    (doors.get(n.getNodeId()
            )));

            if (node.isRobotThere() ) {
                node.getIncidentNodes().stream()
                        .filter(n -> doors.get(n.getNodeId()) != null)
                        .forEach(n -> doors.get(n
                        .getNodeId())
                        .getEdges()
                        .stream()
                        .forEach(e -> {
                            Queue<Robot> q = new LinkedList<>();
                            q.add(new SampleRobot());
                            e.setRobotsQueue(q);
                        }));
            }
            rooms.add(room);
        });
        doorNodes.addAll(doors.values());
        return new SampleBuilding(rooms, doorNodes);
    };

    private static Function<Building, RosonBuilding> simulationToRosonConverter = building -> null;

    private static Queue<Float> generateQueue() {
        Queue<Float> iq = new LinkedList<>();
        Random random = new Random();
        for (int i=0; i < random.nextInt(3) + 1; i++){
            iq.add(random.nextFloat());
        }
        return iq;
    }
}


