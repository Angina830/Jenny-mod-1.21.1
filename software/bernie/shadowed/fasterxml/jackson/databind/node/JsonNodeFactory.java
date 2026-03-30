/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
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
/*     */ 
/*     */ 
/*     */ public class JsonNodeFactory
/*     */   implements Serializable, JsonNodeCreator
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final boolean _cfgBigDecimalExact;
/*  24 */   private static final JsonNodeFactory decimalsNormalized = new JsonNodeFactory(false);
/*     */   
/*  26 */   private static final JsonNodeFactory decimalsAsIs = new JsonNodeFactory(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public static final JsonNodeFactory instance = decimalsNormalized;
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
/*     */   
/*     */   public JsonNodeFactory(boolean bigDecimalExact) {
/*  63 */     this._cfgBigDecimalExact = bigDecimalExact;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonNodeFactory() {
/*  74 */     this(false);
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
/*     */   public static JsonNodeFactory withExactBigDecimals(boolean bigDecimalExact) {
/*  86 */     return bigDecimalExact ? decimalsAsIs : decimalsNormalized;
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
/*     */   public BooleanNode booleanNode(boolean v) {
/* 101 */     return v ? BooleanNode.getTrue() : BooleanNode.getFalse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NullNode nullNode() {
/* 109 */     return NullNode.getInstance();
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
/*     */   public NumericNode numberNode(byte v) {
/* 122 */     return IntNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(Byte value) {
/* 132 */     return (value == null) ? nullNode() : IntNode.valueOf(value.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumericNode numberNode(short v) {
/* 140 */     return ShortNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(Short value) {
/* 150 */     return (value == null) ? nullNode() : ShortNode.valueOf(value.shortValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumericNode numberNode(int v) {
/* 158 */     return IntNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(Integer value) {
/* 168 */     return (value == null) ? nullNode() : IntNode.valueOf(value.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumericNode numberNode(long v) {
/* 177 */     return LongNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(Long v) {
/* 187 */     if (v == null) {
/* 188 */       return nullNode();
/*     */     }
/* 190 */     return LongNode.valueOf(v.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(BigInteger v) {
/* 199 */     if (v == null) {
/* 200 */       return nullNode();
/*     */     }
/* 202 */     return BigIntegerNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumericNode numberNode(float v) {
/* 210 */     return FloatNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(Float value) {
/* 220 */     return (value == null) ? nullNode() : FloatNode.valueOf(value.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumericNode numberNode(double v) {
/* 228 */     return DoubleNode.valueOf(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode numberNode(Double value) {
/* 238 */     return (value == null) ? nullNode() : DoubleNode.valueOf(value.doubleValue());
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
/*     */   public ValueNode numberNode(BigDecimal v) {
/* 254 */     if (v == null) {
/* 255 */       return nullNode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (this._cfgBigDecimalExact) {
/* 263 */       return DecimalNode.valueOf(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     return (v.compareTo(BigDecimal.ZERO) == 0) ? DecimalNode.ZERO : DecimalNode.valueOf(v.stripTrailingZeros());
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
/*     */   public TextNode textNode(String text) {
/* 289 */     return TextNode.valueOf(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryNode binaryNode(byte[] data) {
/* 297 */     return BinaryNode.valueOf(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryNode binaryNode(byte[] data, int offset, int length) {
/* 306 */     return BinaryNode.valueOf(data, offset, length);
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
/*     */   public ArrayNode arrayNode() {
/* 319 */     return new ArrayNode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayNode arrayNode(int capacity) {
/* 327 */     return new ArrayNode(this, capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectNode objectNode() {
/* 333 */     return new ObjectNode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueNode pojoNode(Object pojo) {
/* 342 */     return new POJONode(pojo);
/*     */   }
/*     */   
/*     */   public ValueNode rawValueNode(RawValue value) {
/* 346 */     return new POJONode(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _inIntRange(long l) {
/* 357 */     int i = (int)l;
/* 358 */     long l2 = i;
/* 359 */     return (l2 == l);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\JsonNodeFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */