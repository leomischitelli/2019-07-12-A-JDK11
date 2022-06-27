package it.polito.tdp.food.model;

public class Coppia {
	
	private Food f1;
	private Food f2;
	private double calorieCongiunte;
	public Coppia(Food f1, Food f2, double calorieCongiunte) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.calorieCongiunte = calorieCongiunte;
	}
	public Food getF1() {
		return f1;
	}
	public Food getF2() {
		return f2;
	}
	public double getCalorieCongiunte() {
		return calorieCongiunte;
	}
	
	

}
