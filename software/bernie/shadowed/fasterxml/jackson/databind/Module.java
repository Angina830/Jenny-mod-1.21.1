/*    */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MutableConfigOverride;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.Deserializers;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.KeyDeserializers;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeModifier;
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
/*    */ public abstract class Module
/*    */   implements Versioned
/*    */ {
/*    */   public abstract String getModuleName();
/*    */   
/*    */   public abstract Version version();
/*    */   
/*    */   public Object getTypeId() {
/* 61 */     return getClass().getName();
/*    */   }
/*    */   
/*    */   public abstract void setupModule(SetupContext paramSetupContext);
/*    */   
/*    */   public static interface SetupContext {
/*    */     Version getMapperVersion();
/*    */     
/*    */     <C extends software.bernie.shadowed.fasterxml.jackson.core.ObjectCodec> C getOwner();
/*    */     
/*    */     TypeFactory getTypeFactory();
/*    */     
/*    */     boolean isEnabled(MapperFeature param1MapperFeature);
/*    */     
/*    */     boolean isEnabled(DeserializationFeature param1DeserializationFeature);
/*    */     
/*    */     boolean isEnabled(SerializationFeature param1SerializationFeature);
/*    */     
/*    */     boolean isEnabled(JsonFactory.Feature param1Feature);
/*    */     
/*    */     boolean isEnabled(JsonParser.Feature param1Feature);
/*    */     
/*    */     boolean isEnabled(JsonGenerator.Feature param1Feature);
/*    */     
/*    */     MutableConfigOverride configOverride(Class<?> param1Class);
/*    */     
/*    */     void addDeserializers(Deserializers param1Deserializers);
/*    */     
/*    */     void addKeyDeserializers(KeyDeserializers param1KeyDeserializers);
/*    */     
/*    */     void addSerializers(Serializers param1Serializers);
/*    */     
/*    */     void addKeySerializers(Serializers param1Serializers);
/*    */     
/*    */     void addBeanDeserializerModifier(BeanDeserializerModifier param1BeanDeserializerModifier);
/*    */     
/*    */     void addBeanSerializerModifier(BeanSerializerModifier param1BeanSerializerModifier);
/*    */     
/*    */     void addAbstractTypeResolver(AbstractTypeResolver param1AbstractTypeResolver);
/*    */     
/*    */     void addTypeModifier(TypeModifier param1TypeModifier);
/*    */     
/*    */     void addValueInstantiators(ValueInstantiators param1ValueInstantiators);
/*    */     
/*    */     void setClassIntrospector(ClassIntrospector param1ClassIntrospector);
/*    */     
/*    */     void insertAnnotationIntrospector(AnnotationIntrospector param1AnnotationIntrospector);
/*    */     
/*    */     void appendAnnotationIntrospector(AnnotationIntrospector param1AnnotationIntrospector);
/*    */     
/*    */     void registerSubtypes(Class<?>... param1VarArgs);
/*    */     
/*    */     void registerSubtypes(NamedType... param1VarArgs);
/*    */     
/*    */     void registerSubtypes(Collection<Class<?>> param1Collection);
/*    */     
/*    */     void setMixInAnnotations(Class<?> param1Class1, Class<?> param1Class2);
/*    */     
/*    */     void addDeserializationProblemHandler(DeserializationProblemHandler param1DeserializationProblemHandler);
/*    */     
/*    */     void setNamingStrategy(PropertyNamingStrategy param1PropertyNamingStrategy);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\Module.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */