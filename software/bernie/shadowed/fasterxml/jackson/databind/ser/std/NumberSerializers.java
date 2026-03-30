/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberSerializers
/*     */ {
/*     */   public static void addAll(Map<String, JsonSerializer<?>> allDeserializers) {
/*  26 */     allDeserializers.put(Integer.class.getName(), new IntegerSerializer(Integer.class));
/*  27 */     allDeserializers.put(int.class.getName(), new IntegerSerializer(int.class));
/*  28 */     allDeserializers.put(Long.class.getName(), new LongSerializer(Long.class));
/*  29 */     allDeserializers.put(long.class.getName(), new LongSerializer(long.class));
/*     */     
/*  31 */     allDeserializers.put(Byte.class.getName(), IntLikeSerializer.instance);
/*  32 */     allDeserializers.put(byte.class.getName(), IntLikeSerializer.instance);
/*  33 */     allDeserializers.put(Short.class.getName(), ShortSerializer.instance);
/*  34 */     allDeserializers.put(short.class.getName(), ShortSerializer.instance);
/*     */ 
/*     */     
/*  37 */     allDeserializers.put(Double.class.getName(), new DoubleSerializer(Double.class));
/*  38 */     allDeserializers.put(double.class.getName(), new DoubleSerializer(double.class));
/*  39 */     allDeserializers.put(Float.class.getName(), FloatSerializer.instance);
/*  40 */     allDeserializers.put(float.class.getName(), FloatSerializer.instance);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static abstract class Base<T>
/*     */     extends StdScalarSerializer<T>
/*     */     implements ContextualSerializer
/*     */   {
/*     */     protected final JsonParser.NumberType _numberType;
/*     */     
/*     */     protected final String _schemaType;
/*     */     
/*     */     protected final boolean _isInt;
/*     */ 
/*     */     
/*     */     protected Base(Class<?> cls, JsonParser.NumberType numberType, String schemaType) {
/*  57 */       super(cls, false);
/*  58 */       this._numberType = numberType;
/*  59 */       this._schemaType = schemaType;
/*  60 */       this._isInt = (numberType == JsonParser.NumberType.INT || numberType == JsonParser.NumberType.LONG || numberType == JsonParser.NumberType.BIG_INTEGER);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/*  67 */       return (JsonNode)createSchemaNode(this._schemaType, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/*  74 */       if (this._isInt) {
/*  75 */         visitIntFormat(visitor, typeHint, this._numberType);
/*     */       } else {
/*  77 */         visitFloatFormat(visitor, typeHint, this._numberType);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
/*  85 */       JsonFormat.Value format = findFormatOverrides(prov, property, handledType());
/*  86 */       if (format != null) {
/*  87 */         switch (format.getShape()) {
/*     */           case STRING:
/*  89 */             return ToStringSerializer.instance;
/*     */         } 
/*     */       
/*     */       }
/*  93 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class ShortSerializer
/*     */     extends Base<Object>
/*     */   {
/* 105 */     static final ShortSerializer instance = new ShortSerializer();
/*     */     
/*     */     public ShortSerializer() {
/* 108 */       super(Short.class, JsonParser.NumberType.INT, "number");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 114 */       gen.writeNumber(((Short)value).shortValue());
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
/*     */   @JacksonStdImpl
/*     */   public static final class IntegerSerializer
/*     */     extends Base<Object>
/*     */   {
/*     */     public IntegerSerializer(Class<?> type) {
/* 131 */       super(type, JsonParser.NumberType.INT, "integer");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 137 */       gen.writeNumber(((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 146 */       serialize(value, gen, provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class IntLikeSerializer
/*     */     extends Base<Object>
/*     */   {
/* 157 */     static final IntLikeSerializer instance = new IntLikeSerializer();
/*     */     
/*     */     public IntLikeSerializer() {
/* 160 */       super(Number.class, JsonParser.NumberType.INT, "integer");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 166 */       gen.writeNumber(((Number)value).intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class LongSerializer extends Base<Object> {
/*     */     public LongSerializer(Class<?> cls) {
/* 173 */       super(cls, JsonParser.NumberType.LONG, "number");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 179 */       gen.writeNumber(((Long)value).longValue());
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class FloatSerializer extends Base<Object> {
/* 185 */     static final FloatSerializer instance = new FloatSerializer();
/*     */     
/*     */     public FloatSerializer() {
/* 188 */       super(Float.class, JsonParser.NumberType.FLOAT, "number");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 194 */       gen.writeNumber(((Float)value).floatValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class DoubleSerializer
/*     */     extends Base<Object>
/*     */   {
/*     */     public DoubleSerializer(Class<?> cls) {
/* 208 */       super(cls, JsonParser.NumberType.DOUBLE, "number");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 214 */       gen.writeNumber(((Double)value).doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 223 */       serialize(value, gen, provider);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\NumberSerializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */