package de.robi.polyscape;

import de.robi.polyscape.frame.Frame;
import de.robi.polyscape.frame.Gradient;
import de.robi.polyscape.scape.Exponent;
import de.robi.polyscape.scape.Polynomial;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		Polynomial polynomial = new Polynomial();
		polynomial.setCoefficient(0, 0, 100);
		polynomial.setCoefficient(3, 1, 1);
		polynomial.setCoefficient(0, 2, -1);

		Polynomial polynomial2 = Polynomial.createPolynomial("100 + x^3y-1y^2");
		System.out.println(polynomial2);

		Gradient gradient = new Gradient();
		gradient.addColor(-500, Color.RED);
		gradient.addColor(0, Color.GREEN);
		gradient.addColor(500, Color.BLUE);

		Frame frame = new Frame();
		frame.setGradient(gradient);
		frame.setPolynomial(polynomial2);
	}
}
