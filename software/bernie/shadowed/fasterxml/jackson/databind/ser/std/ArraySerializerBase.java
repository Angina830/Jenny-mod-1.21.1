/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
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
/*     */ public abstract class ArraySerializerBase<T>
/*     */   extends ContainerSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final BeanProperty _property;
/*     */   protected final Boolean _unwrapSingle;
/*     */   
/*     */   protected ArraySerializerBase(Class<T> cls) {
/*  36 */     super(cls);
/*  37 */     this._property = null;
/*  38 */     this._unwrapSingle = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected ArraySerializerBase(Class<T> cls, BeanProperty property) {
/*  50 */     super(cls);
/*  51 */     this._property = property;
/*  52 */     this._unwrapSingle = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ArraySerializerBase(ArraySerializerBase<?> src) {
/*  57 */     super(src._handledType, false);
/*  58 */     this._property = src._property;
/*  59 */     this._unwrapSingle = src._unwrapSingle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArraySerializerBase(ArraySerializerBase<?> src, BeanProperty property, Boolean unwrapSingle) {
/*  68 */     super(src._handledType, false);
/*  69 */     this._property = property;
/*  70 */     this._unwrapSingle = unwrapSingle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected ArraySerializerBase(ArraySerializerBase<?> src, BeanProperty property) {
/*  79 */     super(src._handledType, false);
/*  80 */     this._property = property;
/*  81 */     this._unwrapSingle = src._unwrapSingle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract JsonSerializer<?> _withResolved(BeanProperty paramBeanProperty, Boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/*  94 */     Boolean unwrapSingle = null;
/*     */ 
/*     */     
/*  97 */     if (property != null) {
/*  98 */       JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/*  99 */       if (format != null) {
/* 100 */         unwrapSingle = format.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
/* 101 */         if (unwrapSingle != this._unwrapSingle) {
/* 102 */           return _withResolved(property, unwrapSingle);
/*     */         }
/*     */       } 
/*     */     } 
/* 106 */     return (JsonSerializer<?>)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/* 115 */     if (_shouldUnwrapSingle(provider) && 
/* 116 */       hasSingleElement(value)) {
/* 117 */       serializeContents(value, gen, provider);
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     gen.setCurrentValue(value);
/* 122 */     gen.writeStartArray();
/*     */     
/* 124 */     serializeContents(value, gen, provider);
/* 125 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serializeWithType(T value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 134 */     g.setCurrentValue(value);
/* 135 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
/*     */     
/* 137 */     serializeContents(value, g, provider);
/* 138 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void serializeContents(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean _shouldUnwrapSingle(SerializerProvider provider) {
/* 148 */     if (this._unwrapSingle == null) {
/* 149 */       return provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
/*     */     }
/* 151 */     return this._unwrapSingle.booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\ArraySerializerBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */