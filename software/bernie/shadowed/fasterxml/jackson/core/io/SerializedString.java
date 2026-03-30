/*     */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerializedString
/*     */   implements SerializableString, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final String _value;
/*     */   protected byte[] _quotedUTF8Ref;
/*     */   protected byte[] _unquotedUTF8Ref;
/*     */   protected char[] _quotedChars;
/*     */   protected transient String _jdkSerializeValue;
/*     */   
/*     */   public SerializedString(String v) {
/*  50 */     if (v == null) {
/*  51 */       throw new IllegalStateException("Null String illegal for SerializedString");
/*     */     }
/*  53 */     this._value = v;
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
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/*  71 */     this._jdkSerializeValue = in.readUTF();
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/*  75 */     out.writeUTF(this._value);
/*     */   }
/*     */   
/*     */   protected Object readResolve() {
/*  79 */     return new SerializedString(this._jdkSerializeValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getValue() {
/*  89 */     return this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int charLength() {
/*  95 */     return this._value.length();
/*     */   }
/*     */   
/*     */   public final char[] asQuotedChars() {
/*  99 */     char[] result = this._quotedChars;
/* 100 */     if (result == null) {
/* 101 */       result = JsonStringEncoder.getInstance().quoteAsString(this._value);
/* 102 */       this._quotedChars = result;
/*     */     } 
/* 104 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] asUnquotedUTF8() {
/* 113 */     byte[] result = this._unquotedUTF8Ref;
/* 114 */     if (result == null) {
/* 115 */       result = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 116 */       this._unquotedUTF8Ref = result;
/*     */     } 
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] asQuotedUTF8() {
/* 127 */     byte[] result = this._quotedUTF8Ref;
/* 128 */     if (result == null) {
/* 129 */       result = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 130 */       this._quotedUTF8Ref = result;
/*     */     } 
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int appendQuotedUTF8(byte[] buffer, int offset) {
/* 143 */     byte[] result = this._quotedUTF8Ref;
/* 144 */     if (result == null) {
/* 145 */       result = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 146 */       this._quotedUTF8Ref = result;
/*     */     } 
/* 148 */     int length = result.length;
/* 149 */     if (offset + length > buffer.length) {
/* 150 */       return -1;
/*     */     }
/* 152 */     System.arraycopy(result, 0, buffer, offset, length);
/* 153 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendQuoted(char[] buffer, int offset) {
/* 158 */     char[] result = this._quotedChars;
/* 159 */     if (result == null) {
/* 160 */       result = JsonStringEncoder.getInstance().quoteAsString(this._value);
/* 161 */       this._quotedChars = result;
/*     */     } 
/* 163 */     int length = result.length;
/* 164 */     if (offset + length > buffer.length) {
/* 165 */       return -1;
/*     */     }
/* 167 */     System.arraycopy(result, 0, buffer, offset, length);
/* 168 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendUnquotedUTF8(byte[] buffer, int offset) {
/* 173 */     byte[] result = this._unquotedUTF8Ref;
/* 174 */     if (result == null) {
/* 175 */       result = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 176 */       this._unquotedUTF8Ref = result;
/*     */     } 
/* 178 */     int length = result.length;
/* 179 */     if (offset + length > buffer.length) {
/* 180 */       return -1;
/*     */     }
/* 182 */     System.arraycopy(result, 0, buffer, offset, length);
/* 183 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int appendUnquoted(char[] buffer, int offset) {
/* 188 */     String str = this._value;
/* 189 */     int length = str.length();
/* 190 */     if (offset + length > buffer.length) {
/* 191 */       return -1;
/*     */     }
/* 193 */     str.getChars(0, length, buffer, offset);
/* 194 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeQuotedUTF8(OutputStream out) throws IOException {
/* 199 */     byte[] result = this._quotedUTF8Ref;
/* 200 */     if (result == null) {
/* 201 */       result = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 202 */       this._quotedUTF8Ref = result;
/*     */     } 
/* 204 */     int length = result.length;
/* 205 */     out.write(result, 0, length);
/* 206 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeUnquotedUTF8(OutputStream out) throws IOException {
/* 211 */     byte[] result = this._unquotedUTF8Ref;
/* 212 */     if (result == null) {
/* 213 */       result = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 214 */       this._unquotedUTF8Ref = result;
/*     */     } 
/* 216 */     int length = result.length;
/* 217 */     out.write(result, 0, length);
/* 218 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int putQuotedUTF8(ByteBuffer buffer) {
/* 223 */     byte[] result = this._quotedUTF8Ref;
/* 224 */     if (result == null) {
/* 225 */       result = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 226 */       this._quotedUTF8Ref = result;
/*     */     } 
/* 228 */     int length = result.length;
/* 229 */     if (length > buffer.remaining()) {
/* 230 */       return -1;
/*     */     }
/* 232 */     buffer.put(result, 0, length);
/* 233 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int putUnquotedUTF8(ByteBuffer buffer) {
/* 238 */     byte[] result = this._unquotedUTF8Ref;
/* 239 */     if (result == null) {
/* 240 */       result = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 241 */       this._unquotedUTF8Ref = result;
/*     */     } 
/* 243 */     int length = result.length;
/* 244 */     if (length > buffer.remaining()) {
/* 245 */       return -1;
/*     */     }
/* 247 */     buffer.put(result, 0, length);
/* 248 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 259 */     return this._value;
/*     */   }
/*     */   public final int hashCode() {
/* 262 */     return this._value.hashCode();
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/* 266 */     if (o == this) return true; 
/* 267 */     if (o == null || o.getClass() != getClass()) return false; 
/* 268 */     SerializedString other = (SerializedString)o;
/* 269 */     return this._value.equals(other._value);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\SerializedString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */