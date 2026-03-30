/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LazyInitializer<T>
/*     */   implements ConcurrentInitializer<T>
/*     */ {
/*  82 */   private static final Object NO_INIT = new Object();
/*     */   
/*  84 */   private volatile T object = (T)NO_INIT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get() throws ConcurrentException {
/* 100 */     T result = this.object;
/*     */     
/* 102 */     if (result == NO_INIT) {
/* 103 */       synchronized (this) {
/* 104 */         result = this.object;
/* 105 */         if (result == NO_INIT) {
/* 106 */           this.object = result = initialize();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 111 */     return result;
/*     */   }
/*     */   
/*     */   protected abstract T initialize() throws ConcurrentException;
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\LazyInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */