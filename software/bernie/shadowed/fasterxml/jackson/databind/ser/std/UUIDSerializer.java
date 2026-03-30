/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
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
/*     */ public class UUIDSerializer
/*     */   extends StdScalarSerializer<UUID>
/*     */ {
/*  21 */   static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
/*     */   public UUIDSerializer() {
/*  23 */     super(UUID.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty(SerializerProvider prov, UUID value) {
/*  29 */     if (value.getLeastSignificantBits() == 0L && value.getMostSignificantBits() == 0L)
/*     */     {
/*  31 */       return true;
/*     */     }
/*  33 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(UUID value, JsonGenerator gen, SerializerProvider provider) throws IOException {
/*  41 */     if (gen.canWriteBinaryNatively())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  47 */       if (!(gen instanceof software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer)) {
/*  48 */         gen.writeBinary(_asBytes(value));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  56 */     char[] ch = new char[36];
/*  57 */     long msb = value.getMostSignificantBits();
/*  58 */     _appendInt((int)(msb >> 32L), ch, 0);
/*  59 */     ch[8] = '-';
/*  60 */     int i = (int)msb;
/*  61 */     _appendShort(i >>> 16, ch, 9);
/*  62 */     ch[13] = '-';
/*  63 */     _appendShort(i, ch, 14);
/*  64 */     ch[18] = '-';
/*     */     
/*  66 */     long lsb = value.getLeastSignificantBits();
/*  67 */     _appendShort((int)(lsb >>> 48L), ch, 19);
/*  68 */     ch[23] = '-';
/*  69 */     _appendShort((int)(lsb >>> 32L), ch, 24);
/*  70 */     _appendInt((int)lsb, ch, 28);
/*     */     
/*  72 */     gen.writeString(ch, 0, 36);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void _appendInt(int bits, char[] ch, int offset) {
/*  77 */     _appendShort(bits >> 16, ch, offset);
/*  78 */     _appendShort(bits, ch, offset + 4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void _appendShort(int bits, char[] ch, int offset) {
/*  83 */     ch[offset] = HEX_CHARS[bits >> 12 & 0xF];
/*  84 */     ch[++offset] = HEX_CHARS[bits >> 8 & 0xF];
/*  85 */     ch[++offset] = HEX_CHARS[bits >> 4 & 0xF];
/*  86 */     ch[++offset] = HEX_CHARS[bits & 0xF];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte[] _asBytes(UUID uuid) {
/*  92 */     byte[] buffer = new byte[16];
/*  93 */     long hi = uuid.getMostSignificantBits();
/*  94 */     long lo = uuid.getLeastSignificantBits();
/*  95 */     _appendInt((int)(hi >> 32L), buffer, 0);
/*  96 */     _appendInt((int)hi, buffer, 4);
/*  97 */     _appendInt((int)(lo >> 32L), buffer, 8);
/*  98 */     _appendInt((int)lo, buffer, 12);
/*  99 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void _appendInt(int value, byte[] buffer, int offset) {
/* 104 */     buffer[offset] = (byte)(value >> 24);
/* 105 */     buffer[++offset] = (byte)(value >> 16);
/* 106 */     buffer[++offset] = (byte)(value >> 8);
/* 107 */     buffer[++offset] = (byte)value;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\std\UUIDSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */