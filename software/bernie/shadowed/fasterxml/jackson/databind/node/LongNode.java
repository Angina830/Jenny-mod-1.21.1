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
/*     */ public class LongNode
/*     */   extends NumericNode
/*     */ {
/*     */   protected final long _value;
/*     */   
/*     */   public LongNode(long v) {
/*  26 */     this._value = v;
/*     */   } public static LongNode valueOf(long l) {
/*  28 */     return new LongNode(l);
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
/*  39 */     return JsonParser.NumberType.LONG;
/*     */   }
/*     */   
/*     */   public boolean isIntegralNumber() {
/*  43 */     return true;
/*     */   }
/*     */   public boolean isLong() {
/*  46 */     return true;
/*     */   }
/*     */   public boolean canConvertToInt() {
/*  49 */     return (this._value >= -2147483648L && this._value <= 2147483647L);
/*     */   } public boolean canConvertToLong() {
/*  51 */     return true;
/*     */   }
/*     */   
/*     */   public Number numberValue() {
/*  55 */     return Long.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  59 */     return (short)(int)this._value;
/*     */   }
/*     */   public int intValue() {
/*  62 */     return (int)this._value;
/*     */   }
/*     */   public long longValue() {
/*  65 */     return this._value;
/*     */   }
/*     */   public float floatValue() {
/*  68 */     return (float)this._value;
/*     */   }
/*     */   public double doubleValue() {
/*  71 */     return this._value;
/*     */   }
/*     */   public BigDecimal decimalValue() {
/*  74 */     return BigDecimal.valueOf(this._value);
/*     */   }
/*     */   public BigInteger bigIntegerValue() {
/*  77 */     return BigInteger.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public String asText() {
/*  81 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean asBoolean(boolean defaultValue) {
/*  86 */     return (this._value != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
/*  93 */     jg.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  99 */     if (o == this) return true; 
/* 100 */     if (o == null) return false; 
/* 101 */     if (o instanceof LongNode) {
/* 102 */       return (((LongNode)o)._value == this._value);
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     return (int)this._value ^ (int)(this._value >> 32L);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\LongNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */