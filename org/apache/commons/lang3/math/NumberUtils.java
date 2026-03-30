/*      */ package org.apache.commons.lang3.math;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NumberUtils
/*      */ {
/*   35 */   public static final Long LONG_ZERO = Long.valueOf(0L);
/*      */   
/*   37 */   public static final Long LONG_ONE = Long.valueOf(1L);
/*      */   
/*   39 */   public static final Long LONG_MINUS_ONE = Long.valueOf(-1L);
/*      */   
/*   41 */   public static final Integer INTEGER_ZERO = Integer.valueOf(0);
/*      */   
/*   43 */   public static final Integer INTEGER_ONE = Integer.valueOf(1);
/*      */   
/*   45 */   public static final Integer INTEGER_TWO = Integer.valueOf(2);
/*      */   
/*   47 */   public static final Integer INTEGER_MINUS_ONE = Integer.valueOf(-1);
/*      */   
/*   49 */   public static final Short SHORT_ZERO = Short.valueOf((short)0);
/*      */   
/*   51 */   public static final Short SHORT_ONE = Short.valueOf((short)1);
/*      */   
/*   53 */   public static final Short SHORT_MINUS_ONE = Short.valueOf((short)-1);
/*      */   
/*   55 */   public static final Byte BYTE_ZERO = Byte.valueOf((byte)0);
/*      */   
/*   57 */   public static final Byte BYTE_ONE = Byte.valueOf((byte)1);
/*      */   
/*   59 */   public static final Byte BYTE_MINUS_ONE = Byte.valueOf((byte)-1);
/*      */   
/*   61 */   public static final Double DOUBLE_ZERO = Double.valueOf(0.0D);
/*      */   
/*   63 */   public static final Double DOUBLE_ONE = Double.valueOf(1.0D);
/*      */   
/*   65 */   public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0D);
/*      */   
/*   67 */   public static final Float FLOAT_ZERO = Float.valueOf(0.0F);
/*      */   
/*   69 */   public static final Float FLOAT_ONE = Float.valueOf(1.0F);
/*      */   
/*   71 */   public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toInt(String str) {
/*  104 */     return toInt(str, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toInt(String str, int defaultValue) {
/*  125 */     if (str == null) {
/*  126 */       return defaultValue;
/*      */     }
/*      */     try {
/*  129 */       return Integer.parseInt(str);
/*  130 */     } catch (NumberFormatException nfe) {
/*  131 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long toLong(String str) {
/*  153 */     return toLong(str, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long toLong(String str, long defaultValue) {
/*  174 */     if (str == null) {
/*  175 */       return defaultValue;
/*      */     }
/*      */     try {
/*  178 */       return Long.parseLong(str);
/*  179 */     } catch (NumberFormatException nfe) {
/*  180 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float toFloat(String str) {
/*  203 */     return toFloat(str, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float toFloat(String str, float defaultValue) {
/*  226 */     if (str == null) {
/*  227 */       return defaultValue;
/*      */     }
/*      */     try {
/*  230 */       return Float.parseFloat(str);
/*  231 */     } catch (NumberFormatException nfe) {
/*  232 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(String str) {
/*  255 */     return toDouble(str, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(String str, double defaultValue) {
/*  278 */     if (str == null) {
/*  279 */       return defaultValue;
/*      */     }
/*      */     try {
/*  282 */       return Double.parseDouble(str);
/*  283 */     } catch (NumberFormatException nfe) {
/*  284 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(BigDecimal value) {
/*  305 */     return toDouble(value, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(BigDecimal value, double defaultValue) {
/*  326 */     return (value == null) ? defaultValue : value.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte toByte(String str) {
/*  348 */     return toByte(str, (byte)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte toByte(String str, byte defaultValue) {
/*  369 */     if (str == null) {
/*  370 */       return defaultValue;
/*      */     }
/*      */     try {
/*  373 */       return Byte.parseByte(str);
/*  374 */     } catch (NumberFormatException nfe) {
/*  375 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short toShort(String str) {
/*  397 */     return toShort(str, (short)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short toShort(String str, short defaultValue) {
/*  418 */     if (str == null) {
/*  419 */       return defaultValue;
/*      */     }
/*      */     try {
/*  422 */       return Short.parseShort(str);
/*  423 */     } catch (NumberFormatException nfe) {
/*  424 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(BigDecimal value) {
/*  441 */     return toScaledBigDecimal(value, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(BigDecimal value, int scale, RoundingMode roundingMode) {
/*  457 */     if (value == null) {
/*  458 */       return BigDecimal.ZERO;
/*      */     }
/*  460 */     return value.setScale(scale, (roundingMode == null) ? RoundingMode.HALF_EVEN : roundingMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(Float value) {
/*  479 */     return toScaledBigDecimal(value, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(Float value, int scale, RoundingMode roundingMode) {
/*  495 */     if (value == null) {
/*  496 */       return BigDecimal.ZERO;
/*      */     }
/*  498 */     return toScaledBigDecimal(
/*  499 */         BigDecimal.valueOf(value.floatValue()), scale, roundingMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(Double value) {
/*  518 */     return toScaledBigDecimal(value, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(Double value, int scale, RoundingMode roundingMode) {
/*  534 */     if (value == null) {
/*  535 */       return BigDecimal.ZERO;
/*      */     }
/*  537 */     return toScaledBigDecimal(
/*  538 */         BigDecimal.valueOf(value.doubleValue()), scale, roundingMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(String value) {
/*  557 */     return toScaledBigDecimal(value, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toScaledBigDecimal(String value, int scale, RoundingMode roundingMode) {
/*  573 */     if (value == null) {
/*  574 */       return BigDecimal.ZERO;
/*      */     }
/*  576 */     return toScaledBigDecimal(
/*  577 */         createBigDecimal(value), scale, roundingMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Number createNumber(String str) {
/*      */     String mant, dec, exp;
/*  651 */     if (str == null) {
/*  652 */       return null;
/*      */     }
/*  654 */     if (StringUtils.isBlank(str)) {
/*  655 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*      */     
/*  658 */     String[] hex_prefixes = { "0x", "0X", "-0x", "-0X", "#", "-#" };
/*  659 */     int pfxLen = 0;
/*  660 */     for (String pfx : hex_prefixes) {
/*  661 */       if (str.startsWith(pfx)) {
/*  662 */         pfxLen += pfx.length();
/*      */         break;
/*      */       } 
/*      */     } 
/*  666 */     if (pfxLen > 0) {
/*  667 */       char firstSigDigit = Character.MIN_VALUE;
/*  668 */       for (int i = pfxLen; i < str.length(); ) {
/*  669 */         firstSigDigit = str.charAt(i);
/*  670 */         if (firstSigDigit == '0') {
/*  671 */           pfxLen++;
/*      */           
/*      */           i++;
/*      */         } 
/*      */       } 
/*  676 */       int hexDigits = str.length() - pfxLen;
/*  677 */       if (hexDigits > 16 || (hexDigits == 16 && firstSigDigit > '7')) {
/*  678 */         return createBigInteger(str);
/*      */       }
/*  680 */       if (hexDigits > 8 || (hexDigits == 8 && firstSigDigit > '7')) {
/*  681 */         return createLong(str);
/*      */       }
/*  683 */       return createInteger(str);
/*      */     } 
/*  685 */     char lastChar = str.charAt(str.length() - 1);
/*      */ 
/*      */ 
/*      */     
/*  689 */     int decPos = str.indexOf('.');
/*  690 */     int expPos = str.indexOf('e') + str.indexOf('E') + 1;
/*      */ 
/*      */ 
/*      */     
/*  694 */     if (decPos > -1) {
/*  695 */       if (expPos > -1) {
/*  696 */         if (expPos < decPos || expPos > str.length()) {
/*  697 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         }
/*  699 */         dec = str.substring(decPos + 1, expPos);
/*      */       } else {
/*  701 */         dec = str.substring(decPos + 1);
/*      */       } 
/*  703 */       mant = getMantissa(str, decPos);
/*      */     } else {
/*  705 */       if (expPos > -1) {
/*  706 */         if (expPos > str.length()) {
/*  707 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         }
/*  709 */         mant = getMantissa(str, expPos);
/*      */       } else {
/*  711 */         mant = getMantissa(str);
/*      */       } 
/*  713 */       dec = null;
/*      */     } 
/*  715 */     if (!Character.isDigit(lastChar) && lastChar != '.') {
/*  716 */       if (expPos > -1 && expPos < str.length() - 1) {
/*  717 */         exp = str.substring(expPos + 1, str.length() - 1);
/*      */       } else {
/*  719 */         exp = null;
/*      */       } 
/*      */       
/*  722 */       String numeric = str.substring(0, str.length() - 1);
/*  723 */       boolean bool = (isAllZeros(mant) && isAllZeros(exp));
/*  724 */       switch (lastChar) {
/*      */         case 'L':
/*      */         case 'l':
/*  727 */           if (dec == null && exp == null && ((
/*      */             
/*  729 */             !numeric.isEmpty() && numeric.charAt(0) == '-' && isDigits(numeric.substring(1))) || isDigits(numeric))) {
/*      */             try {
/*  731 */               return createLong(numeric);
/*  732 */             } catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */               
/*  735 */               return createBigInteger(numeric);
/*      */             } 
/*      */           }
/*  738 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         case 'F':
/*      */         case 'f':
/*      */           try {
/*  742 */             Float f = createFloat(str);
/*  743 */             if (!f.isInfinite() && (f.floatValue() != 0.0F || bool))
/*      */             {
/*      */               
/*  746 */               return f;
/*      */             }
/*      */           }
/*  749 */           catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */         
/*      */         case 'D':
/*      */         case 'd':
/*      */           try {
/*  756 */             Double d = createDouble(str);
/*  757 */             if (!d.isInfinite() && (d.floatValue() != 0.0D || bool)) {
/*  758 */               return d;
/*      */             }
/*  760 */           } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */           
/*      */           try {
/*  764 */             return createBigDecimal(numeric);
/*  765 */           } catch (NumberFormatException numberFormatException) {
/*      */             break;
/*      */           } 
/*      */       } 
/*      */       
/*  770 */       throw new NumberFormatException(str + " is not a valid number.");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  776 */     if (expPos > -1 && expPos < str.length() - 1) {
/*  777 */       exp = str.substring(expPos + 1, str.length());
/*      */     } else {
/*  779 */       exp = null;
/*      */     } 
/*  781 */     if (dec == null && exp == null) {
/*      */       
/*      */       try {
/*  784 */         return createInteger(str);
/*  785 */       } catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */         
/*      */         try {
/*  789 */           return createLong(str);
/*  790 */         } catch (NumberFormatException numberFormatException1) {
/*      */ 
/*      */           
/*  793 */           return createBigInteger(str);
/*      */         } 
/*      */       } 
/*      */     }
/*  797 */     boolean allZeros = (isAllZeros(mant) && isAllZeros(exp));
/*      */     try {
/*  799 */       Float f = createFloat(str);
/*  800 */       Double d = createDouble(str);
/*  801 */       if (!f.isInfinite() && (f
/*  802 */         .floatValue() != 0.0F || allZeros) && f
/*  803 */         .toString().equals(d.toString())) {
/*  804 */         return f;
/*      */       }
/*  806 */       if (!d.isInfinite() && (d.doubleValue() != 0.0D || allZeros)) {
/*  807 */         BigDecimal b = createBigDecimal(str);
/*  808 */         if (b.compareTo(BigDecimal.valueOf(d.doubleValue())) == 0) {
/*  809 */           return d;
/*      */         }
/*  811 */         return b;
/*      */       } 
/*  813 */     } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */     
/*  816 */     return createBigDecimal(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getMantissa(String str) {
/*  828 */     return getMantissa(str, str.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getMantissa(String str, int stopPos) {
/*  841 */     char firstChar = str.charAt(0);
/*  842 */     boolean hasSign = (firstChar == '-' || firstChar == '+');
/*      */     
/*  844 */     return hasSign ? str.substring(1, stopPos) : str.substring(0, stopPos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAllZeros(String str) {
/*  856 */     if (str == null) {
/*  857 */       return true;
/*      */     }
/*  859 */     for (int i = str.length() - 1; i >= 0; i--) {
/*  860 */       if (str.charAt(i) != '0') {
/*  861 */         return false;
/*      */       }
/*      */     } 
/*  864 */     return !str.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Float createFloat(String str) {
/*  878 */     if (str == null) {
/*  879 */       return null;
/*      */     }
/*  881 */     return Float.valueOf(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Double createDouble(String str) {
/*  894 */     if (str == null) {
/*  895 */       return null;
/*      */     }
/*  897 */     return Double.valueOf(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer createInteger(String str) {
/*  912 */     if (str == null) {
/*  913 */       return null;
/*      */     }
/*      */     
/*  916 */     return Integer.decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Long createLong(String str) {
/*  931 */     if (str == null) {
/*  932 */       return null;
/*      */     }
/*  934 */     return Long.decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger createBigInteger(String str) {
/*  948 */     if (str == null) {
/*  949 */       return null;
/*      */     }
/*  951 */     int pos = 0;
/*  952 */     int radix = 10;
/*  953 */     boolean negate = false;
/*  954 */     if (str.startsWith("-")) {
/*  955 */       negate = true;
/*  956 */       pos = 1;
/*      */     } 
/*  958 */     if (str.startsWith("0x", pos) || str.startsWith("0X", pos)) {
/*  959 */       radix = 16;
/*  960 */       pos += 2;
/*  961 */     } else if (str.startsWith("#", pos)) {
/*  962 */       radix = 16;
/*  963 */       pos++;
/*  964 */     } else if (str.startsWith("0", pos) && str.length() > pos + 1) {
/*  965 */       radix = 8;
/*  966 */       pos++;
/*      */     } 
/*      */     
/*  969 */     BigInteger value = new BigInteger(str.substring(pos), radix);
/*  970 */     return negate ? value.negate() : value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal createBigDecimal(String str) {
/*  983 */     if (str == null) {
/*  984 */       return null;
/*      */     }
/*      */     
/*  987 */     if (StringUtils.isBlank(str)) {
/*  988 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*  990 */     return new BigDecimal(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long min(long... array) {
/* 1006 */     validateArray(array);
/*      */ 
/*      */     
/* 1009 */     long min = array[0];
/* 1010 */     for (int i = 1; i < array.length; i++) {
/* 1011 */       if (array[i] < min) {
/* 1012 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1016 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int... array) {
/* 1030 */     validateArray(array);
/*      */ 
/*      */     
/* 1033 */     int min = array[0];
/* 1034 */     for (int j = 1; j < array.length; j++) {
/* 1035 */       if (array[j] < min) {
/* 1036 */         min = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1040 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short min(short... array) {
/* 1054 */     validateArray(array);
/*      */ 
/*      */     
/* 1057 */     short min = array[0];
/* 1058 */     for (int i = 1; i < array.length; i++) {
/* 1059 */       if (array[i] < min) {
/* 1060 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1064 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte min(byte... array) {
/* 1078 */     validateArray(array);
/*      */ 
/*      */     
/* 1081 */     byte min = array[0];
/* 1082 */     for (int i = 1; i < array.length; i++) {
/* 1083 */       if (array[i] < min) {
/* 1084 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1088 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double... array) {
/* 1103 */     validateArray(array);
/*      */ 
/*      */     
/* 1106 */     double min = array[0];
/* 1107 */     for (int i = 1; i < array.length; i++) {
/* 1108 */       if (Double.isNaN(array[i])) {
/* 1109 */         return Double.NaN;
/*      */       }
/* 1111 */       if (array[i] < min) {
/* 1112 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1116 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float... array) {
/* 1131 */     validateArray(array);
/*      */ 
/*      */     
/* 1134 */     float min = array[0];
/* 1135 */     for (int i = 1; i < array.length; i++) {
/* 1136 */       if (Float.isNaN(array[i])) {
/* 1137 */         return Float.NaN;
/*      */       }
/* 1139 */       if (array[i] < min) {
/* 1140 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1144 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long... array) {
/* 1160 */     validateArray(array);
/*      */ 
/*      */     
/* 1163 */     long max = array[0];
/* 1164 */     for (int j = 1; j < array.length; j++) {
/* 1165 */       if (array[j] > max) {
/* 1166 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1170 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int... array) {
/* 1184 */     validateArray(array);
/*      */ 
/*      */     
/* 1187 */     int max = array[0];
/* 1188 */     for (int j = 1; j < array.length; j++) {
/* 1189 */       if (array[j] > max) {
/* 1190 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1194 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short max(short... array) {
/* 1208 */     validateArray(array);
/*      */ 
/*      */     
/* 1211 */     short max = array[0];
/* 1212 */     for (int i = 1; i < array.length; i++) {
/* 1213 */       if (array[i] > max) {
/* 1214 */         max = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1218 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte max(byte... array) {
/* 1232 */     validateArray(array);
/*      */ 
/*      */     
/* 1235 */     byte max = array[0];
/* 1236 */     for (int i = 1; i < array.length; i++) {
/* 1237 */       if (array[i] > max) {
/* 1238 */         max = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1242 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double... array) {
/* 1257 */     validateArray(array);
/*      */ 
/*      */     
/* 1260 */     double max = array[0];
/* 1261 */     for (int j = 1; j < array.length; j++) {
/* 1262 */       if (Double.isNaN(array[j])) {
/* 1263 */         return Double.NaN;
/*      */       }
/* 1265 */       if (array[j] > max) {
/* 1266 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1270 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float... array) {
/* 1285 */     validateArray(array);
/*      */ 
/*      */     
/* 1288 */     float max = array[0];
/* 1289 */     for (int j = 1; j < array.length; j++) {
/* 1290 */       if (Float.isNaN(array[j])) {
/* 1291 */         return Float.NaN;
/*      */       }
/* 1293 */       if (array[j] > max) {
/* 1294 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1298 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateArray(Object array) {
/* 1308 */     Validate.notNull(array, "The Array must not be null", new Object[0]);
/* 1309 */     Validate.isTrue((Array.getLength(array) != 0), "Array cannot be empty.", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long min(long a, long b, long c) {
/* 1323 */     if (b < a) {
/* 1324 */       a = b;
/*      */     }
/* 1326 */     if (c < a) {
/* 1327 */       a = c;
/*      */     }
/* 1329 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int a, int b, int c) {
/* 1341 */     if (b < a) {
/* 1342 */       a = b;
/*      */     }
/* 1344 */     if (c < a) {
/* 1345 */       a = c;
/*      */     }
/* 1347 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short min(short a, short b, short c) {
/* 1359 */     if (b < a) {
/* 1360 */       a = b;
/*      */     }
/* 1362 */     if (c < a) {
/* 1363 */       a = c;
/*      */     }
/* 1365 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte min(byte a, byte b, byte c) {
/* 1377 */     if (b < a) {
/* 1378 */       a = b;
/*      */     }
/* 1380 */     if (c < a) {
/* 1381 */       a = c;
/*      */     }
/* 1383 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double a, double b, double c) {
/* 1399 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float a, float b, float c) {
/* 1415 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long a, long b, long c) {
/* 1429 */     if (b > a) {
/* 1430 */       a = b;
/*      */     }
/* 1432 */     if (c > a) {
/* 1433 */       a = c;
/*      */     }
/* 1435 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int a, int b, int c) {
/* 1447 */     if (b > a) {
/* 1448 */       a = b;
/*      */     }
/* 1450 */     if (c > a) {
/* 1451 */       a = c;
/*      */     }
/* 1453 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short max(short a, short b, short c) {
/* 1465 */     if (b > a) {
/* 1466 */       a = b;
/*      */     }
/* 1468 */     if (c > a) {
/* 1469 */       a = c;
/*      */     }
/* 1471 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte max(byte a, byte b, byte c) {
/* 1483 */     if (b > a) {
/* 1484 */       a = b;
/*      */     }
/* 1486 */     if (c > a) {
/* 1487 */       a = c;
/*      */     }
/* 1489 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double a, double b, double c) {
/* 1505 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float a, float b, float c) {
/* 1521 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDigits(String str) {
/* 1536 */     return StringUtils.isNumeric(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean isNumber(String str) {
/* 1566 */     return isCreatable(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCreatable(String str) {
/* 1592 */     if (StringUtils.isEmpty(str)) {
/* 1593 */       return false;
/*      */     }
/* 1595 */     char[] chars = str.toCharArray();
/* 1596 */     int sz = chars.length;
/* 1597 */     boolean hasExp = false;
/* 1598 */     boolean hasDecPoint = false;
/* 1599 */     boolean allowSigns = false;
/* 1600 */     boolean foundDigit = false;
/*      */     
/* 1602 */     int start = (chars[0] == '-' || chars[0] == '+') ? 1 : 0;
/* 1603 */     if (sz > start + 1 && chars[start] == '0' && !StringUtils.contains(str, 46)) {
/* 1604 */       if (chars[start + 1] == 'x' || chars[start + 1] == 'X') {
/* 1605 */         int j = start + 2;
/* 1606 */         if (j == sz) {
/* 1607 */           return false;
/*      */         }
/*      */         
/* 1610 */         for (; j < chars.length; j++) {
/* 1611 */           if ((chars[j] < '0' || chars[j] > '9') && (chars[j] < 'a' || chars[j] > 'f') && (chars[j] < 'A' || chars[j] > 'F'))
/*      */           {
/*      */             
/* 1614 */             return false;
/*      */           }
/*      */         } 
/* 1617 */         return true;
/* 1618 */       }  if (Character.isDigit(chars[start + 1])) {
/*      */         
/* 1620 */         int j = start + 1;
/* 1621 */         for (; j < chars.length; j++) {
/* 1622 */           if (chars[j] < '0' || chars[j] > '7') {
/* 1623 */             return false;
/*      */           }
/*      */         } 
/* 1626 */         return true;
/*      */       } 
/*      */     } 
/* 1629 */     sz--;
/*      */     
/* 1631 */     int i = start;
/*      */ 
/*      */     
/* 1634 */     while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
/* 1635 */       if (chars[i] >= '0' && chars[i] <= '9') {
/* 1636 */         foundDigit = true;
/* 1637 */         allowSigns = false;
/*      */       }
/* 1639 */       else if (chars[i] == '.') {
/* 1640 */         if (hasDecPoint || hasExp)
/*      */         {
/* 1642 */           return false;
/*      */         }
/* 1644 */         hasDecPoint = true;
/* 1645 */       } else if (chars[i] == 'e' || chars[i] == 'E') {
/*      */         
/* 1647 */         if (hasExp)
/*      */         {
/* 1649 */           return false;
/*      */         }
/* 1651 */         if (!foundDigit) {
/* 1652 */           return false;
/*      */         }
/* 1654 */         hasExp = true;
/* 1655 */         allowSigns = true;
/* 1656 */       } else if (chars[i] == '+' || chars[i] == '-') {
/* 1657 */         if (!allowSigns) {
/* 1658 */           return false;
/*      */         }
/* 1660 */         allowSigns = false;
/* 1661 */         foundDigit = false;
/*      */       } else {
/* 1663 */         return false;
/*      */       } 
/* 1665 */       i++;
/*      */     } 
/* 1667 */     if (i < chars.length) {
/* 1668 */       if (chars[i] >= '0' && chars[i] <= '9')
/*      */       {
/* 1670 */         return true;
/*      */       }
/* 1672 */       if (chars[i] == 'e' || chars[i] == 'E')
/*      */       {
/* 1674 */         return false;
/*      */       }
/* 1676 */       if (chars[i] == '.') {
/* 1677 */         if (hasDecPoint || hasExp)
/*      */         {
/* 1679 */           return false;
/*      */         }
/*      */         
/* 1682 */         return foundDigit;
/*      */       } 
/* 1684 */       if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F'))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1689 */         return foundDigit;
/*      */       }
/* 1691 */       if (chars[i] == 'l' || chars[i] == 'L')
/*      */       {
/*      */         
/* 1694 */         return (foundDigit && !hasExp && !hasDecPoint);
/*      */       }
/*      */       
/* 1697 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1701 */     return (!allowSigns && foundDigit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isParsable(String str) {
/* 1722 */     if (StringUtils.isEmpty(str)) {
/* 1723 */       return false;
/*      */     }
/* 1725 */     if (str.charAt(str.length() - 1) == '.') {
/* 1726 */       return false;
/*      */     }
/* 1728 */     if (str.charAt(0) == '-') {
/* 1729 */       if (str.length() == 1) {
/* 1730 */         return false;
/*      */       }
/* 1732 */       return withDecimalsParsing(str, 1);
/*      */     } 
/* 1734 */     return withDecimalsParsing(str, 0);
/*      */   }
/*      */   
/*      */   private static boolean withDecimalsParsing(String str, int beginIdx) {
/* 1738 */     int decimalPoints = 0;
/* 1739 */     for (int i = beginIdx; i < str.length(); i++) {
/* 1740 */       boolean isDecimalPoint = (str.charAt(i) == '.');
/* 1741 */       if (isDecimalPoint) {
/* 1742 */         decimalPoints++;
/*      */       }
/* 1744 */       if (decimalPoints > 1) {
/* 1745 */         return false;
/*      */       }
/* 1747 */       if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
/* 1748 */         return false;
/*      */       }
/*      */     } 
/* 1751 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(int x, int y) {
/* 1765 */     if (x == y) {
/* 1766 */       return 0;
/*      */     }
/* 1768 */     return (x < y) ? -1 : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(long x, long y) {
/* 1782 */     if (x == y) {
/* 1783 */       return 0;
/*      */     }
/* 1785 */     return (x < y) ? -1 : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(short x, short y) {
/* 1799 */     if (x == y) {
/* 1800 */       return 0;
/*      */     }
/* 1802 */     return (x < y) ? -1 : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int compare(byte x, byte y) {
/* 1816 */     return x - y;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\math\NumberUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */