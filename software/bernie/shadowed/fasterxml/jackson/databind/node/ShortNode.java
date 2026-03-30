/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberOutput;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShortNode
/*     */   extends NumericNode
/*     */ {
/*     */   protected final short _value;
/*     */   
/*     */   public ShortNode(short v) {
/*  26 */     this._value = v;
/*     */   } public static ShortNode valueOf(short l) {
/*  28 */     return new ShortNode(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken asToken() {
/*  36 */     return JsonToken.VALUE_NUMBER_INT;
/*     */   }
/*     */   public JsonParser.NumberType numberType() {
/*  39 */     return JsonParser.NumberType.INT;
/*     */   }
/*     */   
/*     */   public boolean isIntegralNumber() {
/*  43 */     return true;
/*     */   }
/*     */   public boolean isShort() {
/*  46 */     return true;
/*     */   }
/*  48 */   public boolean canConvertToInt() { return true; } public boolean canConvertToLong() {
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public Number numberValue() {
/*  53 */     return Short.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  57 */     return this._value;
/*     */   }
/*     */   public int intValue() {
/*  60 */     return this._value;
/*     */   }
/*     */   public long longValue() {
/*  63 */     return this._value;
/*     */   }
/*     */   public float floatValue() {
/*  66 */     return this._value;
/*     */   }
/*     */   public double doubleValue() {
/*  69 */     return this._value;
/*     */   }
/*     */   public BigDecimal decimalValue() {
/*  72 */     return BigDecimal.valueOf(this._value);
/*     */   }
/*     */   public BigInteger bigIntegerValue() {
/*  75 */     return BigInteger.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public String asText() {
/*  79 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean asBoolean(boolean defaultValue) {
/*  84 */     return (this._value != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
/*  91 */     jg.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  97 */     if (o == this) return true; 
/*  98 */     if (o == null) return false; 
/*  99 */     if (o instanceof ShortNode) {
/* 100 */       return (((ShortNode)o)._value == this._value);
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return this._value;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\ShortNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */