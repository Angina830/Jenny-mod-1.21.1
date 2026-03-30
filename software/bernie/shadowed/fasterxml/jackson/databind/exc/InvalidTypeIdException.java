/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.exc;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
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
/*    */ public class InvalidTypeIdException
/*    */   extends MismatchedInputException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final JavaType _baseType;
/*    */   protected final String _typeId;
/*    */   
/*    */   public InvalidTypeIdException(JsonParser p, String msg, JavaType baseType, String typeId) {
/* 36 */     super(p, msg);
/* 37 */     this._baseType = baseType;
/* 38 */     this._typeId = typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public static InvalidTypeIdException from(JsonParser p, String msg, JavaType baseType, String typeId) {
/* 43 */     return new InvalidTypeIdException(p, msg, baseType, typeId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JavaType getBaseType() {
/* 52 */     return this._baseType; } public String getTypeId() {
/* 53 */     return this._typeId;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\exc\InvalidTypeIdException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */