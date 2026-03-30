package software.bernie.shadowed.fasterxml.jackson.databind.deser;

import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;

public interface ResolvableDeserializer {
  void resolve(DeserializationContext paramDeserializationContext) throws JsonMappingException;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\ResolvableDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */