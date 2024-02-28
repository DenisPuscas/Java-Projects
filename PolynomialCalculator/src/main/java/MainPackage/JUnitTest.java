package MainPackage;

import BusinessLogic.Operations;
import DataModels.Polynomial;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JUnitTest {
	private static int nrTesteExecutate = 0;
	private static int nrTesteCuSucces = 0;

	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("S-au executat " + nrTesteExecutate + " teste din care "+ nrTesteCuSucces + " au avut succes!");
	}

	@Before
	public void setUp() {
		nrTesteExecutate++;
	}

	@Test
	public void testRead() throws Exception {
		Polynomial pol = Polynomial.read("x3+2x2+4x-2");
		assertNotNull(pol);
		assertEquals(pol.toString(),"x3+2x2+4x-2");
		nrTesteCuSucces++;
	}

	@Test
	public void testAddition() throws Exception {
		Polynomial pol1 = Polynomial.read("4x5-3x4+x2-8x+1");
		Polynomial pol2 = Polynomial.read("3x4-x3+x2+2x-1");
		assertNotNull(Operations.addition(pol1, pol2));
		assertEquals(Operations.addition(pol1, pol2).toString(),"4x5-x3+2x2-6x");
		nrTesteCuSucces++;
	}

	@Test
	public void testSubtraction() throws Exception {
		Polynomial pol1 = Polynomial.read("4x5-3x4+x2-8x+1");
		Polynomial pol2 = Polynomial.read("3x4-x3+x2+2x-1");
		assertNotNull(Operations.subtraction(pol1, pol2));
		assertEquals(Operations.subtraction(pol1, pol2).toString(),"4x5-6x4+x3-10x+2");
		nrTesteCuSucces++;
	}

	@Test
	public void testMultiplication() throws Exception {
		Polynomial pol1 = Polynomial.read("3x2-x+1");
		Polynomial pol2 = Polynomial.read("x-2");
		assertNotNull(Operations.multiplication(pol1, pol2));
		assertEquals(Operations.multiplication(pol1, pol2).toString(),"3x3-7x2+3x-2");
		nrTesteCuSucces++;
	}

	@Test
	public void testDivision() throws Exception {
		Polynomial pol1 = Polynomial.read("x3-2x2+6x-5");
		Polynomial pol2 = Polynomial.read("x2-1");
		assertNotNull(Operations.division(pol1, pol2));
		assertEquals(Operations.division(pol1, pol2),"c: x-2  r: 7x-7");
		nrTesteCuSucces++;
	}

	@Test
	public void testDerivative() throws Exception {
		Polynomial pol = Polynomial.read("x3-2x2+6x-5");
		assertNotNull(Operations.derivative(pol));
		assertEquals(Operations.derivative(pol).toString(),"3x2-4x+6");
		nrTesteCuSucces++;
	}

	@Test
	public void testIntegral() throws Exception {
		Polynomial pol = Polynomial.read("x3+4x2+5");
		assertNotNull(Operations.integral(pol));
		assertEquals(Operations.integral(pol).toString(),"1/4x4+4/3x3+5x");
		nrTesteCuSucces++;
	}
}
