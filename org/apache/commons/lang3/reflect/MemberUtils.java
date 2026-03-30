/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MemberUtils
/*     */ {
/*     */   private static final int ACCESS_TEST = 7;
/*  39 */   private static final Class<?>[] ORDERED_PRIMITIVE_TYPES = new Class[] { byte.class, short.class, char.class, int.class, long.class, float.class, double.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean setAccessibleWorkaround(AccessibleObject o) {
/*  56 */     if (o == null || o.isAccessible()) {
/*  57 */       return false;
/*     */     }
/*  59 */     Member m = (Member)o;
/*  60 */     if (!o.isAccessible() && Modifier.isPublic(m.getModifiers()) && isPackageAccess(m.getDeclaringClass().getModifiers())) {
/*     */       try {
/*  62 */         o.setAccessible(true);
/*  63 */         return true;
/*  64 */       } catch (SecurityException securityException) {}
/*     */     }
/*     */ 
/*     */     
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isPackageAccess(int modifiers) {
/*  77 */     return ((modifiers & 0x7) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isAccessible(Member m) {
/*  86 */     return (m != null && Modifier.isPublic(m.getModifiers()) && !m.isSynthetic());
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
/*     */   static int compareConstructorFit(Constructor<?> left, Constructor<?> right, Class<?>[] actual) {
/* 103 */     return compareParameterTypes(Executable.of(left), Executable.of(right), actual);
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
/*     */   static int compareMethodFit(Method left, Method right, Class<?>[] actual) {
/* 120 */     return compareParameterTypes(Executable.of(left), Executable.of(right), actual);
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
/*     */   private static int compareParameterTypes(Executable left, Executable right, Class<?>[] actual) {
/* 136 */     float leftCost = getTotalTransformationCost(actual, left);
/* 137 */     float rightCost = getTotalTransformationCost(actual, right);
/* 138 */     return Float.compare(leftCost, rightCost);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getTotalTransformationCost(Class<?>[] srcArgs, Executable executable) {
/* 149 */     Class<?>[] destArgs = executable.getParameterTypes();
/* 150 */     boolean isVarArgs = executable.isVarArgs();
/*     */ 
/*     */     
/* 153 */     float totalCost = 0.0F;
/* 154 */     long normalArgsLen = isVarArgs ? (destArgs.length - 1) : destArgs.length;
/* 155 */     if (srcArgs.length < normalArgsLen) {
/* 156 */       return Float.MAX_VALUE;
/*     */     }
/* 158 */     for (int i = 0; i < normalArgsLen; i++) {
/* 159 */       totalCost += getObjectTransformationCost(srcArgs[i], destArgs[i]);
/*     */     }
/* 161 */     if (isVarArgs) {
/*     */ 
/*     */       
/* 164 */       boolean noVarArgsPassed = (srcArgs.length < destArgs.length);
/* 165 */       boolean explicitArrayForVarargs = (srcArgs.length == destArgs.length && srcArgs[srcArgs.length - 1] != null && srcArgs[srcArgs.length - 1].isArray());
/*     */       
/* 167 */       float varArgsCost = 0.001F;
/* 168 */       Class<?> destClass = destArgs[destArgs.length - 1].getComponentType();
/* 169 */       if (noVarArgsPassed) {
/*     */         
/* 171 */         totalCost += getObjectTransformationCost(destClass, Object.class) + 0.001F;
/* 172 */       } else if (explicitArrayForVarargs) {
/* 173 */         Class<?> sourceClass = srcArgs[srcArgs.length - 1].getComponentType();
/* 174 */         totalCost += getObjectTransformationCost(sourceClass, destClass) + 0.001F;
/*     */       } else {
/*     */         
/* 177 */         for (int j = destArgs.length - 1; j < srcArgs.length; j++) {
/* 178 */           Class<?> srcClass = srcArgs[j];
/* 179 */           totalCost += getObjectTransformationCost(srcClass, destClass) + 0.001F;
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     return totalCost;
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
/*     */   private static float getObjectTransformationCost(Class<?> srcClass, Class<?> destClass) {
/* 195 */     if (destClass.isPrimitive()) {
/* 196 */       return getPrimitivePromotionCost(srcClass, destClass);
/*     */     }
/* 198 */     float cost = 0.0F;
/* 199 */     while (srcClass != null && !destClass.equals(srcClass)) {
/* 200 */       if (destClass.isInterface() && ClassUtils.isAssignable(srcClass, destClass)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 206 */         cost += 0.25F;
/*     */         break;
/*     */       } 
/* 209 */       cost++;
/* 210 */       srcClass = srcClass.getSuperclass();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     if (srcClass == null) {
/* 217 */       cost += 1.5F;
/*     */     }
/* 219 */     return cost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getPrimitivePromotionCost(Class<?> srcClass, Class<?> destClass) {
/* 230 */     if (srcClass == null) {
/* 231 */       return 1.5F;
/*     */     }
/* 233 */     float cost = 0.0F;
/* 234 */     Class<?> cls = srcClass;
/* 235 */     if (!cls.isPrimitive()) {
/*     */       
/* 237 */       cost += 0.1F;
/* 238 */       cls = ClassUtils.wrapperToPrimitive(cls);
/*     */     } 
/* 240 */     for (int i = 0; cls != destClass && i < ORDERED_PRIMITIVE_TYPES.length; i++) {
/* 241 */       if (cls == ORDERED_PRIMITIVE_TYPES[i]) {
/* 242 */         cost += 0.1F;
/* 243 */         if (i < ORDERED_PRIMITIVE_TYPES.length - 1) {
/* 244 */           cls = ORDERED_PRIMITIVE_TYPES[i + 1];
/*     */         }
/*     */       } 
/*     */     } 
/* 248 */     return cost;
/*     */   }
/*     */   
/*     */   static boolean isMatchingMethod(Method method, Class<?>[] parameterTypes) {
/* 252 */     return isMatchingExecutable(Executable.of(method), parameterTypes);
/*     */   }
/*     */   
/*     */   static boolean isMatchingConstructor(Constructor<?> method, Class<?>[] parameterTypes) {
/* 256 */     return isMatchingExecutable(Executable.of(method), parameterTypes);
/*     */   }
/*     */   
/*     */   private static boolean isMatchingExecutable(Executable method, Class<?>[] parameterTypes) {
/* 260 */     Class<?>[] methodParameterTypes = method.getParameterTypes();
/* 261 */     if (ClassUtils.isAssignable(parameterTypes, methodParameterTypes, true)) {
/* 262 */       return true;
/*     */     }
/*     */     
/* 265 */     if (method.isVarArgs()) {
/*     */       int i;
/* 267 */       for (i = 0; i < methodParameterTypes.length - 1 && i < parameterTypes.length; i++) {
/* 268 */         if (!ClassUtils.isAssignable(parameterTypes[i], methodParameterTypes[i], true)) {
/* 269 */           return false;
/*     */         }
/*     */       } 
/* 272 */       Class<?> varArgParameterType = methodParameterTypes[methodParameterTypes.length - 1].getComponentType();
/* 273 */       for (; i < parameterTypes.length; i++) {
/* 274 */         if (!ClassUtils.isAssignable(parameterTypes[i], varArgParameterType, true)) {
/* 275 */           return false;
/*     */         }
/*     */       } 
/* 278 */       return true;
/*     */     } 
/*     */     
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Executable
/*     */   {
/*     */     private final Class<?>[] parameterTypes;
/*     */     
/*     */     private final boolean isVarArgs;
/*     */ 
/*     */     
/*     */     private static Executable of(Method method) {
/* 293 */       return new Executable(method);
/*     */     }
/*     */     
/*     */     private static Executable of(Constructor<?> constructor) {
/* 297 */       return new Executable(constructor);
/*     */     }
/*     */     
/*     */     private Executable(Method method) {
/* 301 */       this.parameterTypes = method.getParameterTypes();
/* 302 */       this.isVarArgs = method.isVarArgs();
/*     */     }
/*     */     
/*     */     private Executable(Constructor<?> constructor) {
/* 306 */       this.parameterTypes = constructor.getParameterTypes();
/* 307 */       this.isVarArgs = constructor.isVarArgs();
/*     */     }
/*     */     
/*     */     public Class<?>[] getParameterTypes() {
/* 311 */       return this.parameterTypes;
/*     */     }
/*     */     
/*     */     public boolean isVarArgs() {
/* 315 */       return this.isVarArgs;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\reflect\MemberUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */