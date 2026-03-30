package software.bernie.shadowed.fasterxml.jackson.databind.util;

import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;

public interface Converter<IN, OUT> {
  OUT convert(IN paramIN);
  
  JavaType getInputType(TypeFactory paramTypeFactory);
  
  JavaType getOutputType(TypeFactory paramTypeFactory);
  
  public static abstract class None implements Converter<Object, Object> {}
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\Converter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */