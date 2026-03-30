/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdResolver;
/*    */ import software.bernie.shadowed.fasterxml.jackson.annotation.SimpleObjectIdResolver;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ClassUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectIdInfo
/*    */ {
/*    */   protected final PropertyName _propertyName;
/*    */   protected final Class<? extends ObjectIdGenerator<?>> _generator;
/*    */   protected final Class<? extends ObjectIdResolver> _resolver;
/*    */   protected final Class<?> _scope;
/*    */   protected final boolean _alwaysAsId;
/* 26 */   private static final ObjectIdInfo EMPTY = new ObjectIdInfo(PropertyName.NO_NAME, Object.class, null, false, null);
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectIdInfo(PropertyName name, Class<?> scope, Class<? extends ObjectIdGenerator<?>> gen, Class<? extends ObjectIdResolver> resolver) {
/* 31 */     this(name, scope, gen, false, resolver);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ObjectIdInfo(PropertyName prop, Class<?> scope, Class<? extends ObjectIdGenerator<?>> gen, boolean alwaysAsId) {
/* 37 */     this(prop, scope, gen, alwaysAsId, (Class)SimpleObjectIdResolver.class);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ObjectIdInfo(PropertyName prop, Class<?> scope, Class<? extends ObjectIdGenerator<?>> gen, boolean alwaysAsId, Class<? extends ObjectIdResolver> resolver) {
/*    */     Class<SimpleObjectIdResolver> clazz;
/* 44 */     this._propertyName = prop;
/* 45 */     this._scope = scope;
/* 46 */     this._generator = gen;
/* 47 */     this._alwaysAsId = alwaysAsId;
/* 48 */     if (resolver == null) {
/* 49 */       clazz = SimpleObjectIdResolver.class;
/*    */     }
/* 51 */     this._resolver = (Class)clazz;
/*    */   }
/*    */   
/*    */   public static ObjectIdInfo empty() {
/* 55 */     return EMPTY;
/*    */   }
/*    */   
/*    */   public ObjectIdInfo withAlwaysAsId(boolean state) {
/* 59 */     if (this._alwaysAsId == state) {
/* 60 */       return this;
/*    */     }
/* 62 */     return new ObjectIdInfo(this._propertyName, this._scope, this._generator, state, this._resolver);
/*    */   }
/*    */   
/* 65 */   public PropertyName getPropertyName() { return this._propertyName; }
/* 66 */   public Class<?> getScope() { return this._scope; }
/* 67 */   public Class<? extends ObjectIdGenerator<?>> getGeneratorType() { return this._generator; }
/* 68 */   public Class<? extends ObjectIdResolver> getResolverType() { return this._resolver; } public boolean getAlwaysAsId() {
/* 69 */     return this._alwaysAsId;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 73 */     return "ObjectIdInfo: propName=" + this._propertyName + ", scope=" + ClassUtil.nameOf(this._scope) + ", generatorType=" + ClassUtil.nameOf(this._generator) + ", alwaysAsId=" + this._alwaysAsId;
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\introspect\ObjectIdInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */