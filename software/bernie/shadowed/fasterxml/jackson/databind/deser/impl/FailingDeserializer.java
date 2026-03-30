/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FailingDeserializer
/*    */   extends StdDeserializer<Object>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final String _message;
/*    */   
/*    */   public FailingDeserializer(String m) {
/* 23 */     super(Object.class);
/* 24 */     this._message = m;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 29 */     ctxt.reportInputMismatch((JsonDeserializer)this, this._message, new Object[0]);
/* 30 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\FailingDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */