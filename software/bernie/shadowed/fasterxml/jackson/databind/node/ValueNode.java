/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonPointer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
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
/*     */ public abstract class ValueNode
/*     */   extends BaseJsonNode
/*     */ {
/*     */   protected JsonNode _at(JsonPointer ptr) {
/*  26 */     return MissingNode.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends JsonNode> T deepCopy() {
/*  35 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*  44 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(this, asToken()));
/*     */     
/*  46 */     serialize(g, provider);
/*  47 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  57 */     return asText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final JsonNode get(int index) {
/*  66 */     return null;
/*     */   }
/*     */   public final JsonNode path(int index) {
/*  69 */     return MissingNode.getInstance();
/*     */   }
/*     */   public final boolean has(int index) {
/*  72 */     return false;
/*     */   }
/*     */   public final boolean hasNonNull(int index) {
/*  75 */     return false;
/*     */   }
/*     */   public final JsonNode get(String fieldName) {
/*  78 */     return null;
/*     */   }
/*     */   public final JsonNode path(String fieldName) {
/*  81 */     return MissingNode.getInstance();
/*     */   }
/*     */   public final boolean has(String fieldName) {
/*  84 */     return false;
/*     */   }
/*     */   public final boolean hasNonNull(String fieldName) {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final JsonNode findValue(String fieldName) {
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObjectNode findParent(String fieldName) {
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
/* 108 */     return foundSoFar;
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
/* 113 */     return foundSoFar;
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
/* 118 */     return foundSoFar;
/*     */   }
/*     */   
/*     */   public abstract JsonToken asToken();
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\ValueNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */