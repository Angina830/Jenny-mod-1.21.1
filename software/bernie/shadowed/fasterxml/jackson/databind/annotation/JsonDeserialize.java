package software.bernie.shadowed.fasterxml.jackson.databind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonAnnotation;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.util.Converter;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonDeserialize {
  Class<? extends JsonDeserializer> using() default JsonDeserializer.None.class;
  
  Class<? extends JsonDeserializer> contentUsing() default JsonDeserializer.None.class;
  
  Class<? extends KeyDeserializer> keyUsing() default KeyDeserializer.None.class;
  
  Class<?> builder() default Void.class;
  
  Class<? extends Converter> converter() default Converter.None.class;
  
  Class<? extends Converter> contentConverter() default Converter.None.class;
  
  Class<?> as() default Void.class;
  
  Class<?> keyAs() default Void.class;
  
  Class<?> contentAs() default Void.class;
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\annotation\JsonDeserialize.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */