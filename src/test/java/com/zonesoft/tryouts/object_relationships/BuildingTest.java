package com.zonesoft.tryouts.object_relationships;

import org.junit.jupiter.api.Disabled;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zonesoft.tryouts.object_relationships.Building.Room;

@Disabled
class BuildingTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuildingTest.class);
	

	@Test
	
	void testMain() {
		String buildingName = "White-House";
		Building building = new Building(buildingName);
		building.getRooms().add(building.new Room("Entrance-Hall", 0));
		building.getRooms().add(building.new Room("Dining-Room", 0));
		building.getRooms().add(building.new Room("Kitchen", 0));
		building.getRooms().add(building.new Room("Master-Bedroom", 1));
		building.getRooms().add(building.new Room("Spare-Bedroom", 1));
		LOGGER.debug("Number of rooms = {}",building.getRooms().size());
		Room room = building.getRooms().get(3);
		room.occupants().add(new Individual("Robert", "Eastwood"));
		LOGGER.debug("building = {}", building);
		LOGGER.debug("room = {}",room);
		LOGGER.debug("room's building = {}", room.getBuilding());
		building = null;
		LOGGER.debug("room = {}",room);
		
	}


}
