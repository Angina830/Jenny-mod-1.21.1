/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ 
/*     */ 
/*     */ public class BigIntegerNode
/*     */   extends NumericNode
/*     */ {
/*  16 */   private static final BigInteger MIN_INTEGER = BigInteger.valueOf(-2147483648L);
/*  17 */   private static final BigInteger MAX_INTEGER = BigInteger.valueOf(2147483647L);
/*  18 */   private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
/*  19 */   private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BigInteger _value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigIntegerNode(BigInteger v) {
/*  29 */     this._value = v;
/*     */   } public static BigIntegerNode valueOf(BigInteger v) {
/*  31 */     return new BigIntegerNode(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken asToken() {
/*  40 */     return JsonToken.VALUE_NUMBER_INT;
/*     */   }
/*     */   public JsonParser.NumberType numberType() {
/*  43 */     return JsonParser.NumberType.BIG_INTEGER;
/*     */   }
/*     */   public boolean isIntegralNumber() {
/*  46 */     return true;
/*     */   }
/*     */   public boolean isBigInteger() {
/*  49 */     return true;
/*     */   }
/*     */   public boolean canConvertToInt() {
/*  52 */     return (this._value.compareTo(MIN_INTEGER) >= 0 && this._value.compareTo(MAX_INTEGER) <= 0);
/*     */   }
/*     */   public boolean canConvertToLong() {
/*  55 */     return (this._value.compareTo(MIN_LONG) >= 0 && this._value.compareTo(MAX_LONG) <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Number numberValue() {
/*  60 */     return this._value;
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  64 */     return this._value.shortValue();
/*     */   }
/*     */   public int intValue() {
/*  67 */     return this._value.intValue();
/*     */   }
/*     */   public long longValue() {
/*  70 */     return this._value.longValue();
/*     */   }
/*     */   public BigInteger bigIntegerValue() {
/*  73 */     return this._value;
/*     */   }
/*     */   public float floatValue() {
/*  76 */     return this._value.floatValue();
/*     */   }
/*     */   public double doubleValue() {
/*  79 */     return this._value.doubleValue();
/*     */   }
/*     */   public BigDecimal decimalValue() {
/*  82 */     return new BigDecimal(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String asText() {
/*  92 */     return this._value.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean asBoolean(boolean defaultValue) {
/*  97 */     return !BigInteger.ZERO.equals(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
/* 104 */     jg.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 110 */     if (o == this) return true; 
/* 111 */     if (o == null) return false; 
/* 112 */     if (!(o instanceof BigIntegerNode)) {
/* 113 */       return false;
/*     */     }
/* 115 */     return ((BigIntegerNode)o)._value.equals(this._value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return this._value.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\BigIntegerNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */