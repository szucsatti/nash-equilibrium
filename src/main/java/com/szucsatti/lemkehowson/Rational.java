package com.szucsatti.lemkehowson;


import lombok.experimental.Delegate;
import org.jscience.mathematics.number.LargeInteger;

public class Rational {

    public static final Rational ZERO;
    public static final Rational ONE;

    static {
        ZERO = new Rational(LargeInteger.ZERO, LargeInteger.ONE);
        ONE = new Rational(LargeInteger.ONE, LargeInteger.ONE);
    }

    @Delegate
    private org.jscience.mathematics.number.Rational rational;

    private Rational(){

    }

    private Rational(final org.jscience.mathematics.number.Rational rational){
        this.rational = rational;
    }

    public boolean isGreaterOrEqualThan(final Rational that){
        return this.rational.isGreaterThan(that.rational) || this.rational.equals(that.rational);
    }

    public boolean isLessThan(final Rational that){
        return this.rational.isLessThan(that.rational);
    }

    public boolean equals(final Object that){
        if (!(that instanceof Rational)) {
            return false;
        } else {
            return this.rational.equals(((Rational) that).rational);
        }
    }

    public Rational plus(final Rational that){
        return new Rational(this.rational.plus(that.rational));
    }

    public Rational minus(final Rational that){
        return new Rational(this.rational.minus(that.rational));
    }

    public Rational times(final Rational that){
        return new Rational(this.rational.times(that.rational));
    }

    public Rational absValue(){
        return new Rational(this.rational.abs());
    }

    public Rational divide(Rational that) {
        return new Rational(this.rational.divide(that.rational));
    }

    private Rational(final LargeInteger dividend, final LargeInteger divisor){
        this.rational = org.jscience.mathematics.number.Rational.valueOf(dividend, divisor);
    }

    public static Rational valueOf(long dividend, long divisor) {
        return new Rational(org.jscience.mathematics.number.Rational.valueOf(dividend, divisor));
    }

    public static Rational valueOf(long dividend){
        return new Rational(org.jscience.mathematics.number.Rational.valueOf(dividend, 1l));
    }

}
