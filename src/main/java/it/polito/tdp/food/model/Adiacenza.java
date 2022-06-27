package it.polito.tdp.food.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Food food;
	private double calorie;
	public Adiacenza(Food food, double calorie) {
		super();
		this.food = food;
		this.calorie = calorie;
	}
	public Food getFood() {
		return food;
	}
	public double getCalorie() {
		return calorie;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return Double.compare(o.getCalorie(), this.calorie);
	}
	@Override
	public String toString() {
		return food.toString() + ": " + calorie;
	}
	
	
	
	

}
