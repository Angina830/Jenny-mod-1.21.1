/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanUtil
/*     */ {
/*     */   public static String okNameForGetter(AnnotatedMethod am, boolean stdNaming) {
/*  27 */     String name = am.getName();
/*  28 */     String str = okNameForIsGetter(am, name, stdNaming);
/*  29 */     if (str == null) {
/*  30 */       str = okNameForRegularGetter(am, name, stdNaming);
/*     */     }
/*  32 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String okNameForRegularGetter(AnnotatedMethod am, String name, boolean stdNaming) {
/*  41 */     if (name.startsWith("get")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  49 */       if ("getCallbacks".equals(name)) {
/*  50 */         if (isCglibGetCallbacks(am)) {
/*  51 */           return null;
/*     */         }
/*  53 */       } else if ("getMetaClass".equals(name)) {
/*     */         
/*  55 */         if (isGroovyMetaClassGetter(am)) {
/*  56 */           return null;
/*     */         }
/*     */       } 
/*  59 */       return stdNaming ? stdManglePropertyName(name, 3) : legacyManglePropertyName(name, 3);
/*     */     } 
/*     */ 
/*     */     
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String okNameForIsGetter(AnnotatedMethod am, String name, boolean stdNaming) {
/*  72 */     if (name.startsWith("is")) {
/*  73 */       Class<?> rt = am.getRawType();
/*  74 */       if (rt == Boolean.class || rt == boolean.class) {
/*  75 */         return stdNaming ? stdManglePropertyName(name, 2) : legacyManglePropertyName(name, 2);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static String okNameForSetter(AnnotatedMethod am, boolean stdNaming) {
/*  88 */     String name = okNameForMutator(am, "set", stdNaming);
/*  89 */     if (name != null && (!"metaClass".equals(name) || !isGroovyMetaClassSetter(am)))
/*     */     {
/*     */       
/*  92 */       return name;
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String okNameForMutator(AnnotatedMethod am, String prefix, boolean stdNaming) {
/* 102 */     String name = am.getName();
/* 103 */     if (name.startsWith(prefix)) {
/* 104 */       return stdNaming ? stdManglePropertyName(name, prefix.length()) : legacyManglePropertyName(name, prefix.length());
/*     */     }
/*     */ 
/*     */     
/* 108 */     return null;
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
/*     */   public static Object getDefaultValue(JavaType type) {
/* 134 */     Class<?> cls = type.getRawClass();
/*     */ 
/*     */ 
/*     */     
/* 138 */     Class<?> prim = ClassUtil.primitiveType(cls);
/* 139 */     if (prim != null) {
/* 140 */       return ClassUtil.defaultValue(prim);
/*     */     }
/* 142 */     if (type.isContainerType() || type.isReferenceType()) {
/* 143 */       return JsonInclude.Include.NON_EMPTY;
/*     */     }
/* 145 */     if (cls == String.class) {
/* 146 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 150 */     if (type.isTypeOrSubTypeOf(Date.class)) {
/* 151 */       return new Date(0L);
/*     */     }
/* 153 */     if (type.isTypeOrSubTypeOf(Calendar.class)) {
/* 154 */       Calendar c = new GregorianCalendar();
/* 155 */       c.setTimeInMillis(0L);
/* 156 */       return c;
/*     */     } 
/* 158 */     return null;
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
/*     */   protected static boolean isCglibGetCallbacks(AnnotatedMethod am) {
/* 177 */     Class<?> rt = am.getRawType();
/*     */     
/* 179 */     if (rt.isArray()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       Class<?> compType = rt.getComponentType();
/*     */       
/* 186 */       String pkgName = ClassUtil.getPackageName(compType);
/* 187 */       if (pkgName != null && 
/* 188 */         pkgName.contains(".cglib")) {
/* 189 */         return (pkgName.startsWith("net.sf.cglib") || pkgName.startsWith("org.hibernate.repackage.cglib") || pkgName.startsWith("org.springframework.cglib"));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isGroovyMetaClassSetter(AnnotatedMethod am) {
/* 206 */     Class<?> argType = am.getRawParameterType(0);
/* 207 */     String pkgName = ClassUtil.getPackageName(argType);
/* 208 */     return (pkgName != null && pkgName.startsWith("groovy.lang"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isGroovyMetaClassGetter(AnnotatedMethod am) {
/* 216 */     String pkgName = ClassUtil.getPackageName(am.getRawType());
/* 217 */     return (pkgName != null && pkgName.startsWith("groovy.lang"));
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
/*     */   protected static String legacyManglePropertyName(String basename, int offset) {
/* 235 */     int end = basename.length();
/* 236 */     if (end == offset) {
/* 237 */       return null;
/*     */     }
/*     */     
/* 240 */     char c = basename.charAt(offset);
/* 241 */     char d = Character.toLowerCase(c);
/*     */     
/* 243 */     if (c == d) {
/* 244 */       return basename.substring(offset);
/*     */     }
/*     */     
/* 247 */     StringBuilder sb = new StringBuilder(end - offset);
/* 248 */     sb.append(d);
/* 249 */     int i = offset + 1;
/* 250 */     for (; i < end; i++) {
/* 251 */       c = basename.charAt(i);
/* 252 */       d = Character.toLowerCase(c);
/* 253 */       if (c == d) {
/* 254 */         sb.append(basename, i, end);
/*     */         break;
/*     */       } 
/* 257 */       sb.append(d);
/*     */     } 
/* 259 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String stdManglePropertyName(String basename, int offset) {
/* 267 */     int end = basename.length();
/* 268 */     if (end == offset) {
/* 269 */       return null;
/*     */     }
/*     */     
/* 272 */     char c0 = basename.charAt(offset);
/* 273 */     char c1 = Character.toLowerCase(c0);
/* 274 */     if (c0 == c1) {
/* 275 */       return basename.substring(offset);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 280 */     if (offset + 1 < end && 
/* 281 */       Character.isUpperCase(basename.charAt(offset + 1))) {
/* 282 */       return basename.substring(offset);
/*     */     }
/*     */     
/* 285 */     StringBuilder sb = new StringBuilder(end - offset);
/* 286 */     sb.append(c1);
/* 287 */     sb.append(basename, offset + 1, end);
/* 288 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\BeanUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */