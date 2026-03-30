/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import java.lang.reflect.AccessibleObject;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.commons.lang3.ClassUtils;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EqualsBuilder
/*      */   implements Builder<Boolean>
/*      */ {
/*   98 */   private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Set<Pair<IDKey, IDKey>> getRegistry() {
/*  127 */     return REGISTRY.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Pair<IDKey, IDKey> getRegisterPair(Object lhs, Object rhs) {
/*  141 */     IDKey left = new IDKey(lhs);
/*  142 */     IDKey right = new IDKey(rhs);
/*  143 */     return Pair.of(left, right);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isRegistered(Object lhs, Object rhs) {
/*  160 */     Set<Pair<IDKey, IDKey>> registry = getRegistry();
/*  161 */     Pair<IDKey, IDKey> pair = getRegisterPair(lhs, rhs);
/*  162 */     Pair<IDKey, IDKey> swappedPair = Pair.of(pair.getRight(), pair.getLeft());
/*      */     
/*  164 */     return (registry != null && (registry
/*  165 */       .contains(pair) || registry.contains(swappedPair)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void register(Object lhs, Object rhs) {
/*  178 */     Set<Pair<IDKey, IDKey>> registry = getRegistry();
/*  179 */     if (registry == null) {
/*  180 */       registry = new HashSet<>();
/*  181 */       REGISTRY.set(registry);
/*      */     } 
/*  183 */     Pair<IDKey, IDKey> pair = getRegisterPair(lhs, rhs);
/*  184 */     registry.add(pair);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void unregister(Object lhs, Object rhs) {
/*  200 */     Set<Pair<IDKey, IDKey>> registry = getRegistry();
/*  201 */     if (registry != null) {
/*  202 */       Pair<IDKey, IDKey> pair = getRegisterPair(lhs, rhs);
/*  203 */       registry.remove(pair);
/*  204 */       if (registry.isEmpty()) {
/*  205 */         REGISTRY.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isEquals = true;
/*      */   
/*      */   private boolean testTransients = false;
/*      */   
/*      */   private boolean testRecursive = false;
/*      */   
/*      */   private List<Class<?>> bypassReflectionClasses;
/*      */   
/*  219 */   private Class<?> reflectUpToClass = null;
/*  220 */   private String[] excludeFields = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder() {
/*  230 */     this.bypassReflectionClasses = new ArrayList<>();
/*  231 */     this.bypassReflectionClasses.add(String.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder setTestTransients(boolean testTransients) {
/*  243 */     this.testTransients = testTransients;
/*  244 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder setTestRecursive(boolean testRecursive) {
/*  257 */     this.testRecursive = testRecursive;
/*  258 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder setBypassReflectionClasses(List<Class<?>> bypassReflectionClasses) {
/*  275 */     this.bypassReflectionClasses = bypassReflectionClasses;
/*  276 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder setReflectUpToClass(Class<?> reflectUpToClass) {
/*  286 */     this.reflectUpToClass = reflectUpToClass;
/*  287 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder setExcludeFields(String... excludeFields) {
/*  297 */     this.excludeFields = excludeFields;
/*  298 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean reflectionEquals(Object lhs, Object rhs, Collection<String> excludeFields) {
/*  325 */     return reflectionEquals(lhs, rhs, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean reflectionEquals(Object lhs, Object rhs, String... excludeFields) {
/*  351 */     return reflectionEquals(lhs, rhs, false, null, excludeFields);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients) {
/*  378 */     return reflectionEquals(lhs, rhs, testTransients, null, new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients, Class<?> reflectUpToClass, String... excludeFields) {
/*  412 */     return reflectionEquals(lhs, rhs, testTransients, reflectUpToClass, false, excludeFields);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients, Class<?> reflectUpToClass, boolean testRecursive, String... excludeFields) {
/*  453 */     if (lhs == rhs) {
/*  454 */       return true;
/*      */     }
/*  456 */     if (lhs == null || rhs == null) {
/*  457 */       return false;
/*      */     }
/*  459 */     return (new EqualsBuilder())
/*  460 */       .setExcludeFields(excludeFields)
/*  461 */       .setReflectUpToClass(reflectUpToClass)
/*  462 */       .setTestTransients(testTransients)
/*  463 */       .setTestRecursive(testRecursive)
/*  464 */       .reflectionAppend(lhs, rhs)
/*  465 */       .isEquals();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder reflectionAppend(Object lhs, Object rhs) {
/*      */     Class<?> testClass;
/*  496 */     if (!this.isEquals) {
/*  497 */       return this;
/*      */     }
/*  499 */     if (lhs == rhs) {
/*  500 */       return this;
/*      */     }
/*  502 */     if (lhs == null || rhs == null) {
/*  503 */       this.isEquals = false;
/*  504 */       return this;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  511 */     Class<?> lhsClass = lhs.getClass();
/*  512 */     Class<?> rhsClass = rhs.getClass();
/*      */     
/*  514 */     if (lhsClass.isInstance(rhs)) {
/*  515 */       testClass = lhsClass;
/*  516 */       if (!rhsClass.isInstance(lhs))
/*      */       {
/*  518 */         testClass = rhsClass;
/*      */       }
/*  520 */     } else if (rhsClass.isInstance(lhs)) {
/*  521 */       testClass = rhsClass;
/*  522 */       if (!lhsClass.isInstance(rhs))
/*      */       {
/*  524 */         testClass = lhsClass;
/*      */       }
/*      */     } else {
/*      */       
/*  528 */       this.isEquals = false;
/*  529 */       return this;
/*      */     } 
/*      */     
/*      */     try {
/*  533 */       if (testClass.isArray()) {
/*  534 */         append(lhs, rhs);
/*      */       
/*      */       }
/*  537 */       else if (this.bypassReflectionClasses != null && (this.bypassReflectionClasses
/*  538 */         .contains(lhsClass) || this.bypassReflectionClasses.contains(rhsClass))) {
/*  539 */         this.isEquals = lhs.equals(rhs);
/*      */       } else {
/*  541 */         reflectionAppend(lhs, rhs, testClass);
/*  542 */         while (testClass.getSuperclass() != null && testClass != this.reflectUpToClass) {
/*  543 */           testClass = testClass.getSuperclass();
/*  544 */           reflectionAppend(lhs, rhs, testClass);
/*      */         }
/*      */       
/*      */       } 
/*  548 */     } catch (IllegalArgumentException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  554 */       this.isEquals = false;
/*  555 */       return this;
/*      */     } 
/*  557 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reflectionAppend(Object lhs, Object rhs, Class<?> clazz) {
/*  573 */     if (isRegistered(lhs, rhs)) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  578 */       register(lhs, rhs);
/*  579 */       Field[] fields = clazz.getDeclaredFields();
/*  580 */       AccessibleObject.setAccessible((AccessibleObject[])fields, true);
/*  581 */       for (int i = 0; i < fields.length && this.isEquals; i++) {
/*  582 */         Field f = fields[i];
/*  583 */         if (!ArrayUtils.contains((Object[])this.excludeFields, f.getName()) && 
/*  584 */           !f.getName().contains("$") && (this.testTransients || 
/*  585 */           !Modifier.isTransient(f.getModifiers())) && 
/*  586 */           !Modifier.isStatic(f.getModifiers()) && 
/*  587 */           !f.isAnnotationPresent((Class)EqualsExclude.class)) {
/*      */           try {
/*  589 */             append(f.get(lhs), f.get(rhs));
/*  590 */           } catch (IllegalAccessException e) {
/*      */ 
/*      */             
/*  593 */             throw new InternalError("Unexpected IllegalAccessException");
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } finally {
/*  598 */       unregister(lhs, rhs);
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
/*      */   public EqualsBuilder appendSuper(boolean superEquals) {
/*  612 */     if (!this.isEquals) {
/*  613 */       return this;
/*      */     }
/*  615 */     this.isEquals = superEquals;
/*  616 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(Object lhs, Object rhs) {
/*  633 */     if (!this.isEquals) {
/*  634 */       return this;
/*      */     }
/*  636 */     if (lhs == rhs) {
/*  637 */       return this;
/*      */     }
/*  639 */     if (lhs == null || rhs == null) {
/*  640 */       setEquals(false);
/*  641 */       return this;
/*      */     } 
/*  643 */     Class<?> lhsClass = lhs.getClass();
/*  644 */     if (lhsClass.isArray()) {
/*      */ 
/*      */       
/*  647 */       appendArray(lhs, rhs);
/*      */     
/*      */     }
/*  650 */     else if (this.testRecursive && !ClassUtils.isPrimitiveOrWrapper(lhsClass)) {
/*  651 */       reflectionAppend(lhs, rhs);
/*      */     } else {
/*  653 */       this.isEquals = lhs.equals(rhs);
/*      */     } 
/*      */     
/*  656 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendArray(Object lhs, Object rhs) {
/*  669 */     if (lhs.getClass() != rhs.getClass()) {
/*  670 */       setEquals(false);
/*  671 */     } else if (lhs instanceof long[]) {
/*  672 */       append((long[])lhs, (long[])rhs);
/*  673 */     } else if (lhs instanceof int[]) {
/*  674 */       append((int[])lhs, (int[])rhs);
/*  675 */     } else if (lhs instanceof short[]) {
/*  676 */       append((short[])lhs, (short[])rhs);
/*  677 */     } else if (lhs instanceof char[]) {
/*  678 */       append((char[])lhs, (char[])rhs);
/*  679 */     } else if (lhs instanceof byte[]) {
/*  680 */       append((byte[])lhs, (byte[])rhs);
/*  681 */     } else if (lhs instanceof double[]) {
/*  682 */       append((double[])lhs, (double[])rhs);
/*  683 */     } else if (lhs instanceof float[]) {
/*  684 */       append((float[])lhs, (float[])rhs);
/*  685 */     } else if (lhs instanceof boolean[]) {
/*  686 */       append((boolean[])lhs, (boolean[])rhs);
/*      */     } else {
/*      */       
/*  689 */       append((Object[])lhs, (Object[])rhs);
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
/*      */   public EqualsBuilder append(long lhs, long rhs) {
/*  705 */     if (!this.isEquals) {
/*  706 */       return this;
/*      */     }
/*  708 */     this.isEquals = (lhs == rhs);
/*  709 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(int lhs, int rhs) {
/*  720 */     if (!this.isEquals) {
/*  721 */       return this;
/*      */     }
/*  723 */     this.isEquals = (lhs == rhs);
/*  724 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(short lhs, short rhs) {
/*  735 */     if (!this.isEquals) {
/*  736 */       return this;
/*      */     }
/*  738 */     this.isEquals = (lhs == rhs);
/*  739 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(char lhs, char rhs) {
/*  750 */     if (!this.isEquals) {
/*  751 */       return this;
/*      */     }
/*  753 */     this.isEquals = (lhs == rhs);
/*  754 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(byte lhs, byte rhs) {
/*  765 */     if (!this.isEquals) {
/*  766 */       return this;
/*      */     }
/*  768 */     this.isEquals = (lhs == rhs);
/*  769 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(double lhs, double rhs) {
/*  786 */     if (!this.isEquals) {
/*  787 */       return this;
/*      */     }
/*  789 */     return append(Double.doubleToLongBits(lhs), Double.doubleToLongBits(rhs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(float lhs, float rhs) {
/*  806 */     if (!this.isEquals) {
/*  807 */       return this;
/*      */     }
/*  809 */     return append(Float.floatToIntBits(lhs), Float.floatToIntBits(rhs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(boolean lhs, boolean rhs) {
/*  820 */     if (!this.isEquals) {
/*  821 */       return this;
/*      */     }
/*  823 */     this.isEquals = (lhs == rhs);
/*  824 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(Object[] lhs, Object[] rhs) {
/*  841 */     if (!this.isEquals) {
/*  842 */       return this;
/*      */     }
/*  844 */     if (lhs == rhs) {
/*  845 */       return this;
/*      */     }
/*  847 */     if (lhs == null || rhs == null) {
/*  848 */       setEquals(false);
/*  849 */       return this;
/*      */     } 
/*  851 */     if (lhs.length != rhs.length) {
/*  852 */       setEquals(false);
/*  853 */       return this;
/*      */     } 
/*  855 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/*  856 */       append(lhs[i], rhs[i]);
/*      */     }
/*  858 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(long[] lhs, long[] rhs) {
/*  872 */     if (!this.isEquals) {
/*  873 */       return this;
/*      */     }
/*  875 */     if (lhs == rhs) {
/*  876 */       return this;
/*      */     }
/*  878 */     if (lhs == null || rhs == null) {
/*  879 */       setEquals(false);
/*  880 */       return this;
/*      */     } 
/*  882 */     if (lhs.length != rhs.length) {
/*  883 */       setEquals(false);
/*  884 */       return this;
/*      */     } 
/*  886 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/*  887 */       append(lhs[i], rhs[i]);
/*      */     }
/*  889 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(int[] lhs, int[] rhs) {
/*  903 */     if (!this.isEquals) {
/*  904 */       return this;
/*      */     }
/*  906 */     if (lhs == rhs) {
/*  907 */       return this;
/*      */     }
/*  909 */     if (lhs == null || rhs == null) {
/*  910 */       setEquals(false);
/*  911 */       return this;
/*      */     } 
/*  913 */     if (lhs.length != rhs.length) {
/*  914 */       setEquals(false);
/*  915 */       return this;
/*      */     } 
/*  917 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/*  918 */       append(lhs[i], rhs[i]);
/*      */     }
/*  920 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(short[] lhs, short[] rhs) {
/*  934 */     if (!this.isEquals) {
/*  935 */       return this;
/*      */     }
/*  937 */     if (lhs == rhs) {
/*  938 */       return this;
/*      */     }
/*  940 */     if (lhs == null || rhs == null) {
/*  941 */       setEquals(false);
/*  942 */       return this;
/*      */     } 
/*  944 */     if (lhs.length != rhs.length) {
/*  945 */       setEquals(false);
/*  946 */       return this;
/*      */     } 
/*  948 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/*  949 */       append(lhs[i], rhs[i]);
/*      */     }
/*  951 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(char[] lhs, char[] rhs) {
/*  965 */     if (!this.isEquals) {
/*  966 */       return this;
/*      */     }
/*  968 */     if (lhs == rhs) {
/*  969 */       return this;
/*      */     }
/*  971 */     if (lhs == null || rhs == null) {
/*  972 */       setEquals(false);
/*  973 */       return this;
/*      */     } 
/*  975 */     if (lhs.length != rhs.length) {
/*  976 */       setEquals(false);
/*  977 */       return this;
/*      */     } 
/*  979 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/*  980 */       append(lhs[i], rhs[i]);
/*      */     }
/*  982 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(byte[] lhs, byte[] rhs) {
/*  996 */     if (!this.isEquals) {
/*  997 */       return this;
/*      */     }
/*  999 */     if (lhs == rhs) {
/* 1000 */       return this;
/*      */     }
/* 1002 */     if (lhs == null || rhs == null) {
/* 1003 */       setEquals(false);
/* 1004 */       return this;
/*      */     } 
/* 1006 */     if (lhs.length != rhs.length) {
/* 1007 */       setEquals(false);
/* 1008 */       return this;
/*      */     } 
/* 1010 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 1011 */       append(lhs[i], rhs[i]);
/*      */     }
/* 1013 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(double[] lhs, double[] rhs) {
/* 1027 */     if (!this.isEquals) {
/* 1028 */       return this;
/*      */     }
/* 1030 */     if (lhs == rhs) {
/* 1031 */       return this;
/*      */     }
/* 1033 */     if (lhs == null || rhs == null) {
/* 1034 */       setEquals(false);
/* 1035 */       return this;
/*      */     } 
/* 1037 */     if (lhs.length != rhs.length) {
/* 1038 */       setEquals(false);
/* 1039 */       return this;
/*      */     } 
/* 1041 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 1042 */       append(lhs[i], rhs[i]);
/*      */     }
/* 1044 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(float[] lhs, float[] rhs) {
/* 1058 */     if (!this.isEquals) {
/* 1059 */       return this;
/*      */     }
/* 1061 */     if (lhs == rhs) {
/* 1062 */       return this;
/*      */     }
/* 1064 */     if (lhs == null || rhs == null) {
/* 1065 */       setEquals(false);
/* 1066 */       return this;
/*      */     } 
/* 1068 */     if (lhs.length != rhs.length) {
/* 1069 */       setEquals(false);
/* 1070 */       return this;
/*      */     } 
/* 1072 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 1073 */       append(lhs[i], rhs[i]);
/*      */     }
/* 1075 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EqualsBuilder append(boolean[] lhs, boolean[] rhs) {
/* 1089 */     if (!this.isEquals) {
/* 1090 */       return this;
/*      */     }
/* 1092 */     if (lhs == rhs) {
/* 1093 */       return this;
/*      */     }
/* 1095 */     if (lhs == null || rhs == null) {
/* 1096 */       setEquals(false);
/* 1097 */       return this;
/*      */     } 
/* 1099 */     if (lhs.length != rhs.length) {
/* 1100 */       setEquals(false);
/* 1101 */       return this;
/*      */     } 
/* 1103 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 1104 */       append(lhs[i], rhs[i]);
/*      */     }
/* 1106 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEquals() {
/* 1116 */     return this.isEquals;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean build() {
/* 1130 */     return Boolean.valueOf(isEquals());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEquals(boolean isEquals) {
/* 1140 */     this.isEquals = isEquals;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 1148 */     this.isEquals = true;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\builder\EqualsBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */