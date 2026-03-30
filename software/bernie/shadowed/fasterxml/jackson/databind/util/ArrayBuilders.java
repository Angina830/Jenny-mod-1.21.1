/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.HashSet;
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
/*     */ public final class ArrayBuilders
/*     */ {
/*  17 */   private BooleanBuilder _booleanBuilder = null;
/*     */ 
/*     */ 
/*     */   
/*  21 */   private ByteBuilder _byteBuilder = null;
/*  22 */   private ShortBuilder _shortBuilder = null;
/*  23 */   private IntBuilder _intBuilder = null;
/*  24 */   private LongBuilder _longBuilder = null;
/*     */   
/*  26 */   private FloatBuilder _floatBuilder = null;
/*  27 */   private DoubleBuilder _doubleBuilder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanBuilder getBooleanBuilder() {
/*  33 */     if (this._booleanBuilder == null) {
/*  34 */       this._booleanBuilder = new BooleanBuilder();
/*     */     }
/*  36 */     return this._booleanBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuilder getByteBuilder() {
/*  41 */     if (this._byteBuilder == null) {
/*  42 */       this._byteBuilder = new ByteBuilder();
/*     */     }
/*  44 */     return this._byteBuilder;
/*     */   }
/*     */   
/*     */   public ShortBuilder getShortBuilder() {
/*  48 */     if (this._shortBuilder == null) {
/*  49 */       this._shortBuilder = new ShortBuilder();
/*     */     }
/*  51 */     return this._shortBuilder;
/*     */   }
/*     */   
/*     */   public IntBuilder getIntBuilder() {
/*  55 */     if (this._intBuilder == null) {
/*  56 */       this._intBuilder = new IntBuilder();
/*     */     }
/*  58 */     return this._intBuilder;
/*     */   }
/*     */   
/*     */   public LongBuilder getLongBuilder() {
/*  62 */     if (this._longBuilder == null) {
/*  63 */       this._longBuilder = new LongBuilder();
/*     */     }
/*  65 */     return this._longBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatBuilder getFloatBuilder() {
/*  70 */     if (this._floatBuilder == null) {
/*  71 */       this._floatBuilder = new FloatBuilder();
/*     */     }
/*  73 */     return this._floatBuilder;
/*     */   }
/*     */   
/*     */   public DoubleBuilder getDoubleBuilder() {
/*  77 */     if (this._doubleBuilder == null) {
/*  78 */       this._doubleBuilder = new DoubleBuilder();
/*     */     }
/*  80 */     return this._doubleBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class BooleanBuilder
/*     */     extends PrimitiveArrayBuilder<boolean[]>
/*     */   {
/*     */     public final boolean[] _constructArray(int len) {
/*  94 */       return new boolean[len];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ByteBuilder
/*     */     extends PrimitiveArrayBuilder<byte[]>
/*     */   {
/*     */     public final byte[] _constructArray(int len) {
/* 102 */       return new byte[len];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ShortBuilder
/*     */     extends PrimitiveArrayBuilder<short[]> {
/*     */     public final short[] _constructArray(int len) {
/* 109 */       return new short[len];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IntBuilder
/*     */     extends PrimitiveArrayBuilder<int[]> {
/*     */     public final int[] _constructArray(int len) {
/* 116 */       return new int[len];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class LongBuilder
/*     */     extends PrimitiveArrayBuilder<long[]> {
/*     */     public final long[] _constructArray(int len) {
/* 123 */       return new long[len];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class FloatBuilder
/*     */     extends PrimitiveArrayBuilder<float[]>
/*     */   {
/*     */     public final float[] _constructArray(int len) {
/* 131 */       return new float[len];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class DoubleBuilder
/*     */     extends PrimitiveArrayBuilder<double[]> {
/*     */     public final double[] _constructArray(int len) {
/* 138 */       return new double[len];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getArrayComparator(final Object defaultValue) {
/* 159 */     final int length = Array.getLength(defaultValue);
/* 160 */     final Class<?> defaultValueType = defaultValue.getClass();
/* 161 */     return new Object()
/*     */       {
/*     */         public boolean equals(Object other) {
/* 164 */           if (other == this) return true; 
/* 165 */           if (!ClassUtil.hasClass(other, defaultValueType)) {
/* 166 */             return false;
/*     */           }
/* 168 */           if (Array.getLength(other) != length) return false;
/*     */           
/* 170 */           for (int i = 0; i < length; i++) {
/* 171 */             Object value1 = Array.get(defaultValue, i);
/* 172 */             Object value2 = Array.get(other, i);
/* 173 */             if (value1 != value2 && 
/* 174 */               value1 != null && 
/* 175 */               !value1.equals(value2)) {
/* 176 */               return false;
/*     */             }
/*     */           } 
/*     */           
/* 180 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> HashSet<T> arrayToSet(T[] elements) {
/* 187 */     if (elements != null) {
/* 188 */       int len = elements.length;
/* 189 */       HashSet<T> result = new HashSet<>(len);
/* 190 */       for (int i = 0; i < len; i++) {
/* 191 */         result.add(elements[i]);
/*     */       }
/* 193 */       return result;
/*     */     } 
/* 195 */     return new HashSet<>();
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
/*     */   public static <T> T[] insertInListNoDup(T[] array, T element) {
/* 209 */     int len = array.length;
/*     */ 
/*     */     
/* 212 */     for (int ix = 0; ix < len; ix++) {
/* 213 */       if (array[ix] == element) {
/*     */         
/* 215 */         if (ix == 0) {
/* 216 */           return array;
/*     */         }
/*     */         
/* 219 */         T[] arrayOfT = (T[])Array.newInstance(array.getClass().getComponentType(), len);
/* 220 */         System.arraycopy(array, 0, arrayOfT, 1, ix);
/* 221 */         arrayOfT[0] = element;
/* 222 */         ix++;
/* 223 */         int left = len - ix;
/* 224 */         if (left > 0) {
/* 225 */           System.arraycopy(array, ix, arrayOfT, ix, left);
/*     */         }
/* 227 */         return arrayOfT;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 232 */     T[] result = (T[])Array.newInstance(array.getClass().getComponentType(), len + 1);
/* 233 */     if (len > 0) {
/* 234 */       System.arraycopy(array, 0, result, 1, len);
/*     */     }
/* 236 */     result[0] = element;
/* 237 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ArrayBuilders.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */