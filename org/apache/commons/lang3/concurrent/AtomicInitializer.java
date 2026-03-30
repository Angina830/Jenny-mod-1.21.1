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
/*    */ public abstract class AtomicInitializer<T>
/*    */   implements ConcurrentInitializer<T>
/*    */ {
/* 68 */   private final AtomicReference<T> reference = new AtomicReference<>();
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
/*    */   public T get() throws ConcurrentException {
/* 81 */     T result = this.reference.get();
/*    */     
/* 83 */     if (result == null) {
/* 84 */       result = initialize();
/* 85 */       if (!this.reference.compareAndSet(null, result))
/*    */       {
/* 87 */         result = this.reference.get();
/*    */       }
/*    */     } 
/*    */     
/* 91 */     return result;
/*    */   }
/*    */   
/*    */   protected abstract T initialize() throws ConcurrentException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\AtomicInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */