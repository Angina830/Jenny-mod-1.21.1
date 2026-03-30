/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ConcreteBeanPropertyBase;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PropertyWriter
/*    */   extends ConcreteBeanPropertyBase
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected PropertyWriter(PropertyMetadata md) {
/* 27 */     super(md);
/*    */   }
/*    */   
/*    */   protected PropertyWriter(BeanPropertyDefinition propDef) {
/* 31 */     super(propDef.getMetadata());
/*    */   }
/*    */   
/*    */   protected PropertyWriter(PropertyWriter base) {
/* 35 */     super(base);
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
/*    */   public abstract String getName();
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
/*    */   public abstract PropertyName getFullName();
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
/*    */   public <A extends java.lang.annotation.Annotation> A findAnnotation(Class<A> acls) {
/* 71 */     A ann = getAnnotation(acls);
/* 72 */     if (ann == null) {
/* 73 */       ann = getContextAnnotation(acls);
/*    */     }
/* 75 */     return ann;
/*    */   }
/*    */   
/*    */   public abstract <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> paramClass);
/*    */   
/*    */   public abstract <A extends java.lang.annotation.Annotation> A getContextAnnotation(Class<A> paramClass);
/*    */   
/*    */   public abstract void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws Exception;
/*    */   
/*    */   public abstract void serializeAsOmittedField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws Exception;
/*    */   
/*    */   public abstract void serializeAsElement(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws Exception;
/*    */   
/*    */   public abstract void serializeAsPlaceholder(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws Exception;
/*    */   
/*    */   public abstract void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor, SerializerProvider paramSerializerProvider) throws JsonMappingException;
/*    */   
/*    */   @Deprecated
/*    */   public abstract void depositSchemaProperty(ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider) throws JsonMappingException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\PropertyWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */