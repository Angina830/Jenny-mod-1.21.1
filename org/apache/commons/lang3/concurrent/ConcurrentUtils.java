/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class ConcurrentUtils
/*     */ {
/*     */   public static ConcurrentException extractCause(ExecutionException ex) {
/*  62 */     if (ex == null || ex.getCause() == null) {
/*  63 */       return null;
/*     */     }
/*     */     
/*  66 */     throwCause(ex);
/*  67 */     return new ConcurrentException(ex.getMessage(), ex.getCause());
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
/*     */   public static ConcurrentRuntimeException extractCauseUnchecked(ExecutionException ex) {
/*  84 */     if (ex == null || ex.getCause() == null) {
/*  85 */       return null;
/*     */     }
/*     */     
/*  88 */     throwCause(ex);
/*  89 */     return new ConcurrentRuntimeException(ex.getMessage(), ex.getCause());
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
/*     */   public static void handleCause(ExecutionException ex) throws ConcurrentException {
/* 107 */     ConcurrentException cex = extractCause(ex);
/*     */     
/* 109 */     if (cex != null) {
/* 110 */       throw cex;
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
/*     */   
/*     */   public static void handleCauseUnchecked(ExecutionException ex) {
/* 128 */     ConcurrentRuntimeException crex = extractCauseUnchecked(ex);
/*     */     
/* 130 */     if (crex != null) {
/* 131 */       throw crex;
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
/*     */   static Throwable checkedException(Throwable ex) {
/* 145 */     Validate.isTrue((ex != null && !(ex instanceof RuntimeException) && !(ex instanceof Error)), "Not a checked exception: " + ex, new Object[0]);
/*     */ 
/*     */     
/* 148 */     return ex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwCause(ExecutionException ex) {
/* 158 */     if (ex.getCause() instanceof RuntimeException) {
/* 159 */       throw (RuntimeException)ex.getCause();
/*     */     }
/*     */     
/* 162 */     if (ex.getCause() instanceof Error) {
/* 163 */       throw (Error)ex.getCause();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T initialize(ConcurrentInitializer<T> initializer) throws ConcurrentException {
/* 183 */     return (initializer != null) ? initializer.get() : null;
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
/*     */   public static <T> T initializeUnchecked(ConcurrentInitializer<T> initializer) {
/*     */     try {
/* 201 */       return initialize(initializer);
/* 202 */     } catch (ConcurrentException cex) {
/* 203 */       throw new ConcurrentRuntimeException(cex.getCause());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> V putIfAbsent(ConcurrentMap<K, V> map, K key, V value) {
/* 243 */     if (map == null) {
/* 244 */       return null;
/*     */     }
/*     */     
/* 247 */     V result = map.putIfAbsent(key, value);
/* 248 */     return (result != null) ? result : value;
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
/*     */   public static <K, V> V createIfAbsent(ConcurrentMap<K, V> map, K key, ConcurrentInitializer<V> init) throws ConcurrentException {
/* 273 */     if (map == null || init == null) {
/* 274 */       return null;
/*     */     }
/*     */     
/* 277 */     V value = map.get(key);
/* 278 */     if (value == null) {
/* 279 */       return putIfAbsent(map, key, init.get());
/*     */     }
/* 281 */     return value;
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
/*     */   public static <K, V> V createIfAbsentUnchecked(ConcurrentMap<K, V> map, K key, ConcurrentInitializer<V> init) {
/*     */     try {
/* 302 */       return createIfAbsent(map, key, init);
/* 303 */     } catch (ConcurrentException cex) {
/* 304 */       throw new ConcurrentRuntimeException(cex.getCause());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Future<T> constantFuture(T value) {
/* 325 */     return new ConstantFuture<>(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ConstantFuture<T>
/*     */     implements Future<T>
/*     */   {
/*     */     private final T value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ConstantFuture(T value) {
/* 343 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 353 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get() {
/* 361 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get(long timeout, TimeUnit unit) {
/* 370 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 379 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean cancel(boolean mayInterruptIfRunning) {
/* 388 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\ConcurrentUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */