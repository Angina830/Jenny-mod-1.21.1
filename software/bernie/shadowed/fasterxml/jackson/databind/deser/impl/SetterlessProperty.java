/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.NullValueProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
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
/*     */ 
/*     */ public final class SetterlessProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMethod _annotated;
/*     */   protected final Method _getter;
/*     */   
/*     */   public SetterlessProperty(BeanPropertyDefinition propDef, JavaType type, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedMethod method) {
/*  39 */     super(propDef, type, typeDeser, contextAnnotations);
/*  40 */     this._annotated = method;
/*  41 */     this._getter = method.getAnnotated();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SetterlessProperty(SetterlessProperty src, JsonDeserializer<?> deser, NullValueProvider nva) {
/*  46 */     super(src, deser, nva);
/*  47 */     this._annotated = src._annotated;
/*  48 */     this._getter = src._getter;
/*     */   }
/*     */   
/*     */   protected SetterlessProperty(SetterlessProperty src, PropertyName newName) {
/*  52 */     super(src, newName);
/*  53 */     this._annotated = src._annotated;
/*  54 */     this._getter = src._getter;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/*  59 */     return new SetterlessProperty(this, newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/*  64 */     if (this._valueDeserializer == deser) {
/*  65 */       return this;
/*     */     }
/*  67 */     return new SetterlessProperty(this, deser, this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/*  72 */     return new SetterlessProperty(this, this._valueDeserializer, nva);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/*  77 */     this._annotated.fixAccess(config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
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
/*     */   public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/*  89 */     return (A)this._annotated.getAnnotation(acls);
/*     */   }
/*     */   public AnnotatedMember getMember() {
/*  92 */     return (AnnotatedMember)this._annotated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*     */     Object toModify;
/* 104 */     JsonToken t = p.getCurrentToken();
/* 105 */     if (t == JsonToken.VALUE_NULL) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (this._valueTypeDeserializer != null) {
/* 112 */       ctxt.reportBadDefinition(getType(), String.format("Problem deserializing 'setterless' property (\"%s\"): no way to handle typed deser with setterless yet", new Object[] { getName() }));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       toModify = this._getter.invoke(instance, (Object[])null);
/* 121 */     } catch (Exception e) {
/* 122 */       _throwAsIOE(p, e);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 128 */     if (toModify == null) {
/* 129 */       ctxt.reportBadDefinition(getType(), String.format("Problem deserializing 'setterless' property '%s': get method returned null", new Object[] { getName() }));
/*     */     }
/*     */ 
/*     */     
/* 133 */     this._valueDeserializer.deserialize(p, ctxt, toModify);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/* 140 */     deserializeAndSet(p, ctxt, instance);
/* 141 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(Object instance, Object value) throws IOException {
/* 146 */     throw new UnsupportedOperationException("Should never call 'set' on setterless property");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/* 152 */     set(instance, value);
/* 153 */     return instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\SetterlessProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */