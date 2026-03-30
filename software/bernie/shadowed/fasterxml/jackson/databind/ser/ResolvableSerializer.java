package software.bernie.shadowed.fasterxml.jackson.databind.ser;

import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

public interface ResolvableSerializer {
  void resolve(SerializerProvider paramSerializerProvider) throws JsonMappingException;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\ResolvableSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */