package org.apache.commons.math3.linear;

import org.apache.commons.math3.TestUtils;
import org.apache.commons.math3.analysis.function.*;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;
import org.junit.Assert;

/**
 * @author Mahedi Hassan
 * 2019-11-15
 */

public class IntermediateVectorTest {

    protected double[][] ma1 = {{1d, 2d, 3d}, {4d, 5d, 6d}, {7d, 8d, 9d}};
    protected double[] vec1 = {1d, 2d, 3d};
    protected double[] vec2 = {4d, 5d, 6d};
    protected double[] vec3 = {7d, 8d, 9d};
    protected double[] vec4 = {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
    protected double[] vec5 = { -4d, 0d, 3d, 1d, -6d, 3d};
    protected double[] vec_null = {0d, 0d, 0d};
    protected Double[] dvec1 = {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
    protected double[][] mat1 = {{1d, 2d, 3d}, {4d, 5d, 6d},{ 7d, 8d, 9d}};

    // tolerances
    protected double entryTolerance = 10E-16;
    protected double normTolerance = 10E-14;

    /**
     * This method contains common statements for {@code testSerial} from both classes
     * classes {@link ArrayRealVector} and {@link SparseRealVector}
     *
     * @param
     */
    public void testBasicFunctions(RealVector v1, RealVector v2, RealVector v5, RealVector v2_t, RealVector v_null,
                                   RealVector v_unitize, RealVector v_projection, RealVector vt2){
        // emacs calc: [-4, 0, 3, 1, -6, 3] A --> 8.4261497731763586307
        double d_getNorm = v5.getNorm();
        Assert.assertEquals("compare values  ", 8.4261497731763586307, d_getNorm, normTolerance);

        // emacs calc: [-4, 0, 3, 1, -6, 3] vN --> 17
        double d_getL1Norm = v5.getL1Norm();
        Assert.assertEquals("compare values  ", 17.0, d_getL1Norm, normTolerance);

        // emacs calc: [-4, 0, 3, 1, -6, 3] vn --> 6
        double d_getLInfNorm = v5.getLInfNorm();
        Assert.assertEquals("compare values  ", 6.0, d_getLInfNorm, normTolerance);


        //octave =  sqrt(sumsq(v1-v2))
        double dist = v1.getDistance(v2);
        Assert.assertEquals("compare values  ",v1.subtract(v2).getNorm(), dist, normTolerance);

        //octave =  sqrt(sumsq(v1-v2))
        double dist_2 = v1.getDistance(v2_t);
        Assert.assertEquals("compare values  ", v1.subtract(v2).getNorm(),dist_2, normTolerance);

        //octave =  ???
        double d_getL1Distance = v1. getL1Distance(v2);
        Assert.assertEquals("compare values  ", 9d, d_getL1Distance, normTolerance);

        double d_getL1Distance_2 = v1.getL1Distance(v2_t);
        Assert.assertEquals("compare values  ", 9d, d_getL1Distance_2, normTolerance);

        //octave =  ???
        double d_getLInfDistance = v1.getLInfDistance(v2);
        Assert.assertEquals("compare values  ", 3d, d_getLInfDistance, normTolerance);

        double d_getLInfDistance_2 = v1. getLInfDistance(v2_t);
        Assert.assertEquals("compare values  ", 3d, d_getLInfDistance_2, normTolerance);

//        ArrayRealVectorTest.RealVectorTestImpl vt2 = new ArrayRealVectorTest.RealVectorTestImpl(vec2);
        RealVector v_add_i = v1.add(vt2);
        double[] result_add_i = {5d, 7d, 9d};
        assertClose("compare vect" ,v_add_i.toArray(),result_add_i,normTolerance);

        RealVector v_subtract_i = v1.subtract(vt2);
        double[] result_subtract_i = {-3d, -3d, -3d};
        assertClose("compare vect" ,v_subtract_i.toArray(),result_subtract_i,normTolerance);

        RealVector  v_ebeMultiply_2 = v1.ebeMultiply(v2_t);
        double[] result_ebeMultiply_2 = {4d, 10d, 18d};
        assertClose("compare vect" ,v_ebeMultiply_2.toArray(),result_ebeMultiply_2,normTolerance);

        RealVector  v_ebeDivide_2 = v1.ebeDivide(v2_t);
        double[] result_ebeDivide_2 = {0.25d, 0.4d, 0.5d};
        assertClose("compare vect" ,v_ebeDivide_2.toArray(),result_ebeDivide_2,normTolerance);

        // octave  dot(v1,v2)
        double dot =  v1.dotProduct(v2);
        Assert.assertEquals("compare val ",32d, dot, normTolerance);

        // octave  dot(v1,v2_t)
        double dot_2 =  v1.dotProduct(v2_t);
        Assert.assertEquals("compare val ",32d, dot_2, normTolerance);

        RealMatrix m_outerProduct = v1.outerProduct(v2);
        Assert.assertEquals("compare val ",4d, m_outerProduct.getEntry(0,0), normTolerance);

        RealMatrix m_outerProduct_2 = v1.outerProduct(v2_t);
        Assert.assertEquals("compare val ",4d, m_outerProduct_2.getEntry(0,0), normTolerance);

        RealVector v_unitVector = v1.unitVector();
        RealVector v_unitVector_2 = v1.mapDivide(v1.getNorm());
        assertClose("compare vect" ,v_unitVector.toArray(),v_unitVector_2.toArray(),normTolerance);

        try {
            v_null.unitVector();
            Assert.fail("Expecting MathArithmeticException");
        } catch (MathArithmeticException ex) {
            // expected behavior
        }
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

        v_unitize.unitize();
        assertClose("compare vect" ,v_unitVector_2.toArray(),v_unitize.toArray(),normTolerance);

        //Statements from testMisc
        String out1 = v1.toString();
        Assert.assertTrue("some output ",  out1.length()!=0);
        try {
            v1.checkVectorDimensions(2);
            Assert.fail("MathIllegalArgumentException expected");
        } catch (MathIllegalArgumentException ex) {
            // expected behavior
        }
    }


    public void testMapFunctions(RealVector v1, RealVector vat, RealVector abs_v, RealVector cbrt_v, RealVector ceil_v) {

        //octave =  v1 .+ 2.0
        RealVector v_mapAdd = v1.mapAdd(2.0d);
        double[] result_mapAdd = {3d, 4d, 5d};
        assertClose("compare vectors" ,result_mapAdd,v_mapAdd.toArray(),normTolerance);

        //octave =  v1 .+ 2.0
        RealVector v_mapAddToSelf = v1.copy();
        v_mapAddToSelf.mapAddToSelf(2.0d);
        double[] result_mapAddToSelf = {3d, 4d, 5d};
        assertClose("compare vectors" ,result_mapAddToSelf,v_mapAddToSelf.toArray(),normTolerance);

        //octave =  v1 .- 2.0
        RealVector v_mapSubtract = v1.mapSubtract(2.0d);
        double[] result_mapSubtract = {-1d, 0d, 1d};
        assertClose("compare vectors" ,result_mapSubtract,v_mapSubtract.toArray(),normTolerance);

        //octave =  v1 .- 2.0
        RealVector v_mapSubtractToSelf = v1.copy();
        v_mapSubtractToSelf.mapSubtractToSelf(2.0d);
        double[] result_mapSubtractToSelf = {-1d, 0d, 1d};
        assertClose("compare vectors" ,result_mapSubtractToSelf,v_mapSubtractToSelf.toArray(),normTolerance);

        //octave =  v1 .* 2.0
        RealVector v_mapMultiply = v1.mapMultiply(2.0d);
        double[] result_mapMultiply = {2d, 4d, 6d};
        assertClose("compare vectors" ,result_mapMultiply,v_mapMultiply.toArray(),normTolerance);

        //octave =  v1 .* 2.0
        RealVector v_mapMultiplyToSelf = v1.copy();
        v_mapMultiplyToSelf.mapMultiplyToSelf(2.0d);
        double[] result_mapMultiplyToSelf = {2d, 4d, 6d};
        assertClose("compare vectors" ,result_mapMultiplyToSelf,v_mapMultiplyToSelf.toArray(),normTolerance);

        //octave =  v1 ./ 2.0
        RealVector v_mapDivide = v1.mapDivide(2.0d);
        double[] result_mapDivide = {.5d, 1d, 1.5d};
        assertClose("compare vectors" ,result_mapDivide,v_mapDivide.toArray(),normTolerance);

        //octave =  v1 ./ 2.0
        RealVector v_mapDivideToSelf = v1.copy();
        v_mapDivideToSelf.mapDivideToSelf(2.0d);
        double[] result_mapDivideToSelf = {.5d, 1d, 1.5d};
        assertClose("compare vectors" ,result_mapDivideToSelf,v_mapDivideToSelf.toArray(),normTolerance);

        //octave =  v1 .^ 2.0
        RealVector v_mapPow = v1.map(new Power(2));
        double[] result_mapPow = {1d, 4d, 9d};
        assertClose("compare vectors" ,result_mapPow,v_mapPow.toArray(),normTolerance);

        //octave =  v1 .^ 2.0
        RealVector v_mapPowToSelf = v1.copy();
        v_mapPowToSelf.mapToSelf(new Power(2));
        double[] result_mapPowToSelf = {1d, 4d, 9d};
        assertClose("compare vectors" ,result_mapPowToSelf,v_mapPowToSelf.toArray(),normTolerance);

        //octave =  exp(v1)
        RealVector v_mapExp = v1.map(new Exp());
        double[] result_mapExp = {2.718281828459045e+00d,7.389056098930650e+00d, 2.008553692318767e+01d};
        assertClose("compare vectors" ,result_mapExp,v_mapExp.toArray(),normTolerance);

        //octave =  exp(v1)
        RealVector v_mapExpToSelf = v1.copy();
        v_mapExpToSelf.mapToSelf(new Exp());
        double[] result_mapExpToSelf = {2.718281828459045e+00d,7.389056098930650e+00d, 2.008553692318767e+01d};
        assertClose("compare vectors" ,result_mapExpToSelf,v_mapExpToSelf.toArray(),normTolerance);


        //octave =  ???
        RealVector v_mapExpm1 = v1.map(new Expm1());
        double[] result_mapExpm1 = {1.718281828459045d,6.38905609893065d, 19.085536923187668d};
        assertClose("compare vectors" ,result_mapExpm1,v_mapExpm1.toArray(),normTolerance);

        //octave =  ???
        RealVector v_mapExpm1ToSelf = v1.copy();
        v_mapExpm1ToSelf.mapToSelf(new Expm1());
        double[] result_mapExpm1ToSelf = {1.718281828459045d,6.38905609893065d, 19.085536923187668d};
        assertClose("compare vectors" ,result_mapExpm1ToSelf,v_mapExpm1ToSelf.toArray(),normTolerance);

        //octave =  log(v1)
        RealVector v_mapLog = v1.map(new Log());
        double[] result_mapLog = {0d,6.931471805599453e-01d, 1.098612288668110e+00d};
        assertClose("compare vectors" ,result_mapLog,v_mapLog.toArray(),normTolerance);

        //octave =  log(v1)
        RealVector v_mapLogToSelf = v1.copy();
        v_mapLogToSelf.mapToSelf(new Log());
        double[] result_mapLogToSelf = {0d,6.931471805599453e-01d, 1.098612288668110e+00d};
        assertClose("compare vectors" ,result_mapLogToSelf,v_mapLogToSelf.toArray(),normTolerance);

        //octave =  log10(v1)
        RealVector v_mapLog10 = v1.map(new Log10());
        double[] result_mapLog10 = {0d,3.010299956639812e-01d, 4.771212547196624e-01d};
        assertClose("compare vectors" ,result_mapLog10,v_mapLog10.toArray(),normTolerance);

        //octave =  log(v1)
        RealVector v_mapLog10ToSelf = v1.copy();
        v_mapLog10ToSelf.mapToSelf(new Log10());
        double[] result_mapLog10ToSelf = {0d,3.010299956639812e-01d, 4.771212547196624e-01d};
        assertClose("compare vectors" ,result_mapLog10ToSelf,v_mapLog10ToSelf.toArray(),normTolerance);

        //octave =  ???
        RealVector v_mapLog1p = v1.map(new Log1p());
        double[] result_mapLog1p = {0.6931471805599453d,1.0986122886681096d,1.3862943611198906d};
        assertClose("compare vectors" ,result_mapLog1p,v_mapLog1p.toArray(),normTolerance);

        //octave =  ???
        RealVector v_mapLog1pToSelf = v1.copy();
        v_mapLog1pToSelf.mapToSelf(new Log1p());
        double[] result_mapLog1pToSelf = {0.6931471805599453d,1.0986122886681096d,1.3862943611198906d};
        assertClose("compare vectors" ,result_mapLog1pToSelf,v_mapLog1pToSelf.toArray(),normTolerance);

        //octave =  cosh(v1)
        RealVector v_mapCosh = v1.map(new Cosh());
        double[] result_mapCosh = {1.543080634815244e+00d,3.762195691083631e+00d, 1.006766199577777e+01d};
        assertClose("compare vectors" ,result_mapCosh,v_mapCosh.toArray(),normTolerance);

        //octave =  cosh(v1)
        RealVector v_mapCoshToSelf = v1.copy();
        v_mapCoshToSelf.mapToSelf(new Cosh());
        double[] result_mapCoshToSelf = {1.543080634815244e+00d,3.762195691083631e+00d, 1.006766199577777e+01d};
        assertClose("compare vectors" ,result_mapCoshToSelf,v_mapCoshToSelf.toArray(),normTolerance);

        //octave =  sinh(v1)
        RealVector v_mapSinh = v1.map(new Sinh());
        double[] result_mapSinh = {1.175201193643801e+00d,3.626860407847019e+00d, 1.001787492740990e+01d};
        assertClose("compare vectors" ,result_mapSinh,v_mapSinh.toArray(),normTolerance);

        //octave =  sinh(v1)
        RealVector v_mapSinhToSelf = v1.copy();
        v_mapSinhToSelf.mapToSelf(new Sinh());
        double[] result_mapSinhToSelf = {1.175201193643801e+00d,3.626860407847019e+00d, 1.001787492740990e+01d};
        assertClose("compare vectors" ,result_mapSinhToSelf,v_mapSinhToSelf.toArray(),normTolerance);

        //octave =  tanh(v1)
        RealVector v_mapTanh = v1.map(new Tanh());
        double[] result_mapTanh = {7.615941559557649e-01d,9.640275800758169e-01d,9.950547536867305e-01d};
        assertClose("compare vectors" ,result_mapTanh,v_mapTanh.toArray(),normTolerance);

        //octave =  tanh(v1)
        RealVector v_mapTanhToSelf = v1.copy();
        v_mapTanhToSelf.mapToSelf(new Tanh());
        double[] result_mapTanhToSelf = {7.615941559557649e-01d,9.640275800758169e-01d,9.950547536867305e-01d};
        assertClose("compare vectors" ,result_mapTanhToSelf,v_mapTanhToSelf.toArray(),normTolerance);

        //octave =  cos(v1)
        RealVector v_mapCos = v1.map(new Cos());
        double[] result_mapCos = {5.403023058681398e-01d,-4.161468365471424e-01d, -9.899924966004454e-01d};
        assertClose("compare vectors" ,result_mapCos,v_mapCos.toArray(),normTolerance);

        //octave =  cos(v1)
        RealVector v_mapCosToSelf = v1.copy();
        v_mapCosToSelf.mapToSelf(new Cos());
        double[] result_mapCosToSelf = {5.403023058681398e-01d,-4.161468365471424e-01d, -9.899924966004454e-01d};
        assertClose("compare vectors" ,result_mapCosToSelf,v_mapCosToSelf.toArray(),normTolerance);

        //octave =  sin(v1)
        RealVector v_mapSin = v1.map(new Sin());
        double[] result_mapSin = {8.414709848078965e-01d,9.092974268256817e-01d,1.411200080598672e-01d};
        assertClose("compare vectors" ,result_mapSin,v_mapSin.toArray(),normTolerance);

        //octave =  sin(v1)
        RealVector v_mapSinToSelf = v1.copy();
        v_mapSinToSelf.mapToSelf(new Sin());
        double[] result_mapSinToSelf = {8.414709848078965e-01d,9.092974268256817e-01d,1.411200080598672e-01d};
        assertClose("compare vectors" ,result_mapSinToSelf,v_mapSinToSelf.toArray(),normTolerance);

        //octave =  tan(v1)
        RealVector v_mapTan = v1.map(new Tan());
        double[] result_mapTan = {1.557407724654902e+00d,-2.185039863261519e+00d,-1.425465430742778e-01d};
        assertClose("compare vectors" ,result_mapTan,v_mapTan.toArray(),normTolerance);

        //octave =  tan(v1)
        RealVector v_mapTanToSelf = v1.copy();
        v_mapTanToSelf.mapToSelf(new Tan());
        double[] result_mapTanToSelf = {1.557407724654902e+00d,-2.185039863261519e+00d,-1.425465430742778e-01d};
        assertClose("compare vectors" ,result_mapTanToSelf,v_mapTanToSelf.toArray(),normTolerance);

//        double[] vat_a = {0d, 0.5d, 1.0d};
//        ArrayRealVector vat = new ArrayRealVector(vat_a);

        //octave =  acos(vat)
        RealVector v_mapAcos = vat.map(new Acos());
        double[] result_mapAcos = {1.570796326794897e+00d,1.047197551196598e+00d, 0.0d};
        assertClose("compare vectors" ,result_mapAcos,v_mapAcos.toArray(),normTolerance);

        //octave =  acos(vat)
        RealVector v_mapAcosToSelf = vat.copy();
        v_mapAcosToSelf.mapToSelf(new Acos());
        double[] result_mapAcosToSelf = {1.570796326794897e+00d,1.047197551196598e+00d, 0.0d};
        assertClose("compare vectors" ,result_mapAcosToSelf,v_mapAcosToSelf.toArray(),normTolerance);

        //octave =  asin(vat)
        RealVector v_mapAsin = vat.map(new Asin());
        double[] result_mapAsin = {0.0d,5.235987755982989e-01d,1.570796326794897e+00d};
        assertClose("compare vectors" ,result_mapAsin,v_mapAsin.toArray(),normTolerance);

        //octave =  asin(vat)
        RealVector v_mapAsinToSelf = vat.copy();
        v_mapAsinToSelf.mapToSelf(new Asin());
        double[] result_mapAsinToSelf = {0.0d,5.235987755982989e-01d,1.570796326794897e+00d};
        assertClose("compare vectors" ,result_mapAsinToSelf,v_mapAsinToSelf.toArray(),normTolerance);

        //octave =  atan(vat)
        RealVector v_mapAtan = vat.map(new Atan());
        double[] result_mapAtan = {0.0d,4.636476090008061e-01d,7.853981633974483e-01d};
        assertClose("compare vectors" ,result_mapAtan,v_mapAtan.toArray(),normTolerance);

        //octave =  atan(vat)
        RealVector v_mapAtanToSelf = vat.copy();
        v_mapAtanToSelf.mapToSelf(new Atan());
        double[] result_mapAtanToSelf = {0.0d,4.636476090008061e-01d,7.853981633974483e-01d};
        assertClose("compare vectors" ,result_mapAtanToSelf,v_mapAtanToSelf.toArray(),normTolerance);

        //octave =  v1 .^-1
        RealVector v_mapInv = v1.map(new Inverse());
        double[] result_mapInv = {1d,0.5d,3.333333333333333e-01d};
        assertClose("compare vectors" ,result_mapInv,v_mapInv.toArray(),normTolerance);

        //octave =  v1 .^-1
        RealVector v_mapInvToSelf = v1.copy();
        v_mapInvToSelf.mapToSelf(new Inverse());
        double[] result_mapInvToSelf = {1d,0.5d,3.333333333333333e-01d};
        assertClose("compare vectors" ,result_mapInvToSelf,v_mapInvToSelf.toArray(),normTolerance);

//        double[] abs_a = {-1.0d, 0.0d, 1.0d};
//        ArrayRealVector abs_v = new ArrayRealVector(abs_a);

        //octave =  abs(abs_v)
        RealVector v_mapAbs = abs_v.map(new Abs());
        double[] result_mapAbs = {1d,0d,1d};
        assertClose("compare vectors" ,result_mapAbs,v_mapAbs.toArray(),normTolerance);

        //octave = abs(abs_v)
        RealVector v_mapAbsToSelf = abs_v.copy();
        v_mapAbsToSelf.mapToSelf(new Abs());
        double[] result_mapAbsToSelf = {1d,0d,1d};
        assertClose("compare vectors" ,result_mapAbsToSelf,v_mapAbsToSelf.toArray(),normTolerance);

        //octave =   sqrt(v1)
        RealVector v_mapSqrt = v1.map(new Sqrt());
        double[] result_mapSqrt = {1d,1.414213562373095e+00d,1.732050807568877e+00d};
        assertClose("compare vectors" ,result_mapSqrt,v_mapSqrt.toArray(),normTolerance);

        //octave =  sqrt(v1)
        RealVector v_mapSqrtToSelf = v1.copy();
        v_mapSqrtToSelf.mapToSelf(new Sqrt());
        double[] result_mapSqrtToSelf = {1d,1.414213562373095e+00d,1.732050807568877e+00d};
        assertClose("compare vectors" ,result_mapSqrtToSelf,v_mapSqrtToSelf.toArray(),normTolerance);

//        double[] cbrt_a = {-2.0d, 0.0d, 2.0d};
//        ArrayRealVector cbrt_v = new ArrayRealVector(cbrt_a);

        //octave =  ???
        RealVector v_mapCbrt = cbrt_v.map(new Cbrt());
        double[] result_mapCbrt = {-1.2599210498948732d,0d,1.2599210498948732d};
        assertClose("compare vectors" ,result_mapCbrt,v_mapCbrt.toArray(),normTolerance);

        //octave = ???
        RealVector v_mapCbrtToSelf = cbrt_v.copy();
        v_mapCbrtToSelf.mapToSelf(new Cbrt());
        double[] result_mapCbrtToSelf =  {-1.2599210498948732d,0d,1.2599210498948732d};
        assertClose("compare vectors" ,result_mapCbrtToSelf,v_mapCbrtToSelf.toArray(),normTolerance);

//        double[] ceil_a = {-1.1d, 0.9d, 1.1d};
//        ArrayRealVector ceil_v = new ArrayRealVector(ceil_a);

        //octave =  ceil(ceil_v)
        RealVector v_mapCeil = ceil_v.map(new Ceil());
        double[] result_mapCeil = {-1d,1d,2d};
        assertClose("compare vectors" ,result_mapCeil,v_mapCeil.toArray(),normTolerance);

        //octave = ceil(ceil_v)
        RealVector v_mapCeilToSelf = ceil_v.copy();
        v_mapCeilToSelf.mapToSelf(new Ceil());
        double[] result_mapCeilToSelf =  {-1d,1d,2d};
        assertClose("compare vectors" ,result_mapCeilToSelf,v_mapCeilToSelf.toArray(),normTolerance);

        //octave =  floor(ceil_v)
        RealVector v_mapFloor = ceil_v.map(new Floor());
        double[] result_mapFloor = {-2d,0d,1d};
        assertClose("compare vectors" ,result_mapFloor,v_mapFloor.toArray(),normTolerance);

        //octave = floor(ceil_v)
        RealVector v_mapFloorToSelf = ceil_v.copy();
        v_mapFloorToSelf.mapToSelf(new Floor());
        double[] result_mapFloorToSelf =  {-2d,0d,1d};
        assertClose("compare vectors" ,result_mapFloorToSelf,v_mapFloorToSelf.toArray(),normTolerance);

        //octave =  ???
        RealVector v_mapRint = ceil_v.map(new Rint());
        double[] result_mapRint = {-1d,1d,1d};
        assertClose("compare vectors" ,result_mapRint,v_mapRint.toArray(),normTolerance);

        //octave = ???
        RealVector v_mapRintToSelf = ceil_v.copy();
        v_mapRintToSelf.mapToSelf(new Rint());
        double[] result_mapRintToSelf =  {-1d,1d,1d};
        assertClose("compare vectors" ,result_mapRintToSelf,v_mapRintToSelf.toArray(),normTolerance);

        //octave =  ???
        RealVector v_mapSignum = ceil_v.map(new Signum());
        double[] result_mapSignum = {-1d,1d,1d};
        assertClose("compare vectors" ,result_mapSignum,v_mapSignum.toArray(),normTolerance);

        //octave = ???
        RealVector v_mapSignumToSelf = ceil_v.copy();
        v_mapSignumToSelf.mapToSelf(new Signum());
        double[] result_mapSignumToSelf =  {-1d,1d,1d};
        assertClose("compare vectors" ,result_mapSignumToSelf,v_mapSignumToSelf.toArray(),normTolerance);


        // Is with the used resolutions of limited value as test
        //octave =  ???
        RealVector v_mapUlp = ceil_v.map(new Ulp());
        double[] result_mapUlp = {2.220446049250313E-16d,1.1102230246251565E-16d,2.220446049250313E-16d};
        assertClose("compare vectors" ,result_mapUlp,v_mapUlp.toArray(),normTolerance);

        //octave = ???
        RealVector v_mapUlpToSelf = ceil_v.copy();
        v_mapUlpToSelf.mapToSelf(new Ulp());
        double[] result_mapUlpToSelf = {2.220446049250313E-16d,1.1102230246251565E-16d,2.220446049250313E-16d};
        assertClose("compare vectors" ,result_mapUlpToSelf,v_mapUlpToSelf.toArray(),normTolerance);
    }

    /**
     * This method contains common statements for {@code testSerial} from both classes
     * classes {@link ArrayRealVector} and {@link SparseRealVector}
     *
     * @param v {@link ArrayRealVector} or {@link SparseRealVector} object
     */
    public void testSerial(RealVector v)  {
        Assert.assertEquals(v,TestUtils.serializeAndRecover(v));
    }

    /**
     * This method contains common statements for {@code testOuterProduct} from both classes
     * {@link ArrayRealVector} or {@link SparseRealVector}
     *
     * @param uv {@link ArrayRealVector} or {@link SparseRealVector} object
     */
    public void testOuterProduct (RealMatrix uv){
        final double tol = Math.ulp(1d);
        Assert.assertEquals(4, uv.getEntry(0, 0), tol);
        Assert.assertEquals(-2, uv.getEntry(0, 1), tol);
        Assert.assertEquals(8, uv.getEntry(1, 0), tol);
        Assert.assertEquals(-4, uv.getEntry(1, 1), tol);
        Assert.assertEquals(-12, uv.getEntry(2, 0), tol);
        Assert.assertEquals(6, uv.getEntry(2, 1), tol);
    }

    /**
     * This method contains test cases for Predicates which are common in both
     * classes {@link ArrayRealVector} and {@link SparseRealVector}
     *
     * @param v {@link ArrayRealVector} or {@link SparseRealVector} object
     */
    public void testPredicates(RealVector v) {

        Assert.assertFalse(v.isNaN());
        v.setEntry(1, Double.NaN);
        Assert.assertTrue(v.isNaN());

        Assert.assertFalse(v.isInfinite());
        v.setEntry(0, Double.POSITIVE_INFINITY);
        Assert.assertFalse(v.isInfinite()); // NaN has higher priority than infinity
        v.setEntry(1, 1);
        Assert.assertTrue(v.isInfinite());
    }

    /** verifies that two vectors are close (sup norm) */
    protected void assertClose(String msg, double[] m, double[] n,
                               double tolerance) {
        if (m.length != n.length) {
            Assert.fail("vectors have different lengths");
        }
        for (int i = 0; i < m.length; i++) {
            Assert.assertEquals(msg + " " +  i + " elements differ", m[i],n[i],tolerance);
        }
    }

//    /**
//     * This method contains common statements for {@code testSerial} from both classes
//     * classes {@link ArrayRealVector} and {@link SparseRealVector}
//     *
//     * @param v1 {@link ArrayRealVector} or {@link SparseRealVector} object
//     */
//    public void testMisc(RealVector v1) {
//
//    }

}
