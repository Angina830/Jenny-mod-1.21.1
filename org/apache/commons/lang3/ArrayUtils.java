/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import org.apache.commons.lang3.builder.EqualsBuilder;
/*      */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*      */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*      */ import org.apache.commons.lang3.builder.ToStringStyle;
/*      */ import org.apache.commons.lang3.math.NumberUtils;
/*      */ import org.apache.commons.lang3.mutable.MutableInt;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayUtils
/*      */ {
/*   54 */   public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public static final char[] EMPTY_CHAR_ARRAY = new char[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   89 */   public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   public static final Field[] EMPTY_FIELD_ARRAY = new Field[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   public static final int[] EMPTY_INT_ARRAY = new int[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  121 */   public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   public static final long[] EMPTY_LONG_ARRAY = new long[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   public static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   public static final short[] EMPTY_SHORT_ARRAY = new short[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   public static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] add(boolean[] array, boolean element) {
/*  203 */     boolean[] newArray = (boolean[])copyArrayGrow1(array, boolean.class);
/*  204 */     newArray[newArray.length - 1] = element;
/*  205 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static boolean[] add(boolean[] array, int index, boolean element) {
/*  239 */     return (boolean[])add(array, index, Boolean.valueOf(element), boolean.class);
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
/*      */   public static byte[] add(byte[] array, byte element) {
/*  264 */     byte[] newArray = (byte[])copyArrayGrow1(array, byte.class);
/*  265 */     newArray[newArray.length - 1] = element;
/*  266 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static byte[] add(byte[] array, int index, byte element) {
/*  301 */     return (byte[])add(array, index, Byte.valueOf(element), byte.class);
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
/*      */   public static char[] add(char[] array, char element) {
/*  326 */     char[] newArray = (char[])copyArrayGrow1(array, char.class);
/*  327 */     newArray[newArray.length - 1] = element;
/*  328 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static char[] add(char[] array, int index, char element) {
/*  364 */     return (char[])add(array, index, Character.valueOf(element), char.class);
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
/*      */   public static double[] add(double[] array, double element) {
/*  389 */     double[] newArray = (double[])copyArrayGrow1(array, double.class);
/*  390 */     newArray[newArray.length - 1] = element;
/*  391 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static double[] add(double[] array, int index, double element) {
/*  426 */     return (double[])add(array, index, Double.valueOf(element), double.class);
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
/*      */   public static float[] add(float[] array, float element) {
/*  451 */     float[] newArray = (float[])copyArrayGrow1(array, float.class);
/*  452 */     newArray[newArray.length - 1] = element;
/*  453 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static float[] add(float[] array, int index, float element) {
/*  488 */     return (float[])add(array, index, Float.valueOf(element), float.class);
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
/*      */   public static int[] add(int[] array, int element) {
/*  513 */     int[] newArray = (int[])copyArrayGrow1(array, int.class);
/*  514 */     newArray[newArray.length - 1] = element;
/*  515 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static int[] add(int[] array, int index, int element) {
/*  550 */     return (int[])add(array, index, Integer.valueOf(element), int.class);
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
/*      */   @Deprecated
/*      */   public static long[] add(long[] array, int index, long element) {
/*  585 */     return (long[])add(array, index, Long.valueOf(element), long.class);
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
/*      */   public static long[] add(long[] array, long element) {
/*  610 */     long[] newArray = (long[])copyArrayGrow1(array, long.class);
/*  611 */     newArray[newArray.length - 1] = element;
/*  612 */     return newArray;
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
/*      */   private static Object add(Object array, int index, Object element, Class<?> clss) {
/*  627 */     if (array == null) {
/*  628 */       if (index != 0) {
/*  629 */         throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
/*      */       }
/*  631 */       Object joinedArray = Array.newInstance(clss, 1);
/*  632 */       Array.set(joinedArray, 0, element);
/*  633 */       return joinedArray;
/*      */     } 
/*  635 */     int length = Array.getLength(array);
/*  636 */     if (index > length || index < 0) {
/*  637 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/*      */     }
/*  639 */     Object result = Array.newInstance(clss, length + 1);
/*  640 */     System.arraycopy(array, 0, result, 0, index);
/*  641 */     Array.set(result, index, element);
/*  642 */     if (index < length) {
/*  643 */       System.arraycopy(array, index, result, index + 1, length - index);
/*      */     }
/*  645 */     return result;
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
/*      */   @Deprecated
/*      */   public static short[] add(short[] array, int index, short element) {
/*  680 */     return (short[])add(array, index, Short.valueOf(element), short.class);
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
/*      */   public static short[] add(short[] array, short element) {
/*  705 */     short[] newArray = (short[])copyArrayGrow1(array, short.class);
/*  706 */     newArray[newArray.length - 1] = element;
/*  707 */     return newArray;
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
/*      */   @Deprecated
/*      */   public static <T> T[] add(T[] array, int index, T element) {
/*  745 */     Class<?> clss = null;
/*  746 */     if (array != null) {
/*  747 */       clss = array.getClass().getComponentType();
/*  748 */     } else if (element != null) {
/*  749 */       clss = element.getClass();
/*      */     } else {
/*  751 */       throw new IllegalArgumentException("Array and element cannot both be null");
/*      */     } 
/*      */     
/*  754 */     T[] newArray = (T[])add(array, index, element, clss);
/*  755 */     return newArray;
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
/*      */   public static <T> T[] add(T[] array, T element) {
/*      */     Class<?> type;
/*  789 */     if (array != null) {
/*  790 */       type = array.getClass().getComponentType();
/*  791 */     } else if (element != null) {
/*  792 */       type = element.getClass();
/*      */     } else {
/*  794 */       throw new IllegalArgumentException("Arguments cannot both be null");
/*      */     } 
/*      */ 
/*      */     
/*  798 */     T[] newArray = (T[])copyArrayGrow1(array, type);
/*  799 */     newArray[newArray.length - 1] = element;
/*  800 */     return newArray;
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
/*      */   public static boolean[] addAll(boolean[] array1, boolean... array2) {
/*  821 */     if (array1 == null)
/*  822 */       return clone(array2); 
/*  823 */     if (array2 == null) {
/*  824 */       return clone(array1);
/*      */     }
/*  826 */     boolean[] joinedArray = new boolean[array1.length + array2.length];
/*  827 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*  828 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/*  829 */     return joinedArray;
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
/*      */   public static byte[] addAll(byte[] array1, byte... array2) {
/*  850 */     if (array1 == null)
/*  851 */       return clone(array2); 
/*  852 */     if (array2 == null) {
/*  853 */       return clone(array1);
/*      */     }
/*  855 */     byte[] joinedArray = new byte[array1.length + array2.length];
/*  856 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*  857 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/*  858 */     return joinedArray;
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
/*      */   public static char[] addAll(char[] array1, char... array2) {
/*  879 */     if (array1 == null)
/*  880 */       return clone(array2); 
/*  881 */     if (array2 == null) {
/*  882 */       return clone(array1);
/*      */     }
/*  884 */     char[] joinedArray = new char[array1.length + array2.length];
/*  885 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*  886 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/*  887 */     return joinedArray;
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
/*      */   public static double[] addAll(double[] array1, double... array2) {
/*  908 */     if (array1 == null)
/*  909 */       return clone(array2); 
/*  910 */     if (array2 == null) {
/*  911 */       return clone(array1);
/*      */     }
/*  913 */     double[] joinedArray = new double[array1.length + array2.length];
/*  914 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*  915 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/*  916 */     return joinedArray;
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
/*      */   public static float[] addAll(float[] array1, float... array2) {
/*  937 */     if (array1 == null)
/*  938 */       return clone(array2); 
/*  939 */     if (array2 == null) {
/*  940 */       return clone(array1);
/*      */     }
/*  942 */     float[] joinedArray = new float[array1.length + array2.length];
/*  943 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*  944 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/*  945 */     return joinedArray;
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
/*      */   public static int[] addAll(int[] array1, int... array2) {
/*  966 */     if (array1 == null)
/*  967 */       return clone(array2); 
/*  968 */     if (array2 == null) {
/*  969 */       return clone(array1);
/*      */     }
/*  971 */     int[] joinedArray = new int[array1.length + array2.length];
/*  972 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*  973 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/*  974 */     return joinedArray;
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
/*      */   public static long[] addAll(long[] array1, long... array2) {
/*  995 */     if (array1 == null)
/*  996 */       return clone(array2); 
/*  997 */     if (array2 == null) {
/*  998 */       return clone(array1);
/*      */     }
/* 1000 */     long[] joinedArray = new long[array1.length + array2.length];
/* 1001 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1002 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1003 */     return joinedArray;
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
/*      */   public static short[] addAll(short[] array1, short... array2) {
/* 1024 */     if (array1 == null)
/* 1025 */       return clone(array2); 
/* 1026 */     if (array2 == null) {
/* 1027 */       return clone(array1);
/*      */     }
/* 1029 */     short[] joinedArray = new short[array1.length + array2.length];
/* 1030 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 1031 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1032 */     return joinedArray;
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
/*      */   public static <T> T[] addAll(T[] array1, T... array2) {
/* 1060 */     if (array1 == null)
/* 1061 */       return clone(array2); 
/* 1062 */     if (array2 == null) {
/* 1063 */       return clone(array1);
/*      */     }
/* 1065 */     Class<?> type1 = array1.getClass().getComponentType();
/*      */     
/* 1067 */     T[] joinedArray = (T[])Array.newInstance(type1, array1.length + array2.length);
/* 1068 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*      */     try {
/* 1070 */       System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 1071 */     } catch (ArrayStoreException ase) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1078 */       Class<?> type2 = array2.getClass().getComponentType();
/* 1079 */       if (!type1.isAssignableFrom(type2)) {
/* 1080 */         throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1
/* 1081 */             .getName(), ase);
/*      */       }
/* 1083 */       throw ase;
/*      */     } 
/* 1085 */     return joinedArray;
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
/*      */   public static boolean[] addFirst(boolean[] array, boolean element) {
/* 1114 */     return (array == null) ? add(array, element) : insert(0, array, new boolean[] { element });
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
/*      */   public static byte[] addFirst(byte[] array, byte element) {
/* 1143 */     return (array == null) ? add(array, element) : insert(0, array, new byte[] { element });
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
/*      */   public static char[] addFirst(char[] array, char element) {
/* 1172 */     return (array == null) ? add(array, element) : insert(0, array, new char[] { element });
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
/*      */   public static double[] addFirst(double[] array, double element) {
/* 1201 */     return (array == null) ? add(array, element) : insert(0, array, new double[] { element });
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
/*      */   public static float[] addFirst(float[] array, float element) {
/* 1230 */     return (array == null) ? add(array, element) : insert(0, array, new float[] { element });
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
/*      */   public static int[] addFirst(int[] array, int element) {
/* 1259 */     return (array == null) ? add(array, element) : insert(0, array, new int[] { element });
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
/*      */   public static long[] addFirst(long[] array, long element) {
/* 1288 */     return (array == null) ? add(array, element) : insert(0, array, new long[] { element });
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
/*      */   public static short[] addFirst(short[] array, short element) {
/* 1317 */     return (array == null) ? add(array, element) : insert(0, array, new short[] { element });
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
/*      */   public static <T> T[] addFirst(T[] array, T element) {
/* 1351 */     return (array == null) ? add(array, element) : insert(0, array, (T[])new Object[] { element });
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
/*      */   public static boolean[] clone(boolean[] array) {
/* 1364 */     if (array == null) {
/* 1365 */       return null;
/*      */     }
/* 1367 */     return (boolean[])array.clone();
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
/*      */   public static byte[] clone(byte[] array) {
/* 1380 */     if (array == null) {
/* 1381 */       return null;
/*      */     }
/* 1383 */     return (byte[])array.clone();
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
/*      */   public static char[] clone(char[] array) {
/* 1396 */     if (array == null) {
/* 1397 */       return null;
/*      */     }
/* 1399 */     return (char[])array.clone();
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
/*      */   public static double[] clone(double[] array) {
/* 1412 */     if (array == null) {
/* 1413 */       return null;
/*      */     }
/* 1415 */     return (double[])array.clone();
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
/*      */   public static float[] clone(float[] array) {
/* 1428 */     if (array == null) {
/* 1429 */       return null;
/*      */     }
/* 1431 */     return (float[])array.clone();
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
/*      */   public static int[] clone(int[] array) {
/* 1444 */     if (array == null) {
/* 1445 */       return null;
/*      */     }
/* 1447 */     return (int[])array.clone();
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
/*      */   public static long[] clone(long[] array) {
/* 1460 */     if (array == null) {
/* 1461 */       return null;
/*      */     }
/* 1463 */     return (long[])array.clone();
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
/*      */   public static short[] clone(short[] array) {
/* 1476 */     if (array == null) {
/* 1477 */       return null;
/*      */     }
/* 1479 */     return (short[])array.clone();
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
/*      */   public static <T> T[] clone(T[] array) {
/* 1498 */     if (array == null) {
/* 1499 */       return null;
/*      */     }
/* 1501 */     return (T[])array.clone();
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
/*      */   public static boolean contains(boolean[] array, boolean valueToFind) {
/* 1514 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(byte[] array, byte valueToFind) {
/* 1527 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(char[] array, char valueToFind) {
/* 1541 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(double[] array, double valueToFind) {
/* 1554 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(double[] array, double valueToFind, double tolerance) {
/* 1571 */     return (indexOf(array, valueToFind, 0, tolerance) != -1);
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
/*      */   public static boolean contains(float[] array, float valueToFind) {
/* 1584 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(int[] array, int valueToFind) {
/* 1597 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(long[] array, long valueToFind) {
/* 1610 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(Object[] array, Object objectToFind) {
/* 1623 */     return (indexOf(array, objectToFind) != -1);
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
/*      */   public static boolean contains(short[] array, short valueToFind) {
/* 1636 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
/* 1649 */     if (array != null) {
/* 1650 */       int arrayLength = Array.getLength(array);
/* 1651 */       Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
/* 1652 */       System.arraycopy(array, 0, newArray, 0, arrayLength);
/* 1653 */       return newArray;
/*      */     } 
/* 1655 */     return Array.newInstance(newArrayComponentType, 1);
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
/*      */   public static <T> T get(T[] array, int index) {
/* 1668 */     return get(array, index, null);
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
/*      */   public static <T> T get(T[] array, int index, T defaultValue) {
/* 1682 */     return isArrayIndexValid(array, index) ? array[index] : defaultValue;
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
/*      */   public static int getLength(Object array) {
/* 1707 */     if (array == null) {
/* 1708 */       return 0;
/*      */     }
/* 1710 */     return Array.getLength(array);
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
/*      */   public static int hashCode(Object array) {
/* 1722 */     return (new HashCodeBuilder()).append(array).toHashCode();
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
/*      */   public static BitSet indexesOf(boolean[] array, boolean valueToFind) {
/* 1737 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(boolean[] array, boolean valueToFind, int startIndex) {
/* 1757 */     BitSet bitSet = new BitSet();
/*      */     
/* 1759 */     if (array == null) {
/* 1760 */       return bitSet;
/*      */     }
/*      */     
/* 1763 */     while (startIndex < array.length) {
/* 1764 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 1766 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 1770 */       bitSet.set(startIndex);
/* 1771 */       startIndex++;
/*      */     } 
/*      */     
/* 1774 */     return bitSet;
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
/*      */   public static BitSet indexesOf(byte[] array, byte valueToFind) {
/* 1789 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(byte[] array, byte valueToFind, int startIndex) {
/* 1808 */     BitSet bitSet = new BitSet();
/*      */     
/* 1810 */     if (array == null) {
/* 1811 */       return bitSet;
/*      */     }
/*      */     
/* 1814 */     while (startIndex < array.length) {
/* 1815 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 1817 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 1821 */       bitSet.set(startIndex);
/* 1822 */       startIndex++;
/*      */     } 
/*      */     
/* 1825 */     return bitSet;
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
/*      */   public static BitSet indexesOf(char[] array, char valueToFind) {
/* 1840 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(char[] array, char valueToFind, int startIndex) {
/* 1859 */     BitSet bitSet = new BitSet();
/*      */     
/* 1861 */     if (array == null) {
/* 1862 */       return bitSet;
/*      */     }
/*      */     
/* 1865 */     while (startIndex < array.length) {
/* 1866 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 1868 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 1872 */       bitSet.set(startIndex);
/* 1873 */       startIndex++;
/*      */     } 
/*      */     
/* 1876 */     return bitSet;
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
/*      */   public static BitSet indexesOf(double[] array, double valueToFind) {
/* 1891 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(double[] array, double valueToFind, double tolerance) {
/* 1912 */     return indexesOf(array, valueToFind, 0, tolerance);
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
/*      */   public static BitSet indexesOf(double[] array, double valueToFind, int startIndex) {
/* 1931 */     BitSet bitSet = new BitSet();
/*      */     
/* 1933 */     if (array == null) {
/* 1934 */       return bitSet;
/*      */     }
/*      */     
/* 1937 */     while (startIndex < array.length) {
/* 1938 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 1940 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 1944 */       bitSet.set(startIndex);
/* 1945 */       startIndex++;
/*      */     } 
/*      */     
/* 1948 */     return bitSet;
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
/*      */   public static BitSet indexesOf(double[] array, double valueToFind, int startIndex, double tolerance) {
/* 1973 */     BitSet bitSet = new BitSet();
/*      */     
/* 1975 */     if (array == null) {
/* 1976 */       return bitSet;
/*      */     }
/*      */     
/* 1979 */     while (startIndex < array.length) {
/* 1980 */       startIndex = indexOf(array, valueToFind, startIndex, tolerance);
/*      */       
/* 1982 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 1986 */       bitSet.set(startIndex);
/* 1987 */       startIndex++;
/*      */     } 
/*      */     
/* 1990 */     return bitSet;
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
/*      */   public static BitSet indexesOf(float[] array, float valueToFind) {
/* 2005 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(float[] array, float valueToFind, int startIndex) {
/* 2024 */     BitSet bitSet = new BitSet();
/*      */     
/* 2026 */     if (array == null) {
/* 2027 */       return bitSet;
/*      */     }
/*      */     
/* 2030 */     while (startIndex < array.length) {
/* 2031 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 2033 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 2037 */       bitSet.set(startIndex);
/* 2038 */       startIndex++;
/*      */     } 
/*      */     
/* 2041 */     return bitSet;
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
/*      */   public static BitSet indexesOf(int[] array, int valueToFind) {
/* 2056 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(int[] array, int valueToFind, int startIndex) {
/* 2075 */     BitSet bitSet = new BitSet();
/*      */     
/* 2077 */     if (array == null) {
/* 2078 */       return bitSet;
/*      */     }
/*      */     
/* 2081 */     while (startIndex < array.length) {
/* 2082 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 2084 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 2088 */       bitSet.set(startIndex);
/* 2089 */       startIndex++;
/*      */     } 
/*      */     
/* 2092 */     return bitSet;
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
/*      */   public static BitSet indexesOf(long[] array, long valueToFind) {
/* 2107 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(long[] array, long valueToFind, int startIndex) {
/* 2126 */     BitSet bitSet = new BitSet();
/*      */     
/* 2128 */     if (array == null) {
/* 2129 */       return bitSet;
/*      */     }
/*      */     
/* 2132 */     while (startIndex < array.length) {
/* 2133 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 2135 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 2139 */       bitSet.set(startIndex);
/* 2140 */       startIndex++;
/*      */     } 
/*      */     
/* 2143 */     return bitSet;
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
/*      */   public static BitSet indexesOf(Object[] array, Object objectToFind) {
/* 2158 */     return indexesOf(array, objectToFind, 0);
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
/*      */   public static BitSet indexesOf(Object[] array, Object objectToFind, int startIndex) {
/* 2177 */     BitSet bitSet = new BitSet();
/*      */     
/* 2179 */     if (array == null) {
/* 2180 */       return bitSet;
/*      */     }
/*      */     
/* 2183 */     while (startIndex < array.length) {
/* 2184 */       startIndex = indexOf(array, objectToFind, startIndex);
/*      */       
/* 2186 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 2190 */       bitSet.set(startIndex);
/* 2191 */       startIndex++;
/*      */     } 
/*      */     
/* 2194 */     return bitSet;
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
/*      */   public static BitSet indexesOf(short[] array, short valueToFind) {
/* 2209 */     return indexesOf(array, valueToFind, 0);
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
/*      */   public static BitSet indexesOf(short[] array, short valueToFind, int startIndex) {
/* 2228 */     BitSet bitSet = new BitSet();
/*      */     
/* 2230 */     if (array == null) {
/* 2231 */       return bitSet;
/*      */     }
/*      */     
/* 2234 */     while (startIndex < array.length) {
/* 2235 */       startIndex = indexOf(array, valueToFind, startIndex);
/*      */       
/* 2237 */       if (startIndex == -1) {
/*      */         break;
/*      */       }
/*      */       
/* 2241 */       bitSet.set(startIndex);
/* 2242 */       startIndex++;
/*      */     } 
/*      */     
/* 2245 */     return bitSet;
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
/*      */   public static int indexOf(boolean[] array, boolean valueToFind) {
/* 2261 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
/* 2280 */     if (isEmpty(array)) {
/* 2281 */       return -1;
/*      */     }
/* 2283 */     if (startIndex < 0) {
/* 2284 */       startIndex = 0;
/*      */     }
/* 2286 */     for (int i = startIndex; i < array.length; i++) {
/* 2287 */       if (valueToFind == array[i]) {
/* 2288 */         return i;
/*      */       }
/*      */     } 
/* 2291 */     return -1;
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
/*      */   public static int indexOf(byte[] array, byte valueToFind) {
/* 2307 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
/* 2325 */     if (array == null) {
/* 2326 */       return -1;
/*      */     }
/* 2328 */     if (startIndex < 0) {
/* 2329 */       startIndex = 0;
/*      */     }
/* 2331 */     for (int i = startIndex; i < array.length; i++) {
/* 2332 */       if (valueToFind == array[i]) {
/* 2333 */         return i;
/*      */       }
/*      */     } 
/* 2336 */     return -1;
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
/*      */   public static int indexOf(char[] array, char valueToFind) {
/* 2353 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(char[] array, char valueToFind, int startIndex) {
/* 2372 */     if (array == null) {
/* 2373 */       return -1;
/*      */     }
/* 2375 */     if (startIndex < 0) {
/* 2376 */       startIndex = 0;
/*      */     }
/* 2378 */     for (int i = startIndex; i < array.length; i++) {
/* 2379 */       if (valueToFind == array[i]) {
/* 2380 */         return i;
/*      */       }
/*      */     } 
/* 2383 */     return -1;
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
/*      */   public static int indexOf(double[] array, double valueToFind) {
/* 2399 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(double[] array, double valueToFind, double tolerance) {
/* 2416 */     return indexOf(array, valueToFind, 0, tolerance);
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
/*      */   public static int indexOf(double[] array, double valueToFind, int startIndex) {
/* 2434 */     if (isEmpty(array)) {
/* 2435 */       return -1;
/*      */     }
/* 2437 */     if (startIndex < 0) {
/* 2438 */       startIndex = 0;
/*      */     }
/* 2440 */     for (int i = startIndex; i < array.length; i++) {
/* 2441 */       if (valueToFind == array[i]) {
/* 2442 */         return i;
/*      */       }
/*      */     } 
/* 2445 */     return -1;
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
/*      */   public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
/* 2466 */     if (isEmpty(array)) {
/* 2467 */       return -1;
/*      */     }
/* 2469 */     if (startIndex < 0) {
/* 2470 */       startIndex = 0;
/*      */     }
/* 2472 */     double min = valueToFind - tolerance;
/* 2473 */     double max = valueToFind + tolerance;
/* 2474 */     for (int i = startIndex; i < array.length; i++) {
/* 2475 */       if (array[i] >= min && array[i] <= max) {
/* 2476 */         return i;
/*      */       }
/*      */     } 
/* 2479 */     return -1;
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
/*      */   public static int indexOf(float[] array, float valueToFind) {
/* 2495 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(float[] array, float valueToFind, int startIndex) {
/* 2513 */     if (isEmpty(array)) {
/* 2514 */       return -1;
/*      */     }
/* 2516 */     if (startIndex < 0) {
/* 2517 */       startIndex = 0;
/*      */     }
/* 2519 */     for (int i = startIndex; i < array.length; i++) {
/* 2520 */       if (valueToFind == array[i]) {
/* 2521 */         return i;
/*      */       }
/*      */     } 
/* 2524 */     return -1;
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
/*      */   public static int indexOf(int[] array, int valueToFind) {
/* 2540 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(int[] array, int valueToFind, int startIndex) {
/* 2558 */     if (array == null) {
/* 2559 */       return -1;
/*      */     }
/* 2561 */     if (startIndex < 0) {
/* 2562 */       startIndex = 0;
/*      */     }
/* 2564 */     for (int i = startIndex; i < array.length; i++) {
/* 2565 */       if (valueToFind == array[i]) {
/* 2566 */         return i;
/*      */       }
/*      */     } 
/* 2569 */     return -1;
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
/*      */   public static int indexOf(long[] array, long valueToFind) {
/* 2585 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(long[] array, long valueToFind, int startIndex) {
/* 2603 */     if (array == null) {
/* 2604 */       return -1;
/*      */     }
/* 2606 */     if (startIndex < 0) {
/* 2607 */       startIndex = 0;
/*      */     }
/* 2609 */     for (int i = startIndex; i < array.length; i++) {
/* 2610 */       if (valueToFind == array[i]) {
/* 2611 */         return i;
/*      */       }
/*      */     } 
/* 2614 */     return -1;
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
/*      */   public static int indexOf(Object[] array, Object objectToFind) {
/* 2630 */     return indexOf(array, objectToFind, 0);
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
/*      */   public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
/* 2648 */     if (array == null) {
/* 2649 */       return -1;
/*      */     }
/* 2651 */     if (startIndex < 0) {
/* 2652 */       startIndex = 0;
/*      */     }
/* 2654 */     if (objectToFind == null) {
/* 2655 */       for (int i = startIndex; i < array.length; i++) {
/* 2656 */         if (array[i] == null) {
/* 2657 */           return i;
/*      */         }
/*      */       } 
/*      */     } else {
/* 2661 */       for (int i = startIndex; i < array.length; i++) {
/* 2662 */         if (objectToFind.equals(array[i])) {
/* 2663 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 2667 */     return -1;
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
/*      */   public static int indexOf(short[] array, short valueToFind) {
/* 2683 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(short[] array, short valueToFind, int startIndex) {
/* 2701 */     if (array == null) {
/* 2702 */       return -1;
/*      */     }
/* 2704 */     if (startIndex < 0) {
/* 2705 */       startIndex = 0;
/*      */     }
/* 2707 */     for (int i = startIndex; i < array.length; i++) {
/* 2708 */       if (valueToFind == array[i]) {
/* 2709 */         return i;
/*      */       }
/*      */     } 
/* 2712 */     return -1;
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
/*      */   public static boolean[] insert(int index, boolean[] array, boolean... values) {
/* 2735 */     if (array == null) {
/* 2736 */       return null;
/*      */     }
/* 2738 */     if (isEmpty(values)) {
/* 2739 */       return clone(array);
/*      */     }
/* 2741 */     if (index < 0 || index > array.length) {
/* 2742 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2745 */     boolean[] result = new boolean[array.length + values.length];
/*      */     
/* 2747 */     System.arraycopy(values, 0, result, index, values.length);
/* 2748 */     if (index > 0) {
/* 2749 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 2751 */     if (index < array.length) {
/* 2752 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 2754 */     return result;
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
/*      */   public static byte[] insert(int index, byte[] array, byte... values) {
/* 2777 */     if (array == null) {
/* 2778 */       return null;
/*      */     }
/* 2780 */     if (isEmpty(values)) {
/* 2781 */       return clone(array);
/*      */     }
/* 2783 */     if (index < 0 || index > array.length) {
/* 2784 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2787 */     byte[] result = new byte[array.length + values.length];
/*      */     
/* 2789 */     System.arraycopy(values, 0, result, index, values.length);
/* 2790 */     if (index > 0) {
/* 2791 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 2793 */     if (index < array.length) {
/* 2794 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 2796 */     return result;
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
/*      */   public static char[] insert(int index, char[] array, char... values) {
/* 2819 */     if (array == null) {
/* 2820 */       return null;
/*      */     }
/* 2822 */     if (isEmpty(values)) {
/* 2823 */       return clone(array);
/*      */     }
/* 2825 */     if (index < 0 || index > array.length) {
/* 2826 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2829 */     char[] result = new char[array.length + values.length];
/*      */     
/* 2831 */     System.arraycopy(values, 0, result, index, values.length);
/* 2832 */     if (index > 0) {
/* 2833 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 2835 */     if (index < array.length) {
/* 2836 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 2838 */     return result;
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
/*      */   public static double[] insert(int index, double[] array, double... values) {
/* 2861 */     if (array == null) {
/* 2862 */       return null;
/*      */     }
/* 2864 */     if (isEmpty(values)) {
/* 2865 */       return clone(array);
/*      */     }
/* 2867 */     if (index < 0 || index > array.length) {
/* 2868 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2871 */     double[] result = new double[array.length + values.length];
/*      */     
/* 2873 */     System.arraycopy(values, 0, result, index, values.length);
/* 2874 */     if (index > 0) {
/* 2875 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 2877 */     if (index < array.length) {
/* 2878 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 2880 */     return result;
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
/*      */   public static float[] insert(int index, float[] array, float... values) {
/* 2903 */     if (array == null) {
/* 2904 */       return null;
/*      */     }
/* 2906 */     if (isEmpty(values)) {
/* 2907 */       return clone(array);
/*      */     }
/* 2909 */     if (index < 0 || index > array.length) {
/* 2910 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2913 */     float[] result = new float[array.length + values.length];
/*      */     
/* 2915 */     System.arraycopy(values, 0, result, index, values.length);
/* 2916 */     if (index > 0) {
/* 2917 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 2919 */     if (index < array.length) {
/* 2920 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 2922 */     return result;
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
/*      */   public static int[] insert(int index, int[] array, int... values) {
/* 2945 */     if (array == null) {
/* 2946 */       return null;
/*      */     }
/* 2948 */     if (isEmpty(values)) {
/* 2949 */       return clone(array);
/*      */     }
/* 2951 */     if (index < 0 || index > array.length) {
/* 2952 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2955 */     int[] result = new int[array.length + values.length];
/*      */     
/* 2957 */     System.arraycopy(values, 0, result, index, values.length);
/* 2958 */     if (index > 0) {
/* 2959 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 2961 */     if (index < array.length) {
/* 2962 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 2964 */     return result;
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
/*      */   public static long[] insert(int index, long[] array, long... values) {
/* 2987 */     if (array == null) {
/* 2988 */       return null;
/*      */     }
/* 2990 */     if (isEmpty(values)) {
/* 2991 */       return clone(array);
/*      */     }
/* 2993 */     if (index < 0 || index > array.length) {
/* 2994 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 2997 */     long[] result = new long[array.length + values.length];
/*      */     
/* 2999 */     System.arraycopy(values, 0, result, index, values.length);
/* 3000 */     if (index > 0) {
/* 3001 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 3003 */     if (index < array.length) {
/* 3004 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 3006 */     return result;
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
/*      */   public static short[] insert(int index, short[] array, short... values) {
/* 3029 */     if (array == null) {
/* 3030 */       return null;
/*      */     }
/* 3032 */     if (isEmpty(values)) {
/* 3033 */       return clone(array);
/*      */     }
/* 3035 */     if (index < 0 || index > array.length) {
/* 3036 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 3039 */     short[] result = new short[array.length + values.length];
/*      */     
/* 3041 */     System.arraycopy(values, 0, result, index, values.length);
/* 3042 */     if (index > 0) {
/* 3043 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 3045 */     if (index < array.length) {
/* 3046 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 3048 */     return result;
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
/*      */   @SafeVarargs
/*      */   public static <T> T[] insert(int index, T[] array, T... values) {
/* 3081 */     if (array == null) {
/* 3082 */       return null;
/*      */     }
/* 3084 */     if (isEmpty((Object[])values)) {
/* 3085 */       return clone(array);
/*      */     }
/* 3087 */     if (index < 0 || index > array.length) {
/* 3088 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
/*      */     }
/*      */     
/* 3091 */     Class<?> type = array.getClass().getComponentType();
/*      */ 
/*      */     
/* 3094 */     T[] result = (T[])Array.newInstance(type, array.length + values.length);
/*      */     
/* 3096 */     System.arraycopy(values, 0, result, index, values.length);
/* 3097 */     if (index > 0) {
/* 3098 */       System.arraycopy(array, 0, result, 0, index);
/*      */     }
/* 3100 */     if (index < array.length) {
/* 3101 */       System.arraycopy(array, index, result, index + values.length, array.length - index);
/*      */     }
/* 3103 */     return result;
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
/*      */   public static <T> boolean isArrayIndexValid(T[] array, int index) {
/* 3122 */     return (index >= 0 && getLength(array) > index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(boolean[] array) {
/* 3133 */     return (getLength(array) == 0);
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
/*      */   public static boolean isEmpty(byte[] array) {
/* 3147 */     return (getLength(array) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(char[] array) {
/* 3158 */     return (getLength(array) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(double[] array) {
/* 3169 */     return (getLength(array) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(float[] array) {
/* 3180 */     return (getLength(array) == 0);
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
/*      */   public static boolean isEmpty(int[] array) {
/* 3193 */     return (getLength(array) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(long[] array) {
/* 3204 */     return (getLength(array) == 0);
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
/*      */   public static boolean isEmpty(Object[] array) {
/* 3216 */     return (getLength(array) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(short[] array) {
/* 3227 */     return (getLength(array) == 0);
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
/*      */   @Deprecated
/*      */   public static boolean isEquals(Object array1, Object array2) {
/* 3244 */     return (new EqualsBuilder()).append(array1, array2).isEquals();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(boolean[] array) {
/* 3255 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(byte[] array) {
/* 3266 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(char[] array) {
/* 3277 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(double[] array) {
/* 3288 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(float[] array) {
/* 3299 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(int[] array) {
/* 3310 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(long[] array) {
/* 3321 */     return !isEmpty(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(short[] array) {
/* 3332 */     return !isEmpty(array);
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
/*      */   public static <T> boolean isNotEmpty(T[] array) {
/* 3345 */     return !isEmpty((Object[])array);
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
/*      */   public static boolean isSameLength(boolean[] array1, boolean[] array2) {
/* 3358 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(byte[] array1, byte[] array2) {
/* 3371 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(char[] array1, char[] array2) {
/* 3384 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(double[] array1, double[] array2) {
/* 3397 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(float[] array1, float[] array2) {
/* 3410 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(int[] array1, int[] array2) {
/* 3423 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(long[] array1, long[] array2) {
/* 3436 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(Object array1, Object array2) {
/* 3453 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(Object[] array1, Object[] array2) {
/* 3468 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameLength(short[] array1, short[] array2) {
/* 3481 */     return (getLength(array1) == getLength(array2));
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
/*      */   public static boolean isSameType(Object array1, Object array2) {
/* 3494 */     if (array1 == null || array2 == null) {
/* 3495 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 3497 */     return array1.getClass().getName().equals(array2.getClass().getName());
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
/*      */   public static boolean isSorted(boolean[] array) {
/* 3509 */     if (array == null || array.length < 2) {
/* 3510 */       return true;
/*      */     }
/*      */     
/* 3513 */     boolean previous = array[0];
/* 3514 */     int n = array.length;
/* 3515 */     for (int i = 1; i < n; i++) {
/* 3516 */       boolean current = array[i];
/* 3517 */       if (BooleanUtils.compare(previous, current) > 0) {
/* 3518 */         return false;
/*      */       }
/*      */       
/* 3521 */       previous = current;
/*      */     } 
/* 3523 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(byte[] array) {
/* 3534 */     if (array == null || array.length < 2) {
/* 3535 */       return true;
/*      */     }
/*      */     
/* 3538 */     byte previous = array[0];
/* 3539 */     int n = array.length;
/* 3540 */     for (int i = 1; i < n; i++) {
/* 3541 */       byte current = array[i];
/* 3542 */       if (NumberUtils.compare(previous, current) > 0) {
/* 3543 */         return false;
/*      */       }
/*      */       
/* 3546 */       previous = current;
/*      */     } 
/* 3548 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(char[] array) {
/* 3559 */     if (array == null || array.length < 2) {
/* 3560 */       return true;
/*      */     }
/*      */     
/* 3563 */     char previous = array[0];
/* 3564 */     int n = array.length;
/* 3565 */     for (int i = 1; i < n; i++) {
/* 3566 */       char current = array[i];
/* 3567 */       if (CharUtils.compare(previous, current) > 0) {
/* 3568 */         return false;
/*      */       }
/*      */       
/* 3571 */       previous = current;
/*      */     } 
/* 3573 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(double[] array) {
/* 3584 */     if (array == null || array.length < 2) {
/* 3585 */       return true;
/*      */     }
/*      */     
/* 3588 */     double previous = array[0];
/* 3589 */     int n = array.length;
/* 3590 */     for (int i = 1; i < n; i++) {
/* 3591 */       double current = array[i];
/* 3592 */       if (Double.compare(previous, current) > 0) {
/* 3593 */         return false;
/*      */       }
/*      */       
/* 3596 */       previous = current;
/*      */     } 
/* 3598 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(float[] array) {
/* 3609 */     if (array == null || array.length < 2) {
/* 3610 */       return true;
/*      */     }
/*      */     
/* 3613 */     float previous = array[0];
/* 3614 */     int n = array.length;
/* 3615 */     for (int i = 1; i < n; i++) {
/* 3616 */       float current = array[i];
/* 3617 */       if (Float.compare(previous, current) > 0) {
/* 3618 */         return false;
/*      */       }
/*      */       
/* 3621 */       previous = current;
/*      */     } 
/* 3623 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(int[] array) {
/* 3634 */     if (array == null || array.length < 2) {
/* 3635 */       return true;
/*      */     }
/*      */     
/* 3638 */     int previous = array[0];
/* 3639 */     int n = array.length;
/* 3640 */     for (int i = 1; i < n; i++) {
/* 3641 */       int current = array[i];
/* 3642 */       if (NumberUtils.compare(previous, current) > 0) {
/* 3643 */         return false;
/*      */       }
/*      */       
/* 3646 */       previous = current;
/*      */     } 
/* 3648 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(long[] array) {
/* 3659 */     if (array == null || array.length < 2) {
/* 3660 */       return true;
/*      */     }
/*      */     
/* 3663 */     long previous = array[0];
/* 3664 */     int n = array.length;
/* 3665 */     for (int i = 1; i < n; i++) {
/* 3666 */       long current = array[i];
/* 3667 */       if (NumberUtils.compare(previous, current) > 0) {
/* 3668 */         return false;
/*      */       }
/*      */       
/* 3671 */       previous = current;
/*      */     } 
/* 3673 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSorted(short[] array) {
/* 3684 */     if (array == null || array.length < 2) {
/* 3685 */       return true;
/*      */     }
/*      */     
/* 3688 */     short previous = array[0];
/* 3689 */     int n = array.length;
/* 3690 */     for (int i = 1; i < n; i++) {
/* 3691 */       short current = array[i];
/* 3692 */       if (NumberUtils.compare(previous, current) > 0) {
/* 3693 */         return false;
/*      */       }
/*      */       
/* 3696 */       previous = current;
/*      */     } 
/* 3698 */     return true;
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
/*      */   public static <T extends Comparable<? super T>> boolean isSorted(T[] array) {
/* 3711 */     return isSorted(array, Comparable::compareTo);
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
/*      */   public static <T> boolean isSorted(T[] array, Comparator<T> comparator) {
/* 3724 */     if (comparator == null) {
/* 3725 */       throw new IllegalArgumentException("Comparator should not be null.");
/*      */     }
/*      */     
/* 3728 */     if (array == null || array.length < 2) {
/* 3729 */       return true;
/*      */     }
/*      */     
/* 3732 */     T previous = array[0];
/* 3733 */     int n = array.length;
/* 3734 */     for (int i = 1; i < n; i++) {
/* 3735 */       T current = array[i];
/* 3736 */       if (comparator.compare(previous, current) > 0) {
/* 3737 */         return false;
/*      */       }
/*      */       
/* 3740 */       previous = current;
/*      */     } 
/* 3742 */     return true;
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
/*      */   public static int lastIndexOf(boolean[] array, boolean valueToFind) {
/* 3757 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
/* 3775 */     if (isEmpty(array)) {
/* 3776 */       return -1;
/*      */     }
/* 3778 */     if (startIndex < 0)
/* 3779 */       return -1; 
/* 3780 */     if (startIndex >= array.length) {
/* 3781 */       startIndex = array.length - 1;
/*      */     }
/* 3783 */     for (int i = startIndex; i >= 0; i--) {
/* 3784 */       if (valueToFind == array[i]) {
/* 3785 */         return i;
/*      */       }
/*      */     } 
/* 3788 */     return -1;
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
/*      */   public static int lastIndexOf(byte[] array, byte valueToFind) {
/* 3802 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
/* 3820 */     if (array == null) {
/* 3821 */       return -1;
/*      */     }
/* 3823 */     if (startIndex < 0)
/* 3824 */       return -1; 
/* 3825 */     if (startIndex >= array.length) {
/* 3826 */       startIndex = array.length - 1;
/*      */     }
/* 3828 */     for (int i = startIndex; i >= 0; i--) {
/* 3829 */       if (valueToFind == array[i]) {
/* 3830 */         return i;
/*      */       }
/*      */     } 
/* 3833 */     return -1;
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
/*      */   public static int lastIndexOf(char[] array, char valueToFind) {
/* 3848 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
/* 3867 */     if (array == null) {
/* 3868 */       return -1;
/*      */     }
/* 3870 */     if (startIndex < 0)
/* 3871 */       return -1; 
/* 3872 */     if (startIndex >= array.length) {
/* 3873 */       startIndex = array.length - 1;
/*      */     }
/* 3875 */     for (int i = startIndex; i >= 0; i--) {
/* 3876 */       if (valueToFind == array[i]) {
/* 3877 */         return i;
/*      */       }
/*      */     } 
/* 3880 */     return -1;
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind) {
/* 3894 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
/* 3911 */     return lastIndexOf(array, valueToFind, 2147483647, tolerance);
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
/* 3929 */     if (isEmpty(array)) {
/* 3930 */       return -1;
/*      */     }
/* 3932 */     if (startIndex < 0)
/* 3933 */       return -1; 
/* 3934 */     if (startIndex >= array.length) {
/* 3935 */       startIndex = array.length - 1;
/*      */     }
/* 3937 */     for (int i = startIndex; i >= 0; i--) {
/* 3938 */       if (valueToFind == array[i]) {
/* 3939 */         return i;
/*      */       }
/*      */     } 
/* 3942 */     return -1;
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
/* 3963 */     if (isEmpty(array)) {
/* 3964 */       return -1;
/*      */     }
/* 3966 */     if (startIndex < 0)
/* 3967 */       return -1; 
/* 3968 */     if (startIndex >= array.length) {
/* 3969 */       startIndex = array.length - 1;
/*      */     }
/* 3971 */     double min = valueToFind - tolerance;
/* 3972 */     double max = valueToFind + tolerance;
/* 3973 */     for (int i = startIndex; i >= 0; i--) {
/* 3974 */       if (array[i] >= min && array[i] <= max) {
/* 3975 */         return i;
/*      */       }
/*      */     } 
/* 3978 */     return -1;
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
/*      */   public static int lastIndexOf(float[] array, float valueToFind) {
/* 3993 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
/* 4011 */     if (isEmpty(array)) {
/* 4012 */       return -1;
/*      */     }
/* 4014 */     if (startIndex < 0)
/* 4015 */       return -1; 
/* 4016 */     if (startIndex >= array.length) {
/* 4017 */       startIndex = array.length - 1;
/*      */     }
/* 4019 */     for (int i = startIndex; i >= 0; i--) {
/* 4020 */       if (valueToFind == array[i]) {
/* 4021 */         return i;
/*      */       }
/*      */     } 
/* 4024 */     return -1;
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
/*      */   public static int lastIndexOf(int[] array, int valueToFind) {
/* 4038 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
/* 4056 */     if (array == null) {
/* 4057 */       return -1;
/*      */     }
/* 4059 */     if (startIndex < 0)
/* 4060 */       return -1; 
/* 4061 */     if (startIndex >= array.length) {
/* 4062 */       startIndex = array.length - 1;
/*      */     }
/* 4064 */     for (int i = startIndex; i >= 0; i--) {
/* 4065 */       if (valueToFind == array[i]) {
/* 4066 */         return i;
/*      */       }
/*      */     } 
/* 4069 */     return -1;
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
/*      */   public static int lastIndexOf(long[] array, long valueToFind) {
/* 4083 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
/* 4101 */     if (array == null) {
/* 4102 */       return -1;
/*      */     }
/* 4104 */     if (startIndex < 0)
/* 4105 */       return -1; 
/* 4106 */     if (startIndex >= array.length) {
/* 4107 */       startIndex = array.length - 1;
/*      */     }
/* 4109 */     for (int i = startIndex; i >= 0; i--) {
/* 4110 */       if (valueToFind == array[i]) {
/* 4111 */         return i;
/*      */       }
/*      */     } 
/* 4114 */     return -1;
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
/*      */   public static int lastIndexOf(Object[] array, Object objectToFind) {
/* 4128 */     return lastIndexOf(array, objectToFind, 2147483647);
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
/*      */   public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
/* 4146 */     if (array == null) {
/* 4147 */       return -1;
/*      */     }
/* 4149 */     if (startIndex < 0)
/* 4150 */       return -1; 
/* 4151 */     if (startIndex >= array.length) {
/* 4152 */       startIndex = array.length - 1;
/*      */     }
/* 4154 */     if (objectToFind == null) {
/* 4155 */       for (int i = startIndex; i >= 0; i--) {
/* 4156 */         if (array[i] == null) {
/* 4157 */           return i;
/*      */         }
/*      */       } 
/* 4160 */     } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
/* 4161 */       for (int i = startIndex; i >= 0; i--) {
/* 4162 */         if (objectToFind.equals(array[i])) {
/* 4163 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 4167 */     return -1;
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
/*      */   public static int lastIndexOf(short[] array, short valueToFind) {
/* 4181 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
/* 4199 */     if (array == null) {
/* 4200 */       return -1;
/*      */     }
/* 4202 */     if (startIndex < 0)
/* 4203 */       return -1; 
/* 4204 */     if (startIndex >= array.length) {
/* 4205 */       startIndex = array.length - 1;
/*      */     }
/* 4207 */     for (int i = startIndex; i >= 0; i--) {
/* 4208 */       if (valueToFind == array[i]) {
/* 4209 */         return i;
/*      */       }
/*      */     } 
/* 4212 */     return -1;
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
/*      */   public static boolean[] nullToEmpty(boolean[] array) {
/* 4229 */     if (isEmpty(array)) {
/* 4230 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 4232 */     return array;
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
/*      */   public static Boolean[] nullToEmpty(Boolean[] array) {
/* 4249 */     if (isEmpty((Object[])array)) {
/* 4250 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*      */     }
/* 4252 */     return array;
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
/*      */   public static byte[] nullToEmpty(byte[] array) {
/* 4269 */     if (isEmpty(array)) {
/* 4270 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 4272 */     return array;
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
/*      */   public static Byte[] nullToEmpty(Byte[] array) {
/* 4289 */     if (isEmpty((Object[])array)) {
/* 4290 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*      */     }
/* 4292 */     return array;
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
/*      */   public static char[] nullToEmpty(char[] array) {
/* 4309 */     if (isEmpty(array)) {
/* 4310 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 4312 */     return array;
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
/*      */   public static Character[] nullToEmpty(Character[] array) {
/* 4329 */     if (isEmpty((Object[])array)) {
/* 4330 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*      */     }
/* 4332 */     return array;
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
/*      */   public static Class<?>[] nullToEmpty(Class<?>[] array) {
/* 4349 */     if (isEmpty((Object[])array)) {
/* 4350 */       return EMPTY_CLASS_ARRAY;
/*      */     }
/* 4352 */     return array;
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
/*      */   public static double[] nullToEmpty(double[] array) {
/* 4369 */     if (isEmpty(array)) {
/* 4370 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 4372 */     return array;
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
/*      */   public static Double[] nullToEmpty(Double[] array) {
/* 4389 */     if (isEmpty((Object[])array)) {
/* 4390 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*      */     }
/* 4392 */     return array;
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
/*      */   public static float[] nullToEmpty(float[] array) {
/* 4409 */     if (isEmpty(array)) {
/* 4410 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 4412 */     return array;
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
/*      */   public static Float[] nullToEmpty(Float[] array) {
/* 4429 */     if (isEmpty((Object[])array)) {
/* 4430 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*      */     }
/* 4432 */     return array;
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
/*      */   public static int[] nullToEmpty(int[] array) {
/* 4449 */     if (isEmpty(array)) {
/* 4450 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 4452 */     return array;
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
/*      */   public static Integer[] nullToEmpty(Integer[] array) {
/* 4472 */     if (isEmpty((Object[])array)) {
/* 4473 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*      */     }
/* 4475 */     return array;
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
/*      */   public static long[] nullToEmpty(long[] array) {
/* 4492 */     if (isEmpty(array)) {
/* 4493 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 4495 */     return array;
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
/*      */   public static Long[] nullToEmpty(Long[] array) {
/* 4512 */     if (isEmpty((Object[])array)) {
/* 4513 */       return EMPTY_LONG_OBJECT_ARRAY;
/*      */     }
/* 4515 */     return array;
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
/*      */   public static Object[] nullToEmpty(Object[] array) {
/* 4532 */     if (isEmpty(array)) {
/* 4533 */       return EMPTY_OBJECT_ARRAY;
/*      */     }
/* 4535 */     return array;
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
/*      */   public static short[] nullToEmpty(short[] array) {
/* 4552 */     if (isEmpty(array)) {
/* 4553 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 4555 */     return array;
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
/*      */   public static Short[] nullToEmpty(Short[] array) {
/* 4572 */     if (isEmpty((Object[])array)) {
/* 4573 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*      */     }
/* 4575 */     return array;
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
/*      */   public static String[] nullToEmpty(String[] array) {
/* 4592 */     if (isEmpty((Object[])array)) {
/* 4593 */       return EMPTY_STRING_ARRAY;
/*      */     }
/* 4595 */     return array;
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
/*      */   public static <T> T[] nullToEmpty(T[] array, Class<T[]> type) {
/* 4614 */     if (type == null) {
/* 4615 */       throw new IllegalArgumentException("The type must not be null");
/*      */     }
/*      */     
/* 4618 */     if (array == null) {
/* 4619 */       return type.cast(Array.newInstance(type.getComponentType(), 0));
/*      */     }
/* 4621 */     return array;
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
/*      */   public static boolean[] remove(boolean[] array, int index) {
/* 4653 */     return (boolean[])remove(array, index);
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
/*      */   public static byte[] remove(byte[] array, int index) {
/* 4685 */     return (byte[])remove(array, index);
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
/*      */   public static char[] remove(char[] array, int index) {
/* 4717 */     return (char[])remove(array, index);
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
/*      */   public static double[] remove(double[] array, int index) {
/* 4749 */     return (double[])remove(array, index);
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
/*      */   public static float[] remove(float[] array, int index) {
/* 4781 */     return (float[])remove(array, index);
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
/*      */   public static int[] remove(int[] array, int index) {
/* 4813 */     return (int[])remove(array, index);
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
/*      */   public static long[] remove(long[] array, int index) {
/* 4845 */     return (long[])remove(array, index);
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
/*      */   private static Object remove(Object array, int index) {
/* 4870 */     int length = getLength(array);
/* 4871 */     if (index < 0 || index >= length) {
/* 4872 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/*      */     }
/*      */     
/* 4875 */     Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
/* 4876 */     System.arraycopy(array, 0, result, 0, index);
/* 4877 */     if (index < length - 1) {
/* 4878 */       System.arraycopy(array, index + 1, result, index, length - index - 1);
/*      */     }
/*      */     
/* 4881 */     return result;
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
/*      */   public static short[] remove(short[] array, int index) {
/* 4913 */     return (short[])remove(array, index);
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
/*      */   public static <T> T[] remove(T[] array, int index) {
/* 4947 */     return (T[])remove(array, index);
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
/*      */   public static boolean[] removeAll(boolean[] array, int... indices) {
/* 4976 */     return (boolean[])removeAll(array, indices);
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
/*      */   public static byte[] removeAll(byte[] array, int... indices) {
/* 5009 */     return (byte[])removeAll(array, indices);
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
/*      */   public static char[] removeAll(char[] array, int... indices) {
/* 5042 */     return (char[])removeAll(array, indices);
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
/*      */   public static double[] removeAll(double[] array, int... indices) {
/* 5075 */     return (double[])removeAll(array, indices);
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
/*      */   public static float[] removeAll(float[] array, int... indices) {
/* 5108 */     return (float[])removeAll(array, indices);
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
/*      */   public static int[] removeAll(int[] array, int... indices) {
/* 5141 */     return (int[])removeAll(array, indices);
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
/*      */   public static long[] removeAll(long[] array, int... indices) {
/* 5174 */     return (long[])removeAll(array, indices);
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
/*      */   static Object removeAll(Object array, BitSet indices) {
/* 5187 */     if (array == null) {
/* 5188 */       return null;
/*      */     }
/*      */     
/* 5191 */     int srcLength = getLength(array);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5198 */     int removals = indices.cardinality();
/* 5199 */     Object result = Array.newInstance(array.getClass().getComponentType(), srcLength - removals);
/* 5200 */     int srcIndex = 0;
/* 5201 */     int destIndex = 0;
/*      */     
/*      */     int set;
/* 5204 */     while ((set = indices.nextSetBit(srcIndex)) != -1) {
/* 5205 */       int i = set - srcIndex;
/* 5206 */       if (i > 0) {
/* 5207 */         System.arraycopy(array, srcIndex, result, destIndex, i);
/* 5208 */         destIndex += i;
/*      */       } 
/* 5210 */       srcIndex = indices.nextClearBit(set);
/*      */     } 
/* 5212 */     int count = srcLength - srcIndex;
/* 5213 */     if (count > 0) {
/* 5214 */       System.arraycopy(array, srcIndex, result, destIndex, count);
/*      */     }
/* 5216 */     return result;
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
/*      */   static Object removeAll(Object array, int... indices) {
/* 5228 */     int length = getLength(array);
/* 5229 */     int diff = 0;
/* 5230 */     int[] clonedIndices = clone(indices);
/* 5231 */     Arrays.sort(clonedIndices);
/*      */ 
/*      */     
/* 5234 */     if (isNotEmpty(clonedIndices)) {
/* 5235 */       int i = clonedIndices.length;
/* 5236 */       int prevIndex = length;
/* 5237 */       while (--i >= 0) {
/* 5238 */         int index = clonedIndices[i];
/* 5239 */         if (index < 0 || index >= length) {
/* 5240 */           throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/*      */         }
/* 5242 */         if (index >= prevIndex) {
/*      */           continue;
/*      */         }
/* 5245 */         diff++;
/* 5246 */         prevIndex = index;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5251 */     Object result = Array.newInstance(array.getClass().getComponentType(), length - diff);
/* 5252 */     if (diff < length) {
/* 5253 */       int end = length;
/* 5254 */       int dest = length - diff;
/* 5255 */       for (int i = clonedIndices.length - 1; i >= 0; i--) {
/* 5256 */         int index = clonedIndices[i];
/* 5257 */         if (end - index > 1) {
/* 5258 */           int cp = end - index - 1;
/* 5259 */           dest -= cp;
/* 5260 */           System.arraycopy(array, index + 1, result, dest, cp);
/*      */         } 
/*      */         
/* 5263 */         end = index;
/*      */       } 
/* 5265 */       if (end > 0) {
/* 5266 */         System.arraycopy(array, 0, result, 0, end);
/*      */       }
/*      */     } 
/* 5269 */     return result;
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
/*      */   public static short[] removeAll(short[] array, int... indices) {
/* 5302 */     return (short[])removeAll(array, indices);
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
/*      */   public static <T> T[] removeAll(T[] array, int... indices) {
/* 5333 */     return (T[])removeAll(array, indices);
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
/*      */   @Deprecated
/*      */   public static boolean[] removeAllOccurences(boolean[] array, boolean element) {
/* 5354 */     return (boolean[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static byte[] removeAllOccurences(byte[] array, byte element) {
/* 5375 */     return (byte[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static char[] removeAllOccurences(char[] array, char element) {
/* 5396 */     return (char[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static double[] removeAllOccurences(double[] array, double element) {
/* 5417 */     return (double[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static float[] removeAllOccurences(float[] array, float element) {
/* 5438 */     return (float[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static int[] removeAllOccurences(int[] array, int element) {
/* 5459 */     return (int[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static long[] removeAllOccurences(long[] array, long element) {
/* 5480 */     return (long[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static short[] removeAllOccurences(short[] array, short element) {
/* 5501 */     return (short[])removeAll(array, indexesOf(array, element));
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
/*      */   @Deprecated
/*      */   public static <T> T[] removeAllOccurences(T[] array, T element) {
/* 5523 */     return (T[])removeAll(array, indexesOf((Object[])array, element));
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
/*      */   public static boolean[] removeAllOccurrences(boolean[] array, boolean element) {
/* 5542 */     return (boolean[])removeAll(array, indexesOf(array, element));
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
/*      */   public static byte[] removeAllOccurrences(byte[] array, byte element) {
/* 5561 */     return (byte[])removeAll(array, indexesOf(array, element));
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
/*      */   public static char[] removeAllOccurrences(char[] array, char element) {
/* 5580 */     return (char[])removeAll(array, indexesOf(array, element));
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
/*      */   public static double[] removeAllOccurrences(double[] array, double element) {
/* 5599 */     return (double[])removeAll(array, indexesOf(array, element));
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
/*      */   public static float[] removeAllOccurrences(float[] array, float element) {
/* 5618 */     return (float[])removeAll(array, indexesOf(array, element));
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
/*      */   public static int[] removeAllOccurrences(int[] array, int element) {
/* 5637 */     return (int[])removeAll(array, indexesOf(array, element));
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
/*      */   public static long[] removeAllOccurrences(long[] array, long element) {
/* 5656 */     return (long[])removeAll(array, indexesOf(array, element));
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
/*      */   public static short[] removeAllOccurrences(short[] array, short element) {
/* 5675 */     return (short[])removeAll(array, indexesOf(array, element));
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
/*      */   public static <T> T[] removeAllOccurrences(T[] array, T element) {
/* 5695 */     return (T[])removeAll(array, indexesOf((Object[])array, element));
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
/*      */   public static boolean[] removeElement(boolean[] array, boolean element) {
/* 5724 */     int index = indexOf(array, element);
/* 5725 */     if (index == -1) {
/* 5726 */       return clone(array);
/*      */     }
/* 5728 */     return remove(array, index);
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
/*      */   public static byte[] removeElement(byte[] array, byte element) {
/* 5757 */     int index = indexOf(array, element);
/* 5758 */     if (index == -1) {
/* 5759 */       return clone(array);
/*      */     }
/* 5761 */     return remove(array, index);
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
/*      */   public static char[] removeElement(char[] array, char element) {
/* 5790 */     int index = indexOf(array, element);
/* 5791 */     if (index == -1) {
/* 5792 */       return clone(array);
/*      */     }
/* 5794 */     return remove(array, index);
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
/*      */   public static double[] removeElement(double[] array, double element) {
/* 5823 */     int index = indexOf(array, element);
/* 5824 */     if (index == -1) {
/* 5825 */       return clone(array);
/*      */     }
/* 5827 */     return remove(array, index);
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
/*      */   public static float[] removeElement(float[] array, float element) {
/* 5856 */     int index = indexOf(array, element);
/* 5857 */     if (index == -1) {
/* 5858 */       return clone(array);
/*      */     }
/* 5860 */     return remove(array, index);
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
/*      */   public static int[] removeElement(int[] array, int element) {
/* 5889 */     int index = indexOf(array, element);
/* 5890 */     if (index == -1) {
/* 5891 */       return clone(array);
/*      */     }
/* 5893 */     return remove(array, index);
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
/*      */   public static long[] removeElement(long[] array, long element) {
/* 5922 */     int index = indexOf(array, element);
/* 5923 */     if (index == -1) {
/* 5924 */       return clone(array);
/*      */     }
/* 5926 */     return remove(array, index);
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
/*      */   public static short[] removeElement(short[] array, short element) {
/* 5955 */     int index = indexOf(array, element);
/* 5956 */     if (index == -1) {
/* 5957 */       return clone(array);
/*      */     }
/* 5959 */     return remove(array, index);
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
/*      */   public static <T> T[] removeElement(T[] array, Object element) {
/* 5989 */     int index = indexOf((Object[])array, element);
/* 5990 */     if (index == -1) {
/* 5991 */       return clone(array);
/*      */     }
/* 5993 */     return remove(array, index);
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
/*      */   public static boolean[] removeElements(boolean[] array, boolean... values) {
/* 6024 */     if (isEmpty(array) || isEmpty(values)) {
/* 6025 */       return clone(array);
/*      */     }
/* 6027 */     HashMap<Boolean, MutableInt> occurrences = new HashMap<>(2);
/* 6028 */     for (boolean v : values) {
/* 6029 */       Boolean boxed = Boolean.valueOf(v);
/* 6030 */       MutableInt count = occurrences.get(boxed);
/* 6031 */       if (count == null) {
/* 6032 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6034 */         count.increment();
/*      */       } 
/*      */     } 
/* 6037 */     BitSet toRemove = new BitSet();
/* 6038 */     for (int i = 0; i < array.length; i++) {
/* 6039 */       boolean key = array[i];
/* 6040 */       MutableInt count = occurrences.get(Boolean.valueOf(key));
/* 6041 */       if (count != null) {
/* 6042 */         if (count.decrementAndGet() == 0) {
/* 6043 */           occurrences.remove(Boolean.valueOf(key));
/*      */         }
/* 6045 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6048 */     return (boolean[])removeAll(array, toRemove);
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
/*      */   public static byte[] removeElements(byte[] array, byte... values) {
/* 6079 */     if (isEmpty(array) || isEmpty(values)) {
/* 6080 */       return clone(array);
/*      */     }
/* 6082 */     Map<Byte, MutableInt> occurrences = new HashMap<>(values.length);
/* 6083 */     for (byte v : values) {
/* 6084 */       Byte boxed = Byte.valueOf(v);
/* 6085 */       MutableInt count = occurrences.get(boxed);
/* 6086 */       if (count == null) {
/* 6087 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6089 */         count.increment();
/*      */       } 
/*      */     } 
/* 6092 */     BitSet toRemove = new BitSet();
/* 6093 */     for (int i = 0; i < array.length; i++) {
/* 6094 */       byte key = array[i];
/* 6095 */       MutableInt count = occurrences.get(Byte.valueOf(key));
/* 6096 */       if (count != null) {
/* 6097 */         if (count.decrementAndGet() == 0) {
/* 6098 */           occurrences.remove(Byte.valueOf(key));
/*      */         }
/* 6100 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6103 */     return (byte[])removeAll(array, toRemove);
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
/*      */   public static char[] removeElements(char[] array, char... values) {
/* 6134 */     if (isEmpty(array) || isEmpty(values)) {
/* 6135 */       return clone(array);
/*      */     }
/* 6137 */     HashMap<Character, MutableInt> occurrences = new HashMap<>(values.length);
/* 6138 */     for (char v : values) {
/* 6139 */       Character boxed = Character.valueOf(v);
/* 6140 */       MutableInt count = occurrences.get(boxed);
/* 6141 */       if (count == null) {
/* 6142 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6144 */         count.increment();
/*      */       } 
/*      */     } 
/* 6147 */     BitSet toRemove = new BitSet();
/* 6148 */     for (int i = 0; i < array.length; i++) {
/* 6149 */       char key = array[i];
/* 6150 */       MutableInt count = occurrences.get(Character.valueOf(key));
/* 6151 */       if (count != null) {
/* 6152 */         if (count.decrementAndGet() == 0) {
/* 6153 */           occurrences.remove(Character.valueOf(key));
/*      */         }
/* 6155 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6158 */     return (char[])removeAll(array, toRemove);
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
/*      */   public static double[] removeElements(double[] array, double... values) {
/* 6189 */     if (isEmpty(array) || isEmpty(values)) {
/* 6190 */       return clone(array);
/*      */     }
/* 6192 */     HashMap<Double, MutableInt> occurrences = new HashMap<>(values.length);
/* 6193 */     for (double v : values) {
/* 6194 */       Double boxed = Double.valueOf(v);
/* 6195 */       MutableInt count = occurrences.get(boxed);
/* 6196 */       if (count == null) {
/* 6197 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6199 */         count.increment();
/*      */       } 
/*      */     } 
/* 6202 */     BitSet toRemove = new BitSet();
/* 6203 */     for (int i = 0; i < array.length; i++) {
/* 6204 */       double key = array[i];
/* 6205 */       MutableInt count = occurrences.get(Double.valueOf(key));
/* 6206 */       if (count != null) {
/* 6207 */         if (count.decrementAndGet() == 0) {
/* 6208 */           occurrences.remove(Double.valueOf(key));
/*      */         }
/* 6210 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6213 */     return (double[])removeAll(array, toRemove);
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
/*      */   public static float[] removeElements(float[] array, float... values) {
/* 6244 */     if (isEmpty(array) || isEmpty(values)) {
/* 6245 */       return clone(array);
/*      */     }
/* 6247 */     HashMap<Float, MutableInt> occurrences = new HashMap<>(values.length);
/* 6248 */     for (float v : values) {
/* 6249 */       Float boxed = Float.valueOf(v);
/* 6250 */       MutableInt count = occurrences.get(boxed);
/* 6251 */       if (count == null) {
/* 6252 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6254 */         count.increment();
/*      */       } 
/*      */     } 
/* 6257 */     BitSet toRemove = new BitSet();
/* 6258 */     for (int i = 0; i < array.length; i++) {
/* 6259 */       float key = array[i];
/* 6260 */       MutableInt count = occurrences.get(Float.valueOf(key));
/* 6261 */       if (count != null) {
/* 6262 */         if (count.decrementAndGet() == 0) {
/* 6263 */           occurrences.remove(Float.valueOf(key));
/*      */         }
/* 6265 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6268 */     return (float[])removeAll(array, toRemove);
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
/*      */   public static int[] removeElements(int[] array, int... values) {
/* 6299 */     if (isEmpty(array) || isEmpty(values)) {
/* 6300 */       return clone(array);
/*      */     }
/* 6302 */     HashMap<Integer, MutableInt> occurrences = new HashMap<>(values.length);
/* 6303 */     for (int v : values) {
/* 6304 */       Integer boxed = Integer.valueOf(v);
/* 6305 */       MutableInt count = occurrences.get(boxed);
/* 6306 */       if (count == null) {
/* 6307 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6309 */         count.increment();
/*      */       } 
/*      */     } 
/* 6312 */     BitSet toRemove = new BitSet();
/* 6313 */     for (int i = 0; i < array.length; i++) {
/* 6314 */       int key = array[i];
/* 6315 */       MutableInt count = occurrences.get(Integer.valueOf(key));
/* 6316 */       if (count != null) {
/* 6317 */         if (count.decrementAndGet() == 0) {
/* 6318 */           occurrences.remove(Integer.valueOf(key));
/*      */         }
/* 6320 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6323 */     return (int[])removeAll(array, toRemove);
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
/*      */   public static long[] removeElements(long[] array, long... values) {
/* 6354 */     if (isEmpty(array) || isEmpty(values)) {
/* 6355 */       return clone(array);
/*      */     }
/* 6357 */     HashMap<Long, MutableInt> occurrences = new HashMap<>(values.length);
/* 6358 */     for (long v : values) {
/* 6359 */       Long boxed = Long.valueOf(v);
/* 6360 */       MutableInt count = occurrences.get(boxed);
/* 6361 */       if (count == null) {
/* 6362 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6364 */         count.increment();
/*      */       } 
/*      */     } 
/* 6367 */     BitSet toRemove = new BitSet();
/* 6368 */     for (int i = 0; i < array.length; i++) {
/* 6369 */       long key = array[i];
/* 6370 */       MutableInt count = occurrences.get(Long.valueOf(key));
/* 6371 */       if (count != null) {
/* 6372 */         if (count.decrementAndGet() == 0) {
/* 6373 */           occurrences.remove(Long.valueOf(key));
/*      */         }
/* 6375 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6378 */     return (long[])removeAll(array, toRemove);
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
/*      */   public static short[] removeElements(short[] array, short... values) {
/* 6409 */     if (isEmpty(array) || isEmpty(values)) {
/* 6410 */       return clone(array);
/*      */     }
/* 6412 */     HashMap<Short, MutableInt> occurrences = new HashMap<>(values.length);
/* 6413 */     for (short v : values) {
/* 6414 */       Short boxed = Short.valueOf(v);
/* 6415 */       MutableInt count = occurrences.get(boxed);
/* 6416 */       if (count == null) {
/* 6417 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 6419 */         count.increment();
/*      */       } 
/*      */     } 
/* 6422 */     BitSet toRemove = new BitSet();
/* 6423 */     for (int i = 0; i < array.length; i++) {
/* 6424 */       short key = array[i];
/* 6425 */       MutableInt count = occurrences.get(Short.valueOf(key));
/* 6426 */       if (count != null) {
/* 6427 */         if (count.decrementAndGet() == 0) {
/* 6428 */           occurrences.remove(Short.valueOf(key));
/*      */         }
/* 6430 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/* 6433 */     return (short[])removeAll(array, toRemove);
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
/*      */   @SafeVarargs
/*      */   public static <T> T[] removeElements(T[] array, T... values) {
/* 6466 */     if (isEmpty((Object[])array) || isEmpty((Object[])values)) {
/* 6467 */       return clone(array);
/*      */     }
/* 6469 */     HashMap<T, MutableInt> occurrences = new HashMap<>(values.length);
/* 6470 */     for (T v : values) {
/* 6471 */       MutableInt count = occurrences.get(v);
/* 6472 */       if (count == null) {
/* 6473 */         occurrences.put(v, new MutableInt(1));
/*      */       } else {
/* 6475 */         count.increment();
/*      */       } 
/*      */     } 
/* 6478 */     BitSet toRemove = new BitSet();
/* 6479 */     for (int i = 0; i < array.length; i++) {
/* 6480 */       T key = array[i];
/* 6481 */       MutableInt count = occurrences.get(key);
/* 6482 */       if (count != null) {
/* 6483 */         if (count.decrementAndGet() == 0) {
/* 6484 */           occurrences.remove(key);
/*      */         }
/* 6486 */         toRemove.set(i);
/*      */       } 
/*      */     } 
/*      */     
/* 6490 */     T[] result = (T[])removeAll(array, toRemove);
/* 6491 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(boolean[] array) {
/* 6502 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6505 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6526 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6529 */     int i = Math.max(startIndexInclusive, 0);
/* 6530 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6532 */     while (j > i) {
/* 6533 */       boolean tmp = array[j];
/* 6534 */       array[j] = array[i];
/* 6535 */       array[i] = tmp;
/* 6536 */       j--;
/* 6537 */       i++;
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
/*      */   public static void reverse(byte[] array) {
/* 6549 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6552 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(byte[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6573 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6576 */     int i = Math.max(startIndexInclusive, 0);
/* 6577 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6579 */     while (j > i) {
/* 6580 */       byte tmp = array[j];
/* 6581 */       array[j] = array[i];
/* 6582 */       array[i] = tmp;
/* 6583 */       j--;
/* 6584 */       i++;
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
/*      */   public static void reverse(char[] array) {
/* 6596 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6599 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(char[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6620 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6623 */     int i = Math.max(startIndexInclusive, 0);
/* 6624 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6626 */     while (j > i) {
/* 6627 */       char tmp = array[j];
/* 6628 */       array[j] = array[i];
/* 6629 */       array[i] = tmp;
/* 6630 */       j--;
/* 6631 */       i++;
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
/*      */   public static void reverse(double[] array) {
/* 6643 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6646 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(double[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6667 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6670 */     int i = Math.max(startIndexInclusive, 0);
/* 6671 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6673 */     while (j > i) {
/* 6674 */       double tmp = array[j];
/* 6675 */       array[j] = array[i];
/* 6676 */       array[i] = tmp;
/* 6677 */       j--;
/* 6678 */       i++;
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
/*      */   public static void reverse(float[] array) {
/* 6690 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6693 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(float[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6714 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6717 */     int i = Math.max(startIndexInclusive, 0);
/* 6718 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6720 */     while (j > i) {
/* 6721 */       float tmp = array[j];
/* 6722 */       array[j] = array[i];
/* 6723 */       array[i] = tmp;
/* 6724 */       j--;
/* 6725 */       i++;
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
/*      */   public static void reverse(int[] array) {
/* 6737 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6740 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(int[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6761 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6764 */     int i = Math.max(startIndexInclusive, 0);
/* 6765 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6767 */     while (j > i) {
/* 6768 */       int tmp = array[j];
/* 6769 */       array[j] = array[i];
/* 6770 */       array[i] = tmp;
/* 6771 */       j--;
/* 6772 */       i++;
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
/*      */   public static void reverse(long[] array) {
/* 6784 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6787 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(long[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6808 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6811 */     int i = Math.max(startIndexInclusive, 0);
/* 6812 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6814 */     while (j > i) {
/* 6815 */       long tmp = array[j];
/* 6816 */       array[j] = array[i];
/* 6817 */       array[i] = tmp;
/* 6818 */       j--;
/* 6819 */       i++;
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
/*      */   public static void reverse(Object[] array) {
/* 6835 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6838 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(Object[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6859 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6862 */     int i = Math.max(startIndexInclusive, 0);
/* 6863 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6865 */     while (j > i) {
/* 6866 */       Object tmp = array[j];
/* 6867 */       array[j] = array[i];
/* 6868 */       array[i] = tmp;
/* 6869 */       j--;
/* 6870 */       i++;
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
/*      */   public static void reverse(short[] array) {
/* 6882 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6885 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(short[] array, int startIndexInclusive, int endIndexExclusive) {
/* 6906 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6909 */     int i = Math.max(startIndexInclusive, 0);
/* 6910 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 6912 */     while (j > i) {
/* 6913 */       short tmp = array[j];
/* 6914 */       array[j] = array[i];
/* 6915 */       array[i] = tmp;
/* 6916 */       j--;
/* 6917 */       i++;
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
/*      */   public static void shift(boolean[] array, int offset) {
/* 6934 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6937 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(boolean[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 6960 */     if (array == null) {
/*      */       return;
/*      */     }
/* 6963 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 6966 */     if (startIndexInclusive < 0) {
/* 6967 */       startIndexInclusive = 0;
/*      */     }
/* 6969 */     if (endIndexExclusive >= array.length) {
/* 6970 */       endIndexExclusive = array.length;
/*      */     }
/* 6972 */     int n = endIndexExclusive - startIndexInclusive;
/* 6973 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 6976 */     offset %= n;
/* 6977 */     if (offset < 0) {
/* 6978 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 6982 */     while (n > 1 && offset > 0) {
/* 6983 */       int n_offset = n - offset;
/*      */       
/* 6985 */       if (offset > n_offset) {
/* 6986 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 6987 */         n = offset;
/* 6988 */         offset -= n_offset; continue;
/* 6989 */       }  if (offset < n_offset) {
/* 6990 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 6991 */         startIndexInclusive += offset;
/* 6992 */         n = n_offset; continue;
/*      */       } 
/* 6994 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(byte[] array, int offset) {
/* 7013 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7016 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(byte[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7039 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7042 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7045 */     if (startIndexInclusive < 0) {
/* 7046 */       startIndexInclusive = 0;
/*      */     }
/* 7048 */     if (endIndexExclusive >= array.length) {
/* 7049 */       endIndexExclusive = array.length;
/*      */     }
/* 7051 */     int n = endIndexExclusive - startIndexInclusive;
/* 7052 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7055 */     offset %= n;
/* 7056 */     if (offset < 0) {
/* 7057 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7061 */     while (n > 1 && offset > 0) {
/* 7062 */       int n_offset = n - offset;
/*      */       
/* 7064 */       if (offset > n_offset) {
/* 7065 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7066 */         n = offset;
/* 7067 */         offset -= n_offset; continue;
/* 7068 */       }  if (offset < n_offset) {
/* 7069 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7070 */         startIndexInclusive += offset;
/* 7071 */         n = n_offset; continue;
/*      */       } 
/* 7073 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(char[] array, int offset) {
/* 7092 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7095 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(char[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7118 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7121 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7124 */     if (startIndexInclusive < 0) {
/* 7125 */       startIndexInclusive = 0;
/*      */     }
/* 7127 */     if (endIndexExclusive >= array.length) {
/* 7128 */       endIndexExclusive = array.length;
/*      */     }
/* 7130 */     int n = endIndexExclusive - startIndexInclusive;
/* 7131 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7134 */     offset %= n;
/* 7135 */     if (offset < 0) {
/* 7136 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7140 */     while (n > 1 && offset > 0) {
/* 7141 */       int n_offset = n - offset;
/*      */       
/* 7143 */       if (offset > n_offset) {
/* 7144 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7145 */         n = offset;
/* 7146 */         offset -= n_offset; continue;
/* 7147 */       }  if (offset < n_offset) {
/* 7148 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7149 */         startIndexInclusive += offset;
/* 7150 */         n = n_offset; continue;
/*      */       } 
/* 7152 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(double[] array, int offset) {
/* 7171 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7174 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(double[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7197 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7200 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7203 */     if (startIndexInclusive < 0) {
/* 7204 */       startIndexInclusive = 0;
/*      */     }
/* 7206 */     if (endIndexExclusive >= array.length) {
/* 7207 */       endIndexExclusive = array.length;
/*      */     }
/* 7209 */     int n = endIndexExclusive - startIndexInclusive;
/* 7210 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7213 */     offset %= n;
/* 7214 */     if (offset < 0) {
/* 7215 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7219 */     while (n > 1 && offset > 0) {
/* 7220 */       int n_offset = n - offset;
/*      */       
/* 7222 */       if (offset > n_offset) {
/* 7223 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7224 */         n = offset;
/* 7225 */         offset -= n_offset; continue;
/* 7226 */       }  if (offset < n_offset) {
/* 7227 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7228 */         startIndexInclusive += offset;
/* 7229 */         n = n_offset; continue;
/*      */       } 
/* 7231 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(float[] array, int offset) {
/* 7250 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7253 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(float[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7276 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7279 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7282 */     if (startIndexInclusive < 0) {
/* 7283 */       startIndexInclusive = 0;
/*      */     }
/* 7285 */     if (endIndexExclusive >= array.length) {
/* 7286 */       endIndexExclusive = array.length;
/*      */     }
/* 7288 */     int n = endIndexExclusive - startIndexInclusive;
/* 7289 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7292 */     offset %= n;
/* 7293 */     if (offset < 0) {
/* 7294 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7298 */     while (n > 1 && offset > 0) {
/* 7299 */       int n_offset = n - offset;
/*      */       
/* 7301 */       if (offset > n_offset) {
/* 7302 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7303 */         n = offset;
/* 7304 */         offset -= n_offset; continue;
/* 7305 */       }  if (offset < n_offset) {
/* 7306 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7307 */         startIndexInclusive += offset;
/* 7308 */         n = n_offset; continue;
/*      */       } 
/* 7310 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(int[] array, int offset) {
/* 7329 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7332 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(int[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7355 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7358 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7361 */     if (startIndexInclusive < 0) {
/* 7362 */       startIndexInclusive = 0;
/*      */     }
/* 7364 */     if (endIndexExclusive >= array.length) {
/* 7365 */       endIndexExclusive = array.length;
/*      */     }
/* 7367 */     int n = endIndexExclusive - startIndexInclusive;
/* 7368 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7371 */     offset %= n;
/* 7372 */     if (offset < 0) {
/* 7373 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7377 */     while (n > 1 && offset > 0) {
/* 7378 */       int n_offset = n - offset;
/*      */       
/* 7380 */       if (offset > n_offset) {
/* 7381 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7382 */         n = offset;
/* 7383 */         offset -= n_offset; continue;
/* 7384 */       }  if (offset < n_offset) {
/* 7385 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7386 */         startIndexInclusive += offset;
/* 7387 */         n = n_offset; continue;
/*      */       } 
/* 7389 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(long[] array, int offset) {
/* 7408 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7411 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(long[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7434 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7437 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7440 */     if (startIndexInclusive < 0) {
/* 7441 */       startIndexInclusive = 0;
/*      */     }
/* 7443 */     if (endIndexExclusive >= array.length) {
/* 7444 */       endIndexExclusive = array.length;
/*      */     }
/* 7446 */     int n = endIndexExclusive - startIndexInclusive;
/* 7447 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7450 */     offset %= n;
/* 7451 */     if (offset < 0) {
/* 7452 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7456 */     while (n > 1 && offset > 0) {
/* 7457 */       int n_offset = n - offset;
/*      */       
/* 7459 */       if (offset > n_offset) {
/* 7460 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7461 */         n = offset;
/* 7462 */         offset -= n_offset; continue;
/* 7463 */       }  if (offset < n_offset) {
/* 7464 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7465 */         startIndexInclusive += offset;
/* 7466 */         n = n_offset; continue;
/*      */       } 
/* 7468 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(Object[] array, int offset) {
/* 7489 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7492 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(Object[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7515 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7518 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7521 */     if (startIndexInclusive < 0) {
/* 7522 */       startIndexInclusive = 0;
/*      */     }
/* 7524 */     if (endIndexExclusive >= array.length) {
/* 7525 */       endIndexExclusive = array.length;
/*      */     }
/* 7527 */     int n = endIndexExclusive - startIndexInclusive;
/* 7528 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7531 */     offset %= n;
/* 7532 */     if (offset < 0) {
/* 7533 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7537 */     while (n > 1 && offset > 0) {
/* 7538 */       int n_offset = n - offset;
/*      */       
/* 7540 */       if (offset > n_offset) {
/* 7541 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7542 */         n = offset;
/* 7543 */         offset -= n_offset; continue;
/* 7544 */       }  if (offset < n_offset) {
/* 7545 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7546 */         startIndexInclusive += offset;
/* 7547 */         n = n_offset; continue;
/*      */       } 
/* 7549 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shift(short[] array, int offset) {
/* 7568 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7571 */     shift(array, 0, array.length, offset);
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
/*      */   public static void shift(short[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
/* 7594 */     if (array == null) {
/*      */       return;
/*      */     }
/* 7597 */     if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
/*      */       return;
/*      */     }
/* 7600 */     if (startIndexInclusive < 0) {
/* 7601 */       startIndexInclusive = 0;
/*      */     }
/* 7603 */     if (endIndexExclusive >= array.length) {
/* 7604 */       endIndexExclusive = array.length;
/*      */     }
/* 7606 */     int n = endIndexExclusive - startIndexInclusive;
/* 7607 */     if (n <= 1) {
/*      */       return;
/*      */     }
/* 7610 */     offset %= n;
/* 7611 */     if (offset < 0) {
/* 7612 */       offset += n;
/*      */     }
/*      */ 
/*      */     
/* 7616 */     while (n > 1 && offset > 0) {
/* 7617 */       int n_offset = n - offset;
/*      */       
/* 7619 */       if (offset > n_offset) {
/* 7620 */         swap(array, startIndexInclusive, startIndexInclusive + n - n_offset, n_offset);
/* 7621 */         n = offset;
/* 7622 */         offset -= n_offset; continue;
/* 7623 */       }  if (offset < n_offset) {
/* 7624 */         swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
/* 7625 */         startIndexInclusive += offset;
/* 7626 */         n = n_offset; continue;
/*      */       } 
/* 7628 */       swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
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
/*      */   public static void shuffle(boolean[] array) {
/* 7642 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(boolean[] array, Random random) {
/* 7654 */     for (int i = array.length; i > 1; i--) {
/* 7655 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(byte[] array) {
/* 7667 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(byte[] array, Random random) {
/* 7679 */     for (int i = array.length; i > 1; i--) {
/* 7680 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(char[] array) {
/* 7692 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(char[] array, Random random) {
/* 7704 */     for (int i = array.length; i > 1; i--) {
/* 7705 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(double[] array) {
/* 7717 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(double[] array, Random random) {
/* 7729 */     for (int i = array.length; i > 1; i--) {
/* 7730 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(float[] array) {
/* 7742 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(float[] array, Random random) {
/* 7754 */     for (int i = array.length; i > 1; i--) {
/* 7755 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(int[] array) {
/* 7767 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(int[] array, Random random) {
/* 7779 */     for (int i = array.length; i > 1; i--) {
/* 7780 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(long[] array) {
/* 7792 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(long[] array, Random random) {
/* 7804 */     for (int i = array.length; i > 1; i--) {
/* 7805 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(Object[] array) {
/* 7817 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(Object[] array, Random random) {
/* 7829 */     for (int i = array.length; i > 1; i--) {
/* 7830 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */   public static void shuffle(short[] array) {
/* 7842 */     shuffle(array, new Random());
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
/*      */   public static void shuffle(short[] array, Random random) {
/* 7854 */     for (int i = array.length; i > 1; i--) {
/* 7855 */       swap(array, i - 1, random.nextInt(i), 1);
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
/*      */ 
/*      */   
/*      */   public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
/* 7880 */     if (array == null) {
/* 7881 */       return null;
/*      */     }
/* 7883 */     if (startIndexInclusive < 0) {
/* 7884 */       startIndexInclusive = 0;
/*      */     }
/* 7886 */     if (endIndexExclusive > array.length) {
/* 7887 */       endIndexExclusive = array.length;
/*      */     }
/* 7889 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 7890 */     if (newSize <= 0) {
/* 7891 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/*      */     
/* 7894 */     boolean[] subarray = new boolean[newSize];
/* 7895 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 7896 */     return subarray;
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
/*      */   public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
/* 7920 */     if (array == null) {
/* 7921 */       return null;
/*      */     }
/* 7923 */     if (startIndexInclusive < 0) {
/* 7924 */       startIndexInclusive = 0;
/*      */     }
/* 7926 */     if (endIndexExclusive > array.length) {
/* 7927 */       endIndexExclusive = array.length;
/*      */     }
/* 7929 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 7930 */     if (newSize <= 0) {
/* 7931 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/*      */     
/* 7934 */     byte[] subarray = new byte[newSize];
/* 7935 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 7936 */     return subarray;
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
/*      */   public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
/* 7960 */     if (array == null) {
/* 7961 */       return null;
/*      */     }
/* 7963 */     if (startIndexInclusive < 0) {
/* 7964 */       startIndexInclusive = 0;
/*      */     }
/* 7966 */     if (endIndexExclusive > array.length) {
/* 7967 */       endIndexExclusive = array.length;
/*      */     }
/* 7969 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 7970 */     if (newSize <= 0) {
/* 7971 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/*      */     
/* 7974 */     char[] subarray = new char[newSize];
/* 7975 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 7976 */     return subarray;
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
/*      */   public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
/* 8000 */     if (array == null) {
/* 8001 */       return null;
/*      */     }
/* 8003 */     if (startIndexInclusive < 0) {
/* 8004 */       startIndexInclusive = 0;
/*      */     }
/* 8006 */     if (endIndexExclusive > array.length) {
/* 8007 */       endIndexExclusive = array.length;
/*      */     }
/* 8009 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 8010 */     if (newSize <= 0) {
/* 8011 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/*      */     
/* 8014 */     double[] subarray = new double[newSize];
/* 8015 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 8016 */     return subarray;
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
/*      */   public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
/* 8040 */     if (array == null) {
/* 8041 */       return null;
/*      */     }
/* 8043 */     if (startIndexInclusive < 0) {
/* 8044 */       startIndexInclusive = 0;
/*      */     }
/* 8046 */     if (endIndexExclusive > array.length) {
/* 8047 */       endIndexExclusive = array.length;
/*      */     }
/* 8049 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 8050 */     if (newSize <= 0) {
/* 8051 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/*      */     
/* 8054 */     float[] subarray = new float[newSize];
/* 8055 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 8056 */     return subarray;
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
/*      */   public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
/* 8080 */     if (array == null) {
/* 8081 */       return null;
/*      */     }
/* 8083 */     if (startIndexInclusive < 0) {
/* 8084 */       startIndexInclusive = 0;
/*      */     }
/* 8086 */     if (endIndexExclusive > array.length) {
/* 8087 */       endIndexExclusive = array.length;
/*      */     }
/* 8089 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 8090 */     if (newSize <= 0) {
/* 8091 */       return EMPTY_INT_ARRAY;
/*      */     }
/*      */     
/* 8094 */     int[] subarray = new int[newSize];
/* 8095 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 8096 */     return subarray;
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
/*      */   public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
/* 8120 */     if (array == null) {
/* 8121 */       return null;
/*      */     }
/* 8123 */     if (startIndexInclusive < 0) {
/* 8124 */       startIndexInclusive = 0;
/*      */     }
/* 8126 */     if (endIndexExclusive > array.length) {
/* 8127 */       endIndexExclusive = array.length;
/*      */     }
/* 8129 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 8130 */     if (newSize <= 0) {
/* 8131 */       return EMPTY_LONG_ARRAY;
/*      */     }
/*      */     
/* 8134 */     long[] subarray = new long[newSize];
/* 8135 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 8136 */     return subarray;
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
/*      */   public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
/* 8160 */     if (array == null) {
/* 8161 */       return null;
/*      */     }
/* 8163 */     if (startIndexInclusive < 0) {
/* 8164 */       startIndexInclusive = 0;
/*      */     }
/* 8166 */     if (endIndexExclusive > array.length) {
/* 8167 */       endIndexExclusive = array.length;
/*      */     }
/* 8169 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 8170 */     if (newSize <= 0) {
/* 8171 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/*      */     
/* 8174 */     short[] subarray = new short[newSize];
/* 8175 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 8176 */     return subarray;
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
/*      */   public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
/* 8211 */     if (array == null) {
/* 8212 */       return null;
/*      */     }
/* 8214 */     if (startIndexInclusive < 0) {
/* 8215 */       startIndexInclusive = 0;
/*      */     }
/* 8217 */     if (endIndexExclusive > array.length) {
/* 8218 */       endIndexExclusive = array.length;
/*      */     }
/* 8220 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 8221 */     Class<?> type = array.getClass().getComponentType();
/* 8222 */     if (newSize <= 0) {
/*      */       
/* 8224 */       T[] emptyArray = (T[])Array.newInstance(type, 0);
/* 8225 */       return emptyArray;
/*      */     } 
/*      */ 
/*      */     
/* 8229 */     T[] subarray = (T[])Array.newInstance(type, newSize);
/* 8230 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 8231 */     return subarray;
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
/*      */   public static void swap(boolean[] array, int offset1, int offset2) {
/* 8256 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8259 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(boolean[] array, int offset1, int offset2, int len) {
/* 8287 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8290 */     if (offset1 < 0) {
/* 8291 */       offset1 = 0;
/*      */     }
/* 8293 */     if (offset2 < 0) {
/* 8294 */       offset2 = 0;
/*      */     }
/* 8296 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8297 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8298 */       boolean aux = array[offset1];
/* 8299 */       array[offset1] = array[offset2];
/* 8300 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(byte[] array, int offset1, int offset2) {
/* 8327 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8330 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(byte[] array, int offset1, int offset2, int len) {
/* 8358 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8361 */     if (offset1 < 0) {
/* 8362 */       offset1 = 0;
/*      */     }
/* 8364 */     if (offset2 < 0) {
/* 8365 */       offset2 = 0;
/*      */     }
/* 8367 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8368 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8369 */       byte aux = array[offset1];
/* 8370 */       array[offset1] = array[offset2];
/* 8371 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(char[] array, int offset1, int offset2) {
/* 8397 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8400 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(char[] array, int offset1, int offset2, int len) {
/* 8428 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8431 */     if (offset1 < 0) {
/* 8432 */       offset1 = 0;
/*      */     }
/* 8434 */     if (offset2 < 0) {
/* 8435 */       offset2 = 0;
/*      */     }
/* 8437 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8438 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8439 */       char aux = array[offset1];
/* 8440 */       array[offset1] = array[offset2];
/* 8441 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[] array, int offset1, int offset2) {
/* 8467 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8470 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(double[] array, int offset1, int offset2, int len) {
/* 8498 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8501 */     if (offset1 < 0) {
/* 8502 */       offset1 = 0;
/*      */     }
/* 8504 */     if (offset2 < 0) {
/* 8505 */       offset2 = 0;
/*      */     }
/* 8507 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8508 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8509 */       double aux = array[offset1];
/* 8510 */       array[offset1] = array[offset2];
/* 8511 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(float[] array, int offset1, int offset2) {
/* 8537 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8540 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(float[] array, int offset1, int offset2, int len) {
/* 8568 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8571 */     if (offset1 < 0) {
/* 8572 */       offset1 = 0;
/*      */     }
/* 8574 */     if (offset2 < 0) {
/* 8575 */       offset2 = 0;
/*      */     }
/* 8577 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8578 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8579 */       float aux = array[offset1];
/* 8580 */       array[offset1] = array[offset2];
/* 8581 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[] array, int offset1, int offset2) {
/* 8608 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8611 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(int[] array, int offset1, int offset2, int len) {
/* 8639 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8642 */     if (offset1 < 0) {
/* 8643 */       offset1 = 0;
/*      */     }
/* 8645 */     if (offset2 < 0) {
/* 8646 */       offset2 = 0;
/*      */     }
/* 8648 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8649 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8650 */       int aux = array[offset1];
/* 8651 */       array[offset1] = array[offset2];
/* 8652 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(long[] array, int offset1, int offset2) {
/* 8679 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8682 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(long[] array, int offset1, int offset2, int len) {
/* 8710 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8713 */     if (offset1 < 0) {
/* 8714 */       offset1 = 0;
/*      */     }
/* 8716 */     if (offset2 < 0) {
/* 8717 */       offset2 = 0;
/*      */     }
/* 8719 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8720 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8721 */       long aux = array[offset1];
/* 8722 */       array[offset1] = array[offset2];
/* 8723 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(Object[] array, int offset1, int offset2) {
/* 8751 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8754 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(Object[] array, int offset1, int offset2, int len) {
/* 8782 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8785 */     if (offset1 < 0) {
/* 8786 */       offset1 = 0;
/*      */     }
/* 8788 */     if (offset2 < 0) {
/* 8789 */       offset2 = 0;
/*      */     }
/* 8791 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8792 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8793 */       Object aux = array[offset1];
/* 8794 */       array[offset1] = array[offset2];
/* 8795 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(short[] array, int offset1, int offset2) {
/* 8821 */     if (isEmpty(array)) {
/*      */       return;
/*      */     }
/* 8824 */     swap(array, offset1, offset2, 1);
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
/*      */   public static void swap(short[] array, int offset1, int offset2, int len) {
/* 8852 */     if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
/*      */       return;
/*      */     }
/* 8855 */     if (offset1 < 0) {
/* 8856 */       offset1 = 0;
/*      */     }
/* 8858 */     if (offset2 < 0) {
/* 8859 */       offset2 = 0;
/*      */     }
/* 8861 */     if (offset1 == offset2) {
/*      */       return;
/*      */     }
/* 8864 */     len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
/* 8865 */     for (int i = 0; i < len; i++, offset1++, offset2++) {
/* 8866 */       short aux = array[offset1];
/* 8867 */       array[offset1] = array[offset2];
/* 8868 */       array[offset2] = aux;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] toArray(T... items) {
/* 8912 */     return items;
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
/*      */   public static Map<Object, Object> toMap(Object[] array) {
/* 8943 */     if (array == null) {
/* 8944 */       return null;
/*      */     }
/* 8946 */     Map<Object, Object> map = new HashMap<>((int)(array.length * 1.5D));
/* 8947 */     for (int i = 0; i < array.length; i++) {
/* 8948 */       Object object = array[i];
/* 8949 */       if (object instanceof Map.Entry) {
/* 8950 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
/* 8951 */         map.put(entry.getKey(), entry.getValue());
/* 8952 */       } else if (object instanceof Object[]) {
/* 8953 */         Object[] entry = (Object[])object;
/* 8954 */         if (entry.length < 2) {
/* 8955 */           throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
/*      */         }
/*      */ 
/*      */         
/* 8959 */         map.put(entry[0], entry[1]);
/*      */       } else {
/* 8961 */         throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 8966 */     return map;
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
/*      */   public static Boolean[] toObject(boolean[] array) {
/* 8978 */     if (array == null)
/* 8979 */       return null; 
/* 8980 */     if (array.length == 0) {
/* 8981 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*      */     }
/* 8983 */     Boolean[] result = new Boolean[array.length];
/* 8984 */     for (int i = 0; i < array.length; i++) {
/* 8985 */       result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/* 8987 */     return result;
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
/*      */   public static Byte[] toObject(byte[] array) {
/* 8999 */     if (array == null)
/* 9000 */       return null; 
/* 9001 */     if (array.length == 0) {
/* 9002 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*      */     }
/* 9004 */     Byte[] result = new Byte[array.length];
/* 9005 */     for (int i = 0; i < array.length; i++) {
/* 9006 */       result[i] = Byte.valueOf(array[i]);
/*      */     }
/* 9008 */     return result;
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
/*      */   public static Character[] toObject(char[] array) {
/* 9020 */     if (array == null)
/* 9021 */       return null; 
/* 9022 */     if (array.length == 0) {
/* 9023 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*      */     }
/* 9025 */     Character[] result = new Character[array.length];
/* 9026 */     for (int i = 0; i < array.length; i++) {
/* 9027 */       result[i] = Character.valueOf(array[i]);
/*      */     }
/* 9029 */     return result;
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
/*      */   public static Double[] toObject(double[] array) {
/* 9041 */     if (array == null)
/* 9042 */       return null; 
/* 9043 */     if (array.length == 0) {
/* 9044 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*      */     }
/* 9046 */     Double[] result = new Double[array.length];
/* 9047 */     for (int i = 0; i < array.length; i++) {
/* 9048 */       result[i] = Double.valueOf(array[i]);
/*      */     }
/* 9050 */     return result;
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
/*      */   public static Float[] toObject(float[] array) {
/* 9062 */     if (array == null)
/* 9063 */       return null; 
/* 9064 */     if (array.length == 0) {
/* 9065 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*      */     }
/* 9067 */     Float[] result = new Float[array.length];
/* 9068 */     for (int i = 0; i < array.length; i++) {
/* 9069 */       result[i] = Float.valueOf(array[i]);
/*      */     }
/* 9071 */     return result;
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
/*      */   public static Integer[] toObject(int[] array) {
/* 9083 */     if (array == null)
/* 9084 */       return null; 
/* 9085 */     if (array.length == 0) {
/* 9086 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*      */     }
/* 9088 */     Integer[] result = new Integer[array.length];
/* 9089 */     for (int i = 0; i < array.length; i++) {
/* 9090 */       result[i] = Integer.valueOf(array[i]);
/*      */     }
/* 9092 */     return result;
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
/*      */   public static Long[] toObject(long[] array) {
/* 9104 */     if (array == null)
/* 9105 */       return null; 
/* 9106 */     if (array.length == 0) {
/* 9107 */       return EMPTY_LONG_OBJECT_ARRAY;
/*      */     }
/* 9109 */     Long[] result = new Long[array.length];
/* 9110 */     for (int i = 0; i < array.length; i++) {
/* 9111 */       result[i] = Long.valueOf(array[i]);
/*      */     }
/* 9113 */     return result;
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
/*      */   public static Short[] toObject(short[] array) {
/* 9125 */     if (array == null)
/* 9126 */       return null; 
/* 9127 */     if (array.length == 0) {
/* 9128 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*      */     }
/* 9130 */     Short[] result = new Short[array.length];
/* 9131 */     for (int i = 0; i < array.length; i++) {
/* 9132 */       result[i] = Short.valueOf(array[i]);
/*      */     }
/* 9134 */     return result;
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
/*      */   public static boolean[] toPrimitive(Boolean[] array) {
/* 9149 */     if (array == null)
/* 9150 */       return null; 
/* 9151 */     if (array.length == 0) {
/* 9152 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 9154 */     boolean[] result = new boolean[array.length];
/* 9155 */     for (int i = 0; i < array.length; i++) {
/* 9156 */       result[i] = array[i].booleanValue();
/*      */     }
/* 9158 */     return result;
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
/*      */   public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
/* 9171 */     if (array == null)
/* 9172 */       return null; 
/* 9173 */     if (array.length == 0) {
/* 9174 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 9176 */     boolean[] result = new boolean[array.length];
/* 9177 */     for (int i = 0; i < array.length; i++) {
/* 9178 */       Boolean b = array[i];
/* 9179 */       result[i] = (b == null) ? valueForNull : b.booleanValue();
/*      */     } 
/* 9181 */     return result;
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
/*      */   public static byte[] toPrimitive(Byte[] array) {
/* 9196 */     if (array == null)
/* 9197 */       return null; 
/* 9198 */     if (array.length == 0) {
/* 9199 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 9201 */     byte[] result = new byte[array.length];
/* 9202 */     for (int i = 0; i < array.length; i++) {
/* 9203 */       result[i] = array[i].byteValue();
/*      */     }
/* 9205 */     return result;
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
/*      */   public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
/* 9218 */     if (array == null)
/* 9219 */       return null; 
/* 9220 */     if (array.length == 0) {
/* 9221 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 9223 */     byte[] result = new byte[array.length];
/* 9224 */     for (int i = 0; i < array.length; i++) {
/* 9225 */       Byte b = array[i];
/* 9226 */       result[i] = (b == null) ? valueForNull : b.byteValue();
/*      */     } 
/* 9228 */     return result;
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
/*      */   public static char[] toPrimitive(Character[] array) {
/* 9243 */     if (array == null)
/* 9244 */       return null; 
/* 9245 */     if (array.length == 0) {
/* 9246 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 9248 */     char[] result = new char[array.length];
/* 9249 */     for (int i = 0; i < array.length; i++) {
/* 9250 */       result[i] = array[i].charValue();
/*      */     }
/* 9252 */     return result;
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
/*      */   public static char[] toPrimitive(Character[] array, char valueForNull) {
/* 9265 */     if (array == null)
/* 9266 */       return null; 
/* 9267 */     if (array.length == 0) {
/* 9268 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 9270 */     char[] result = new char[array.length];
/* 9271 */     for (int i = 0; i < array.length; i++) {
/* 9272 */       Character b = array[i];
/* 9273 */       result[i] = (b == null) ? valueForNull : b.charValue();
/*      */     } 
/* 9275 */     return result;
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
/*      */   public static double[] toPrimitive(Double[] array) {
/* 9290 */     if (array == null)
/* 9291 */       return null; 
/* 9292 */     if (array.length == 0) {
/* 9293 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 9295 */     double[] result = new double[array.length];
/* 9296 */     for (int i = 0; i < array.length; i++) {
/* 9297 */       result[i] = array[i].doubleValue();
/*      */     }
/* 9299 */     return result;
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
/*      */   public static double[] toPrimitive(Double[] array, double valueForNull) {
/* 9312 */     if (array == null)
/* 9313 */       return null; 
/* 9314 */     if (array.length == 0) {
/* 9315 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 9317 */     double[] result = new double[array.length];
/* 9318 */     for (int i = 0; i < array.length; i++) {
/* 9319 */       Double b = array[i];
/* 9320 */       result[i] = (b == null) ? valueForNull : b.doubleValue();
/*      */     } 
/* 9322 */     return result;
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
/*      */   public static float[] toPrimitive(Float[] array) {
/* 9337 */     if (array == null)
/* 9338 */       return null; 
/* 9339 */     if (array.length == 0) {
/* 9340 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 9342 */     float[] result = new float[array.length];
/* 9343 */     for (int i = 0; i < array.length; i++) {
/* 9344 */       result[i] = array[i].floatValue();
/*      */     }
/* 9346 */     return result;
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
/*      */   public static float[] toPrimitive(Float[] array, float valueForNull) {
/* 9359 */     if (array == null)
/* 9360 */       return null; 
/* 9361 */     if (array.length == 0) {
/* 9362 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 9364 */     float[] result = new float[array.length];
/* 9365 */     for (int i = 0; i < array.length; i++) {
/* 9366 */       Float b = array[i];
/* 9367 */       result[i] = (b == null) ? valueForNull : b.floatValue();
/*      */     } 
/* 9369 */     return result;
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
/*      */   public static int[] toPrimitive(Integer[] array) {
/* 9384 */     if (array == null)
/* 9385 */       return null; 
/* 9386 */     if (array.length == 0) {
/* 9387 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 9389 */     int[] result = new int[array.length];
/* 9390 */     for (int i = 0; i < array.length; i++) {
/* 9391 */       result[i] = array[i].intValue();
/*      */     }
/* 9393 */     return result;
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
/*      */   public static int[] toPrimitive(Integer[] array, int valueForNull) {
/* 9406 */     if (array == null)
/* 9407 */       return null; 
/* 9408 */     if (array.length == 0) {
/* 9409 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 9411 */     int[] result = new int[array.length];
/* 9412 */     for (int i = 0; i < array.length; i++) {
/* 9413 */       Integer b = array[i];
/* 9414 */       result[i] = (b == null) ? valueForNull : b.intValue();
/*      */     } 
/* 9416 */     return result;
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
/*      */   public static long[] toPrimitive(Long[] array) {
/* 9431 */     if (array == null)
/* 9432 */       return null; 
/* 9433 */     if (array.length == 0) {
/* 9434 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 9436 */     long[] result = new long[array.length];
/* 9437 */     for (int i = 0; i < array.length; i++) {
/* 9438 */       result[i] = array[i].longValue();
/*      */     }
/* 9440 */     return result;
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
/*      */   public static long[] toPrimitive(Long[] array, long valueForNull) {
/* 9453 */     if (array == null)
/* 9454 */       return null; 
/* 9455 */     if (array.length == 0) {
/* 9456 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 9458 */     long[] result = new long[array.length];
/* 9459 */     for (int i = 0; i < array.length; i++) {
/* 9460 */       Long b = array[i];
/* 9461 */       result[i] = (b == null) ? valueForNull : b.longValue();
/*      */     } 
/* 9463 */     return result;
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
/*      */   public static Object toPrimitive(Object array) {
/* 9476 */     if (array == null) {
/* 9477 */       return null;
/*      */     }
/* 9479 */     Class<?> ct = array.getClass().getComponentType();
/* 9480 */     Class<?> pt = ClassUtils.wrapperToPrimitive(ct);
/* 9481 */     if (int.class.equals(pt)) {
/* 9482 */       return toPrimitive((Integer[])array);
/*      */     }
/* 9484 */     if (long.class.equals(pt)) {
/* 9485 */       return toPrimitive((Long[])array);
/*      */     }
/* 9487 */     if (short.class.equals(pt)) {
/* 9488 */       return toPrimitive((Short[])array);
/*      */     }
/* 9490 */     if (double.class.equals(pt)) {
/* 9491 */       return toPrimitive((Double[])array);
/*      */     }
/* 9493 */     if (float.class.equals(pt)) {
/* 9494 */       return toPrimitive((Float[])array);
/*      */     }
/* 9496 */     return array;
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
/*      */   public static short[] toPrimitive(Short[] array) {
/* 9511 */     if (array == null)
/* 9512 */       return null; 
/* 9513 */     if (array.length == 0) {
/* 9514 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 9516 */     short[] result = new short[array.length];
/* 9517 */     for (int i = 0; i < array.length; i++) {
/* 9518 */       result[i] = array[i].shortValue();
/*      */     }
/* 9520 */     return result;
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
/*      */   public static short[] toPrimitive(Short[] array, short valueForNull) {
/* 9533 */     if (array == null)
/* 9534 */       return null; 
/* 9535 */     if (array.length == 0) {
/* 9536 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 9538 */     short[] result = new short[array.length];
/* 9539 */     for (int i = 0; i < array.length; i++) {
/* 9540 */       Short b = array[i];
/* 9541 */       result[i] = (b == null) ? valueForNull : b.shortValue();
/*      */     } 
/* 9543 */     return result;
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
/*      */   public static String toString(Object array) {
/* 9560 */     return toString(array, "{}");
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
/*      */   public static String toString(Object array, String stringIfNull) {
/* 9576 */     if (array == null) {
/* 9577 */       return stringIfNull;
/*      */     }
/* 9579 */     return (new ToStringBuilder(array, ToStringStyle.SIMPLE_STYLE)).append(array).toString();
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
/*      */   public static String[] toStringArray(Object[] array) {
/* 9594 */     if (array == null)
/* 9595 */       return null; 
/* 9596 */     if (array.length == 0) {
/* 9597 */       return EMPTY_STRING_ARRAY;
/*      */     }
/*      */     
/* 9600 */     String[] result = new String[array.length];
/* 9601 */     for (int i = 0; i < array.length; i++) {
/* 9602 */       result[i] = array[i].toString();
/*      */     }
/*      */     
/* 9605 */     return result;
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
/*      */   public static String[] toStringArray(Object[] array, String valueForNullElements) {
/* 9620 */     if (null == array)
/* 9621 */       return null; 
/* 9622 */     if (array.length == 0) {
/* 9623 */       return EMPTY_STRING_ARRAY;
/*      */     }
/*      */     
/* 9626 */     String[] result = new String[array.length];
/* 9627 */     for (int i = 0; i < array.length; i++) {
/* 9628 */       Object object = array[i];
/* 9629 */       result[i] = (object == null) ? valueForNullElements : object.toString();
/*      */     } 
/*      */     
/* 9632 */     return result;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */