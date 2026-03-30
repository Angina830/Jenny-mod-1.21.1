/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnresolvedId
/*    */ {
/*    */   private final Object _id;
/*    */   private final JsonLocation _location;
/*    */   private final Class<?> _type;
/*    */   
/*    */   public UnresolvedId(Object id, Class<?> type, JsonLocation where) {
/* 17 */     this._id = id;
/* 18 */     this._type = type;
/* 19 */     this._location = where;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getId() {
/* 25 */     return this._id;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getType() {
/* 30 */     return this._type; } public JsonLocation getLocation() {
/* 31 */     return this._location;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     return String.format("Object id [%s] (for %s) at %s", new Object[] { this._id, ClassUtil.nameOf(this._type), this._location });
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\UnresolvedId.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */