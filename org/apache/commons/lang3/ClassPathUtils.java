/*     */ package org.apache.commons.lang3;
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
/*     */ public class ClassPathUtils
/*     */ {
/*     */   public static String toFullyQualifiedName(Class<?> context, String resourceName) {
/*  58 */     Validate.notNull(context, "Parameter '%s' must not be null!", new Object[] { "context" });
/*  59 */     Validate.notNull(resourceName, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/*  60 */     return toFullyQualifiedName(context.getPackage(), resourceName);
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
/*     */   public static String toFullyQualifiedName(Package context, String resourceName) {
/*  80 */     Validate.notNull(context, "Parameter '%s' must not be null!", new Object[] { "context" });
/*  81 */     Validate.notNull(resourceName, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/*  82 */     return context.getName() + "." + resourceName;
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
/*     */   public static String toFullyQualifiedPath(Class<?> context, String resourceName) {
/* 102 */     Validate.notNull(context, "Parameter '%s' must not be null!", new Object[] { "context" });
/* 103 */     Validate.notNull(resourceName, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/* 104 */     return toFullyQualifiedPath(context.getPackage(), resourceName);
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
/*     */   
/*     */   public static String toFullyQualifiedPath(Package context, String resourceName) {
/* 125 */     Validate.notNull(context, "Parameter '%s' must not be null!", new Object[] { "context" });
/* 126 */     Validate.notNull(resourceName, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/* 127 */     return context.getName().replace('.', '/') + "/" + resourceName;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\ClassPathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */