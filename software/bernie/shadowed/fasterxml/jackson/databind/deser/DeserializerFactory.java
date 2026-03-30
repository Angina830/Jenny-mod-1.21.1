/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.AbstractTypeResolver;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ArrayType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.CollectionLikeType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.CollectionType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapLikeType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ReferenceType;
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
/*    */ public abstract class DeserializerFactory
/*    */ {
/* 43 */   protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
/*    */   
/*    */   public abstract DeserializerFactory withAdditionalDeserializers(Deserializers paramDeserializers);
/*    */   
/*    */   public abstract DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers paramKeyDeserializers);
/*    */   
/*    */   public abstract DeserializerFactory withDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier);
/*    */   
/*    */   public abstract DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver paramAbstractTypeResolver);
/*    */   
/*    */   public abstract DeserializerFactory withValueInstantiators(ValueInstantiators paramValueInstantiators);
/*    */   
/*    */   public abstract JavaType mapAbstractType(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   public abstract ValueInstantiator findValueInstantiator(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<Object> createBeanDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<Object> createBuilderBasedDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription, Class<?> paramClass) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createEnumDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createReferenceDeserializer(DeserializationContext paramDeserializationContext, ReferenceType paramReferenceType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createTreeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createArrayDeserializer(DeserializationContext paramDeserializationContext, ArrayType paramArrayType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createCollectionDeserializer(DeserializationContext paramDeserializationContext, CollectionType paramCollectionType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext paramDeserializationContext, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createMapDeserializer(DeserializationContext paramDeserializationContext, MapType paramMapType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext paramDeserializationContext, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription) throws JsonMappingException;
/*    */   
/*    */   public abstract KeyDeserializer createKeyDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   public abstract TypeDeserializer findTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType) throws JsonMappingException;
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\DeserializerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */