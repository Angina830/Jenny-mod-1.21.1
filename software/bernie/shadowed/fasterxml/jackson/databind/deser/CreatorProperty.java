/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidDefinitionException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
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
/*     */ public class CreatorProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedParameter _annotated;
/*     */   protected final Object _injectableValueId;
/*     */   protected final int _creatorIndex;
/*     */   protected SettableBeanProperty _fallbackSetter;
/*     */   
/*     */   public CreatorProperty(PropertyName name, JavaType type, PropertyName wrapperName, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedParameter param, int index, Object injectableValueId, PropertyMetadata metadata) {
/*  84 */     super(name, type, wrapperName, typeDeser, contextAnnotations, metadata);
/*  85 */     this._annotated = param;
/*  86 */     this._creatorIndex = index;
/*  87 */     this._injectableValueId = injectableValueId;
/*  88 */     this._fallbackSetter = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CreatorProperty(CreatorProperty src, PropertyName newName) {
/*  95 */     super(src, newName);
/*  96 */     this._annotated = src._annotated;
/*  97 */     this._creatorIndex = src._creatorIndex;
/*  98 */     this._injectableValueId = src._injectableValueId;
/*  99 */     this._fallbackSetter = src._fallbackSetter;
/*     */   }
/*     */ 
/*     */   
/*     */   protected CreatorProperty(CreatorProperty src, JsonDeserializer<?> deser, NullValueProvider nva) {
/* 104 */     super(src, deser, nva);
/* 105 */     this._annotated = src._annotated;
/* 106 */     this._creatorIndex = src._creatorIndex;
/* 107 */     this._injectableValueId = src._injectableValueId;
/* 108 */     this._fallbackSetter = src._fallbackSetter;
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withName(PropertyName newName) {
/* 113 */     return new CreatorProperty(this, newName);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
/* 118 */     if (this._valueDeserializer == deser) {
/* 119 */       return this;
/*     */     }
/* 121 */     return new CreatorProperty(this, deser, this._nullProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public SettableBeanProperty withNullProvider(NullValueProvider nva) {
/* 126 */     return new CreatorProperty(this, this._valueDeserializer, nva);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/* 131 */     if (this._fallbackSetter != null) {
/* 132 */       this._fallbackSetter.fixAccess(config);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFallbackSetter(SettableBeanProperty fallbackSetter) {
/* 143 */     this._fallbackSetter = fallbackSetter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object findInjectableValue(DeserializationContext context, Object beanInstance) throws JsonMappingException {
/* 153 */     if (this._injectableValueId == null) {
/* 154 */       context.reportBadDefinition(ClassUtil.classOf(beanInstance), String.format("Property '%s' (type %s) has no injectable value id configured", new Object[] { getName(), getClass().getName() }));
/*     */     }
/*     */ 
/*     */     
/* 158 */     return context.findInjectableValue(this._injectableValueId, (BeanProperty)this, beanInstance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject(DeserializationContext context, Object beanInstance) throws IOException {
/* 167 */     set(beanInstance, findInjectableValue(context, beanInstance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends java.lang.annotation.Annotation> A getAnnotation(Class<A> acls) {
/* 178 */     if (this._annotated == null) {
/* 179 */       return null;
/*     */     }
/* 181 */     return (A)this._annotated.getAnnotation(acls);
/*     */   }
/*     */   public AnnotatedMember getMember() {
/* 184 */     return (AnnotatedMember)this._annotated;
/*     */   }
/*     */   public int getCreatorIndex() {
/* 187 */     return this._creatorIndex;
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
/*     */   public void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/* 200 */     _verifySetter();
/* 201 */     this._fallbackSetter.set(instance, deserialize(p, ctxt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeSetAndReturn(JsonParser p, DeserializationContext ctxt, Object instance) throws IOException {
/* 208 */     _verifySetter();
/* 209 */     return this._fallbackSetter.setAndReturn(instance, deserialize(p, ctxt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object value) throws IOException {
/* 215 */     _verifySetter();
/* 216 */     this._fallbackSetter.set(instance, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setAndReturn(Object instance, Object value) throws IOException {
/* 222 */     _verifySetter();
/* 223 */     return this._fallbackSetter.setAndReturn(instance, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getInjectableValueId() {
/* 228 */     return this._injectableValueId;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 232 */     return "[creator property, name '" + getName() + "'; inject id '" + this._injectableValueId + "']";
/*     */   }
/*     */   
/*     */   private final void _verifySetter() throws IOException {
/* 236 */     if (this._fallbackSetter == null) {
/* 237 */       _reportMissingSetter((JsonParser)null, (DeserializationContext)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void _reportMissingSetter(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 244 */     String msg = "No fallback setter/field defined for creator property '" + getName() + "'";
/*     */ 
/*     */     
/* 247 */     if (ctxt != null) {
/* 248 */       ctxt.reportBadDefinition(getType(), msg);
/*     */     } else {
/* 250 */       throw InvalidDefinitionException.from(p, msg, getType());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\CreatorProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */