/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.module;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.Module;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleModule
/*     */   extends Module
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final String _name;
/*     */   protected final Version _version;
/*  38 */   protected SimpleSerializers _serializers = null;
/*  39 */   protected SimpleDeserializers _deserializers = null;
/*     */   
/*  41 */   protected SimpleSerializers _keySerializers = null;
/*  42 */   protected SimpleKeyDeserializers _keyDeserializers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected SimpleAbstractTypeResolver _abstractTypes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected SimpleValueInstantiators _valueInstantiators = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   protected BeanDeserializerModifier _deserializerModifier = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected BeanSerializerModifier _serializerModifier = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   protected HashMap<Class<?>, Class<?>> _mixins = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   protected LinkedHashSet<NamedType> _subtypes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected PropertyNamingStrategy _namingStrategy = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule() {
/*  98 */     this._name = (getClass() == SimpleModule.class) ? ("SimpleModule-" + System.identityHashCode(this)) : getClass().getName();
/*     */ 
/*     */     
/* 101 */     this._version = Version.unknownVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule(String name) {
/* 109 */     this(name, Version.unknownVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule(Version version) {
/* 117 */     this._name = version.getArtifactId();
/* 118 */     this._version = version;
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
/*     */   public SimpleModule(String name, Version version) {
/* 131 */     this._name = name;
/* 132 */     this._version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule(String name, Version version, Map<Class<?>, JsonDeserializer<?>> deserializers) {
/* 140 */     this(name, version, deserializers, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule(String name, Version version, List<JsonSerializer<?>> serializers) {
/* 148 */     this(name, version, null, serializers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule(String name, Version version, Map<Class<?>, JsonDeserializer<?>> deserializers, List<JsonSerializer<?>> serializers) {
/* 158 */     this._name = name;
/* 159 */     this._version = version;
/* 160 */     if (deserializers != null) {
/* 161 */       this._deserializers = new SimpleDeserializers(deserializers);
/*     */     }
/* 163 */     if (serializers != null) {
/* 164 */       this._serializers = new SimpleSerializers(serializers);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTypeId() {
/* 175 */     if (getClass() == SimpleModule.class) {
/* 176 */       return null;
/*     */     }
/* 178 */     return super.getTypeId();
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
/*     */   public void setSerializers(SimpleSerializers s) {
/* 191 */     this._serializers = s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeserializers(SimpleDeserializers d) {
/* 198 */     this._deserializers = d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeySerializers(SimpleSerializers ks) {
/* 205 */     this._keySerializers = ks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyDeserializers(SimpleKeyDeserializers kd) {
/* 212 */     this._keyDeserializers = kd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAbstractTypes(SimpleAbstractTypeResolver atr) {
/* 219 */     this._abstractTypes = atr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueInstantiators(SimpleValueInstantiators svi) {
/* 226 */     this._valueInstantiators = svi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule setDeserializerModifier(BeanDeserializerModifier mod) {
/* 233 */     this._deserializerModifier = mod;
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule setSerializerModifier(BeanSerializerModifier mod) {
/* 241 */     this._serializerModifier = mod;
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleModule setNamingStrategy(PropertyNamingStrategy naming) {
/* 249 */     this._namingStrategy = naming;
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule addSerializer(JsonSerializer<?> ser) {
/* 261 */     _checkNotNull(ser, "serializer");
/* 262 */     if (this._serializers == null) {
/* 263 */       this._serializers = new SimpleSerializers();
/*     */     }
/* 265 */     this._serializers.addSerializer(ser);
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> SimpleModule addSerializer(Class<? extends T> type, JsonSerializer<T> ser) {
/* 271 */     _checkNotNull(type, "type to register serializer for");
/* 272 */     _checkNotNull(ser, "serializer");
/* 273 */     if (this._serializers == null) {
/* 274 */       this._serializers = new SimpleSerializers();
/*     */     }
/* 276 */     this._serializers.addSerializer(type, ser);
/* 277 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> SimpleModule addKeySerializer(Class<? extends T> type, JsonSerializer<T> ser) {
/* 282 */     _checkNotNull(type, "type to register key serializer for");
/* 283 */     _checkNotNull(ser, "key serializer");
/* 284 */     if (this._keySerializers == null) {
/* 285 */       this._keySerializers = new SimpleSerializers();
/*     */     }
/* 287 */     this._keySerializers.addSerializer(type, ser);
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> SimpleModule addDeserializer(Class<T> type, JsonDeserializer<? extends T> deser) {
/* 299 */     _checkNotNull(type, "type to register deserializer for");
/* 300 */     _checkNotNull(deser, "deserializer");
/* 301 */     if (this._deserializers == null) {
/* 302 */       this._deserializers = new SimpleDeserializers();
/*     */     }
/* 304 */     this._deserializers.addDeserializer(type, deser);
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleModule addKeyDeserializer(Class<?> type, KeyDeserializer deser) {
/* 310 */     _checkNotNull(type, "type to register key deserializer for");
/* 311 */     _checkNotNull(deser, "key deserializer");
/* 312 */     if (this._keyDeserializers == null) {
/* 313 */       this._keyDeserializers = new SimpleKeyDeserializers();
/*     */     }
/* 315 */     this._keyDeserializers.addDeserializer(type, deser);
/* 316 */     return this;
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
/*     */   public <T> SimpleModule addAbstractTypeMapping(Class<T> superType, Class<? extends T> subType) {
/* 333 */     _checkNotNull(superType, "abstract type to map");
/* 334 */     _checkNotNull(subType, "concrete type to map to");
/* 335 */     if (this._abstractTypes == null) {
/* 336 */       this._abstractTypes = new SimpleAbstractTypeResolver();
/*     */     }
/*     */     
/* 339 */     this._abstractTypes = this._abstractTypes.addMapping(superType, subType);
/* 340 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule registerSubtypes(Class<?>... subtypes) {
/* 350 */     if (this._subtypes == null) {
/* 351 */       this._subtypes = new LinkedHashSet<>();
/*     */     }
/* 353 */     for (Class<?> subtype : subtypes) {
/* 354 */       _checkNotNull(subtype, "subtype to register");
/* 355 */       this._subtypes.add(new NamedType(subtype));
/*     */     } 
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleModule registerSubtypes(NamedType... subtypes) {
/* 367 */     if (this._subtypes == null) {
/* 368 */       this._subtypes = new LinkedHashSet<>();
/*     */     }
/* 370 */     for (NamedType subtype : subtypes) {
/* 371 */       _checkNotNull(subtype, "subtype to register");
/* 372 */       this._subtypes.add(subtype);
/*     */     } 
/* 374 */     return this;
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
/*     */   public SimpleModule registerSubtypes(Collection<Class<?>> subtypes) {
/* 386 */     if (this._subtypes == null) {
/* 387 */       this._subtypes = new LinkedHashSet<>();
/*     */     }
/* 389 */     for (Class<?> subtype : subtypes) {
/* 390 */       _checkNotNull(subtype, "subtype to register");
/* 391 */       this._subtypes.add(new NamedType(subtype));
/*     */     } 
/* 393 */     return this;
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
/*     */   public SimpleModule addValueInstantiator(Class<?> beanType, ValueInstantiator inst) {
/* 411 */     _checkNotNull(beanType, "class to register value instantiator for");
/* 412 */     _checkNotNull(inst, "value instantiator");
/* 413 */     if (this._valueInstantiators == null) {
/* 414 */       this._valueInstantiators = new SimpleValueInstantiators();
/*     */     }
/* 416 */     this._valueInstantiators = this._valueInstantiators.addValueInstantiator(beanType, inst);
/* 417 */     return this;
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
/*     */   public SimpleModule setMixInAnnotation(Class<?> targetType, Class<?> mixinClass) {
/* 430 */     _checkNotNull(targetType, "target type");
/* 431 */     _checkNotNull(mixinClass, "mixin class");
/* 432 */     if (this._mixins == null) {
/* 433 */       this._mixins = new HashMap<>();
/*     */     }
/* 435 */     this._mixins.put(targetType, mixinClass);
/* 436 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModuleName() {
/* 447 */     return this._name;
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
/*     */   public void setupModule(Module.SetupContext context) {
/* 460 */     if (this._serializers != null) {
/* 461 */       context.addSerializers((Serializers)this._serializers);
/*     */     }
/* 463 */     if (this._deserializers != null) {
/* 464 */       context.addDeserializers(this._deserializers);
/*     */     }
/* 466 */     if (this._keySerializers != null) {
/* 467 */       context.addKeySerializers((Serializers)this._keySerializers);
/*     */     }
/* 469 */     if (this._keyDeserializers != null) {
/* 470 */       context.addKeyDeserializers(this._keyDeserializers);
/*     */     }
/* 472 */     if (this._abstractTypes != null) {
/* 473 */       context.addAbstractTypeResolver(this._abstractTypes);
/*     */     }
/* 475 */     if (this._valueInstantiators != null) {
/* 476 */       context.addValueInstantiators((ValueInstantiators)this._valueInstantiators);
/*     */     }
/* 478 */     if (this._deserializerModifier != null) {
/* 479 */       context.addBeanDeserializerModifier(this._deserializerModifier);
/*     */     }
/* 481 */     if (this._serializerModifier != null) {
/* 482 */       context.addBeanSerializerModifier(this._serializerModifier);
/*     */     }
/* 484 */     if (this._subtypes != null && this._subtypes.size() > 0) {
/* 485 */       context.registerSubtypes((NamedType[])this._subtypes.toArray((Object[])new NamedType[this._subtypes.size()]));
/*     */     }
/* 487 */     if (this._namingStrategy != null) {
/* 488 */       context.setNamingStrategy(this._namingStrategy);
/*     */     }
/* 490 */     if (this._mixins != null) {
/* 491 */       for (Map.Entry<Class<?>, Class<?>> entry : this._mixins.entrySet()) {
/* 492 */         context.setMixInAnnotations(entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Version version() {
/* 498 */     return this._version;
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
/*     */   protected void _checkNotNull(Object thingy, String type) {
/* 511 */     if (thingy == null)
/* 512 */       throw new IllegalArgumentException(String.format("Cannot pass `null` as %s", new Object[] { type })); 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\module\SimpleModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */