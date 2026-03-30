/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Collection;
/*     */ import java.util.Currency;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.regex.Pattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ 
/*     */ public class StdJdkSerializers {
/*     */   public static Collection<Map.Entry<Class<?>, Object>> all() {
/*  27 */     HashMap<Class<?>, Object> sers = new HashMap<>();
/*     */ 
/*     */     
/*  30 */     sers.put(URL.class, new ToStringSerializer(URL.class));
/*  31 */     sers.put(URI.class, new ToStringSerializer(URI.class));
/*     */     
/*  33 */     sers.put(Currency.class, new ToStringSerializer(Currency.class));
/*  34 */     sers.put(UUID.class, new UUIDSerializer());
/*  35 */     sers.put(Pattern.class, new ToStringSerializer(Pattern.class));
/*  36 */     sers.put(Locale.class, new ToStringSerializer(Locale.class));
/*     */ 
/*     */     
/*  39 */     sers.put(AtomicBoolean.class, AtomicBooleanSerializer.class);
/*  40 */     sers.put(AtomicInteger.class, AtomicIntegerSerializer.class);
/*  41 */     sers.put(AtomicLong.class, AtomicLongSerializer.class);
/*     */ 
/*     */     
/*  44 */     sers.put(File.class, FileSerializer.class);
/*  45 */     sers.put(Class.class, ClassSerializer.class);
/*     */ 
/*     */     
/*  48 */     sers.put(Void.class, NullSerializer.instance);
/*  49 */     sers.put(void.class, NullSerializer.instance);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  55 */       sers.put(Timestamp.class, DateSerializer.instance);
/*     */ 
/*     */       
/*  58 */       sers.put(Date.class, SqlDateSerializer.class);
/*  59 */       sers.put(Time.class, SqlTimeSerializer.class);
/*  60 */     } catch (NoClassDefFoundError e) {}
/*     */ 
/*     */ 
/*     */     
/*  64 */     return sers.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AtomicBooleanSerializer
/*     */     extends StdScalarSerializer<AtomicBoolean>
/*     */   {
/*     */     public AtomicBooleanSerializer() {
/*  76 */       super(AtomicBoolean.class, false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicBoolean value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
/*  80 */       gen.writeBoolean(value.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/*  85 */       return (JsonNode)createSchemaNode("boolean", true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/*  90 */       visitor.expectBooleanFormat(typeHint);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AtomicIntegerSerializer
/*     */     extends StdScalarSerializer<AtomicInteger> {
/*     */     public AtomicIntegerSerializer() {
/*  97 */       super(AtomicInteger.class, false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicInteger value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
/* 101 */       gen.writeNumber(value.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 106 */       return (JsonNode)createSchemaNode("integer", true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 112 */       visitIntFormat(visitor, typeHint, JsonParser.NumberType.INT);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AtomicLongSerializer
/*     */     extends StdScalarSerializer<AtomicLong> {
/*     */     public AtomicLongSerializer() {
/* 119 */       super(AtomicLong.class, false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicLong value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
/* 123 */       gen.writeNumber(value.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 128 */       return (JsonNode)createSchemaNode("integer", true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 135 */       visitIntFormat(visitor, typeHint, JsonParser.NumberType.LONG);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StdJdkSerializers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */