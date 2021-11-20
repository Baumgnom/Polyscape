package de.robi.polyscape;

import de.robi.polyscape.frame.Frame;
import de.robi.polyscape.frame.Gradient;
import de.robi.polyscape.scape.Constraint;
import de.robi.polyscape.math.Matrix;
import de.robi.polyscape.scape.Polynomial;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		Random random = new Random();

		/*Matrix matrix = new Matrix(10, 13);
		for(int i = 0; i < matrix.getHeight(); i++) {
			for(int j = 0; j < matrix.getWidth(); j++) {
				matrix.set(i, j, random.nextInt(5) - 2);
			}
		}

		System.out.println(matrix);

		matrix.solve();*/







		Frame frame = new Frame();

		Gradient gradient = new Gradient();
		gradient.addColor(-1000, new Color(255, 0, 127));
		gradient.addColor(-200, new Color(255, 0, 0));
		gradient.addColor(-20, new Color(255, 127, 0));
		gradient.addColor(-5, new Color(255, 255, 0));
		gradient.addColor(0, new Color(0, 255, 0));
		gradient.addColor(5, new Color(0, 255, 255));
		gradient.addColor(20, new Color(0, 127, 255));
		gradient.addColor(200, new Color(0, 0, 255));
		gradient.addColor(1000, new Color(127, 0, 255));
		frame.setGradient(gradient);

		while(true) {
			Constraint constraint = new Constraint();


			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 5; j++) {
					constraint.addConstraint(i, j, random.nextInt(5));
				}
			}


			Matrix matrix = null;
			for(int grade = 10; ; grade++) {
				matrix = constraint.createMatrix(grade);
				matrix.solve();
				System.out.println(matrix);

				if(matrix.valid()) {
					break;
				}
			}

			Polynomial polynomial = matrix.createPolynomial();

			Map<Double[], Double> map = constraint.getConstraints();
			for(Double[] pos : map.keySet()) {
				System.out.println("Expected at[" + pos[0] + "," + pos[1] + "]: \t" + map.get(pos) + "\t - polynom: " + polynomial.getValue(pos[0], pos[1]));
			}


			frame.setPolynomial(polynomial);

			System.out.println(matrix);
			//System.out.println(polynomial);

			return;
		}
	}
}
