package pl.edu.agh.miss.intruders.model.graph;

import java.util.*;

public class Building {
    private Map<String, Gate> gates = new HashMap<>();
    private Map<String, Space> spaces = new HashMap<>();
    private List<Edge> edges = new LinkedList<>();

    public  Gate addGate(Gate gate) {
        return gates.put(gate.getNodeId(), gate);
    }

    public Space addSpace(Space space) {
        return spaces.put(space.getNodeId(), space);
    }

    public void addEdges(List<Edge> edgeList) {
        this.edges.addAll(edgeList);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Map<String, Gate> getGates() {
        return gates;
    }

    public Map<String, Space> getSpaces() {
        return spaces;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SPACES:").append("\n");
        spaces.forEach((id, space) -> sb.append("\t").append(space.getSpaceId()).append(space.toString()).append("\n"));
        sb.append("GATES:").append("\n");
        gates.forEach((id, gate) -> sb.append("\t").append(gate.getGateId()).append(gate.toString()).append("\n"));
        sb.append("EDGES:").append("\n");
        edges.forEach((edge) -> sb.append("\t").append(edge.toString()).append("\n"));
        return sb.toString();
    }
}
