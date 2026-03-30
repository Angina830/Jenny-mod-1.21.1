/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.TreeNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RawValue;
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
/*     */ public abstract class ContainerNode<T extends ContainerNode<T>>
/*     */   extends BaseJsonNode
/*     */   implements JsonNodeCreator
/*     */ {
/*     */   protected final JsonNodeFactory _nodeFactory;
/*     */   
/*     */   protected ContainerNode(JsonNodeFactory nc) {
/*  26 */     this._nodeFactory = nc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String asText() {
/*  36 */     return "";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ArrayNode arrayNode() {
/*  65 */     return this._nodeFactory.arrayNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ArrayNode arrayNode(int capacity) {
/*  73 */     return this._nodeFactory.arrayNode(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObjectNode objectNode() {
/*  80 */     return this._nodeFactory.objectNode();
/*     */   }
/*     */   public final NullNode nullNode() {
/*  83 */     return this._nodeFactory.nullNode();
/*     */   }
/*     */   public final BooleanNode booleanNode(boolean v) {
/*  86 */     return this._nodeFactory.booleanNode(v);
/*     */   }
/*     */   public final NumericNode numberNode(byte v) {
/*  89 */     return this._nodeFactory.numberNode(v);
/*     */   } public final NumericNode numberNode(short v) {
/*  91 */     return this._nodeFactory.numberNode(v);
/*     */   } public final NumericNode numberNode(int v) {
/*  93 */     return this._nodeFactory.numberNode(v);
/*     */   }
/*     */   public final NumericNode numberNode(long v) {
/*  96 */     return this._nodeFactory.numberNode(v);
/*     */   }
/*     */   
/*     */   public final NumericNode numberNode(float v) {
/* 100 */     return this._nodeFactory.numberNode(v);
/*     */   } public final NumericNode numberNode(double v) {
/* 102 */     return this._nodeFactory.numberNode(v);
/*     */   }
/*     */   public final ValueNode numberNode(BigInteger v) {
/* 105 */     return this._nodeFactory.numberNode(v);
/*     */   } public final ValueNode numberNode(BigDecimal v) {
/* 107 */     return this._nodeFactory.numberNode(v);
/*     */   }
/*     */   public final ValueNode numberNode(Byte v) {
/* 110 */     return this._nodeFactory.numberNode(v);
/*     */   } public final ValueNode numberNode(Short v) {
/* 112 */     return this._nodeFactory.numberNode(v);
/*     */   } public final ValueNode numberNode(Integer v) {
/* 114 */     return this._nodeFactory.numberNode(v);
/*     */   } public final ValueNode numberNode(Long v) {
/* 116 */     return this._nodeFactory.numberNode(v);
/*     */   }
/*     */   public final ValueNode numberNode(Float v) {
/* 119 */     return this._nodeFactory.numberNode(v);
/*     */   } public final ValueNode numberNode(Double v) {
/* 121 */     return this._nodeFactory.numberNode(v);
/*     */   }
/*     */   public final TextNode textNode(String text) {
/* 124 */     return this._nodeFactory.textNode(text);
/*     */   }
/*     */   public final BinaryNode binaryNode(byte[] data) {
/* 127 */     return this._nodeFactory.binaryNode(data);
/*     */   } public final BinaryNode binaryNode(byte[] data, int offset, int length) {
/* 129 */     return this._nodeFactory.binaryNode(data, offset, length);
/*     */   }
/*     */   public final ValueNode pojoNode(Object pojo) {
/* 132 */     return this._nodeFactory.pojoNode(pojo);
/*     */   }
/*     */   public final ValueNode rawValueNode(RawValue value) {
/* 135 */     return this._nodeFactory.rawValueNode(value);
/*     */   }
/*     */   
/*     */   public abstract JsonToken asToken();
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public abstract JsonNode get(int paramInt);
/*     */   
/*     */   public abstract JsonNode get(String paramString);
/*     */   
/*     */   public abstract T removeAll();
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\ContainerNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */