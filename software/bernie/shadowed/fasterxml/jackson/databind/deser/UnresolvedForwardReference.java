/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnresolvedForwardReference
/*    */   extends JsonMappingException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private ReadableObjectId _roid;
/*    */   private List<UnresolvedId> _unresolvedIds;
/*    */   
/*    */   public UnresolvedForwardReference(JsonParser p, String msg, JsonLocation loc, ReadableObjectId roid) {
/* 28 */     super((Closeable)p, msg, loc);
/* 29 */     this._roid = roid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnresolvedForwardReference(JsonParser p, String msg) {
/* 36 */     super((Closeable)p, msg);
/* 37 */     this._unresolvedIds = new ArrayList<>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public UnresolvedForwardReference(String msg, JsonLocation loc, ReadableObjectId roid) {
/* 45 */     super(msg, loc);
/* 46 */     this._roid = roid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public UnresolvedForwardReference(String msg) {
/* 54 */     super(msg);
/* 55 */     this._unresolvedIds = new ArrayList<>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReadableObjectId getRoid() {
/* 65 */     return this._roid;
/*    */   }
/*    */   
/*    */   public Object getUnresolvedId() {
/* 69 */     return (this._roid.getKey()).key;
/*    */   }
/*    */   
/*    */   public void addUnresolvedId(Object id, Class<?> type, JsonLocation where) {
/* 73 */     this._unresolvedIds.add(new UnresolvedId(id, type, where));
/*    */   }
/*    */   
/*    */   public List<UnresolvedId> getUnresolvedIds() {
/* 77 */     return this._unresolvedIds;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 83 */     String msg = super.getMessage();
/* 84 */     if (this._unresolvedIds == null) {
/* 85 */       return msg;
/*    */     }
/*    */     
/* 88 */     StringBuilder sb = new StringBuilder(msg);
/* 89 */     Iterator<UnresolvedId> iterator = this._unresolvedIds.iterator();
/* 90 */     while (iterator.hasNext()) {
/* 91 */       UnresolvedId unresolvedId = iterator.next();
/* 92 */       sb.append(unresolvedId.toString());
/* 93 */       if (iterator.hasNext()) {
/* 94 */         sb.append(", ");
/*    */       }
/*    */     } 
/* 97 */     sb.append('.');
/* 98 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\UnresolvedForwardReference.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */