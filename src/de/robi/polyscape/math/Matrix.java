package de.robi.polyscape.math;

import de.robi.polyscape.math.Fraction;
import de.robi.polyscape.scape.Polynomial;

public class Matrix {
	private final Fraction[][] content;

	public Matrix(int m, int n) {
		content = new Fraction[m][n];
	}

	public void set(int i, int j, double value) {
		content[i][j] = Fraction.createFraction(value);
	}

	public void setRow(int i, double[] row) {
		if(row.length != content[i].length) return;

		for(int j = 0; j < getWidth(); j++) {
			content[i][j] = Fraction.createFraction(row[i]);
		}
	}

	public void setRow(int i, Fraction[] row) {
		if(row.length != content[i].length) return;

		content[i] = row;
	}

	public double get(int i, int j) {
		return content[i][j].value();
	}

	public int getHeight() {
		return content.length;
	}

	public int getWidth() {
		return content[0].length;
	}

	public void solve() {
		for(int i = 0, j = 0; j < getWidth() - 1 && i < getHeight(); i++, j++) {
			int m = -1, n = -1;



			for(; j < getWidth(); j++) {
				for(int pm = i; pm < content.length; pm++) {
					if(content[pm][j].value() != 0) {
						m = pm;
						n = j;
						break;
					}
				}
				if(m != -1) break;
			}

			if(m == -1) {
				break;
			}

			if(m != i) {
				swap(m, i);
				m = i;
			}


			multiply(m, content[m][n].invert());

			for(int row = 0; row < content.length; row++) {
				if(row != m) {
					add(m, row, content[row][n].multiply(-1));
				}
			}
		}
	}

	private void swap(int m1, int m2) {
		Fraction[] tmp = content[m1];
		content[m1] = content[m2];
		content[m2] = tmp;
	}

	private void multiply(int m, Fraction mod) {
		for(int n = 0; n < content[m].length; n++) {
			content[m][n] = content[m][n].multiply(mod);
		}
	}

	private void add(int m1, int m2, Fraction mod) {
		for(int n = 0; n < content[m1].length; n++) {
			content[m2][n] = content[m2][n].add(content[m1][n].multiply(mod));
		}
	}

	public boolean valid() {
		for(int i = 0; i < getHeight(); i++) {
			boolean zero = true;
			for(int j = 0; j < getWidth() - 1; j++) {
				if(content[i][j].value() != 0) {
					zero = false;
					break;
				}
			}
			if(zero && content[i][getWidth() - 1].value() != 0) {
				return false;
			}
		}
		return true;
	}

	public Polynomial createPolynomial() {
		Polynomial polynomial = new Polynomial();

		int vars = Math.min(content.length, content[0].length - 1);

		int[][] exponents = new int[getWidth()][2];
		int grade = 0;
		int y = 0;
		for(int i = 0; i < getWidth(); i++) {
			int x = grade - y;
			exponents[i][0] = x;
			exponents[i][1] = y;

			if(y == grade) {
				y = 0;
				grade++;
			} else {
				y++;
			}
		}



		int i = 0, j = 0;
		while(i < getHeight() && j < getWidth()) {
			if(content[i][j].value() != 1) {
				j++;
				continue;
			}
			double value = 0;
			if(i < vars) {
				value = content[i][getWidth() - 1].value();
			}

			polynomial.setCoefficient(exponents[j][0], exponents[j][1], value);
			i++;
			j++;
		}

		return polynomial;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (Fraction[] fractions : content) {
			s.append("[");
			for (int n = 0; n < fractions.length; n++) {
				s.append(fractions[n]);
				if (n < fractions.length - 1) s.append(", ");
			}
			s.append("]\n");
		}

		return s.toString();
	}
}
