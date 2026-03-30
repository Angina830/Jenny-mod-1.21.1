/*    */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RuntimeJsonMappingException
/*    */   extends RuntimeException
/*    */ {
/*    */   public RuntimeJsonMappingException(JsonMappingException cause) {
/* 11 */     super((Throwable)cause);
/*    */   }
/*    */   
/*    */   public RuntimeJsonMappingException(String message) {
/* 15 */     super(message);
/*    */   }
/*    */   
/*    */   public RuntimeJsonMappingException(String message, JsonMappingException cause) {
/* 19 */     super(message, (Throwable)cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\RuntimeJsonMappingException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */