/*      */ package org.apache.commons.lang3.reflect;
/*      */ 
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.commons.lang3.ClassUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MethodUtils
/*      */ {
/*   63 */   private static final Comparator<Method> METHOD_BY_SIGNATURE = Comparator.comparing(Method::toString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object invokeMethod(Object object, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*   98 */     return invokeMethod(object, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, (Class<?>[])null);
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
/*      */   public static Object invokeMethod(Object object, boolean forceAccess, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  121 */     return invokeMethod(object, forceAccess, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
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
/*      */   public static Object invokeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  149 */     args = ArrayUtils.nullToEmpty(args);
/*  150 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  151 */     return invokeMethod(object, methodName, args, parameterTypes);
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
/*      */   public static Object invokeMethod(Object object, boolean forceAccess, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  180 */     args = ArrayUtils.nullToEmpty(args);
/*  181 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  182 */     return invokeMethod(object, forceAccess, methodName, args, parameterTypes);
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
/*      */   public static Object invokeMethod(Object object, boolean forceAccess, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*      */     String messagePrefix;
/*  207 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/*  208 */     args = ArrayUtils.nullToEmpty(args);
/*      */ 
/*      */     
/*  211 */     Method method = null;
/*      */     
/*  213 */     if (forceAccess) {
/*  214 */       messagePrefix = "No such method: ";
/*  215 */       method = getMatchingMethod(object.getClass(), methodName, parameterTypes);
/*      */       
/*  217 */       if (method != null && !method.isAccessible()) {
/*  218 */         method.setAccessible(true);
/*      */       }
/*      */     } else {
/*  221 */       messagePrefix = "No such accessible method: ";
/*  222 */       method = getMatchingAccessibleMethod(object.getClass(), methodName, parameterTypes);
/*      */     } 
/*      */ 
/*      */     
/*  226 */     if (method == null) {
/*  227 */       throw new NoSuchMethodException(messagePrefix + methodName + "() on object: " + object
/*      */           
/*  229 */           .getClass().getName());
/*      */     }
/*  231 */     args = toVarArgs(method, args);
/*      */     
/*  233 */     return method.invoke(object, args);
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
/*      */   public static Object invokeMethod(Object object, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  259 */     return invokeMethod(object, false, methodName, args, parameterTypes);
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
/*      */   public static Object invokeExactMethod(Object object, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  283 */     return invokeExactMethod(object, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
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
/*      */   public static Object invokeExactMethod(Object object, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  306 */     args = ArrayUtils.nullToEmpty(args);
/*  307 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  308 */     return invokeExactMethod(object, methodName, args, parameterTypes);
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
/*      */   public static Object invokeExactMethod(Object object, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  334 */     args = ArrayUtils.nullToEmpty(args);
/*  335 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/*  336 */     Method method = getAccessibleMethod(object.getClass(), methodName, parameterTypes);
/*      */     
/*  338 */     if (method == null) {
/*  339 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object
/*      */           
/*  341 */           .getClass().getName());
/*      */     }
/*  343 */     return method.invoke(object, args);
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
/*      */   public static Object invokeExactStaticMethod(Class<?> cls, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  369 */     args = ArrayUtils.nullToEmpty(args);
/*  370 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/*  371 */     Method method = getAccessibleMethod(cls, methodName, parameterTypes);
/*  372 */     if (method == null) {
/*  373 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls
/*  374 */           .getName());
/*      */     }
/*  376 */     return method.invoke((Object)null, args);
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
/*      */   public static Object invokeStaticMethod(Class<?> cls, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  406 */     args = ArrayUtils.nullToEmpty(args);
/*  407 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  408 */     return invokeStaticMethod(cls, methodName, args, parameterTypes);
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
/*      */   public static Object invokeStaticMethod(Class<?> cls, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  437 */     args = ArrayUtils.nullToEmpty(args);
/*  438 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/*  439 */     Method method = getMatchingAccessibleMethod(cls, methodName, parameterTypes);
/*      */     
/*  441 */     if (method == null) {
/*  442 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls
/*  443 */           .getName());
/*      */     }
/*  445 */     args = toVarArgs(method, args);
/*  446 */     return method.invoke((Object)null, args);
/*      */   }
/*      */   
/*      */   private static Object[] toVarArgs(Method method, Object[] args) {
/*  450 */     if (method.isVarArgs()) {
/*  451 */       Class<?>[] methodParameterTypes = method.getParameterTypes();
/*  452 */       args = getVarArgs(args, methodParameterTypes);
/*      */     } 
/*  454 */     return args;
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
/*      */   static Object[] getVarArgs(Object[] args, Class<?>[] methodParameterTypes) {
/*  468 */     if (args.length == methodParameterTypes.length && (args[args.length - 1] == null || args[args.length - 1]
/*  469 */       .getClass().equals(methodParameterTypes[methodParameterTypes.length - 1])))
/*      */     {
/*  471 */       return args;
/*      */     }
/*      */ 
/*      */     
/*  475 */     Object[] newArgs = new Object[methodParameterTypes.length];
/*      */ 
/*      */     
/*  478 */     System.arraycopy(args, 0, newArgs, 0, methodParameterTypes.length - 1);
/*      */ 
/*      */     
/*  481 */     Class<?> varArgComponentType = methodParameterTypes[methodParameterTypes.length - 1].getComponentType();
/*  482 */     int varArgLength = args.length - methodParameterTypes.length + 1;
/*      */     
/*  484 */     Object varArgsArray = Array.newInstance(ClassUtils.primitiveToWrapper(varArgComponentType), varArgLength);
/*      */     
/*  486 */     System.arraycopy(args, methodParameterTypes.length - 1, varArgsArray, 0, varArgLength);
/*      */     
/*  488 */     if (varArgComponentType.isPrimitive())
/*      */     {
/*  490 */       varArgsArray = ArrayUtils.toPrimitive(varArgsArray);
/*      */     }
/*      */ 
/*      */     
/*  494 */     newArgs[methodParameterTypes.length - 1] = varArgsArray;
/*      */ 
/*      */     
/*  497 */     return newArgs;
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
/*      */   public static Object invokeExactStaticMethod(Class<?> cls, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  521 */     args = ArrayUtils.nullToEmpty(args);
/*  522 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  523 */     return invokeExactStaticMethod(cls, methodName, args, parameterTypes);
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
/*      */   public static Method getAccessibleMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*      */     try {
/*  541 */       return getAccessibleMethod(cls.getMethod(methodName, parameterTypes));
/*      */     }
/*  543 */     catch (NoSuchMethodException e) {
/*  544 */       return null;
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
/*      */   public static Method getAccessibleMethod(Method method) {
/*  557 */     if (!MemberUtils.isAccessible(method)) {
/*  558 */       return null;
/*      */     }
/*      */     
/*  561 */     Class<?> cls = method.getDeclaringClass();
/*  562 */     if (Modifier.isPublic(cls.getModifiers())) {
/*  563 */       return method;
/*      */     }
/*  565 */     String methodName = method.getName();
/*  566 */     Class<?>[] parameterTypes = method.getParameterTypes();
/*      */ 
/*      */     
/*  569 */     method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes);
/*      */ 
/*      */ 
/*      */     
/*  573 */     if (method == null) {
/*  574 */       method = getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
/*      */     }
/*      */     
/*  577 */     return method;
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
/*      */   private static Method getAccessibleMethodFromSuperclass(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*  592 */     Class<?> parentClass = cls.getSuperclass();
/*  593 */     while (parentClass != null) {
/*  594 */       if (Modifier.isPublic(parentClass.getModifiers())) {
/*      */         try {
/*  596 */           return parentClass.getMethod(methodName, parameterTypes);
/*  597 */         } catch (NoSuchMethodException e) {
/*  598 */           return null;
/*      */         } 
/*      */       }
/*  601 */       parentClass = parentClass.getSuperclass();
/*      */     } 
/*  603 */     return null;
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
/*      */   private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*  624 */     for (; cls != null; cls = cls.getSuperclass()) {
/*      */ 
/*      */       
/*  627 */       Class<?>[] interfaces = cls.getInterfaces();
/*  628 */       for (Class<?> anInterface : interfaces) {
/*      */         
/*  630 */         if (Modifier.isPublic(anInterface.getModifiers()))
/*      */           
/*      */           try {
/*      */ 
/*      */             
/*  635 */             return anInterface.getDeclaredMethod(methodName, parameterTypes);
/*      */           }
/*  637 */           catch (NoSuchMethodException noSuchMethodException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  644 */             Method method = getAccessibleMethodFromInterfaceNest(anInterface, methodName, parameterTypes);
/*      */             
/*  646 */             if (method != null)
/*  647 */               return method; 
/*      */           }  
/*      */       } 
/*      */     } 
/*  651 */     return null;
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
/*      */   public static Method getMatchingAccessibleMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*      */     try {
/*  679 */       Method method = cls.getMethod(methodName, parameterTypes);
/*  680 */       MemberUtils.setAccessibleWorkaround(method);
/*  681 */       return method;
/*  682 */     } catch (NoSuchMethodException noSuchMethodException) {
/*      */ 
/*      */       
/*  685 */       Method[] methods = cls.getMethods();
/*  686 */       List<Method> matchingMethods = new ArrayList<>();
/*  687 */       for (Method method : methods) {
/*      */         
/*  689 */         if (method.getName().equals(methodName) && 
/*  690 */           MemberUtils.isMatchingMethod(method, parameterTypes)) {
/*  691 */           matchingMethods.add(method);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  696 */       matchingMethods.sort(METHOD_BY_SIGNATURE);
/*      */       
/*  698 */       Method bestMatch = null;
/*  699 */       for (Method method : matchingMethods) {
/*      */         
/*  701 */         Method accessibleMethod = getAccessibleMethod(method);
/*  702 */         if (accessibleMethod != null && (bestMatch == null || MemberUtils.compareMethodFit(accessibleMethod, bestMatch, parameterTypes) < 0))
/*      */         {
/*      */ 
/*      */           
/*  706 */           bestMatch = accessibleMethod;
/*      */         }
/*      */       } 
/*  709 */       if (bestMatch != null) {
/*  710 */         MemberUtils.setAccessibleWorkaround(bestMatch);
/*      */       }
/*      */       
/*  713 */       if (bestMatch != null && bestMatch.isVarArgs() && (bestMatch.getParameterTypes()).length > 0 && parameterTypes.length > 0) {
/*  714 */         Class<?>[] methodParameterTypes = bestMatch.getParameterTypes();
/*  715 */         Class<?> methodParameterComponentType = methodParameterTypes[methodParameterTypes.length - 1].getComponentType();
/*  716 */         String methodParameterComponentTypeName = ClassUtils.primitiveToWrapper(methodParameterComponentType).getName();
/*      */         
/*  718 */         Class<?> lastParameterType = parameterTypes[parameterTypes.length - 1];
/*  719 */         String parameterTypeName = (lastParameterType == null) ? null : lastParameterType.getName();
/*  720 */         String parameterTypeSuperClassName = (lastParameterType == null) ? null : lastParameterType.getSuperclass().getName();
/*      */         
/*  722 */         if (parameterTypeName != null && parameterTypeSuperClassName != null && !methodParameterComponentTypeName.equals(parameterTypeName) && 
/*  723 */           !methodParameterComponentTypeName.equals(parameterTypeSuperClassName)) {
/*  724 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  728 */       return bestMatch;
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
/*      */   public static Method getMatchingMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*  743 */     Validate.notNull(cls, "Null class not allowed.", new Object[0]);
/*  744 */     Validate.notEmpty(methodName, "Null or blank methodName not allowed.", new Object[0]);
/*      */ 
/*      */     
/*  747 */     Method[] methodArray = cls.getDeclaredMethods();
/*  748 */     List<Class<?>> superclassList = ClassUtils.getAllSuperclasses(cls);
/*  749 */     for (Class<?> klass : superclassList) {
/*  750 */       methodArray = (Method[])ArrayUtils.addAll((Object[])methodArray, (Object[])klass.getDeclaredMethods());
/*      */     }
/*      */     
/*  753 */     Method inexactMatch = null;
/*  754 */     for (Method method : methodArray) {
/*  755 */       if (methodName.equals(method.getName()) && 
/*  756 */         Objects.deepEquals(parameterTypes, method.getParameterTypes()))
/*  757 */         return method; 
/*  758 */       if (methodName.equals(method.getName()) && 
/*  759 */         ClassUtils.isAssignable(parameterTypes, method.getParameterTypes(), true) && (
/*  760 */         inexactMatch == null || distance(parameterTypes, method.getParameterTypes()) < 
/*  761 */         distance(parameterTypes, inexactMatch.getParameterTypes()))) {
/*  762 */         inexactMatch = method;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  767 */     return inexactMatch;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int distance(Class<?>[] classArray, Class<?>[] toClassArray) {
/*  778 */     int answer = 0;
/*      */     
/*  780 */     if (!ClassUtils.isAssignable(classArray, toClassArray, true)) {
/*  781 */       return -1;
/*      */     }
/*  783 */     for (int offset = 0; offset < classArray.length; offset++) {
/*      */       
/*  785 */       if (!classArray[offset].equals(toClassArray[offset]))
/*      */       {
/*  787 */         if (ClassUtils.isAssignable(classArray[offset], toClassArray[offset], true) && 
/*  788 */           !ClassUtils.isAssignable(classArray[offset], toClassArray[offset], false)) {
/*  789 */           answer++;
/*      */         } else {
/*  791 */           answer += 2;
/*      */         } 
/*      */       }
/*      */     } 
/*  795 */     return answer;
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
/*      */   public static Set<Method> getOverrideHierarchy(Method method, ClassUtils.Interfaces interfacesBehavior) {
/*  807 */     Validate.notNull(method);
/*  808 */     Set<Method> result = new LinkedHashSet<>();
/*  809 */     result.add(method);
/*      */     
/*  811 */     Class<?>[] parameterTypes = method.getParameterTypes();
/*      */     
/*  813 */     Class<?> declaringClass = method.getDeclaringClass();
/*      */     
/*  815 */     Iterator<Class<?>> hierarchy = ClassUtils.hierarchy(declaringClass, interfacesBehavior).iterator();
/*      */     
/*  817 */     hierarchy.next();
/*  818 */     label21: while (hierarchy.hasNext()) {
/*  819 */       Class<?> c = hierarchy.next();
/*  820 */       Method m = getMatchingAccessibleMethod(c, method.getName(), parameterTypes);
/*  821 */       if (m == null) {
/*      */         continue;
/*      */       }
/*  824 */       if (Arrays.equals((Object[])m.getParameterTypes(), (Object[])parameterTypes)) {
/*      */         
/*  826 */         result.add(m);
/*      */         
/*      */         continue;
/*      */       } 
/*  830 */       Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(declaringClass, m.getDeclaringClass());
/*  831 */       for (int i = 0; i < parameterTypes.length; i++) {
/*  832 */         Type childType = TypeUtils.unrollVariables(typeArguments, method.getGenericParameterTypes()[i]);
/*  833 */         Type parentType = TypeUtils.unrollVariables(typeArguments, m.getGenericParameterTypes()[i]);
/*  834 */         if (!TypeUtils.equals(childType, parentType)) {
/*      */           continue label21;
/*      */         }
/*      */       } 
/*  838 */       result.add(m);
/*      */     } 
/*  840 */     return result;
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
/*      */   public static Method[] getMethodsWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
/*  854 */     return getMethodsWithAnnotation(cls, annotationCls, false, false);
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
/*      */   public static List<Method> getMethodsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
/*  869 */     return getMethodsListWithAnnotation(cls, annotationCls, false, false);
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
/*      */   public static Method[] getMethodsWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls, boolean searchSupers, boolean ignoreAccess) {
/*  888 */     List<Method> annotatedMethodsList = getMethodsListWithAnnotation(cls, annotationCls, searchSupers, ignoreAccess);
/*      */     
/*  890 */     return annotatedMethodsList.<Method>toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
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
/*      */   public static List<Method> getMethodsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls, boolean searchSupers, boolean ignoreAccess) {
/*  911 */     Validate.notNull(cls, "The class must not be null", new Object[0]);
/*  912 */     Validate.notNull(annotationCls, "The annotation class must not be null", new Object[0]);
/*  913 */     List<Class<?>> classes = searchSupers ? getAllSuperclassesAndInterfaces(cls) : new ArrayList<>();
/*      */     
/*  915 */     classes.add(0, cls);
/*  916 */     List<Method> annotatedMethods = new ArrayList<>();
/*  917 */     for (Class<?> acls : classes) {
/*  918 */       Method[] methods = ignoreAccess ? acls.getDeclaredMethods() : acls.getMethods();
/*  919 */       for (Method method : methods) {
/*  920 */         if (method.getAnnotation(annotationCls) != null) {
/*  921 */           annotatedMethods.add(method);
/*      */         }
/*      */       } 
/*      */     } 
/*  925 */     return annotatedMethods;
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
/*      */   public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationCls, boolean searchSupers, boolean ignoreAccess) {
/*  953 */     Validate.notNull(method, "The method must not be null", new Object[0]);
/*  954 */     Validate.notNull(annotationCls, "The annotation class must not be null", new Object[0]);
/*  955 */     if (!ignoreAccess && !MemberUtils.isAccessible(method)) {
/*  956 */       return null;
/*      */     }
/*      */     
/*  959 */     A annotation = method.getAnnotation(annotationCls);
/*      */     
/*  961 */     if (annotation == null && searchSupers) {
/*  962 */       Class<?> mcls = method.getDeclaringClass();
/*  963 */       List<Class<?>> classes = getAllSuperclassesAndInterfaces(mcls);
/*  964 */       for (Class<?> acls : classes) {
/*      */         
/*  966 */         Method equivalentMethod = ignoreAccess ? getMatchingMethod(acls, method.getName(), method.getParameterTypes()) : getMatchingAccessibleMethod(acls, method.getName(), method.getParameterTypes());
/*  967 */         if (equivalentMethod != null) {
/*  968 */           annotation = equivalentMethod.getAnnotation(annotationCls);
/*  969 */           if (annotation != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  976 */     return annotation;
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
/*      */   private static List<Class<?>> getAllSuperclassesAndInterfaces(Class<?> cls) {
/*  990 */     if (cls == null) {
/*  991 */       return null;
/*      */     }
/*      */     
/*  994 */     List<Class<?>> allSuperClassesAndInterfaces = new ArrayList<>();
/*  995 */     List<Class<?>> allSuperclasses = ClassUtils.getAllSuperclasses(cls);
/*  996 */     int superClassIndex = 0;
/*  997 */     List<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(cls);
/*  998 */     int interfaceIndex = 0;
/*  999 */     while (interfaceIndex < allInterfaces.size() || superClassIndex < allSuperclasses
/* 1000 */       .size()) {
/*      */       Class<?> acls;
/* 1002 */       if (interfaceIndex >= allInterfaces.size()) {
/* 1003 */         acls = allSuperclasses.get(superClassIndex++);
/* 1004 */       } else if (superClassIndex >= allSuperclasses.size() || interfaceIndex < superClassIndex || superClassIndex >= interfaceIndex) {
/* 1005 */         acls = allInterfaces.get(interfaceIndex++);
/*      */       } else {
/* 1007 */         acls = allSuperclasses.get(superClassIndex++);
/*      */       } 
/* 1009 */       allSuperClassesAndInterfaces.add(acls);
/*      */     } 
/* 1011 */     return allSuperClassesAndInterfaces;
/*      */   }
/*      */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\reflect\MethodUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */