package it.polito.tdp.food.model;

import java.time.Duration;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		INIZIO_PREPARAZIONE,
		FINE_PREPARAZIONE
	}

	private Duration time;
	private EventType type;
	private Food food;
	private double calorie;
	
	public Event(Duration time, EventType type, Food food, double calorie) {
		super();
		this.time = time;
		this.type = type;
		this.food = food;
		this.calorie = calorie;
	}
	
	public Duration getTime() {
		return time;
	}

	public Food getFood() {
		return food;
	}

	public EventType getType() {
		return type;
	}

	public double getCalorie() {
		return calorie;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
	

}
