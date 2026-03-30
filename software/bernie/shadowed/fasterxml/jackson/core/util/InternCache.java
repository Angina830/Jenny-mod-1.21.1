/*    */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public final class InternCache
/*    */   extends ConcurrentHashMap<String, String>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static final int MAX_ENTRIES = 180;
/* 29 */   public static final InternCache instance = new InternCache();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   private final Object lock = new Object();
/*    */   private InternCache() {
/* 38 */     super(180, 0.8F, 4);
/*    */   }
/*    */   public String intern(String input) {
/* 41 */     String result = get(input);
/* 42 */     if (result != null) return result;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     if (size() >= 180)
/*    */     {
/*    */ 
/*    */ 
/*    */       
/* 54 */       synchronized (this.lock) {
/* 55 */         if (size() >= 180) {
/* 56 */           clear();
/*    */         }
/*    */       } 
/*    */     }
/* 60 */     result = input.intern();
/* 61 */     put(result, result);
/* 62 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\InternCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */