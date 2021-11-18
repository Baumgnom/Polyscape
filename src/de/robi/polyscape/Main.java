package de.robi.polyscape;

import de.robi.polyscape.frame.Frame;
import de.robi.polyscape.frame.Gradient;
import de.robi.polyscape.scape.Constraint;
import de.robi.polyscape.scape.Matrix;
import de.robi.polyscape.scape.Polynomial;

import java.awt.*;
import java.util.Random;

public class Main {

	public static void main(String[] args) {


		/*Constraint constraint = new Constraint();
		constraint.addConstraint(1, 0, 1);
		constraint.addConstraint(0.8, 0.2, 1);
		constraint.addConstraint(0.5, 0.5, 1);
		constraint.addConstraint(0.3, 0.9, 1);
		constraint.addConstraint(0.5, 1.2, 1);
		constraint.addConstraint(1, 1, 2);
		constraint.addConstraint(0, 0, 0.7);
		constraint.addConstraint(1, -1, 0.8);
		constraint.addConstraint(1, -1, 0.8);
		constraint.addConstraint(-1, -1, 0.6);

		Matrix matrix = constraint.createMatrix(3);*/

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
			Matrix matrix = new Matrix(21, 22);

			for(int i = 0; i < matrix.getHeight(); i++) {
				for(int j = 0; j < matrix.getWidth(); j++) {
					matrix.set(i, j, random.nextInt(200) - 100);
				}
			}

			matrix.solve();


			Polynomial polynomial = matrix.createPolynomial();

			frame.setPolynomial(polynomial);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
