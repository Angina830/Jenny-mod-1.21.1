/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*    */ public class AtomicReferenceDeserializer
/*    */   extends ReferenceTypeDeserializer<AtomicReference<Object>>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public AtomicReferenceDeserializer(JavaType fullType, ValueInstantiator inst, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
/* 26 */     super(fullType, inst, typeDeser, deser);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AtomicReferenceDeserializer withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
/* 37 */     return new AtomicReferenceDeserializer(this._fullType, this._valueInstantiator, typeDeser, valueDeser);
/*    */   }
/*    */ 
/*    */   
/*    */   public AtomicReference<Object> getNullValue(DeserializationContext ctxt) {
/* 42 */     return new AtomicReference();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getEmptyValue(DeserializationContext ctxt) {
/* 47 */     return new AtomicReference();
/*    */   }
/*    */ 
/*    */   
/*    */   public AtomicReference<Object> referenceValue(Object contents) {
/* 52 */     return new AtomicReference(contents);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getReferenced(AtomicReference<Object> reference) {
/* 57 */     return reference.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public AtomicReference<Object> updateReference(AtomicReference<Object> reference, Object contents) {
/* 62 */     reference.set(contents);
/* 63 */     return reference;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 69 */     return Boolean.TRUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\AtomicReferenceDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */