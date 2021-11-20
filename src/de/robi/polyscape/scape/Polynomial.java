package de.robi.polyscape.scape;

import de.robi.polyscape.math.Tuple;

import java.util.*;

public class Polynomial {
	private final Map<Tuple<Integer>, Double> coefficients;


	public Polynomial() {
		this.coefficients = new HashMap<>();
	}
	public Polynomial(Map<Tuple<Integer>, Double> coefficients) {
		this.coefficients = new HashMap<>();

		for(Tuple<Integer> exponent : coefficients.keySet()) {
			if(exponent.x() < 0 || exponent.y() < 0) continue;
			double value = coefficients.get(exponent);
			if(this.coefficients.containsKey(exponent)) {
				value += this.coefficients.get(exponent);
			}
			this.coefficients.put(exponent, value);
		}
	}

	public double getValue(double x, double y) {
		double value = 0;
		for(Tuple<Integer> exponent : coefficients.keySet()) {
			int powerX = exponent.x();
			int powerY = exponent.y();
			value += Math.pow(x, powerX) * Math.pow(y, powerY) * coefficients.get(exponent);
		}

		return value;
	}

	public void setCoefficient(int powerX, int powerY, double value) {
		if(powerX < 0 || powerY < 0) return;

		Tuple<Integer> exponent = new Tuple<>(powerX, powerY);

		if(this.coefficients.containsKey(exponent)) {
			value += this.coefficients.get(exponent);
		}
		this.coefficients.put(exponent, value);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for(Tuple<Integer> exponent : coefficients.keySet()) {
			s.append("[").append(exponent.x()).append(", ").append(exponent.y()).append(", ").append(coefficients.get(exponent)).append("],");
		}

		return s.substring(0, s.length()-1);
	}

	public static Polynomial createPolynomial(String function) {
		Map<Tuple<Integer>, Double> coefficients = new HashMap<>();

		function = function.replaceAll("[+]*-", "+-");
		String[] parts = function.split("\\s*[+]\\s*");
		for(String part : parts) {
			part = part.replaceAll("[*]*x[\\^]?", "*x").replaceAll("[*]*y[\\^]?", "*y");
			String[] components = part.split("[*]");
			double value = 1;

			int xComp = 0;
			int yComp = 0;

			for(String comp : components) {
				if(comp.startsWith("x")) {
					if(comp.length() == 1) xComp++;
					else xComp += Integer.parseInt(components[1].substring(1));
				} else if(comp.startsWith("y")) {
					if(comp.length() == 1) yComp++;
					else yComp += Integer.parseInt(components[1].substring(1));
				} else if(comp.length() > 0){
					value *= Double.parseDouble(components[0].replaceAll(",", "."));
				}
			}

			Tuple<Integer> exponent = new Tuple<>(xComp, yComp);
			if(coefficients.containsKey(exponent)) {
				value += coefficients.get(exponent);
			}
			coefficients.put(exponent, value);
		}

		return new Polynomial(coefficients);
	}

	public static void main(String[] args) {
		Polynomial polynomial = new Polynomial();

		polynomial.setCoefficient(1, 1, 1);
		polynomial.setCoefficient(1, 1, 2);
		System.out.println(polynomial);
	}
}
