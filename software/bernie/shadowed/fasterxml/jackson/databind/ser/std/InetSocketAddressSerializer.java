/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.type.WritableTypeId;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InetSocketAddressSerializer
/*    */   extends StdScalarSerializer<InetSocketAddress>
/*    */ {
/*    */   public InetSocketAddressSerializer() {
/* 19 */     super(InetSocketAddress.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(InetSocketAddress value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
/* 24 */     InetAddress addr = value.getAddress();
/* 25 */     String str = (addr == null) ? value.getHostName() : addr.toString().trim();
/* 26 */     int ix = str.indexOf('/');
/* 27 */     if (ix >= 0) {
/* 28 */       if (ix == 0) {
/* 29 */         str = (addr instanceof java.net.Inet6Address) ? ("[" + str.substring(1) + "]") : str.substring(1);
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 34 */         str = str.substring(0, ix);
/*    */       } 
/*    */     }
/*    */     
/* 38 */     jgen.writeString(str + ":" + value.getPort());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeWithType(InetSocketAddress value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
/* 46 */     WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, InetSocketAddress.class, JsonToken.VALUE_STRING));
/*    */     
/* 48 */     serialize(value, g, provider);
/* 49 */     typeSer.writeTypeSuffix(g, typeIdDef);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\InetSocketAddressSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */