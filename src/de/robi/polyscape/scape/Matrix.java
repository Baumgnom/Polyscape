package de.robi.polyscape.scape;

public class Matrix {
	private final double[][] content;

	public Matrix(int m, int n) {
		content = new double[m][n];
	}

	public void set(int i, int j, double value) {
		content[i][j] = value;
	}

	public void setRow(int i, double[] row) {
		if(row.length != content[i].length) return;

		content[i] = row;
	}

	public double get(int i, int j) {
		return content[i][j];
	}

	public int getHeight() {
		return content.length;
	}

	public int getWidth() {
		return content[0].length;
	}

	public void solve() {
		for(int i = 0; i < content[0].length - 1 && i < content.length; i++) {
			int m = -1, n = -1;

			for(int pn = i; pn < content[0].length; pn++) {
				for(int pm = i; pm < content.length; pm++) {
					if(content[pm][pn] != 0) {
						m = pm;
						n = pn;
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


			multiply(m, 1 / content[m][n]);

			content[m][n] = 1; // avoid rounding errors

			for(int row = 0; row < content.length; row++) {
				if(row != m) {
					add(m, row, -content[row][n]);
					content[row][n] = 0; // avoid rounding errors
				}
			}
		}
	}

	private void swap(int m1, int m2) {
		double[] tmp = content[m1];
		content[m1] = content[m2];
		content[m2] = tmp;
	}

	private void multiply(int m, double mod) {
		for(int n = 0; n < content[m].length; n++) {
			double val = content[m][n] * mod;
			if(val == -0) val = 0;
			content[m][n] = val;
		}
	}

	private void add(int m1, int m2, double mod) {
		for(int n = 0; n < content[m1].length; n++) {
			double val = content[m2][n] + content[m1][n] * mod;
			if(val == -0) val = 0;
			content[m2][n] = val;
		}
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
			if(content[i][j] != 1) {
				j++;
				continue;
			}
			double value = 0;
			if(i < vars) {
				value = content[i][getWidth() - 1];
			}

			polynomial.setCoefficient(exponents[j][0], exponents[j][1], value);
			i++;
			j++;
		}

		/*for(int i = 0, grade = 0; i < vars; grade++) {
			for(int y = 0; y <= grade; y++) {
				int x = grade - y;

				double value = 0;
				if(i < vars) {
					value = content[i][getWidth() - 1];
				}

				polynomial.setCoefficient(x, y, value);

				i++;
			}
		}*/

		return polynomial;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (double[] doubles : content) {
			s.append("[");
			for (int n = 0; n < doubles.length; n++) {
				s.append(doubles[n]);
				if (n < doubles.length - 1) s.append(", ");
			}
			s.append("]\n");
		}

		return s.toString();
	}
}
