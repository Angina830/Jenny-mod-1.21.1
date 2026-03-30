/*    */ package software.bernie.shadowed.fasterxml.jackson.core.io;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Arrays;
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
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
/*    */ public abstract class CharacterEscapes
/*    */   implements Serializable
/*    */ {
/*    */   public static final int ESCAPE_NONE = 0;
/*    */   public static final int ESCAPE_STANDARD = -1;
/*    */   public static final int ESCAPE_CUSTOM = -2;
/*    */   
/*    */   public abstract int[] getEscapeCodesForAscii();
/*    */   
/*    */   public abstract SerializableString getEscapeSequence(int paramInt);
/*    */   
/*    */   public static int[] standardAsciiEscapesForJSON() {
/* 68 */     int[] esc = CharTypes.get7BitOutputEscapes();
/* 69 */     return Arrays.copyOf(esc, esc.length);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\io\CharacterEscapes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */