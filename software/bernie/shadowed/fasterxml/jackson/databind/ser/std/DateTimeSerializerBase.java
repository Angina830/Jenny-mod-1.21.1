/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.StdDateFormat;
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
/*     */ public abstract class DateTimeSerializerBase<T>
/*     */   extends StdScalarSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final Boolean _useTimestamp;
/*     */   protected final DateFormat _customFormat;
/*     */   protected final AtomicReference<DateFormat> _reusedCustomFormat;
/*     */   
/*     */   protected DateTimeSerializerBase(Class<T> type, Boolean useTimestamp, DateFormat customFormat) {
/*  53 */     super(type);
/*  54 */     this._useTimestamp = useTimestamp;
/*  55 */     this._customFormat = customFormat;
/*  56 */     this._reusedCustomFormat = (customFormat == null) ? null : new AtomicReference<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract DateTimeSerializerBase<T> withFormat(Boolean paramBoolean, DateFormat paramDateFormat);
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/*  65 */     if (property == null) {
/*  66 */       return this;
/*     */     }
/*  68 */     JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/*  69 */     if (format == null) {
/*  70 */       return this;
/*     */     }
/*     */     
/*  73 */     JsonFormat.Shape shape = format.getShape();
/*  74 */     if (shape.isNumeric()) {
/*  75 */       return withFormat(Boolean.TRUE, (DateFormat)null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (format.hasPattern()) {
/*  81 */       Locale loc = format.hasLocale() ? format.getLocale() : serializers.getLocale();
/*     */ 
/*     */       
/*  84 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.getPattern(), loc);
/*  85 */       TimeZone tz = format.hasTimeZone() ? format.getTimeZone() : serializers.getTimeZone();
/*     */       
/*  87 */       simpleDateFormat.setTimeZone(tz);
/*  88 */       return withFormat(Boolean.FALSE, simpleDateFormat);
/*     */     } 
/*     */ 
/*     */     
/*  92 */     boolean hasLocale = format.hasLocale();
/*  93 */     boolean hasTZ = format.hasTimeZone();
/*  94 */     boolean asString = (shape == JsonFormat.Shape.STRING);
/*     */     
/*  96 */     if (!hasLocale && !hasTZ && !asString) {
/*  97 */       return this;
/*     */     }
/*     */     
/* 100 */     DateFormat df0 = serializers.getConfig().getDateFormat();
/*     */     
/* 102 */     if (df0 instanceof StdDateFormat) {
/* 103 */       StdDateFormat std = (StdDateFormat)df0;
/* 104 */       if (format.hasLocale()) {
/* 105 */         std = std.withLocale(format.getLocale());
/*     */       }
/* 107 */       if (format.hasTimeZone()) {
/* 108 */         std = std.withTimeZone(format.getTimeZone());
/*     */       }
/* 110 */       return withFormat(Boolean.FALSE, (DateFormat)std);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (!(df0 instanceof SimpleDateFormat)) {
/* 117 */       serializers.reportBadDefinition(handledType(), String.format("Configured `DateFormat` (%s) not a `SimpleDateFormat`; cannot configure `Locale` or `TimeZone`", new Object[] { df0.getClass().getName() }));
/*     */     }
/*     */ 
/*     */     
/* 121 */     SimpleDateFormat df = (SimpleDateFormat)df0;
/* 122 */     if (hasLocale) {
/*     */       
/* 124 */       df = new SimpleDateFormat(df.toPattern(), format.getLocale());
/*     */     } else {
/* 126 */       df = (SimpleDateFormat)df.clone();
/*     */     } 
/* 128 */     TimeZone newTz = format.getTimeZone();
/* 129 */     boolean changeTZ = (newTz != null && !newTz.equals(df.getTimeZone()));
/* 130 */     if (changeTZ) {
/* 131 */       df.setTimeZone(newTz);
/*     */     }
/* 133 */     return withFormat(Boolean.FALSE, df);
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
/*     */   public boolean isEmpty(SerializerProvider serializers, T value) {
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract long _timestamp(T paramT);
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider serializers, Type typeHint) {
/* 155 */     return (JsonNode)createSchemaNode(_asTimestamp(serializers) ? "number" : "string", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 161 */     _acceptJsonFormatVisitor(visitor, typeHint, _asTimestamp(visitor.getProvider()));
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
/*     */   public abstract void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _asTimestamp(SerializerProvider serializers) {
/* 182 */     if (this._useTimestamp != null) {
/* 183 */       return this._useTimestamp.booleanValue();
/*     */     }
/* 185 */     if (this._customFormat == null) {
/* 186 */       if (serializers != null) {
/* 187 */         return serializers.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */       }
/*     */       
/* 190 */       throw new IllegalArgumentException("Null SerializerProvider passed for " + handledType().getName());
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint, boolean asNumber) throws JsonMappingException {
/* 198 */     if (asNumber) {
/* 199 */       visitIntFormat(visitor, typeHint, JsonParser.NumberType.LONG, JsonValueFormat.UTC_MILLISEC);
/*     */     } else {
/*     */       
/* 202 */       visitStringFormat(visitor, typeHint, JsonValueFormat.DATE_TIME);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _serializeAsString(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 211 */     if (this._customFormat == null) {
/* 212 */       provider.defaultSerializeDateValue(value, g);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     DateFormat f = this._reusedCustomFormat.getAndSet(null);
/* 224 */     if (f == null) {
/* 225 */       f = (DateFormat)this._customFormat.clone();
/*     */     }
/* 227 */     g.writeString(f.format(value));
/* 228 */     this._reusedCustomFormat.compareAndSet(null, f);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\DateTimeSerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */