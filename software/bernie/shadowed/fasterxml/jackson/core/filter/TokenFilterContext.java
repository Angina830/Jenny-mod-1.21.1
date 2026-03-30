/*     */ package software.bernie.shadowed.fasterxml.jackson.core.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
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
/*     */ public class TokenFilterContext
/*     */   extends JsonStreamContext
/*     */ {
/*     */   protected final TokenFilterContext _parent;
/*     */   protected TokenFilterContext _child;
/*     */   protected String _currentName;
/*     */   protected TokenFilter _filter;
/*     */   protected boolean _startHandled;
/*     */   protected boolean _needToHandleName;
/*     */   
/*     */   protected TokenFilterContext(int type, TokenFilterContext parent, TokenFilter filter, boolean startHandled) {
/*  72 */     this._type = type;
/*  73 */     this._parent = parent;
/*  74 */     this._filter = filter;
/*  75 */     this._index = -1;
/*  76 */     this._startHandled = startHandled;
/*  77 */     this._needToHandleName = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected TokenFilterContext reset(int type, TokenFilter filter, boolean startWritten) {
/*  83 */     this._type = type;
/*  84 */     this._filter = filter;
/*  85 */     this._index = -1;
/*  86 */     this._currentName = null;
/*  87 */     this._startHandled = startWritten;
/*  88 */     this._needToHandleName = false;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TokenFilterContext createRootContext(TokenFilter filter) {
/* 100 */     return new TokenFilterContext(0, null, filter, true);
/*     */   }
/*     */   
/*     */   public TokenFilterContext createChildArrayContext(TokenFilter filter, boolean writeStart) {
/* 104 */     TokenFilterContext ctxt = this._child;
/* 105 */     if (ctxt == null) {
/* 106 */       this._child = ctxt = new TokenFilterContext(1, this, filter, writeStart);
/* 107 */       return ctxt;
/*     */     } 
/* 109 */     return ctxt.reset(1, filter, writeStart);
/*     */   }
/*     */   
/*     */   public TokenFilterContext createChildObjectContext(TokenFilter filter, boolean writeStart) {
/* 113 */     TokenFilterContext ctxt = this._child;
/* 114 */     if (ctxt == null) {
/* 115 */       this._child = ctxt = new TokenFilterContext(2, this, filter, writeStart);
/* 116 */       return ctxt;
/*     */     } 
/* 118 */     return ctxt.reset(2, filter, writeStart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenFilter setFieldName(String name) throws JsonProcessingException {
/* 128 */     this._currentName = name;
/* 129 */     this._needToHandleName = true;
/* 130 */     return this._filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenFilter checkValue(TokenFilter filter) {
/* 139 */     if (this._type == 2) {
/* 140 */       return filter;
/*     */     }
/*     */     
/* 143 */     int ix = ++this._index;
/* 144 */     if (this._type == 1) {
/* 145 */       return filter.includeElement(ix);
/*     */     }
/* 147 */     return filter.includeRootValue(ix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePath(JsonGenerator gen) throws IOException {
/* 156 */     if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
/*     */       return;
/*     */     }
/* 159 */     if (this._parent != null) {
/* 160 */       this._parent._writePath(gen);
/*     */     }
/* 162 */     if (this._startHandled) {
/*     */       
/* 164 */       if (this._needToHandleName) {
/* 165 */         gen.writeFieldName(this._currentName);
/*     */       }
/*     */     } else {
/* 168 */       this._startHandled = true;
/* 169 */       if (this._type == 2) {
/* 170 */         gen.writeStartObject();
/* 171 */         gen.writeFieldName(this._currentName);
/* 172 */       } else if (this._type == 1) {
/* 173 */         gen.writeStartArray();
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
/*     */   public void writeImmediatePath(JsonGenerator gen) throws IOException {
/* 186 */     if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
/*     */       return;
/*     */     }
/* 189 */     if (this._startHandled) {
/*     */       
/* 191 */       if (this._needToHandleName) {
/* 192 */         gen.writeFieldName(this._currentName);
/*     */       }
/*     */     } else {
/* 195 */       this._startHandled = true;
/* 196 */       if (this._type == 2) {
/* 197 */         gen.writeStartObject();
/* 198 */         if (this._needToHandleName) {
/* 199 */           gen.writeFieldName(this._currentName);
/*     */         }
/* 201 */       } else if (this._type == 1) {
/* 202 */         gen.writeStartArray();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void _writePath(JsonGenerator gen) throws IOException {
/* 209 */     if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
/*     */       return;
/*     */     }
/* 212 */     if (this._parent != null) {
/* 213 */       this._parent._writePath(gen);
/*     */     }
/* 215 */     if (this._startHandled) {
/*     */       
/* 217 */       if (this._needToHandleName) {
/* 218 */         this._needToHandleName = false;
/* 219 */         gen.writeFieldName(this._currentName);
/*     */       } 
/*     */     } else {
/* 222 */       this._startHandled = true;
/* 223 */       if (this._type == 2) {
/* 224 */         gen.writeStartObject();
/* 225 */         if (this._needToHandleName) {
/* 226 */           this._needToHandleName = false;
/* 227 */           gen.writeFieldName(this._currentName);
/*     */         } 
/* 229 */       } else if (this._type == 1) {
/* 230 */         gen.writeStartArray();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TokenFilterContext closeArray(JsonGenerator gen) throws IOException {
/* 237 */     if (this._startHandled) {
/* 238 */       gen.writeEndArray();
/*     */     }
/* 240 */     if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
/* 241 */       this._filter.filterFinishArray();
/*     */     }
/* 243 */     return this._parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public TokenFilterContext closeObject(JsonGenerator gen) throws IOException {
/* 248 */     if (this._startHandled) {
/* 249 */       gen.writeEndObject();
/*     */     }
/* 251 */     if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
/* 252 */       this._filter.filterFinishObject();
/*     */     }
/* 254 */     return this._parent;
/*     */   }
/*     */   
/*     */   public void skipParentChecks() {
/* 258 */     this._filter = null;
/* 259 */     for (TokenFilterContext ctxt = this._parent; ctxt != null; ctxt = ctxt._parent) {
/* 260 */       this._parent._filter = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCurrentValue() {
/* 271 */     return null;
/*     */   }
/*     */   
/*     */   public void setCurrentValue(Object v) {}
/*     */   
/* 276 */   public final TokenFilterContext getParent() { return this._parent; } public final String getCurrentName() {
/* 277 */     return this._currentName;
/*     */   } public boolean hasCurrentName() {
/* 279 */     return (this._currentName != null);
/*     */   }
/* 281 */   public TokenFilter getFilter() { return this._filter; } public boolean isStartHandled() {
/* 282 */     return this._startHandled;
/*     */   }
/*     */   public JsonToken nextTokenToRead() {
/* 285 */     if (!this._startHandled) {
/* 286 */       this._startHandled = true;
/* 287 */       if (this._type == 2) {
/* 288 */         return JsonToken.START_OBJECT;
/*     */       }
/*     */       
/* 291 */       return JsonToken.START_ARRAY;
/*     */     } 
/*     */     
/* 294 */     if (this._needToHandleName && this._type == 2) {
/* 295 */       this._needToHandleName = false;
/* 296 */       return JsonToken.FIELD_NAME;
/*     */     } 
/* 298 */     return null;
/*     */   }
/*     */   
/*     */   public TokenFilterContext findChildOf(TokenFilterContext parent) {
/* 302 */     if (this._parent == parent) {
/* 303 */       return this;
/*     */     }
/* 305 */     TokenFilterContext curr = this._parent;
/* 306 */     while (curr != null) {
/* 307 */       TokenFilterContext p = curr._parent;
/* 308 */       if (p == parent) {
/* 309 */         return curr;
/*     */       }
/* 311 */       curr = p;
/*     */     } 
/*     */     
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void appendDesc(StringBuilder sb) {
/* 320 */     if (this._parent != null) {
/* 321 */       this._parent.appendDesc(sb);
/*     */     }
/* 323 */     if (this._type == 2) {
/* 324 */       sb.append('{');
/* 325 */       if (this._currentName != null) {
/* 326 */         sb.append('"');
/*     */         
/* 328 */         sb.append(this._currentName);
/* 329 */         sb.append('"');
/*     */       } else {
/* 331 */         sb.append('?');
/*     */       } 
/* 333 */       sb.append('}');
/* 334 */     } else if (this._type == 1) {
/* 335 */       sb.append('[');
/* 336 */       sb.append(getCurrentIndex());
/* 337 */       sb.append(']');
/*     */     } else {
/*     */       
/* 340 */       sb.append("/");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 351 */     StringBuilder sb = new StringBuilder(64);
/* 352 */     appendDesc(sb);
/* 353 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\filter\TokenFilterContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */