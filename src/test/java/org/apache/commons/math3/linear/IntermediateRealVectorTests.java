package org.apache.commons.math3.linear;

import java.util.function.Supplier;
import org.apache.commons.math3.TestUtils;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
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

	protected void assertCloseExtracted(double[] m, double[] n, String msg, double tolerance) {
		if (m.length != n.length) {
			Assert.fail("vectors have different lengths");
		}
		for (int i = 0; i < m.length; i++) {
			Assert.assertEquals(msg + " " + i + " elements differ", m[i], n[i], tolerance);
		}
		return;
	}

}