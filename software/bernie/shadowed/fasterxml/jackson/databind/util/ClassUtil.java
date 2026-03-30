/*      */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*      */ import java.io.IOException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumMap;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*      */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*      */ 
/*      */ public final class ClassUtil {
/*   17 */   private static final Class<?> CLS_OBJECT = Object.class;
/*      */   
/*   19 */   private static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
/*   20 */   private static final Ctor[] NO_CTORS = new Ctor[0];
/*      */   
/*   22 */   private static final Iterator<?> EMPTY_ITERATOR = Collections.emptyIterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> emptyIterator() {
/*   35 */     return (Iterator)EMPTY_ITERATOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<JavaType> findSuperTypes(JavaType type, Class<?> endBefore, boolean addClassItself) {
/*   60 */     if (type == null || type.hasRawClass(endBefore) || type.hasRawClass(Object.class)) {
/*   61 */       return Collections.emptyList();
/*      */     }
/*   63 */     List<JavaType> result = new ArrayList<>(8);
/*   64 */     _addSuperTypes(type, endBefore, result, addClassItself);
/*   65 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> findRawSuperTypes(Class<?> cls, Class<?> endBefore, boolean addClassItself) {
/*   72 */     if (cls == null || cls == endBefore || cls == Object.class) {
/*   73 */       return Collections.emptyList();
/*      */     }
/*   75 */     List<Class<?>> result = new ArrayList<>(8);
/*   76 */     _addRawSuperTypes(cls, endBefore, result, addClassItself);
/*   77 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> findSuperClasses(Class<?> cls, Class<?> endBefore, boolean addClassItself) {
/*   89 */     List<Class<?>> result = new LinkedList<>();
/*   90 */     if (cls != null && cls != endBefore) {
/*   91 */       if (addClassItself) {
/*   92 */         result.add(cls);
/*      */       }
/*   94 */       while ((cls = cls.getSuperclass()) != null && 
/*   95 */         cls != endBefore)
/*      */       {
/*      */         
/*   98 */         result.add(cls);
/*      */       }
/*      */     } 
/*  101 */     return result;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore) {
/*  106 */     return findSuperTypes(cls, endBefore, new ArrayList<>(8));
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore, List<Class<?>> result) {
/*  111 */     _addRawSuperTypes(cls, endBefore, result, false);
/*  112 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void _addSuperTypes(JavaType type, Class<?> endBefore, Collection<JavaType> result, boolean addClassItself) {
/*  118 */     if (type == null) {
/*      */       return;
/*      */     }
/*  121 */     Class<?> cls = type.getRawClass();
/*  122 */     if (cls == endBefore || cls == Object.class)
/*  123 */       return;  if (addClassItself) {
/*  124 */       if (result.contains(type)) {
/*      */         return;
/*      */       }
/*  127 */       result.add(type);
/*      */     } 
/*  129 */     for (JavaType intCls : type.getInterfaces()) {
/*  130 */       _addSuperTypes(intCls, endBefore, result, true);
/*      */     }
/*  132 */     _addSuperTypes(type.getSuperClass(), endBefore, result, true);
/*      */   }
/*      */   
/*      */   private static void _addRawSuperTypes(Class<?> cls, Class<?> endBefore, Collection<Class<?>> result, boolean addClassItself) {
/*  136 */     if (cls == endBefore || cls == null || cls == Object.class)
/*  137 */       return;  if (addClassItself) {
/*  138 */       if (result.contains(cls)) {
/*      */         return;
/*      */       }
/*  141 */       result.add(cls);
/*      */     } 
/*  143 */     for (Class<?> intCls : _interfaces(cls)) {
/*  144 */       _addRawSuperTypes(intCls, endBefore, result, true);
/*      */     }
/*  146 */     _addRawSuperTypes(cls.getSuperclass(), endBefore, result, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String canBeABeanType(Class<?> type) {
/*  162 */     if (type.isAnnotation()) {
/*  163 */       return "annotation";
/*      */     }
/*  165 */     if (type.isArray()) {
/*  166 */       return "array";
/*      */     }
/*  168 */     if (type.isEnum()) {
/*  169 */       return "enum";
/*      */     }
/*  171 */     if (type.isPrimitive()) {
/*  172 */       return "primitive";
/*      */     }
/*      */ 
/*      */     
/*  176 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String isLocalType(Class<?> type, boolean allowNonStatic) {
/*      */     
/*  187 */     try { if (hasEnclosingMethod(type)) {
/*  188 */         return "local/anonymous";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  195 */       if (!allowNonStatic && 
/*  196 */         !Modifier.isStatic(type.getModifiers()) && 
/*  197 */         getEnclosingClass(type) != null) {
/*  198 */         return "non-static member class";
/*      */       
/*      */       }
/*      */        }
/*      */     
/*  203 */     catch (SecurityException e) {  }
/*  204 */     catch (NullPointerException e) {}
/*  205 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getOuterClass(Class<?> type) {
/*      */     try {
/*  216 */       if (hasEnclosingMethod(type)) {
/*  217 */         return null;
/*      */       }
/*  219 */       if (!Modifier.isStatic(type.getModifiers())) {
/*  220 */         return getEnclosingClass(type);
/*      */       }
/*  222 */     } catch (SecurityException e) {}
/*  223 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProxyType(Class<?> type) {
/*  241 */     String name = type.getName();
/*      */     
/*  243 */     if (name.startsWith("net.sf.cglib.proxy.") || name.startsWith("org.hibernate.proxy."))
/*      */     {
/*  245 */       return true;
/*      */     }
/*      */     
/*  248 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConcrete(Class<?> type) {
/*  257 */     int mod = type.getModifiers();
/*  258 */     return ((mod & 0x600) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isConcrete(Member member) {
/*  263 */     int mod = member.getModifiers();
/*  264 */     return ((mod & 0x600) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCollectionMapOrArray(Class<?> type) {
/*  269 */     if (type.isArray()) return true; 
/*  270 */     if (Collection.class.isAssignableFrom(type)) return true; 
/*  271 */     if (Map.class.isAssignableFrom(type)) return true; 
/*  272 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isBogusClass(Class<?> cls) {
/*  276 */     return (cls == Void.class || cls == void.class || cls == NoClass.class);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isNonStaticInnerClass(Class<?> cls) {
/*  281 */     return (!Modifier.isStatic(cls.getModifiers()) && getEnclosingClass(cls) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isObjectOrPrimitive(Class<?> cls) {
/*  289 */     return (cls == CLS_OBJECT || cls.isPrimitive());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasClass(Object inst, Class<?> raw) {
/*  298 */     return (inst != null && inst.getClass() == raw);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void verifyMustOverride(Class<?> expType, Object instance, String method) {
/*  307 */     if (instance.getClass() != expType) {
/*  308 */       throw new IllegalStateException(String.format("Sub-class %s (of class %s) must override method '%s'", new Object[] { instance.getClass().getName(), expType.getName(), method }));
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
/*      */   @Deprecated
/*      */   public static boolean hasGetterSignature(Method m) {
/*  327 */     if (Modifier.isStatic(m.getModifiers())) {
/*  328 */       return false;
/*      */     }
/*      */     
/*  331 */     Class<?>[] pts = m.getParameterTypes();
/*  332 */     if (pts != null && pts.length != 0) {
/*  333 */       return false;
/*      */     }
/*      */     
/*  336 */     if (void.class == m.getReturnType()) {
/*  337 */       return false;
/*      */     }
/*      */     
/*  340 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable throwIfError(Throwable t) {
/*  356 */     if (t instanceof Error) {
/*  357 */       throw (Error)t;
/*      */     }
/*  359 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable throwIfRTE(Throwable t) {
/*  369 */     if (t instanceof RuntimeException) {
/*  370 */       throw (RuntimeException)t;
/*      */     }
/*  372 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable throwIfIOE(Throwable t) throws IOException {
/*  382 */     if (t instanceof IOException) {
/*  383 */       throw (IOException)t;
/*      */     }
/*  385 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable getRootCause(Throwable t) {
/*  400 */     while (t.getCause() != null) {
/*  401 */       t = t.getCause();
/*      */     }
/*  403 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Throwable throwRootCauseIfIOE(Throwable t) throws IOException {
/*  414 */     return throwIfIOE(getRootCause(t));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void throwAsIAE(Throwable t) {
/*  422 */     throwAsIAE(t, t.getMessage());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void throwAsIAE(Throwable t, String msg) {
/*  432 */     throwIfRTE(t);
/*  433 */     throwIfError(t);
/*  434 */     throw new IllegalArgumentException(msg, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T throwAsMappingException(DeserializationContext ctxt, IOException e0) throws JsonMappingException {
/*  442 */     if (e0 instanceof JsonMappingException) {
/*  443 */       throw (JsonMappingException)e0;
/*      */     }
/*  445 */     JsonMappingException e = JsonMappingException.from(ctxt, e0.getMessage());
/*  446 */     e.initCause(e0);
/*  447 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unwrapAndThrowAsIAE(Throwable t) {
/*  457 */     throwAsIAE(getRootCause(t));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unwrapAndThrowAsIAE(Throwable t, String msg) {
/*  467 */     throwAsIAE(getRootCause(t), msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeOnFailAndThrowAsIOE(JsonGenerator g, Exception fail) throws IOException {
/*  485 */     g.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
/*      */     try {
/*  487 */       g.close();
/*  488 */     } catch (Exception e) {
/*  489 */       fail.addSuppressed(e);
/*      */     } 
/*  491 */     throwIfIOE(fail);
/*  492 */     throwIfRTE(fail);
/*  493 */     throw new RuntimeException(fail);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeOnFailAndThrowAsIOE(JsonGenerator g, Closeable toClose, Exception fail) throws IOException {
/*  509 */     if (g != null) {
/*  510 */       g.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
/*      */       try {
/*  512 */         g.close();
/*  513 */       } catch (Exception e) {
/*  514 */         fail.addSuppressed(e);
/*      */       } 
/*      */     } 
/*  517 */     if (toClose != null) {
/*      */       try {
/*  519 */         toClose.close();
/*  520 */       } catch (Exception e) {
/*  521 */         fail.addSuppressed(e);
/*      */       } 
/*      */     }
/*  524 */     throwIfIOE(fail);
/*  525 */     throwIfRTE(fail);
/*  526 */     throw new RuntimeException(fail);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T createInstance(Class<T> cls, boolean canFixAccess) throws IllegalArgumentException {
/*  551 */     Constructor<T> ctor = findConstructor(cls, canFixAccess);
/*  552 */     if (ctor == null) {
/*  553 */       throw new IllegalArgumentException("Class " + cls.getName() + " has no default (no arg) constructor");
/*      */     }
/*      */     try {
/*  556 */       return ctor.newInstance(new Object[0]);
/*  557 */     } catch (Exception e) {
/*  558 */       unwrapAndThrowAsIAE(e, "Failed to instantiate class " + cls.getName() + ", problem: " + e.getMessage());
/*  559 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Constructor<T> findConstructor(Class<T> cls, boolean forceAccess) throws IllegalArgumentException {
/*      */     try {
/*  567 */       Constructor<T> ctor = cls.getDeclaredConstructor(new Class[0]);
/*  568 */       if (forceAccess) {
/*  569 */         checkAndFixAccess(ctor, forceAccess);
/*      */       
/*      */       }
/*  572 */       else if (!Modifier.isPublic(ctor.getModifiers())) {
/*  573 */         throw new IllegalArgumentException("Default constructor for " + cls.getName() + " is not accessible (non-public?): not allowed to try modify access via Reflection: cannot instantiate type");
/*      */       } 
/*      */       
/*  576 */       return ctor;
/*  577 */     } catch (NoSuchMethodException e) {
/*      */     
/*  579 */     } catch (Exception e) {
/*  580 */       unwrapAndThrowAsIAE(e, "Failed to find default constructor of class " + cls.getName() + ", problem: " + e.getMessage());
/*      */     } 
/*  582 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> classOf(Object inst) {
/*  595 */     if (inst == null) {
/*  596 */       return null;
/*      */     }
/*  598 */     return inst.getClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> rawClass(JavaType t) {
/*  605 */     if (t == null) {
/*  606 */       return null;
/*      */     }
/*  608 */     return t.getRawClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T nonNull(T valueOrNull, T defaultValue) {
/*  615 */     return (valueOrNull == null) ? defaultValue : valueOrNull;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String nullOrToString(Object value) {
/*  622 */     if (value == null) {
/*  623 */       return null;
/*      */     }
/*  625 */     return value.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String nonNullString(String str) {
/*  632 */     if (str == null) {
/*  633 */       return "";
/*      */     }
/*  635 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String quotedOr(Object str, String forNull) {
/*  645 */     if (str == null) {
/*  646 */       return forNull;
/*      */     }
/*  648 */     return String.format("\"%s\"", new Object[] { str });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getClassDescription(Object classOrInstance) {
/*  664 */     if (classOrInstance == null) {
/*  665 */       return "unknown";
/*      */     }
/*  667 */     Class<?> cls = (classOrInstance instanceof Class) ? (Class)classOrInstance : classOrInstance.getClass();
/*      */     
/*  669 */     return nameOf(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String classNameOf(Object inst) {
/*  680 */     if (inst == null) {
/*  681 */       return "[null]";
/*      */     }
/*  683 */     return nameOf(inst.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String nameOf(Class<?> cls) {
/*  693 */     if (cls == null) {
/*  694 */       return "[null]";
/*      */     }
/*  696 */     int index = 0;
/*  697 */     while (cls.isArray()) {
/*  698 */       index++;
/*  699 */       cls = cls.getComponentType();
/*      */     } 
/*  701 */     String base = cls.isPrimitive() ? cls.getSimpleName() : cls.getName();
/*  702 */     if (index > 0) {
/*  703 */       StringBuilder sb = new StringBuilder(base);
/*      */       while (true) {
/*  705 */         sb.append("[]");
/*  706 */         if (--index <= 0)
/*  707 */         { base = sb.toString(); break; } 
/*      */       } 
/*  709 */     }  return backticked(base);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String nameOf(Named named) {
/*  719 */     if (named == null) {
/*  720 */       return "[null]";
/*      */     }
/*  722 */     return backticked(named.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String backticked(String text) {
/*  731 */     if (text == null) {
/*  732 */       return "[null]";
/*      */     }
/*  734 */     return (new StringBuilder(text.length() + 2)).append('`').append(text).append('`').toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object defaultValue(Class<?> cls) {
/*  749 */     if (cls == int.class) {
/*  750 */       return Integer.valueOf(0);
/*      */     }
/*  752 */     if (cls == long.class) {
/*  753 */       return Long.valueOf(0L);
/*      */     }
/*  755 */     if (cls == boolean.class) {
/*  756 */       return Boolean.FALSE;
/*      */     }
/*  758 */     if (cls == double.class) {
/*  759 */       return Double.valueOf(0.0D);
/*      */     }
/*  761 */     if (cls == float.class) {
/*  762 */       return Float.valueOf(0.0F);
/*      */     }
/*  764 */     if (cls == byte.class) {
/*  765 */       return Byte.valueOf((byte)0);
/*      */     }
/*  767 */     if (cls == short.class) {
/*  768 */       return Short.valueOf((short)0);
/*      */     }
/*  770 */     if (cls == char.class) {
/*  771 */       return Character.valueOf(false);
/*      */     }
/*  773 */     throw new IllegalArgumentException("Class " + cls.getName() + " is not a primitive type");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> wrapperType(Class<?> primitiveType) {
/*  782 */     if (primitiveType == int.class) {
/*  783 */       return Integer.class;
/*      */     }
/*  785 */     if (primitiveType == long.class) {
/*  786 */       return Long.class;
/*      */     }
/*  788 */     if (primitiveType == boolean.class) {
/*  789 */       return Boolean.class;
/*      */     }
/*  791 */     if (primitiveType == double.class) {
/*  792 */       return Double.class;
/*      */     }
/*  794 */     if (primitiveType == float.class) {
/*  795 */       return Float.class;
/*      */     }
/*  797 */     if (primitiveType == byte.class) {
/*  798 */       return Byte.class;
/*      */     }
/*  800 */     if (primitiveType == short.class) {
/*  801 */       return Short.class;
/*      */     }
/*  803 */     if (primitiveType == char.class) {
/*  804 */       return Character.class;
/*      */     }
/*  806 */     throw new IllegalArgumentException("Class " + primitiveType.getName() + " is not a primitive type");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> primitiveType(Class<?> type) {
/*  817 */     if (type.isPrimitive()) {
/*  818 */       return type;
/*      */     }
/*      */     
/*  821 */     if (type == Integer.class) {
/*  822 */       return int.class;
/*      */     }
/*  824 */     if (type == Long.class) {
/*  825 */       return long.class;
/*      */     }
/*  827 */     if (type == Boolean.class) {
/*  828 */       return boolean.class;
/*      */     }
/*  830 */     if (type == Double.class) {
/*  831 */       return double.class;
/*      */     }
/*  833 */     if (type == Float.class) {
/*  834 */       return float.class;
/*      */     }
/*  836 */     if (type == Byte.class) {
/*  837 */       return byte.class;
/*      */     }
/*  839 */     if (type == Short.class) {
/*  840 */       return short.class;
/*      */     }
/*  842 */     if (type == Character.class) {
/*  843 */       return char.class;
/*      */     }
/*  845 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static void checkAndFixAccess(Member member) {
/*  864 */     checkAndFixAccess(member, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkAndFixAccess(Member member, boolean force) {
/*  881 */     AccessibleObject ao = (AccessibleObject)member;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  888 */       if (force || !Modifier.isPublic(member.getModifiers()) || !Modifier.isPublic(member.getDeclaringClass().getModifiers()))
/*      */       {
/*      */         
/*  891 */         ao.setAccessible(true);
/*      */       }
/*  893 */     } catch (SecurityException se) {
/*      */ 
/*      */       
/*  896 */       if (!ao.isAccessible()) {
/*  897 */         Class<?> declClass = member.getDeclaringClass();
/*  898 */         throw new IllegalArgumentException("Cannot access " + member + " (from class " + declClass.getName() + "; failed to set access: " + se.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<? extends Enum<?>> findEnumType(EnumSet<?> s) {
/*  918 */     if (!s.isEmpty()) {
/*  919 */       return findEnumType(s.iterator().next());
/*      */     }
/*      */     
/*  922 */     return EnumTypeLocator.instance.enumTypeFor(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<? extends Enum<?>> findEnumType(EnumMap<?, ?> m) {
/*  933 */     if (!m.isEmpty()) {
/*  934 */       return findEnumType(m.keySet().iterator().next());
/*      */     }
/*      */     
/*  937 */     return EnumTypeLocator.instance.enumTypeFor(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<? extends Enum<?>> findEnumType(Enum<?> en) {
/*  950 */     Class<?> ec = en.getClass();
/*  951 */     if (ec.getSuperclass() != Enum.class) {
/*  952 */       ec = ec.getSuperclass();
/*      */     }
/*  954 */     return (Class)ec;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<? extends Enum<?>> findEnumType(Class<?> cls) {
/*  967 */     if (cls.getSuperclass() != Enum.class) {
/*  968 */       cls = cls.getSuperclass();
/*      */     }
/*  970 */     return (Class)cls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Annotation> Enum<?> findFirstAnnotatedEnumValue(Class<Enum<?>> enumClass, Class<T> annotationClass) {
/*  986 */     Field[] fields = getDeclaredFields(enumClass);
/*  987 */     for (Field field : fields) {
/*  988 */       if (field.isEnumConstant()) {
/*  989 */         Annotation defaultValueAnnotation = field.getAnnotation(annotationClass);
/*  990 */         if (defaultValueAnnotation != null) {
/*  991 */           String name = field.getName();
/*  992 */           for (Enum<?> enumValue : (Enum[])enumClass.getEnumConstants()) {
/*  993 */             if (name.equals(enumValue.name())) {
/*  994 */               return enumValue;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1000 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isJacksonStdImpl(Object impl) {
/* 1020 */     return (impl == null || isJacksonStdImpl(impl.getClass()));
/*      */   }
/*      */   
/*      */   public static boolean isJacksonStdImpl(Class<?> implClass) {
/* 1024 */     return (implClass.getAnnotation(JacksonStdImpl.class) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(Class<?> cls) {
/* 1040 */     Package pkg = cls.getPackage();
/* 1041 */     return (pkg == null) ? null : pkg.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasEnclosingMethod(Class<?> cls) {
/* 1048 */     return (!isObjectOrPrimitive(cls) && cls.getEnclosingMethod() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Field[] getDeclaredFields(Class<?> cls) {
/* 1055 */     return cls.getDeclaredFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method[] getDeclaredMethods(Class<?> cls) {
/* 1062 */     return cls.getDeclaredMethods();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Annotation[] findClassAnnotations(Class<?> cls) {
/* 1069 */     if (isObjectOrPrimitive(cls)) {
/* 1070 */       return NO_ANNOTATIONS;
/*      */     }
/* 1072 */     return cls.getDeclaredAnnotations();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method[] getClassMethods(Class<?> cls) {
/*      */     try {
/* 1085 */       return getDeclaredMethods(cls);
/* 1086 */     } catch (NoClassDefFoundError ex) {
/*      */       Class<?> contextClass;
/*      */       
/* 1089 */       ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 1090 */       if (loader == null)
/*      */       {
/* 1092 */         throw ex;
/*      */       }
/*      */       
/*      */       try {
/* 1096 */         contextClass = loader.loadClass(cls.getName());
/* 1097 */       } catch (ClassNotFoundException e) {
/* 1098 */         ex.addSuppressed(e);
/* 1099 */         throw ex;
/*      */       } 
/* 1101 */       return contextClass.getDeclaredMethods();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Ctor[] getConstructors(Class<?> cls) {
/* 1111 */     if (cls.isInterface() || isObjectOrPrimitive(cls)) {
/* 1112 */       return NO_CTORS;
/*      */     }
/* 1114 */     Constructor[] arrayOfConstructor = (Constructor[])cls.getDeclaredConstructors();
/* 1115 */     int len = arrayOfConstructor.length;
/* 1116 */     Ctor[] result = new Ctor[len];
/* 1117 */     for (int i = 0; i < len; i++) {
/* 1118 */       result[i] = new Ctor(arrayOfConstructor[i]);
/*      */     }
/* 1120 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getDeclaringClass(Class<?> cls) {
/* 1130 */     return isObjectOrPrimitive(cls) ? null : cls.getDeclaringClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type getGenericSuperclass(Class<?> cls) {
/* 1137 */     return cls.getGenericSuperclass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type[] getGenericInterfaces(Class<?> cls) {
/* 1144 */     return cls.getGenericInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getEnclosingClass(Class<?> cls) {
/* 1152 */     return isObjectOrPrimitive(cls) ? null : cls.getEnclosingClass();
/*      */   }
/*      */   
/*      */   private static Class<?>[] _interfaces(Class<?> cls) {
/* 1156 */     return cls.getInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EnumTypeLocator
/*      */   {
/* 1171 */     static final EnumTypeLocator instance = new EnumTypeLocator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     private final Field enumSetTypeField = locateField(EnumSet.class, "elementType", Class.class);
/* 1180 */     private final Field enumMapTypeField = locateField(EnumMap.class, "elementType", Class.class);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Class<? extends Enum<?>> enumTypeFor(EnumSet<?> set) {
/* 1186 */       if (this.enumSetTypeField != null) {
/* 1187 */         return (Class<? extends Enum<?>>)get(set, this.enumSetTypeField);
/*      */       }
/* 1189 */       throw new IllegalStateException("Cannot figure out type for EnumSet (odd JDK platform?)");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Class<? extends Enum<?>> enumTypeFor(EnumMap<?, ?> set) {
/* 1195 */       if (this.enumMapTypeField != null) {
/* 1196 */         return (Class<? extends Enum<?>>)get(set, this.enumMapTypeField);
/*      */       }
/* 1198 */       throw new IllegalStateException("Cannot figure out type for EnumMap (odd JDK platform?)");
/*      */     }
/*      */ 
/*      */     
/*      */     private Object get(Object bean, Field field) {
/*      */       try {
/* 1204 */         return field.get(bean);
/* 1205 */       } catch (Exception e) {
/* 1206 */         throw new IllegalArgumentException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static Field locateField(Class<?> fromClass, String expectedName, Class<?> type) {
/* 1212 */       Field found = null;
/*      */       
/* 1214 */       Field[] fields = ClassUtil.getDeclaredFields(fromClass);
/* 1215 */       for (Field f : fields) {
/* 1216 */         if (expectedName.equals(f.getName()) && f.getType() == type) {
/* 1217 */           found = f;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1222 */       if (found == null) {
/* 1223 */         for (Field f : fields) {
/* 1224 */           if (f.getType() == type) {
/*      */             
/* 1226 */             if (found != null) return null; 
/* 1227 */             found = f;
/*      */           } 
/*      */         } 
/*      */       }
/* 1231 */       if (found != null) {
/*      */         try {
/* 1233 */           found.setAccessible(true);
/* 1234 */         } catch (Throwable t) {}
/*      */       }
/* 1236 */       return found;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Ctor
/*      */   {
/*      */     public final Constructor<?> _ctor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Annotation[] _annotations;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Annotation[][] _paramAnnotations;
/*      */ 
/*      */ 
/*      */     
/* 1260 */     private int _paramCount = -1;
/*      */     
/*      */     public Ctor(Constructor<?> ctor) {
/* 1263 */       this._ctor = ctor;
/*      */     }
/*      */     
/*      */     public Constructor<?> getConstructor() {
/* 1267 */       return this._ctor;
/*      */     }
/*      */     
/*      */     public int getParamCount() {
/* 1271 */       int c = this._paramCount;
/* 1272 */       if (c < 0) {
/* 1273 */         c = (this._ctor.getParameterTypes()).length;
/* 1274 */         this._paramCount = c;
/*      */       } 
/* 1276 */       return c;
/*      */     }
/*      */     
/*      */     public Class<?> getDeclaringClass() {
/* 1280 */       return this._ctor.getDeclaringClass();
/*      */     }
/*      */     
/*      */     public Annotation[] getDeclaredAnnotations() {
/* 1284 */       Annotation[] result = this._annotations;
/* 1285 */       if (result == null) {
/* 1286 */         result = this._ctor.getDeclaredAnnotations();
/* 1287 */         this._annotations = result;
/*      */       } 
/* 1289 */       return result;
/*      */     }
/*      */     
/*      */     public Annotation[][] getParameterAnnotations() {
/* 1293 */       Annotation[][] result = this._paramAnnotations;
/* 1294 */       if (result == null) {
/* 1295 */         result = this._ctor.getParameterAnnotations();
/* 1296 */         this._paramAnnotations = result;
/*      */       } 
/* 1298 */       return result;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\ClassUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */