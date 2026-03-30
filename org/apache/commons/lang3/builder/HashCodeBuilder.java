/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashCodeBuilder
/*     */   implements Builder<Integer>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_VALUE = 17;
/*     */   private static final int DEFAULT_MULTIPLIER_VALUE = 37;
/* 123 */   private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int iConstant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Set<IDKey> getRegistry() {
/* 151 */     return REGISTRY.get();
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
/*     */   static boolean isRegistered(Object value) {
/* 166 */     Set<IDKey> registry = getRegistry();
/* 167 */     return (registry != null && registry.contains(new IDKey(value)));
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
/*     */   private static void reflectionAppend(Object object, Class<?> clazz, HashCodeBuilder builder, boolean useTransients, String[] excludeFields) {
/* 188 */     if (isRegistered(object)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 192 */       register(object);
/*     */       
/* 194 */       Field[] fields = clazz.getDeclaredFields();
/* 195 */       Arrays.sort(fields, Comparator.comparing(Field::getName));
/* 196 */       AccessibleObject.setAccessible((AccessibleObject[])fields, true);
/* 197 */       for (Field field : fields) {
/* 198 */         if (!ArrayUtils.contains((Object[])excludeFields, field.getName()) && 
/* 199 */           !field.getName().contains("$") && (useTransients || 
/* 200 */           !Modifier.isTransient(field.getModifiers())) && 
/* 201 */           !Modifier.isStatic(field.getModifiers()) && 
/* 202 */           !field.isAnnotationPresent((Class)HashCodeExclude.class)) {
/*     */           try {
/* 204 */             Object fieldValue = field.get(object);
/* 205 */             builder.append(fieldValue);
/* 206 */           } catch (IllegalAccessException e) {
/*     */ 
/*     */             
/* 209 */             throw new InternalError("Unexpected IllegalAccessException");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } finally {
/* 214 */       unregister(object);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object) {
/* 259 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, false, null, new String[0]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients) {
/* 306 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, null, new String[0]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, T object, boolean testTransients, Class<? super T> reflectUpToClass, String... excludeFields) {
/* 361 */     Validate.notNull(object, "The object to build a hash code for must not be null", new Object[0]);
/* 362 */     HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
/* 363 */     Class<?> clazz = object.getClass();
/* 364 */     reflectionAppend(object, clazz, builder, testTransients, excludeFields);
/* 365 */     while (clazz.getSuperclass() != null && clazz != reflectUpToClass) {
/* 366 */       clazz = clazz.getSuperclass();
/* 367 */       reflectionAppend(object, clazz, builder, testTransients, excludeFields);
/*     */     } 
/* 369 */     return builder.toHashCode();
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
/*     */   public static int reflectionHashCode(Object object, boolean testTransients) {
/* 408 */     return reflectionHashCode(17, 37, object, testTransients, null, new String[0]);
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
/*     */   public static int reflectionHashCode(Object object, Collection<String> excludeFields) {
/* 448 */     return reflectionHashCode(object, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
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
/*     */   
/*     */   public static int reflectionHashCode(Object object, String... excludeFields) {
/* 489 */     return reflectionHashCode(17, 37, object, false, null, excludeFields);
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
/*     */   private static void register(Object value) {
/* 502 */     Set<IDKey> registry = getRegistry();
/* 503 */     if (registry == null) {
/* 504 */       registry = new HashSet<>();
/* 505 */       REGISTRY.set(registry);
/*     */     } 
/* 507 */     registry.add(new IDKey(value));
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
/*     */   private static void unregister(Object value) {
/* 523 */     Set<IDKey> registry = getRegistry();
/* 524 */     if (registry != null) {
/* 525 */       registry.remove(new IDKey(value));
/* 526 */       if (registry.isEmpty()) {
/* 527 */         REGISTRY.remove();
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
/* 540 */   private int iTotal = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashCodeBuilder() {
/* 548 */     this.iConstant = 37;
/* 549 */     this.iTotal = 17;
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
/*     */   public HashCodeBuilder(int initialOddNumber, int multiplierOddNumber) {
/* 570 */     Validate.isTrue((initialOddNumber % 2 != 0), "HashCodeBuilder requires an odd initial value", new Object[0]);
/* 571 */     Validate.isTrue((multiplierOddNumber % 2 != 0), "HashCodeBuilder requires an odd multiplier", new Object[0]);
/* 572 */     this.iConstant = multiplierOddNumber;
/* 573 */     this.iTotal = initialOddNumber;
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
/*     */   public HashCodeBuilder append(boolean value) {
/* 598 */     this.iTotal = this.iTotal * this.iConstant + (value ? 0 : 1);
/* 599 */     return this;
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
/*     */   public HashCodeBuilder append(boolean[] array) {
/* 612 */     if (array == null) {
/* 613 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 615 */       for (boolean element : array) {
/* 616 */         append(element);
/*     */       }
/*     */     } 
/* 619 */     return this;
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
/*     */   public HashCodeBuilder append(byte value) {
/* 634 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 635 */     return this;
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
/*     */   public HashCodeBuilder append(byte[] array) {
/* 650 */     if (array == null) {
/* 651 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 653 */       for (byte element : array) {
/* 654 */         append(element);
/*     */       }
/*     */     } 
/* 657 */     return this;
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
/*     */   public HashCodeBuilder append(char value) {
/* 670 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 671 */     return this;
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
/*     */   public HashCodeBuilder append(char[] array) {
/* 684 */     if (array == null) {
/* 685 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 687 */       for (char element : array) {
/* 688 */         append(element);
/*     */       }
/*     */     } 
/* 691 */     return this;
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
/*     */   public HashCodeBuilder append(double value) {
/* 704 */     return append(Double.doubleToLongBits(value));
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
/*     */   public HashCodeBuilder append(double[] array) {
/* 717 */     if (array == null) {
/* 718 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 720 */       for (double element : array) {
/* 721 */         append(element);
/*     */       }
/*     */     } 
/* 724 */     return this;
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
/*     */   public HashCodeBuilder append(float value) {
/* 737 */     this.iTotal = this.iTotal * this.iConstant + Float.floatToIntBits(value);
/* 738 */     return this;
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
/*     */   public HashCodeBuilder append(float[] array) {
/* 751 */     if (array == null) {
/* 752 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 754 */       for (float element : array) {
/* 755 */         append(element);
/*     */       }
/*     */     } 
/* 758 */     return this;
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
/*     */   public HashCodeBuilder append(int value) {
/* 771 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 772 */     return this;
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
/*     */   public HashCodeBuilder append(int[] array) {
/* 785 */     if (array == null) {
/* 786 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 788 */       for (int element : array) {
/* 789 */         append(element);
/*     */       }
/*     */     } 
/* 792 */     return this;
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
/*     */   public HashCodeBuilder append(long value) {
/* 809 */     this.iTotal = this.iTotal * this.iConstant + (int)(value ^ value >> 32L);
/* 810 */     return this;
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
/*     */   public HashCodeBuilder append(long[] array) {
/* 823 */     if (array == null) {
/* 824 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 826 */       for (long element : array) {
/* 827 */         append(element);
/*     */       }
/*     */     } 
/* 830 */     return this;
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
/*     */   public HashCodeBuilder append(Object object) {
/* 843 */     if (object == null) {
/* 844 */       this.iTotal *= this.iConstant;
/*     */     
/*     */     }
/* 847 */     else if (object.getClass().isArray()) {
/*     */ 
/*     */       
/* 850 */       appendArray(object);
/*     */     } else {
/* 852 */       this.iTotal = this.iTotal * this.iConstant + object.hashCode();
/*     */     } 
/*     */     
/* 855 */     return this;
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
/*     */   private void appendArray(Object object) {
/* 869 */     if (object instanceof long[]) {
/* 870 */       append((long[])object);
/* 871 */     } else if (object instanceof int[]) {
/* 872 */       append((int[])object);
/* 873 */     } else if (object instanceof short[]) {
/* 874 */       append((short[])object);
/* 875 */     } else if (object instanceof char[]) {
/* 876 */       append((char[])object);
/* 877 */     } else if (object instanceof byte[]) {
/* 878 */       append((byte[])object);
/* 879 */     } else if (object instanceof double[]) {
/* 880 */       append((double[])object);
/* 881 */     } else if (object instanceof float[]) {
/* 882 */       append((float[])object);
/* 883 */     } else if (object instanceof boolean[]) {
/* 884 */       append((boolean[])object);
/*     */     } else {
/*     */       
/* 887 */       append((Object[])object);
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
/*     */   public HashCodeBuilder append(Object[] array) {
/* 901 */     if (array == null) {
/* 902 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 904 */       for (Object element : array) {
/* 905 */         append(element);
/*     */       }
/*     */     } 
/* 908 */     return this;
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
/*     */   public HashCodeBuilder append(short value) {
/* 921 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 922 */     return this;
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
/*     */   public HashCodeBuilder append(short[] array) {
/* 935 */     if (array == null) {
/* 936 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 938 */       for (short element : array) {
/* 939 */         append(element);
/*     */       }
/*     */     } 
/* 942 */     return this;
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
/*     */   public HashCodeBuilder appendSuper(int superHashCode) {
/* 956 */     this.iTotal = this.iTotal * this.iConstant + superHashCode;
/* 957 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int toHashCode() {
/* 968 */     return this.iTotal;
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
/*     */   public Integer build() {
/* 980 */     return Integer.valueOf(toHashCode());
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
/*     */   public int hashCode() {
/* 994 */     return toHashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\HashCodeBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */