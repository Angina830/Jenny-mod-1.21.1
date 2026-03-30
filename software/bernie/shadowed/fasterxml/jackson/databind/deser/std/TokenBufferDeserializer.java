/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;
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
/*    */ @JacksonStdImpl
/*    */ public class TokenBufferDeserializer
/*    */   extends StdScalarDeserializer<TokenBuffer>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public TokenBufferDeserializer() {
/* 27 */     super(TokenBuffer.class);
/*    */   }
/*    */   
/*    */   public TokenBuffer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 31 */     return createBufferInstance(p).deserialize(p, ctxt);
/*    */   }
/*    */   
/*    */   protected TokenBuffer createBufferInstance(JsonParser p) {
/* 35 */     return new TokenBuffer(p);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\TokenBufferDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */