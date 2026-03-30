/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class IteratorSerializer
/*     */   extends AsArraySerializerBase<Iterator<?>> {
/*     */   public IteratorSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts) {
/*  19 */     super(Iterator.class, elemType, staticTyping, vts, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IteratorSerializer(IteratorSerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSerializer, Boolean unwrapSingle) {
/*  25 */     super(src, property, vts, valueSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, Iterator<?> value) {
/*  30 */     return !value.hasNext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSingleElement(Iterator<?> value) {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/*  41 */     return (ContainerSerializer<?>)new IteratorSerializer(this, this._property, vts, this._elementSerializer, this._unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IteratorSerializer withResolved(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
/*  48 */     return new IteratorSerializer(this, property, vts, elementSerializer, unwrapSingle);
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
/*     */   public final void serialize(Iterator<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*  66 */     gen.writeStartArray();
/*  67 */     serializeContents(value, gen, provider);
/*  68 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContents(Iterator<?> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  75 */     if (!value.hasNext()) {
/*     */       return;
/*     */     }
/*  78 */     JsonSerializer<Object> serializer = this._elementSerializer;
/*  79 */     if (serializer == null) {
/*  80 */       _serializeDynamicContents(value, g, provider);
/*     */       return;
/*     */     } 
/*  83 */     TypeSerializer typeSer = this._valueTypeSerializer;
/*     */     do {
/*  85 */       Object elem = value.next();
/*  86 */       if (elem == null) {
/*  87 */         provider.defaultSerializeNull(g);
/*  88 */       } else if (typeSer == null) {
/*  89 */         serializer.serialize(elem, g, provider);
/*     */       } else {
/*  91 */         serializer.serializeWithType(elem, g, provider, typeSer);
/*     */       } 
/*  93 */     } while (value.hasNext());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _serializeDynamicContents(Iterator<?> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  99 */     JsonSerializer<Object> serializer = this._elementSerializer;
/* 100 */     TypeSerializer typeSer = this._valueTypeSerializer;
/* 101 */     PropertySerializerMap serializers = this._dynamicSerializers;
/*     */     do {
/* 103 */       Object elem = value.next();
/* 104 */       if (elem == null) {
/* 105 */         provider.defaultSerializeNull(g);
/*     */       } else {
/*     */         
/* 108 */         Class<?> cc = elem.getClass();
/* 109 */         serializers.serializerFor(cc);
/* 110 */         if (serializer == null) {
/* 111 */           if (this._elementType.hasGenericTypes()) {
/* 112 */             serializer = _findAndAddDynamic(serializers, provider.constructSpecializedType(this._elementType, cc), provider);
/*     */           } else {
/*     */             
/* 115 */             serializer = _findAndAddDynamic(serializers, cc, provider);
/*     */           } 
/* 117 */           serializers = this._dynamicSerializers;
/*     */         } 
/* 119 */         if (typeSer == null)
/* 120 */         { serializer.serialize(elem, g, provider); }
/*     */         else
/* 122 */         { serializer.serializeWithType(elem, g, provider, typeSer); } 
/*     */       } 
/* 124 */     } while (value.hasNext());
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\impl\IteratorSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */