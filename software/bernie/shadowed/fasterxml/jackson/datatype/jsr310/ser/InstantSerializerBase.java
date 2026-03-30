/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.function.ToLongFunction;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.DecimalUtils;
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
/*     */ public abstract class InstantSerializerBase<T extends Temporal>
/*     */   extends JSR310FormattedSerializerBase<T>
/*     */ {
/*     */   private final DateTimeFormatter defaultFormat;
/*     */   private final ToLongFunction<T> getEpochMillis;
/*     */   private final ToLongFunction<T> getEpochSeconds;
/*     */   private final ToIntFunction<T> getNanoseconds;
/*     */   
/*     */   protected InstantSerializerBase(Class<T> supportedType, ToLongFunction<T> getEpochMillis, ToLongFunction<T> getEpochSeconds, ToIntFunction<T> getNanoseconds, DateTimeFormatter formatter) {
/*  59 */     super(supportedType, null);
/*  60 */     this.defaultFormat = formatter;
/*  61 */     this.getEpochMillis = getEpochMillis;
/*  62 */     this.getEpochSeconds = getEpochSeconds;
/*  63 */     this.getNanoseconds = getNanoseconds;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected InstantSerializerBase(InstantSerializerBase<T> base, Boolean useTimestamp, DateTimeFormatter dtf) {
/*  69 */     super(base, useTimestamp, dtf, null);
/*  70 */     this.defaultFormat = base.defaultFormat;
/*  71 */     this.getEpochMillis = base.getEpochMillis;
/*  72 */     this.getEpochSeconds = base.getEpochSeconds;
/*  73 */     this.getNanoseconds = base.getNanoseconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(T value, JsonGenerator generator, SerializerProvider provider) throws IOException {
/*     */     String str;
/*  84 */     if (useTimestamp(provider)) {
/*  85 */       if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/*  86 */         generator.writeNumber(DecimalUtils.toBigDecimal(this.getEpochSeconds
/*  87 */               .applyAsLong(value), this.getNanoseconds.applyAsInt(value)));
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       generator.writeNumber(this.getEpochMillis.applyAsLong(value));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  96 */     if (this._formatter != null) {
/*  97 */       str = this._formatter.format((TemporalAccessor)value);
/*  98 */     } else if (this.defaultFormat != null) {
/*  99 */       str = this.defaultFormat.format((TemporalAccessor)value);
/*     */     } else {
/* 101 */       str = value.toString();
/*     */     } 
/* 103 */     generator.writeString(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 111 */     SerializerProvider prov = visitor.getProvider();
/* 112 */     if (prov != null && prov
/* 113 */       .isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 114 */       JsonNumberFormatVisitor v2 = visitor.expectNumberFormat(typeHint);
/* 115 */       if (v2 != null) {
/* 116 */         v2.numberType(JsonParser.NumberType.BIG_DECIMAL);
/*     */       }
/*     */     } else {
/* 119 */       JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
/* 120 */       if (v2 != null) {
/* 121 */         v2.numberType(JsonParser.NumberType.LONG);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken serializationShape(SerializerProvider provider) {
/* 128 */     if (useTimestamp(provider)) {
/* 129 */       if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)) {
/* 130 */         return JsonToken.VALUE_NUMBER_FLOAT;
/*     */       }
/* 132 */       return JsonToken.VALUE_NUMBER_INT;
/*     */     } 
/* 134 */     return JsonToken.VALUE_STRING;
/*     */   }
/*     */   
/*     */   protected abstract JSR310FormattedSerializerBase<?> withFormat(Boolean paramBoolean, DateTimeFormatter paramDateTimeFormatter, JsonFormat.Shape paramShape);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\InstantSerializerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */