/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
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
/*     */ public class CollectionSerializer
/*     */   extends AsArraySerializerBase<Collection<?>>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public CollectionSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> valueSerializer) {
/*  41 */     super(Collection.class, elemType, staticTyping, vts, valueSerializer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public CollectionSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> valueSerializer) {
/*  51 */     this(elemType, staticTyping, vts, valueSerializer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionSerializer(CollectionSerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSerializer, Boolean unwrapSingle) {
/*  57 */     super(src, property, vts, valueSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
/*  62 */     return new CollectionSerializer(this, this._property, vts, this._elementSerializer, this._unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionSerializer withResolved(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
/*  69 */     return new CollectionSerializer(this, property, vts, elementSerializer, unwrapSingle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, Collection<?> value) {
/*  80 */     return value.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSingleElement(Collection<?> value) {
/*  85 */     return (value.size() == 1);
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
/*     */   public final void serialize(Collection<?> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*  97 */     int len = value.size();
/*  98 */     if (len == 1 && ((
/*  99 */       this._unwrapSingle == null && provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
/*     */ 
/*     */       
/* 102 */       serializeContents(value, g, provider);
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     g.writeStartArray(len);
/* 107 */     serializeContents(value, g, provider);
/* 108 */     g.writeEndArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContents(Collection<?> value, JsonGenerator g, SerializerProvider provider) throws IOException {
/* 114 */     g.setCurrentValue(value);
/* 115 */     if (this._elementSerializer != null) {
/* 116 */       serializeContentsUsing(value, g, provider, this._elementSerializer);
/*     */       return;
/*     */     } 
/* 119 */     Iterator<?> it = value.iterator();
/* 120 */     if (!it.hasNext()) {
/*     */       return;
/*     */     }
/* 123 */     PropertySerializerMap serializers = this._dynamicSerializers;
/* 124 */     TypeSerializer typeSer = this._valueTypeSerializer;
/*     */     
/* 126 */     int i = 0;
/*     */     try {
/*     */       do {
/* 129 */         Object elem = it.next();
/* 130 */         if (elem == null) {
/* 131 */           provider.defaultSerializeNull(g);
/*     */         } else {
/* 133 */           Class<?> cc = elem.getClass();
/* 134 */           JsonSerializer<Object> serializer = serializers.serializerFor(cc);
/* 135 */           if (serializer == null) {
/* 136 */             if (this._elementType.hasGenericTypes()) {
/* 137 */               serializer = _findAndAddDynamic(serializers, provider.constructSpecializedType(this._elementType, cc), provider);
/*     */             } else {
/*     */               
/* 140 */               serializer = _findAndAddDynamic(serializers, cc, provider);
/*     */             } 
/* 142 */             serializers = this._dynamicSerializers;
/*     */           } 
/* 144 */           if (typeSer == null) {
/* 145 */             serializer.serialize(elem, g, provider);
/*     */           } else {
/* 147 */             serializer.serializeWithType(elem, g, provider, typeSer);
/*     */           } 
/*     */         } 
/* 150 */         i++;
/* 151 */       } while (it.hasNext());
/* 152 */     } catch (Exception e) {
/* 153 */       wrapAndThrow(provider, e, value, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeContentsUsing(Collection<?> value, JsonGenerator g, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
/* 160 */     Iterator<?> it = value.iterator();
/* 161 */     if (it.hasNext()) {
/* 162 */       TypeSerializer typeSer = this._valueTypeSerializer;
/* 163 */       int i = 0;
/*     */       do {
/* 165 */         Object elem = it.next();
/*     */         try {
/* 167 */           if (elem == null) {
/* 168 */             provider.defaultSerializeNull(g);
/*     */           }
/* 170 */           else if (typeSer == null) {
/* 171 */             ser.serialize(elem, g, provider);
/*     */           } else {
/* 173 */             ser.serializeWithType(elem, g, provider, typeSer);
/*     */           } 
/*     */           
/* 176 */           i++;
/* 177 */         } catch (Exception e) {
/* 178 */           wrapAndThrow(provider, e, value, i);
/*     */         } 
/* 180 */       } while (it.hasNext());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\CollectionSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */