/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public final class IndexedStringListSerializer
/*     */   extends StaticListSerializerBase<List<String>>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   public static final IndexedStringListSerializer instance = new IndexedStringListSerializer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IndexedStringListSerializer() {
/*  37 */     super(List.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public IndexedStringListSerializer(IndexedStringListSerializer src, Boolean unwrapSingle) {
/*  42 */     super(src, unwrapSingle);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/*  47 */     return (JsonSerializer<?>)new IndexedStringListSerializer(this, unwrapSingle);
/*     */   }
/*     */   protected JsonNode contentSchema() {
/*  50 */     return (JsonNode)createSchemaNode("string", true);
/*     */   }
/*     */   
/*     */   protected void acceptContentVisitor(JsonArrayFormatVisitor visitor) throws JsonMappingException {
/*  54 */     visitor.itemsFormat(JsonFormatTypes.STRING);
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
/*     */   public void serialize(List<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  67 */     int len = value.size();
/*  68 */     if (len == 1 && ((
/*  69 */       this._unwrapSingle == null && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
/*     */ 
/*     */       
/*  72 */       serializeContents(value, g, provider, 1);
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     g.writeStartArray(len);
/*  77 */     serializeContents(value, g, provider, len);
/*  78 */     g.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(List<String> value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*  86 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
/*     */     
/*  88 */     serializeContents(value, g, provider, value.size());
/*  89 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void serializeContents(List<String> value, JsonGenerator g, SerializerProvider provider, int len) throws IOException {
/*  95 */     g.setCurrentValue(value);
/*  96 */     int i = 0;
/*     */     try {
/*  98 */       for (; i < len; i++) {
/*  99 */         String str = value.get(i);
/* 100 */         if (str == null) {
/* 101 */           provider.defaultSerializeNull(g);
/*     */         } else {
/* 103 */           g.writeString(str);
/*     */         } 
/*     */       } 
/* 106 */     } catch (Exception e) {
/* 107 */       wrapAndThrow(provider, e, value, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\IndexedStringListSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */