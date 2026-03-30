/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ 
/*    */ public class AtomicBooleanDeserializer extends StdScalarDeserializer<AtomicBoolean> {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public AtomicBooleanDeserializer() {
/* 13 */     super(AtomicBoolean.class);
/*    */   }
/*    */   
/*    */   public AtomicBoolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
/* 17 */     return new AtomicBoolean(_parseBooleanPrimitive(jp, ctxt));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\AtomicBooleanDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */