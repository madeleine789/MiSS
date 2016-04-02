package pl.edu.agh.miss.intruders.view;


import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import pl.edu.agh.miss.intruders.model.graph.*;
import pl.edu.agh.miss.intruders.model.graph.Edge;

public class GraphView {

    private boolean mergedEdges = false;
    private boolean nodeLabels = false;
    private boolean edgeLabels = false;
    private boolean robots = false;

    public void generateAndDisplay(Building building) {
        if(!mergedEdges)
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = new MultiGraph("building");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.display();

        int i = 0;
        for (Edge edge : building.getEdges()) {
            String start, end;
            if (building.isGate(edge.getNodeFromId())) start = building.getGates().get(edge.getNodeFromId()).getLabel();
            else start = building.getSpaces().get(edge.getNodeFromId()).getLabel();

            if (building.isGate(edge.getNodeToId())) end = building.getGates().get(edge.getNodeToId()).getLabel();
            else end = building.getSpaces().get(edge.getNodeToId()).getLabel();

            org.graphstream.graph.Edge e = graph.addEdge(String.valueOf(i), start, end, true);
            if(edgeLabels) {
                e.addAttribute("ui.label", String.valueOf(edge.getCost()));
                e.addAttribute("ui.style", "text-alignment: under;");
            }
            i++;
        }

        for (org.graphstream.graph.Node node : graph) {
            if (nodeLabels) {
                node.addAttribute("ui.label", node.getId());
                node.addAttribute("ui.style", "text-alignment: under;");
            }

            if (node.getId().contains("S")) {
                node.addAttribute("ui.style", "shape : box; size: 15px, 15px; fill-color: rgb(0,100,255);");
                if (robots) {
                    node.addAttribute("ui.style", "icon: url('./src/main/resources/robot.png'); icon-mode: at-left; ");
                }
            } else if (node.getId().contains("G")) {
                node.addAttribute("ui.style", "shape : diamond; size: 15px, 15px; fill-color: rgb(255,100,0);");
            }
        }
    }

    public GraphView withMergedEdges(boolean value) {
        this.mergedEdges = value;
        return this;
    }

    public GraphView withEdgeLabels(boolean value) {
        this.edgeLabels = value;
        return this;
    }

    public GraphView withNodeLabels(boolean value) {
        this.nodeLabels = value;
        return this;
    }

    public GraphView withRobots(boolean value) {
        this.robots = value;
        return this;
    }
}

