/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.module;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ArrayType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.ClassKey;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.CollectionLikeType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.CollectionType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapLikeType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.MapType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleSerializers
/*     */   extends Serializers.Base
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8531646511998456779L;
/*  37 */   protected HashMap<ClassKey, JsonSerializer<?>> _classMappings = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _hasEnumSerializer = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleSerializers() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleSerializers(List<JsonSerializer<?>> sers) {
/*  63 */     addSerializers(sers);
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
/*     */   public void addSerializer(JsonSerializer<?> ser) {
/*  78 */     Class<?> cls = ser.handledType();
/*  79 */     if (cls == null || cls == Object.class) {
/*  80 */       throw new IllegalArgumentException("JsonSerializer of type " + ser.getClass().getName() + " does not define valid handledType() -- must either register with method that takes type argument " + " or make serializer extend 'com.fasterxml.jackson.databind.ser.std.StdSerializer'");
/*     */     }
/*     */ 
/*     */     
/*  84 */     _addSerializer(cls, ser);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void addSerializer(Class<? extends T> type, JsonSerializer<T> ser) {
/*  89 */     _addSerializer(type, ser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSerializers(List<JsonSerializer<?>> sers) {
/*  96 */     for (JsonSerializer<?> ser : sers) {
/*  97 */       addSerializer(ser);
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
/*     */   public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
/* 111 */     Class<?> cls = type.getRawClass();
/* 112 */     ClassKey key = new ClassKey(cls);
/* 113 */     JsonSerializer<?> ser = null;
/*     */ 
/*     */     
/* 116 */     if (cls.isInterface()) {
/* 117 */       if (this._interfaceMappings != null) {
/* 118 */         ser = this._interfaceMappings.get(key);
/* 119 */         if (ser != null) {
/* 120 */           return ser;
/*     */         }
/*     */       }
/*     */     
/* 124 */     } else if (this._classMappings != null) {
/* 125 */       ser = this._classMappings.get(key);
/* 126 */       if (ser != null) {
/* 127 */         return ser;
/*     */       }
/*     */ 
/*     */       
/* 131 */       if (this._hasEnumSerializer && type.isEnumType()) {
/* 132 */         key.reset(Enum.class);
/* 133 */         ser = this._classMappings.get(key);
/* 134 */         if (ser != null) {
/* 135 */           return ser;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 140 */       for (Class<?> curr = cls; curr != null; curr = curr.getSuperclass()) {
/* 141 */         key.reset(curr);
/* 142 */         ser = this._classMappings.get(key);
/* 143 */         if (ser != null) {
/* 144 */           return ser;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if (this._interfaceMappings != null) {
/* 151 */       ser = _findInterfaceMapping(cls, key);
/* 152 */       if (ser != null) {
/* 153 */         return ser;
/*     */       }
/*     */       
/* 156 */       if (!cls.isInterface()) {
/* 157 */         while ((cls = cls.getSuperclass()) != null) {
/* 158 */           ser = _findInterfaceMapping(cls, key);
/* 159 */           if (ser != null) {
/* 160 */             return ser;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> findArraySerializer(SerializationConfig config, ArrayType type, BeanDescription beanDesc, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
/* 172 */     return findSerializer(config, (JavaType)type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> findCollectionSerializer(SerializationConfig config, CollectionType type, BeanDescription beanDesc, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
/* 179 */     return findSerializer(config, (JavaType)type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig config, CollectionLikeType type, BeanDescription beanDesc, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
/* 186 */     return findSerializer(config, (JavaType)type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> findMapSerializer(SerializationConfig config, MapType type, BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
/* 194 */     return findSerializer(config, (JavaType)type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> findMapLikeSerializer(SerializationConfig config, MapLikeType type, BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
/* 202 */     return findSerializer(config, (JavaType)type, beanDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonSerializer<?> _findInterfaceMapping(Class<?> cls, ClassKey key) {
/* 213 */     for (Class<?> iface : cls.getInterfaces()) {
/* 214 */       key.reset(iface);
/* 215 */       JsonSerializer<?> ser = this._interfaceMappings.get(key);
/* 216 */       if (ser != null) {
/* 217 */         return ser;
/*     */       }
/* 219 */       ser = _findInterfaceMapping(iface, key);
/* 220 */       if (ser != null) {
/* 221 */         return ser;
/*     */       }
/*     */     } 
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _addSerializer(Class<?> cls, JsonSerializer<?> ser) {
/* 229 */     ClassKey key = new ClassKey(cls);
/*     */     
/* 231 */     if (cls.isInterface()) {
/* 232 */       if (this._interfaceMappings == null) {
/* 233 */         this._interfaceMappings = new HashMap<>();
/*     */       }
/* 235 */       this._interfaceMappings.put(key, ser);
/*     */     } else {
/* 237 */       if (this._classMappings == null) {
/* 238 */         this._classMappings = new HashMap<>();
/*     */       }
/* 240 */       this._classMappings.put(key, ser);
/* 241 */       if (cls == Enum.class)
/* 242 */         this._hasEnumSerializer = true; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\module\SimpleSerializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */