/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ArrayUtils;
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
/*     */ public class DiffBuilder<T>
/*     */   implements Builder<DiffResult<T>>
/*     */ {
/*     */   private final List<Diff<?>> diffs;
/*     */   private final boolean objectsTriviallyEqual;
/*     */   private final T left;
/*     */   private final T right;
/*     */   private final ToStringStyle style;
/*     */   
/*     */   public DiffBuilder(T lhs, T rhs, ToStringStyle style, boolean testTriviallyEqual) {
/* 107 */     Validate.notNull(lhs, "lhs cannot be null", new Object[0]);
/* 108 */     Validate.notNull(rhs, "rhs cannot be null", new Object[0]);
/*     */     
/* 110 */     this.diffs = new ArrayList<>();
/* 111 */     this.left = lhs;
/* 112 */     this.right = rhs;
/* 113 */     this.style = style;
/*     */ 
/*     */     
/* 116 */     this.objectsTriviallyEqual = (testTriviallyEqual && (lhs == rhs || lhs.equals(rhs)));
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
/*     */   public DiffBuilder(T lhs, T rhs, ToStringStyle style) {
/* 148 */     this(lhs, rhs, style, true);
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
/*     */   public DiffBuilder<T> append(String fieldName, final boolean lhs, final boolean rhs) {
/* 168 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 170 */     if (this.objectsTriviallyEqual) {
/* 171 */       return this;
/*     */     }
/* 173 */     if (lhs != rhs) {
/* 174 */       this.diffs.add(new Diff<Boolean>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Boolean getLeft() {
/* 179 */               return Boolean.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Boolean getRight() {
/* 184 */               return Boolean.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 188 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final boolean[] lhs, final boolean[] rhs) {
/* 208 */     validateFieldNameNotNull(fieldName);
/* 209 */     if (this.objectsTriviallyEqual) {
/* 210 */       return this;
/*     */     }
/* 212 */     if (!Arrays.equals(lhs, rhs)) {
/* 213 */       this.diffs.add(new Diff<Boolean[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Boolean[] getLeft() {
/* 218 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Boolean[] getRight() {
/* 223 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 227 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final byte lhs, final byte rhs) {
/* 247 */     validateFieldNameNotNull(fieldName);
/* 248 */     if (this.objectsTriviallyEqual) {
/* 249 */       return this;
/*     */     }
/* 251 */     if (lhs != rhs) {
/* 252 */       this.diffs.add(new Diff<Byte>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Byte getLeft() {
/* 257 */               return Byte.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Byte getRight() {
/* 262 */               return Byte.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 266 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final byte[] lhs, final byte[] rhs) {
/* 286 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 288 */     if (this.objectsTriviallyEqual) {
/* 289 */       return this;
/*     */     }
/* 291 */     if (!Arrays.equals(lhs, rhs)) {
/* 292 */       this.diffs.add(new Diff<Byte[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Byte[] getLeft() {
/* 297 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Byte[] getRight() {
/* 302 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 306 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final char lhs, final char rhs) {
/* 326 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 328 */     if (this.objectsTriviallyEqual) {
/* 329 */       return this;
/*     */     }
/* 331 */     if (lhs != rhs) {
/* 332 */       this.diffs.add(new Diff<Character>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Character getLeft() {
/* 337 */               return Character.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Character getRight() {
/* 342 */               return Character.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 346 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final char[] lhs, final char[] rhs) {
/* 366 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 368 */     if (this.objectsTriviallyEqual) {
/* 369 */       return this;
/*     */     }
/* 371 */     if (!Arrays.equals(lhs, rhs)) {
/* 372 */       this.diffs.add(new Diff<Character[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Character[] getLeft() {
/* 377 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Character[] getRight() {
/* 382 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 386 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final double lhs, final double rhs) {
/* 406 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 408 */     if (this.objectsTriviallyEqual) {
/* 409 */       return this;
/*     */     }
/* 411 */     if (Double.doubleToLongBits(lhs) != Double.doubleToLongBits(rhs)) {
/* 412 */       this.diffs.add(new Diff<Double>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Double getLeft() {
/* 417 */               return Double.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Double getRight() {
/* 422 */               return Double.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 426 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final double[] lhs, final double[] rhs) {
/* 446 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 448 */     if (this.objectsTriviallyEqual) {
/* 449 */       return this;
/*     */     }
/* 451 */     if (!Arrays.equals(lhs, rhs)) {
/* 452 */       this.diffs.add(new Diff<Double[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Double[] getLeft() {
/* 457 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Double[] getRight() {
/* 462 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 466 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final float lhs, final float rhs) {
/* 486 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 488 */     if (this.objectsTriviallyEqual) {
/* 489 */       return this;
/*     */     }
/* 491 */     if (Float.floatToIntBits(lhs) != Float.floatToIntBits(rhs)) {
/* 492 */       this.diffs.add(new Diff<Float>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Float getLeft() {
/* 497 */               return Float.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Float getRight() {
/* 502 */               return Float.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 506 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final float[] lhs, final float[] rhs) {
/* 526 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 528 */     if (this.objectsTriviallyEqual) {
/* 529 */       return this;
/*     */     }
/* 531 */     if (!Arrays.equals(lhs, rhs)) {
/* 532 */       this.diffs.add(new Diff<Float[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Float[] getLeft() {
/* 537 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Float[] getRight() {
/* 542 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 546 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final int lhs, final int rhs) {
/* 566 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 568 */     if (this.objectsTriviallyEqual) {
/* 569 */       return this;
/*     */     }
/* 571 */     if (lhs != rhs) {
/* 572 */       this.diffs.add(new Diff<Integer>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Integer getLeft() {
/* 577 */               return Integer.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Integer getRight() {
/* 582 */               return Integer.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 586 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final int[] lhs, final int[] rhs) {
/* 606 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 608 */     if (this.objectsTriviallyEqual) {
/* 609 */       return this;
/*     */     }
/* 611 */     if (!Arrays.equals(lhs, rhs)) {
/* 612 */       this.diffs.add(new Diff<Integer[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Integer[] getLeft() {
/* 617 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Integer[] getRight() {
/* 622 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 626 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final long lhs, final long rhs) {
/* 646 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 648 */     if (this.objectsTriviallyEqual) {
/* 649 */       return this;
/*     */     }
/* 651 */     if (lhs != rhs) {
/* 652 */       this.diffs.add(new Diff<Long>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Long getLeft() {
/* 657 */               return Long.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Long getRight() {
/* 662 */               return Long.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 666 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final long[] lhs, final long[] rhs) {
/* 686 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 688 */     if (this.objectsTriviallyEqual) {
/* 689 */       return this;
/*     */     }
/* 691 */     if (!Arrays.equals(lhs, rhs)) {
/* 692 */       this.diffs.add(new Diff<Long[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Long[] getLeft() {
/* 697 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Long[] getRight() {
/* 702 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 706 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final short lhs, final short rhs) {
/* 726 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 728 */     if (this.objectsTriviallyEqual) {
/* 729 */       return this;
/*     */     }
/* 731 */     if (lhs != rhs) {
/* 732 */       this.diffs.add(new Diff<Short>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Short getLeft() {
/* 737 */               return Short.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Short getRight() {
/* 742 */               return Short.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 746 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final short[] lhs, final short[] rhs) {
/* 766 */     validateFieldNameNotNull(fieldName);
/*     */     
/* 768 */     if (this.objectsTriviallyEqual) {
/* 769 */       return this;
/*     */     }
/* 771 */     if (!Arrays.equals(lhs, rhs)) {
/* 772 */       this.diffs.add(new Diff<Short[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Short[] getLeft() {
/* 777 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Short[] getRight() {
/* 782 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 786 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final Object lhs, final Object rhs) {
/*     */     Object objectToTest;
/* 806 */     validateFieldNameNotNull(fieldName);
/* 807 */     if (this.objectsTriviallyEqual) {
/* 808 */       return this;
/*     */     }
/* 810 */     if (lhs == rhs) {
/* 811 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 815 */     if (lhs != null) {
/* 816 */       objectToTest = lhs;
/*     */     } else {
/*     */       
/* 819 */       objectToTest = rhs;
/*     */     } 
/*     */     
/* 822 */     if (objectToTest.getClass().isArray()) {
/* 823 */       if (objectToTest instanceof boolean[]) {
/* 824 */         return append(fieldName, (boolean[])lhs, (boolean[])rhs);
/*     */       }
/* 826 */       if (objectToTest instanceof byte[]) {
/* 827 */         return append(fieldName, (byte[])lhs, (byte[])rhs);
/*     */       }
/* 829 */       if (objectToTest instanceof char[]) {
/* 830 */         return append(fieldName, (char[])lhs, (char[])rhs);
/*     */       }
/* 832 */       if (objectToTest instanceof double[]) {
/* 833 */         return append(fieldName, (double[])lhs, (double[])rhs);
/*     */       }
/* 835 */       if (objectToTest instanceof float[]) {
/* 836 */         return append(fieldName, (float[])lhs, (float[])rhs);
/*     */       }
/* 838 */       if (objectToTest instanceof int[]) {
/* 839 */         return append(fieldName, (int[])lhs, (int[])rhs);
/*     */       }
/* 841 */       if (objectToTest instanceof long[]) {
/* 842 */         return append(fieldName, (long[])lhs, (long[])rhs);
/*     */       }
/* 844 */       if (objectToTest instanceof short[]) {
/* 845 */         return append(fieldName, (short[])lhs, (short[])rhs);
/*     */       }
/*     */       
/* 848 */       return append(fieldName, (Object[])lhs, (Object[])rhs);
/*     */     } 
/*     */ 
/*     */     
/* 852 */     if (lhs != null && lhs.equals(rhs)) {
/* 853 */       return this;
/*     */     }
/*     */     
/* 856 */     this.diffs.add(new Diff(fieldName)
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object getLeft() {
/* 861 */             return lhs;
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getRight() {
/* 866 */             return rhs;
/*     */           }
/*     */         });
/*     */     
/* 870 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, final Object[] lhs, final Object[] rhs) {
/* 890 */     validateFieldNameNotNull(fieldName);
/* 891 */     if (this.objectsTriviallyEqual) {
/* 892 */       return this;
/*     */     }
/*     */     
/* 895 */     if (!Arrays.equals(lhs, rhs)) {
/* 896 */       this.diffs.add(new Diff<Object[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Object[] getLeft() {
/* 901 */               return lhs;
/*     */             }
/*     */ 
/*     */             
/*     */             public Object[] getRight() {
/* 906 */               return rhs;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 911 */     return this;
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
/*     */   public DiffBuilder<T> append(String fieldName, DiffResult<T> diffResult) {
/* 951 */     validateFieldNameNotNull(fieldName);
/* 952 */     Validate.notNull(diffResult, "Diff result cannot be null", new Object[0]);
/* 953 */     if (this.objectsTriviallyEqual) {
/* 954 */       return this;
/*     */     }
/*     */     
/* 957 */     for (Diff<?> diff : diffResult.getDiffs()) {
/* 958 */       append(fieldName + "." + diff.getFieldName(), diff
/* 959 */           .getLeft(), diff.getRight());
/*     */     }
/*     */     
/* 962 */     return this;
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
/*     */   public DiffResult<T> build() {
/* 976 */     return new DiffResult<>(this.left, this.right, this.diffs, this.style);
/*     */   }
/*     */   
/*     */   private void validateFieldNameNotNull(String fieldName) {
/* 980 */     Validate.notNull(fieldName, "Field name cannot be null", new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\DiffBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */