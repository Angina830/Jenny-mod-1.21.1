package software.bernie.shadowed.fasterxml.jackson.databind.jsonschema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import software.bernie.shadowed.fasterxml.jackson.annotation.JacksonAnnotation;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonSerializableSchema {
  public static final String NO_VALUE = "##irrelevant";
  
  String id() default "";
  
  String schemaType() default "any";
  
  @Deprecated
  String schemaObjectPropertiesDefinition() default "##irrelevant";
  
  @Deprecated
  String schemaItemDefinition() default "##irrelevant";
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\jsonschema\JsonSerializableSchema.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */