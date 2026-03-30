/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.MapperFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.BeanUtil;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ public class PropertyBuilder {
/*  18 */   private static final Object NO_DEFAULT_MARKER = Boolean.FALSE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SerializationConfig _config;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanDescription _beanDesc;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _defaultBean;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JsonInclude.Value _defaultInclusion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _useRealPropertyDefaults;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyBuilder(SerializationConfig config, BeanDescription beanDesc) {
/*  54 */     this._config = config;
/*  55 */     this._beanDesc = beanDesc;
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
/*  66 */     JsonInclude.Value inclPerType = JsonInclude.Value.merge(beanDesc.findPropertyInclusion(JsonInclude.Value.empty()), config.getDefaultPropertyInclusion(beanDesc.getBeanClass(), JsonInclude.Value.empty()));
/*     */ 
/*     */ 
/*     */     
/*  70 */     this._defaultInclusion = JsonInclude.Value.merge(config.getDefaultPropertyInclusion(), inclPerType);
/*     */     
/*  72 */     this._useRealPropertyDefaults = (inclPerType.getValueInclusion() == JsonInclude.Include.NON_DEFAULT);
/*  73 */     this._annotationIntrospector = this._config.getAnnotationIntrospector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotations getClassAnnotations() {
/*  83 */     return this._beanDesc.getClassAnnotations();
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
/*     */   protected BeanPropertyWriter buildWriter(SerializerProvider prov, BeanPropertyDefinition propDef, JavaType declaredType, JsonSerializer<?> ser, TypeSerializer typeSer, TypeSerializer contentTypeSer, AnnotatedMember am, boolean defaultUseStaticTyping) throws JsonMappingException {
/*     */     JavaType serializationType;
/*     */     Object defaultBean;
/*     */     try {
/* 100 */       serializationType = findSerializationType((Annotated)am, defaultUseStaticTyping, declaredType);
/* 101 */     } catch (JsonMappingException e) {
/* 102 */       if (propDef == null) {
/* 103 */         return (BeanPropertyWriter)prov.reportBadDefinition(declaredType, e.getMessage());
/*     */       }
/* 105 */       return (BeanPropertyWriter)prov.reportBadPropertyDefinition(this._beanDesc, propDef, e.getMessage(), new Object[0]);
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (contentTypeSer != null) {
/*     */ 
/*     */ 
/*     */       
/* 113 */       if (serializationType == null)
/*     */       {
/* 115 */         serializationType = declaredType;
/*     */       }
/* 117 */       JavaType ct = serializationType.getContentType();
/*     */       
/* 119 */       if (ct == null) {
/* 120 */         prov.reportBadPropertyDefinition(this._beanDesc, propDef, "serialization type " + serializationType + " has no content", new Object[0]);
/*     */       }
/*     */       
/* 123 */       serializationType = serializationType.withContentTypeHandler(contentTypeSer);
/* 124 */       ct = serializationType.getContentType();
/*     */     } 
/*     */     
/* 127 */     Object valueToSuppress = null;
/* 128 */     boolean suppressNulls = false;
/*     */ 
/*     */     
/* 131 */     JavaType actualType = (serializationType == null) ? declaredType : serializationType;
/*     */ 
/*     */     
/* 134 */     AnnotatedMember accessor = propDef.getAccessor();
/* 135 */     if (accessor == null)
/*     */     {
/* 137 */       return (BeanPropertyWriter)prov.reportBadPropertyDefinition(this._beanDesc, propDef, "could not determine property type", new Object[0]);
/*     */     }
/*     */     
/* 140 */     Class<?> rawPropertyType = accessor.getRawType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     JsonInclude.Value inclV = this._config.getDefaultInclusion(actualType.getRawClass(), rawPropertyType, this._defaultInclusion);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     inclV = inclV.withOverrides(propDef.findInclusion());
/*     */     
/* 152 */     JsonInclude.Include inclusion = inclV.getValueInclusion();
/* 153 */     if (inclusion == JsonInclude.Include.USE_DEFAULTS) {
/* 154 */       inclusion = JsonInclude.Include.ALWAYS;
/*     */     }
/* 156 */     switch (inclusion) {
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
/*     */       case NON_DEFAULT:
/* 169 */         if (this._useRealPropertyDefaults && (defaultBean = getDefaultBean()) != null) {
/*     */           
/* 171 */           if (prov.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
/* 172 */             am.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
/*     */           }
/*     */           try {
/* 175 */             valueToSuppress = am.getValue(defaultBean);
/* 176 */           } catch (Exception e) {
/* 177 */             _throwWrapped(e, propDef.getName(), defaultBean);
/*     */           } 
/*     */         } else {
/* 180 */           valueToSuppress = BeanUtil.getDefaultValue(actualType);
/* 181 */           suppressNulls = true;
/*     */         } 
/* 183 */         if (valueToSuppress == null) {
/* 184 */           suppressNulls = true; break;
/*     */         } 
/* 186 */         if (valueToSuppress.getClass().isArray()) {
/* 187 */           valueToSuppress = ArrayBuilders.getArrayComparator(valueToSuppress);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case NON_ABSENT:
/* 193 */         suppressNulls = true;
/*     */         
/* 195 */         if (actualType.isReferenceType()) {
/* 196 */           valueToSuppress = BeanPropertyWriter.MARKER_FOR_EMPTY;
/*     */         }
/*     */         break;
/*     */       
/*     */       case NON_EMPTY:
/* 201 */         suppressNulls = true;
/*     */         
/* 203 */         valueToSuppress = BeanPropertyWriter.MARKER_FOR_EMPTY;
/*     */         break;
/*     */       case CUSTOM:
/* 206 */         valueToSuppress = prov.includeFilterInstance(propDef, inclV.getValueFilter());
/* 207 */         if (valueToSuppress == null) {
/* 208 */           suppressNulls = true; break;
/*     */         } 
/* 210 */         suppressNulls = prov.includeFilterSuppressNulls(valueToSuppress);
/*     */         break;
/*     */       
/*     */       case NON_NULL:
/* 214 */         suppressNulls = true;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 219 */         if (actualType.isContainerType() && !this._config.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS))
/*     */         {
/* 221 */           valueToSuppress = BeanPropertyWriter.MARKER_FOR_EMPTY;
/*     */         }
/*     */         break;
/*     */     } 
/* 225 */     Class<?>[] views = propDef.findViews();
/* 226 */     if (views == null) {
/* 227 */       views = this._beanDesc.findDefaultViews();
/*     */     }
/* 229 */     BeanPropertyWriter bpw = new BeanPropertyWriter(propDef, am, this._beanDesc.getClassAnnotations(), declaredType, ser, typeSer, serializationType, suppressNulls, valueToSuppress, views);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     Object serDef = this._annotationIntrospector.findNullSerializer((Annotated)am);
/* 235 */     if (serDef != null) {
/* 236 */       bpw.assignNullSerializer(prov.serializerInstance((Annotated)am, serDef));
/*     */     }
/*     */     
/* 239 */     NameTransformer unwrapper = this._annotationIntrospector.findUnwrappingNameTransformer(am);
/* 240 */     if (unwrapper != null) {
/* 241 */       bpw = bpw.unwrappingWriter(unwrapper);
/*     */     }
/* 243 */     return bpw;
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
/*     */   protected JavaType findSerializationType(Annotated a, boolean useStaticTyping, JavaType declaredType) throws JsonMappingException {
/* 261 */     JavaType secondary = this._annotationIntrospector.refineSerializationType((MapperConfig)this._config, a, declaredType);
/*     */ 
/*     */ 
/*     */     
/* 265 */     if (secondary != declaredType) {
/* 266 */       Class<?> serClass = secondary.getRawClass();
/*     */       
/* 268 */       Class<?> rawDeclared = declaredType.getRawClass();
/* 269 */       if (!serClass.isAssignableFrom(rawDeclared))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 278 */         if (!rawDeclared.isAssignableFrom(serClass)) {
/* 279 */           throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + a.getName() + "': class " + serClass.getName() + " not a super-type of (declared) class " + rawDeclared.getName());
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 286 */       useStaticTyping = true;
/* 287 */       declaredType = secondary;
/*     */     } 
/*     */     
/* 290 */     JsonSerialize.Typing typing = this._annotationIntrospector.findSerializationTyping(a);
/* 291 */     if (typing != null && typing != JsonSerialize.Typing.DEFAULT_TYPING) {
/* 292 */       useStaticTyping = (typing == JsonSerialize.Typing.STATIC);
/*     */     }
/* 294 */     if (useStaticTyping)
/*     */     {
/* 296 */       return declaredType.withStaticTyping();
/*     */     }
/*     */     
/* 299 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getDefaultBean() {
/* 310 */     Object def = this._defaultBean;
/* 311 */     if (def == null) {
/*     */ 
/*     */ 
/*     */       
/* 315 */       def = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
/* 316 */       if (def == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 324 */         def = NO_DEFAULT_MARKER;
/*     */       }
/* 326 */       this._defaultBean = def;
/*     */     } 
/* 328 */     return (def == NO_DEFAULT_MARKER) ? null : this._defaultBean;
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
/*     */   @Deprecated
/*     */   protected Object getPropertyDefaultValue(String name, AnnotatedMember member, JavaType type) {
/* 350 */     Object defaultBean = getDefaultBean();
/* 351 */     if (defaultBean == null) {
/* 352 */       return getDefaultValue(type);
/*     */     }
/*     */     try {
/* 355 */       return member.getValue(defaultBean);
/* 356 */     } catch (Exception e) {
/* 357 */       return _throwWrapped(e, name, defaultBean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Object getDefaultValue(JavaType type) {
/* 366 */     return BeanUtil.getDefaultValue(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _throwWrapped(Exception e, String propName, Object defaultBean) {
/* 377 */     Throwable t = e;
/* 378 */     while (t.getCause() != null) {
/* 379 */       t = t.getCause();
/*     */     }
/* 381 */     ClassUtil.throwIfError(t);
/* 382 */     ClassUtil.throwIfRTE(t);
/* 383 */     throw new IllegalArgumentException("Failed to get property '" + propName + "' of default " + defaultBean.getClass().getName() + " instance");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\PropertyBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */