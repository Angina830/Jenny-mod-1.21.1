/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import org.apache.commons.lang3.math.NumberUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BooleanUtils
/*      */ {
/*      */   public static Boolean negate(Boolean bool) {
/*   64 */     if (bool == null) {
/*   65 */       return null;
/*      */     }
/*   67 */     return bool.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static boolean isTrue(Boolean bool) {
/*   87 */     return Boolean.TRUE.equals(bool);
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
/*      */   public static boolean isNotTrue(Boolean bool) {
/*  105 */     return !isTrue(bool);
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
/*      */   public static boolean isFalse(Boolean bool) {
/*  123 */     return Boolean.FALSE.equals(bool);
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
/*      */   public static boolean isNotFalse(Boolean bool) {
/*  141 */     return !isFalse(bool);
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
/*      */   public static boolean toBoolean(Boolean bool) {
/*  159 */     return (bool != null && bool.booleanValue());
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
/*      */   public static boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull) {
/*  179 */     if (bool == null) {
/*  180 */       return valueIfNull;
/*      */     }
/*  182 */     return bool.booleanValue();
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
/*      */   public static boolean toBoolean(int value) {
/*  202 */     return (value != 0);
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
/*      */   public static Boolean toBooleanObject(int value) {
/*  220 */     return (value == 0) ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static Boolean toBooleanObject(Integer value) {
/*  243 */     if (value == null) {
/*  244 */       return null;
/*      */     }
/*  246 */     return (value.intValue() == 0) ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static boolean toBoolean(int value, int trueValue, int falseValue) {
/*  271 */     if (value == trueValue) {
/*  272 */       return true;
/*      */     }
/*  274 */     if (value == falseValue) {
/*  275 */       return false;
/*      */     }
/*  277 */     throw new IllegalArgumentException("The Integer did not match either specified value");
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
/*      */   public static boolean toBoolean(Integer value, Integer trueValue, Integer falseValue) {
/*  298 */     if (value == null) {
/*  299 */       if (trueValue == null) {
/*  300 */         return true;
/*      */       }
/*  302 */       if (falseValue == null)
/*  303 */         return false; 
/*      */     } else {
/*  305 */       if (value.equals(trueValue))
/*  306 */         return true; 
/*  307 */       if (value.equals(falseValue))
/*  308 */         return false; 
/*      */     } 
/*  310 */     throw new IllegalArgumentException("The Integer did not match either specified value");
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
/*      */   public static Boolean toBooleanObject(int value, int trueValue, int falseValue, int nullValue) {
/*  339 */     if (value == trueValue) {
/*  340 */       return Boolean.TRUE;
/*      */     }
/*  342 */     if (value == falseValue) {
/*  343 */       return Boolean.FALSE;
/*      */     }
/*  345 */     if (value == nullValue) {
/*  346 */       return null;
/*      */     }
/*  348 */     throw new IllegalArgumentException("The Integer did not match any specified value");
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
/*      */   public static Boolean toBooleanObject(Integer value, Integer trueValue, Integer falseValue, Integer nullValue) {
/*  377 */     if (value == null) {
/*  378 */       if (trueValue == null) {
/*  379 */         return Boolean.TRUE;
/*      */       }
/*  381 */       if (falseValue == null) {
/*  382 */         return Boolean.FALSE;
/*      */       }
/*  384 */       if (nullValue == null)
/*  385 */         return null; 
/*      */     } else {
/*  387 */       if (value.equals(trueValue))
/*  388 */         return Boolean.TRUE; 
/*  389 */       if (value.equals(falseValue))
/*  390 */         return Boolean.FALSE; 
/*  391 */       if (value.equals(nullValue))
/*  392 */         return null; 
/*      */     } 
/*  394 */     throw new IllegalArgumentException("The Integer did not match any specified value");
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
/*      */   public static int toInteger(boolean bool) {
/*  412 */     return bool ? 1 : 0;
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
/*      */   public static Integer toIntegerObject(boolean bool) {
/*  428 */     return bool ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
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
/*      */   public static Integer toIntegerObject(Boolean bool) {
/*  446 */     if (bool == null) {
/*  447 */       return null;
/*      */     }
/*  449 */     return bool.booleanValue() ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
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
/*      */   public static int toInteger(boolean bool, int trueValue, int falseValue) {
/*  466 */     return bool ? trueValue : falseValue;
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
/*      */   public static int toInteger(Boolean bool, int trueValue, int falseValue, int nullValue) {
/*  485 */     if (bool == null) {
/*  486 */       return nullValue;
/*      */     }
/*  488 */     return bool.booleanValue() ? trueValue : falseValue;
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
/*      */   public static Integer toIntegerObject(boolean bool, Integer trueValue, Integer falseValue) {
/*  505 */     return bool ? trueValue : falseValue;
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
/*      */   public static Integer toIntegerObject(Boolean bool, Integer trueValue, Integer falseValue, Integer nullValue) {
/*  524 */     if (bool == null) {
/*  525 */       return nullValue;
/*      */     }
/*  527 */     return bool.booleanValue() ? trueValue : falseValue;
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
/*      */   public static Boolean toBooleanObject(String str) {
/*      */     char ch0;
/*      */     char ch1;
/*      */     char ch2;
/*      */     char ch3;
/*      */     char ch4;
/*  576 */     if (str == "true") {
/*  577 */       return Boolean.TRUE;
/*      */     }
/*  579 */     if (str == null) {
/*  580 */       return null;
/*      */     }
/*  582 */     switch (str.length()) {
/*      */       case 1:
/*  584 */         ch0 = str.charAt(0);
/*  585 */         if (ch0 == 'y' || ch0 == 'Y' || ch0 == 't' || ch0 == 'T' || ch0 == '1')
/*      */         {
/*      */           
/*  588 */           return Boolean.TRUE;
/*      */         }
/*  590 */         if (ch0 == 'n' || ch0 == 'N' || ch0 == 'f' || ch0 == 'F' || ch0 == '0')
/*      */         {
/*      */           
/*  593 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 2:
/*  598 */         ch0 = str.charAt(0);
/*  599 */         ch1 = str.charAt(1);
/*  600 */         if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N'))
/*      */         {
/*  602 */           return Boolean.TRUE;
/*      */         }
/*  604 */         if ((ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O'))
/*      */         {
/*  606 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 3:
/*  611 */         ch0 = str.charAt(0);
/*  612 */         ch1 = str.charAt(1);
/*  613 */         ch2 = str.charAt(2);
/*  614 */         if ((ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S'))
/*      */         {
/*      */           
/*  617 */           return Boolean.TRUE;
/*      */         }
/*  619 */         if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F'))
/*      */         {
/*      */           
/*  622 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 4:
/*  627 */         ch0 = str.charAt(0);
/*  628 */         ch1 = str.charAt(1);
/*  629 */         ch2 = str.charAt(2);
/*  630 */         ch3 = str.charAt(3);
/*  631 */         if ((ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R') && (ch2 == 'u' || ch2 == 'U') && (ch3 == 'e' || ch3 == 'E'))
/*      */         {
/*      */ 
/*      */           
/*  635 */           return Boolean.TRUE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 5:
/*  640 */         ch0 = str.charAt(0);
/*  641 */         ch1 = str.charAt(1);
/*  642 */         ch2 = str.charAt(2);
/*  643 */         ch3 = str.charAt(3);
/*  644 */         ch4 = str.charAt(4);
/*  645 */         if ((ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A') && (ch2 == 'l' || ch2 == 'L') && (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E'))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  650 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  658 */     return null;
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
/*      */   public static Boolean toBooleanObject(String str, String trueString, String falseString, String nullString) {
/*  688 */     if (str == null) {
/*  689 */       if (trueString == null) {
/*  690 */         return Boolean.TRUE;
/*      */       }
/*  692 */       if (falseString == null) {
/*  693 */         return Boolean.FALSE;
/*      */       }
/*  695 */       if (nullString == null)
/*  696 */         return null; 
/*      */     } else {
/*  698 */       if (str.equals(trueString))
/*  699 */         return Boolean.TRUE; 
/*  700 */       if (str.equals(falseString))
/*  701 */         return Boolean.FALSE; 
/*  702 */       if (str.equals(nullString)) {
/*  703 */         return null;
/*      */       }
/*      */     } 
/*  706 */     throw new IllegalArgumentException("The String did not match any specified value");
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
/*      */   public static boolean toBoolean(String str) {
/*  741 */     return (toBooleanObject(str) == Boolean.TRUE);
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
/*      */   public static boolean toBoolean(String str, String trueString, String falseString) {
/*  759 */     if (str == trueString)
/*  760 */       return true; 
/*  761 */     if (str == falseString)
/*  762 */       return false; 
/*  763 */     if (str != null) {
/*  764 */       if (str.equals(trueString))
/*  765 */         return true; 
/*  766 */       if (str.equals(falseString)) {
/*  767 */         return false;
/*      */       }
/*      */     } 
/*  770 */     throw new IllegalArgumentException("The String did not match either specified value");
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
/*      */   public static String toStringTrueFalse(Boolean bool) {
/*  789 */     return toString(bool, "true", "false", null);
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
/*      */   public static String toStringOnOff(Boolean bool) {
/*  806 */     return toString(bool, "on", "off", null);
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
/*      */   public static String toStringYesNo(Boolean bool) {
/*  823 */     return toString(bool, "yes", "no", null);
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
/*      */   public static String toString(Boolean bool, String trueString, String falseString, String nullString) {
/*  842 */     if (bool == null) {
/*  843 */       return nullString;
/*      */     }
/*  845 */     return bool.booleanValue() ? trueString : falseString;
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
/*      */   public static String toStringTrueFalse(boolean bool) {
/*  863 */     return toString(bool, "true", "false");
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
/*      */   public static String toStringOnOff(boolean bool) {
/*  879 */     return toString(bool, "on", "off");
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
/*      */   public static String toStringYesNo(boolean bool) {
/*  895 */     return toString(bool, "yes", "no");
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
/*      */   public static String toString(boolean bool, String trueString, String falseString) {
/*  912 */     return bool ? trueString : falseString;
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
/*      */   public static boolean and(boolean... array) {
/*  937 */     if (array == null) {
/*  938 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  940 */     if (array.length == 0) {
/*  941 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*  943 */     for (boolean element : array) {
/*  944 */       if (!element) {
/*  945 */         return false;
/*      */       }
/*      */     } 
/*  948 */     return true;
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
/*      */   public static Boolean and(Boolean... array) {
/*  972 */     if (array == null) {
/*  973 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  975 */     if (array.length == 0) {
/*  976 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/*  979 */       boolean[] primitive = ArrayUtils.toPrimitive(array);
/*  980 */       return and(primitive) ? Boolean.TRUE : Boolean.FALSE;
/*  981 */     } catch (NullPointerException ex) {
/*  982 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */   public static boolean or(boolean... array) {
/* 1005 */     if (array == null) {
/* 1006 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1008 */     if (array.length == 0) {
/* 1009 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/* 1011 */     for (boolean element : array) {
/* 1012 */       if (element) {
/* 1013 */         return true;
/*      */       }
/*      */     } 
/* 1016 */     return false;
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
/*      */   public static Boolean or(Boolean... array) {
/* 1040 */     if (array == null) {
/* 1041 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1043 */     if (array.length == 0) {
/* 1044 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/* 1047 */       boolean[] primitive = ArrayUtils.toPrimitive(array);
/* 1048 */       return or(primitive) ? Boolean.TRUE : Boolean.FALSE;
/* 1049 */     } catch (NullPointerException ex) {
/* 1050 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */   public static boolean xor(boolean... array) {
/* 1069 */     if (array == null) {
/* 1070 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1072 */     if (array.length == 0) {
/* 1073 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */ 
/*      */     
/* 1077 */     boolean result = false;
/* 1078 */     for (boolean element : array) {
/* 1079 */       result ^= element;
/*      */     }
/*      */     
/* 1082 */     return result;
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
/*      */   public static Boolean xor(Boolean... array) {
/* 1102 */     if (array == null) {
/* 1103 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1105 */     if (array.length == 0) {
/* 1106 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/* 1109 */       boolean[] primitive = ArrayUtils.toPrimitive(array);
/* 1110 */       return xor(primitive) ? Boolean.TRUE : Boolean.FALSE;
/* 1111 */     } catch (NullPointerException ex) {
/* 1112 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */   public static int compare(boolean x, boolean y) {
/* 1127 */     if (x == y) {
/* 1128 */       return 0;
/*      */     }
/* 1130 */     return x ? 1 : -1;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\BooleanUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */