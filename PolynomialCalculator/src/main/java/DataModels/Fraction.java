package DataModels;

public class Fraction {
	private int numerator;
	private int denominator;

	public Fraction(int numerator, int denominator) {
		this.setNumerator(numerator);
		this.setDenominator(denominator);
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public Fraction addFraction(Fraction f) {
		if (this.denominator == f.denominator) {
			return new Fraction(this.numerator + f.numerator, this.denominator);
		}
		if (this.denominator % f.denominator == 0) {
			return new Fraction(this.numerator + f.numerator * this.denominator / f.denominator, this.denominator);
		}
		if (f.denominator % this.denominator == 0) {
			return new Fraction(this.numerator * f.denominator / this.denominator + f.numerator, f.denominator);
		}
		return new Fraction(this.numerator * f.denominator + f.numerator * this.denominator, this.denominator * f.denominator);
	}

	public Fraction subtFraction(Fraction f) {
		if (this.denominator == f.denominator) {
			return new Fraction(this.numerator - f.numerator, this.denominator);
		}
		if (this.denominator % f.denominator == 0) {
			return new Fraction(this.numerator - f.numerator * this.denominator / f.denominator, this.denominator);
		}
		if (f.denominator % this.denominator == 0) {
			return new Fraction(this.numerator * f.denominator / this.denominator - f.numerator, f.denominator);
		}
		return new Fraction(this.numerator * f.denominator - f.numerator * this.denominator, this.denominator * f.denominator);
	}

	public Fraction multFraction(Fraction f) {
		return new Fraction(this.numerator * f.numerator, this.denominator * f.denominator);
	}
	
	public Fraction divFraction(Fraction f) {
		return new Fraction(this.numerator * f.denominator, this.denominator * f.numerator);
	}
	
	public String toString() {
		return numerator + "/" + denominator;
	}

}
