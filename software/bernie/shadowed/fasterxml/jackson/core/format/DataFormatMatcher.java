/*     */ package software.bernie.shadowed.fasterxml.jackson.core.format;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonFactory;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.MergedStream;
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
/*     */ public class DataFormatMatcher
/*     */ {
/*     */   protected final InputStream _originalStream;
/*     */   protected final byte[] _bufferedData;
/*     */   protected final int _bufferedStart;
/*     */   protected final int _bufferedLength;
/*     */   protected final JsonFactory _match;
/*     */   protected final MatchStrength _matchStrength;
/*     */   
/*     */   protected DataFormatMatcher(InputStream in, byte[] buffered, int bufferedStart, int bufferedLength, JsonFactory match, MatchStrength strength) {
/*  46 */     this._originalStream = in;
/*  47 */     this._bufferedData = buffered;
/*  48 */     this._bufferedStart = bufferedStart;
/*  49 */     this._bufferedLength = bufferedLength;
/*  50 */     this._match = match;
/*  51 */     this._matchStrength = strength;
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
/*     */   public boolean hasMatch() {
/*  64 */     return (this._match != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatchStrength getMatchStrength() {
/*  71 */     return (this._matchStrength == null) ? MatchStrength.INCONCLUSIVE : this._matchStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonFactory getMatch() {
/*  77 */     return this._match;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMatchedFormatName() {
/*  87 */     return this._match.getFormatName();
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
/*     */   public JsonParser createParserWithMatch() throws IOException {
/* 102 */     if (this._match == null) {
/* 103 */       return null;
/*     */     }
/* 105 */     if (this._originalStream == null) {
/* 106 */       return this._match.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */     }
/* 108 */     return this._match.createParser(getDataStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getDataStream() {
/* 119 */     if (this._originalStream == null) {
/* 120 */       return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */     }
/* 122 */     return (InputStream)new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\format\DataFormatMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */