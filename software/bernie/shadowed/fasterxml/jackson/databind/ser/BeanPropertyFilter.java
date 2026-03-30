package software.bernie.shadowed.fasterxml.jackson.databind.ser;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;

@Deprecated
public interface BeanPropertyFilter {
  void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, BeanPropertyWriter paramBeanPropertyWriter) throws Exception;
  
  @Deprecated
  void depositSchemaProperty(BeanPropertyWriter paramBeanPropertyWriter, ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider) throws JsonMappingException;
  
  void depositSchemaProperty(BeanPropertyWriter paramBeanPropertyWriter, JsonObjectFormatVisitor paramJsonObjectFormatVisitor, SerializerProvider paramSerializerProvider) throws JsonMappingException;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\BeanPropertyFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */