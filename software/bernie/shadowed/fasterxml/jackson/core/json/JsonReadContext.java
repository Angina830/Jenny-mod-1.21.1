/*     */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParseException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonStreamContext;
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
/*     */ public final class JsonReadContext
/*     */   extends JsonStreamContext
/*     */ {
/*     */   protected final JsonReadContext _parent;
/*     */   protected DupDetector _dups;
/*     */   protected JsonReadContext _child;
/*     */   protected String _currentName;
/*     */   protected Object _currentValue;
/*     */   protected int _lineNr;
/*     */   protected int _columnNr;
/*     */   
/*     */   public JsonReadContext(JsonReadContext parent, DupDetector dups, int type, int lineNr, int colNr) {
/*  58 */     this._parent = parent;
/*  59 */     this._dups = dups;
/*  60 */     this._type = type;
/*  61 */     this._lineNr = lineNr;
/*  62 */     this._columnNr = colNr;
/*  63 */     this._index = -1;
/*     */   }
/*     */   
/*     */   protected void reset(int type, int lineNr, int colNr) {
/*  67 */     this._type = type;
/*  68 */     this._index = -1;
/*  69 */     this._lineNr = lineNr;
/*  70 */     this._columnNr = colNr;
/*  71 */     this._currentName = null;
/*  72 */     this._currentValue = null;
/*  73 */     if (this._dups != null) {
/*  74 */       this._dups.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonReadContext withDupDetector(DupDetector dups) {
/*  85 */     this._dups = dups;
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getCurrentValue() {
/*  91 */     return this._currentValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentValue(Object v) {
/*  96 */     this._currentValue = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonReadContext createRootContext(int lineNr, int colNr, DupDetector dups) {
/* 106 */     return new JsonReadContext(null, dups, 0, lineNr, colNr);
/*     */   }
/*     */   
/*     */   public static JsonReadContext createRootContext(DupDetector dups) {
/* 110 */     return new JsonReadContext(null, dups, 0, 1, 0);
/*     */   }
/*     */   
/*     */   public JsonReadContext createChildArrayContext(int lineNr, int colNr) {
/* 114 */     JsonReadContext ctxt = this._child;
/* 115 */     if (ctxt == null) {
/* 116 */       this._child = ctxt = new JsonReadContext(this, (this._dups == null) ? null : this._dups.child(), 1, lineNr, colNr);
/*     */     } else {
/*     */       
/* 119 */       ctxt.reset(1, lineNr, colNr);
/*     */     } 
/* 121 */     return ctxt;
/*     */   }
/*     */   
/*     */   public JsonReadContext createChildObjectContext(int lineNr, int colNr) {
/* 125 */     JsonReadContext ctxt = this._child;
/* 126 */     if (ctxt == null) {
/* 127 */       this._child = ctxt = new JsonReadContext(this, (this._dups == null) ? null : this._dups.child(), 2, lineNr, colNr);
/*     */       
/* 129 */       return ctxt;
/*     */     } 
/* 131 */     ctxt.reset(2, lineNr, colNr);
/* 132 */     return ctxt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCurrentName() {
/* 141 */     return this._currentName;
/*     */   }
/*     */   public boolean hasCurrentName() {
/* 144 */     return (this._currentName != null);
/*     */   } public JsonReadContext getParent() {
/* 146 */     return this._parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonLocation getStartLocation(Object srcRef) {
/* 151 */     long totalChars = -1L;
/* 152 */     return new JsonLocation(srcRef, totalChars, this._lineNr, this._columnNr);
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
/*     */   public JsonReadContext clearAndGetParent() {
/* 172 */     this._currentValue = null;
/*     */     
/* 174 */     return this._parent;
/*     */   }
/*     */   
/*     */   public DupDetector getDupDetector() {
/* 178 */     return this._dups;
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
/*     */   public boolean expectComma() {
/* 192 */     int ix = ++this._index;
/* 193 */     return (this._type != 0 && ix > 0);
/*     */   }
/*     */   
/*     */   public void setCurrentName(String name) throws JsonProcessingException {
/* 197 */     this._currentName = name;
/* 198 */     if (this._dups != null) _checkDup(this._dups, name); 
/*     */   }
/*     */   
/*     */   private void _checkDup(DupDetector dd, String name) throws JsonProcessingException {
/* 202 */     if (dd.isDup(name)) {
/* 203 */       Object src = dd.getSource();
/* 204 */       throw new JsonParseException((src instanceof JsonParser) ? (JsonParser)src : null, "Duplicate field '" + name + "'");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\JsonReadContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */