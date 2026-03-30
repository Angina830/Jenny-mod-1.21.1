/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ReferenceType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AtomicReferenceSerializer
/*    */   extends ReferenceTypeSerializer<AtomicReference<?>>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public AtomicReferenceSerializer(ReferenceType fullType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> ser) {
/* 24 */     super(fullType, staticTyping, vts, ser);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AtomicReferenceSerializer(AtomicReferenceSerializer base, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper, Object suppressableValue, boolean suppressNulls) {
/* 32 */     super(base, property, vts, valueSer, unwrapper, suppressableValue, suppressNulls);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ReferenceTypeSerializer<AtomicReference<?>> withResolved(BeanProperty prop, TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper) {
/* 41 */     return new AtomicReferenceSerializer(this, prop, vts, valueSer, unwrapper, this._suppressableValue, this._suppressNulls);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReferenceTypeSerializer<AtomicReference<?>> withContentInclusion(Object suppressableValue, boolean suppressNulls) {
/* 49 */     return new AtomicReferenceSerializer(this, this._property, this._valueTypeSerializer, this._valueSerializer, this._unwrapper, suppressableValue, suppressNulls);
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
/*    */   protected boolean _isValuePresent(AtomicReference<?> value) {
/* 62 */     return (value.get() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object _getReferenced(AtomicReference<?> value) {
/* 67 */     return value.get();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object _getReferencedIfPresent(AtomicReference<?> value) {
/* 72 */     return value.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\AtomicReferenceSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */