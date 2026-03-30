/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;
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
/*     */ public class StdDelegatingDeserializer<T>
/*     */   extends StdDeserializer<T>
/*     */   implements ContextualDeserializer, ResolvableDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Converter<Object, T> _converter;
/*     */   protected final JavaType _delegateType;
/*     */   protected final JsonDeserializer<Object> _delegateDeserializer;
/*     */   
/*     */   public StdDelegatingDeserializer(Converter<?, T> converter) {
/*  65 */     super(Object.class);
/*  66 */     this._converter = (Converter)converter;
/*  67 */     this._delegateType = null;
/*  68 */     this._delegateDeserializer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdDelegatingDeserializer(Converter<Object, T> converter, JavaType delegateType, JsonDeserializer<?> delegateDeserializer) {
/*  75 */     super(delegateType);
/*  76 */     this._converter = converter;
/*  77 */     this._delegateType = delegateType;
/*  78 */     this._delegateDeserializer = (JsonDeserializer)delegateDeserializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdDelegatingDeserializer(StdDelegatingDeserializer<T> src) {
/*  86 */     super(src);
/*  87 */     this._converter = src._converter;
/*  88 */     this._delegateType = src._delegateType;
/*  89 */     this._delegateDeserializer = src._delegateDeserializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StdDelegatingDeserializer<T> withDelegate(Converter<Object, T> converter, JavaType delegateType, JsonDeserializer<?> delegateDeserializer) {
/*  99 */     ClassUtil.verifyMustOverride(StdDelegatingDeserializer.class, this, "withDelegate");
/* 100 */     return new StdDelegatingDeserializer(converter, delegateType, delegateDeserializer);
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
/*     */   public void resolve(DeserializationContext ctxt) throws JsonMappingException {
/* 115 */     if (this._delegateDeserializer != null && this._delegateDeserializer instanceof ResolvableDeserializer) {
/* 116 */       ((ResolvableDeserializer)this._delegateDeserializer).resolve(ctxt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/* 125 */     if (this._delegateDeserializer != null) {
/* 126 */       JsonDeserializer<?> deser = ctxt.handleSecondaryContextualization(this._delegateDeserializer, property, this._delegateType);
/*     */       
/* 128 */       if (deser != this._delegateDeserializer) {
/* 129 */         return withDelegate(this._converter, this._delegateType, deser);
/*     */       }
/* 131 */       return this;
/*     */     } 
/*     */     
/* 134 */     JavaType delegateType = this._converter.getInputType(ctxt.getTypeFactory());
/* 135 */     return withDelegate(this._converter, delegateType, ctxt.findContextualValueDeserializer(delegateType, property));
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
/*     */   public JsonDeserializer<?> getDelegatee() {
/* 147 */     return this._delegateDeserializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> handledType() {
/* 152 */     return this._delegateDeserializer.handledType();
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 157 */     return this._delegateDeserializer.supportsUpdate(config);
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
/*     */   public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 169 */     Object delegateValue = this._delegateDeserializer.deserialize(p, ctxt);
/* 170 */     if (delegateValue == null) {
/* 171 */       return null;
/*     */     }
/* 173 */     return convertValue(delegateValue);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 191 */     Object delegateValue = this._delegateDeserializer.deserialize(p, ctxt);
/* 192 */     if (delegateValue == null) {
/* 193 */       return null;
/*     */     }
/* 195 */     return convertValue(delegateValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T deserialize(JsonParser p, DeserializationContext ctxt, Object intoValue) throws IOException {
/* 203 */     if (this._delegateType.getRawClass().isAssignableFrom(intoValue.getClass())) {
/* 204 */       return (T)this._delegateDeserializer.deserialize(p, ctxt, intoValue);
/*     */     }
/* 206 */     return (T)_handleIncompatibleUpdateValue(p, ctxt, intoValue);
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
/*     */   
/*     */   protected Object _handleIncompatibleUpdateValue(JsonParser p, DeserializationContext ctxt, Object intoValue) throws IOException {
/* 222 */     throw new UnsupportedOperationException(String.format("Cannot update object of type %s (using deserializer for type %s)" + intoValue.getClass().getName(), new Object[] { this._delegateType }));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T convertValue(Object delegateValue) {
/* 246 */     return (T)this._converter.convert(delegateValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\StdDelegatingDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */