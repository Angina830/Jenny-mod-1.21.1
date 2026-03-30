/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectIdValueProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final ObjectIdReader _objectIdReader;
/*     */   
/*     */   public ObjectIdValueProperty(ObjectIdReader objectIdReader, PropertyMetadata metadata) {
/*  27 */     super(objectIdReader.propertyName, objectIdReader.getIdType(), metadata, objectIdReader.getDeserializer());
/*     */     
/*  29 */     this._objectIdReader = objectIdReader;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectIdValueProperty(ObjectIdValueProperty src, JsonDeserializer<?> deser, NullValueProvider nva) {
/*  35 */     super(src, deser, nva);
/*  36 */     this._objectIdReader = src._objectIdReader;
/*     */   }
/*     */   
/*     */   protected ObjectIdValueProperty(ObjectIdValueProperty src, PropertyName newName) {
/*  40 */     super(src, newName);
/*  41 */     this._objectIdReader = src._objectIdReader;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/*  46 */     return new ObjectIdValueProperty(this, newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/*  51 */     if (this._valueDeserializer == deser) {
/*  52 */       return this;
/*     */     }
/*  54 */     return new ObjectIdValueProperty(this, deser, this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/*  59 */     return new ObjectIdValueProperty(this, this._valueDeserializer, nva);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/*  66 */     return null;
/*     */   }
/*     */   public AnnotatedMember getMember() {
/*  69 */     return null;
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
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  81 */     deserializeSetAndReturn(p, ctxt, instance);
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
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  94 */     if (p.hasToken(JsonToken.VALUE_NULL)) {
/*  95 */       return null;
/*     */     }
/*  97 */     Object id = this._valueDeserializer.deserialize(p, ctxt);
/*  98 */     ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator, this._objectIdReader.resolver);
/*  99 */     roid.bindItem(instance);
/*     */     
/* 101 */     SettableBeanProperty idProp = this._objectIdReader.idProperty;
/* 102 */     if (idProp != null) {
/* 103 */       return idProp.setAndReturn(instance, id);
/*     */     }
/* 105 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object value) throws IOException {
/* 110 */     setAndReturn(instance, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/* 116 */     SettableBeanProperty idProp = this._objectIdReader.idProperty;
/* 117 */     if (idProp == null) {
/* 118 */       throw new UnsupportedOperationException("Should not call set() on ObjectIdProperty that has no SettableBeanProperty");
/*     */     }
/*     */     
/* 121 */     return idProp.setAndReturn(instance, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\ObjectIdValueProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */