/*     */ package software.bernie.shadowed.fasterxml.jackson.core.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
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
/*     */ 
/*     */ public class DataFormatDetector
/*     */ {
/*     */   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
/*     */   protected final JsonFactory[] _detectors;
/*     */   protected final MatchStrength _optimalMatch;
/*     */   protected final MatchStrength _minimalMatch;
/*     */   protected final int _maxInputLookahead;
/*     */   
/*     */   public DataFormatDetector(JsonFactory... detectors) {
/*  58 */     this(detectors, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataFormatDetector(Collection<JsonFactory> detectors) {
/*  63 */     this(detectors.<JsonFactory>toArray(new JsonFactory[detectors.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFormatDetector withOptimalMatch(MatchStrength optMatch) {
/*  72 */     if (optMatch == this._optimalMatch) {
/*  73 */       return this;
/*     */     }
/*  75 */     return new DataFormatDetector(this._detectors, optMatch, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFormatDetector withMinimalMatch(MatchStrength minMatch) {
/*  83 */     if (minMatch == this._minimalMatch) {
/*  84 */       return this;
/*     */     }
/*  86 */     return new DataFormatDetector(this._detectors, this._optimalMatch, minMatch, this._maxInputLookahead);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFormatDetector withMaxInputLookahead(int lookaheadBytes) {
/*  94 */     if (lookaheadBytes == this._maxInputLookahead) {
/*  95 */       return this;
/*     */     }
/*  97 */     return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, lookaheadBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   private DataFormatDetector(JsonFactory[] detectors, MatchStrength optMatch, MatchStrength minMatch, int maxInputLookahead) {
/* 102 */     this._detectors = detectors;
/* 103 */     this._optimalMatch = optMatch;
/* 104 */     this._minimalMatch = minMatch;
/* 105 */     this._maxInputLookahead = maxInputLookahead;
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
/*     */   public DataFormatMatcher findFormat(InputStream in) throws IOException {
/* 123 */     return _findFormat(new InputAccessor.Std(in, new byte[this._maxInputLookahead]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFormatMatcher findFormat(byte[] fullInputData) throws IOException {
/* 134 */     return _findFormat(new InputAccessor.Std(fullInputData));
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
/*     */   public DataFormatMatcher findFormat(byte[] fullInputData, int offset, int len) throws IOException {
/* 147 */     return _findFormat(new InputAccessor.Std(fullInputData, offset, len));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 157 */     StringBuilder sb = new StringBuilder();
/* 158 */     sb.append('[');
/* 159 */     int len = this._detectors.length;
/* 160 */     if (len > 0) {
/* 161 */       sb.append(this._detectors[0].getFormatName());
/* 162 */       for (int i = 1; i < len; i++) {
/* 163 */         sb.append(", ");
/* 164 */         sb.append(this._detectors[i].getFormatName());
/*     */       } 
/*     */     } 
/* 167 */     sb.append(']');
/* 168 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DataFormatMatcher _findFormat(InputAccessor.Std acc) throws IOException {
/* 178 */     JsonFactory bestMatch = null;
/* 179 */     MatchStrength bestMatchStrength = null;
/* 180 */     for (JsonFactory f : this._detectors) {
/* 181 */       acc.reset();
/* 182 */       MatchStrength strength = f.hasFormat(acc);
/*     */       
/* 184 */       if (strength != null && strength.ordinal() >= this._minimalMatch.ordinal())
/*     */       {
/*     */ 
/*     */         
/* 188 */         if (bestMatch == null || 
/* 189 */           bestMatchStrength.ordinal() < strength.ordinal()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 194 */           bestMatch = f;
/* 195 */           bestMatchStrength = strength;
/* 196 */           if (strength.ordinal() >= this._optimalMatch.ordinal())
/*     */             break; 
/*     */         }  } 
/*     */     } 
/* 200 */     return acc.createMatcher(bestMatch, bestMatchStrength);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\format\DataFormatDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */