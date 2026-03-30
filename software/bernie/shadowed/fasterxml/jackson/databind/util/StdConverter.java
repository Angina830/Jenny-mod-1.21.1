/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
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
/*    */ 
/*    */ 
/*    */ public abstract class StdConverter<IN, OUT>
/*    */   implements Converter<IN, OUT>
/*    */ {
/*    */   public abstract OUT convert(IN paramIN);
/*    */   
/*    */   public JavaType getInputType(TypeFactory typeFactory) {
/* 27 */     return _findConverterType(typeFactory).containedType(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public JavaType getOutputType(TypeFactory typeFactory) {
/* 32 */     return _findConverterType(typeFactory).containedType(1);
/*    */   }
/*    */   
/*    */   protected JavaType _findConverterType(TypeFactory tf) {
/* 36 */     JavaType thisType = tf.constructType(getClass());
/* 37 */     JavaType convType = thisType.findSuperType(Converter.class);
/* 38 */     if (convType == null || convType.containedTypeCount() < 2) {
/* 39 */       throw new IllegalStateException("Cannot find OUT type parameter for Converter of type " + getClass().getName());
/*    */     }
/* 41 */     return convType;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\StdConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */