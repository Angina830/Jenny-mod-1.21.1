/*    */ package org.apache.commons.lang3.concurrent;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AtomicSafeInitializer<T>
/*    */   implements ConcurrentInitializer<T>
/*    */ {
/* 58 */   private final AtomicReference<AtomicSafeInitializer<T>> factory = new AtomicReference<>();
/*    */ 
/*    */ 
/*    */   
/* 62 */   private final AtomicReference<T> reference = new AtomicReference<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final T get() throws ConcurrentException {
/*    */     T result;
/* 75 */     while ((result = this.reference.get()) == null) {
/* 76 */       if (this.factory.compareAndSet(null, this)) {
/* 77 */         this.reference.set(initialize());
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return result;
/*    */   }
/*    */   
/*    */   protected abstract T initialize() throws ConcurrentException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\AtomicSafeInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */