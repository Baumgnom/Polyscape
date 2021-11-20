package de.robi.polyscape.math;

public record Fraction(long numerator, long denominator) {
	private static int accuracy = 15;

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
		try {
			return new Fraction(Math.addExact(Math.multiplyExact(this.numerator, fraction.denominator), Math.multiplyExact(this.denominator, fraction.numerator)), Math.multiplyExact(this.denominator, fraction.denominator));
		} catch (ArithmeticException e) {
			return Fraction.createFraction(this.value() + fraction.value());
		}
	}

	public Fraction multiply(Fraction fraction) {
		try {
			return new Fraction(Math.multiplyExact(this.numerator, fraction.numerator), Math.multiplyExact(this.denominator, fraction.denominator));
		} catch (ArithmeticException ignored) {}

		Fraction fractionA = new Fraction(this.numerator, fraction.denominator);
		Fraction fractionB = new Fraction(fraction.numerator, this.denominator);
		try {
			return new Fraction(Math.multiplyExact(fractionA.numerator, fractionB.numerator), Math.multiplyExact(fractionA.denominator, fractionB.denominator));
		} catch (ArithmeticException e) {
			return Fraction.createFraction(this.value() * fraction.value());
		}
	}

	public Fraction multiply(long scalar) {
		return new Fraction(Math.multiplyExact(this.numerator, scalar), this.denominator);
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

	public static void main(String[] args) {
		Fraction f1 = new Fraction(-271121060, 1139119);

		Fraction f2 = new Fraction(-320725, 175013);

		Fraction e = new Fraction(-424419548, 1139119);

		Fraction ex = new Fraction(424419548, 1139119);

		System.out.println(424419548 * 271121060);
		//System.out.println(f1.value() + " * " + (-e.value()) + " + " + f2.value() + " = " + f2.add(f2.multiply(e.multiply(-1))).value());
	}
}
