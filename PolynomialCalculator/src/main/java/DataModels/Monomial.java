package DataModels;

public class Monomial {
	private int coefficient;
	private int exponent;
	private Fraction fraction;
	private boolean isFraction;

	public Monomial(int coef, int exp) {
		coefficient = coef;
		exponent = exp;
		isFraction = false;
	}

	public Monomial(Fraction coef, int exp) {
		if (coef.getNumerator() % coef.getDenominator() == 0) {
			coefficient = coef.getNumerator() / coef.getDenominator();
			exponent = exp;
			isFraction = false;
		} else {
			fraction = coef;
			exponent = exp;
			isFraction = true;
		}
	}

	public int getCoefficient() {
		return coefficient;
	}

	public int getExponent() {
		return exponent;
	}
	
	public boolean isFraction() {
		return isFraction;
	}

	public Monomial addition(Monomial m) {
		if (this.isFraction && m.isFraction) {
			return new Monomial(this.fraction.addFraction(m.fraction), this.exponent);
		}
		if (this.isFraction) {
			return new Monomial(this.fraction.addFraction(new Fraction(m.getCoefficient(), 1)), this.exponent);
		}
		if (m.isFraction) {
			return new Monomial(new Fraction(this.getCoefficient(), 1).addFraction(m.fraction), this.exponent);
		}
		return new Monomial(this.coefficient + m.coefficient, this.exponent);
	}

	public Monomial subtraction(Monomial m) {
		if (this.isFraction && m.isFraction) {
			return new Monomial(this.fraction.subtFraction(m.fraction), this.exponent);
		}
		if (this.isFraction) {
			return new Monomial(this.fraction.subtFraction(new Fraction(m.getCoefficient(), 1)), this.exponent);
		}
		if (m.isFraction) {
			return new Monomial(new Fraction(this.getCoefficient(), 1).subtFraction(m.fraction), this.exponent);
		}
		return new Monomial(this.coefficient - m.coefficient, this.exponent);
	}

	public Monomial multiplication(Monomial m) {
		if (this.isFraction && m.isFraction) {
			return new Monomial(this.fraction.multFraction(m.fraction), this.exponent + m.exponent);
		}
		if (this.isFraction) {
			return new Monomial(this.fraction.multFraction(new Fraction(m.getCoefficient(), 1)), this.exponent + m.exponent);
		}
		if (m.isFraction) {
			return new Monomial(new Fraction(this.getCoefficient(), 1).multFraction(m.fraction), this.exponent + m.exponent);
		}
		return new Monomial(this.coefficient * m.coefficient, this.exponent + m.exponent);
	}

	public Monomial division(Monomial m) {
		if (this.isFraction && m.isFraction) {
			return new Monomial(this.fraction.divFraction(m.fraction), this.exponent - m.exponent);
		}
		if (this.isFraction) {
			return new Monomial(this.fraction.divFraction(new Fraction(m.getCoefficient(), 1)),
					this.exponent - m.exponent);
		}
		if (m.isFraction) {
			return new Monomial(new Fraction(this.getCoefficient(), 1).divFraction(m.fraction),
					this.exponent - m.exponent);
		}
		if (this.coefficient % m.coefficient == 0) {
			return new Monomial(this.coefficient / m.coefficient, this.exponent - m.exponent);
		}
		return new Monomial(new Fraction(this.coefficient, m.coefficient), this.exponent - m.exponent);
	}

	public Monomial derivate() {
		return new Monomial(this.coefficient * this.exponent, this.exponent - 1);
	}

	public Monomial integral() {
		if (this.exponent == 0) {
			return new Monomial(this.coefficient, this.exponent + 1);
		}
		return new Monomial(new Fraction(this.coefficient, this.exponent + 1), this.exponent + 1);
	}

	public String toString(boolean sign) {
		if (isFraction) {
			return ((fraction.getNumerator() > 0 && !sign) ? "+" : "") + fraction.toString() 
			+ ((exponent != 0) ? "x" : "") + ((exponent > 1) ? exponent : "");
		}
		return ((coefficient > 0 && !sign) ? "+" : "") + ((coefficient == -1 && exponent != 0) ? "-" : "")
				+ ((coefficient != 1 && coefficient != -1 || exponent == 0) ? coefficient : "")
				+ ((exponent != 0) ? "x" : "") + ((exponent > 1) ? exponent : "");
	}
}
