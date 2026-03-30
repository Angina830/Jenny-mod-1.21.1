package software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;

import java.util.Set;

public interface JsonValueFormatVisitor {
  void format(JsonValueFormat paramJsonValueFormat);
  
  void enumTypes(Set<String> paramSet);
  
  public static class Base implements JsonValueFormatVisitor {
    public void format(JsonValueFormat format) {}
    
    public void enumTypes(Set<String> enums) {}
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonFormatVisitors\JsonValueFormatVisitor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */