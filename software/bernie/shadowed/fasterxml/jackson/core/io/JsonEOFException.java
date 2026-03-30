/*    */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
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
/*    */ public class JsonEOFException
/*    */   extends JsonParseException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final JsonToken _token;
/*    */   
/*    */   public JsonEOFException(JsonParser p, JsonToken token, String msg) {
/* 26 */     super(p, msg);
/* 27 */     this._token = token;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonToken getTokenBeingDecoded() {
/* 35 */     return this._token;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\JsonEOFException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */