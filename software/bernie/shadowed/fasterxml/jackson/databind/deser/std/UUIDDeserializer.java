/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variants;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidFormatException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UUIDDeserializer
/*     */   extends FromStringDeserializer<UUID>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   static final int[] HEX_DIGITS = new int[127];
/*     */   static {
/*  19 */     Arrays.fill(HEX_DIGITS, -1); int i;
/*  20 */     for (i = 0; i < 10; ) { HEX_DIGITS[48 + i] = i; i++; }
/*  21 */      for (i = 0; i < 6; i++) {
/*  22 */       HEX_DIGITS[97 + i] = 10 + i;
/*  23 */       HEX_DIGITS[65 + i] = 10 + i;
/*     */     } 
/*     */   }
/*     */   public UUIDDeserializer() {
/*  27 */     super(UUID.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UUID _deserialize(String id, DeserializationContext ctxt) throws IOException {
/*  34 */     if (id.length() != 36) {
/*     */ 
/*     */ 
/*     */       
/*  38 */       if (id.length() == 24) {
/*  39 */         byte[] stuff = Base64Variants.getDefaultVariant().decode(id);
/*  40 */         return _fromBytes(stuff, ctxt);
/*     */       } 
/*  42 */       return _badFormat(id, ctxt);
/*     */     } 
/*     */ 
/*     */     
/*  46 */     if (id.charAt(8) != '-' || id.charAt(13) != '-' || id.charAt(18) != '-' || id.charAt(23) != '-')
/*     */     {
/*  48 */       _badFormat(id, ctxt);
/*     */     }
/*  50 */     long l1 = intFromChars(id, 0, ctxt);
/*  51 */     l1 <<= 32L;
/*  52 */     long l2 = shortFromChars(id, 9, ctxt) << 16L;
/*  53 */     l2 |= shortFromChars(id, 14, ctxt);
/*  54 */     long hi = l1 + l2;
/*     */     
/*  56 */     int i1 = shortFromChars(id, 19, ctxt) << 16 | shortFromChars(id, 24, ctxt);
/*  57 */     l1 = i1;
/*  58 */     l1 <<= 32L;
/*  59 */     l2 = intFromChars(id, 28, ctxt);
/*  60 */     l2 = l2 << 32L >>> 32L;
/*  61 */     long lo = l1 | l2;
/*     */     
/*  63 */     return new UUID(hi, lo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected UUID _deserializeEmbedded(Object ob, DeserializationContext ctxt) throws IOException {
/*  69 */     if (ob instanceof byte[]) {
/*  70 */       return _fromBytes((byte[])ob, ctxt);
/*     */     }
/*  72 */     super._deserializeEmbedded(ob, ctxt);
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private UUID _badFormat(String uuidStr, DeserializationContext ctxt) throws IOException {
/*  79 */     return (UUID)ctxt.handleWeirdStringValue(handledType(), uuidStr, "UUID has to be represented by standard 36-char representation", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   int intFromChars(String str, int index, DeserializationContext ctxt) throws JsonMappingException {
/*  84 */     return (byteFromChars(str, index, ctxt) << 24) + (byteFromChars(str, index + 2, ctxt) << 16) + (byteFromChars(str, index + 4, ctxt) << 8) + byteFromChars(str, index + 6, ctxt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int shortFromChars(String str, int index, DeserializationContext ctxt) throws JsonMappingException {
/*  91 */     return (byteFromChars(str, index, ctxt) << 8) + byteFromChars(str, index + 2, ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   int byteFromChars(String str, int index, DeserializationContext ctxt) throws JsonMappingException {
/*  96 */     char c1 = str.charAt(index);
/*  97 */     char c2 = str.charAt(index + 1);
/*     */     
/*  99 */     if (c1 <= '' && c2 <= '') {
/* 100 */       int hex = HEX_DIGITS[c1] << 4 | HEX_DIGITS[c2];
/* 101 */       if (hex >= 0) {
/* 102 */         return hex;
/*     */       }
/*     */     } 
/* 105 */     if (c1 > '' || HEX_DIGITS[c1] < 0) {
/* 106 */       return _badChar(str, index, ctxt, c1);
/*     */     }
/* 108 */     return _badChar(str, index + 1, ctxt, c2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int _badChar(String uuidStr, int index, DeserializationContext ctxt, char c) throws JsonMappingException {
/* 114 */     throw ctxt.weirdStringException(uuidStr, handledType(), String.format("Non-hex character '%c' (value 0x%s), not valid for UUID String", new Object[] { Character.valueOf(c), Integer.toHexString(c) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UUID _fromBytes(byte[] bytes, DeserializationContext ctxt) throws JsonMappingException {
/* 121 */     if (bytes.length != 16) {
/* 122 */       throw InvalidFormatException.from(ctxt.getParser(), "Can only construct UUIDs from byte[16]; got " + bytes.length + " bytes", bytes, handledType());
/*     */     }
/*     */ 
/*     */     
/* 126 */     return new UUID(_long(bytes, 0), _long(bytes, 8));
/*     */   }
/*     */   
/*     */   private static long _long(byte[] b, int offset) {
/* 130 */     long l1 = _int(b, offset) << 32L;
/* 131 */     long l2 = _int(b, offset + 4);
/*     */     
/* 133 */     l2 = l2 << 32L >>> 32L;
/* 134 */     return l1 | l2;
/*     */   }
/*     */   
/*     */   private static int _int(byte[] b, int offset) {
/* 138 */     return b[offset] << 24 | (b[offset + 1] & 0xFF) << 16 | (b[offset + 2] & 0xFF) << 8 | b[offset + 3] & 0xFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\UUIDDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */