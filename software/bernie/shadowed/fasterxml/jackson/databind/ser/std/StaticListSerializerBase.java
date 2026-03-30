/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StaticListSerializerBase<T extends Collection<?>>
/*     */   extends StdSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final Boolean _unwrapSingle;
/*     */   
/*     */   protected StaticListSerializerBase(Class<?> cls) {
/*  35 */     super(cls, false);
/*  36 */     this._unwrapSingle = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StaticListSerializerBase(StaticListSerializerBase<?> src, Boolean unwrapSingle) {
/*  44 */     super(src);
/*  45 */     this._unwrapSingle = unwrapSingle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/*  66 */     JsonSerializer<?> ser = null;
/*  67 */     Boolean unwrapSingle = null;
/*     */     
/*  69 */     if (property != null) {
/*  70 */       AnnotationIntrospector intr = serializers.getAnnotationIntrospector();
/*  71 */       AnnotatedMember m = property.getMember();
/*  72 */       if (m != null) {
/*  73 */         Object serDef = intr.findContentSerializer((Annotated)m);
/*  74 */         if (serDef != null) {
/*  75 */           ser = serializers.serializerInstance((Annotated)m, serDef);
/*     */         }
/*     */       } 
/*     */     } 
/*  79 */     JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/*  80 */     if (format != null) {
/*  81 */       unwrapSingle = format.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
/*     */     }
/*     */     
/*  84 */     ser = findContextualConvertingSerializer(serializers, property, ser);
/*  85 */     if (ser == null) {
/*  86 */       ser = serializers.findValueSerializer(String.class, property);
/*     */     }
/*     */     
/*  89 */     if (isDefaultSerializer(ser)) {
/*  90 */       if (unwrapSingle == this._unwrapSingle) {
/*  91 */         return this;
/*     */       }
/*  93 */       return _withResolved(property, unwrapSingle);
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return (JsonSerializer<?>)new CollectionSerializer(serializers.constructType(String.class), true, null, ser);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider provider, T value) {
/* 103 */     return (value == null || value.size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
/* 108 */     return createSchemaNode("array", true).set("items", contentSchema());
/*     */   }
/*     */ 
/*     */   
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
/* 113 */     acceptContentVisitor(visitor.expectArrayFormat(typeHint));
/*     */   }
/*     */   
/*     */   public abstract JsonSerializer<?> _withResolved(BeanProperty paramBeanProperty, Boolean paramBoolean);
/*     */   
/*     */   protected abstract JsonNode contentSchema();
/*     */   
/*     */   protected abstract void acceptContentVisitor(JsonArrayFormatVisitor paramJsonArrayFormatVisitor) throws JsonMappingException;
/*     */   
/*     */   public abstract void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer) throws IOException;
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\StaticListSerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */