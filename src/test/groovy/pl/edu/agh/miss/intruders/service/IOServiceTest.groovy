package pl.edu.agh.miss.intruders.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import pl.edu.agh.miss.intruders.model.graph.Building
import pl.edu.agh.miss.intruders.model.graph.Edge
import pl.edu.agh.miss.intruders.model.graph.Node
import spock.lang.Specification

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotEquals
import static org.junit.Assert.assertNotNull

class IOServiceTest extends Specification {

    File file = new File(IOService.class.getClassLoader().getResource("roson/simple.roson").getFile())
    ObjectMapper mapper = new ObjectMapper();

    def "should create proper building from file"() {
        when:
        Building building = IOService.importFromJson(file)
        then:
        assertNotNull(building)
        assertEquals(building.spaces.size(), 2)
        assertEquals(building.gates.size(), 2)
        assertEquals(building.edges.size(), 6)
        assertEquals(building.getNode("Node1").incidentEdges.size(), 2)
        assertEquals(building.getNode("Node2").incidentEdges.size(), 4)
    }

    def "should set probability if probability field was found"() {
        when:
        Building building = IOService.importFromJson(file)
        then:
        assertEquals(building.getNode("Node1").getProbability(), 0.8, 0.00001)
        assertEquals(building.getNode("Node2").getProbability(), Node.DEFAULT_PROBABILITY, 0.00001)
    }

}
