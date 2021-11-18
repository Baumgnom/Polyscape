package de.robi.polyscape.scape;

import java.util.*;

public class Polynomial {
	private final Map<Integer[], Double> coefficients;


	public Polynomial() {
		this.coefficients = new HashMap<>();
	}
	public Polynomial(Map<Integer[], Double> coefficients) {
		this.coefficients = new HashMap<>();

		for(Integer[] exponent : coefficients.keySet()) {
			if(exponent.length != 2) continue;
			if(exponent[0] < 0 || exponent[1] < 0) continue;
			double value = coefficients.get(exponent);
			if(this.coefficients.containsKey(exponent)) {
				value += this.coefficients.get(exponent);
			}
			this.coefficients.put(exponent, value);
		}
	}

	public double getValue(double x, double y) {
		double value = 0;
		for(Integer[] exponent : coefficients.keySet()) {
			int powerX = exponent[0];
			int powerY = exponent[1];
			value += Math.pow(x, powerX) * Math.pow(y, powerY) * coefficients.get(exponent);
		}

		return value;
	}

	public void setCoefficient(int powerX, int powerY, double value) {
		if(powerX < 0 || powerY < 0) return;

		Integer[] exponent = new Integer[] {powerX, powerY};

		if(this.coefficients.containsKey(exponent)) {
			value += this.coefficients.get(exponent);
		}
		this.coefficients.put(exponent, value);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for(Integer[] exponent : coefficients.keySet()) {
			s.append("[").append(exponent[0]).append(", ").append(exponent[1]).append(", ").append(coefficients.get(exponent)).append("],");
		}

		return s.substring(0, s.length()-1);
	}

	public static Polynomial createPolynomial(String function) {
		Map<Integer[], Double> coefficients = new HashMap<>();

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

			Integer[] exponent = new Integer[] {xComp, yComp};
			if(coefficients.containsKey(exponent)) {
				value += coefficients.get(exponent);
			}
			coefficients.put(exponent, value);
		}

		return new Polynomial(coefficients);
	}
}
