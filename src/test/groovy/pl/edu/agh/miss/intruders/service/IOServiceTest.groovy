package pl.edu.agh.miss.intruders.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import pl.edu.agh.miss.intruders.model.graph.Building
import pl.edu.agh.miss.intruders.model.graph.Edge
import spock.lang.Specification

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotEquals
import static org.junit.Assert.assertNotNull

class IOServiceTest extends Specification {

    File file = new File(IOService.class.getClassLoader().getResource("roson/simple.roson").getFile())
    ObjectMapper mapper = new ObjectMapper();

    def "should create building from file"() {
        when:
        Building building = IOService.importFromJson(file)
        then:
        assertNotNull(building)
        assertFalse(building.spaces.isEmpty())
        assertFalse(building.gates.isEmpty())
        assertFalse(building.edges.isEmpty())
    }

    def "should merge gate nodes"() {
        given:
        JsonNode gates = mapper.readTree(file).get("gate-nodes")
        when:
        Building building = IOService.importFromJson(file)
        then:
        assertNotEquals(gates.size(), building.gates.size())
        assertFalse(building.gates.containsKey('Node2') && building.gates.containsKey('Node3'))
        assertFalse(!building.gates.containsKey('Node23'))
        gates.size() == 2
        building.gates.size() == 1
    }

    def "should remove edges after gate merger"() {
        given:
        JsonNode edges = mapper.readTree(file).get("node-nodes")
        when:
        Building building = IOService.importFromJson(file)
        then:
        assertNotEquals(building.edges.size(), edges.size())
        building.edges.size() == 4
        edges.size() == 6
    }

    def "should set probability if probability field was found"() {
        when:
        Building building = IOService.importFromJson(file)
        then:
        assertEquals(building.spaces.get("Node1").getProbability(), 0.8, 0.00001)
        assertEquals(building.spaces.get("Node4").getProbability(), 0.01, 0.00001)
    }

}
