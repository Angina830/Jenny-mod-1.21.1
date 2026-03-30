/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class IterableSerializer
/*     */   extends AsArraySerializerBase<Iterable<?>> {
/*     */   public IterableSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts) {
/*  19 */     super(Iterable.class, elemType, staticTyping, vts, (JsonSerializer)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IterableSerializer(IterableSerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSerializer, Boolean unwrapSingle) {
/*  25 */     super(src, property, vts, valueSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/*  30 */     return new IterableSerializer(this, this._property, vts, this._elementSerializer, this._unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterableSerializer withResolved(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
/*  37 */     return new IterableSerializer(this, property, vts, elementSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, Iterable<?> value) {
/*  43 */     return !value.iterator().hasNext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSingleElement(Iterable<?> value) {
/*  49 */     if (value != null) {
/*  50 */       Iterator<?> it = value.iterator();
/*  51 */       if (it.hasNext()) {
/*  52 */         it.next();
/*  53 */         if (!it.hasNext()) {
/*  54 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(Iterable<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*  65 */     if ((this._unwrapSingle == null && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)
/*     */     {
/*     */       
/*  68 */       if (hasSingleElement(value)) {
/*  69 */         serializeContents(value, gen, provider);
/*     */         return;
/*     */       } 
/*     */     }
/*  73 */     gen.writeStartArray();
/*  74 */     serializeContents(value, gen, provider);
/*  75 */     gen.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContents(Iterable<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
/*  82 */     Iterator<?> it = value.iterator();
/*  83 */     if (it.hasNext()) {
/*  84 */       TypeSerializer typeSer = this._valueTypeSerializer;
/*  85 */       JsonSerializer<Object> prevSerializer = null;
/*  86 */       Class<?> prevClass = null;
/*     */       
/*     */       do {
/*  89 */         Object elem = it.next();
/*  90 */         if (elem == null) {
/*  91 */           provider.defaultSerializeNull(jgen);
/*     */         } else {
/*     */           
/*  94 */           JsonSerializer<Object> currSerializer = this._elementSerializer;
/*  95 */           if (currSerializer == null) {
/*     */             
/*  97 */             Class<?> cc = elem.getClass();
/*  98 */             if (cc == prevClass) {
/*  99 */               currSerializer = prevSerializer;
/*     */             } else {
/* 101 */               currSerializer = provider.findValueSerializer(cc, this._property);
/* 102 */               prevSerializer = currSerializer;
/* 103 */               prevClass = cc;
/*     */             } 
/*     */           } 
/* 106 */           if (typeSer == null)
/* 107 */           { currSerializer.serialize(elem, jgen, provider); }
/*     */           else
/* 109 */           { currSerializer.serializeWithType(elem, jgen, provider, typeSer); } 
/*     */         } 
/* 111 */       } while (it.hasNext());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\IterableSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */