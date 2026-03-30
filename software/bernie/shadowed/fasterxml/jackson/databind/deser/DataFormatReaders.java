/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.format.InputAccessor;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.format.MatchStrength;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.MergedStream;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ObjectReader;
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
/*     */ public class DataFormatReaders
/*     */ {
/*     */   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
/*     */   protected final ObjectReader[] _readers;
/*     */   protected final MatchStrength _optimalMatch;
/*     */   protected final MatchStrength _minimalMatch;
/*     */   protected final int _maxInputLookahead;
/*     */   
/*     */   public DataFormatReaders(ObjectReader... detectors) {
/*  65 */     this(detectors, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataFormatReaders(Collection<ObjectReader> detectors) {
/*  70 */     this(detectors.<ObjectReader>toArray(new ObjectReader[detectors.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DataFormatReaders(ObjectReader[] detectors, MatchStrength optMatch, MatchStrength minMatch, int maxInputLookahead) {
/*  77 */     this._readers = detectors;
/*  78 */     this._optimalMatch = optMatch;
/*  79 */     this._minimalMatch = minMatch;
/*  80 */     this._maxInputLookahead = maxInputLookahead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFormatReaders withOptimalMatch(MatchStrength optMatch) {
/*  90 */     if (optMatch == this._optimalMatch) {
/*  91 */       return this;
/*     */     }
/*  93 */     return new DataFormatReaders(this._readers, optMatch, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */   public DataFormatReaders withMinimalMatch(MatchStrength minMatch) {
/*  97 */     if (minMatch == this._minimalMatch) {
/*  98 */       return this;
/*     */     }
/* 100 */     return new DataFormatReaders(this._readers, this._optimalMatch, minMatch, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */   public DataFormatReaders with(ObjectReader[] readers) {
/* 104 */     return new DataFormatReaders(readers, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataFormatReaders withMaxInputLookahead(int lookaheadBytes) {
/* 109 */     if (lookaheadBytes == this._maxInputLookahead) {
/* 110 */       return this;
/*     */     }
/* 112 */     return new DataFormatReaders(this._readers, this._optimalMatch, this._minimalMatch, lookaheadBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFormatReaders with(DeserializationConfig config) {
/* 123 */     int len = this._readers.length;
/* 124 */     ObjectReader[] r = new ObjectReader[len];
/* 125 */     for (int i = 0; i < len; i++) {
/* 126 */       r[i] = this._readers[i].with(config);
/*     */     }
/* 128 */     return new DataFormatReaders(r, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataFormatReaders withType(JavaType type) {
/* 133 */     int len = this._readers.length;
/* 134 */     ObjectReader[] r = new ObjectReader[len];
/* 135 */     for (int i = 0; i < len; i++) {
/* 136 */       r[i] = this._readers[i].forType(type);
/*     */     }
/* 138 */     return new DataFormatReaders(r, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
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
/*     */   
/*     */   public Match findFormat(InputStream in) throws IOException {
/* 157 */     return _findFormat(new AccessorForReader(in, new byte[this._maxInputLookahead]));
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
/*     */   public Match findFormat(byte[] fullInputData) throws IOException {
/* 169 */     return _findFormat(new AccessorForReader(fullInputData));
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
/*     */   public Match findFormat(byte[] fullInputData, int offset, int len) throws IOException {
/* 183 */     return _findFormat(new AccessorForReader(fullInputData, offset, len));
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
/*     */   public String toString() {
/* 195 */     StringBuilder sb = new StringBuilder();
/* 196 */     sb.append('[');
/* 197 */     int len = this._readers.length;
/* 198 */     if (len > 0) {
/* 199 */       sb.append(this._readers[0].getFactory().getFormatName());
/* 200 */       for (int i = 1; i < len; i++) {
/* 201 */         sb.append(", ");
/* 202 */         sb.append(this._readers[i].getFactory().getFormatName());
/*     */       } 
/*     */     } 
/* 205 */     sb.append(']');
/* 206 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Match _findFormat(AccessorForReader acc) throws IOException {
/* 217 */     ObjectReader bestMatch = null;
/* 218 */     MatchStrength bestMatchStrength = null;
/* 219 */     for (ObjectReader f : this._readers) {
/* 220 */       acc.reset();
/* 221 */       MatchStrength strength = f.getFactory().hasFormat((InputAccessor)acc);
/*     */       
/* 223 */       if (strength != null && strength.ordinal() >= this._minimalMatch.ordinal())
/*     */       {
/*     */ 
/*     */         
/* 227 */         if (bestMatch == null || 
/* 228 */           bestMatchStrength.ordinal() < strength.ordinal()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 233 */           bestMatch = f;
/* 234 */           bestMatchStrength = strength;
/* 235 */           if (strength.ordinal() >= this._optimalMatch.ordinal())
/*     */             break; 
/*     */         }  } 
/*     */     } 
/* 239 */     return acc.createMatcher(bestMatch, bestMatchStrength);
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
/*     */   protected class AccessorForReader
/*     */     extends InputAccessor.Std
/*     */   {
/*     */     public AccessorForReader(InputStream in, byte[] buffer) {
/* 254 */       super(in, buffer);
/*     */     }
/*     */     public AccessorForReader(byte[] inputDocument) {
/* 257 */       super(inputDocument);
/*     */     }
/*     */     public AccessorForReader(byte[] inputDocument, int start, int len) {
/* 260 */       super(inputDocument, start, len);
/*     */     }
/*     */ 
/*     */     
/*     */     public DataFormatReaders.Match createMatcher(ObjectReader match, MatchStrength matchStrength) {
/* 265 */       return new DataFormatReaders.Match(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, match, matchStrength);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Match
/*     */   {
/*     */     protected final InputStream _originalStream;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final byte[] _bufferedData;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final int _bufferedStart;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final int _bufferedLength;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final ObjectReader _match;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final MatchStrength _matchStrength;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Match(InputStream in, byte[] buffered, int bufferedStart, int bufferedLength, ObjectReader match, MatchStrength strength) {
/* 306 */       this._originalStream = in;
/* 307 */       this._bufferedData = buffered;
/* 308 */       this._bufferedStart = bufferedStart;
/* 309 */       this._bufferedLength = bufferedLength;
/* 310 */       this._match = match;
/* 311 */       this._matchStrength = strength;
/*     */     }
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
/*     */     public boolean hasMatch() {
/* 324 */       return (this._match != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MatchStrength getMatchStrength() {
/* 331 */       return (this._matchStrength == null) ? MatchStrength.INCONCLUSIVE : this._matchStrength;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectReader getReader() {
/* 337 */       return this._match;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getMatchedFormatName() {
/* 347 */       return this._match.getFactory().getFormatName();
/*     */     }
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
/*     */     public JsonParser createParserWithMatch() throws IOException {
/* 363 */       if (this._match == null) {
/* 364 */         return null;
/*     */       }
/* 366 */       JsonFactory jf = this._match.getFactory();
/* 367 */       if (this._originalStream == null) {
/* 368 */         return jf.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */       }
/* 370 */       return jf.createParser(getDataStream());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getDataStream() {
/* 381 */       if (this._originalStream == null) {
/* 382 */         return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */       }
/* 384 */       return (InputStream)new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\DataFormatReaders.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */