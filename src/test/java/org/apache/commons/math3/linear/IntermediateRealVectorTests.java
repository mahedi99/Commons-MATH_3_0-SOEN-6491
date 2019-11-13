package org.apache.commons.math3.linear;

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

}