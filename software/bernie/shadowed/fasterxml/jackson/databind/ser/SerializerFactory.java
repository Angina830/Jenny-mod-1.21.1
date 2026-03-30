package software.bernie.shadowed.fasterxml.jackson.databind.ser;

import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class SerializerFactory {
  public abstract SerializerFactory withAdditionalSerializers(Serializers paramSerializers);
  
  public abstract SerializerFactory withAdditionalKeySerializers(Serializers paramSerializers);
  
  public abstract SerializerFactory withSerializerModifier(BeanSerializerModifier paramBeanSerializerModifier);
  
  public abstract JsonSerializer<Object> createSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType) throws JsonMappingException;
  
  public abstract TypeSerializer createTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType) throws JsonMappingException;
  
  public abstract JsonSerializer<Object> createKeySerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer) throws JsonMappingException;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ser\SerializerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */