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
/*     */ 
/*     */ public class DecimalNode
/*     */   extends NumericNode
/*     */ {
/*  17 */   public static final DecimalNode ZERO = new DecimalNode(BigDecimal.ZERO);
/*     */   
/*  19 */   private static final BigDecimal MIN_INTEGER = BigDecimal.valueOf(-2147483648L);
/*  20 */   private static final BigDecimal MAX_INTEGER = BigDecimal.valueOf(2147483647L);
/*  21 */   private static final BigDecimal MIN_LONG = BigDecimal.valueOf(Long.MIN_VALUE);
/*  22 */   private static final BigDecimal MAX_LONG = BigDecimal.valueOf(Long.MAX_VALUE);
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BigDecimal _value;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecimalNode(BigDecimal v) {
/*  32 */     this._value = v;
/*     */   } public static DecimalNode valueOf(BigDecimal d) {
/*  34 */     return new DecimalNode(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken asToken() {
/*  42 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*     */   }
/*     */   public JsonParser.NumberType numberType() {
/*  45 */     return JsonParser.NumberType.BIG_DECIMAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFloatingPointNumber() {
/*  54 */     return true;
/*     */   }
/*     */   public boolean isBigDecimal() {
/*  57 */     return true;
/*     */   }
/*     */   public boolean canConvertToInt() {
/*  60 */     return (this._value.compareTo(MIN_INTEGER) >= 0 && this._value.compareTo(MAX_INTEGER) <= 0);
/*     */   }
/*     */   public boolean canConvertToLong() {
/*  63 */     return (this._value.compareTo(MIN_LONG) >= 0 && this._value.compareTo(MAX_LONG) <= 0);
/*     */   }
/*     */   
/*     */   public Number numberValue() {
/*  67 */     return this._value;
/*     */   }
/*     */   public short shortValue() {
/*  70 */     return this._value.shortValue();
/*     */   }
/*     */   public int intValue() {
/*  73 */     return this._value.intValue();
/*     */   }
/*     */   public long longValue() {
/*  76 */     return this._value.longValue();
/*     */   }
/*     */   
/*     */   public BigInteger bigIntegerValue() {
/*  80 */     return this._value.toBigInteger();
/*     */   }
/*     */   public float floatValue() {
/*  83 */     return this._value.floatValue();
/*     */   }
/*     */   public double doubleValue() {
/*  86 */     return this._value.doubleValue();
/*     */   }
/*     */   public BigDecimal decimalValue() {
/*  89 */     return this._value;
/*     */   }
/*     */   
/*     */   public String asText() {
/*  93 */     return this._value.toString();
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
/*     */   public final void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
/* 110 */     jgen.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 116 */     if (o == this) return true; 
/* 117 */     if (o == null) return false; 
/* 118 */     if (o instanceof DecimalNode) {
/* 119 */       return (((DecimalNode)o)._value.compareTo(this._value) == 0);
/*     */     }
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Double.valueOf(doubleValue()).hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\DecimalNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */