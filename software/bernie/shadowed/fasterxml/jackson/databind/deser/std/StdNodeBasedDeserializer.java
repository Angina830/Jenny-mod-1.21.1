/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*    */ public abstract class StdNodeBasedDeserializer<T>
/*    */   extends StdDeserializer<T>
/*    */   implements ResolvableDeserializer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected JsonDeserializer<Object> _treeDeserializer;
/*    */   
/*    */   protected StdNodeBasedDeserializer(JavaType targetType) {
/* 35 */     super(targetType);
/*    */   }
/*    */   
/*    */   protected StdNodeBasedDeserializer(Class<T> targetType) {
/* 39 */     super(targetType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected StdNodeBasedDeserializer(StdNodeBasedDeserializer<?> src) {
/* 47 */     super(src);
/* 48 */     this._treeDeserializer = src._treeDeserializer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolve(DeserializationContext ctxt) throws JsonMappingException {
/* 53 */     this._treeDeserializer = ctxt.findRootValueDeserializer(ctxt.constructType(JsonNode.class));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract T convert(JsonNode paramJsonNode, DeserializationContext paramDeserializationContext) throws IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
/* 72 */     JsonNode n = (JsonNode)this._treeDeserializer.deserialize(jp, ctxt);
/* 73 */     return convert(n, ctxt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer td) throws IOException, JsonProcessingException {
/* 84 */     JsonNode n = (JsonNode)this._treeDeserializer.deserializeWithType(jp, ctxt, td);
/* 85 */     return convert(n, ctxt);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StdNodeBasedDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */