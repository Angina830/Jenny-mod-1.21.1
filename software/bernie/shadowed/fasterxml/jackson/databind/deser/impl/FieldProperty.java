/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
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
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FieldProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedField _annotated;
/*     */   protected final transient Field _field;
/*     */   protected final boolean _skipNulls;
/*     */   
/*     */   public FieldProperty(BeanPropertyDefinition propDef, JavaType type, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedField field) {
/*  46 */     super(propDef, type, typeDeser, contextAnnotations);
/*  47 */     this._annotated = field;
/*  48 */     this._field = field.getAnnotated();
/*  49 */     this._skipNulls = NullsConstantProvider.isSkipper(this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   protected FieldProperty(FieldProperty src, JsonDeserializer<?> deser, NullValueProvider nva) {
/*  54 */     super(src, deser, nva);
/*  55 */     this._annotated = src._annotated;
/*  56 */     this._field = src._field;
/*  57 */     this._skipNulls = NullsConstantProvider.isSkipper(nva);
/*     */   }
/*     */   
/*     */   protected FieldProperty(FieldProperty src, PropertyName newName) {
/*  61 */     super(src, newName);
/*  62 */     this._annotated = src._annotated;
/*  63 */     this._field = src._field;
/*  64 */     this._skipNulls = src._skipNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FieldProperty(FieldProperty src) {
/*  72 */     super(src);
/*  73 */     this._annotated = src._annotated;
/*  74 */     Field f = this._annotated.getAnnotated();
/*  75 */     if (f == null) {
/*  76 */       throw new IllegalArgumentException("Missing field (broken JDK (de)serialization?)");
/*     */     }
/*  78 */     this._field = f;
/*  79 */     this._skipNulls = src._skipNulls;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/*  84 */     return new FieldProperty(this, newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/*  89 */     if (this._valueDeserializer == deser) {
/*  90 */       return this;
/*     */     }
/*  92 */     return new FieldProperty(this, deser, this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/*  97 */     return new FieldProperty(this, this._valueDeserializer, nva);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/* 102 */     ClassUtil.checkAndFixAccess(this._field, config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
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
/* 114 */     return (this._annotated == null) ? null : (A)this._annotated.getAnnotation(acls);
/*     */   }
/*     */   public AnnotatedMember getMember() {
/* 117 */     return (AnnotatedMember)this._annotated;
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
/*     */     Object value;
/* 130 */     if (p.hasToken(JsonToken.VALUE_NULL)) {
/* 131 */       if (this._skipNulls) {
/*     */         return;
/*     */       }
/* 134 */       value = this._nullProvider.getNullValue(ctxt);
/* 135 */     } else if (this._valueTypeDeserializer == null) {
/* 136 */       value = this._valueDeserializer.deserialize(p, ctxt);
/*     */     } else {
/* 138 */       value = this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     } 
/*     */     try {
/* 141 */       this._field.set(instance, value);
/* 142 */     } catch (Exception e) {
/* 143 */       _throwAsIOE(p, e, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*     */     Object value;
/* 152 */     if (p.hasToken(JsonToken.VALUE_NULL)) {
/* 153 */       if (this._skipNulls) {
/* 154 */         return instance;
/*     */       }
/* 156 */       value = this._nullProvider.getNullValue(ctxt);
/* 157 */     } else if (this._valueTypeDeserializer == null) {
/* 158 */       value = this._valueDeserializer.deserialize(p, ctxt);
/*     */     } else {
/* 160 */       value = this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     } 
/*     */     try {
/* 163 */       this._field.set(instance, value);
/* 164 */     } catch (Exception e) {
/* 165 */       _throwAsIOE(p, e, value);
/*     */     } 
/* 167 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object value) throws IOException {
/*     */     try {
/* 174 */       this._field.set(instance, value);
/* 175 */     } catch (Exception e) {
/*     */       
/* 177 */       _throwAsIOE(e, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/*     */     try {
/* 185 */       this._field.set(instance, value);
/* 186 */     } catch (Exception e) {
/*     */       
/* 188 */       _throwAsIOE(e, value);
/*     */     } 
/* 190 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object readResolve() {
/* 200 */     return new FieldProperty(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\FieldProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */