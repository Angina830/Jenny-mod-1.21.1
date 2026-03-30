/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ArrayNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.EnumValues;
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
/*     */ @JacksonStdImpl
/*     */ public class EnumSerializer
/*     */   extends StdScalarSerializer<Enum<?>>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final EnumValues _values;
/*     */   protected final Boolean _serializeAsIndex;
/*     */   
/*     */   public EnumSerializer(EnumValues v, Boolean serializeAsIndex) {
/*  58 */     super(v.getEnumClass(), false);
/*  59 */     this._values = v;
/*  60 */     this._serializeAsIndex = serializeAsIndex;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumSerializer construct(Class<?> enumClass, SerializationConfig config, BeanDescription beanDesc, JsonFormat.Value format) {
/*  77 */     EnumValues v = EnumValues.constructFromName((MapperConfig)config, enumClass);
/*  78 */     Boolean serializeAsIndex = _isShapeWrittenUsingIndex(enumClass, format, true, (Boolean)null);
/*  79 */     return new EnumSerializer(v, serializeAsIndex);
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
/*     */   public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/*  91 */     JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/*     */     
/*  93 */     if (format != null) {
/*  94 */       Class<?> type = handledType();
/*  95 */       Boolean serializeAsIndex = _isShapeWrittenUsingIndex(type, format, false, this._serializeAsIndex);
/*     */       
/*  97 */       if (serializeAsIndex != this._serializeAsIndex) {
/*  98 */         return new EnumSerializer(this._values, serializeAsIndex);
/*     */       }
/*     */     } 
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumValues getEnumValues() {
/* 110 */     return this._values;
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
/*     */   public final void serialize(Enum<?> en, JsonGenerator gen, SerializerProvider serializers) throws IOException {
/* 123 */     if (_serializeAsIndex(serializers)) {
/* 124 */       gen.writeNumber(en.ordinal());
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     if (serializers.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
/* 129 */       gen.writeString(en.toString());
/*     */       return;
/*     */     } 
/* 132 */     gen.writeString(this._values.serializedValueFor(en));
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
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 144 */     if (_serializeAsIndex(provider)) {
/* 145 */       return (JsonNode)createSchemaNode("integer", true);
/*     */     }
/* 147 */     ObjectNode objectNode = createSchemaNode("string", true);
/* 148 */     if (typeHint != null) {
/* 149 */       JavaType type = provider.constructType(typeHint);
/* 150 */       if (type.isEnumType()) {
/* 151 */         ArrayNode enumNode = objectNode.putArray("enum");
/* 152 */         for (SerializableString value : this._values.values()) {
/* 153 */           enumNode.add(value.getValue());
/*     */         }
/*     */       } 
/*     */     } 
/* 157 */     return (JsonNode)objectNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 164 */     SerializerProvider serializers = visitor.getProvider();
/* 165 */     if (_serializeAsIndex(serializers)) {
/* 166 */       visitIntFormat(visitor, typeHint, JsonParser.NumberType.INT);
/*     */       return;
/*     */     } 
/* 169 */     JsonStringFormatVisitor stringVisitor = visitor.expectStringFormat(typeHint);
/* 170 */     if (stringVisitor != null) {
/* 171 */       Set<String> enums = new LinkedHashSet<>();
/*     */ 
/*     */       
/* 174 */       if (serializers != null && serializers.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
/*     */         
/* 176 */         for (Enum<?> e : (Iterable<Enum<?>>)this._values.enums()) {
/* 177 */           enums.add(e.toString());
/*     */         }
/*     */       } else {
/*     */         
/* 181 */         for (SerializableString value : this._values.values()) {
/* 182 */           enums.add(value.getValue());
/*     */         }
/*     */       } 
/* 185 */       stringVisitor.enumTypes(enums);
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
/*     */   protected final boolean _serializeAsIndex(SerializerProvider serializers) {
/* 197 */     if (this._serializeAsIndex != null) {
/* 198 */       return this._serializeAsIndex.booleanValue();
/*     */     }
/* 200 */     return serializers.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Boolean _isShapeWrittenUsingIndex(Class<?> enumClass, JsonFormat.Value format, boolean fromClass, Boolean defaultValue) {
/* 211 */     JsonFormat.Shape shape = (format == null) ? null : format.getShape();
/* 212 */     if (shape == null) {
/* 213 */       return defaultValue;
/*     */     }
/*     */     
/* 216 */     if (shape == JsonFormat.Shape.ANY || shape == JsonFormat.Shape.SCALAR) {
/* 217 */       return defaultValue;
/*     */     }
/*     */     
/* 220 */     if (shape == JsonFormat.Shape.STRING || shape == JsonFormat.Shape.NATURAL) {
/* 221 */       return Boolean.FALSE;
/*     */     }
/*     */     
/* 224 */     if (shape.isNumeric() || shape == JsonFormat.Shape.ARRAY) {
/* 225 */       return Boolean.TRUE;
/*     */     }
/*     */     
/* 228 */     throw new IllegalArgumentException(String.format("Unsupported serialization shape (%s) for Enum %s, not supported as %s annotation", new Object[] { shape, enumClass.getName(), fromClass ? "class" : "property" }));
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\EnumSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */