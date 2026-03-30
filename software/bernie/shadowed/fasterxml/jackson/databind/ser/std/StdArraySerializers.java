/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*     */ 
/*     */ public class StdArraySerializers {
/*  25 */   protected static final HashMap<String, JsonSerializer<?>> _arraySerializers = new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/*  29 */     _arraySerializers.put(boolean[].class.getName(), new BooleanArraySerializer());
/*  30 */     _arraySerializers.put(byte[].class.getName(), new ByteArraySerializer());
/*  31 */     _arraySerializers.put(char[].class.getName(), new CharArraySerializer());
/*  32 */     _arraySerializers.put(short[].class.getName(), new ShortArraySerializer());
/*  33 */     _arraySerializers.put(int[].class.getName(), new IntArraySerializer());
/*  34 */     _arraySerializers.put(long[].class.getName(), new LongArraySerializer());
/*  35 */     _arraySerializers.put(float[].class.getName(), new FloatArraySerializer());
/*  36 */     _arraySerializers.put(double[].class.getName(), new DoubleArraySerializer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonSerializer<?> findStandardImpl(Class<?> cls) {
/*  46 */     return _arraySerializers.get(cls.getName());
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
/*     */   protected static abstract class TypedPrimitiveArraySerializer<T>
/*     */     extends ArraySerializerBase<T>
/*     */   {
/*     */     protected TypedPrimitiveArraySerializer(Class<T> cls) {
/*  63 */       super(cls);
/*     */     }
/*     */ 
/*     */     
/*     */     protected TypedPrimitiveArraySerializer(TypedPrimitiveArraySerializer<T> src, BeanProperty prop, Boolean unwrapSingle) {
/*  68 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/*  76 */       return this;
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
/*     */   public static class BooleanArraySerializer
/*     */     extends ArraySerializerBase<boolean[]>
/*     */   {
/*  92 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Boolean.class);
/*     */     public BooleanArraySerializer() {
/*  94 */       super((Class)boolean[].class);
/*     */     }
/*     */     
/*     */     protected BooleanArraySerializer(BooleanArraySerializer src, BeanProperty prop, Boolean unwrapSingle) {
/*  98 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/* 103 */       return (JsonSerializer<?>)new BooleanArraySerializer(this, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/* 112 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getContentType() {
/* 117 */       return VALUE_TYPE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> getContentSerializer() {
/* 123 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, boolean[] value) {
/* 128 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSingleElement(boolean[] value) {
/* 133 */       return (value.length == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void serialize(boolean[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 139 */       int len = value.length;
/* 140 */       if (len == 1 && _shouldUnwrapSingle(provider)) {
/* 141 */         serializeContents(value, g, provider);
/*     */         return;
/*     */       } 
/* 144 */       g.writeStartArray(len);
/* 145 */       g.setCurrentValue(value);
/* 146 */       serializeContents(value, g, provider);
/* 147 */       g.writeEndArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeContents(boolean[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 154 */       for (int i = 0, len = value.length; i < len; i++) {
/* 155 */         g.writeBoolean(value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 162 */       ObjectNode o = createSchemaNode("array", true);
/* 163 */       o.set("items", (JsonNode)createSchemaNode("boolean"));
/* 164 */       return (JsonNode)o;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 171 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.BOOLEAN);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class ShortArraySerializer
/*     */     extends TypedPrimitiveArraySerializer<short[]>
/*     */   {
/* 180 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(short.class);
/*     */     public ShortArraySerializer() {
/* 182 */       super((Class)short[].class);
/*     */     }
/*     */     public ShortArraySerializer(ShortArraySerializer src, BeanProperty prop, Boolean unwrapSingle) {
/* 185 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/* 190 */       return (JsonSerializer<?>)new ShortArraySerializer(this, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getContentType() {
/* 195 */       return VALUE_TYPE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> getContentSerializer() {
/* 201 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, short[] value) {
/* 206 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSingleElement(short[] value) {
/* 211 */       return (value.length == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void serialize(short[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 217 */       int len = value.length;
/* 218 */       if (len == 1 && _shouldUnwrapSingle(provider)) {
/* 219 */         serializeContents(value, g, provider);
/*     */         return;
/*     */       } 
/* 222 */       g.writeStartArray(len);
/* 223 */       g.setCurrentValue(value);
/* 224 */       serializeContents(value, g, provider);
/* 225 */       g.writeEndArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeContents(short[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 233 */       for (int i = 0, len = value.length; i < len; i++) {
/* 234 */         g.writeNumber(value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 242 */       ObjectNode o = createSchemaNode("array", true);
/* 243 */       return o.set("items", (JsonNode)createSchemaNode("integer"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 250 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.INTEGER);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class CharArraySerializer
/*     */     extends StdSerializer<char[]>
/*     */   {
/*     */     public CharArraySerializer() {
/* 264 */       super((Class)char[].class);
/*     */     }
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, char[] value) {
/* 268 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(char[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 276 */       if (provider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
/* 277 */         g.writeStartArray(value.length);
/* 278 */         g.setCurrentValue(value);
/* 279 */         _writeArrayContents(g, value);
/* 280 */         g.writeEndArray();
/*     */       } else {
/* 282 */         g.writeString(value, 0, value.length);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeWithType(char[] value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/*     */       WritableTypeId typeIdDef;
/* 292 */       boolean asArray = provider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);
/*     */       
/* 294 */       if (asArray) {
/* 295 */         typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
/*     */         
/* 297 */         _writeArrayContents(g, value);
/*     */       } else {
/* 299 */         typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_STRING));
/*     */         
/* 301 */         g.writeString(value, 0, value.length);
/*     */       } 
/* 303 */       typeSer.writeTypeSuffix(g, typeIdDef);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final void _writeArrayContents(JsonGenerator g, char[] value) throws IOException {
/* 309 */       for (int i = 0, len = value.length; i < len; i++) {
/* 310 */         g.writeString(value, i, 1);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 317 */       ObjectNode o = createSchemaNode("array", true);
/* 318 */       ObjectNode itemSchema = createSchemaNode("string");
/* 319 */       itemSchema.put("type", "string");
/* 320 */       return o.set("items", (JsonNode)itemSchema);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 327 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.STRING);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class IntArraySerializer
/*     */     extends ArraySerializerBase<int[]>
/*     */   {
/* 336 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(int.class);
/*     */     public IntArraySerializer() {
/* 338 */       super((Class)int[].class);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected IntArraySerializer(IntArraySerializer src, BeanProperty prop, Boolean unwrapSingle) {
/* 345 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/* 350 */       return (JsonSerializer<?>)new IntArraySerializer(this, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/* 359 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getContentType() {
/* 364 */       return VALUE_TYPE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> getContentSerializer() {
/* 370 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, int[] value) {
/* 375 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSingleElement(int[] value) {
/* 380 */       return (value.length == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void serialize(int[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 386 */       int len = value.length;
/* 387 */       if (len == 1 && _shouldUnwrapSingle(provider)) {
/* 388 */         serializeContents(value, g, provider);
/*     */         
/*     */         return;
/*     */       } 
/* 392 */       g.setCurrentValue(value);
/* 393 */       g.writeArray(value, 0, value.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeContents(int[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 400 */       for (int i = 0, len = value.length; i < len; i++) {
/* 401 */         g.writeNumber(value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 407 */       return createSchemaNode("array", true).set("items", (JsonNode)createSchemaNode("integer"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 413 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.INTEGER);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class LongArraySerializer
/*     */     extends TypedPrimitiveArraySerializer<long[]>
/*     */   {
/* 422 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(long.class);
/*     */     public LongArraySerializer() {
/* 424 */       super((Class)long[].class);
/*     */     }
/*     */     public LongArraySerializer(LongArraySerializer src, BeanProperty prop, Boolean unwrapSingle) {
/* 427 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/* 432 */       return (JsonSerializer<?>)new LongArraySerializer(this, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getContentType() {
/* 437 */       return VALUE_TYPE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> getContentSerializer() {
/* 443 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, long[] value) {
/* 448 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSingleElement(long[] value) {
/* 453 */       return (value.length == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void serialize(long[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 459 */       int len = value.length;
/* 460 */       if (len == 1 && _shouldUnwrapSingle(provider)) {
/* 461 */         serializeContents(value, g, provider);
/*     */         
/*     */         return;
/*     */       } 
/* 465 */       g.setCurrentValue(value);
/* 466 */       g.writeArray(value, 0, value.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeContents(long[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 473 */       for (int i = 0, len = value.length; i < len; i++) {
/* 474 */         g.writeNumber(value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 481 */       return createSchemaNode("array", true).set("items", (JsonNode)createSchemaNode("number", true));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 489 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.NUMBER);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class FloatArraySerializer
/*     */     extends TypedPrimitiveArraySerializer<float[]>
/*     */   {
/* 498 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(float.class);
/*     */     
/*     */     public FloatArraySerializer() {
/* 501 */       super((Class)float[].class);
/*     */     }
/*     */     
/*     */     public FloatArraySerializer(FloatArraySerializer src, BeanProperty prop, Boolean unwrapSingle) {
/* 505 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/* 510 */       return (JsonSerializer<?>)new FloatArraySerializer(this, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getContentType() {
/* 515 */       return VALUE_TYPE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> getContentSerializer() {
/* 521 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, float[] value) {
/* 526 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSingleElement(float[] value) {
/* 531 */       return (value.length == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void serialize(float[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 537 */       int len = value.length;
/* 538 */       if (len == 1 && _shouldUnwrapSingle(provider)) {
/* 539 */         serializeContents(value, g, provider);
/*     */         return;
/*     */       } 
/* 542 */       g.writeStartArray(len);
/* 543 */       g.setCurrentValue(value);
/* 544 */       serializeContents(value, g, provider);
/* 545 */       g.writeEndArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeContents(float[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 552 */       for (int i = 0, len = value.length; i < len; i++) {
/* 553 */         g.writeNumber(value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 559 */       return createSchemaNode("array", true).set("items", (JsonNode)createSchemaNode("number"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 565 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.NUMBER);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static class DoubleArraySerializer
/*     */     extends ArraySerializerBase<double[]>
/*     */   {
/* 574 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(double.class);
/*     */     public DoubleArraySerializer() {
/* 576 */       super((Class)double[].class);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected DoubleArraySerializer(DoubleArraySerializer src, BeanProperty prop, Boolean unwrapSingle) {
/* 583 */       super(src, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
/* 588 */       return (JsonSerializer<?>)new DoubleArraySerializer(this, prop, unwrapSingle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/* 597 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public JavaType getContentType() {
/* 602 */       return VALUE_TYPE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonSerializer<?> getContentSerializer() {
/* 608 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty(SerializerProvider prov, double[] value) {
/* 613 */       return (value.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSingleElement(double[] value) {
/* 618 */       return (value.length == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void serialize(double[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 624 */       int len = value.length;
/* 625 */       if (len == 1 && _shouldUnwrapSingle(provider)) {
/* 626 */         serializeContents(value, g, provider);
/*     */         return;
/*     */       } 
/* 629 */       g.setCurrentValue(value);
/*     */       
/* 631 */       g.writeArray(value, 0, value.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void serializeContents(double[] value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 637 */       for (int i = 0, len = value.length; i < len; i++) {
/* 638 */         g.writeNumber(value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 644 */       return createSchemaNode("array", true).set("items", (JsonNode)createSchemaNode("number"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 651 */       visitArrayFormat(visitor, typeHint, JsonFormatTypes.NUMBER);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StdArraySerializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */