/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NullNode
/*    */   extends ValueNode
/*    */ {
/* 18 */   public static final NullNode instance = new NullNode();
/*    */ 
/*    */   
/*    */   public static NullNode getInstance() {
/* 22 */     return instance;
/*    */   }
/*    */   
/*    */   public JsonNodeType getNodeType() {
/* 26 */     return JsonNodeType.NULL;
/*    */   }
/*    */   public JsonToken asToken() {
/* 29 */     return JsonToken.VALUE_NULL;
/*    */   }
/* 31 */   public String asText(String defaultValue) { return defaultValue; } public String asText() {
/* 32 */     return "null";
/*    */   }
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
/*    */   public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
/* 47 */     provider.defaultSerializeNull(g);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 52 */     return (o == this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 57 */     return JsonNodeType.NULL.ordinal();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\NullNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */