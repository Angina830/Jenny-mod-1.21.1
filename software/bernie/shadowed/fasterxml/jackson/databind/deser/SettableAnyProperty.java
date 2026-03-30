/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*     */ public class SettableAnyProperty
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final BeanProperty _property;
/*     */   protected final AnnotatedMember _setter;
/*     */   final boolean _setterIsField;
/*     */   protected final JavaType _type;
/*     */   protected JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   protected final KeyDeserializer _keyDeserializer;
/*     */   
/*     */   public SettableAnyProperty(BeanProperty property, AnnotatedMember setter, JavaType type, KeyDeserializer keyDeser, JsonDeserializer<Object> valueDeser, TypeDeserializer typeDeser) {
/*  62 */     this._property = property;
/*  63 */     this._setter = setter;
/*  64 */     this._type = type;
/*  65 */     this._valueDeserializer = valueDeser;
/*  66 */     this._valueTypeDeserializer = typeDeser;
/*  67 */     this._keyDeserializer = keyDeser;
/*  68 */     this._setterIsField = setter instanceof AnnotatedField;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public SettableAnyProperty(BeanProperty property, AnnotatedMember setter, JavaType type, JsonDeserializer<Object> valueDeser, TypeDeserializer typeDeser) {
/*  75 */     this(property, setter, type, null, valueDeser, typeDeser);
/*     */   }
/*     */   
/*     */   public SettableAnyProperty withValueDeserializer(JsonDeserializer<Object> deser) {
/*  79 */     return new SettableAnyProperty(this._property, this._setter, this._type, this._keyDeserializer, deser, this._valueTypeDeserializer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixAccess(DeserializationConfig config) {
/*  84 */     this._setter.fixAccess(config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
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
/*  99 */     if (this._setter == null || this._setter.getAnnotated() == null) {
/* 100 */       throw new IllegalArgumentException("Missing method (broken JDK (de)serialization?)");
/*     */     }
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty getProperty() {
/* 111 */     return this._property;
/*     */   } public boolean hasValueDeserializer() {
/* 113 */     return (this._valueDeserializer != null);
/*     */   } public JavaType getType() {
/* 115 */     return this._type;
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
/*     */   public final void deserializeAndSet(JsonParser p, DeserializationContext ctxt, Object instance, String propName) throws IOException {
/*     */     try {
/* 132 */       Object key = (this._keyDeserializer == null) ? propName : this._keyDeserializer.deserializeKey(propName, ctxt);
/*     */       
/* 134 */       set(instance, key, deserialize(p, ctxt));
/* 135 */     } catch (UnresolvedForwardReference reference) {
/* 136 */       if (this._valueDeserializer.getObjectIdReader() == null) {
/* 137 */         throw JsonMappingException.from(p, "Unresolved forward reference but no identity info.", reference);
/*     */       }
/* 139 */       AnySetterReferring referring = new AnySetterReferring(this, reference, this._type.getRawClass(), instance, propName);
/*     */       
/* 141 */       reference.getRoid().appendReferring(referring);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
/* 147 */     JsonToken t = p.getCurrentToken();
/* 148 */     if (t == JsonToken.VALUE_NULL) {
/* 149 */       return this._valueDeserializer.getNullValue(ctxt);
/*     */     }
/* 151 */     if (this._valueTypeDeserializer != null) {
/* 152 */       return this._valueDeserializer.deserializeWithType(p, ctxt, this._valueTypeDeserializer);
/*     */     }
/* 154 */     return this._valueDeserializer.deserialize(p, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object instance, Object propName, Object value) throws IOException {
/*     */     try {
/* 162 */       if (this._setterIsField) {
/* 163 */         AnnotatedField field = (AnnotatedField)this._setter;
/* 164 */         Map<Object, Object> val = (Map<Object, Object>)field.getValue(instance);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         if (val != null)
/*     */         {
/* 172 */           val.put(propName, value);
/*     */         }
/*     */       } else {
/*     */         
/* 176 */         ((AnnotatedMethod)this._setter).callOnWith(instance, new Object[] { propName, value });
/*     */       } 
/* 178 */     } catch (Exception e) {
/* 179 */       _throwAsIOE(e, propName, value);
/*     */     } 
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
/*     */   protected void _throwAsIOE(Exception e, Object propName, Object value) throws IOException {
/* 197 */     if (e instanceof IllegalArgumentException) {
/* 198 */       String actType = ClassUtil.classNameOf(value);
/* 199 */       StringBuilder msg = (new StringBuilder("Problem deserializing \"any\" property '")).append(propName);
/* 200 */       msg.append("' of class " + getClassName() + " (expected type: ").append(this._type);
/* 201 */       msg.append("; actual type: ").append(actType).append(")");
/* 202 */       String origMsg = e.getMessage();
/* 203 */       if (origMsg != null) {
/* 204 */         msg.append(", problem: ").append(origMsg);
/*     */       } else {
/* 206 */         msg.append(" (no error message provided)");
/*     */       } 
/* 208 */       throw new JsonMappingException(null, msg.toString(), e);
/*     */     } 
/* 210 */     ClassUtil.throwIfIOE(e);
/* 211 */     ClassUtil.throwIfRTE(e);
/*     */     
/* 213 */     Throwable t = ClassUtil.getRootCause(e);
/* 214 */     throw new JsonMappingException(null, t.getMessage(), t);
/*     */   }
/*     */   private String getClassName() {
/* 217 */     return this._setter.getDeclaringClass().getName();
/*     */   } public String toString() {
/* 219 */     return "[any property on class " + getClassName() + "]";
/*     */   }
/*     */   
/*     */   private static class AnySetterReferring
/*     */     extends ReadableObjectId.Referring {
/*     */     private final SettableAnyProperty _parent;
/*     */     private final Object _pojo;
/*     */     private final String _propName;
/*     */     
/*     */     public AnySetterReferring(SettableAnyProperty parent, UnresolvedForwardReference reference, Class<?> type, Object instance, String propName) {
/* 229 */       super(reference, type);
/* 230 */       this._parent = parent;
/* 231 */       this._pojo = instance;
/* 232 */       this._propName = propName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleResolvedForwardReference(Object id, Object value) throws IOException {
/* 239 */       if (!hasId(id)) {
/* 240 */         throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + id.toString() + "] that wasn't previously registered.");
/*     */       }
/*     */       
/* 243 */       this._parent.set(this._pojo, this._propName, value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\SettableAnyProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */