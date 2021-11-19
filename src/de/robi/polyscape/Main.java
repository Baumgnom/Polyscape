package de.robi.polyscape;

import de.robi.polyscape.frame.Frame;
import de.robi.polyscape.frame.Gradient;
import de.robi.polyscape.scape.Constraint;
import de.robi.polyscape.scape.Matrix;
import de.robi.polyscape.scape.Polynomial;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class Main {

	public static void main(String[] args) {


		/**/

		Random random = new Random();


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


			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					constraint.addConstraint(i, j, random.nextInt(5));
				}
			}

			Matrix matrix = constraint.createMatrix(5);
			System.out.println(matrix);
			matrix.solve();

			Polynomial polynomial = matrix.createPolynomial();

			Map<Double[], Double> map = constraint.getConstraints();
			for(Double[] pos : map.keySet()) {
				System.out.println("Expected at[" + pos[0] + "," + pos[1] + "]: \t" + map.get(pos) + "\t - polynom: " + polynomial.getValue(pos[0], pos[1]));
			}


			frame.setPolynomial(polynomial);

			System.out.println(matrix);
			System.out.println(polynomial);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
