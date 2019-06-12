package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	List<Condiment> condimentiGrafo;
	private double somma_best;
	List<Condiment> best;
	
	
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(double calories) {
		grafo = new SimpleWeightedGraph<Condiment, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		condimentiGrafo = new ArrayList<Condiment>();
		double peso;
		
		//Aggiungiamo i vertici
		condimentiGrafo = dao.listCondimentsByCalories(calories);
		Graphs.addAllVertices(grafo, condimentiGrafo);
		
		//Aggiungiamo gli archi con un doppio ciclo for
		for (Condiment c1 : grafo.vertexSet()) {
			for (Condiment c2 : grafo.vertexSet()) {
				if (!c1.equals(c2)) {
					peso = dao.getEdgeWeight(c1.getFood_code(), c2.getFood_code());
					if (peso != 0)
					Graphs.addEdge(grafo, c1, c2, peso);
				}
			}	
		}
		
		
	}
	
	public List<Ingrediente> trovalistaIngredienti() {
		List<Ingrediente> lista = new ArrayList<Ingrediente>();
		double somma = 0.0;
		for (Condiment source : grafo.vertexSet()) {
			for (Condiment dest : Graphs.neighborSetOf(grafo, source)) {
			somma += grafo.getEdgeWeight(grafo.getEdge(source, dest));
		}
			lista.add(new Ingrediente(source, somma));
			somma = 0.0;
	}
		Collections.sort(lista);
		return lista;
	}
	
	public List<Condiment> trovaDieta(Condiment prescelto) {
		
		best = new ArrayList<Condiment>();
		List<Condiment> parziale = new ArrayList<Condiment>();
		somma_best = 0.0;
		
		
		for (Condiment c : grafo.vertexSet()) {
			parziale.add(c);
			cerca(parziale.size(), parziale, prescelto);
			parziale.remove(0);
		}
		return best;
	}
	
	/*RICORSIONE:
	 * Soluzione parziale = lista di Condiment
	 * Soluzione finale = lista di Condiment che contenga prescelto e abbia la somma di calorie massima
	 * Generazione soluzioni = scelgo degli ingredienti che non siano appartenenti alla lista dei vicini
	 */
	
	
	private void cerca(int livello, List<Condiment> parziale, Condiment prescelto) {
		boolean trovato = false;
		
		//Prendiamo l'ultimo coindimento aggiunto
		Condiment ultimo = parziale.get(livello-1);
		
		//Cicliamo la lista dei vertici e aggiungiamo un condimento non adiacente ad ultimo
		for (Condiment prossimo : grafo.vertexSet()) {
			if (!Graphs.neighborSetOf(grafo, ultimo).contains(prossimo) && !parziale.contains(prossimo)) {
				parziale.add(prossimo);
				trovato = true;
				cerca(livello+1, parziale, prescelto);
				parziale.remove(livello);
				}
			}
		
		//Caso terminale 
		if (!trovato) {
			double somma = 0.0;
			for (Condiment c2 : parziale) {
				somma += c2.getCondiment_calories();
			}
			if (somma > somma_best && parziale.contains(prescelto)) {
				somma_best = somma;
				best = new ArrayList<Condiment>(parziale);
			}
		
		}
	}

	public Graph<Condiment, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Condiment> getCondimentiGrafo() {
		Collections.sort(condimentiGrafo);
		return condimentiGrafo;
	}
	
	
}

