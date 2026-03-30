/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
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
/*     */ 
/*     */ 
/*     */ public class FloatNode
/*     */   extends NumericNode
/*     */ {
/*     */   protected final float _value;
/*     */   
/*     */   public FloatNode(float v) {
/*  27 */     this._value = v;
/*     */   } public static FloatNode valueOf(float v) {
/*  29 */     return new FloatNode(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken asToken() {
/*  37 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*     */   }
/*     */   public JsonParser.NumberType numberType() {
/*  40 */     return JsonParser.NumberType.FLOAT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFloatingPointNumber() {
/*  49 */     return true;
/*     */   }
/*     */   public boolean isFloat() {
/*  52 */     return true;
/*     */   }
/*     */   public boolean canConvertToInt() {
/*  55 */     return (this._value >= -2.14748365E9F && this._value <= 2.14748365E9F);
/*     */   }
/*     */   
/*     */   public boolean canConvertToLong() {
/*  59 */     return (this._value >= -9.223372E18F && this._value <= 9.223372E18F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Number numberValue() {
/*  64 */     return Float.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  68 */     return (short)(int)this._value;
/*     */   }
/*     */   public int intValue() {
/*  71 */     return (int)this._value;
/*     */   }
/*     */   public long longValue() {
/*  74 */     return (long)this._value;
/*     */   }
/*     */   public float floatValue() {
/*  77 */     return this._value;
/*     */   }
/*     */   public double doubleValue() {
/*  80 */     return this._value;
/*     */   }
/*     */   public BigDecimal decimalValue() {
/*  83 */     return BigDecimal.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public BigInteger bigIntegerValue() {
/*  87 */     return decimalValue().toBigInteger();
/*     */   }
/*     */ 
/*     */   
/*     */   public String asText() {
/*  92 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/*  98 */     return (Float.isNaN(this._value) || Float.isInfinite(this._value));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
/* 103 */     g.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 109 */     if (o == this) return true; 
/* 110 */     if (o == null) return false; 
/* 111 */     if (o instanceof FloatNode) {
/*     */ 
/*     */       
/* 114 */       float otherValue = ((FloatNode)o)._value;
/* 115 */       return (Float.compare(this._value, otherValue) == 0);
/*     */     } 
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Float.floatToIntBits(this._value);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\FloatNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */