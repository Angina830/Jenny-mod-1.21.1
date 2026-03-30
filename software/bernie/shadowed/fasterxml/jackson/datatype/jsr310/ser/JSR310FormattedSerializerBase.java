/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.Locale;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
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
/*     */ abstract class JSR310FormattedSerializerBase<T>
/*     */   extends JSR310SerializerBase<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Boolean _useTimestamp;
/*     */   protected final DateTimeFormatter _formatter;
/*     */   protected final JsonFormat.Shape _shape;
/*     */   
/*     */   protected JSR310FormattedSerializerBase(Class<T> supportedType) {
/*  62 */     this(supportedType, (DateTimeFormatter)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JSR310FormattedSerializerBase(Class<T> supportedType, DateTimeFormatter formatter) {
/*  67 */     super(supportedType);
/*  68 */     this._useTimestamp = null;
/*  69 */     this._shape = null;
/*  70 */     this._formatter = formatter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JSR310FormattedSerializerBase(JSR310FormattedSerializerBase<?> base, Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
/*  76 */     super(base.handledType());
/*  77 */     this._useTimestamp = useTimestamp;
/*  78 */     this._formatter = dtf;
/*  79 */     this._shape = shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract JSR310FormattedSerializerBase<?> withFormat(Boolean paramBoolean, DateTimeFormatter paramDateTimeFormatter, JsonFormat.Shape paramShape);
/*     */ 
/*     */ 
/*     */   
/*     */   protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId) {
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
/*  97 */     JsonFormat.Value format = findFormatOverrides(prov, property, handledType());
/*  98 */     if (format != null) {
/*  99 */       Boolean useTimestamp = null;
/*     */ 
/*     */       
/* 102 */       JsonFormat.Shape shape = format.getShape();
/* 103 */       if (shape == JsonFormat.Shape.ARRAY || shape.isNumeric()) {
/* 104 */         useTimestamp = Boolean.TRUE;
/*     */       } else {
/* 106 */         useTimestamp = (shape == JsonFormat.Shape.STRING) ? Boolean.FALSE : null;
/*     */       } 
/* 108 */       DateTimeFormatter dtf = this._formatter;
/*     */ 
/*     */       
/* 111 */       if (format.hasPattern()) {
/* 112 */         String pattern = format.getPattern();
/* 113 */         Locale locale = format.hasLocale() ? format.getLocale() : prov.getLocale();
/* 114 */         if (locale == null) {
/* 115 */           dtf = DateTimeFormatter.ofPattern(pattern);
/*     */         } else {
/* 117 */           dtf = DateTimeFormatter.ofPattern(pattern, locale);
/*     */         } 
/*     */ 
/*     */         
/* 121 */         if (format.hasTimeZone()) {
/* 122 */           dtf = dtf.withZone(format.getTimeZone().toZoneId());
/*     */         }
/*     */       } 
/* 125 */       JSR310FormattedSerializerBase<?> ser = this;
/* 126 */       if (shape != this._shape || useTimestamp != this._useTimestamp || dtf != this._formatter) {
/* 127 */         ser = ser.withFormat(useTimestamp, dtf, shape);
/*     */       }
/* 129 */       Boolean writeZoneId = format.getFeature(JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID);
/* 130 */       if (writeZoneId != null) {
/* 131 */         ser = ser.withFeatures(writeZoneId);
/*     */       }
/* 133 */       return (JsonSerializer<?>)ser;
/*     */     } 
/* 135 */     return (JsonSerializer<?>)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 141 */     return (JsonNode)createSchemaNode(
/* 142 */         provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) ? "array" : "string", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 149 */     SerializerProvider provider = visitor.getProvider();
/* 150 */     boolean useTimestamp = (provider != null && useTimestamp(provider));
/* 151 */     if (useTimestamp) {
/* 152 */       _acceptTimestampVisitor(visitor, typeHint);
/*     */     } else {
/* 154 */       JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
/* 155 */       if (v2 != null) {
/* 156 */         v2.format(JsonValueFormat.DATE_TIME);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 164 */     JsonArrayFormatVisitor v2 = visitor.expectArrayFormat(typeHint);
/* 165 */     if (v2 != null) {
/* 166 */       v2.itemsFormat(JsonFormatTypes.INTEGER);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean useTimestamp(SerializerProvider provider) {
/* 171 */     if (this._useTimestamp != null) {
/* 172 */       return this._useTimestamp.booleanValue();
/*     */     }
/*     */     
/* 175 */     if (this._formatter != null) {
/* 176 */       return false;
/*     */     }
/* 178 */     return provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */   }
/*     */   
/*     */   protected boolean _useTimestampExplicitOnly(SerializerProvider provider) {
/* 182 */     if (this._useTimestamp != null) {
/* 183 */       return this._useTimestamp.booleanValue();
/*     */     }
/* 185 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\ser\JSR310FormattedSerializerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */