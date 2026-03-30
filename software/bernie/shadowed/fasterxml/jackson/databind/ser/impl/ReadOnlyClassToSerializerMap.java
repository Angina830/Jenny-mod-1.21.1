/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
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
/*     */ public final class ReadOnlyClassToSerializerMap
/*     */ {
/*     */   private final Bucket[] _buckets;
/*     */   private final int _size;
/*     */   private final int _mask;
/*     */   
/*     */   public ReadOnlyClassToSerializerMap(Map<TypeKey, JsonSerializer<Object>> serializers) {
/*  28 */     int size = findSize(serializers.size());
/*  29 */     this._size = size;
/*  30 */     this._mask = size - 1;
/*  31 */     Bucket[] buckets = new Bucket[size];
/*  32 */     for (Map.Entry<TypeKey, JsonSerializer<Object>> entry : serializers.entrySet()) {
/*  33 */       TypeKey key = entry.getKey();
/*  34 */       int index = key.hashCode() & this._mask;
/*  35 */       buckets[index] = new Bucket(buckets[index], key, entry.getValue());
/*     */     } 
/*  37 */     this._buckets = buckets;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int findSize(int size) {
/*  43 */     int needed = (size <= 64) ? (size + size) : (size + (size >> 2));
/*  44 */     int result = 8;
/*  45 */     while (result < needed) {
/*  46 */       result += result;
/*     */     }
/*  48 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReadOnlyClassToSerializerMap from(HashMap<TypeKey, JsonSerializer<Object>> src) {
/*  55 */     return new ReadOnlyClassToSerializerMap(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  64 */     return this._size;
/*     */   }
/*     */   
/*     */   public JsonSerializer<Object> typedValueSerializer(JavaType type) {
/*  68 */     Bucket bucket = this._buckets[TypeKey.typedHash(type) & this._mask];
/*  69 */     if (bucket == null) {
/*  70 */       return null;
/*     */     }
/*  72 */     if (bucket.matchesTyped(type)) {
/*  73 */       return bucket.value;
/*     */     }
/*  75 */     while ((bucket = bucket.next) != null) {
/*  76 */       if (bucket.matchesTyped(type)) {
/*  77 */         return bucket.value;
/*     */       }
/*     */     } 
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> typedValueSerializer(Class<?> type) {
/*  85 */     Bucket bucket = this._buckets[TypeKey.typedHash(type) & this._mask];
/*  86 */     if (bucket == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     if (bucket.matchesTyped(type)) {
/*  90 */       return bucket.value;
/*     */     }
/*  92 */     while ((bucket = bucket.next) != null) {
/*  93 */       if (bucket.matchesTyped(type)) {
/*  94 */         return bucket.value;
/*     */       }
/*     */     } 
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> untypedValueSerializer(JavaType type) {
/* 102 */     Bucket bucket = this._buckets[TypeKey.untypedHash(type) & this._mask];
/* 103 */     if (bucket == null) {
/* 104 */       return null;
/*     */     }
/* 106 */     if (bucket.matchesUntyped(type)) {
/* 107 */       return bucket.value;
/*     */     }
/* 109 */     while ((bucket = bucket.next) != null) {
/* 110 */       if (bucket.matchesUntyped(type)) {
/* 111 */         return bucket.value;
/*     */       }
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<Object> untypedValueSerializer(Class<?> type) {
/* 119 */     Bucket bucket = this._buckets[TypeKey.untypedHash(type) & this._mask];
/* 120 */     if (bucket == null) {
/* 121 */       return null;
/*     */     }
/* 123 */     if (bucket.matchesUntyped(type)) {
/* 124 */       return bucket.value;
/*     */     }
/* 126 */     while ((bucket = bucket.next) != null) {
/* 127 */       if (bucket.matchesUntyped(type)) {
/* 128 */         return bucket.value;
/*     */       }
/*     */     } 
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Bucket
/*     */   {
/*     */     public final JsonSerializer<Object> value;
/*     */ 
/*     */     
/*     */     public final Bucket next;
/*     */ 
/*     */     
/*     */     protected final Class<?> _class;
/*     */     
/*     */     protected final JavaType _type;
/*     */     
/*     */     protected final boolean _isTyped;
/*     */ 
/*     */     
/*     */     public Bucket(Bucket next, TypeKey key, JsonSerializer<Object> value) {
/* 152 */       this.next = next;
/* 153 */       this.value = value;
/* 154 */       this._isTyped = key.isTyped();
/* 155 */       this._class = key.getRawType();
/* 156 */       this._type = key.getType();
/*     */     }
/*     */     
/*     */     public boolean matchesTyped(Class<?> key) {
/* 160 */       return (this._class == key && this._isTyped);
/*     */     }
/*     */     
/*     */     public boolean matchesUntyped(Class<?> key) {
/* 164 */       return (this._class == key && !this._isTyped);
/*     */     }
/*     */     
/*     */     public boolean matchesTyped(JavaType key) {
/* 168 */       return (this._isTyped && key.equals(this._type));
/*     */     }
/*     */     
/*     */     public boolean matchesUntyped(JavaType key) {
/* 172 */       return (!this._isTyped && key.equals(this._type));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\ReadOnlyClassToSerializerMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */