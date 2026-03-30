/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.TreeSet;
/*      */ import java.util.function.Supplier;
/*      */ import org.apache.commons.lang3.exception.CloneFailedException;
/*      */ import org.apache.commons.lang3.mutable.MutableInt;
/*      */ import org.apache.commons.lang3.text.StrBuilder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectUtils
/*      */ {
/*      */   private static final char AT_SIGN = '@';
/*      */   
/*      */   public static class Null
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7092611880189329093L;
/*      */     
/*      */     private Object readResolve() {
/*   87 */       return ObjectUtils.NULL;
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
/*  108 */   public static final Null NULL = new Null();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean allNull(Object... values) {
/*  133 */     return !anyNotNull(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean allNotNull(Object... values) {
/*  162 */     if (values == null) {
/*  163 */       return false;
/*      */     }
/*      */     
/*  166 */     for (Object val : values) {
/*  167 */       if (val == null) {
/*  168 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  172 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean anyNull(Object... values) {
/*  200 */     return !allNotNull(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean anyNotNull(Object... values) {
/*  227 */     return (firstNonNull(values) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T clone(T obj) {
/*  242 */     if (obj instanceof Cloneable) {
/*      */       Object result;
/*  244 */       if (obj.getClass().isArray()) {
/*  245 */         Class<?> componentType = obj.getClass().getComponentType();
/*  246 */         if (componentType.isPrimitive()) {
/*  247 */           int length = Array.getLength(obj);
/*  248 */           result = Array.newInstance(componentType, length);
/*  249 */           while (length-- > 0) {
/*  250 */             Array.set(result, length, Array.get(obj, length));
/*      */           }
/*      */         } else {
/*  253 */           result = ((Object[])obj).clone();
/*      */         } 
/*      */       } else {
/*      */         try {
/*  257 */           Method clone = obj.getClass().getMethod("clone", new Class[0]);
/*  258 */           result = clone.invoke(obj, new Object[0]);
/*  259 */         } catch (NoSuchMethodException e) {
/*  260 */           throw new CloneFailedException("Cloneable type " + obj
/*  261 */               .getClass().getName() + " has no clone method", e);
/*      */         }
/*  263 */         catch (IllegalAccessException e) {
/*  264 */           throw new CloneFailedException("Cannot clone Cloneable type " + obj
/*  265 */               .getClass().getName(), e);
/*  266 */         } catch (InvocationTargetException e) {
/*  267 */           throw new CloneFailedException("Exception cloning Cloneable type " + obj
/*  268 */               .getClass().getName(), e.getCause());
/*      */         } 
/*      */       } 
/*      */       
/*  272 */       T checked = (T)result;
/*  273 */       return checked;
/*      */     } 
/*      */     
/*  276 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T cloneIfPossible(T obj) {
/*  296 */     T clone = clone(obj);
/*  297 */     return (clone == null) ? obj : clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
/*  311 */     return compare(c1, c2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
/*  328 */     if (c1 == c2)
/*  329 */       return 0; 
/*  330 */     if (c1 == null)
/*  331 */       return nullGreater ? 1 : -1; 
/*  332 */     if (c2 == null) {
/*  333 */       return nullGreater ? -1 : 1;
/*      */     }
/*  335 */     return c1.compareTo(c2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean CONST(boolean v) {
/*  356 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte CONST(byte v) {
/*  377 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char CONST(char v) {
/*  398 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double CONST(double v) {
/*  419 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float CONST(float v) {
/*  440 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int CONST(int v) {
/*  461 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long CONST(long v) {
/*  482 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short CONST(short v) {
/*  503 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T CONST(T v) {
/*  525 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte CONST_BYTE(int v) {
/*  549 */     if (v < -128 || v > 127) {
/*  550 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + v + "]");
/*      */     }
/*  552 */     return (byte)v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short CONST_SHORT(int v) {
/*  576 */     if (v < -32768 || v > 32767) {
/*  577 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + v + "]");
/*      */     }
/*  579 */     return (short)v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T defaultIfNull(T object, T defaultValue) {
/*  600 */     return (object != null) ? object : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static boolean equals(Object object1, Object object2) {
/*  628 */     if (object1 == object2) {
/*  629 */       return true;
/*      */     }
/*  631 */     if (object1 == null || object2 == null) {
/*  632 */       return false;
/*      */     }
/*  634 */     return object1.equals(object2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <T> T firstNonNull(T... values) {
/*  661 */     if (values != null) {
/*  662 */       for (T val : values) {
/*  663 */         if (val != null) {
/*  664 */           return val;
/*      */         }
/*      */       } 
/*      */     }
/*  668 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <T> T getFirstNonNull(Supplier<T>... suppliers) {
/*  697 */     if (suppliers != null) {
/*  698 */       for (Supplier<T> supplier : suppliers) {
/*  699 */         if (supplier != null) {
/*  700 */           T value = supplier.get();
/*  701 */           if (value != null) {
/*  702 */             return value;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*  707 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T getIfNull(T object, Supplier<T> defaultSupplier) {
/*  736 */     return (object != null) ? object : ((defaultSupplier == null) ? null : defaultSupplier.get());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static int hashCode(Object obj) {
/*  757 */     return (obj == null) ? 0 : obj.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static int hashCodeMulti(Object... objects) {
/*  784 */     int hash = 1;
/*  785 */     if (objects != null) {
/*  786 */       for (Object object : objects) {
/*  787 */         int tmpHash = hashCode(object);
/*  788 */         hash = hash * 31 + tmpHash;
/*      */       } 
/*      */     }
/*  791 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void identityToString(Appendable appendable, Object object) throws IOException {
/*  811 */     Validate.notNull(object, "Cannot get the toString of a null object", new Object[0]);
/*  812 */     appendable.append(object.getClass().getName())
/*  813 */       .append('@')
/*  814 */       .append(Integer.toHexString(System.identityHashCode(object)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String identityToString(Object object) {
/*  836 */     if (object == null) {
/*  837 */       return null;
/*      */     }
/*  839 */     String name = object.getClass().getName();
/*  840 */     String hexString = Integer.toHexString(System.identityHashCode(object));
/*  841 */     StringBuilder builder = new StringBuilder(name.length() + 1 + hexString.length());
/*      */     
/*  843 */     builder.append(name)
/*  844 */       .append('@')
/*  845 */       .append(hexString);
/*      */     
/*  847 */     return builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static void identityToString(StrBuilder builder, Object object) {
/*  869 */     Validate.notNull(object, "Cannot get the toString of a null object", new Object[0]);
/*  870 */     String name = object.getClass().getName();
/*  871 */     String hexString = Integer.toHexString(System.identityHashCode(object));
/*  872 */     builder.ensureCapacity(builder.length() + name.length() + 1 + hexString.length());
/*  873 */     builder.append(name)
/*  874 */       .append('@')
/*  875 */       .append(hexString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void identityToString(StringBuffer buffer, Object object) {
/*  894 */     Validate.notNull(object, "Cannot get the toString of a null object", new Object[0]);
/*  895 */     String name = object.getClass().getName();
/*  896 */     String hexString = Integer.toHexString(System.identityHashCode(object));
/*  897 */     buffer.ensureCapacity(buffer.length() + name.length() + 1 + hexString.length());
/*  898 */     buffer.append(name)
/*  899 */       .append('@')
/*  900 */       .append(hexString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void identityToString(StringBuilder builder, Object object) {
/*  919 */     Validate.notNull(object, "Cannot get the toString of a null object", new Object[0]);
/*  920 */     String name = object.getClass().getName();
/*  921 */     String hexString = Integer.toHexString(System.identityHashCode(object));
/*  922 */     builder.ensureCapacity(builder.length() + name.length() + 1 + hexString.length());
/*  923 */     builder.append(name)
/*  924 */       .append('@')
/*  925 */       .append(hexString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(Object object) {
/*  978 */     if (object == null) {
/*  979 */       return true;
/*      */     }
/*  981 */     if (object instanceof CharSequence) {
/*  982 */       return (((CharSequence)object).length() == 0);
/*      */     }
/*  984 */     if (object.getClass().isArray()) {
/*  985 */       return (Array.getLength(object) == 0);
/*      */     }
/*  987 */     if (object instanceof Collection) {
/*  988 */       return ((Collection)object).isEmpty();
/*      */     }
/*  990 */     if (object instanceof Map) {
/*  991 */       return ((Map)object).isEmpty();
/*      */     }
/*  993 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(Object object) {
/* 1022 */     return !isEmpty(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <T extends Comparable<? super T>> T max(T... values) {
/* 1040 */     T result = null;
/* 1041 */     if (values != null) {
/* 1042 */       for (T value : values) {
/* 1043 */         if (compare(value, result, false) > 0) {
/* 1044 */           result = value;
/*      */         }
/*      */       } 
/*      */     }
/* 1048 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <T> T median(Comparator<T> comparator, T... items) {
/* 1064 */     Validate.notEmpty(items, "null/empty items", new Object[0]);
/* 1065 */     Validate.noNullElements(items);
/* 1066 */     Validate.notNull(comparator, "null comparator", new Object[0]);
/* 1067 */     TreeSet<T> sort = new TreeSet<>(comparator);
/* 1068 */     Collections.addAll(sort, items);
/*      */ 
/*      */     
/* 1071 */     T result = (T)sort.toArray()[(sort.size() - 1) / 2];
/* 1072 */     return result;
/*      */   }
/*      */ 
/*      */ 
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
/*      */   public static <T extends Comparable<? super T>> T median(T... items) {
/* 1087 */     Validate.notEmpty(items);
/* 1088 */     Validate.noNullElements(items);
/* 1089 */     TreeSet<T> sort = new TreeSet<>();
/* 1090 */     Collections.addAll(sort, items);
/*      */     
/* 1092 */     return (T)sort.toArray()[(sort.size() - 1) / 2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <T extends Comparable<? super T>> T min(T... values) {
/* 1113 */     T result = null;
/* 1114 */     if (values != null) {
/* 1115 */       for (T value : values) {
/* 1116 */         if (compare(value, result, true) < 0) {
/* 1117 */           result = value;
/*      */         }
/*      */       } 
/*      */     }
/* 1121 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <T> T mode(T... items) {
/* 1137 */     if (ArrayUtils.isNotEmpty(items)) {
/* 1138 */       HashMap<T, MutableInt> occurrences = new HashMap<>(items.length);
/* 1139 */       for (T t : items) {
/* 1140 */         MutableInt count = occurrences.get(t);
/* 1141 */         if (count == null) {
/* 1142 */           occurrences.put(t, new MutableInt(1));
/*      */         } else {
/* 1144 */           count.increment();
/*      */         } 
/*      */       } 
/* 1147 */       T result = null;
/* 1148 */       int max = 0;
/* 1149 */       for (Map.Entry<T, MutableInt> e : occurrences.entrySet()) {
/* 1150 */         int cmp = ((MutableInt)e.getValue()).intValue();
/* 1151 */         if (cmp == max) {
/* 1152 */           result = null; continue;
/* 1153 */         }  if (cmp > max) {
/* 1154 */           max = cmp;
/* 1155 */           result = e.getKey();
/*      */         } 
/*      */       } 
/* 1158 */       return result;
/*      */     } 
/* 1160 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean notEqual(Object object1, Object object2) {
/* 1183 */     return !equals(object1, object2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static String toString(Object obj) {
/* 1210 */     return (obj == null) ? "" : obj.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static String toString(Object obj, String nullStr) {
/* 1236 */     return (obj == null) ? nullStr : obj.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Object obj, Supplier<String> supplier) {
/* 1260 */     return (obj == null) ? ((supplier == null) ? null : supplier.get()) : obj.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\ObjectUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */