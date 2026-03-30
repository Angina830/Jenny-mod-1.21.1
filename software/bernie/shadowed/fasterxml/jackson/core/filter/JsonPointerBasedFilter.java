/*    */ package software.bernie.shadowed.fasterxml.jackson.core.filter;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonPointer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsonPointerBasedFilter
/*    */   extends TokenFilter
/*    */ {
/*    */   protected final JsonPointer _pathToMatch;
/*    */   
/*    */   public JsonPointerBasedFilter(String ptrExpr) {
/* 18 */     this(JsonPointer.compile(ptrExpr));
/*    */   }
/*    */   
/*    */   public JsonPointerBasedFilter(JsonPointer match) {
/* 22 */     this._pathToMatch = match;
/*    */   }
/*    */ 
/*    */   
/*    */   public TokenFilter includeElement(int index) {
/* 27 */     JsonPointer next = this._pathToMatch.matchElement(index);
/* 28 */     if (next == null) {
/* 29 */       return null;
/*    */     }
/* 31 */     if (next.matches()) {
/* 32 */       return TokenFilter.INCLUDE_ALL;
/*    */     }
/* 34 */     return new JsonPointerBasedFilter(next);
/*    */   }
/*    */ 
/*    */   
/*    */   public TokenFilter includeProperty(String name) {
/* 39 */     JsonPointer next = this._pathToMatch.matchProperty(name);
/* 40 */     if (next == null) {
/* 41 */       return null;
/*    */     }
/* 43 */     if (next.matches()) {
/* 44 */       return TokenFilter.INCLUDE_ALL;
/*    */     }
/* 46 */     return new JsonPointerBasedFilter(next);
/*    */   }
/*    */ 
/*    */   
/*    */   public TokenFilter filterStartArray() {
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public TokenFilter filterStartObject() {
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean _includeScalar() {
/* 62 */     return this._pathToMatch.matches();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return "[JsonPointerFilter at: " + this._pathToMatch + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\filter\JsonPointerBasedFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */