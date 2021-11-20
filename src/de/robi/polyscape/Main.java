package de.robi.polyscape;

import de.robi.polyscape.frame.Frame;
import de.robi.polyscape.frame.Gradient;
import de.robi.polyscape.math.Tuple;
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

			for(int x = 0; x < 5; x++) {
				for(int y = 0; y < 5; y++) {
					int px = (x - 2) * 3 + random.nextInt(3) - 1;
					int py = (y - 2) * 3 + random.nextInt(3) - 1;
					constraint.addConstraint(px, py,  Math.max(random.nextInt(3) + random.nextInt(3), 0));
				}
			}


			Matrix matrix;
			for(int grade = 5; ; grade++) {
				matrix = constraint.createMatrix(grade);
				matrix.solve();

				if(matrix.valid()) {
					break;
				}
			}

			Polynomial polynomial = matrix.createPolynomial();

			/*Map<Tuple<Double>, Double> map = constraint.getConstraints();
			for(Tuple<Double> pos : map.keySet()) {
				//System.out.println("Expected at[" + pos.x() + "," + pos.y() + "]: \t" + map.get(pos) + "\t - polynomial: " + polynomial.getValue(pos.x(), pos.y()));
				if(Math.abs(map.get(pos) - polynomial.getValue(pos.x(), pos.y())) > Math.pow(10, -10)) {
					System.out.println("Expected at[" + pos.x() + "," + pos.y() + "]: \t" + map.get(pos) + "\t - polynomial: " + polynomial.getValue(pos.x(), pos.y()));
				}
			}*/

			System.out.println("Created another polynomial!");

			frame.setPolynomial(polynomial);

			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
