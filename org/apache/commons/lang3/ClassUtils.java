/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.mutable.MutableObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassUtils
/*      */ {
/*      */   public static final char PACKAGE_SEPARATOR_CHAR = '.';
/*      */   
/*      */   public enum Interfaces
/*      */   {
/*   55 */     INCLUDE,
/*      */ 
/*      */     
/*   58 */     EXCLUDE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final String PACKAGE_SEPARATOR = String.valueOf('.');
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   private static final Map<String, Class<?>> namePrimitiveMap = new HashMap<>();
/*      */   static {
/*   86 */     namePrimitiveMap.put("boolean", boolean.class);
/*   87 */     namePrimitiveMap.put("byte", byte.class);
/*   88 */     namePrimitiveMap.put("char", char.class);
/*   89 */     namePrimitiveMap.put("short", short.class);
/*   90 */     namePrimitiveMap.put("int", int.class);
/*   91 */     namePrimitiveMap.put("long", long.class);
/*   92 */     namePrimitiveMap.put("double", double.class);
/*   93 */     namePrimitiveMap.put("float", float.class);
/*   94 */     namePrimitiveMap.put("void", void.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();
/*      */   static {
/*  102 */     primitiveWrapperMap.put(boolean.class, Boolean.class);
/*  103 */     primitiveWrapperMap.put(byte.class, Byte.class);
/*  104 */     primitiveWrapperMap.put(char.class, Character.class);
/*  105 */     primitiveWrapperMap.put(short.class, Short.class);
/*  106 */     primitiveWrapperMap.put(int.class, Integer.class);
/*  107 */     primitiveWrapperMap.put(long.class, Long.class);
/*  108 */     primitiveWrapperMap.put(double.class, Double.class);
/*  109 */     primitiveWrapperMap.put(float.class, Float.class);
/*  110 */     primitiveWrapperMap.put(void.class, void.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();
/*      */   static {
/*  118 */     for (Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperMap.entrySet()) {
/*  119 */       Class<?> primitiveClass = entry.getKey();
/*  120 */       Class<?> wrapperClass = entry.getValue();
/*  121 */       if (!primitiveClass.equals(wrapperClass)) {
/*  122 */         wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  138 */     Map<String, String> m = new HashMap<>();
/*  139 */     m.put("int", "I");
/*  140 */     m.put("boolean", "Z");
/*  141 */     m.put("float", "F");
/*  142 */     m.put("long", "J");
/*  143 */     m.put("short", "S");
/*  144 */     m.put("byte", "B");
/*  145 */     m.put("double", "D");
/*  146 */     m.put("char", "C");
/*  147 */     Map<String, String> r = new HashMap<>();
/*  148 */     for (Map.Entry<String, String> e : m.entrySet()) {
/*  149 */       r.put(e.getValue(), e.getKey());
/*      */     }
/*  151 */     abbreviationMap = Collections.unmodifiableMap(m);
/*  152 */     reverseAbbreviationMap = Collections.unmodifiableMap(r);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Map<String, String> abbreviationMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Map<String, String> reverseAbbreviationMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(Object object, String valueIfNull) {
/*  181 */     if (object == null) {
/*  182 */       return valueIfNull;
/*      */     }
/*  184 */     return getShortClassName(object.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(Class<?> cls) {
/*  199 */     if (cls == null) {
/*  200 */       return "";
/*      */     }
/*  202 */     return getShortClassName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(String className) {
/*  231 */     if (StringUtils.isEmpty(className)) {
/*  232 */       return "";
/*      */     }
/*      */     
/*  235 */     StringBuilder arrayPrefix = new StringBuilder();
/*      */ 
/*      */     
/*  238 */     if (className.startsWith("[")) {
/*  239 */       while (className.charAt(0) == '[') {
/*  240 */         className = className.substring(1);
/*  241 */         arrayPrefix.append("[]");
/*      */       } 
/*      */       
/*  244 */       if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
/*  245 */         className = className.substring(1, className.length() - 1);
/*      */       }
/*      */       
/*  248 */       if (reverseAbbreviationMap.containsKey(className)) {
/*  249 */         className = reverseAbbreviationMap.get(className);
/*      */       }
/*      */     } 
/*      */     
/*  253 */     int lastDotIdx = className.lastIndexOf('.');
/*  254 */     int innerIdx = className.indexOf('$', (lastDotIdx == -1) ? 0 : (lastDotIdx + 1));
/*      */     
/*  256 */     String out = className.substring(lastDotIdx + 1);
/*  257 */     if (innerIdx != -1) {
/*  258 */       out = out.replace('$', '.');
/*      */     }
/*  260 */     return out + arrayPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Class<?> cls) {
/*  272 */     return getSimpleName(cls, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Class<?> cls, String valueIfNull) {
/*  286 */     return (cls == null) ? valueIfNull : cls.getSimpleName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Object object) {
/*  304 */     return getSimpleName(object, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Object object, String valueIfNull) {
/*  318 */     return (object == null) ? valueIfNull : object.getClass().getSimpleName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getName(Class<?> cls) {
/*  330 */     return getName(cls, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getName(Class<?> cls, String valueIfNull) {
/*  343 */     return (cls == null) ? valueIfNull : cls.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getName(Object object) {
/*  355 */     return getName(object, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getName(Object object, String valueIfNull) {
/*  368 */     return (object == null) ? valueIfNull : object.getClass().getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(Object object, String valueIfNull) {
/*  381 */     if (object == null) {
/*  382 */       return valueIfNull;
/*      */     }
/*  384 */     return getPackageName(object.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(Class<?> cls) {
/*  394 */     if (cls == null) {
/*  395 */       return "";
/*      */     }
/*  397 */     return getPackageName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(String className) {
/*  410 */     if (StringUtils.isEmpty(className)) {
/*  411 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  415 */     while (className.charAt(0) == '[') {
/*  416 */       className = className.substring(1);
/*      */     }
/*      */     
/*  419 */     if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
/*  420 */       className = className.substring(1);
/*      */     }
/*      */     
/*  423 */     int i = className.lastIndexOf('.');
/*  424 */     if (i == -1) {
/*  425 */       return "";
/*      */     }
/*  427 */     return className.substring(0, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAbbreviatedName(Class<?> cls, int lengthHint) {
/*  443 */     if (cls == null) {
/*  444 */       return "";
/*      */     }
/*  446 */     return getAbbreviatedName(cls.getName(), lengthHint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAbbreviatedName(String className, int lengthHint) {
/*  490 */     if (lengthHint <= 0) {
/*  491 */       throw new IllegalArgumentException("len must be > 0");
/*      */     }
/*  493 */     if (className == null) {
/*  494 */       return "";
/*      */     }
/*  496 */     if (className.length() <= lengthHint) {
/*  497 */       return className;
/*      */     }
/*  499 */     char[] abbreviated = className.toCharArray();
/*  500 */     int target = 0;
/*  501 */     int source = 0;
/*  502 */     while (source < abbreviated.length) {
/*      */       
/*  504 */       int runAheadTarget = target;
/*  505 */       while (source < abbreviated.length && abbreviated[source] != '.') {
/*  506 */         abbreviated[runAheadTarget++] = abbreviated[source++];
/*      */       }
/*      */       
/*  509 */       target++;
/*  510 */       if (useFull(runAheadTarget, source, abbreviated.length, lengthHint) || target > runAheadTarget)
/*      */       {
/*  512 */         target = runAheadTarget;
/*      */       }
/*      */ 
/*      */       
/*  516 */       if (source < abbreviated.length) {
/*  517 */         abbreviated[target++] = abbreviated[source++];
/*      */       }
/*      */     } 
/*  520 */     return new String(abbreviated, 0, target);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean useFull(int runAheadTarget, int source, int originalLength, int desiredLength) {
/*  555 */     return (source >= originalLength || runAheadTarget + originalLength - source <= desiredLength);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
/*  569 */     if (cls == null) {
/*  570 */       return null;
/*      */     }
/*  572 */     List<Class<?>> classes = new ArrayList<>();
/*  573 */     Class<?> superclass = cls.getSuperclass();
/*  574 */     while (superclass != null) {
/*  575 */       classes.add(superclass);
/*  576 */       superclass = superclass.getSuperclass();
/*      */     } 
/*  578 */     return classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> getAllInterfaces(Class<?> cls) {
/*  595 */     if (cls == null) {
/*  596 */       return null;
/*      */     }
/*      */     
/*  599 */     LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
/*  600 */     getAllInterfaces(cls, interfacesFound);
/*      */     
/*  602 */     return new ArrayList<>(interfacesFound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound) {
/*  612 */     while (cls != null) {
/*  613 */       Class<?>[] interfaces = cls.getInterfaces();
/*      */       
/*  615 */       for (Class<?> i : interfaces) {
/*  616 */         if (interfacesFound.add(i)) {
/*  617 */           getAllInterfaces(i, interfacesFound);
/*      */         }
/*      */       } 
/*      */       
/*  621 */       cls = cls.getSuperclass();
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
/*      */   public static List<Class<?>> convertClassNamesToClasses(List<String> classNames) {
/*  640 */     if (classNames == null) {
/*  641 */       return null;
/*      */     }
/*  643 */     List<Class<?>> classes = new ArrayList<>(classNames.size());
/*  644 */     for (String className : classNames) {
/*      */       try {
/*  646 */         classes.add(Class.forName(className));
/*  647 */       } catch (Exception ex) {
/*  648 */         classes.add(null);
/*      */       } 
/*      */     } 
/*  651 */     return classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> convertClassesToClassNames(List<Class<?>> classes) {
/*  667 */     if (classes == null) {
/*  668 */       return null;
/*      */     }
/*  670 */     List<String> classNames = new ArrayList<>(classes.size());
/*  671 */     for (Class<?> cls : classes) {
/*  672 */       if (cls == null) {
/*  673 */         classNames.add(null); continue;
/*      */       } 
/*  675 */       classNames.add(cls.getName());
/*      */     } 
/*      */     
/*  678 */     return classNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?>[] classArray, Class<?>... toClassArray) {
/*  720 */     return isAssignable(classArray, toClassArray, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?>[] classArray, Class<?>[] toClassArray, boolean autoboxing) {
/*  756 */     if (!ArrayUtils.isSameLength((Object[])classArray, (Object[])toClassArray)) {
/*  757 */       return false;
/*      */     }
/*  759 */     if (classArray == null) {
/*  760 */       classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  762 */     if (toClassArray == null) {
/*  763 */       toClassArray = ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  765 */     for (int i = 0; i < classArray.length; i++) {
/*  766 */       if (!isAssignable(classArray[i], toClassArray[i], autoboxing)) {
/*  767 */         return false;
/*      */       }
/*      */     } 
/*  770 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrimitiveOrWrapper(Class<?> type) {
/*  784 */     if (type == null) {
/*  785 */       return false;
/*      */     }
/*  787 */     return (type.isPrimitive() || isPrimitiveWrapper(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrimitiveWrapper(Class<?> type) {
/*  801 */     return wrapperPrimitiveMap.containsKey(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
/*  836 */     return isAssignable(cls, toClass, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?> cls, Class<?> toClass, boolean autoboxing) {
/*  867 */     if (toClass == null) {
/*  868 */       return false;
/*      */     }
/*      */     
/*  871 */     if (cls == null) {
/*  872 */       return !toClass.isPrimitive();
/*      */     }
/*      */     
/*  875 */     if (autoboxing) {
/*  876 */       if (cls.isPrimitive() && !toClass.isPrimitive()) {
/*  877 */         cls = primitiveToWrapper(cls);
/*  878 */         if (cls == null) {
/*  879 */           return false;
/*      */         }
/*      */       } 
/*  882 */       if (toClass.isPrimitive() && !cls.isPrimitive()) {
/*  883 */         cls = wrapperToPrimitive(cls);
/*  884 */         if (cls == null) {
/*  885 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*  889 */     if (cls.equals(toClass)) {
/*  890 */       return true;
/*      */     }
/*  892 */     if (cls.isPrimitive()) {
/*  893 */       if (!toClass.isPrimitive()) {
/*  894 */         return false;
/*      */       }
/*  896 */       if (int.class.equals(cls)) {
/*  897 */         return (long.class.equals(toClass) || float.class
/*  898 */           .equals(toClass) || double.class
/*  899 */           .equals(toClass));
/*      */       }
/*  901 */       if (long.class.equals(cls)) {
/*  902 */         return (float.class.equals(toClass) || double.class
/*  903 */           .equals(toClass));
/*      */       }
/*  905 */       if (boolean.class.equals(cls)) {
/*  906 */         return false;
/*      */       }
/*  908 */       if (double.class.equals(cls)) {
/*  909 */         return false;
/*      */       }
/*  911 */       if (float.class.equals(cls)) {
/*  912 */         return double.class.equals(toClass);
/*      */       }
/*  914 */       if (char.class.equals(cls)) {
/*  915 */         return (int.class.equals(toClass) || long.class
/*  916 */           .equals(toClass) || float.class
/*  917 */           .equals(toClass) || double.class
/*  918 */           .equals(toClass));
/*      */       }
/*  920 */       if (short.class.equals(cls)) {
/*  921 */         return (int.class.equals(toClass) || long.class
/*  922 */           .equals(toClass) || float.class
/*  923 */           .equals(toClass) || double.class
/*  924 */           .equals(toClass));
/*      */       }
/*  926 */       if (byte.class.equals(cls)) {
/*  927 */         return (short.class.equals(toClass) || int.class
/*  928 */           .equals(toClass) || long.class
/*  929 */           .equals(toClass) || float.class
/*  930 */           .equals(toClass) || double.class
/*  931 */           .equals(toClass));
/*      */       }
/*      */       
/*  934 */       return false;
/*      */     } 
/*  936 */     return toClass.isAssignableFrom(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> primitiveToWrapper(Class<?> cls) {
/*  952 */     Class<?> convertedClass = cls;
/*  953 */     if (cls != null && cls.isPrimitive()) {
/*  954 */       convertedClass = primitiveWrapperMap.get(cls);
/*      */     }
/*  956 */     return convertedClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?>[] primitivesToWrappers(Class<?>... classes) {
/*  970 */     if (classes == null) {
/*  971 */       return null;
/*      */     }
/*      */     
/*  974 */     if (classes.length == 0) {
/*  975 */       return classes;
/*      */     }
/*      */     
/*  978 */     Class<?>[] convertedClasses = new Class[classes.length];
/*  979 */     for (int i = 0; i < classes.length; i++) {
/*  980 */       convertedClasses[i] = primitiveToWrapper(classes[i]);
/*      */     }
/*  982 */     return convertedClasses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> wrapperToPrimitive(Class<?> cls) {
/* 1002 */     return wrapperPrimitiveMap.get(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?>[] wrappersToPrimitives(Class<?>... classes) {
/* 1020 */     if (classes == null) {
/* 1021 */       return null;
/*      */     }
/*      */     
/* 1024 */     if (classes.length == 0) {
/* 1025 */       return classes;
/*      */     }
/*      */     
/* 1028 */     Class<?>[] convertedClasses = new Class[classes.length];
/* 1029 */     for (int i = 0; i < classes.length; i++) {
/* 1030 */       convertedClasses[i] = wrapperToPrimitive(classes[i]);
/*      */     }
/* 1032 */     return convertedClasses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInnerClass(Class<?> cls) {
/* 1045 */     return (cls != null && cls.getEnclosingClass() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(ClassLoader classLoader, String className, boolean initialize) throws ClassNotFoundException {
/*      */     try {
/*      */       Class<?> clazz;
/* 1066 */       if (namePrimitiveMap.containsKey(className)) {
/* 1067 */         clazz = namePrimitiveMap.get(className);
/*      */       } else {
/* 1069 */         clazz = Class.forName(toCanonicalName(className), initialize, classLoader);
/*      */       } 
/* 1071 */       return clazz;
/* 1072 */     } catch (ClassNotFoundException ex) {
/*      */       
/* 1074 */       int lastDotIndex = className.lastIndexOf('.');
/*      */       
/* 1076 */       if (lastDotIndex != -1) {
/*      */         try {
/* 1078 */           return getClass(classLoader, className.substring(0, lastDotIndex) + '$' + className
/* 1079 */               .substring(lastDotIndex + 1), initialize);
/*      */         }
/* 1081 */         catch (ClassNotFoundException classNotFoundException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1086 */       throw ex;
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
/*      */   public static Class<?> getClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
/* 1103 */     return getClass(classLoader, className, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(String className) throws ClassNotFoundException {
/* 1118 */     return getClass(className, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(String className, boolean initialize) throws ClassNotFoundException {
/* 1133 */     ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
/* 1134 */     ClassLoader loader = (contextCL == null) ? ClassUtils.class.getClassLoader() : contextCL;
/* 1135 */     return getClass(loader, className, initialize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method getPublicMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
/* 1165 */     Method declaredMethod = cls.getMethod(methodName, parameterTypes);
/* 1166 */     if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
/* 1167 */       return declaredMethod;
/*      */     }
/*      */     
/* 1170 */     List<Class<?>> candidateClasses = new ArrayList<>();
/* 1171 */     candidateClasses.addAll(getAllInterfaces(cls));
/* 1172 */     candidateClasses.addAll(getAllSuperclasses(cls));
/*      */     
/* 1174 */     for (Class<?> candidateClass : candidateClasses) {
/* 1175 */       Method candidateMethod; if (!Modifier.isPublic(candidateClass.getModifiers())) {
/*      */         continue;
/*      */       }
/*      */       
/*      */       try {
/* 1180 */         candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
/* 1181 */       } catch (NoSuchMethodException ex) {
/*      */         continue;
/*      */       } 
/* 1184 */       if (Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) {
/* 1185 */         return candidateMethod;
/*      */       }
/*      */     } 
/*      */     
/* 1189 */     throw new NoSuchMethodException("Can't find a public method for " + methodName + " " + 
/* 1190 */         ArrayUtils.toString(parameterTypes));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toCanonicalName(String className) {
/* 1201 */     className = StringUtils.deleteWhitespace(className);
/* 1202 */     Validate.notNull(className, "className must not be null.", new Object[0]);
/* 1203 */     if (className.endsWith("[]")) {
/* 1204 */       StringBuilder classNameBuffer = new StringBuilder();
/* 1205 */       while (className.endsWith("[]")) {
/* 1206 */         className = className.substring(0, className.length() - 2);
/* 1207 */         classNameBuffer.append("[");
/*      */       } 
/* 1209 */       String abbreviation = abbreviationMap.get(className);
/* 1210 */       if (abbreviation != null) {
/* 1211 */         classNameBuffer.append(abbreviation);
/*      */       } else {
/* 1213 */         classNameBuffer.append("L").append(className).append(";");
/*      */       } 
/* 1215 */       className = classNameBuffer.toString();
/*      */     } 
/* 1217 */     return className;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?>[] toClass(Object... array) {
/* 1231 */     if (array == null)
/* 1232 */       return null; 
/* 1233 */     if (array.length == 0) {
/* 1234 */       return ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/* 1236 */     Class<?>[] classes = new Class[array.length];
/* 1237 */     for (int i = 0; i < array.length; i++) {
/* 1238 */       classes[i] = (array[i] == null) ? null : array[i].getClass();
/*      */     }
/* 1240 */     return classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortCanonicalName(Object object, String valueIfNull) {
/* 1254 */     if (object == null) {
/* 1255 */       return valueIfNull;
/*      */     }
/* 1257 */     return getShortCanonicalName(object.getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCanonicalName(Class<?> cls) {
/* 1269 */     return getCanonicalName(cls, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCanonicalName(Class<?> cls, String valueIfNull) {
/* 1282 */     if (cls == null) {
/* 1283 */       return valueIfNull;
/*      */     }
/* 1285 */     String canonicalName = cls.getCanonicalName();
/* 1286 */     return (canonicalName == null) ? valueIfNull : canonicalName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCanonicalName(Object object) {
/* 1298 */     return getCanonicalName(object, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCanonicalName(Object object, String valueIfNull) {
/* 1311 */     if (object == null) {
/* 1312 */       return valueIfNull;
/*      */     }
/* 1314 */     String canonicalName = object.getClass().getCanonicalName();
/* 1315 */     return (canonicalName == null) ? valueIfNull : canonicalName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortCanonicalName(Class<?> cls) {
/* 1326 */     if (cls == null) {
/* 1327 */       return "";
/*      */     }
/* 1329 */     return getShortCanonicalName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortCanonicalName(String canonicalName) {
/* 1376 */     return getShortClassName(getCanonicalName(canonicalName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageCanonicalName(Object object, String valueIfNull) {
/* 1390 */     if (object == null) {
/* 1391 */       return valueIfNull;
/*      */     }
/* 1393 */     return getPackageCanonicalName(object.getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageCanonicalName(Class<?> cls) {
/* 1404 */     if (cls == null) {
/* 1405 */       return "";
/*      */     }
/* 1407 */     return getPackageCanonicalName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageCanonicalName(String name) {
/* 1421 */     return getPackageName(getCanonicalName(name));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getCanonicalName(String className) {
/* 1445 */     className = StringUtils.deleteWhitespace(className);
/* 1446 */     if (className == null) {
/* 1447 */       return null;
/*      */     }
/* 1449 */     int dim = 0;
/* 1450 */     while (className.startsWith("[")) {
/* 1451 */       dim++;
/* 1452 */       className = className.substring(1);
/*      */     } 
/* 1454 */     if (dim < 1) {
/* 1455 */       return className;
/*      */     }
/* 1457 */     if (className.startsWith("L")) {
/* 1458 */       className = className.substring(1, 
/*      */           
/* 1460 */           className.endsWith(";") ? (className
/* 1461 */           .length() - 1) : className
/* 1462 */           .length());
/*      */     }
/* 1464 */     else if (!className.isEmpty()) {
/* 1465 */       className = reverseAbbreviationMap.get(className.substring(0, 1));
/*      */     } 
/*      */     
/* 1468 */     StringBuilder canonicalClassNameBuffer = new StringBuilder(className);
/* 1469 */     for (int i = 0; i < dim; i++) {
/* 1470 */       canonicalClassNameBuffer.append("[]");
/*      */     }
/* 1472 */     return canonicalClassNameBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterable<Class<?>> hierarchy(Class<?> type) {
/* 1484 */     return hierarchy(type, Interfaces.EXCLUDE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterable<Class<?>> hierarchy(Class<?> type, Interfaces interfacesBehavior) {
/* 1496 */     Iterable<Class<?>> classes = () -> {
/*      */         MutableObject<Class<?>> next = new MutableObject(type);
/*      */         return new Iterator<Class<?>>()
/*      */           {
/*      */             public boolean hasNext()
/*      */             {
/* 1502 */               return (next.getValue() != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public Class<?> next() {
/* 1507 */               Class<?> result = (Class)next.getValue();
/* 1508 */               next.setValue(result.getSuperclass());
/* 1509 */               return result;
/*      */             }
/*      */ 
/*      */             
/*      */             public void remove() {
/* 1514 */               throw new UnsupportedOperationException();
/*      */             }
/*      */           };
/*      */       };
/*      */     
/* 1519 */     if (interfacesBehavior != Interfaces.INCLUDE) {
/* 1520 */       return classes;
/*      */     }
/* 1522 */     return () -> {
/*      */         final Set<Class<?>> seenInterfaces = new HashSet<>();
/*      */         final Iterator<Class<?>> wrapped = classes.iterator();
/*      */         return new Iterator<Class<?>>()
/*      */           {
/* 1527 */             Iterator interfaces = Collections.emptySet().iterator();
/*      */ 
/*      */             
/*      */             public boolean hasNext() {
/* 1531 */               return (this.interfaces.hasNext() || wrapped.hasNext());
/*      */             }
/*      */ 
/*      */             
/*      */             public Class<?> next() {
/* 1536 */               if (this.interfaces.hasNext()) {
/* 1537 */                 Class<?> nextInterface = this.interfaces.next();
/* 1538 */                 seenInterfaces.add(nextInterface);
/* 1539 */                 return nextInterface;
/*      */               } 
/* 1541 */               Class<?> nextSuperclass = wrapped.next();
/* 1542 */               Set<Class<?>> currentInterfaces = new LinkedHashSet();
/* 1543 */               walkInterfaces(currentInterfaces, nextSuperclass);
/* 1544 */               this.interfaces = currentInterfaces.iterator();
/* 1545 */               return nextSuperclass;
/*      */             }
/*      */             
/*      */             private void walkInterfaces(Set<Class<?>> addTo, Class c) {
/* 1549 */               for (Class<?> iface : c.getInterfaces()) {
/* 1550 */                 if (!seenInterfaces.contains(iface)) {
/* 1551 */                   addTo.add(iface);
/*      */                 }
/* 1553 */                 walkInterfaces(addTo, iface);
/*      */               } 
/*      */             }
/*      */ 
/*      */             
/*      */             public void remove() {
/* 1559 */               throw new UnsupportedOperationException();
/*      */             }
/*      */           };
/*      */       };
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\ClassUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */