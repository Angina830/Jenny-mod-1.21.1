/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InetAddressSerializer
/*    */   extends StdScalarSerializer<InetAddress>
/*    */   implements ContextualSerializer
/*    */ {
/*    */   protected final boolean _asNumeric;
/*    */   
/*    */   public InetAddressSerializer() {
/* 36 */     this(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InetAddressSerializer(boolean asNumeric) {
/* 43 */     super(InetAddress.class);
/* 44 */     this._asNumeric = asNumeric;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonSerializer<?> createContextual(SerializerProvider serializers, BeanProperty property) throws JsonMappingException {
/* 51 */     JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
/*    */     
/* 53 */     boolean asNumeric = false;
/* 54 */     if (format != null) {
/* 55 */       JsonFormat.Shape shape = format.getShape();
/* 56 */       if (shape.isNumeric() || shape == JsonFormat.Shape.ARRAY) {
/* 57 */         asNumeric = true;
/*    */       }
/*    */     } 
/* 60 */     if (asNumeric != this._asNumeric) {
/* 61 */       return new InetAddressSerializer(asNumeric);
/*    */     }
/* 63 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(InetAddress value, JsonGenerator g, SerializerProvider provider) throws IOException {
/*    */     String str;
/* 71 */     if (this._asNumeric) {
/* 72 */       str = value.getHostAddress();
/*    */     } else {
/*    */       
/* 75 */       str = value.toString().trim();
/* 76 */       int ix = str.indexOf('/');
/* 77 */       if (ix >= 0) {
/* 78 */         if (ix == 0) {
/* 79 */           str = str.substring(1);
/*    */         } else {
/* 81 */           str = str.substring(0, ix);
/*    */         } 
/*    */       }
/*    */     } 
/* 85 */     g.writeString(str);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(InetAddress value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 93 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, InetAddress.class, JsonToken.VALUE_STRING));
/*    */     
/* 95 */     serialize(value, g, provider);
/* 96 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\InetAddressSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */