/*    */ package software.bernie.shadowed.eliotlash.mclib.utils;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ public class JsonUtils
/*    */ {
/*    */   public static String jsonToPretty(JsonElement element) {
/* 12 */     StringWriter writer = new StringWriter();
/* 13 */     JsonWriter jsonWriter = new JsonWriter(writer);
/* 14 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*    */     
/* 16 */     jsonWriter.setIndent("    ");
/* 17 */     gson.toJson(element, jsonWriter);
/*    */ 
/*    */     
/* 20 */     return writer.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mcli\\utils\JsonUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */