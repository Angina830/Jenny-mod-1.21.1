/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NumericNode
/*    */   extends ValueNode
/*    */ {
/*    */   public final JsonNodeType getNodeType() {
/* 19 */     return JsonNodeType.NUMBER;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract JsonParser.NumberType numberType();
/*    */ 
/*    */   
/*    */   public abstract Number numberValue();
/*    */ 
/*    */   
/*    */   public abstract int intValue();
/*    */ 
/*    */   
/*    */   public abstract long longValue();
/*    */ 
/*    */   
/*    */   public abstract double doubleValue();
/*    */   
/*    */   public abstract BigDecimal decimalValue();
/*    */   
/*    */   public abstract BigInteger bigIntegerValue();
/*    */   
/*    */   public abstract boolean canConvertToInt();
/*    */   
/*    */   public abstract boolean canConvertToLong();
/*    */   
/*    */   public abstract String asText();
/*    */   
/*    */   public final int asInt() {
/* 48 */     return intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public final int asInt(int defaultValue) {
/* 53 */     return intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public final long asLong() {
/* 58 */     return longValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public final long asLong(long defaultValue) {
/* 63 */     return longValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public final double asDouble() {
/* 68 */     return doubleValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public final double asDouble(double defaultValue) {
/* 73 */     return doubleValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isNaN() {
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\NumericNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */