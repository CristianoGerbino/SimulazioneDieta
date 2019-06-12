package it.polito.tdp.food.model;

import it.polito.tdp.food.db.Condiment;

public class Ingrediente implements Comparable<Ingrediente> {

	private Condiment condimento;
	private Double numCibi;
	
	public Ingrediente(Condiment condimento, Double numCibi) {
		this.condimento = condimento;
		this.numCibi = numCibi;
	}

	public Condiment getCondimento() {
		return condimento;
	}

	public Double getNumCibi() {
		return numCibi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condimento == null) ? 0 : condimento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		if (condimento == null) {
			if (other.condimento != null)
				return false;
		} else if (!condimento.equals(other.condimento))
			return false;
		return true;
	}

	@Override
	public int compareTo(Ingrediente o) {
		return (int) (o.condimento.getCondiment_calories() - this.condimento.getCondiment_calories());
	}

	@Override
	public String toString() {
		return this.condimento.getDisplay_name()+" ("+this.getCondimento().getCondiment_calories()+" calorie) contenuto in "
				+this.getNumCibi()+" cibi";
	}
	
	
	
	
}
