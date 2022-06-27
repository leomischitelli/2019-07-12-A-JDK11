package it.polito.tdp.food.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import it.polito.tdp.food.model.Event.EventType;

public class Simulator {
	
	private Queue<Event> queue;
	private Model model;
	
	private Set<Food> inPreparazione;
	private Set<Food> giaPreparati;
	
	private int totPreparazioni;
	private Duration totTime;
	
	public Simulator() {
		super();
	}
	
	public void init(int K, Model model, Food primo) {
		this.queue = new PriorityQueue<>();
		this.model = model;
		this.inPreparazione = new HashSet<Food>();
		this.giaPreparati = new HashSet<Food>();
		this.totPreparazioni = 0;
		this.totTime = Duration.ZERO;
		
		//inizializzo
		List<Adiacenza> vicini = new ArrayList<>(this.model.getCalorie(primo));
		int i = 0;
		while(i < K && i < vicini.size()) {
			Adiacenza a = vicini.get(i);
			this.queue.add(new Event(Duration.ofMinutes(0), EventType.INIZIO_PREPARAZIONE, a.getFood(), a.getCalorie()));
			i++;
		}
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.totTime = e.getTime();
			processEvent(e);
		}
	}
	

	private void processEvent(Event e) {
		Duration time = e.getTime();
		EventType type = e.getType();
		Food food = e.getFood();
		Double calorie = e.getCalorie();
		
		switch(type) {
		case INIZIO_PREPARAZIONE:
			this.inPreparazione.add(food);
			Duration durata = getMinutesFromDouble(calorie);
			this.queue.add(new Event(time.plus(durata), EventType.FINE_PREPARAZIONE, food, -1.0)); //valore di calorie non rilevante
			
			
			break;
		case FINE_PREPARAZIONE:
			this.inPreparazione.remove(food);
			this.giaPreparati.add(food);
			this.totPreparazioni++;
			
			//seleziono nuovo cibo
			List<Adiacenza> vicini = new ArrayList<>(this.model.getCalorie(food));
			for(Adiacenza a : vicini) {
				Food f = a.getFood();
				if(!this.inPreparazione.contains(f) && !this.giaPreparati.contains(f)) {
					this.queue.add(new Event(time, EventType.INIZIO_PREPARAZIONE, f, a.getCalorie()));
				}
			}
			
			
			break;
		}
		
	}

	private Duration getMinutesFromDouble(double calorie) {
		Duration duration = Duration.ofMinutes((long) (calorie * 60));
		return duration;
	}

	public int getTotPreparazioni() {
		return totPreparazioni;
	}

	public Duration getTotTime() {
		return totTime;
	}
	
	
	
	

}
