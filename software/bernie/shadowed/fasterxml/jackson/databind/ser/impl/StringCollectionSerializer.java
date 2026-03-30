/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class StringCollectionSerializer
/*     */   extends StaticListSerializerBase<Collection<String>>
/*     */ {
/*  27 */   public static final StringCollectionSerializer instance = new StringCollectionSerializer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringCollectionSerializer() {
/*  36 */     super(Collection.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringCollectionSerializer(StringCollectionSerializer src, Boolean unwrapSingle) {
/*  42 */     super(src, unwrapSingle);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/*  47 */     return (JsonSerializer<?>)new StringCollectionSerializer(this, unwrapSingle);
/*     */   }
/*     */   
/*     */   protected JsonNode contentSchema() {
/*  51 */     return (JsonNode)createSchemaNode("string", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void acceptContentVisitor(JsonArrayFormatVisitor visitor) throws JsonMappingException {
/*  57 */     visitor.itemsFormat(JsonFormatTypes.STRING);
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
/*     */   public void serialize(Collection<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  70 */     g.setCurrentValue(value);
/*  71 */     int len = value.size();
/*  72 */     if (len == 1 && ((
/*  73 */       this._unwrapSingle == null && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
/*     */ 
/*     */       
/*  76 */       serializeContents(value, g, provider);
/*     */       
/*     */       return;
/*     */     } 
/*  80 */     g.writeStartArray(len);
/*  81 */     serializeContents(value, g, provider);
/*  82 */     g.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeWithType(Collection<String> value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*  90 */     g.setCurrentValue(value);
/*  91 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
/*     */     
/*  93 */     serializeContents(value, g, provider);
/*  94 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void serializeContents(Collection<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 101 */     int i = 0;
/*     */     
/*     */     try {
/* 104 */       for (String str : value) {
/* 105 */         if (str == null) {
/* 106 */           provider.defaultSerializeNull(g);
/*     */         } else {
/* 108 */           g.writeString(str);
/*     */         } 
/* 110 */         i++;
/*     */       } 
/* 112 */     } catch (Exception e) {
/* 113 */       wrapAndThrow(provider, e, value, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\StringCollectionSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */