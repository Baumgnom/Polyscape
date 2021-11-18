package de.robi.polyscape.scape;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Exponent {
	public final int index1;
	public final int index2;
	public double value;

	public Exponent(int index1, int index2, double value) {
		if(index1 < 0) index1 = 0;
		if(index2 < 0) index2 = 0;
		this.index1 = index1;
		this.index2 = index2;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Exponent that = (Exponent) o;
		return index1 == that.index1 && index2 == that.index2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(index1, index2);
	}
}
