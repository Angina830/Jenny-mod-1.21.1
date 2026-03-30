package software.bernie.shadowed.fasterxml.jackson.databind.cfg;

public interface ConfigFeature {
  boolean enabledByDefault();
  
  int getMask();
  
  boolean enabledIn(int paramInt);
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\ConfigFeature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */