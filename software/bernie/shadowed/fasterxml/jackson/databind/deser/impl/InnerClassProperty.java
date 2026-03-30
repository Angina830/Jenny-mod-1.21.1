/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InnerClassProperty
/*     */   extends SettableBeanProperty.Delegating
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final transient Constructor<?> _creator;
/*     */   protected AnnotatedConstructor _annotated;
/*     */   
/*     */   public InnerClassProperty(SettableBeanProperty delegate, Constructor<?> ctor) {
/*  39 */     super(delegate);
/*  40 */     this._creator = ctor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InnerClassProperty(SettableBeanProperty src, AnnotatedConstructor ann) {
/*  49 */     super(src);
/*  50 */     this._annotated = ann;
/*  51 */     this._creator = (this._annotated == null) ? null : this._annotated.getAnnotated();
/*  52 */     if (this._creator == null) {
/*  53 */       throw new IllegalArgumentException("Missing constructor (broken JDK (de)serialization?)");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SettableBeanProperty withDelegate(SettableBeanProperty d) {
/*  59 */     if (d == this.delegate) {
/*  60 */       return (SettableBeanProperty)this;
/*     */     }
/*  62 */     return (SettableBeanProperty)new InnerClassProperty(d, this._creator);
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
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object bean) throws IOException {
/*     */     Object object;
/*  75 */     JsonToken t = p.getCurrentToken();
/*     */     
/*  77 */     if (t == JsonToken.VALUE_NULL) {
/*  78 */       object = this._valueDeserializer.getNullValue(ctxt);
/*  79 */     } else if (this._valueTypeDeserializer != null) {
/*  80 */       object = this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     } else {
/*     */       try {
/*  83 */         object = this._creator.newInstance(new Object[] { bean });
/*  84 */       } catch (Exception e) {
/*  85 */         ClassUtil.unwrapAndThrowAsIAE(e, String.format("Failed to instantiate class %s, problem: %s", new Object[] { this._creator.getDeclaringClass().getName(), e.getMessage() }));
/*     */ 
/*     */         
/*  88 */         object = null;
/*     */       } 
/*  90 */       this._valueDeserializer.deserialize(p, ctxt, object);
/*     */     } 
/*  92 */     set(bean, object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/*  99 */     return setAndReturn(instance, deserialize(p, ctxt));
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
/*     */ 
/*     */   
/*     */   Object readResolve() {
/* 114 */     return new InnerClassProperty((SettableBeanProperty)this, this._annotated);
/*     */   }
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 119 */     if (this._annotated == null) {
/* 120 */       return new InnerClassProperty((SettableBeanProperty)this, new AnnotatedConstructor(null, this._creator, null, null));
/*     */     }
/* 122 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\impl\InnerClassProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */