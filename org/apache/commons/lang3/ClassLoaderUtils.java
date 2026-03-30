/*    */ package org.apache.commons.lang3;
/*    */ 
/*    */ import java.net.URLClassLoader;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassLoaderUtils
/*    */ {
/*    */   public static String toString(ClassLoader classLoader) {
/* 37 */     if (classLoader instanceof URLClassLoader) {
/* 38 */       return toString((URLClassLoader)classLoader);
/*    */     }
/* 40 */     return classLoader.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String toString(URLClassLoader classLoader) {
/* 51 */     return classLoader + Arrays.toString((Object[])classLoader.getURLs());
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\org\apache\commons\lang3\ClassLoaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */