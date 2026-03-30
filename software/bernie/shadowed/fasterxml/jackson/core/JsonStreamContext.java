/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
/*     */ 
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.io.CharTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JsonStreamContext
/*     */ {
/*     */   protected static final int TYPE_ROOT = 0;
/*     */   protected static final int TYPE_ARRAY = 1;
/*     */   protected static final int TYPE_OBJECT = 2;
/*     */   protected int _type;
/*     */   protected int _index;
/*     */   
/*     */   protected JsonStreamContext() {}
/*     */   
/*     */   protected JsonStreamContext(JsonStreamContext base) {
/*  54 */     this._type = base._type;
/*  55 */     this._index = base._index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JsonStreamContext(int type, int index) {
/*  62 */     this._type = type;
/*  63 */     this._index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract JsonStreamContext getParent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean inArray() {
/*  82 */     return (this._type == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean inRoot() {
/*  89 */     return (this._type == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean inObject() {
/*  95 */     return (this._type == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final String getTypeDesc() {
/* 106 */     switch (this._type) { case 0:
/* 107 */         return "ROOT";
/* 108 */       case 1: return "ARRAY";
/* 109 */       case 2: return "OBJECT"; }
/*     */     
/* 111 */     return "?";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeDesc() {
/* 118 */     switch (this._type) { case 0:
/* 119 */         return "root";
/* 120 */       case 1: return "Array";
/* 121 */       case 2: return "Object"; }
/*     */     
/* 123 */     return "?";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getEntryCount() {
/* 129 */     return this._index + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getCurrentIndex() {
/* 134 */     return (this._index < 0) ? 0 : this._index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCurrentIndex() {
/* 143 */     return (this._index >= 0);
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
/*     */   public boolean hasPathSegment() {
/* 163 */     if (this._type == 2)
/* 164 */       return hasCurrentName(); 
/* 165 */     if (this._type == 1) {
/* 166 */       return hasCurrentIndex();
/*     */     }
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getCurrentName();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCurrentName() {
/* 181 */     return (getCurrentName() != null);
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
/*     */   public Object getCurrentValue() {
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentValue(Object v) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPointer pathAsPointer() {
/* 218 */     return JsonPointer.forPath(this, false);
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
/*     */   public JsonPointer pathAsPointer(boolean includeRoot) {
/* 231 */     return JsonPointer.forPath(this, includeRoot);
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
/*     */   public JsonLocation getStartLocation(Object srcRef) {
/* 250 */     return JsonLocation.NA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 261 */     StringBuilder sb = new StringBuilder(64);
/* 262 */     switch (this._type)
/*     */     { case 0:
/* 264 */         sb.append("/");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 285 */         return sb.toString();case 1: sb.append('['); sb.append(getCurrentIndex()); sb.append(']'); return sb.toString(); }  sb.append('{'); String currentName = getCurrentName(); if (currentName != null) { sb.append('"'); CharTypes.appendQuoted(sb, currentName); sb.append('"'); } else { sb.append('?'); }  sb.append('}'); return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\JsonStreamContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */