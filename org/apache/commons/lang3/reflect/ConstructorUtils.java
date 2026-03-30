/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
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
/*     */ public class ConstructorUtils
/*     */ {
/*     */   public static <T> T invokeConstructor(Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
/*  83 */     args = ArrayUtils.nullToEmpty(args);
/*  84 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  85 */     return invokeConstructor(cls, args, parameterTypes);
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
/*     */   public static <T> T invokeConstructor(Class<T> cls, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
/* 111 */     args = ArrayUtils.nullToEmpty(args);
/* 112 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/* 113 */     Constructor<T> ctor = getMatchingAccessibleConstructor(cls, parameterTypes);
/* 114 */     if (ctor == null) {
/* 115 */       throw new NoSuchMethodException("No such accessible constructor on object: " + cls
/* 116 */           .getName());
/*     */     }
/* 118 */     if (ctor.isVarArgs()) {
/* 119 */       Class<?>[] methodParameterTypes = ctor.getParameterTypes();
/* 120 */       args = MethodUtils.getVarArgs(args, methodParameterTypes);
/*     */     } 
/* 122 */     return ctor.newInstance(args);
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
/*     */   public static <T> T invokeExactConstructor(Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
/* 147 */     args = ArrayUtils.nullToEmpty(args);
/* 148 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/* 149 */     return invokeExactConstructor(cls, args, parameterTypes);
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
/*     */   public static <T> T invokeExactConstructor(Class<T> cls, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
/* 175 */     args = ArrayUtils.nullToEmpty(args);
/* 176 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/* 177 */     Constructor<T> ctor = getAccessibleConstructor(cls, parameterTypes);
/* 178 */     if (ctor == null) {
/* 179 */       throw new NoSuchMethodException("No such accessible constructor on object: " + cls
/* 180 */           .getName());
/*     */     }
/* 182 */     return ctor.newInstance(args);
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
/*     */   public static <T> Constructor<T> getAccessibleConstructor(Class<T> cls, Class<?>... parameterTypes) {
/* 202 */     Validate.notNull(cls, "class cannot be null", new Object[0]);
/*     */     try {
/* 204 */       return getAccessibleConstructor(cls.getConstructor(parameterTypes));
/* 205 */     } catch (NoSuchMethodException e) {
/* 206 */       return null;
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
/*     */   public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> ctor) {
/* 222 */     Validate.notNull(ctor, "constructor cannot be null", new Object[0]);
/* 223 */     return (MemberUtils.isAccessible(ctor) && 
/* 224 */       isAccessible(ctor.getDeclaringClass())) ? ctor : null;
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
/*     */   public static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> cls, Class<?>... parameterTypes) {
/* 247 */     Validate.notNull(cls, "class cannot be null", new Object[0]);
/*     */ 
/*     */     
/*     */     try {
/* 251 */       Constructor<T> ctor = cls.getConstructor(parameterTypes);
/* 252 */       MemberUtils.setAccessibleWorkaround(ctor);
/* 253 */       return ctor;
/* 254 */     } catch (NoSuchMethodException noSuchMethodException) {
/*     */       
/* 256 */       Constructor<T> result = null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       Constructor[] arrayOfConstructor = (Constructor[])cls.getConstructors();
/*     */ 
/*     */       
/* 264 */       for (Constructor<?> ctor : arrayOfConstructor) {
/*     */         
/* 266 */         if (MemberUtils.isMatchingConstructor(ctor, parameterTypes)) {
/*     */           
/* 268 */           ctor = getAccessibleConstructor(ctor);
/* 269 */           if (ctor != null) {
/* 270 */             MemberUtils.setAccessibleWorkaround(ctor);
/* 271 */             if (result == null || MemberUtils.compareConstructorFit(ctor, result, parameterTypes) < 0) {
/*     */ 
/*     */ 
/*     */               
/* 275 */               Constructor<T> constructor = (Constructor)ctor;
/* 276 */               result = constructor;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 281 */       return result;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAccessible(Class<?> type) {
/* 292 */     Class<?> cls = type;
/* 293 */     while (cls != null) {
/* 294 */       if (!Modifier.isPublic(cls.getModifiers())) {
/* 295 */         return false;
/*     */       }
/* 297 */       cls = cls.getEnclosingClass();
/*     */     } 
/* 299 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\reflect\ConstructorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */