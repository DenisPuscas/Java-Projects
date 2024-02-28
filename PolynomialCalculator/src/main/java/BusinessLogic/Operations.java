package BusinessLogic;

import DataModels.Monomial;
import DataModels.Polynomial;

import java.util.Map;

public class Operations {

    public static Polynomial addition(Polynomial p1, Polynomial p2) {
        Polynomial res = new Polynomial();
        res.getPolynomial().putAll(p1.getPolynomial());
        for (Map.Entry<Integer, Monomial> entry : p2.getPolynomial().entrySet()) {
            Integer key = entry.getKey();
            if (res.getPolynomial().containsKey(key)) {
                Monomial mon = res.getPolynomial().get(key).addition(entry.getValue());
                if (mon.getCoefficient() == 0) {
                    res.getPolynomial().remove(key);
                } else {
                    res.getPolynomial().replace(key, mon);
                }
            } else {
                res.getPolynomial().put(key, entry.getValue());
            }
        }
        return res;
    }

    public static Polynomial subtraction(Polynomial p1, Polynomial p2) {
        Polynomial res = new Polynomial();
        res.getPolynomial().putAll(p1.getPolynomial());
        for (Map.Entry<Integer, Monomial> entry : p2.getPolynomial().entrySet()) {
            Integer key = entry.getKey();
            if (res.getPolynomial().containsKey(key)) {
                Monomial mon = res.getPolynomial().get(key).subtraction(entry.getValue());
                if (!mon.isFraction() && mon.getCoefficient() == 0) {
                    res.getPolynomial().remove(key);
                } else {
                    res.getPolynomial().replace(key, mon);
                }
            } else {
                res.getPolynomial().put(key, new Monomial(-1 * entry.getValue().getCoefficient(), key));
            }
        }
        if (res.getPolynomial().isEmpty()){
            res.getPolynomial().put(0, new Monomial(0, 0));
        }
        return res;
    }

    public static Polynomial multiplication(Polynomial p1, Polynomial p2) {
        Polynomial res = new Polynomial();
        Polynomial temp = new Polynomial();
        for (Monomial m1 : p1.getPolynomial().values()) {
            for (Monomial m2 : p2.getPolynomial().values()) {
                Monomial m3 = m1.multiplication(m2);
                temp.getPolynomial().put(m3.getExponent(), m3);
            }
            res = addition(res, temp);
            temp.getPolynomial().clear();
        }
        return res;
    }

    public static Polynomial monomMultiplication(Polynomial p, Monomial m) {
        Polynomial res = new Polynomial();
        Polynomial temp = new Polynomial();
        for (Monomial m1 : p.getPolynomial().values()) {
            Monomial m2 = m1.multiplication(m);
            temp.getPolynomial().put(m2.getExponent(), m2);
            res = addition(res, temp);
            temp.getPolynomial().clear();
        }
        return res;
    }

    public static String division(Polynomial p1, Polynomial p2) {
        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial();
        Polynomial pol1, pol2;
        if (p1.getPolynomial().get(p1.getPolynomial().firstKey()).getExponent() >= p2.getPolynomial().get(p2.getPolynomial().firstKey()).getExponent()) {
            pol1 = p1;
            pol2 = p2;
        } else {
            pol1 = p2;
            pol2 = p1;
        }
        remainder.getPolynomial().put(pol1.getPolynomial().firstKey(), pol1.getPolynomial().get(pol1.getPolynomial().firstKey()));
        do {
            Monomial aux = remainder.getPolynomial().get(pol1.getPolynomial().firstKey()).division(pol2.getPolynomial().get(pol2.getPolynomial().firstKey()));
            quotient.getPolynomial().put(aux.getExponent(), aux);
            remainder = subtraction(pol1, monomMultiplication(pol2, quotient.getPolynomial().get(quotient.getPolynomial().lastKey())));
            pol1 = remainder;
        } while (remainder.getPolynomial().get(remainder.getPolynomial().firstKey()).getExponent() >= pol2.getPolynomial().get(pol2.getPolynomial().firstKey()).getExponent() && (!remainder.toString().equals("0")));

        return "c: " + quotient.toString() + "  r: " + remainder.toString();
    }

    public static Polynomial derivative(Polynomial p) {
        Polynomial res = new Polynomial();
        for (Monomial m : p.getPolynomial().values()) {
            Monomial aux = m.derivate();
            if (aux.getCoefficient() != 0) {
                res.getPolynomial().put(aux.getExponent(), aux);
            }
        }
        return res;
    }

    public static Polynomial integral(Polynomial p) {
        Polynomial res = new Polynomial();
        for (Monomial m : p.getPolynomial().values()) {
            res.getPolynomial().put(m.integral().getExponent(), m.integral());
        }
        return res;
    }
}
