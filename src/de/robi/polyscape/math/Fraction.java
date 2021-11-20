package de.robi.polyscape.math;

public record Fraction(long numerator, long denominator) {
	private static int accuracy = 10;

	public Fraction {
		int signN = numerator < 0? -1 : 1;
		int signD = denominator < 0? -1 : 1;
		int sign = signN * signD;

		numerator = numerator * signN;
		denominator = denominator * signD;


		if(denominator != 0) {
			long max = Math.max(numerator, denominator);
			long min = Math.min(numerator, denominator);
			while(min != 0) {
				long remainder = max % min;
				max = min;
				min = remainder;
			}

			numerator /= max;
			denominator /= max;
		}

		numerator = numerator * sign;
	}

	public static Fraction createFraction(double value) {
		double fractional = value - Math.floor(value);
		long floor = (long) Math.floor(value);
		double error = Math.pow(10, -accuracy);

		if(fractional < error) {
			return new Fraction(floor, 1);
		}
		if(1 - fractional < error) {
			return new Fraction(floor + 1, 1);
		}

		long lowerNumerator = 0;
		long lowerDenominator = 1;
		long upperNumerator = 1;
		long upperDenominator = 0;


		while(true) {
			long middleNumerator = lowerNumerator + upperNumerator;
			long middleDenominator = lowerDenominator + upperDenominator;

			double middle = (double) middleNumerator / middleDenominator;

			if(Math.abs(fractional - middle) < error) {
				return new Fraction(middleNumerator + floor * middleDenominator, middleDenominator);
			}

			if(fractional < middle) {
				upperNumerator = middleNumerator;
				upperDenominator = middleDenominator;
			} else {
				lowerNumerator = middleNumerator;
				lowerDenominator = middleDenominator;
			}
		}
	}

	public double value() {
		return (double) numerator / denominator;
	}

	public Fraction add(Fraction fraction) {
		return new Fraction(this.numerator * fraction.denominator + this.denominator * fraction.numerator, this.denominator * fraction.denominator);
	}

	public Fraction multiply(Fraction fraction) {
		return new Fraction(this.numerator * fraction.numerator, this.denominator * fraction.denominator);
	}

	public Fraction multiply(int scalar) {
		return new Fraction(this.numerator * scalar, this.denominator);
	}

	public Fraction invert() {
		return new Fraction(this.denominator, this.numerator);
	}


	@Override
	public String toString() {
		if(denominator == 1) return "" + numerator;
		return numerator + "/" + denominator;
	}

	public static void setAccuracy(int accuracy) {
		Fraction.accuracy = accuracy;
	}
}
