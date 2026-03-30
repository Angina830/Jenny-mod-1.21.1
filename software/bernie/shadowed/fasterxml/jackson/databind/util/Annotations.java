package software.bernie.shadowed.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

public interface Annotations {
  <A extends Annotation> A get(Class<A> paramClass);
  
  boolean has(Class<?> paramClass);
  
  boolean hasOneOf(Class<? extends Annotation>[] paramArrayOfClass);
  
  int size();
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databin\\util\Annotations.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */