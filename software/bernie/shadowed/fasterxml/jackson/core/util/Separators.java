/*    */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class Separators
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final char objectFieldValueSeparator;
/*    */   private final char objectEntrySeparator;
/*    */   private final char arrayValueSeparator;
/*    */   
/*    */   public static Separators createDefaultInstance() {
/* 23 */     return new Separators();
/*    */   }
/*    */   
/*    */   public Separators() {
/* 27 */     this(':', ',', ',');
/*    */   }
/*    */ 
/*    */   
/*    */   public Separators(char objectFieldValueSeparator, char objectEntrySeparator, char arrayValueSeparator) {
/* 32 */     this.objectFieldValueSeparator = objectFieldValueSeparator;
/* 33 */     this.objectEntrySeparator = objectEntrySeparator;
/* 34 */     this.arrayValueSeparator = arrayValueSeparator;
/*    */   }
/*    */   
/*    */   public Separators withObjectFieldValueSeparator(char sep) {
/* 38 */     return (this.objectFieldValueSeparator == sep) ? this : new Separators(sep, this.objectEntrySeparator, this.arrayValueSeparator);
/*    */   }
/*    */ 
/*    */   
/*    */   public Separators withObjectEntrySeparator(char sep) {
/* 43 */     return (this.objectEntrySeparator == sep) ? this : new Separators(this.objectFieldValueSeparator, sep, this.arrayValueSeparator);
/*    */   }
/*    */ 
/*    */   
/*    */   public Separators withArrayValueSeparator(char sep) {
/* 48 */     return (this.arrayValueSeparator == sep) ? this : new Separators(this.objectFieldValueSeparator, this.objectEntrySeparator, sep);
/*    */   }
/*    */ 
/*    */   
/*    */   public char getObjectFieldValueSeparator() {
/* 53 */     return this.objectFieldValueSeparator;
/*    */   }
/*    */   
/*    */   public char getObjectEntrySeparator() {
/* 57 */     return this.objectEntrySeparator;
/*    */   }
/*    */   
/*    */   public char getArrayValueSeparator() {
/* 61 */     return this.arrayValueSeparator;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\Separators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */