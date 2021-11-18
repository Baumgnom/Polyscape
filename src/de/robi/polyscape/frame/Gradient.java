package de.robi.polyscape.frame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Gradient {
	private static final Color FALLBACK = Color.RED;

	private final Map<Double, Color> gradient;

	public Gradient() {
		this.gradient = new HashMap<>();
	}

	public void addColor(double value, Color color) {
		this.gradient.put(value, color);
	}

	public Color getColor(double value) {
		Double lowerBound = null;
		Double upperBound = null;

		for(Double key : gradient.keySet()) {
			if(key <= value && (lowerBound == null || key > lowerBound)) {
				lowerBound = key;
			}
			if(key >= value && (upperBound == null || key < upperBound)) {
				upperBound = key;
			}
		}

		if(lowerBound != null && lowerBound.equals(upperBound)) {
			return gradient.get(lowerBound);
		} else if(lowerBound != null && upperBound != null) {
			double distance = upperBound - lowerBound;
			double position = value - lowerBound;
			double weightA = position / distance;
			double weightB = 1 - weightA;
			Color colorA = gradient.get(upperBound);
			Color colorB = gradient.get(lowerBound);
			int red = (int) (colorA.getRed() * weightA + colorB.getRed() * weightB);
			int blue = (int) (colorA.getBlue() * weightA + colorB.getBlue() * weightB);
			int green = (int) (colorA.getGreen() * weightA + colorB.getGreen() * weightB);
			return new Color(red, green, blue);
		} else if(lowerBound != null) {
			return gradient.get(lowerBound);
		} else if(upperBound != null) {
			return gradient.get(upperBound);
		} else {
			return FALLBACK;
		}
	}
}
