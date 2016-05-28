package pl.edu.agh.miss.intruders.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.edu.agh.miss.intruders.model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class IOService {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static RosonBuilding importFromJson(File file) throws IOException {
        JsonNode root = mapper.readTree(file);
        JsonNode nodesNode = root.get("nodes");
        JsonNode gatesNode = root.get("gate-nodes");
        JsonNode spacesNode = root.get("space-nodes");
        JsonNode edgesNode = root.get("node-nodes");
        JsonNode robotsNode = root.get("space-robots");
        JsonNode intrudersNode = root.get("space-victims");


        Map<String, Node> nodes = new HashMap<>();
        Map<String, Gate> gates = new HashMap<>();
        Map<String, Space> spaces = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        gatesNode.forEach(gateNode -> {
            Gate gate = new Gate(gateNode.get("nodeId").asText(), gateNode.get("gateId").asText());
            if (gateNode.has("probability")) gate.setProbability(gateNode.get("probability").asDouble());
            gates.put(gate.getNodeId(), gate);
        });

        spacesNode.forEach(spaceNode -> {
            if(!gates.containsKey(spaceNode.get("nodeId").asText())) {
                Space space = new Space(spaceNode.get("nodeId").asText(), spaceNode.get("spaceId").asText());
                spaces.put(space.getNodeId(), space);
            }
        });

        nodesNode.forEach(nodeNode -> {
            Node node = new Node(nodeNode.get("id").asText());
            if (nodeNode.has("position")) {
                node.setX(nodeNode.get("position").get("x").asDouble());
                node.setY(nodeNode.get("position").get("y").asDouble());
            }
            nodes.put(node.getNodeId(), node);
        });

        edgesNode.forEach(edgeNode -> {
            Edge edge = new Edge();
            edge.setNodeFromId(edgeNode.get("nodeFromId").asText());
            edge.setNodeToId(edgeNode.get("nodeToId").asText());
            edge.setCost(edgeNode.get("cost").asDouble());

            nodes.get(edge.getNodeFromId()).getIncidentEdges().add(edge);
            nodes.get(edge.getNodeFromId()).getIncidentNodes().add(nodes.get(edge.getNodeToId()));
            nodes.get(edge.getNodeToId()).getIncidentNodes().add(nodes.get(edge.getNodeFromId()));
            nodes.get(edge.getNodeToId()).getIncidentEdges().add(edge);
            edges.add(edge);
        });

        robotsNode.forEach(robot -> {
            String spaceId = robot.get("spaceId").asText();
            Space space = spaces.values().stream()
                    .filter(s -> Objects.equals(s.getSpaceId(), spaceId)).collect(Collectors.toList()).get(0);
            space.isRobotThere(true);
            nodes.get(space.getNodeId()).getIncidentNodes().stream()
                    .filter(node -> gates.containsKey(node.getNodeId())).forEach(gate -> gate.isRobotThere(true));

        });

        intrudersNode.forEach(intruder -> {
            String spaceId = intruder.get("spaceId").asText();
            Space space = spaces.values().stream()
                    .filter(val -> Objects.equals(val.getSpaceId(), spaceId)).collect(Collectors.toList()).get(0);
            space.setProbability(1f);
            space.isIntruderThere(true);
            nodes.get(space.getNodeId()).getIncidentNodes().stream()
                    .filter(node -> gates.containsKey(node.getNodeId())).forEach(gate -> {
                gates.get(gate.getNodeId()).setProbability(1f);
                gates.get(gate.getNodeId()).isIntruderThere(true);
            });
        });

        return createBuilding(nodes, gates, spaces, edges);
    }

    public static void exportToJson(RosonBuilding building, File file) throws IOException {
        ObjectNode root = mapper.createObjectNode();

        ArrayNode nodesNode = root.putArray("nodes");
        ArrayNode edgesNode = root.putArray("node-nodes");
        ArrayNode spacesNode = root.putArray("space-nodes");
        ArrayNode gatesNode = root.putArray("gate-nodes");

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
    }

    private static RosonBuilding createBuilding(Map<String, Node> nodes, Map<String, Gate> gates,
                                                Map<String, Space> spaces, List<Edge> edges) {
        RosonBuilding building = new RosonBuilding();
        nodes.forEach((nodeId, node) -> {
            if (gates.containsKey(nodeId)){
                Gate gate = gates.get(nodeId);
                gate.setIncidentEdges(node.getIncidentEdges());
                gate.setIncidentNodes(node.getIncidentNodes());
                gate.setX(node.getX());
                gate.setY(node.getY());
                building.addGate(gate);
            } else if (spaces.containsKey(nodeId)){
                Space space = spaces.get(nodeId);
                space.setIncidentEdges(node.getIncidentEdges());
                space.setIncidentNodes(node.getIncidentNodes());
                space.setX(node.getX());
                space.setY(node.getY());
                building.addSpace(space);
            }
        });

        List<Gate> toDelete = new ArrayList<>();
        building.getGates().forEach((id, gate) -> {
            if (gate.getIncidentNodes().size() == 1) {
                toDelete.add(gate);
            }
        });
        building.addEdges(edges);
        if (!toDelete.isEmpty()) toDelete.forEach(gate -> building.deleteNode(gate.getNodeId()));
        return building;
    }

}
