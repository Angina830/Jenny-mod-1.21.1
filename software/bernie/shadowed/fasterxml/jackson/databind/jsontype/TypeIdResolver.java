package software.bernie.shadowed.fasterxml.jackson.databind.jsontype;

import java.io.IOException;
import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;
import software.bernie.shadowed.fasterxml.jackson.databind.DatabindContext;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;

public interface TypeIdResolver {
  void init(JavaType paramJavaType);
  
  String idFromValue(Object paramObject);
  
  String idFromValueAndType(Object paramObject, Class<?> paramClass);
  
  String idFromBaseType();
  
  JavaType typeFromId(DatabindContext paramDatabindContext, String paramString) throws IOException;
  
  String getDescForKnownTypeIds();
  
  JsonTypeInfo.Id getMechanism();
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsontype\TypeIdResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */