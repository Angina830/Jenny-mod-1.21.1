/*     */ package software.bernie.shadowed.fasterxml.jackson.core.json;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerationException;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonWriteContext
/*     */   extends JsonStreamContext
/*     */ {
/*     */   public static final int STATUS_OK_AS_IS = 0;
/*     */   public static final int STATUS_OK_AFTER_COMMA = 1;
/*     */   public static final int STATUS_OK_AFTER_COLON = 2;
/*     */   public static final int STATUS_OK_AFTER_SPACE = 3;
/*     */   public static final int STATUS_EXPECT_VALUE = 4;
/*     */   public static final int STATUS_EXPECT_NAME = 5;
/*     */   protected final JsonWriteContext _parent;
/*     */   protected DupDetector _dups;
/*     */   protected JsonWriteContext _child;
/*     */   protected String _currentName;
/*     */   protected Object _currentValue;
/*     */   protected boolean _gotName;
/*     */   
/*     */   protected JsonWriteContext(int type, JsonWriteContext parent, DupDetector dups) {
/*  71 */     this._type = type;
/*  72 */     this._parent = parent;
/*  73 */     this._dups = dups;
/*  74 */     this._index = -1;
/*     */   }
/*     */   
/*     */   protected JsonWriteContext reset(int type) {
/*  78 */     this._type = type;
/*  79 */     this._index = -1;
/*  80 */     this._currentName = null;
/*  81 */     this._gotName = false;
/*  82 */     this._currentValue = null;
/*  83 */     if (this._dups != null) this._dups.reset(); 
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public JsonWriteContext withDupDetector(DupDetector dups) {
/*  88 */     this._dups = dups;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getCurrentValue() {
/*  94 */     return this._currentValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentValue(Object v) {
/*  99 */     this._currentValue = v;
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
/*     */   @Deprecated
/*     */   public static JsonWriteContext createRootContext() {
/* 112 */     return createRootContext((DupDetector)null);
/*     */   }
/*     */   public static JsonWriteContext createRootContext(DupDetector dd) {
/* 115 */     return new JsonWriteContext(0, null, dd);
/*     */   }
/*     */   
/*     */   public JsonWriteContext createChildArrayContext() {
/* 119 */     JsonWriteContext ctxt = this._child;
/* 120 */     if (ctxt == null) {
/* 121 */       this._child = ctxt = new JsonWriteContext(1, this, (this._dups == null) ? null : this._dups.child());
/* 122 */       return ctxt;
/*     */     } 
/* 124 */     return ctxt.reset(1);
/*     */   }
/*     */   
/*     */   public JsonWriteContext createChildObjectContext() {
/* 128 */     JsonWriteContext ctxt = this._child;
/* 129 */     if (ctxt == null) {
/* 130 */       this._child = ctxt = new JsonWriteContext(2, this, (this._dups == null) ? null : this._dups.child());
/* 131 */       return ctxt;
/*     */     } 
/* 133 */     return ctxt.reset(2);
/*     */   }
/*     */   
/* 136 */   public final JsonWriteContext getParent() { return this._parent; } public final String getCurrentName() {
/* 137 */     return this._currentName;
/*     */   } public boolean hasCurrentName() {
/* 139 */     return (this._currentName != null);
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
/*     */   public JsonWriteContext clearAndGetParent() {
/* 152 */     this._currentValue = null;
/*     */     
/* 154 */     return this._parent;
/*     */   }
/*     */   
/*     */   public DupDetector getDupDetector() {
/* 158 */     return this._dups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int writeFieldName(String name) throws JsonProcessingException {
/* 167 */     if (this._type != 2 || this._gotName) {
/* 168 */       return 4;
/*     */     }
/* 170 */     this._gotName = true;
/* 171 */     this._currentName = name;
/* 172 */     if (this._dups != null) _checkDup(this._dups, name); 
/* 173 */     return (this._index < 0) ? 0 : 1;
/*     */   }
/*     */   
/*     */   private final void _checkDup(DupDetector dd, String name) throws JsonProcessingException {
/* 177 */     if (dd.isDup(name)) {
/* 178 */       Object src = dd.getSource();
/* 179 */       throw new JsonGenerationException("Duplicate field '" + name + "'", (src instanceof JsonGenerator) ? (JsonGenerator)src : null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int writeValue() {
/* 186 */     if (this._type == 2) {
/* 187 */       if (!this._gotName) {
/* 188 */         return 5;
/*     */       }
/* 190 */       this._gotName = false;
/* 191 */       this._index++;
/* 192 */       return 2;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     if (this._type == 1) {
/* 197 */       int ix = this._index;
/* 198 */       this._index++;
/* 199 */       return (ix < 0) ? 0 : 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 204 */     this._index++;
/* 205 */     return (this._index == 0) ? 0 : 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\json\JsonWriteContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */