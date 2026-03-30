/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MissingNode
/*     */   extends ValueNode
/*     */ {
/*  25 */   private static final MissingNode instance = new MissingNode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends software.bernie.shadowed.fasterxml.jackson.databind.JsonNode> T deepCopy() {
/*  32 */     return (T)this;
/*     */   } public static MissingNode getInstance() {
/*  34 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonNodeType getNodeType() {
/*  39 */     return JsonNodeType.MISSING;
/*     */   }
/*     */   public JsonToken asToken() {
/*  42 */     return JsonToken.NOT_AVAILABLE;
/*     */   } public String asText() {
/*  44 */     return "";
/*     */   } public String asText(String defaultValue) {
/*  46 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
/*  67 */     jg.writeNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonProcessingException {
/*  75 */     g.writeNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  89 */     return (o == this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  95 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     return JsonNodeType.MISSING.ordinal();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\MissingNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */