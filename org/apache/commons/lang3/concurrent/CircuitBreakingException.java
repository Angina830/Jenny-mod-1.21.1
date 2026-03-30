/*    */ package org.apache.commons.lang3.concurrent;
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
/*    */ public class CircuitBreakingException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1408176654686913340L;
/*    */   
/*    */   public CircuitBreakingException() {}
/*    */   
/*    */   public CircuitBreakingException(String message, Throwable cause) {
/* 46 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CircuitBreakingException(String message) {
/* 55 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CircuitBreakingException(Throwable cause) {
/* 64 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\concurrent\CircuitBreakingException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */