package it.polito.tdp.food.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<Food, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<Food> listaCibi;
	private Map<Integer, Food> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int porzioni) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.listaCibi = new ArrayList<>(this.dao.getFoodByPortion(porzioni));
		this.idMap = new HashMap<>();
		for(Food f : this.listaCibi)
			idMap.put(f.getFood_code(), f);
		
		Graphs.addAllVertices(this.grafo, this.listaCibi);
		List<Coppia> archi = new ArrayList<>(this.dao.getCalorieCongiunte(idMap, porzioni));
		for(Coppia c : archi)
			Graphs.addEdge(this.grafo, c.getF1(), c.getF2(), c.getCalorieCongiunte());
		
	}
	
	
	public List<Adiacenza> getCalorie(Food food){
		List<Food> vicini = Graphs.neighborListOf(this.grafo, food);
		List<Adiacenza> adiacenze = new ArrayList<>();
		for(Food f : vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(food, f);
			adiacenze.add(new Adiacenza(f, this.grafo.getEdgeWeight(e)));
		}
		Collections.sort(adiacenze);
		
		return adiacenze;
	}
	 
	public List<Food> getListaCibi(){
		return this.listaCibi;
	}
	
	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public void simula(Food primo, int K) {
		this.sim = new Simulator();
		sim.init(K, this, primo);
		sim.run();
	}
	
	public int getTotPreparazioni() {
		return this.sim.getTotPreparazioni();
	}

	public Duration getTotTime() {
		return this.sim.getTotTime();
	}
	
	

}
