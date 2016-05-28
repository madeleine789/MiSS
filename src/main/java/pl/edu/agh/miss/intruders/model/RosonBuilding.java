package pl.edu.agh.miss.intruders.model;

import java.util.*;

public class RosonBuilding {
    private Map<String, Gate> gates = new HashMap<>();
    private Map<String, Space> spaces = new HashMap<>();
    private List<Edge> edges = new LinkedList<>();
    private boolean automaticLayout = true;

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

    public boolean isGate(String nodeId) {
        return this.gates.containsKey(nodeId);
    }

    public boolean isSpace(String nodeId) {
        return this.spaces.containsKey(nodeId);
    }

    public Node getNode(String id) {
        if (isSpace(id)) return this.spaces.get(id);
        else if (isGate(id)) return this.gates.get(id);
        else return null;
    }

    public void deleteNode(String id) {
        Node toDelete = getNode(id);
        this.getEdges().removeAll(toDelete.getIncidentEdges());
        if (isGate(id)) gates.remove(id);
        else spaces.remove(id);
    }

    public Edge getEdge(String repr) {
        Optional<Edge> any = getEdges().stream().filter(edge -> Objects.equals(edge.toString(), repr)).findAny();
        if (any.isPresent()) return any.get();
        return null;
    }

    public boolean needsAutomaticLayout() {
        return automaticLayout;
    }

    public void setAutomaticLayout(boolean automaticLayout) {
        this.automaticLayout = automaticLayout;
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
