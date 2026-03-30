/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.AccessPattern;
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
/*     */ public abstract class ReferenceTypeDeserializer<T>
/*     */   extends StdDeserializer<T>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   protected final JavaType _fullType;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   protected final JsonDeserializer<Object> _valueDeserializer;
/*     */   
/*     */   public ReferenceTypeDeserializer(JavaType fullType, ValueInstantiator vi, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
/*  49 */     super(fullType);
/*  50 */     this._valueInstantiator = vi;
/*  51 */     this._fullType = fullType;
/*  52 */     this._valueDeserializer = (JsonDeserializer)deser;
/*  53 */     this._valueTypeDeserializer = typeDeser;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ReferenceTypeDeserializer(JavaType fullType, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
/*  60 */     this(fullType, null, typeDeser, deser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
/*  67 */     JsonDeserializer<?> deser = this._valueDeserializer;
/*  68 */     if (deser == null) {
/*  69 */       deser = ctxt.findContextualValueDeserializer(this._fullType.getReferencedType(), property);
/*     */     } else {
/*  71 */       deser = ctxt.handleSecondaryContextualization(deser, property, this._fullType.getReferencedType());
/*     */     } 
/*  73 */     TypeDeserializer typeDeser = this._valueTypeDeserializer;
/*  74 */     if (typeDeser != null) {
/*  75 */       typeDeser = typeDeser.forProperty(property);
/*     */     }
/*     */     
/*  78 */     if (deser == this._valueDeserializer && typeDeser == this._valueTypeDeserializer) {
/*  79 */       return this;
/*     */     }
/*  81 */     return withResolved(typeDeser, deser);
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
/*     */   public AccessPattern getNullAccessPattern() {
/*  96 */     return AccessPattern.DYNAMIC;
/*     */   }
/*     */ 
/*     */   
/*     */   public AccessPattern getEmptyAccessPattern() {
/* 101 */     return AccessPattern.DYNAMIC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ReferenceTypeDeserializer<T> withResolved(TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T getNullValue(DeserializationContext paramDeserializationContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEmptyValue(DeserializationContext ctxt) {
/* 125 */     return getNullValue(ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T referenceValue(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T updateReference(T paramT, Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object getReferenced(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType getValueType() {
/* 156 */     return this._fullType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean supportsUpdate(DeserializationConfig config) {
/* 165 */     return (this._valueDeserializer == null) ? null : this._valueDeserializer.supportsUpdate(config);
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
/*     */   public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 179 */     if (this._valueInstantiator != null) {
/*     */       
/* 181 */       T value = (T)this._valueInstantiator.createUsingDefault(ctxt);
/* 182 */       return deserialize(p, ctxt, value);
/*     */     } 
/* 184 */     Object contents = (this._valueTypeDeserializer == null) ? this._valueDeserializer.deserialize(p, ctxt) : this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */ 
/*     */     
/* 187 */     return referenceValue(contents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T deserialize(JsonParser p, DeserializationContext ctxt, T reference) throws IOException {
/*     */     Object contents;
/* 195 */     Boolean B = this._valueDeserializer.supportsUpdate(ctxt.getConfig());
/*     */     
/* 197 */     if (B.equals(Boolean.FALSE) || this._valueTypeDeserializer != null) {
/* 198 */       contents = (this._valueTypeDeserializer == null) ? this._valueDeserializer.deserialize(p, ctxt) : this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 203 */       contents = getReferenced(reference);
/*     */       
/* 205 */       if (contents == null) {
/* 206 */         contents = (this._valueTypeDeserializer == null) ? this._valueDeserializer.deserialize(p, ctxt) : this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */ 
/*     */         
/* 209 */         return referenceValue(contents);
/*     */       } 
/* 211 */       contents = this._valueDeserializer.deserialize(p, ctxt, contents);
/*     */     } 
/*     */     
/* 214 */     return updateReference(reference, contents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
/* 221 */     JsonToken t = p.getCurrentToken();
/* 222 */     if (t == JsonToken.VALUE_NULL) {
/* 223 */       return getNullValue(ctxt);
/*     */     }
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
/* 237 */     if (this._valueTypeDeserializer == null) {
/* 238 */       return deserialize(p, ctxt);
/*     */     }
/* 240 */     return referenceValue(this._valueTypeDeserializer.deserializeTypedFromAny(p, ctxt));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\ReferenceTypeDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */