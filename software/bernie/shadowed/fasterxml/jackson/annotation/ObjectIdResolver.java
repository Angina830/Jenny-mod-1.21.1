package software.bernie.shadowed.fasterxml.jackson.annotation;

public interface ObjectIdResolver {
  void bindItem(ObjectIdGenerator.IdKey paramIdKey, Object paramObject);
  
  Object resolveId(ObjectIdGenerator.IdKey paramIdKey);
  
  ObjectIdResolver newForDeserialization(Object paramObject);
  
  boolean canUseFor(ObjectIdResolver paramObjectIdResolver);
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\annotation\ObjectIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */