/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
/*    */ 
/*    */ 
/*    */ public abstract class StdScalarDeserializer<T>
/*    */   extends StdDeserializer<T>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected StdScalarDeserializer(Class<?> vc) {
/* 18 */     super(vc); } protected StdScalarDeserializer(JavaType valueType) {
/* 19 */     super(valueType);
/*    */   }
/*    */   protected StdScalarDeserializer(StdScalarDeserializer<?> src) {
/* 22 */     super(src);
/*    */   }
/*    */   
/*    */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 26 */     return typeDeserializer.deserializeTypedFromScalar(p, ctxt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T deserialize(JsonParser p, DeserializationContext ctxt, T intoValue) throws IOException {
/* 36 */     ctxt.reportBadMerge(this);
/*    */     
/* 38 */     return (T)deserialize(p, ctxt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 47 */     return Boolean.FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AccessPattern getNullAccessPattern() {
/* 53 */     return AccessPattern.ALWAYS_NULL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AccessPattern getEmptyAccessPattern() {
/* 60 */     return AccessPattern.CONSTANT;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StdScalarDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */