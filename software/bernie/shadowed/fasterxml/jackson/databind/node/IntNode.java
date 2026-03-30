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
/*     */ public class IntNode
/*     */   extends NumericNode
/*     */ {
/*     */   static final int MIN_CANONICAL = -1;
/*     */   static final int MAX_CANONICAL = 10;
/*     */   private static final IntNode[] CANONICALS;
/*     */   protected final int _value;
/*     */   
/*     */   static {
/*  25 */     int count = 12;
/*  26 */     CANONICALS = new IntNode[count];
/*  27 */     for (int i = 0; i < count; i++) {
/*  28 */       CANONICALS[i] = new IntNode(-1 + i);
/*     */     }
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
/*     */   public IntNode(int v) {
/*  43 */     this._value = v;
/*     */   }
/*     */   public static IntNode valueOf(int i) {
/*  46 */     if (i > 10 || i < -1) return new IntNode(i); 
/*  47 */     return CANONICALS[i - -1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken asToken() {
/*  56 */     return JsonToken.VALUE_NUMBER_INT;
/*     */   }
/*     */   public JsonParser.NumberType numberType() {
/*  59 */     return JsonParser.NumberType.INT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntegralNumber() {
/*  68 */     return true;
/*     */   }
/*     */   public boolean isInt() {
/*  71 */     return true;
/*     */   }
/*  73 */   public boolean canConvertToInt() { return true; } public boolean canConvertToLong() {
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   public Number numberValue() {
/*  78 */     return Integer.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  82 */     return (short)this._value;
/*     */   }
/*     */   public int intValue() {
/*  85 */     return this._value;
/*     */   }
/*     */   public long longValue() {
/*  88 */     return this._value;
/*     */   }
/*     */   public float floatValue() {
/*  91 */     return this._value;
/*     */   }
/*     */   public double doubleValue() {
/*  94 */     return this._value;
/*     */   }
/*     */   
/*     */   public BigDecimal decimalValue() {
/*  98 */     return BigDecimal.valueOf(this._value);
/*     */   }
/*     */   public BigInteger bigIntegerValue() {
/* 101 */     return BigInteger.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public String asText() {
/* 105 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean asBoolean(boolean defaultValue) {
/* 110 */     return (this._value != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
/* 117 */     jg.writeNumber(this._value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 123 */     if (o == this) return true; 
/* 124 */     if (o == null) return false; 
/* 125 */     if (o instanceof IntNode) {
/* 126 */       return (((IntNode)o)._value == this._value);
/*     */     }
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 132 */     return this._value;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\IntNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */