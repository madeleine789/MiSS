package pl.edu.agh.miss.intruders.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.edu.agh.miss.intruders.model.graph.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class IOService {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static Building importFromJson(File file) throws IOException {
        JsonNode root = mapper.readTree(file);
        JsonNode nodesNode = root.get("nodes");
        JsonNode gatesNode = root.get("gate-nodes");
        JsonNode spacesNode = root.get("space-nodes");
        JsonNode edgesNode = root.get("node-nodes");

        Map<String, Node> nodes = new HashMap<>();
        Map<String, String> gates = new HashMap<>();
        Map<String, Space> spaces = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        gatesNode.forEach(gateNode -> {
            Gate gate = new Gate(gateNode.get("nodeId").asText(), gateNode.get("gateId").asText());
            gates.put(gate.getNodeId(), gate.getGateId());
        });

        spacesNode.forEach(spaceNode -> {
            Space space = new Space(spaceNode.get("nodeId").asText(), spaceNode.get("spaceId").asText());
            if (spaceNode.has("probability")) space.setProbability(spaceNode.get("probability").asDouble());
            spaces.put(space.getNodeId(), space);
        });

        nodesNode.forEach(nodeNode -> {
            Node node = new Node(nodeNode.get("id").asText());
            nodes.put(node.getNodeId(), node);
        });

        edgesNode.forEach(edgeNode -> {
            Edge edge = new Edge();
            edge.setNodeFromId(edgeNode.get("nodeFromId").asText());
            edge.setNodeToId(edgeNode.get("nodeToId").asText());
            edge.setCost(edgeNode.get("cost").asDouble());

            nodes.get(edge.getNodeFromId()).getIncidentEdges().add(edge);
            nodes.get(edge.getNodeToId()).getIncidentEdges().add(edge);
            edges.add(edge);
        });

        return createBuilding(nodes, gates, spaces, edges);
    }

    public static void exportToJson(Building building, File file) throws IOException {
        ObjectNode root = mapper.createObjectNode();

        ArrayNode nodesNode = root.putArray("nodes");
        ArrayNode edgesNode = root.putArray("node-nodes");
        ArrayNode spacesNode = root.putArray("space-nodes");
        ArrayNode gatesNode = root.putArray("gate-nodes");

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
    }

    private static Building createBuilding(Map<String, Node> nodes, Map<String, String> gates,
                                           Map<String, Space> spaces, List<Edge> edges) {
        Building building = new Building();
        nodes.forEach((nodeId, node) -> {
            if (gates.containsKey(nodeId)){
                Gate gate = new Gate(nodeId, gates.get(nodeId));
                gate.setIncidentEdges(node.getIncidentEdges());
                building.addGate(gate);
            } else if (spaces.containsKey(nodeId)){
                Space space = spaces.get(nodeId);
                space.setIncidentEdges(node.getIncidentEdges());
                building.addSpace(space);
            }
        });
        building.addEdges(edges);
        return building;
    }

}
