/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DatabindContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinimalClassNameIdResolver
/*    */   extends ClassNameIdResolver
/*    */ {
/*    */   protected final String _basePackageName;
/*    */   protected final String _basePackagePrefix;
/*    */   
/*    */   protected MinimalClassNameIdResolver(JavaType baseType, TypeFactory typeFactory) {
/* 27 */     super(baseType, typeFactory);
/* 28 */     String base = baseType.getRawClass().getName();
/* 29 */     int ix = base.lastIndexOf('.');
/* 30 */     if (ix < 0) {
/* 31 */       this._basePackageName = "";
/* 32 */       this._basePackagePrefix = ".";
/*    */     } else {
/* 34 */       this._basePackagePrefix = base.substring(0, ix + 1);
/* 35 */       this._basePackageName = base.substring(0, ix);
/*    */     } 
/*    */   }
/*    */   
/*    */   public JsonTypeInfo.Id getMechanism() {
/* 40 */     return JsonTypeInfo.Id.MINIMAL_CLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public String idFromValue(Object value) {
/* 45 */     String n = value.getClass().getName();
/* 46 */     if (n.startsWith(this._basePackagePrefix))
/*    */     {
/* 48 */       return n.substring(this._basePackagePrefix.length() - 1);
/*    */     }
/* 50 */     return n;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected JavaType _typeFromId(String id, DatabindContext ctxt) throws IOException {
/* 56 */     if (id.startsWith(".")) {
/* 57 */       StringBuilder sb = new StringBuilder(id.length() + this._basePackageName.length());
/* 58 */       if (this._basePackageName.length() == 0) {
/*    */         
/* 60 */         sb.append(id.substring(1));
/*    */       } else {
/*    */         
/* 63 */         sb.append(this._basePackageName).append(id);
/*    */       } 
/* 65 */       id = sb.toString();
/*    */     } 
/* 67 */     return super._typeFromId(id, ctxt);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\impl\MinimalClassNameIdResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */