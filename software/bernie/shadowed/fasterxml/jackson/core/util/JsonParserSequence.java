/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonToken;
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
/*     */ public class JsonParserSequence
/*     */   extends JsonParserDelegate
/*     */ {
/*     */   protected final JsonParser[] _parsers;
/*     */   protected final boolean _checkForExistingToken;
/*     */   protected int _nextParserIndex;
/*     */   protected boolean _hasToken;
/*     */   
/*     */   @Deprecated
/*     */   protected JsonParserSequence(JsonParser[] parsers) {
/*  60 */     this(false, parsers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonParserSequence(boolean checkForExistingToken, JsonParser[] parsers) {
/*  68 */     super(parsers[0]);
/*  69 */     this._checkForExistingToken = checkForExistingToken;
/*  70 */     this._hasToken = (checkForExistingToken && this.delegate.hasCurrentToken());
/*  71 */     this._parsers = parsers;
/*  72 */     this._nextParserIndex = 1;
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
/*     */   public static JsonParserSequence createFlattened(boolean checkForExistingToken, JsonParser first, JsonParser second) {
/*  87 */     if (!(first instanceof JsonParserSequence) && !(second instanceof JsonParserSequence)) {
/*  88 */       return new JsonParserSequence(checkForExistingToken, new JsonParser[] { first, second });
/*     */     }
/*     */     
/*  91 */     ArrayList<JsonParser> p = new ArrayList<JsonParser>();
/*  92 */     if (first instanceof JsonParserSequence) {
/*  93 */       ((JsonParserSequence)first).addFlattenedActiveParsers(p);
/*     */     } else {
/*  95 */       p.add(first);
/*     */     } 
/*  97 */     if (second instanceof JsonParserSequence) {
/*  98 */       ((JsonParserSequence)second).addFlattenedActiveParsers(p);
/*     */     } else {
/* 100 */       p.add(second);
/*     */     } 
/* 102 */     return new JsonParserSequence(checkForExistingToken, p.<JsonParser>toArray(new JsonParser[p.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JsonParserSequence createFlattened(JsonParser first, JsonParser second) {
/* 112 */     return createFlattened(false, first, second);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addFlattenedActiveParsers(List<JsonParser> listToAddIn) {
/* 118 */     for (int i = this._nextParserIndex - 1, len = this._parsers.length; i < len; i++) {
/* 119 */       JsonParser p = this._parsers[i];
/* 120 */       if (p instanceof JsonParserSequence) {
/* 121 */         ((JsonParserSequence)p).addFlattenedActiveParsers(listToAddIn);
/*     */       } else {
/* 123 */         listToAddIn.add(p);
/*     */       } 
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
/*     */   public void close() throws IOException {
/*     */     
/* 137 */     do { this.delegate.close(); } while (switchToNext());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonToken nextToken() throws IOException {
/* 143 */     if (this.delegate == null) {
/* 144 */       return null;
/*     */     }
/* 146 */     if (this._hasToken) {
/* 147 */       this._hasToken = false;
/* 148 */       return this.delegate.currentToken();
/*     */     } 
/* 150 */     JsonToken t = this.delegate.nextToken();
/* 151 */     if (t == null) {
/* 152 */       return switchAndReturnNext();
/*     */     }
/* 154 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonParser skipChildren() throws IOException {
/* 165 */     if (this.delegate.currentToken() != JsonToken.START_OBJECT && this.delegate.currentToken() != JsonToken.START_ARRAY)
/*     */     {
/* 167 */       return this;
/*     */     }
/* 169 */     int open = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 174 */       JsonToken t = nextToken();
/* 175 */       if (t == null) {
/* 176 */         return this;
/*     */       }
/* 178 */       if (t.isStructStart()) {
/* 179 */         open++; continue;
/* 180 */       }  if (t.isStructEnd() && 
/* 181 */         --open == 0) {
/* 182 */         return this;
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int containedParsersCount() {
/* 200 */     return this._parsers.length;
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
/*     */   
/*     */   protected boolean switchToNext() {
/* 220 */     if (this._nextParserIndex < this._parsers.length) {
/* 221 */       this.delegate = this._parsers[this._nextParserIndex++];
/* 222 */       return true;
/*     */     } 
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonToken switchAndReturnNext() throws IOException {
/* 229 */     while (this._nextParserIndex < this._parsers.length) {
/* 230 */       this.delegate = this._parsers[this._nextParserIndex++];
/* 231 */       if (this._checkForExistingToken && this.delegate.hasCurrentToken()) {
/* 232 */         return this.delegate.getCurrentToken();
/*     */       }
/* 234 */       JsonToken t = this.delegate.nextToken();
/* 235 */       if (t != null) {
/* 236 */         return t;
/*     */       }
/*     */     } 
/* 239 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\JsonParserSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */