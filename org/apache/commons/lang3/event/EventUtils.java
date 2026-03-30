/*     */ package org.apache.commons.lang3.event;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.reflect.MethodUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventUtils
/*     */ {
/*     */   public static <L> void addEventListener(Object eventSource, Class<L> listenerType, L listener) {
/*     */     try {
/*  49 */       MethodUtils.invokeMethod(eventSource, "add" + listenerType.getSimpleName(), new Object[] { listener });
/*  50 */     } catch (NoSuchMethodException e) {
/*  51 */       throw new IllegalArgumentException("Class " + eventSource.getClass().getName() + " does not have a public add" + listenerType
/*  52 */           .getSimpleName() + " method which takes a parameter of type " + listenerType
/*  53 */           .getName() + ".");
/*  54 */     } catch (IllegalAccessException e) {
/*  55 */       throw new IllegalArgumentException("Class " + eventSource.getClass().getName() + " does not have an accessible add" + listenerType
/*  56 */           .getSimpleName() + " method which takes a parameter of type " + listenerType
/*  57 */           .getName() + ".");
/*  58 */     } catch (InvocationTargetException e) {
/*  59 */       throw new RuntimeException("Unable to add listener.", e.getCause());
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
/*     */   public static <L> void bindEventsToMethod(Object target, String methodName, Object eventSource, Class<L> listenerType, String... eventTypes) {
/*  76 */     L listener = listenerType.cast(Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[] { listenerType }, new EventBindingInvocationHandler(target, methodName, eventTypes)));
/*     */     
/*  78 */     addEventListener(eventSource, listenerType, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EventBindingInvocationHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     private final Object target;
/*     */     
/*     */     private final String methodName;
/*     */     
/*     */     private final Set<String> eventTypes;
/*     */ 
/*     */     
/*     */     EventBindingInvocationHandler(Object target, String methodName, String[] eventTypes) {
/*  94 */       this.target = target;
/*  95 */       this.methodName = methodName;
/*  96 */       this.eventTypes = new HashSet<>(Arrays.asList(eventTypes));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
/* 110 */       if (this.eventTypes.isEmpty() || this.eventTypes.contains(method.getName())) {
/* 111 */         if (hasMatchingParametersMethod(method)) {
/* 112 */           return MethodUtils.invokeMethod(this.target, this.methodName, parameters);
/*     */         }
/* 114 */         return MethodUtils.invokeMethod(this.target, this.methodName);
/*     */       } 
/* 116 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hasMatchingParametersMethod(Method method) {
/* 126 */       return (MethodUtils.getAccessibleMethod(this.target.getClass(), this.methodName, method.getParameterTypes()) != null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\event\EventUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */