/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variants;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinaryNode
/*     */   extends ValueNode
/*     */ {
/*  17 */   static final BinaryNode EMPTY_BINARY_NODE = new BinaryNode(new byte[0]);
/*     */   
/*     */   protected final byte[] _data;
/*     */ 
/*     */   
/*     */   public BinaryNode(byte[] data) {
/*  23 */     this._data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public BinaryNode(byte[] data, int offset, int length) {
/*  28 */     if (offset == 0 && length == data.length) {
/*  29 */       this._data = data;
/*     */     } else {
/*  31 */       this._data = new byte[length];
/*  32 */       System.arraycopy(data, offset, this._data, 0, length);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BinaryNode valueOf(byte[] data) {
/*  38 */     if (data == null) {
/*  39 */       return null;
/*     */     }
/*  41 */     if (data.length == 0) {
/*  42 */       return EMPTY_BINARY_NODE;
/*     */     }
/*  44 */     return new BinaryNode(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BinaryNode valueOf(byte[] data, int offset, int length) {
/*  49 */     if (data == null) {
/*  50 */       return null;
/*     */     }
/*  52 */     if (length == 0) {
/*  53 */       return EMPTY_BINARY_NODE;
/*     */     }
/*  55 */     return new BinaryNode(data, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNodeType getNodeType() {
/*  61 */     return JsonNodeType.BINARY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken asToken() {
/*  70 */     return JsonToken.VALUE_EMBEDDED_OBJECT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] binaryValue() {
/*  79 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String asText() {
/*  87 */     return Base64Variants.getDefaultVariant().encode(this._data, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException, JsonProcessingException {
/*  94 */     jg.writeBinary(provider.getConfig().getBase64Variant(), this._data, 0, this._data.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 101 */     if (o == this) return true; 
/* 102 */     if (o == null) return false; 
/* 103 */     if (!(o instanceof BinaryNode)) {
/* 104 */       return false;
/*     */     }
/* 106 */     return Arrays.equals(((BinaryNode)o)._data, this._data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return (this._data == null) ? -1 : this._data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return Base64Variants.getDefaultVariant().encode(this._data, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\BinaryNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */