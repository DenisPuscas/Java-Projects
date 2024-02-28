package DataModels;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
	private TreeMap<Integer, Monomial> polynomial;

	public Polynomial() {
		setPolynomial(new TreeMap<Integer, Monomial>(Collections.reverseOrder()));
	}

	public TreeMap<Integer, Monomial> getPolynomial() {
		return polynomial;
	}

	public void setPolynomial(TreeMap<Integer, Monomial> polynomial) {
		this.polynomial = polynomial;
	}

	public void addMonomial(Integer coef, Integer exp) {
		if (this.polynomial.containsKey(exp)) {
			this.polynomial.put(exp, new Monomial(this.polynomial.get(exp).getCoefficient() + coef, exp));
		} else {
			this.polynomial.put(exp, new Monomial(coef, exp));
		}
	}

	public static Polynomial read(String input) throws Exception {
		if (input.isEmpty() || input.equals("ex: -x3+2x2-x+10")) throw new Exception("Insert the polynomial");
		if (input.matches("(.*)[a-wyz`!@#$%^&*()_=,.<>/?:;'{}](.*)|(.*)xx(.*)|(.*)--(.*)|(.*)\\+\\+(.*)")) throw new Exception("Bad input");
		Polynomial res = new Polynomial();
		Matcher matcher = Pattern.compile("([+-]?\\d*)x(\\d*)|([+-]\\d*|\\d+)").matcher(input);
		while (matcher.find()) {
			if (!matcher.group().equals("")) {
				if (!(matcher.group(3) == null)) {
					res.addMonomial(Integer.parseInt(matcher.group(3)), 0);
				} else if ((matcher.group(1).equals("")) && matcher.group(2).equals("")) {
					res.addMonomial(1, 1);
				} else if ((matcher.group(1).equals(""))) {
					res.addMonomial(1, Integer.parseInt(matcher.group(2)));
				} else if (matcher.group(2).equals("")) {
					if (matcher.group(1).matches("-")) {
						res.addMonomial(-1, 1);
					} else if (matcher.group(1).matches("\\+")) {
						res.addMonomial(1, 1);
					} else {
						res.addMonomial(Integer.parseInt(matcher.group(1)), 1);
					}
				} else {
					if (matcher.group(1).matches("-")) {
						res.addMonomial(-1, Integer.parseInt(matcher.group(2)));
					} else if (matcher.group(1).matches("\\+")) {
						res.addMonomial(1, Integer.parseInt(matcher.group(2)));
					} else {
						res.addMonomial(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
					}
				}
			}
		}
		return res;
	}

	public String toString() {
		String res = "";
		Collection<Monomial> values = this.polynomial.values();
		for (Monomial mon : values) {
			res += mon.toString(res.isEmpty());
		}
		return res;
	}
}
