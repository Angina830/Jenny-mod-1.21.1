/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*    */ @Deprecated
/*    */ public abstract class NonTypedScalarSerializerBase<T>
/*    */   extends StdScalarSerializer<T>
/*    */ {
/*    */   protected NonTypedScalarSerializerBase(Class<T> t) {
/* 22 */     super(t);
/*    */   }
/*    */   
/*    */   protected NonTypedScalarSerializerBase(Class<?> t, boolean bogus) {
/* 26 */     super(t, bogus);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void serializeWithType(T value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 34 */     serialize(value, gen, provider);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\NonTypedScalarSerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */