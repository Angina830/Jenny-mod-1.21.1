/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.TypeKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SerializerCache
/*     */ {
/*  33 */   private final HashMap<TypeKey, JsonSerializer<Object>> _sharedMap = new HashMap<>(64);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private final AtomicReference<ReadOnlyClassToSerializerMap> _readOnlyMap = new AtomicReference<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReadOnlyClassToSerializerMap getReadOnlyLookupMap() {
/*  50 */     ReadOnlyClassToSerializerMap m = this._readOnlyMap.get();
/*  51 */     if (m != null) {
/*  52 */       return m;
/*     */     }
/*  54 */     return _makeReadOnlyLookupMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final synchronized ReadOnlyClassToSerializerMap _makeReadOnlyLookupMap() {
/*  60 */     ReadOnlyClassToSerializerMap m = this._readOnlyMap.get();
/*  61 */     if (m == null) {
/*  62 */       m = ReadOnlyClassToSerializerMap.from(this._sharedMap);
/*  63 */       this._readOnlyMap.set(m);
/*     */     } 
/*  65 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int size() {
/*  75 */     return this._sharedMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> untypedValueSerializer(Class<?> type) {
/*  84 */     synchronized (this) {
/*  85 */       return this._sharedMap.get(new TypeKey(type, false));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> untypedValueSerializer(JavaType type) {
/*  91 */     synchronized (this) {
/*  92 */       return this._sharedMap.get(new TypeKey(type, false));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> typedValueSerializer(JavaType type) {
/*  98 */     synchronized (this) {
/*  99 */       return this._sharedMap.get(new TypeKey(type, true));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> typedValueSerializer(Class<?> cls) {
/* 105 */     synchronized (this) {
/* 106 */       return this._sharedMap.get(new TypeKey(cls, true));
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
/*     */   public void addTypedSerializer(JavaType type, JsonSerializer<Object> ser) {
/* 123 */     synchronized (this) {
/* 124 */       if (this._sharedMap.put(new TypeKey(type, true), ser) == null)
/*     */       {
/* 126 */         this._readOnlyMap.set(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTypedSerializer(Class<?> cls, JsonSerializer<Object> ser) {
/* 133 */     synchronized (this) {
/* 134 */       if (this._sharedMap.put(new TypeKey(cls, true), ser) == null)
/*     */       {
/* 136 */         this._readOnlyMap.set(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAndResolveNonTypedSerializer(Class<?> type, JsonSerializer<Object> ser, SerializerProvider provider) throws JsonMappingException {
/* 145 */     synchronized (this) {
/* 146 */       if (this._sharedMap.put(new TypeKey(type, false), ser) == null) {
/* 147 */         this._readOnlyMap.set(null);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       if (ser instanceof ResolvableSerializer) {
/* 155 */         ((ResolvableSerializer)ser).resolve(provider);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAndResolveNonTypedSerializer(JavaType type, JsonSerializer<Object> ser, SerializerProvider provider) throws JsonMappingException {
/* 164 */     synchronized (this) {
/* 165 */       if (this._sharedMap.put(new TypeKey(type, false), ser) == null) {
/* 166 */         this._readOnlyMap.set(null);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       if (ser instanceof ResolvableSerializer) {
/* 174 */         ((ResolvableSerializer)ser).resolve(provider);
/*     */       }
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
/*     */   public void addAndResolveNonTypedSerializer(Class<?> rawType, JavaType fullType, JsonSerializer<Object> ser, SerializerProvider provider) throws JsonMappingException {
/* 190 */     synchronized (this) {
/* 191 */       Object<Object> ob1 = (Object<Object>)this._sharedMap.put(new TypeKey(rawType, false), ser);
/* 192 */       Object<Object> ob2 = (Object<Object>)this._sharedMap.put(new TypeKey(fullType, false), ser);
/* 193 */       if (ob1 == null || ob2 == null) {
/* 194 */         this._readOnlyMap.set(null);
/*     */       }
/* 196 */       if (ser instanceof ResolvableSerializer) {
/* 197 */         ((ResolvableSerializer)ser).resolve(provider);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/* 207 */     this._sharedMap.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\SerializerCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */