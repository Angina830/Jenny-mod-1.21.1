/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import java.lang.reflect.AccessibleObject;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Objects;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompareToBuilder
/*      */   implements Builder<Integer>
/*      */ {
/*  112 */   private int comparison = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reflectionCompare(Object lhs, Object rhs) {
/*  143 */     return reflectionCompare(lhs, rhs, false, null, new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients) {
/*  175 */     return reflectionCompare(lhs, rhs, compareTransients, null, new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reflectionCompare(Object lhs, Object rhs, Collection<String> excludeFields) {
/*  208 */     return reflectionCompare(lhs, rhs, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reflectionCompare(Object lhs, Object rhs, String... excludeFields) {
/*  241 */     return reflectionCompare(lhs, rhs, false, null, excludeFields);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients, Class<?> reflectUpToClass, String... excludeFields) {
/*  283 */     if (lhs == rhs) {
/*  284 */       return 0;
/*      */     }
/*  286 */     Objects.requireNonNull(lhs, "lhs");
/*  287 */     Objects.requireNonNull(rhs, "rhs");
/*      */     
/*  289 */     Class<?> lhsClazz = lhs.getClass();
/*  290 */     if (!lhsClazz.isInstance(rhs)) {
/*  291 */       throw new ClassCastException();
/*      */     }
/*  293 */     CompareToBuilder compareToBuilder = new CompareToBuilder();
/*  294 */     reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients, excludeFields);
/*  295 */     while (lhsClazz.getSuperclass() != null && lhsClazz != reflectUpToClass) {
/*  296 */       lhsClazz = lhsClazz.getSuperclass();
/*  297 */       reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients, excludeFields);
/*      */     } 
/*  299 */     return compareToBuilder.toComparison();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void reflectionAppend(Object lhs, Object rhs, Class<?> clazz, CompareToBuilder builder, boolean useTransients, String[] excludeFields) {
/*  321 */     Field[] fields = clazz.getDeclaredFields();
/*  322 */     AccessibleObject.setAccessible((AccessibleObject[])fields, true);
/*  323 */     for (int i = 0; i < fields.length && builder.comparison == 0; i++) {
/*  324 */       Field f = fields[i];
/*  325 */       if (!ArrayUtils.contains((Object[])excludeFields, f.getName()) && 
/*  326 */         !f.getName().contains("$") && (useTransients || 
/*  327 */         !Modifier.isTransient(f.getModifiers())) && 
/*  328 */         !Modifier.isStatic(f.getModifiers())) {
/*      */         try {
/*  330 */           builder.append(f.get(lhs), f.get(rhs));
/*  331 */         } catch (IllegalAccessException e) {
/*      */ 
/*      */           
/*  334 */           throw new InternalError("Unexpected IllegalAccessException");
/*      */         } 
/*      */       }
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
/*      */   public CompareToBuilder appendSuper(int superCompareTo) {
/*  350 */     if (this.comparison != 0) {
/*  351 */       return this;
/*      */     }
/*  353 */     this.comparison = superCompareTo;
/*  354 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(Object lhs, Object rhs) {
/*  378 */     return append(lhs, rhs, (Comparator<?>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(Object lhs, Object rhs, Comparator<?> comparator) {
/*  407 */     if (this.comparison != 0) {
/*  408 */       return this;
/*      */     }
/*  410 */     if (lhs == rhs) {
/*  411 */       return this;
/*      */     }
/*  413 */     if (lhs == null) {
/*  414 */       this.comparison = -1;
/*  415 */       return this;
/*      */     } 
/*  417 */     if (rhs == null) {
/*  418 */       this.comparison = 1;
/*  419 */       return this;
/*      */     } 
/*  421 */     if (lhs.getClass().isArray()) {
/*      */       
/*  423 */       appendArray(lhs, rhs, comparator);
/*      */     
/*      */     }
/*  426 */     else if (comparator == null) {
/*      */       
/*  428 */       Comparable<Object> comparable = (Comparable<Object>)lhs;
/*  429 */       this.comparison = comparable.compareTo(rhs);
/*      */     } else {
/*      */       
/*  432 */       Comparator<Object> comparator2 = (Comparator)comparator;
/*  433 */       this.comparison = comparator2.compare(lhs, rhs);
/*      */     } 
/*      */     
/*  436 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendArray(Object lhs, Object rhs, Comparator<?> comparator) {
/*  443 */     if (lhs instanceof long[]) {
/*  444 */       append((long[])lhs, (long[])rhs);
/*  445 */     } else if (lhs instanceof int[]) {
/*  446 */       append((int[])lhs, (int[])rhs);
/*  447 */     } else if (lhs instanceof short[]) {
/*  448 */       append((short[])lhs, (short[])rhs);
/*  449 */     } else if (lhs instanceof char[]) {
/*  450 */       append((char[])lhs, (char[])rhs);
/*  451 */     } else if (lhs instanceof byte[]) {
/*  452 */       append((byte[])lhs, (byte[])rhs);
/*  453 */     } else if (lhs instanceof double[]) {
/*  454 */       append((double[])lhs, (double[])rhs);
/*  455 */     } else if (lhs instanceof float[]) {
/*  456 */       append((float[])lhs, (float[])rhs);
/*  457 */     } else if (lhs instanceof boolean[]) {
/*  458 */       append((boolean[])lhs, (boolean[])rhs);
/*      */     }
/*      */     else {
/*      */       
/*  462 */       append((Object[])lhs, (Object[])rhs, comparator);
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
/*      */   public CompareToBuilder append(long lhs, long rhs) {
/*  476 */     if (this.comparison != 0) {
/*  477 */       return this;
/*      */     }
/*  479 */     this.comparison = Long.compare(lhs, rhs);
/*  480 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(int lhs, int rhs) {
/*  492 */     if (this.comparison != 0) {
/*  493 */       return this;
/*      */     }
/*  495 */     this.comparison = Integer.compare(lhs, rhs);
/*  496 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(short lhs, short rhs) {
/*  508 */     if (this.comparison != 0) {
/*  509 */       return this;
/*      */     }
/*  511 */     this.comparison = Short.compare(lhs, rhs);
/*  512 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(char lhs, char rhs) {
/*  524 */     if (this.comparison != 0) {
/*  525 */       return this;
/*      */     }
/*  527 */     this.comparison = Character.compare(lhs, rhs);
/*  528 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(byte lhs, byte rhs) {
/*  540 */     if (this.comparison != 0) {
/*  541 */       return this;
/*      */     }
/*  543 */     this.comparison = Byte.compare(lhs, rhs);
/*  544 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(double lhs, double rhs) {
/*  561 */     if (this.comparison != 0) {
/*  562 */       return this;
/*      */     }
/*  564 */     this.comparison = Double.compare(lhs, rhs);
/*  565 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(float lhs, float rhs) {
/*  582 */     if (this.comparison != 0) {
/*  583 */       return this;
/*      */     }
/*  585 */     this.comparison = Float.compare(lhs, rhs);
/*  586 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(boolean lhs, boolean rhs) {
/*  598 */     if (this.comparison != 0) {
/*  599 */       return this;
/*      */     }
/*  601 */     if (lhs == rhs) {
/*  602 */       return this;
/*      */     }
/*  604 */     if (lhs) {
/*  605 */       this.comparison = 1;
/*      */     } else {
/*  607 */       this.comparison = -1;
/*      */     } 
/*  609 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(Object[] lhs, Object[] rhs) {
/*  634 */     return append(lhs, rhs, (Comparator<?>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(Object[] lhs, Object[] rhs, Comparator<?> comparator) {
/*  661 */     if (this.comparison != 0) {
/*  662 */       return this;
/*      */     }
/*  664 */     if (lhs == rhs) {
/*  665 */       return this;
/*      */     }
/*  667 */     if (lhs == null) {
/*  668 */       this.comparison = -1;
/*  669 */       return this;
/*      */     } 
/*  671 */     if (rhs == null) {
/*  672 */       this.comparison = 1;
/*  673 */       return this;
/*      */     } 
/*  675 */     if (lhs.length != rhs.length) {
/*  676 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  677 */       return this;
/*      */     } 
/*  679 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  680 */       append(lhs[i], rhs[i], comparator);
/*      */     }
/*  682 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(long[] lhs, long[] rhs) {
/*  701 */     if (this.comparison != 0) {
/*  702 */       return this;
/*      */     }
/*  704 */     if (lhs == rhs) {
/*  705 */       return this;
/*      */     }
/*  707 */     if (lhs == null) {
/*  708 */       this.comparison = -1;
/*  709 */       return this;
/*      */     } 
/*  711 */     if (rhs == null) {
/*  712 */       this.comparison = 1;
/*  713 */       return this;
/*      */     } 
/*  715 */     if (lhs.length != rhs.length) {
/*  716 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  717 */       return this;
/*      */     } 
/*  719 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  720 */       append(lhs[i], rhs[i]);
/*      */     }
/*  722 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(int[] lhs, int[] rhs) {
/*  741 */     if (this.comparison != 0) {
/*  742 */       return this;
/*      */     }
/*  744 */     if (lhs == rhs) {
/*  745 */       return this;
/*      */     }
/*  747 */     if (lhs == null) {
/*  748 */       this.comparison = -1;
/*  749 */       return this;
/*      */     } 
/*  751 */     if (rhs == null) {
/*  752 */       this.comparison = 1;
/*  753 */       return this;
/*      */     } 
/*  755 */     if (lhs.length != rhs.length) {
/*  756 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  757 */       return this;
/*      */     } 
/*  759 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  760 */       append(lhs[i], rhs[i]);
/*      */     }
/*  762 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(short[] lhs, short[] rhs) {
/*  781 */     if (this.comparison != 0) {
/*  782 */       return this;
/*      */     }
/*  784 */     if (lhs == rhs) {
/*  785 */       return this;
/*      */     }
/*  787 */     if (lhs == null) {
/*  788 */       this.comparison = -1;
/*  789 */       return this;
/*      */     } 
/*  791 */     if (rhs == null) {
/*  792 */       this.comparison = 1;
/*  793 */       return this;
/*      */     } 
/*  795 */     if (lhs.length != rhs.length) {
/*  796 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  797 */       return this;
/*      */     } 
/*  799 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  800 */       append(lhs[i], rhs[i]);
/*      */     }
/*  802 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(char[] lhs, char[] rhs) {
/*  821 */     if (this.comparison != 0) {
/*  822 */       return this;
/*      */     }
/*  824 */     if (lhs == rhs) {
/*  825 */       return this;
/*      */     }
/*  827 */     if (lhs == null) {
/*  828 */       this.comparison = -1;
/*  829 */       return this;
/*      */     } 
/*  831 */     if (rhs == null) {
/*  832 */       this.comparison = 1;
/*  833 */       return this;
/*      */     } 
/*  835 */     if (lhs.length != rhs.length) {
/*  836 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  837 */       return this;
/*      */     } 
/*  839 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  840 */       append(lhs[i], rhs[i]);
/*      */     }
/*  842 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(byte[] lhs, byte[] rhs) {
/*  861 */     if (this.comparison != 0) {
/*  862 */       return this;
/*      */     }
/*  864 */     if (lhs == rhs) {
/*  865 */       return this;
/*      */     }
/*  867 */     if (lhs == null) {
/*  868 */       this.comparison = -1;
/*  869 */       return this;
/*      */     } 
/*  871 */     if (rhs == null) {
/*  872 */       this.comparison = 1;
/*  873 */       return this;
/*      */     } 
/*  875 */     if (lhs.length != rhs.length) {
/*  876 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  877 */       return this;
/*      */     } 
/*  879 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  880 */       append(lhs[i], rhs[i]);
/*      */     }
/*  882 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(double[] lhs, double[] rhs) {
/*  901 */     if (this.comparison != 0) {
/*  902 */       return this;
/*      */     }
/*  904 */     if (lhs == rhs) {
/*  905 */       return this;
/*      */     }
/*  907 */     if (lhs == null) {
/*  908 */       this.comparison = -1;
/*  909 */       return this;
/*      */     } 
/*  911 */     if (rhs == null) {
/*  912 */       this.comparison = 1;
/*  913 */       return this;
/*      */     } 
/*  915 */     if (lhs.length != rhs.length) {
/*  916 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  917 */       return this;
/*      */     } 
/*  919 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  920 */       append(lhs[i], rhs[i]);
/*      */     }
/*  922 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(float[] lhs, float[] rhs) {
/*  941 */     if (this.comparison != 0) {
/*  942 */       return this;
/*      */     }
/*  944 */     if (lhs == rhs) {
/*  945 */       return this;
/*      */     }
/*  947 */     if (lhs == null) {
/*  948 */       this.comparison = -1;
/*  949 */       return this;
/*      */     } 
/*  951 */     if (rhs == null) {
/*  952 */       this.comparison = 1;
/*  953 */       return this;
/*      */     } 
/*  955 */     if (lhs.length != rhs.length) {
/*  956 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  957 */       return this;
/*      */     } 
/*  959 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/*  960 */       append(lhs[i], rhs[i]);
/*      */     }
/*  962 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompareToBuilder append(boolean[] lhs, boolean[] rhs) {
/*  981 */     if (this.comparison != 0) {
/*  982 */       return this;
/*      */     }
/*  984 */     if (lhs == rhs) {
/*  985 */       return this;
/*      */     }
/*  987 */     if (lhs == null) {
/*  988 */       this.comparison = -1;
/*  989 */       return this;
/*      */     } 
/*  991 */     if (rhs == null) {
/*  992 */       this.comparison = 1;
/*  993 */       return this;
/*      */     } 
/*  995 */     if (lhs.length != rhs.length) {
/*  996 */       this.comparison = (lhs.length < rhs.length) ? -1 : 1;
/*  997 */       return this;
/*      */     } 
/*  999 */     for (int i = 0; i < lhs.length && this.comparison == 0; i++) {
/* 1000 */       append(lhs[i], rhs[i]);
/*      */     }
/* 1002 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int toComparison() {
/* 1016 */     return this.comparison;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer build() {
/* 1031 */     return Integer.valueOf(toComparison());
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\CompareToBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */