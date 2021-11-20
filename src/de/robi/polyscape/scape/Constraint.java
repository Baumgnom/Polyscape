package de.robi.polyscape.scape;

import de.robi.polyscape.math.Matrix;
import de.robi.polyscape.math.Tuple;

import java.util.HashMap;
import java.util.Map;

public class Constraint {
	private final Map<Tuple<Double>, Double> constraints;

	public Constraint() {
		this.constraints = new HashMap<>();
	}

	public void addConstraint(double x, double y, double value) {
		constraints.put(new Tuple<>(x, y), value);
	}

	public Map<Tuple<Double>, Double> getConstraints() {
		return new HashMap<>(constraints);
	}

	public Matrix createMatrix(int grade) {
		if(grade < 0) return null;

		int size = (grade + 1) * (grade + 2) / 2;

		int[][] exponents = new int[size][2];
		int index = 0;
		for(int i = 0; i <= grade; i++) {
			for(int y = 0; y <= i; y++) {
				int x = i - y;
				exponents[index][0] = x;
				exponents[index][1] = y;
				index++;
			}
		}

		Matrix matrix = new Matrix(constraints.size(), size + 1);

		int i = 0;
		for(Tuple<Double> position : constraints.keySet()) {
			for(int j = 0; j < size; j++) {

				matrix.set(i, j, Math.pow(position.x(), exponents[j][0]) * Math.pow(position.y(), exponents[j][1]));
			}
			matrix.set(i, size, constraints.get(position));
			i++;
		}

		return matrix;
	}
}
