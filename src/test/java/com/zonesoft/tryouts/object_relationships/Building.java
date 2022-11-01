package com.zonesoft.tryouts.object_relationships;

import static com.zonesoft.chatapp.utils.ToStringHelpers.*;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private String name;
	private final List<Room> rooms = new ArrayList<>();

	public Building(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		n(sb);
		lb(sb);
			n(sb); t(sb); v(sb, "\"name\": "); qv(sb,this.name); c(sb);
			n(sb); t(sb); v(sb, "\"rooms\": "); v(sb,this.rooms); 
			n(sb);
		rb(sb);	
	return sb.toString();
	}
	
	class Room{
		private String label;
		private int floor;
		private final List<Individual> listOccupants = new ArrayList<>();
		
		public Room(String label, int floor) {
			super();
			this.label = label;
			this.floor = floor;
		}
		
		public String getBuilding() {
			return Building.this.getName();
		}
		
		public String getLabel() {
			return this.label;
		}
		
		
		public void setLabel(String label) {
			this.label = label;
		}
		
		
		public int getFloor() {
			return this.floor;
		}
		
		
		public void setFloor(int floor) {
			this.floor = floor;
		}
		
		public List<Individual> occupants() {
			return listOccupants;
		}


		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			n(sb);
			lb(sb);
			n(sb); t(sb); v(sb, "\"building-name\": "); qv(sb,Building.this.name); c(sb);
				n(sb); t(sb); v(sb, "\"label\": "); qv(sb,this.label); c(sb);
				n(sb); t(sb); v(sb, "\"floor\": "); qv(sb,this.floor); c(sb);
				n(sb); t(sb); v(sb, "\"listOccupants\": "); v(sb,this.listOccupants);
				n(sb);
			rb(sb);
			return sb.toString();
		}
	}
}
