/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SerializerFactoryConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   protected static final Serializers[] NO_SERIALIZERS = new Serializers[0];
/*    */   
/* 21 */   protected static final BeanSerializerModifier[] NO_MODIFIERS = new BeanSerializerModifier[0];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Serializers[] _additionalSerializers;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Serializers[] _additionalKeySerializers;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final BeanSerializerModifier[] _modifiers;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SerializerFactoryConfig() {
/* 42 */     this(null, null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SerializerFactoryConfig(Serializers[] allAdditionalSerializers, Serializers[] allAdditionalKeySerializers, BeanSerializerModifier[] modifiers) {
/* 49 */     this._additionalSerializers = (allAdditionalSerializers == null) ? NO_SERIALIZERS : allAdditionalSerializers;
/*    */     
/* 51 */     this._additionalKeySerializers = (allAdditionalKeySerializers == null) ? NO_SERIALIZERS : allAdditionalKeySerializers;
/*    */     
/* 53 */     this._modifiers = (modifiers == null) ? NO_MODIFIERS : modifiers;
/*    */   }
/*    */ 
/*    */   
/*    */   public SerializerFactoryConfig withAdditionalSerializers(Serializers additional) {
/* 58 */     if (additional == null) {
/* 59 */       throw new IllegalArgumentException("Cannot pass null Serializers");
/*    */     }
/* 61 */     Serializers[] all = (Serializers[])ArrayBuilders.insertInListNoDup((Object[])this._additionalSerializers, additional);
/* 62 */     return new SerializerFactoryConfig(all, this._additionalKeySerializers, this._modifiers);
/*    */   }
/*    */ 
/*    */   
/*    */   public SerializerFactoryConfig withAdditionalKeySerializers(Serializers additional) {
/* 67 */     if (additional == null) {
/* 68 */       throw new IllegalArgumentException("Cannot pass null Serializers");
/*    */     }
/* 70 */     Serializers[] all = (Serializers[])ArrayBuilders.insertInListNoDup((Object[])this._additionalKeySerializers, additional);
/* 71 */     return new SerializerFactoryConfig(this._additionalSerializers, all, this._modifiers);
/*    */   }
/*    */ 
/*    */   
/*    */   public SerializerFactoryConfig withSerializerModifier(BeanSerializerModifier modifier) {
/* 76 */     if (modifier == null) {
/* 77 */       throw new IllegalArgumentException("Cannot pass null modifier");
/*    */     }
/* 79 */     BeanSerializerModifier[] modifiers = (BeanSerializerModifier[])ArrayBuilders.insertInListNoDup((Object[])this._modifiers, modifier);
/* 80 */     return new SerializerFactoryConfig(this._additionalSerializers, this._additionalKeySerializers, modifiers);
/*    */   }
/*    */   
/* 83 */   public boolean hasSerializers() { return (this._additionalSerializers.length > 0); }
/* 84 */   public boolean hasKeySerializers() { return (this._additionalKeySerializers.length > 0); }
/* 85 */   public boolean hasSerializerModifiers() { return (this._modifiers.length > 0); }
/* 86 */   public Iterable<Serializers> serializers() { return (Iterable<Serializers>)new ArrayIterator((Object[])this._additionalSerializers); }
/* 87 */   public Iterable<Serializers> keySerializers() { return (Iterable<Serializers>)new ArrayIterator((Object[])this._additionalKeySerializers); } public Iterable<BeanSerializerModifier> serializerModifiers() {
/* 88 */     return (Iterable<BeanSerializerModifier>)new ArrayIterator((Object[])this._modifiers);
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\SerializerFactoryConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */