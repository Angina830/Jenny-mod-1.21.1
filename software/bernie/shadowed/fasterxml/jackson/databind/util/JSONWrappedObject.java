/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*     */ public class JSONWrappedObject
/*     */   implements JsonSerializable
/*     */ {
/*     */   protected final String _prefix;
/*     */   protected final String _suffix;
/*     */   protected final Object _value;
/*     */   protected final JavaType _serializationType;
/*     */   
/*     */   public JSONWrappedObject(String prefix, String suffix, Object value) {
/*  46 */     this(prefix, suffix, value, (JavaType)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWrappedObject(String prefix, String suffix, Object value, JavaType asType) {
/*  56 */     this._prefix = prefix;
/*  57 */     this._suffix = suffix;
/*  58 */     this._value = value;
/*  59 */     this._serializationType = asType;
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
/*     */   public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonProcessingException {
/*  73 */     serialize(jgen, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
/*  81 */     if (this._prefix != null) jgen.writeRaw(this._prefix); 
/*  82 */     if (this._value == null) {
/*  83 */       provider.defaultSerializeNull(jgen);
/*  84 */     } else if (this._serializationType != null) {
/*  85 */       provider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, jgen, provider);
/*     */     } else {
/*  87 */       Class<?> cls = this._value.getClass();
/*  88 */       provider.findTypedValueSerializer(cls, true, null).serialize(this._value, jgen, provider);
/*     */     } 
/*  90 */     if (this._suffix != null) jgen.writeRaw(this._suffix);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  99 */     return this._prefix;
/* 100 */   } public String getSuffix() { return this._suffix; }
/* 101 */   public Object getValue() { return this._value; } public JavaType getSerializationType() {
/* 102 */     return this._serializationType;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\JSONWrappedObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */