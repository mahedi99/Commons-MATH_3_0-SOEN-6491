/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

import org.apache.commons.math3.TestUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.analysis.function.Abs;
import org.apache.commons.math3.analysis.function.Acos;
import org.apache.commons.math3.analysis.function.Asin;
import org.apache.commons.math3.analysis.function.Atan;
import org.apache.commons.math3.analysis.function.Cbrt;
import org.apache.commons.math3.analysis.function.Cosh;
import org.apache.commons.math3.analysis.function.Cos;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.commons.math3.analysis.function.Expm1;
import org.apache.commons.math3.analysis.function.Inverse;
import org.apache.commons.math3.analysis.function.Log10;
import org.apache.commons.math3.analysis.function.Log1p;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.analysis.function.Tanh;
import org.apache.commons.math3.analysis.function.Tan;
import org.apache.commons.math3.analysis.function.Floor;
import org.apache.commons.math3.analysis.function.Ceil;
import org.apache.commons.math3.analysis.function.Rint;
import org.apache.commons.math3.analysis.function.Signum;
import org.apache.commons.math3.analysis.function.Ulp;
import org.apache.commons.math3.analysis.function.Power;

/**
 * Test cases for the {@link OpenMapRealVector} class.
 *
 * @version $Id$
 */
public class SparseRealVectorTest extends IntermediateVectorTest{

    // Testclass to test the RealVector interface
    // only with enough content to support the test
    public static class SparseRealVectorTestImpl extends RealVector implements Serializable {

        private static final long serialVersionUID = -6251371752518113791L;
        /** Entries of the vector. */
        protected double data[];

        public SparseRealVectorTestImpl(double[] d) {
            data = d.clone();
        }

        private UnsupportedOperationException unsupported() {
            return new UnsupportedOperationException("Not supported, unneeded for test purposes");
        }

        @Override
        public RealVector map(UnivariateFunction function) {
            throw unsupported();
        }

        @Override
        public RealVector mapToSelf(UnivariateFunction function) {
            throw unsupported();
        }

        @Override
        public Iterator<Entry> iterator() {
            throw unsupported();
        }

        @Override
        public RealVector copy() {
            return new SparseRealVectorTestImpl(data);
        }

        @Override
        public RealVector add(RealVector v) {
            throw unsupported();
        }


        @Override
        public RealVector subtract(RealVector v) {
            throw unsupported();
        }

        @Override
        public RealVector mapAdd(double d) {
            throw unsupported();
        }

        @Override
        public RealVector mapAddToSelf(double d) {
            throw unsupported();
        }

        @Override
        public RealVector mapSubtract(double d) {
            throw unsupported();
        }

        @Override
        public RealVector mapSubtractToSelf(double d) {
            throw unsupported();
        }

        @Override
        public RealVector mapMultiply(double d) {
            double[] out = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                out[i] = data[i] * d;
            }
            return new OpenMapRealVector(out);
        }

        @Override
        public RealVector mapMultiplyToSelf(double d) {
            throw unsupported();
        }

        @Override
        public RealVector mapDivide(double d) {
            throw unsupported();
        }

        @Override
        public RealVector mapDivideToSelf(double d) {
            throw unsupported();
        }

        @Override
        public RealVector ebeMultiply(RealVector v) {
            throw unsupported();
        }

        @Override
        public RealVector ebeDivide(RealVector v) {
            throw unsupported();
        }

        @Override
        public double dotProduct(RealVector v) {
            double dot = 0;
            for (int i = 0; i < data.length; i++) {
                dot += data[i] * v.getEntry(i);
            }
            return dot;
        }

        @Override
        public double getNorm() {
            throw unsupported();
        }

        @Override
        public double getL1Norm() {
            throw unsupported();
        }

        @Override
        public double getLInfNorm() {
            throw unsupported();
        }

        @Override
        public double getDistance(RealVector v) {
            throw unsupported();
        }

        @Override
        public double getL1Distance(RealVector v) {
            throw unsupported();
        }

        @Override
        public double getLInfDistance(RealVector v) {
            throw unsupported();
        }

        @Override
        public RealVector unitVector() {
            throw unsupported();
        }

        @Override
        public void unitize() {
            throw unsupported();
        }

        @Override
        public RealVector projection(RealVector v) {
            throw unsupported();
        }

        @Override
        public RealMatrix outerProduct(RealVector v) {
            throw unsupported();
        }

        @Override
        public double getEntry(int index) {
            return data[index];
        }

        @Override
        public int getDimension() {
            return data.length;
        }

        @Override
        public RealVector append(RealVector v) {
            throw unsupported();
        }

        @Override
        public RealVector append(double d) {
            throw unsupported();
        }

        @Override
        public RealVector getSubVector(int index, int n) {
            throw unsupported();
        }

        @Override
        public void setEntry(int index, double value) {
            data[index] = value;
        }

        @Override
        public void setSubVector(int index, RealVector v) {
            throw unsupported();
        }

        @Override
        public void set(double value) {
            throw unsupported();
        }

        @Override
        public double[] toArray() {
            return data.clone();
        }

        @Override
        public boolean isNaN() {
            throw unsupported();
        }

        @Override
        public boolean isInfinite() {
            throw unsupported();
        }

    }

    @Test
    public void testConstructors() {

        OpenMapRealVector v0 = new OpenMapRealVector();
        Assert.assertEquals("testData len", 0, v0.getDimension());

        OpenMapRealVector v1 = new OpenMapRealVector(7);
        Assert.assertEquals("testData len", 7, v1.getDimension());
        Assert.assertEquals("testData is 0.0 ", 0.0, v1.getEntry(6), 0);

        OpenMapRealVector v3 = new OpenMapRealVector(vec1);
        Assert.assertEquals("testData len", 3, v3.getDimension());
        Assert.assertEquals("testData is 2.0 ", 2.0, v3.getEntry(1), 0);

        //SparseRealVector v4 = new SparseRealVector(vec4, 3, 2);
        //Assert.assertEquals("testData len", 2, v4.getDimension());
        //Assert.assertEquals("testData is 4.0 ", 4.0, v4.getEntry(0));
        //try {
        //    new SparseRealVector(vec4, 8, 3);
        //    Assert.fail("MathIllegalArgumentException expected");
        //} catch (MathIllegalArgumentException ex) {
            // expected behavior
        //}

        RealVector v5_i = new OpenMapRealVector(dvec1);
        Assert.assertEquals("testData len", 9, v5_i.getDimension());
        Assert.assertEquals("testData is 9.0 ", 9.0, v5_i.getEntry(8), 0);

        OpenMapRealVector v5 = new OpenMapRealVector(dvec1);
        Assert.assertEquals("testData len", 9, v5.getDimension());
        Assert.assertEquals("testData is 9.0 ", 9.0, v5.getEntry(8), 0);

        OpenMapRealVector v7 = new OpenMapRealVector(v1);
        Assert.assertEquals("testData len", 7, v7.getDimension());
        Assert.assertEquals("testData is 0.0 ", 0.0, v7.getEntry(6), 0);

        SparseRealVectorTestImpl v7_i = new SparseRealVectorTestImpl(vec1);

        OpenMapRealVector v7_2 = new OpenMapRealVector(v7_i);
        Assert.assertEquals("testData len", 3, v7_2.getDimension());
        Assert.assertEquals("testData is 0.0 ", 2.0d, v7_2.getEntry(1), 0);

        OpenMapRealVector v8 = new OpenMapRealVector(v1);
        Assert.assertEquals("testData len", 7, v8.getDimension());
        Assert.assertEquals("testData is 0.0 ", 0.0, v8.getEntry(6), 0);

    }

    @Test
    public void testDataInOut() {

        OpenMapRealVector v1 = new OpenMapRealVector(vec1);
        OpenMapRealVector v2 = new OpenMapRealVector(vec2);
        OpenMapRealVector v4 = new OpenMapRealVector(vec4);
        SparseRealVectorTestImpl v2_t = new SparseRealVectorTestImpl(vec2);

        RealVector v_append_1 = v1.append(v2);
        Assert.assertEquals("testData len", 6, v_append_1.getDimension());
        Assert.assertEquals("testData is 4.0 ", 4.0, v_append_1.getEntry(3), 0);

        RealVector v_append_2 = v1.append(2.0);
        Assert.assertEquals("testData len", 4, v_append_2.getDimension());
        Assert.assertEquals("testData is 2.0 ", 2.0, v_append_2.getEntry(3), 0);

        RealVector v_append_4 = v1.append(v2_t);
        Assert.assertEquals("testData len", 6, v_append_4.getDimension());
        Assert.assertEquals("testData is 4.0 ", 4.0, v_append_4.getEntry(3), 0);

        RealVector vout5 = v4.getSubVector(3, 3);
        Assert.assertEquals("testData len", 3, vout5.getDimension());
        Assert.assertEquals("testData is 4.0 ", 5.0, vout5.getEntry(1), 0);
        try {
            v4.getSubVector(3, 7);
            Assert.fail("OutOfRangeException expected");
        } catch (OutOfRangeException ex) {
            // expected behavior
        }

        OpenMapRealVector v_set1 = v1.copy();
        v_set1.setEntry(1, 11.0);
        Assert.assertEquals("testData is 11.0 ", 11.0, v_set1.getEntry(1), 0);
        try {
            v_set1.setEntry(3, 11.0);
            Assert.fail("OutOfRangeException expected");
        } catch (OutOfRangeException ex) {
            // expected behavior
        }

        OpenMapRealVector v_set2 = v4.copy();
        v_set2.setSubVector(3, v1);
        Assert.assertEquals("testData is 1.0 ", 1.0, v_set2.getEntry(3), 0);
        Assert.assertEquals("testData is 7.0 ", 7.0, v_set2.getEntry(6), 0);
        try {
            v_set2.setSubVector(7, v1);
            Assert.fail("OutOfRangeException expected");
        } catch (OutOfRangeException ex) {
            // expected behavior
        }

        OpenMapRealVector v_set3 = v1.copy();
        v_set3.set(13.0);
        Assert.assertEquals("testData is 13.0 ", 13.0, v_set3.getEntry(2), 0);

        try {
            v_set3.getEntry(23);
            Assert.fail("OutOfRangeException expected");
        } catch (OutOfRangeException ex) {
            // expected behavior
        }

        OpenMapRealVector v_set4 = v4.copy();
        v_set4.setSubVector(3, v2_t);
        Assert.assertEquals("testData is 1.0 ", 4.0, v_set4.getEntry(3), 0);
        Assert.assertEquals("testData is 7.0 ", 7.0, v_set4.getEntry(6), 0);
        try {
            v_set4.setSubVector(7, v2_t);
            Assert.fail("OutOfRangeException expected");
        } catch (OutOfRangeException ex) {
            // expected behavior
        }


    }

    @Test
    public void testBasicFunctions() {
        OpenMapRealVector v1 = new OpenMapRealVector(vec1);
        OpenMapRealVector v2 = new OpenMapRealVector(vec2);
        OpenMapRealVector v5 = new OpenMapRealVector(vec5);
        OpenMapRealVector v_null = new OpenMapRealVector(vec_null);

        OpenMapRealVector v_unitize = v1.copy();
        RealVector v_projection = v1.projection(v2);

        SparseRealVectorTestImpl v2_t = new SparseRealVectorTestImpl(vec2);

        SparseRealVectorTestImpl vt2 = new SparseRealVectorTestImpl(vec2);

        //duplicate statements
        super.testBasicFunctions(v1, v2, v5, v2_t, v_null, v_unitize, v_projection, vt2);

        //octave =  v1 + v2
        OpenMapRealVector v_add = v1.add(v2);
        double[] result_add = {5d, 7d, 9d};
        assertClose("compare vect" ,v_add.toArray(),result_add,normTolerance);

        //octave =  v1 - v2
        OpenMapRealVector v_subtract = v1.subtract(v2);
        double[] result_subtract = {-3d, -3d, -3d};
        assertClose("compare vect" ,v_subtract.toArray(),result_subtract,normTolerance);

        RealVector v_unitVector = v1.unitVector();
        RealVector v_unitVector_2 = v1.mapDivide(v1.getNorm());
        assertClose("compare vect" ,v_unitVector.toArray(),v_unitVector_2.toArray(),normTolerance);

        try {
            v_null.unitVector();
            Assert.fail("Expecting MathArithmeticException");
        } catch (MathArithmeticException ex) {
            // expected behavior
        }

        v_unitize.unitize();
        assertClose("compare vect" ,v_unitVector_2.toArray(),v_unitize.toArray(),normTolerance);
        try {
            v_null.unitize();
            Assert.fail("Expecting MathArithmeticException");
        } catch (MathArithmeticException ex) {
            // expected behavior
        }

        double[] result_projection = {1.662337662337662, 2.0779220779220777, 2.493506493506493};
        assertClose("compare vect", v_projection.toArray(), result_projection, normTolerance);

        RealVector v_projection_2 = v1.projection(v2_t);
        double[] result_projection_2 = {1.662337662337662, 2.0779220779220777, 2.493506493506493};
        assertClose("compare vect", v_projection_2.toArray(), result_projection_2, normTolerance);

        //Calling testMapFunctions() by using some common objects from testBasicFunctions()
        double[] vat_a = {0d, 0.5d, 1.0d};
        OpenMapRealVector vat = new OpenMapRealVector(vat_a);
        double[] abs_a = {-1.0d, 0.0d, 1.0d};
        OpenMapRealVector abs_v = new OpenMapRealVector(abs_a);
        double[] cbrt_a = {-2.0d, 0.0d, 2.0d};
        OpenMapRealVector cbrt_v = new OpenMapRealVector(cbrt_a);
        double[] ceil_a = {-1.1d, 0.9d, 1.1d};
        OpenMapRealVector ceil_v = new OpenMapRealVector(ceil_a);
        super.testMapFunctions(v1, vat, abs_v, cbrt_v, ceil_v);

    }

    @Test
    public void testOuterProduct() {
        final OpenMapRealVector u = new OpenMapRealVector(new double[] {1, 2, -3});
        final OpenMapRealVector v = new OpenMapRealVector(new double[] {4, -2});

        final RealMatrix uv = u.outerProduct(v);

        super.testOuterProduct(uv);
    }

