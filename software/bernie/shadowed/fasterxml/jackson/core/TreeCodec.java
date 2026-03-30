package software.bernie.shadowed.fasterxml.jackson.core;

import java.io.IOException;

public abstract class TreeCodec {
  public abstract <T extends TreeNode> T readTree(JsonParser paramJsonParser) throws IOException, JsonProcessingException;
  
  public abstract void writeTree(JsonGenerator paramJsonGenerator, TreeNode paramTreeNode) throws IOException, JsonProcessingException;
  
  public abstract TreeNode createArrayNode();
  
  public abstract TreeNode createObjectNode();
  
  public abstract JsonParser treeAsTokens(TreeNode paramTreeNode);
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\TreeCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */