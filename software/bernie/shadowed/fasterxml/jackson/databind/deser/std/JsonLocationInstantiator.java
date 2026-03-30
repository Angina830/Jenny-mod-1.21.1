/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.CreatorProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsonLocationInstantiator
/*    */   extends ValueInstantiator.Base
/*    */ {
/*    */   public JsonLocationInstantiator() {
/* 22 */     super(JsonLocation.class);
/*    */   }
/*    */   
/*    */   public boolean canCreateFromObjectWith() {
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
/* 30 */     JavaType intType = config.constructType(int.class);
/* 31 */     JavaType longType = config.constructType(long.class);
/* 32 */     return new SettableBeanProperty[] { (SettableBeanProperty)creatorProp("sourceRef", config.constructType(Object.class), 0), (SettableBeanProperty)creatorProp("byteOffset", longType, 1), (SettableBeanProperty)creatorProp("charOffset", longType, 2), (SettableBeanProperty)creatorProp("lineNr", intType, 3), (SettableBeanProperty)creatorProp("columnNr", intType, 4) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static CreatorProperty creatorProp(String name, JavaType type, int index) {
/* 42 */     return new CreatorProperty(PropertyName.construct(name), type, null, null, null, null, index, null, PropertyMetadata.STD_REQUIRED);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) {
/* 48 */     return new JsonLocation(args[0], _long(args[1]), _long(args[2]), _int(args[3]), _int(args[4]));
/*    */   }
/*    */ 
/*    */   
/*    */   private static final long _long(Object o) {
/* 53 */     return (o == null) ? 0L : ((Number)o).longValue();
/*    */   }
/*    */   
/*    */   private static final int _int(Object o) {
/* 57 */     return (o == null) ? 0 : ((Number)o).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\deser\std\JsonLocationInstantiator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */