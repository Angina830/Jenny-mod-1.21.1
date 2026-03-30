/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*     */ public class StdTypeResolverBuilder
/*     */   implements TypeResolverBuilder<StdTypeResolverBuilder>
/*     */ {
/*     */   protected JsonTypeInfo.Id _idType;
/*     */   protected JsonTypeInfo.As _includeAs;
/*     */   protected String _typeProperty;
/*     */   protected boolean _typeIdVisible = false;
/*     */   protected Class<?> _defaultImpl;
/*     */   protected TypeIdResolver _customIdResolver;
/*     */   
/*     */   protected StdTypeResolverBuilder(JsonTypeInfo.Id idType, JsonTypeInfo.As idAs, String propName) {
/*  54 */     this._idType = idType;
/*  55 */     this._includeAs = idAs;
/*  56 */     this._typeProperty = propName;
/*     */   }
/*     */   
/*     */   public static StdTypeResolverBuilder noTypeInfoBuilder() {
/*  60 */     return (new StdTypeResolverBuilder()).init(JsonTypeInfo.Id.NONE, (TypeIdResolver)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdTypeResolverBuilder init(JsonTypeInfo.Id idType, TypeIdResolver idRes) {
/*  67 */     if (idType == null) {
/*  68 */       throw new IllegalArgumentException("idType cannot be null");
/*     */     }
/*  70 */     this._idType = idType;
/*  71 */     this._customIdResolver = idRes;
/*     */     
/*  73 */     this._typeProperty = idType.getDefaultPropertyName();
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeSerializer buildTypeSerializer(SerializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
/*  81 */     if (this._idType == JsonTypeInfo.Id.NONE) return null;
/*     */ 
/*     */     
/*  84 */     if (baseType.isPrimitive()) {
/*  85 */       return null;
/*     */     }
/*  87 */     TypeIdResolver idRes = idResolver((MapperConfig<?>)config, baseType, subtypes, true, false);
/*  88 */     switch (this._includeAs) {
/*     */       case CLASS:
/*  90 */         return new AsArrayTypeSerializer(idRes, null);
/*     */       case MINIMAL_CLASS:
/*  92 */         return new AsPropertyTypeSerializer(idRes, null, this._typeProperty);
/*     */       case NAME:
/*  94 */         return new AsWrapperTypeSerializer(idRes, null);
/*     */       case NONE:
/*  96 */         return new AsExternalTypeSerializer(idRes, null, this._typeProperty);
/*     */       
/*     */       case CUSTOM:
/*  99 */         return new AsExistingPropertyTypeSerializer(idRes, null, this._typeProperty);
/*     */     } 
/* 101 */     throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
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
/*     */   public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
/*     */     JavaType defaultImpl;
/* 114 */     if (this._idType == JsonTypeInfo.Id.NONE) return null;
/*     */ 
/*     */     
/* 117 */     if (baseType.isPrimitive()) {
/* 118 */       return null;
/*     */     }
/*     */     
/* 121 */     TypeIdResolver idRes = idResolver((MapperConfig<?>)config, baseType, subtypes, false, true);
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (this._defaultImpl == null) {
/* 126 */       defaultImpl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 134 */     else if (this._defaultImpl == Void.class || this._defaultImpl == NoClass.class) {
/*     */       
/* 136 */       defaultImpl = config.getTypeFactory().constructType(this._defaultImpl);
/*     */     } else {
/* 138 */       defaultImpl = config.getTypeFactory().constructSpecializedType(baseType, this._defaultImpl);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     switch (this._includeAs) {
/*     */       case CLASS:
/* 146 */         return new AsArrayTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, defaultImpl);
/*     */       
/*     */       case MINIMAL_CLASS:
/*     */       case CUSTOM:
/* 150 */         return new AsPropertyTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, defaultImpl, this._includeAs);
/*     */       
/*     */       case NAME:
/* 153 */         return new AsWrapperTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, defaultImpl);
/*     */       
/*     */       case NONE:
/* 156 */         return new AsExternalTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, defaultImpl);
/*     */     } 
/*     */     
/* 159 */     throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdTypeResolverBuilder inclusion(JsonTypeInfo.As includeAs) {
/* 170 */     if (includeAs == null) {
/* 171 */       throw new IllegalArgumentException("includeAs cannot be null");
/*     */     }
/* 173 */     this._includeAs = includeAs;
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdTypeResolverBuilder typeProperty(String typeIdPropName) {
/* 184 */     if (typeIdPropName == null || typeIdPropName.length() == 0) {
/* 185 */       typeIdPropName = this._idType.getDefaultPropertyName();
/*     */     }
/* 187 */     this._typeProperty = typeIdPropName;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StdTypeResolverBuilder defaultImpl(Class<?> defaultImpl) {
/* 193 */     this._defaultImpl = defaultImpl;
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StdTypeResolverBuilder typeIdVisibility(boolean isVisible) {
/* 199 */     this._typeIdVisible = isVisible;
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDefaultImpl() {
/* 209 */     return this._defaultImpl;
/*     */   }
/* 211 */   public String getTypeProperty() { return this._typeProperty; } public boolean isTypeIdVisible() {
/* 212 */     return this._typeIdVisible;
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
/*     */   protected TypeIdResolver idResolver(MapperConfig<?> config, JavaType baseType, Collection<NamedType> subtypes, boolean forSer, boolean forDeser) {
/* 229 */     if (this._customIdResolver != null) return this._customIdResolver; 
/* 230 */     if (this._idType == null) throw new IllegalStateException("Cannot build, 'init()' not yet called"); 
/* 231 */     switch (this._idType) {
/*     */       case CLASS:
/* 233 */         return new ClassNameIdResolver(baseType, config.getTypeFactory());
/*     */       case MINIMAL_CLASS:
/* 235 */         return new MinimalClassNameIdResolver(baseType, config.getTypeFactory());
/*     */       case NAME:
/* 237 */         return TypeNameIdResolver.construct(config, baseType, subtypes, forSer, forDeser);
/*     */       case NONE:
/* 239 */         return null;
/*     */     } 
/*     */     
/* 242 */     throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + this._idType);
/*     */   }
/*     */   
/*     */   public StdTypeResolverBuilder() {}
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\StdTypeResolverBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */