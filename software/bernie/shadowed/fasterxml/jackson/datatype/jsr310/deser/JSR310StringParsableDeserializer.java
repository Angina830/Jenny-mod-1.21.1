/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Period;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*     */ public class JSR310StringParsableDeserializer
/*     */   extends JSR310DeserializerBase<Object>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected static final int TYPE_PERIOD = 1;
/*     */   protected static final int TYPE_ZONE_ID = 2;
/*     */   protected static final int TYPE_ZONE_OFFSET = 3;
/*  52 */   public static final JsonDeserializer<Period> PERIOD = createDeserializer(Period.class, 1);
/*     */ 
/*     */   
/*  55 */   public static final JsonDeserializer<ZoneId> ZONE_ID = createDeserializer(ZoneId.class, 2);
/*     */ 
/*     */   
/*  58 */   public static final JsonDeserializer<ZoneOffset> ZONE_OFFSET = createDeserializer(ZoneOffset.class, 3);
/*     */ 
/*     */   
/*     */   protected final int _valueType;
/*     */ 
/*     */   
/*     */   protected JSR310StringParsableDeserializer(Class<?> supportedType, int valueId) {
/*  65 */     super(supportedType);
/*  66 */     this._valueType = valueId;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> JsonDeserializer<T> createDeserializer(Class<T> type, int typeId) {
/*  71 */     return (JsonDeserializer<T>)new JSR310StringParsableDeserializer(type, typeId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException {
/*  77 */     if (parser.hasToken(JsonToken.VALUE_STRING)) {
/*  78 */       String string = parser.getText().trim();
/*  79 */       if (string.length() == 0) {
/*  80 */         return null;
/*     */       }
/*     */       try {
/*  83 */         switch (this._valueType) {
/*     */           case 1:
/*  85 */             return Period.parse(string);
/*     */           case 2:
/*  87 */             return ZoneId.of(string);
/*     */           case 3:
/*  89 */             return ZoneOffset.of(string);
/*     */         } 
/*  91 */       } catch (DateTimeException e) {
/*  92 */         _rethrowDateTimeException(parser, context, e, string);
/*     */       } 
/*     */     } 
/*  95 */     if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT))
/*     */     {
/*     */       
/*  98 */       return parser.getEmbeddedObject();
/*     */     }
/* 100 */     if (parser.hasToken(JsonToken.START_ARRAY)) {
/* 101 */       return _deserializeFromArray(parser, context);
/*     */     }
/*     */     
/* 104 */     throw context.wrongTokenException(parser, handledType(), JsonToken.VALUE_STRING, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserializeWithType(JsonParser parser, DeserializationContext context, TypeDeserializer deserializer) throws IOException {
/* 115 */     JsonToken t = parser.getCurrentToken();
/* 116 */     if (t != null && t.isScalarValue()) {
/* 117 */       return deserialize(parser, context);
/*     */     }
/* 119 */     return deserializer.deserializeTypedFromAny(parser, context);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\deser\JSR310StringParsableDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */