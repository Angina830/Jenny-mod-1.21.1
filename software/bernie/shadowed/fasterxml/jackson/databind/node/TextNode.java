/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variant;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Base64Variants;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.NumberInput;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.util.ByteArrayBuilder;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidFormatException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextNode
/*     */   extends ValueNode
/*     */ {
/*  19 */   static final TextNode EMPTY_STRING_NODE = new TextNode("");
/*     */   protected final String _value;
/*     */   
/*     */   public TextNode(String v) {
/*  23 */     this._value = v;
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
/*     */   public static TextNode valueOf(String v) {
/*  36 */     if (v == null) {
/*  37 */       return null;
/*     */     }
/*  39 */     if (v.length() == 0) {
/*  40 */       return EMPTY_STRING_NODE;
/*     */     }
/*  42 */     return new TextNode(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonNodeType getNodeType() {
/*  47 */     return JsonNodeType.STRING;
/*     */   }
/*     */   public JsonToken asToken() {
/*  50 */     return JsonToken.VALUE_STRING;
/*     */   }
/*     */   
/*     */   public String textValue() {
/*  54 */     return this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
/*  65 */     String str = this._value.trim();
/*  66 */     ByteArrayBuilder builder = new ByteArrayBuilder(4 + (str.length() * 3 << 2));
/*     */     try {
/*  68 */       b64variant.decode(str, builder);
/*  69 */     } catch (IllegalArgumentException e) {
/*  70 */       throw InvalidFormatException.from(null, String.format("Cannot access contents of TextNode as binary due to broken Base64 encoding: %s", new Object[] { e.getMessage() }), str, byte[].class);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     return builder.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] binaryValue() throws IOException {
/*  81 */     return getBinaryValue(Base64Variants.getDefaultVariant());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String asText() {
/*  92 */     return this._value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String asText(String defaultValue) {
/*  97 */     return (this._value == null) ? defaultValue : this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean asBoolean(boolean defaultValue) {
/* 104 */     if (this._value != null) {
/* 105 */       String v = this._value.trim();
/* 106 */       if ("true".equals(v)) {
/* 107 */         return true;
/*     */       }
/* 109 */       if ("false".equals(v)) {
/* 110 */         return false;
/*     */       }
/*     */     } 
/* 113 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int asInt(int defaultValue) {
/* 118 */     return NumberInput.parseAsInt(this._value, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public long asLong(long defaultValue) {
/* 123 */     return NumberInput.parseAsLong(this._value, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public double asDouble(double defaultValue) {
/* 128 */     return NumberInput.parseAsDouble(this._value, defaultValue);
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
/*     */   public final void serialize(JsonGenerator g, SerializerProvider provider) throws IOException {
/* 140 */     if (this._value == null) {
/* 141 */       g.writeNull();
/*     */     } else {
/* 143 */       g.writeString(this._value);
/*     */     } 
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
/*     */   public boolean equals(Object o) {
/* 156 */     if (o == this) return true; 
/* 157 */     if (o == null) return false; 
/* 158 */     if (o instanceof TextNode) {
/* 159 */       return ((TextNode)o)._value.equals(this._value);
/*     */     }
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 165 */     return this._value.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 173 */     int len = this._value.length();
/* 174 */     len = len + 2 + (len >> 4);
/* 175 */     StringBuilder sb = new StringBuilder(len);
/* 176 */     appendQuoted(sb, this._value);
/* 177 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void appendQuoted(StringBuilder sb, String content) {
/* 182 */     sb.append('"');
/* 183 */     CharTypes.appendQuoted(sb, content);
/* 184 */     sb.append('"');
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\TextNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */