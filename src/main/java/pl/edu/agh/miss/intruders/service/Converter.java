package pl.edu.agh.miss.intruders.service;

import pl.edu.agh.miss.intruders.api.*;
import pl.edu.agh.miss.intruders.api.impl.*;
import pl.edu.agh.miss.intruders.model.Node;
import pl.edu.agh.miss.intruders.model.RosonBuilding;

import java.util.*;
import java.util.function.Function;

public class Converter {
    private RosonBuilding rosonBuilding;

    public Converter(RosonBuilding rosonBuilding) {
        this.rosonBuilding = rosonBuilding;
    }


    public Building rosonToSimulation() {
        return rosonToSimulationConverter.apply(this.rosonBuilding);
    }

    public RosonBuilding simulationToRoson(Building building) {
        return simulationToRosonConverter.apply(building);
    }

    private Function<RosonBuilding, SampleBuilding> rosonToSimulationConverter = building -> {
        List<DoorNode> doorNodes = new LinkedList<>();
        Map<String, DoorNode> doors = new HashMap<>();
        List<Room> rooms = new LinkedList<>();
        building.getEdges().stream()
                .filter(edge -> building.isGate(edge.getNodeFromId()) && building.isGate(edge.getNodeToId()) && !doors
                        .containsKey(edge.getNodeFromId()) && !doors.containsKey(edge.getNodeToId()))
                .forEach(edge -> {

            DoorNode start = new SampleDoorNode();
            DoorNode end = new SampleDoorNode();
            DoorEdge startEnd = new SampleDoorEdge(start, end);
            DoorEdge endStart = new SampleDoorEdge(end, start);

            startEnd.setLength((int) (edge.getCost() * 10));
            endStart.setLength((int) (edge.getCost() * 10));
            startEnd.setDestination(end);
            startEnd.setSource(start);
            endStart.setDestination(start);
            endStart.setSource(end);

            start.addEdge(startEnd);
            start.addEdge(endStart);
            end.addEdge(startEnd);
            end.addEdge(endStart);

            startEnd.setIntrudersQueue(generateQueue(startEnd.getLength()));
            endStart.setIntrudersQueue(generateQueue(endStart.getLength()));

            start.setName(edge.getNodeFromId());
            start.setProbability(building.getNode(edge.getNodeFromId()).getProbability());
            end.setName(edge.getNodeToId());
            end.setProbability(building.getNode(edge.getNodeToId()).getProbability());
            end.setTheOtherSide(start);
            start.setTheOtherSide(end);
            doors.put(edge.getNodeFromId(), start);
            doors.put(edge.getNodeToId(), end);
        });

        building.getSpaces().values().stream().forEach(node -> {
            Room room = new SampleRoom();
            node.getIncidentNodes().stream().filter(n -> building.isGate(n.getNodeId())).forEach(n -> room.addNode
                    (doors.get(n.getNodeId()
            )));
            node.getIncidentNodes().stream().forEach(gate -> addBidirectionalEdges(gate, node.getIncidentNodes(), doors));
            node.getIncidentNodes().stream()
                    .filter(n -> doors.get(n.getNodeId()) != null)
                    .forEach(n -> doors.get(n
                    .getNodeId())
                    .getEdges()
                    .stream()
                    .forEach(e -> {
                        Queue<List<Robot>> q = new LinkedList<>();
                        List<Robot> l = new LinkedList<>();
                        if (node.isRobotThere()) {
                        	l.add(new SampleRobot());
                        	node.isRobotThere(false);
                        	System.out.println(node);
                        }
                        q.add(l);
                        while (q.size()<e.getLength()) {
                        	q.add(new LinkedList<>());
                        }
                        e.setRobotsQueue(q);
                    }));
            rooms.add(room);
        });
        doorNodes.addAll(doors.values());
        return new SampleBuilding(rooms, doorNodes);
    };

    private void addBidirectionalEdges(Node gate, Set<Node> incidentNodes, Map<String, DoorNode> doors) {
        DoorNode door = doors.get(gate.getNodeId());
        if (door == null) return;
        incidentNodes.stream().filter(node -> !Objects.equals(node.getNodeId(), gate.getNodeId())).forEach(node -> {
            DoorNode other = doors.getOrDefault(node.getNodeId(), null);
            if (other != null) {
                gate.getIncidentNodes().add(rosonBuilding.getNode(other.getName()));
                DoorEdge startEnd = new SampleDoorEdge(door, other);
                startEnd.setLength(1);
                door.addEdge(startEnd);
                other.addEdge(startEnd);
                startEnd.setIntrudersQueue(generateQueue(startEnd.getLength()));
            }
        });
    }

    private Function<Building, RosonBuilding> simulationToRosonConverter = building -> {
        building.getDoorNodes().forEach(node -> {
            float probability = node.getProbability();
            this.rosonBuilding.getNode(node.getName()).getIncidentNodes().stream()
                    .filter(n -> this.rosonBuilding.isSpace(n.getNodeId()))
                    .forEach(n -> n.setProbability(probability));
            this.rosonBuilding.getNode(node.getName()).setProbability(probability);

            this.rosonBuilding.getEdges().forEach( edge -> {
                if (/* node.getRobots().length > 0 && */(Objects.equals(edge.getNodeFromId(), node.getName()) || Objects
                        .equals(edge.getNodeToId(), node.getName()))) {
                    boolean onlyNullRobots = true;
                    for (Robot r: node.getRobots()) {
                        if (r != null) onlyNullRobots = false;
                    }
                    if (onlyNullRobots) {
                        edge.setHasRobot(false);
                        rosonBuilding.getNode(node.getName()).isRobotThere(false);
                    } else {
                        edge.setHasRobot(true);
                        rosonBuilding.getNode(node.getName()).isRobotThere(true);
                    }
                }
            });
        });
        return this.rosonBuilding;
    };

    private static Queue<Float> generateQueue(int size) {
        Queue<Float> iq = new LinkedList<>();
        for (int i=0; i < size; i++) {
            iq.add(0f);
        }
        return iq;
    }
}


