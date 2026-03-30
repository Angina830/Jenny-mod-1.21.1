package software.bernie.shadowed.fasterxml.jackson.core.async;

public interface NonBlockingInputFeeder {
  boolean needMoreInput();
  
  void endOfInput();
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\async\NonBlockingInputFeeder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */