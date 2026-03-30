/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DatabindContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.NullifyingDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
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
/*     */ public abstract class TypeDeserializerBase
/*     */   extends TypeDeserializer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final TypeIdResolver _idResolver;
/*     */   protected final JavaType _baseType;
/*     */   protected final BeanProperty _property;
/*     */   protected final JavaType _defaultImpl;
/*     */   protected final String _typePropertyName;
/*     */   protected final boolean _typeIdVisible;
/*     */   protected final Map<String, JsonDeserializer<Object>> _deserializers;
/*     */   protected JsonDeserializer<Object> _defaultImplDeserializer;
/*     */   
/*     */   protected TypeDeserializerBase(JavaType baseType, TypeIdResolver idRes, String typePropertyName, boolean typeIdVisible, JavaType defaultImpl) {
/*  75 */     this._baseType = baseType;
/*  76 */     this._idResolver = idRes;
/*  77 */     this._typePropertyName = ClassUtil.nonNullString(typePropertyName);
/*  78 */     this._typeIdVisible = typeIdVisible;
/*     */     
/*  80 */     this._deserializers = new ConcurrentHashMap<>(16, 0.75F, 2);
/*  81 */     this._defaultImpl = defaultImpl;
/*  82 */     this._property = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeDeserializerBase(TypeDeserializerBase src, BeanProperty property) {
/*  87 */     this._baseType = src._baseType;
/*  88 */     this._idResolver = src._idResolver;
/*  89 */     this._typePropertyName = src._typePropertyName;
/*  90 */     this._typeIdVisible = src._typeIdVisible;
/*  91 */     this._deserializers = src._deserializers;
/*  92 */     this._defaultImpl = src._defaultImpl;
/*  93 */     this._defaultImplDeserializer = src._defaultImplDeserializer;
/*  94 */     this._property = property;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TypeDeserializer forProperty(BeanProperty paramBeanProperty);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract JsonTypeInfo.As getTypeInclusion();
/*     */ 
/*     */ 
/*     */   
/*     */   public String baseTypeName() {
/* 109 */     return this._baseType.getRawClass().getName();
/*     */   }
/*     */   public final String getPropertyName() {
/* 112 */     return this._typePropertyName;
/*     */   }
/*     */   public TypeIdResolver getTypeIdResolver() {
/* 115 */     return this._idResolver;
/*     */   }
/*     */   
/*     */   public Class<?> getDefaultImpl() {
/* 119 */     return ClassUtil.rawClass(this._defaultImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaType baseType() {
/* 126 */     return this._baseType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     StringBuilder sb = new StringBuilder();
/* 133 */     sb.append('[').append(getClass().getName());
/* 134 */     sb.append("; base-type:").append(this._baseType);
/* 135 */     sb.append("; id-resolver: ").append(this._idResolver);
/* 136 */     sb.append(']');
/* 137 */     return sb.toString();
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
/*     */   protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext ctxt, String typeId) throws IOException {
/* 149 */     JsonDeserializer<Object> deser = this._deserializers.get(typeId);
/* 150 */     if (deser == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       JavaType type = this._idResolver.typeFromId((DatabindContext)ctxt, typeId);
/* 157 */       if (type == null) {
/*     */         
/* 159 */         deser = _findDefaultImplDeserializer(ctxt);
/* 160 */         if (deser == null)
/*     */         {
/* 162 */           JavaType actual = _handleUnknownTypeId(ctxt, typeId);
/* 163 */           if (actual == null)
/*     */           {
/* 165 */             return null;
/*     */           }
/*     */           
/* 168 */           deser = ctxt.findContextualValueDeserializer(actual, this._property);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 179 */         if (this._baseType != null && this._baseType.getClass() == type.getClass())
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 190 */           if (!type.hasGenericTypes()) {
/* 191 */             type = ctxt.getTypeFactory().constructSpecializedType(this._baseType, type.getRawClass());
/*     */           }
/*     */         }
/* 194 */         deser = ctxt.findContextualValueDeserializer(type, this._property);
/*     */       } 
/* 196 */       this._deserializers.put(typeId, deser);
/*     */     } 
/* 198 */     return deser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonDeserializer<Object> _findDefaultImplDeserializer(DeserializationContext ctxt) throws IOException {
/* 207 */     if (this._defaultImpl == null) {
/* 208 */       if (!ctxt.isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) {
/* 209 */         return (JsonDeserializer<Object>)NullifyingDeserializer.instance;
/*     */       }
/* 211 */       return null;
/*     */     } 
/* 213 */     Class<?> raw = this._defaultImpl.getRawClass();
/* 214 */     if (ClassUtil.isBogusClass(raw)) {
/* 215 */       return (JsonDeserializer<Object>)NullifyingDeserializer.instance;
/*     */     }
/*     */     
/* 218 */     synchronized (this._defaultImpl) {
/* 219 */       if (this._defaultImplDeserializer == null) {
/* 220 */         this._defaultImplDeserializer = ctxt.findContextualValueDeserializer(this._defaultImpl, this._property);
/*     */       }
/*     */       
/* 223 */       return this._defaultImplDeserializer;
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
/*     */   @Deprecated
/*     */   protected Object _deserializeWithNativeTypeId(JsonParser jp, DeserializationContext ctxt) throws IOException {
/* 236 */     return _deserializeWithNativeTypeId(jp, ctxt, jp.getTypeId());
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
/*     */   protected Object _deserializeWithNativeTypeId(JsonParser jp, DeserializationContext ctxt, Object typeId) throws IOException {
/*     */     JsonDeserializer<Object> deser;
/* 249 */     if (typeId == null) {
/*     */ 
/*     */ 
/*     */       
/* 253 */       deser = _findDefaultImplDeserializer(ctxt);
/* 254 */       if (deser == null) {
/* 255 */         return ctxt.reportInputMismatch(baseType(), "No (native) type id found when one was expected for polymorphic type handling", new Object[0]);
/*     */       }
/*     */     } else {
/*     */       
/* 259 */       String typeIdStr = (typeId instanceof String) ? (String)typeId : String.valueOf(typeId);
/* 260 */       deser = _findDeserializer(ctxt, typeIdStr);
/*     */     } 
/* 262 */     return deser.deserialize(jp, ctxt);
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
/*     */   protected JavaType _handleUnknownTypeId(DeserializationContext ctxt, String typeId) throws IOException {
/* 281 */     String extraDesc = this._idResolver.getDescForKnownTypeIds();
/* 282 */     if (extraDesc == null) {
/* 283 */       extraDesc = "type ids are not statically known";
/*     */     } else {
/* 285 */       extraDesc = "known type ids = " + extraDesc;
/*     */     } 
/* 287 */     if (this._property != null) {
/* 288 */       extraDesc = String.format("%s (for POJO property '%s')", new Object[] { extraDesc, this._property.getName() });
/*     */     }
/*     */     
/* 291 */     return ctxt.handleUnknownTypeId(this._baseType, typeId, this._idResolver, extraDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JavaType _handleMissingTypeId(DeserializationContext ctxt, String extraDesc) throws IOException {
/* 300 */     return ctxt.handleMissingTypeId(this._baseType, this._idResolver, extraDesc);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\TypeDeserializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */