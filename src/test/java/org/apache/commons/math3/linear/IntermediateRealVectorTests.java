package org.apache.commons.math3.linear;

import java.util.ArrayList;
import java.util.function.Supplier;
import org.apache.commons.math3.TestUtils;
import org.apache.commons.math3.analysis.function.Abs;
import org.apache.commons.math3.analysis.function.Acos;
import org.apache.commons.math3.analysis.function.Asin;
import org.apache.commons.math3.analysis.function.Atan;
import org.apache.commons.math3.analysis.function.Cbrt;
import org.apache.commons.math3.analysis.function.Ceil;
import org.apache.commons.math3.analysis.function.Cos;
import org.apache.commons.math3.analysis.function.Cosh;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.commons.math3.analysis.function.Expm1;
import org.apache.commons.math3.analysis.function.Floor;
import org.apache.commons.math3.analysis.function.Inverse;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Log10;
import org.apache.commons.math3.analysis.function.Log1p;
import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.analysis.function.Rint;
import org.apache.commons.math3.analysis.function.Signum;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.analysis.function.Tan;
import org.apache.commons.math3.analysis.function.Tanh;
import org.apache.commons.math3.analysis.function.Ulp;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;
import org.junit.Assert;

/**
 * Test cases supercalss for the {@link SparseRealVectorTest} class.{@link ArrayRealVectorTest} class.
 *
 * @version $Id$
 */
public class IntermediateRealVectorTests {

	protected double[] vec1 = {1d, 2d, 3d};
	protected double[] vec2 = {4d, 5d, 6d};
	protected double[] vec3 = {7d, 8d, 9d};
	protected double[] vec4 = {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
	protected double[] vec5 = { -4d, 0d, 3d, 1d, -6d, 3d};
	protected double[] vec_null = {0d, 0d, 0d};
	protected Double[] dvec1 = {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
	protected double normTolerance = 10E-14;

	public IntermediateRealVectorTests() {
		super();
	}

	// method pull up as its type clone 2 resolved
	protected void testSerialExtracted(Supplier<RealVector> arg0) {
		RealVector v = arg0.get();
		Assert.assertEquals(v, TestUtils.serializeAndRecover(v));
	}

	protected void testOuterProductExtracted(Supplier<RealVector> arg0, Supplier<RealVector> arg1) {
		final RealVector u = arg0.get();
		final RealVector v = arg1.get();
		final RealMatrix uv = u.outerProduct(v);
		final double tol = Math.ulp(1d);
		Assert.assertEquals(4, uv.getEntry(0, 0), tol);
		Assert.assertEquals(-2, uv.getEntry(0, 1), tol);
		Assert.assertEquals(8, uv.getEntry(1, 0), tol);
		Assert.assertEquals(-4, uv.getEntry(1, 1), tol);
		Assert.assertEquals(-12, uv.getEntry(2, 0), tol);
		Assert.assertEquals(6, uv.getEntry(2, 1), tol);
	}

	protected void testMiscExtracted(Supplier<RealVector> arg0) {
		RealVector v1 = arg0.get();
		String out1 = v1.toString();
		Assert.assertTrue("some output ", out1.length() != 0);
		try {
			v1.checkVectorDimensions(2);
			Assert.fail("MathIllegalArgumentException expected");
		} catch (MathIllegalArgumentException ex) {
		}
	}

	protected void assertCloseExtracted(String msg, double[] m, double[] n, double tolerance) {
		if (m.length != n.length) {
			Assert.fail("vectors have different lengths");
		}
		for (int i = 0; i < m.length; i++) {
			Assert.assertEquals(msg + " " + i + " elements differ", m[i], n[i], tolerance);
		}
		return;
	}
	

	protected void testPredicatesExtracted(Supplier<RealVector> arg0, RealVector arg1, RealVector arg2,
			RealVector arg3) {
		RealVector v = arg0.get();
		Assert.assertFalse(v.isNaN());
		v.setEntry(1, Double.NaN);
		Assert.assertTrue(v.isNaN());
		Assert.assertFalse(v.isInfinite());
		v.setEntry(0, Double.POSITIVE_INFINITY);
		Assert.assertFalse(v.isInfinite());
		v.setEntry(1, 1);
		Assert.assertTrue(v.isInfinite());
		v.setEntry(0, 0);
		Assert.assertEquals(v, arg1);
		Assert.assertNotSame(v, arg2);
		Assert.assertNotSame(v, arg3);
	}
	
	protected void testMapFunctionsExtracted(ArrayList<RealVector> vector) {
		
	       RealVector v1 =vector.get(0); // preserving behaviour same variable names
	       RealVector vat = vector.get(1);
	       RealVector abs_v = vector.get(2);
	       RealVector cbrt_v = vector.get(3);
	       RealVector ceil_v = vector.get(4);
	       
	        //octave =  v1 .+ 2.0
	        RealVector v_mapAdd = v1.mapAdd(2.0d);
	        double[] result_mapAdd = {3d, 4d, 5d};
	        assertCloseExtracted("compare vectors" ,result_mapAdd,v_mapAdd.toArray(),normTolerance);

	        //octave =  v1 .+ 2.0
	        RealVector v_mapAddToSelf = v1.copy();
	        v_mapAddToSelf.mapAddToSelf(2.0d);
	        double[] result_mapAddToSelf = {3d, 4d, 5d};
	        assertCloseExtracted("compare vectors" ,result_mapAddToSelf,v_mapAddToSelf.toArray(),normTolerance);

	        //octave =  v1 .- 2.0
	        RealVector v_mapSubtract = v1.mapSubtract(2.0d);
	        double[] result_mapSubtract = {-1d, 0d, 1d};
	        assertCloseExtracted("compare vectors" ,result_mapSubtract,v_mapSubtract.toArray(),normTolerance);

	        //octave =  v1 .- 2.0
	        RealVector v_mapSubtractToSelf = v1.copy();
	        v_mapSubtractToSelf.mapSubtractToSelf(2.0d);
	        double[] result_mapSubtractToSelf = {-1d, 0d, 1d};
	        assertCloseExtracted("compare vectors" ,result_mapSubtractToSelf,v_mapSubtractToSelf.toArray(),normTolerance);

	        //octave =  v1 .* 2.0
	        RealVector v_mapMultiply = v1.mapMultiply(2.0d);
	        double[] result_mapMultiply = {2d, 4d, 6d};
	        assertCloseExtracted("compare vectors" ,result_mapMultiply,v_mapMultiply.toArray(),normTolerance);

	        //octave =  v1 .* 2.0
	        RealVector v_mapMultiplyToSelf = v1.copy();
	        v_mapMultiplyToSelf.mapMultiplyToSelf(2.0d);
	        double[] result_mapMultiplyToSelf = {2d, 4d, 6d};
	        assertCloseExtracted("compare vectors" ,result_mapMultiplyToSelf,v_mapMultiplyToSelf.toArray(),normTolerance);

	        //octave =  v1 ./ 2.0
	        RealVector v_mapDivide = v1.mapDivide(2.0d);
	        double[] result_mapDivide = {.5d, 1d, 1.5d};
	        assertCloseExtracted("compare vectors" ,result_mapDivide,v_mapDivide.toArray(),normTolerance);

	        //octave =  v1 ./ 2.0
	        RealVector v_mapDivideToSelf = v1.copy();
	        v_mapDivideToSelf.mapDivideToSelf(2.0d);
	        double[] result_mapDivideToSelf = {.5d, 1d, 1.5d};
	        assertCloseExtracted("compare vectors" ,result_mapDivideToSelf,v_mapDivideToSelf.toArray(),normTolerance);

	        //octave =  v1 .^ 2.0
	        RealVector v_mapPow = v1.map(new Power(2));
	        double[] result_mapPow = {1d, 4d, 9d};
	        assertCloseExtracted("compare vectors" ,result_mapPow,v_mapPow.toArray(),normTolerance);

	        //octave =  v1 .^ 2.0
	        RealVector v_mapPowToSelf = v1.copy();
	        v_mapPowToSelf.mapToSelf(new Power(2));
	        double[] result_mapPowToSelf = {1d, 4d, 9d};
	        assertCloseExtracted("compare vectors" ,result_mapPowToSelf,v_mapPowToSelf.toArray(),normTolerance);

	        //octave =  exp(v1)
	        RealVector v_mapExp = v1.map(new Exp());
	        double[] result_mapExp = {2.718281828459045e+00d,7.389056098930650e+00d, 2.008553692318767e+01d};
	        assertCloseExtracted("compare vectors" ,result_mapExp,v_mapExp.toArray(),normTolerance);

	        //octave =  exp(v1)
	        RealVector v_mapExpToSelf = v1.copy();
	        v_mapExpToSelf.mapToSelf(new Exp());
	        double[] result_mapExpToSelf = {2.718281828459045e+00d,7.389056098930650e+00d, 2.008553692318767e+01d};
	        assertCloseExtracted("compare vectors" ,result_mapExpToSelf,v_mapExpToSelf.toArray(),normTolerance);


	        //octave =  ???
	        RealVector v_mapExpm1 = v1.map(new Expm1());
	        double[] result_mapExpm1 = {1.718281828459045d,6.38905609893065d, 19.085536923187668d};
	        assertCloseExtracted("compare vectors" ,result_mapExpm1,v_mapExpm1.toArray(),normTolerance);

	        //octave =  ???
	        RealVector v_mapExpm1ToSelf = v1.copy();
	        v_mapExpm1ToSelf.mapToSelf(new Expm1());
	        double[] result_mapExpm1ToSelf = {1.718281828459045d,6.38905609893065d, 19.085536923187668d};
	        assertCloseExtracted("compare vectors" ,result_mapExpm1ToSelf,v_mapExpm1ToSelf.toArray(),normTolerance);

	        //octave =  log(v1)
	        RealVector v_mapLog = v1.map(new Log());
	        double[] result_mapLog = {0d,6.931471805599453e-01d, 1.098612288668110e+00d};
	        assertCloseExtracted("compare vectors" ,result_mapLog,v_mapLog.toArray(),normTolerance);

	        //octave =  log(v1)
	        RealVector v_mapLogToSelf = v1.copy();
	        v_mapLogToSelf.mapToSelf(new Log());
	        double[] result_mapLogToSelf = {0d,6.931471805599453e-01d, 1.098612288668110e+00d};
	        assertCloseExtracted("compare vectors" ,result_mapLogToSelf,v_mapLogToSelf.toArray(),normTolerance);

	        //octave =  log10(v1)
	        RealVector v_mapLog10 = v1.map(new Log10());
	        double[] result_mapLog10 = {0d,3.010299956639812e-01d, 4.771212547196624e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapLog10,v_mapLog10.toArray(),normTolerance);

	        //octave =  log(v1)
	        RealVector v_mapLog10ToSelf = v1.copy();
	        v_mapLog10ToSelf.mapToSelf(new Log10());
	        double[] result_mapLog10ToSelf = {0d,3.010299956639812e-01d, 4.771212547196624e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapLog10ToSelf,v_mapLog10ToSelf.toArray(),normTolerance);

	        //octave =  ???
	        RealVector v_mapLog1p = v1.map(new Log1p());
	        double[] result_mapLog1p = {0.6931471805599453d,1.0986122886681096d,1.3862943611198906d};
	        assertCloseExtracted("compare vectors" ,result_mapLog1p,v_mapLog1p.toArray(),normTolerance);

	        //octave =  ???
	        RealVector v_mapLog1pToSelf = v1.copy();
	        v_mapLog1pToSelf.mapToSelf(new Log1p());
	        double[] result_mapLog1pToSelf = {0.6931471805599453d,1.0986122886681096d,1.3862943611198906d};
	        assertCloseExtracted("compare vectors" ,result_mapLog1pToSelf,v_mapLog1pToSelf.toArray(),normTolerance);

	        //octave =  cosh(v1)
	        RealVector v_mapCosh = v1.map(new Cosh());
	        double[] result_mapCosh = {1.543080634815244e+00d,3.762195691083631e+00d, 1.006766199577777e+01d};
	        assertCloseExtracted("compare vectors" ,result_mapCosh,v_mapCosh.toArray(),normTolerance);

	        //octave =  cosh(v1)
	        RealVector v_mapCoshToSelf = v1.copy();
	        v_mapCoshToSelf.mapToSelf(new Cosh());
	        double[] result_mapCoshToSelf = {1.543080634815244e+00d,3.762195691083631e+00d, 1.006766199577777e+01d};
	        assertCloseExtracted("compare vectors" ,result_mapCoshToSelf,v_mapCoshToSelf.toArray(),normTolerance);

	        //octave =  sinh(v1)
	        RealVector v_mapSinh = v1.map(new Sinh());
	        double[] result_mapSinh = {1.175201193643801e+00d,3.626860407847019e+00d, 1.001787492740990e+01d};
	        assertCloseExtracted("compare vectors" ,result_mapSinh,v_mapSinh.toArray(),normTolerance);

	        //octave =  sinh(v1)
	        RealVector v_mapSinhToSelf = v1.copy();
	        v_mapSinhToSelf.mapToSelf(new Sinh());
	        double[] result_mapSinhToSelf = {1.175201193643801e+00d,3.626860407847019e+00d, 1.001787492740990e+01d};
	        assertCloseExtracted("compare vectors" ,result_mapSinhToSelf,v_mapSinhToSelf.toArray(),normTolerance);

	        //octave =  tanh(v1)
	        RealVector v_mapTanh = v1.map(new Tanh());
	        double[] result_mapTanh = {7.615941559557649e-01d,9.640275800758169e-01d,9.950547536867305e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapTanh,v_mapTanh.toArray(),normTolerance);

	        //octave =  tanh(v1)
	        RealVector v_mapTanhToSelf = v1.copy();
	        v_mapTanhToSelf.mapToSelf(new Tanh());
	        double[] result_mapTanhToSelf = {7.615941559557649e-01d,9.640275800758169e-01d,9.950547536867305e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapTanhToSelf,v_mapTanhToSelf.toArray(),normTolerance);

	        //octave =  cos(v1)
	        RealVector v_mapCos = v1.map(new Cos());
	        double[] result_mapCos = {5.403023058681398e-01d,-4.161468365471424e-01d, -9.899924966004454e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapCos,v_mapCos.toArray(),normTolerance);

	        //octave =  cos(v1)
	        RealVector v_mapCosToSelf = v1.copy();
	        v_mapCosToSelf.mapToSelf(new Cos());
	        double[] result_mapCosToSelf = {5.403023058681398e-01d,-4.161468365471424e-01d, -9.899924966004454e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapCosToSelf,v_mapCosToSelf.toArray(),normTolerance);

	        //octave =  sin(v1)
	        RealVector v_mapSin = v1.map(new Sin());
	        double[] result_mapSin = {8.414709848078965e-01d,9.092974268256817e-01d,1.411200080598672e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapSin,v_mapSin.toArray(),normTolerance);

	        //octave =  sin(v1)
	        RealVector v_mapSinToSelf = v1.copy();
	        v_mapSinToSelf.mapToSelf(new Sin());
	        double[] result_mapSinToSelf = {8.414709848078965e-01d,9.092974268256817e-01d,1.411200080598672e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapSinToSelf,v_mapSinToSelf.toArray(),normTolerance);

	        //octave =  tan(v1)
	        RealVector v_mapTan = v1.map(new Tan());
	        double[] result_mapTan = {1.557407724654902e+00d,-2.185039863261519e+00d,-1.425465430742778e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapTan,v_mapTan.toArray(),normTolerance);

	        //octave =  tan(v1)
	        RealVector v_mapTanToSelf = v1.copy();
	        v_mapTanToSelf.mapToSelf(new Tan());
	        double[] result_mapTanToSelf = {1.557407724654902e+00d,-2.185039863261519e+00d,-1.425465430742778e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapTanToSelf,v_mapTanToSelf.toArray(),normTolerance);

	        //octave =  acos(vat)
	        RealVector v_mapAcos = vat.map(new Acos());
	        double[] result_mapAcos = {1.570796326794897e+00d,1.047197551196598e+00d, 0.0d};
	        assertCloseExtracted("compare vectors" ,result_mapAcos,v_mapAcos.toArray(),normTolerance);

	        //octave =  acos(vat)
	        RealVector v_mapAcosToSelf = vat.copy();
	        v_mapAcosToSelf.mapToSelf(new Acos());
	        double[] result_mapAcosToSelf = {1.570796326794897e+00d,1.047197551196598e+00d, 0.0d};
	        assertCloseExtracted("compare vectors" ,result_mapAcosToSelf,v_mapAcosToSelf.toArray(),normTolerance);

	        //octave =  asin(vat)
	        RealVector v_mapAsin = vat.map(new Asin());
	        double[] result_mapAsin = {0.0d,5.235987755982989e-01d,1.570796326794897e+00d};
	        assertCloseExtracted("compare vectors" ,result_mapAsin,v_mapAsin.toArray(),normTolerance);

	        //octave =  asin(vat)
	        RealVector v_mapAsinToSelf = vat.copy();
	        v_mapAsinToSelf.mapToSelf(new Asin());
	        double[] result_mapAsinToSelf = {0.0d,5.235987755982989e-01d,1.570796326794897e+00d};
	        assertCloseExtracted("compare vectors" ,result_mapAsinToSelf,v_mapAsinToSelf.toArray(),normTolerance);

	        //octave =  atan(vat)
	        RealVector v_mapAtan = vat.map(new Atan());
	        double[] result_mapAtan = {0.0d,4.636476090008061e-01d,7.853981633974483e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapAtan,v_mapAtan.toArray(),normTolerance);

	        //octave =  atan(vat)
	        RealVector v_mapAtanToSelf = vat.copy();
	        v_mapAtanToSelf.mapToSelf(new Atan());
	        double[] result_mapAtanToSelf = {0.0d,4.636476090008061e-01d,7.853981633974483e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapAtanToSelf,v_mapAtanToSelf.toArray(),normTolerance);

	        //octave =  v1 .^-1
	        RealVector v_mapInv = v1.map(new Inverse());
	        double[] result_mapInv = {1d,0.5d,3.333333333333333e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapInv,v_mapInv.toArray(),normTolerance);

	        //octave =  v1 .^-1
	        RealVector v_mapInvToSelf = v1.copy();
	        v_mapInvToSelf.mapToSelf(new Inverse());
	        double[] result_mapInvToSelf = {1d,0.5d,3.333333333333333e-01d};
	        assertCloseExtracted("compare vectors" ,result_mapInvToSelf,v_mapInvToSelf.toArray(),normTolerance);

	        //octave =  abs(abs_v)
	        RealVector v_mapAbs = abs_v.map(new Abs());
	        double[] result_mapAbs = {1d,0d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapAbs,v_mapAbs.toArray(),normTolerance);

	        //octave = abs(abs_v)
	        RealVector v_mapAbsToSelf = abs_v.copy();
	        v_mapAbsToSelf.mapToSelf(new Abs());
	        double[] result_mapAbsToSelf = {1d,0d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapAbsToSelf,v_mapAbsToSelf.toArray(),normTolerance);

	        //octave =   sqrt(v1)
	        RealVector v_mapSqrt = v1.map(new Sqrt());
	        double[] result_mapSqrt = {1d,1.414213562373095e+00d,1.732050807568877e+00d};
	        assertCloseExtracted("compare vectors" ,result_mapSqrt,v_mapSqrt.toArray(),normTolerance);

	        //octave =  sqrt(v1)
	        RealVector v_mapSqrtToSelf = v1.copy();
	        v_mapSqrtToSelf.mapToSelf(new Sqrt());
	        double[] result_mapSqrtToSelf = {1d,1.414213562373095e+00d,1.732050807568877e+00d};
	        assertCloseExtracted("compare vectors" ,result_mapSqrtToSelf,v_mapSqrtToSelf.toArray(),normTolerance);


	        //octave =  ???
	        RealVector v_mapCbrt = cbrt_v.map(new Cbrt());
	        double[] result_mapCbrt = {-1.2599210498948732d,0d,1.2599210498948732d};
	        assertCloseExtracted("compare vectors" ,result_mapCbrt,v_mapCbrt.toArray(),normTolerance);

	        //octave = ???
	        RealVector v_mapCbrtToSelf = cbrt_v.copy();
	        v_mapCbrtToSelf.mapToSelf(new Cbrt());
	        double[] result_mapCbrtToSelf =  {-1.2599210498948732d,0d,1.2599210498948732d};
	        assertCloseExtracted("compare vectors" ,result_mapCbrtToSelf,v_mapCbrtToSelf.toArray(),normTolerance);

	        //octave =  ceil(ceil_v)
	        RealVector v_mapCeil = ceil_v.map(new Ceil());
	        double[] result_mapCeil = {-1d,1d,2d};
	        assertCloseExtracted("compare vectors" ,result_mapCeil,v_mapCeil.toArray(),normTolerance);

	        //octave = ceil(ceil_v)
	        RealVector v_mapCeilToSelf = ceil_v.copy();
	        v_mapCeilToSelf.mapToSelf(new Ceil());
	        double[] result_mapCeilToSelf =  {-1d,1d,2d};
	        assertCloseExtracted("compare vectors" ,result_mapCeilToSelf,v_mapCeilToSelf.toArray(),normTolerance);

	        //octave =  floor(ceil_v)
	        RealVector v_mapFloor = ceil_v.map(new Floor());
	        double[] result_mapFloor = {-2d,0d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapFloor,v_mapFloor.toArray(),normTolerance);

	        //octave = floor(ceil_v)
	        RealVector v_mapFloorToSelf = ceil_v.copy();
	        v_mapFloorToSelf.mapToSelf(new Floor());
	        double[] result_mapFloorToSelf =  {-2d,0d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapFloorToSelf,v_mapFloorToSelf.toArray(),normTolerance);

	        //octave =  ???
	        RealVector v_mapRint = ceil_v.map(new Rint());
	        double[] result_mapRint = {-1d,1d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapRint,v_mapRint.toArray(),normTolerance);

	        //octave = ???
	        RealVector v_mapRintToSelf = ceil_v.copy();
	        v_mapRintToSelf.mapToSelf(new Rint());
	        double[] result_mapRintToSelf =  {-1d,1d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapRintToSelf,v_mapRintToSelf.toArray(),normTolerance);

	        //octave =  ???
	        RealVector v_mapSignum = ceil_v.map(new Signum());
	        double[] result_mapSignum = {-1d,1d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapSignum,v_mapSignum.toArray(),normTolerance);

	        //octave = ???
	        RealVector v_mapSignumToSelf = ceil_v.copy();
	        v_mapSignumToSelf.mapToSelf(new Signum());
	        double[] result_mapSignumToSelf =  {-1d,1d,1d};
	        assertCloseExtracted("compare vectors" ,result_mapSignumToSelf,v_mapSignumToSelf.toArray(),normTolerance);


	        // Is with the used resolutions of limited value as test
	        //octave =  ???
	        RealVector v_mapUlp = ceil_v.map(new Ulp());
	        double[] result_mapUlp = {2.220446049250313E-16d,1.1102230246251565E-16d,2.220446049250313E-16d};
	        assertCloseExtracted("compare vectors" ,result_mapUlp,v_mapUlp.toArray(),normTolerance);

	        //octave = ???
	        RealVector v_mapUlpToSelf = ceil_v.copy();
	        v_mapUlpToSelf.mapToSelf(new Ulp());
	        double[] result_mapUlpToSelf = {2.220446049250313E-16d,1.1102230246251565E-16d,2.220446049250313E-16d};
	        assertCloseExtracted("compare vectors" ,result_mapUlpToSelf,v_mapUlpToSelf.toArray(),normTolerance);
		
	}

}