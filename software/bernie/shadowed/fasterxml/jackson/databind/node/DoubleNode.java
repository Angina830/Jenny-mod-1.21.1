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
/*     */ public class DoubleNode
/*     */   extends NumericNode
/*     */ {
/*     */   protected final double _value;
/*     */   
/*     */   public DoubleNode(double v) {
/*  27 */     this._value = v;
/*     */   } public static DoubleNode valueOf(double v) {
/*  29 */     return new DoubleNode(v);
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
/*  40 */     return JsonParser.NumberType.DOUBLE;
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
/*     */   public boolean isDouble() {
/*  52 */     return true;
/*     */   }
/*     */   public boolean canConvertToInt() {
/*  55 */     return (this._value >= -2.147483648E9D && this._value <= 2.147483647E9D);
/*     */   }
/*     */   public boolean canConvertToLong() {
/*  58 */     return (this._value >= -9.223372036854776E18D && this._value <= 9.223372036854776E18D);
/*     */   }
/*     */ 
/*     */   
/*     */   public Number numberValue() {
/*  63 */     return Double.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  67 */     return (short)(int)this._value;
/*     */   }
/*     */   public int intValue() {
/*  70 */     return (int)this._value;
/*     */   }
/*     */   public long longValue() {
/*  73 */     return (long)this._value;
/*     */   }
/*     */   public float floatValue() {
/*  76 */     return (float)this._value;
/*     */   }
/*     */   public double doubleValue() {
/*  79 */     return this._value;
/*     */   }
/*     */   public BigDecimal decimalValue() {
/*  82 */     return BigDecimal.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public BigInteger bigIntegerValue() {
/*  86 */     return decimalValue().toBigInteger();
/*     */   }
/*     */ 
/*     */   
/*     */   public String asText() {
/*  91 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/*  97 */     return (Double.isNaN(this._value) || Double.isInfinite(this._value));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
/* 102 */     g.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 108 */     if (o == this) return true; 
/* 109 */     if (o == null) return false; 
/* 110 */     if (o instanceof DoubleNode) {
/*     */ 
/*     */       
/* 113 */       double otherValue = ((DoubleNode)o)._value;
/* 114 */       return (Double.compare(this._value, otherValue) == 0);
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     long l = Double.doubleToLongBits(this._value);
/* 124 */     return (int)l ^ (int)(l >> 32L);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\DoubleNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */