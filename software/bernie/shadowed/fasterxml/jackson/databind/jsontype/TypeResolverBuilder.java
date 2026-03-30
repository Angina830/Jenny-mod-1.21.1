package software.bernie.shadowed.fasterxml.jackson.databind.jsontype;

import java.util.Collection;
import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializationConfig;

public interface TypeResolverBuilder<T extends TypeResolverBuilder<T>> {
  Class<?> getDefaultImpl();
  
  TypeSerializer buildTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection);
  
  TypeDeserializer buildTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection);
  
  T init(JsonTypeInfo.Id paramId, TypeIdResolver paramTypeIdResolver);
  
  T inclusion(JsonTypeInfo.As paramAs);
  
  T typeProperty(String paramString);
  
  T defaultImpl(Class<?> paramClass);
  
  T typeIdVisibility(boolean paramBoolean);
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\TypeResolverBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */