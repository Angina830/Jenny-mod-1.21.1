/*     */ package org.apache.commons.lang3.math;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Fraction
/*     */   extends Number
/*     */   implements Comparable<Fraction>
/*     */ {
/*     */   private static final long serialVersionUID = 65382027393090L;
/*  48 */   public static final Fraction ZERO = new Fraction(0, 1);
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final Fraction ONE = new Fraction(1, 1);
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Fraction ONE_HALF = new Fraction(1, 2);
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final Fraction ONE_THIRD = new Fraction(1, 3);
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final Fraction TWO_THIRDS = new Fraction(2, 3);
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final Fraction ONE_QUARTER = new Fraction(1, 4);
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final Fraction ONE_FIFTH = new Fraction(1, 5);
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numerator;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int denominator;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private transient int hashCode = 0;
/*     */ 
/*     */ 
/*     */   
/* 111 */   private transient String toString = null;
/*     */ 
/*     */ 
/*     */   
/* 115 */   private transient String toProperString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Fraction(int numerator, int denominator) {
/* 126 */     this.numerator = numerator;
/* 127 */     this.denominator = denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Fraction getFraction(int numerator, int denominator) {
/* 143 */     if (denominator == 0) {
/* 144 */       throw new ArithmeticException("The denominator must not be zero");
/*     */     }
/* 146 */     if (denominator < 0) {
/* 147 */       if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE) {
/* 148 */         throw new ArithmeticException("overflow: can't negate");
/*     */       }
/* 150 */       numerator = -numerator;
/* 151 */       denominator = -denominator;
/*     */     } 
/* 153 */     return new Fraction(numerator, denominator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Fraction getFraction(int whole, int numerator, int denominator) {
/*     */     long numeratorValue;
/* 173 */     if (denominator == 0) {
/* 174 */       throw new ArithmeticException("The denominator must not be zero");
/*     */     }
/* 176 */     if (denominator < 0) {
/* 177 */       throw new ArithmeticException("The denominator must not be negative");
/*     */     }
/* 179 */     if (numerator < 0) {
/* 180 */       throw new ArithmeticException("The numerator must not be negative");
/*     */     }
/*     */     
/* 183 */     if (whole < 0) {
/* 184 */       numeratorValue = whole * denominator - numerator;
/*     */     } else {
/* 186 */       numeratorValue = whole * denominator + numerator;
/*     */     } 
/* 188 */     if (numeratorValue < -2147483648L || numeratorValue > 2147483647L) {
/* 189 */       throw new ArithmeticException("Numerator too large to represent as an Integer.");
/*     */     }
/* 191 */     return new Fraction((int)numeratorValue, denominator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Fraction getReducedFraction(int numerator, int denominator) {
/* 209 */     if (denominator == 0) {
/* 210 */       throw new ArithmeticException("The denominator must not be zero");
/*     */     }
/* 212 */     if (numerator == 0) {
/* 213 */       return ZERO;
/*     */     }
/*     */     
/* 216 */     if (denominator == Integer.MIN_VALUE && (numerator & 0x1) == 0) {
/* 217 */       numerator /= 2;
/* 218 */       denominator /= 2;
/*     */     } 
/* 220 */     if (denominator < 0) {
/* 221 */       if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE) {
/* 222 */         throw new ArithmeticException("overflow: can't negate");
/*     */       }
/* 224 */       numerator = -numerator;
/* 225 */       denominator = -denominator;
/*     */     } 
/*     */     
/* 228 */     int gcd = greatestCommonDivisor(numerator, denominator);
/* 229 */     numerator /= gcd;
/* 230 */     denominator /= gcd;
/* 231 */     return new Fraction(numerator, denominator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Fraction getFraction(double value) {
/*     */     double delta1;
/* 249 */     int sign = (value < 0.0D) ? -1 : 1;
/* 250 */     value = Math.abs(value);
/* 251 */     if (value > 2.147483647E9D || Double.isNaN(value)) {
/* 252 */       throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
/*     */     }
/* 254 */     int wholeNumber = (int)value;
/* 255 */     value -= wholeNumber;
/*     */     
/* 257 */     int numer0 = 0;
/* 258 */     int denom0 = 1;
/* 259 */     int numer1 = 1;
/* 260 */     int denom1 = 0;
/* 261 */     int numer2 = 0;
/* 262 */     int denom2 = 0;
/* 263 */     int a1 = (int)value;
/* 264 */     int a2 = 0;
/* 265 */     double x1 = 1.0D;
/* 266 */     double x2 = 0.0D;
/* 267 */     double y1 = value - a1;
/* 268 */     double y2 = 0.0D;
/* 269 */     double delta2 = Double.MAX_VALUE;
/*     */     
/* 271 */     int i = 1;
/*     */     do {
/* 273 */       delta1 = delta2;
/* 274 */       a2 = (int)(x1 / y1);
/* 275 */       x2 = y1;
/* 276 */       y2 = x1 - a2 * y1;
/* 277 */       numer2 = a1 * numer1 + numer0;
/* 278 */       denom2 = a1 * denom1 + denom0;
/* 279 */       double fraction = numer2 / denom2;
/* 280 */       delta2 = Math.abs(value - fraction);
/* 281 */       a1 = a2;
/* 282 */       x1 = x2;
/* 283 */       y1 = y2;
/* 284 */       numer0 = numer1;
/* 285 */       denom0 = denom1;
/* 286 */       numer1 = numer2;
/* 287 */       denom1 = denom2;
/* 288 */       i++;
/* 289 */     } while (delta1 > delta2 && denom2 <= 10000 && denom2 > 0 && i < 25);
/* 290 */     if (i == 25) {
/* 291 */       throw new ArithmeticException("Unable to convert double to fraction");
/*     */     }
/* 293 */     return getReducedFraction((numer0 + wholeNumber * denom0) * sign, denom0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Fraction getFraction(String str) {
/* 315 */     Validate.notNull(str, "The string must not be null", new Object[0]);
/*     */     
/* 317 */     int pos = str.indexOf('.');
/* 318 */     if (pos >= 0) {
/* 319 */       return getFraction(Double.parseDouble(str));
/*     */     }
/*     */ 
/*     */     
/* 323 */     pos = str.indexOf(' ');
/* 324 */     if (pos > 0) {
/* 325 */       int whole = Integer.parseInt(str.substring(0, pos));
/* 326 */       str = str.substring(pos + 1);
/* 327 */       pos = str.indexOf('/');
/* 328 */       if (pos < 0) {
/* 329 */         throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
/*     */       }
/* 331 */       int i = Integer.parseInt(str.substring(0, pos));
/* 332 */       int j = Integer.parseInt(str.substring(pos + 1));
/* 333 */       return getFraction(whole, i, j);
/*     */     } 
/*     */ 
/*     */     
/* 337 */     pos = str.indexOf('/');
/* 338 */     if (pos < 0)
/*     */     {
/* 340 */       return getFraction(Integer.parseInt(str), 1);
/*     */     }
/* 342 */     int numer = Integer.parseInt(str.substring(0, pos));
/* 343 */     int denom = Integer.parseInt(str.substring(pos + 1));
/* 344 */     return getFraction(numer, denom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumerator() {
/* 359 */     return this.numerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDenominator() {
/* 368 */     return this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProperNumerator() {
/* 383 */     return Math.abs(this.numerator % this.denominator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProperWhole() {
/* 398 */     return this.numerator / this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 412 */     return this.numerator / this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 423 */     return this.numerator / this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 434 */     return this.numerator / this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 445 */     return this.numerator / this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction reduce() {
/* 461 */     if (this.numerator == 0) {
/* 462 */       return equals(ZERO) ? this : ZERO;
/*     */     }
/* 464 */     int gcd = greatestCommonDivisor(Math.abs(this.numerator), this.denominator);
/* 465 */     if (gcd == 1) {
/* 466 */       return this;
/*     */     }
/* 468 */     return getFraction(this.numerator / gcd, this.denominator / gcd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction invert() {
/* 481 */     if (this.numerator == 0) {
/* 482 */       throw new ArithmeticException("Unable to invert zero.");
/*     */     }
/* 484 */     if (this.numerator == Integer.MIN_VALUE) {
/* 485 */       throw new ArithmeticException("overflow: can't negate numerator");
/*     */     }
/* 487 */     if (this.numerator < 0) {
/* 488 */       return new Fraction(-this.denominator, -this.numerator);
/*     */     }
/* 490 */     return new Fraction(this.denominator, this.numerator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction negate() {
/* 502 */     if (this.numerator == Integer.MIN_VALUE) {
/* 503 */       throw new ArithmeticException("overflow: too large to negate");
/*     */     }
/* 505 */     return new Fraction(-this.numerator, this.denominator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction abs() {
/* 518 */     if (this.numerator >= 0) {
/* 519 */       return this;
/*     */     }
/* 521 */     return negate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction pow(int power) {
/* 537 */     if (power == 1)
/* 538 */       return this; 
/* 539 */     if (power == 0)
/* 540 */       return ONE; 
/* 541 */     if (power < 0) {
/* 542 */       if (power == Integer.MIN_VALUE) {
/* 543 */         return invert().pow(2).pow(-(power / 2));
/*     */       }
/* 545 */       return invert().pow(-power);
/*     */     } 
/* 547 */     Fraction f = multiplyBy(this);
/* 548 */     if (power % 2 == 0) {
/* 549 */       return f.pow(power / 2);
/*     */     }
/* 551 */     return f.pow(power / 2).multiplyBy(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int greatestCommonDivisor(int u, int v) {
/* 567 */     if (u == 0 || v == 0) {
/* 568 */       if (u == Integer.MIN_VALUE || v == Integer.MIN_VALUE) {
/* 569 */         throw new ArithmeticException("overflow: gcd is 2^31");
/*     */       }
/* 571 */       return Math.abs(u) + Math.abs(v);
/*     */     } 
/*     */     
/* 574 */     if (Math.abs(u) == 1 || Math.abs(v) == 1) {
/* 575 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     if (u > 0) {
/* 582 */       u = -u;
/*     */     }
/* 584 */     if (v > 0) {
/* 585 */       v = -v;
/*     */     }
/*     */     
/* 588 */     int k = 0;
/* 589 */     while ((u & 0x1) == 0 && (v & 0x1) == 0 && k < 31) {
/* 590 */       u /= 2;
/* 591 */       v /= 2;
/* 592 */       k++;
/*     */     } 
/* 594 */     if (k == 31) {
/* 595 */       throw new ArithmeticException("overflow: gcd is 2^31");
/*     */     }
/*     */ 
/*     */     
/* 599 */     int t = ((u & 0x1) == 1) ? v : -(u / 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 605 */       while ((t & 0x1) == 0) {
/* 606 */         t /= 2;
/*     */       }
/*     */       
/* 609 */       if (t > 0) {
/* 610 */         u = -t;
/*     */       } else {
/* 612 */         v = t;
/*     */       } 
/*     */       
/* 615 */       t = (v - u) / 2;
/*     */ 
/*     */       
/* 618 */       if (t == 0) {
/* 619 */         return -u * (1 << k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int mulAndCheck(int x, int y) {
/* 635 */     long m = x * y;
/* 636 */     if (m < -2147483648L || m > 2147483647L) {
/* 637 */       throw new ArithmeticException("overflow: mul");
/*     */     }
/* 639 */     return (int)m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int mulPosAndCheck(int x, int y) {
/* 653 */     long m = x * y;
/* 654 */     if (m > 2147483647L) {
/* 655 */       throw new ArithmeticException("overflow: mulPos");
/*     */     }
/* 657 */     return (int)m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int addAndCheck(int x, int y) {
/* 670 */     long s = x + y;
/* 671 */     if (s < -2147483648L || s > 2147483647L) {
/* 672 */       throw new ArithmeticException("overflow: add");
/*     */     }
/* 674 */     return (int)s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int subAndCheck(int x, int y) {
/* 687 */     long s = x - y;
/* 688 */     if (s < -2147483648L || s > 2147483647L) {
/* 689 */       throw new ArithmeticException("overflow: add");
/*     */     }
/* 691 */     return (int)s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction add(Fraction fraction) {
/* 705 */     return addSub(fraction, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction subtract(Fraction fraction) {
/* 719 */     return addSub(fraction, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Fraction addSub(Fraction fraction, boolean isAdd) {
/* 733 */     Validate.notNull(fraction, "The fraction must not be null", new Object[0]);
/*     */     
/* 735 */     if (this.numerator == 0) {
/* 736 */       return isAdd ? fraction : fraction.negate();
/*     */     }
/* 738 */     if (fraction.numerator == 0) {
/* 739 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 743 */     int d1 = greatestCommonDivisor(this.denominator, fraction.denominator);
/* 744 */     if (d1 == 1) {
/*     */       
/* 746 */       int i = mulAndCheck(this.numerator, fraction.denominator);
/* 747 */       int j = mulAndCheck(fraction.numerator, this.denominator);
/* 748 */       return new Fraction(isAdd ? addAndCheck(i, j) : subAndCheck(i, j), mulPosAndCheck(this.denominator, fraction.denominator));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 754 */     BigInteger uvp = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf((fraction.denominator / d1)));
/* 755 */     BigInteger upv = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf((this.denominator / d1)));
/* 756 */     BigInteger t = isAdd ? uvp.add(upv) : uvp.subtract(upv);
/*     */ 
/*     */     
/* 759 */     int tmodd1 = t.mod(BigInteger.valueOf(d1)).intValue();
/* 760 */     int d2 = (tmodd1 == 0) ? d1 : greatestCommonDivisor(tmodd1, d1);
/*     */ 
/*     */     
/* 763 */     BigInteger w = t.divide(BigInteger.valueOf(d2));
/* 764 */     if (w.bitLength() > 31) {
/* 765 */       throw new ArithmeticException("overflow: numerator too large after multiply");
/*     */     }
/* 767 */     return new Fraction(w.intValue(), mulPosAndCheck(this.denominator / d1, fraction.denominator / d2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction multiplyBy(Fraction fraction) {
/* 781 */     Validate.notNull(fraction, "The fraction must not be null", new Object[0]);
/* 782 */     if (this.numerator == 0 || fraction.numerator == 0) {
/* 783 */       return ZERO;
/*     */     }
/*     */ 
/*     */     
/* 787 */     int d1 = greatestCommonDivisor(this.numerator, fraction.denominator);
/* 788 */     int d2 = greatestCommonDivisor(fraction.numerator, this.denominator);
/* 789 */     return getReducedFraction(mulAndCheck(this.numerator / d1, fraction.numerator / d2), 
/* 790 */         mulPosAndCheck(this.denominator / d2, fraction.denominator / d1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction divideBy(Fraction fraction) {
/* 804 */     Validate.notNull(fraction, "The fraction must not be null", new Object[0]);
/* 805 */     if (fraction.numerator == 0) {
/* 806 */       throw new ArithmeticException("The fraction to divide by must not be zero");
/*     */     }
/* 808 */     return multiplyBy(fraction.invert());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 824 */     if (obj == this) {
/* 825 */       return true;
/*     */     }
/* 827 */     if (!(obj instanceof Fraction)) {
/* 828 */       return false;
/*     */     }
/* 830 */     Fraction other = (Fraction)obj;
/* 831 */     return (getNumerator() == other.getNumerator() && getDenominator() == other.getDenominator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 841 */     if (this.hashCode == 0)
/*     */     {
/* 843 */       this.hashCode = 37 * (629 + getNumerator()) + getDenominator();
/*     */     }
/* 845 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Fraction other) {
/* 862 */     if (this == other) {
/* 863 */       return 0;
/*     */     }
/* 865 */     if (this.numerator == other.numerator && this.denominator == other.denominator) {
/* 866 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 870 */     long first = this.numerator * other.denominator;
/* 871 */     long second = other.numerator * this.denominator;
/* 872 */     return Long.compare(first, second);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 884 */     if (this.toString == null) {
/* 885 */       this.toString = getNumerator() + "/" + getDenominator();
/*     */     }
/* 887 */     return this.toString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toProperString() {
/* 900 */     if (this.toProperString == null) {
/* 901 */       if (this.numerator == 0) {
/* 902 */         this.toProperString = "0";
/* 903 */       } else if (this.numerator == this.denominator) {
/* 904 */         this.toProperString = "1";
/* 905 */       } else if (this.numerator == -1 * this.denominator) {
/* 906 */         this.toProperString = "-1";
/* 907 */       } else if (((this.numerator > 0) ? -this.numerator : this.numerator) < -this.denominator) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 912 */         int properNumerator = getProperNumerator();
/* 913 */         if (properNumerator == 0) {
/* 914 */           this.toProperString = Integer.toString(getProperWhole());
/*     */         } else {
/* 916 */           this.toProperString = getProperWhole() + " " + properNumerator + "/" + getDenominator();
/*     */         } 
/*     */       } else {
/* 919 */         this.toProperString = getNumerator() + "/" + getDenominator();
/*     */       } 
/*     */     }
/* 922 */     return this.toProperString;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\math\Fraction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */