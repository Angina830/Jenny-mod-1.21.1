/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*     */ import org.apache.commons.lang3.builder.ToStringStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationUtils
/*     */ {
/*  50 */   private static final ToStringStyle TO_STRING_STYLE = new ToStringStyle()
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       protected String getShortClassName(Class<?> cls) {
/*  72 */         for (Class<?> iface : ClassUtils.getAllInterfaces(cls)) {
/*  73 */           if (Annotation.class.isAssignableFrom(iface)) {
/*  74 */             return "@" + iface.getName();
/*     */           }
/*     */         } 
/*  77 */         return "";
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
/*  85 */         if (value instanceof Annotation) {
/*  86 */           value = AnnotationUtils.toString((Annotation)value);
/*     */         }
/*  88 */         super.appendDetail(buffer, fieldName, value);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Annotation a1, Annotation a2) {
/* 116 */     if (a1 == a2) {
/* 117 */       return true;
/*     */     }
/* 119 */     if (a1 == null || a2 == null) {
/* 120 */       return false;
/*     */     }
/* 122 */     Class<? extends Annotation> type1 = a1.annotationType();
/* 123 */     Class<? extends Annotation> type2 = a2.annotationType();
/* 124 */     Validate.notNull(type1, "Annotation %s with null annotationType()", new Object[] { a1 });
/* 125 */     Validate.notNull(type2, "Annotation %s with null annotationType()", new Object[] { a2 });
/* 126 */     if (!type1.equals(type2)) {
/* 127 */       return false;
/*     */     }
/*     */     try {
/* 130 */       for (Method m : type1.getDeclaredMethods()) {
/* 131 */         if ((m.getParameterTypes()).length == 0 && 
/* 132 */           isValidAnnotationMemberType(m.getReturnType())) {
/* 133 */           Object v1 = m.invoke(a1, new Object[0]);
/* 134 */           Object v2 = m.invoke(a2, new Object[0]);
/* 135 */           if (!memberEquals(m.getReturnType(), v1, v2)) {
/* 136 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/* 140 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException ex) {
/* 141 */       return false;
/*     */     } 
/* 143 */     return true;
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
/*     */   public static int hashCode(Annotation a) {
/* 159 */     int result = 0;
/* 160 */     Class<? extends Annotation> type = a.annotationType();
/* 161 */     for (Method m : type.getDeclaredMethods()) {
/*     */       try {
/* 163 */         Object value = m.invoke(a, new Object[0]);
/* 164 */         if (value == null) {
/* 165 */           throw new IllegalStateException(
/* 166 */               String.format("Annotation method %s returned null", new Object[] { m }));
/*     */         }
/* 168 */         result += hashMember(m.getName(), value);
/* 169 */       } catch (RuntimeException ex) {
/* 170 */         throw ex;
/* 171 */       } catch (Exception ex) {
/* 172 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     } 
/* 175 */     return result;
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
/*     */   public static String toString(Annotation a) {
/* 187 */     ToStringBuilder builder = new ToStringBuilder(a, TO_STRING_STYLE);
/* 188 */     for (Method m : a.annotationType().getDeclaredMethods()) {
/* 189 */       if ((m.getParameterTypes()).length <= 0)
/*     */         
/*     */         try {
/*     */           
/* 193 */           builder.append(m.getName(), m.invoke(a, new Object[0]));
/* 194 */         } catch (RuntimeException ex) {
/* 195 */           throw ex;
/* 196 */         } catch (Exception ex) {
/* 197 */           throw new RuntimeException(ex);
/*     */         }  
/*     */     } 
/* 200 */     return builder.build();
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
/*     */   public static boolean isValidAnnotationMemberType(Class<?> type) {
/* 215 */     if (type == null) {
/* 216 */       return false;
/*     */     }
/* 218 */     if (type.isArray()) {
/* 219 */       type = type.getComponentType();
/*     */     }
/* 221 */     return (type.isPrimitive() || type.isEnum() || type.isAnnotation() || String.class
/* 222 */       .equals(type) || Class.class.equals(type));
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
/*     */   private static int hashMember(String name, Object value) {
/* 234 */     int part1 = name.hashCode() * 127;
/* 235 */     if (value.getClass().isArray()) {
/* 236 */       return part1 ^ arrayMemberHash(value.getClass().getComponentType(), value);
/*     */     }
/* 238 */     if (value instanceof Annotation) {
/* 239 */       return part1 ^ hashCode((Annotation)value);
/*     */     }
/* 241 */     return part1 ^ value.hashCode();
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
/*     */   private static boolean memberEquals(Class<?> type, Object o1, Object o2) {
/* 255 */     if (o1 == o2) {
/* 256 */       return true;
/*     */     }
/* 258 */     if (o1 == null || o2 == null) {
/* 259 */       return false;
/*     */     }
/* 261 */     if (type.isArray()) {
/* 262 */       return arrayMemberEquals(type.getComponentType(), o1, o2);
/*     */     }
/* 264 */     if (type.isAnnotation()) {
/* 265 */       return equals((Annotation)o1, (Annotation)o2);
/*     */     }
/* 267 */     return o1.equals(o2);
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
/*     */   private static boolean arrayMemberEquals(Class<?> componentType, Object o1, Object o2) {
/* 279 */     if (componentType.isAnnotation()) {
/* 280 */       return annotationArrayMemberEquals((Annotation[])o1, (Annotation[])o2);
/*     */     }
/* 282 */     if (componentType.equals(byte.class)) {
/* 283 */       return Arrays.equals((byte[])o1, (byte[])o2);
/*     */     }
/* 285 */     if (componentType.equals(short.class)) {
/* 286 */       return Arrays.equals((short[])o1, (short[])o2);
/*     */     }
/* 288 */     if (componentType.equals(int.class)) {
/* 289 */       return Arrays.equals((int[])o1, (int[])o2);
/*     */     }
/* 291 */     if (componentType.equals(char.class)) {
/* 292 */       return Arrays.equals((char[])o1, (char[])o2);
/*     */     }
/* 294 */     if (componentType.equals(long.class)) {
/* 295 */       return Arrays.equals((long[])o1, (long[])o2);
/*     */     }
/* 297 */     if (componentType.equals(float.class)) {
/* 298 */       return Arrays.equals((float[])o1, (float[])o2);
/*     */     }
/* 300 */     if (componentType.equals(double.class)) {
/* 301 */       return Arrays.equals((double[])o1, (double[])o2);
/*     */     }
/* 303 */     if (componentType.equals(boolean.class)) {
/* 304 */       return Arrays.equals((boolean[])o1, (boolean[])o2);
/*     */     }
/* 306 */     return Arrays.equals((Object[])o1, (Object[])o2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean annotationArrayMemberEquals(Annotation[] a1, Annotation[] a2) {
/* 317 */     if (a1.length != a2.length) {
/* 318 */       return false;
/*     */     }
/* 320 */     for (int i = 0; i < a1.length; i++) {
/* 321 */       if (!equals(a1[i], a2[i])) {
/* 322 */         return false;
/*     */       }
/*     */     } 
/* 325 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int arrayMemberHash(Class<?> componentType, Object o) {
/* 336 */     if (componentType.equals(byte.class)) {
/* 337 */       return Arrays.hashCode((byte[])o);
/*     */     }
/* 339 */     if (componentType.equals(short.class)) {
/* 340 */       return Arrays.hashCode((short[])o);
/*     */     }
/* 342 */     if (componentType.equals(int.class)) {
/* 343 */       return Arrays.hashCode((int[])o);
/*     */     }
/* 345 */     if (componentType.equals(char.class)) {
/* 346 */       return Arrays.hashCode((char[])o);
/*     */     }
/* 348 */     if (componentType.equals(long.class)) {
/* 349 */       return Arrays.hashCode((long[])o);
/*     */     }
/* 351 */     if (componentType.equals(float.class)) {
/* 352 */       return Arrays.hashCode((float[])o);
/*     */     }
/* 354 */     if (componentType.equals(double.class)) {
/* 355 */       return Arrays.hashCode((double[])o);
/*     */     }
/* 357 */     if (componentType.equals(boolean.class)) {
/* 358 */       return Arrays.hashCode((boolean[])o);
/*     */     }
/* 360 */     return Arrays.hashCode((Object[])o);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\AnnotationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */