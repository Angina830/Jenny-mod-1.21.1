/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueInjector
/*    */   extends BeanProperty.Std
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final Object _valueId;
/*    */   
/*    */   public ValueInjector(PropertyName propName, JavaType type, AnnotatedMember mutator, Object valueId) {
/* 27 */     super(propName, type, null, mutator, PropertyMetadata.STD_OPTIONAL);
/* 28 */     this._valueId = valueId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object findValue(DeserializationContext context, Object beanInstance) throws JsonMappingException {
/* 34 */     return context.findInjectableValue(this._valueId, (BeanProperty)this, beanInstance);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void inject(DeserializationContext context, Object beanInstance) throws IOException {
/* 40 */     this._member.setValue(beanInstance, findValue(context, beanInstance));
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ValueInjector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */