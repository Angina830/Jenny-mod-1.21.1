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
/*     */ 
/*     */ 
/*     */ public final class MethodProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMethod _annotated;
/*     */   protected final transient Method _setter;
/*     */   protected final boolean _skipNulls;
/*     */   
/*     */   public MethodProperty(BeanPropertyDefinition propDef, JavaType type, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedMethod method) {
/*  42 */     super(propDef, type, typeDeser, contextAnnotations);
/*  43 */     this._annotated = method;
/*  44 */     this._setter = method.getAnnotated();
/*  45 */     this._skipNulls = NullsConstantProvider.isSkipper(this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MethodProperty(MethodProperty src, JsonDeserializer<?> deser, NullValueProvider nva) {
/*  50 */     super(src, deser, nva);
/*  51 */     this._annotated = src._annotated;
/*  52 */     this._setter = src._setter;
/*  53 */     this._skipNulls = NullsConstantProvider.isSkipper(nva);
/*     */   }
/*     */   
/*     */   protected MethodProperty(MethodProperty src, PropertyName newName) {
/*  57 */     super(src, newName);
/*  58 */     this._annotated = src._annotated;
/*  59 */     this._setter = src._setter;
/*  60 */     this._skipNulls = src._skipNulls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MethodProperty(MethodProperty src, Method m) {
/*  67 */     super(src);
/*  68 */     this._annotated = src._annotated;
/*  69 */     this._setter = m;
/*  70 */     this._skipNulls = src._skipNulls;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/*  75 */     return new MethodProperty(this, newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/*  80 */     if (this._valueDeserializer == deser) {
/*  81 */       return this;
/*     */     }
/*  83 */     return new MethodProperty(this, deser, this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/*  88 */     return new MethodProperty(this, this._valueDeserializer, nva);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/*  93 */     this._annotated.fixAccess(config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
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
/* 105 */     return (this._annotated == null) ? null : (A)this._annotated.getAnnotation(acls);
/*     */   }
/*     */   public AnnotatedMember getMember() {
/* 108 */     return (AnnotatedMember)this._annotated;
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
/* 121 */     if (p.hasToken(JsonToken.VALUE_NULL)) {
/* 122 */       if (this._skipNulls) {
/*     */         return;
/*     */       }
/* 125 */       value = this._nullProvider.getNullValue(ctxt);
/* 126 */     } else if (this._valueTypeDeserializer == null) {
/* 127 */       value = this._valueDeserializer.deserialize(p, ctxt);
/*     */     } else {
/* 129 */       value = this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     } 
/*     */     try {
/* 132 */       this._setter.invoke(instance, new Object[] { value });
/* 133 */     } catch (Exception e) {
/* 134 */       _throwAsIOE(p, e, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*     */     Object value;
/* 143 */     if (p.hasToken(JsonToken.VALUE_NULL)) {
/* 144 */       if (this._skipNulls) {
/* 145 */         return instance;
/*     */       }
/* 147 */       value = this._nullProvider.getNullValue(ctxt);
/* 148 */     } else if (this._valueTypeDeserializer == null) {
/* 149 */       value = this._valueDeserializer.deserialize(p, ctxt);
/*     */     } else {
/* 151 */       value = this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     } 
/*     */     try {
/* 154 */       Object result = this._setter.invoke(instance, new Object[] { value });
/* 155 */       return (result == null) ? instance : result;
/* 156 */     } catch (Exception e) {
/* 157 */       _throwAsIOE(p, e, value);
/* 158 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Object instance, Object value) throws IOException {
/*     */     try {
/* 166 */       this._setter.invoke(instance, new Object[] { value });
/* 167 */     } catch (Exception e) {
/*     */       
/* 169 */       _throwAsIOE(e, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/*     */     try {
/* 177 */       Object result = this._setter.invoke(instance, new Object[] { value });
/* 178 */       return (result == null) ? instance : result;
/* 179 */     } catch (Exception e) {
/*     */       
/* 181 */       _throwAsIOE(e, value);
/* 182 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object readResolve() {
/* 193 */     return new MethodProperty(this, this._annotated.getAnnotated());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\MethodProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */