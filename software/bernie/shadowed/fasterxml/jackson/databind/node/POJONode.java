/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.RawValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POJONode
/*     */   extends ValueNode
/*     */ {
/*     */   protected final Object _value;
/*     */   
/*     */   public POJONode(Object v) {
/*  20 */     this._value = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonNodeType getNodeType() {
/*  30 */     return JsonNodeType.POJO;
/*     */   }
/*     */   public JsonToken asToken() {
/*  33 */     return JsonToken.VALUE_EMBEDDED_OBJECT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] binaryValue() throws IOException {
/*  43 */     if (this._value instanceof byte[]) {
/*  44 */       return (byte[])this._value;
/*     */     }
/*  46 */     return super.binaryValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String asText() {
/*  56 */     return (this._value == null) ? "null" : this._value.toString();
/*     */   }
/*     */   public String asText(String defaultValue) {
/*  59 */     return (this._value == null) ? defaultValue : this._value.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean asBoolean(boolean defaultValue) {
/*  65 */     if (this._value != null && this._value instanceof Boolean) {
/*  66 */       return ((Boolean)this._value).booleanValue();
/*     */     }
/*  68 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int asInt(int defaultValue) {
/*  74 */     if (this._value instanceof Number) {
/*  75 */       return ((Number)this._value).intValue();
/*     */     }
/*  77 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long asLong(long defaultValue) {
/*  83 */     if (this._value instanceof Number) {
/*  84 */       return ((Number)this._value).longValue();
/*     */     }
/*  86 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double asDouble(double defaultValue) {
/*  92 */     if (this._value instanceof Number) {
/*  93 */       return ((Number)this._value).doubleValue();
/*     */     }
/*  95 */     return defaultValue;
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
/*     */   public final void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
/* 107 */     if (this._value == null) {
/* 108 */       serializers.defaultSerializeNull(gen);
/* 109 */     } else if (this._value instanceof JsonSerializable) {
/* 110 */       ((JsonSerializable)this._value).serialize(gen, serializers);
/*     */     } else {
/* 112 */       gen.writeObject(this._value);
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
/*     */   public Object getPojo() {
/* 125 */     return this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 136 */     if (o == this) return true; 
/* 137 */     if (o == null) return false; 
/* 138 */     if (o instanceof POJONode) {
/* 139 */       return _pojoEquals((POJONode)o);
/*     */     }
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _pojoEquals(POJONode other) {
/* 149 */     if (this._value == null) {
/* 150 */       return (other._value == null);
/*     */     }
/* 152 */     return this._value.equals(other._value);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 156 */     return this._value.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     if (this._value instanceof byte[]) {
/* 163 */       return String.format("(binary value of %d bytes)", new Object[] { Integer.valueOf(((byte[])this._value).length) });
/*     */     }
/* 165 */     if (this._value instanceof RawValue) {
/* 166 */       return String.format("(raw value '%s')", new Object[] { ((RawValue)this._value).toString() });
/*     */     }
/* 168 */     return String.valueOf(this._value);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\node\POJONode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */