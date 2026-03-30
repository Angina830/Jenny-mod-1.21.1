package software.bernie.shadowed.fasterxml.jackson.databind.deser;

import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;

public interface ContextualDeserializer {
  JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty) throws JsonMappingException;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\ContextualDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */