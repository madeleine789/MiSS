package pl.edu.agh.miss.intruders.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.edu.agh.miss.intruders.model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class IOService {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static RosonBuilding importFromJson(File file) throws IOException {
        JsonNode root = mapper.readTree(file);
        JsonNode nodesNode = root.get("nodes");
        JsonNode gatesNode = root.get("gate-nodes");
        JsonNode spacesNode = root.get("space-nodes");
        JsonNode edgesNode = root.get("node-nodes");

        Map<String, Node> nodes = new HashMap<>();
        Map<String, Gate> gates = new HashMap<>();
        Map<String, Space> spaces = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        final boolean[] automaticLayout = { true };

        gatesNode.forEach(gateNode -> {
            Gate gate = new Gate(gateNode.get("nodeId").asText(), gateNode.get("gateId").asText());
            if (gateNode.has("probability")) gate.setProbability(gateNode.get("probability").asDouble());
            gates.put(gate.getNodeId(), gate);
        });

        spacesNode.forEach(spaceNode -> {
            Space space = new Space(spaceNode.get("nodeId").asText(), spaceNode.get("spaceId").asText());
            if (spaceNode.has("probability")) space.setProbability(spaceNode.get("probability").asDouble());
            spaces.put(space.getNodeId(), space);
        });

        nodesNode.forEach(nodeNode -> {
            Node node = new Node(nodeNode.get("id").asText());
            if (nodeNode.has("position")) {
                node.setX(nodeNode.get("position").get("x").asDouble());
                node.setY(nodeNode.get("position").get("y").asDouble());
                automaticLayout[0] = false;
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
            nodes.get(edge.getNodeToId()).getIncidentEdges().add(edge);
            edges.add(edge);
        });

        return createBuilding(nodes, gates, spaces, edges, automaticLayout[0]);
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
                                                Map<String, Space> spaces, List<Edge> edges, boolean
                                                   automaticLayout) {
        RosonBuilding building = new RosonBuilding();
        building.setAutomaticLayout(automaticLayout);
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