//    @Test
//    public void testMisc() {
//        OpenMapRealVector v1 = new OpenMapRealVector(vec1);
//
//        //calling method in super class containing test cases
//        super.testMisc(v1);
//    }

    @Test
    public void testPredicates() {

        OpenMapRealVector v = new OpenMapRealVector(new double[] { 0, 1, 2 });

        //Calling method in the parent class for common statements
        super.testPredicates(v);

        v.setEntry(0, 0);
        Assert.assertEquals(v, new OpenMapRealVector(new double[] { 0, 1, 2 }));
        Assert.assertNotSame(v, new OpenMapRealVector(new double[] { 0, 1, 2 + FastMath.ulp(2)}));
        Assert.assertNotSame(v, new OpenMapRealVector(new double[] { 0, 1, 2, 3 }));

    }

    @Test
    public void testSerial()  {
        OpenMapRealVector v = new OpenMapRealVector(new double[] { 0, 1, 2 });
        super.testSerial(v);
    }

    /* Check that the operations do not throw an exception (cf. MATH-645). */
    @Test
    public void testConcurrentModification() {
        final RealVector u = new OpenMapRealVector(3, 1e-6);
        u.setEntry(0, 1);
        u.setEntry(1, 0);
        u.setEntry(2, 2);

        final RealVector v1 = new OpenMapRealVector(3, 1e-6);
        v1.setEntry(0, 0);
        v1.setEntry(1, 3);
        v1.setEntry(2, 0);

        u.ebeMultiply(v1);
        u.ebeDivide(v1);
    }
}
