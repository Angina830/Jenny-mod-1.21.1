/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.AbstractTypeResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.Deserializers;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.KeyDeserializers;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.util.ArrayIterator;
/*     */ 
/*     */ public class DeserializerFactoryConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
/*  18 */   protected static final BeanDeserializerModifier[] NO_MODIFIERS = new BeanDeserializerModifier[0];
/*  19 */   protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
/*  20 */   protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   protected static final KeyDeserializers[] DEFAULT_KEY_DESERIALIZERS = new KeyDeserializers[] { (KeyDeserializers)new StdKeyDeserializers() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Deserializers[] _additionalDeserializers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final KeyDeserializers[] _additionalKeyDeserializers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final BeanDeserializerModifier[] _modifiers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractTypeResolver[] _abstractTypeResolvers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ValueInstantiators[] _valueInstantiators;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactoryConfig() {
/*  71 */     this(null, null, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DeserializerFactoryConfig(Deserializers[] allAdditionalDeserializers, KeyDeserializers[] allAdditionalKeyDeserializers, BeanDeserializerModifier[] modifiers, AbstractTypeResolver[] atr, ValueInstantiators[] vi) {
/*  84 */     this._additionalDeserializers = (allAdditionalDeserializers == null) ? NO_DESERIALIZERS : allAdditionalDeserializers;
/*     */     
/*  86 */     this._additionalKeyDeserializers = (allAdditionalKeyDeserializers == null) ? DEFAULT_KEY_DESERIALIZERS : allAdditionalKeyDeserializers;
/*     */     
/*  88 */     this._modifiers = (modifiers == null) ? NO_MODIFIERS : modifiers;
/*  89 */     this._abstractTypeResolvers = (atr == null) ? NO_ABSTRACT_TYPE_RESOLVERS : atr;
/*  90 */     this._valueInstantiators = (vi == null) ? NO_VALUE_INSTANTIATORS : vi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactoryConfig withAdditionalDeserializers(Deserializers additional) {
/* 101 */     if (additional == null) {
/* 102 */       throw new IllegalArgumentException("Cannot pass null Deserializers");
/*     */     }
/* 104 */     Deserializers[] all = (Deserializers[])ArrayBuilders.insertInListNoDup((Object[])this._additionalDeserializers, additional);
/* 105 */     return new DeserializerFactoryConfig(all, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactoryConfig withAdditionalKeyDeserializers(KeyDeserializers additional) {
/* 117 */     if (additional == null) {
/* 118 */       throw new IllegalArgumentException("Cannot pass null KeyDeserializers");
/*     */     }
/* 120 */     KeyDeserializers[] all = (KeyDeserializers[])ArrayBuilders.insertInListNoDup((Object[])this._additionalKeyDeserializers, additional);
/* 121 */     return new DeserializerFactoryConfig(this._additionalDeserializers, all, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactoryConfig withDeserializerModifier(BeanDeserializerModifier modifier) {
/* 133 */     if (modifier == null) {
/* 134 */       throw new IllegalArgumentException("Cannot pass null modifier");
/*     */     }
/* 136 */     BeanDeserializerModifier[] all = (BeanDeserializerModifier[])ArrayBuilders.insertInListNoDup((Object[])this._modifiers, modifier);
/* 137 */     return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, all, this._abstractTypeResolvers, this._valueInstantiators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactoryConfig withAbstractTypeResolver(AbstractTypeResolver resolver) {
/* 150 */     if (resolver == null) {
/* 151 */       throw new IllegalArgumentException("Cannot pass null resolver");
/*     */     }
/* 153 */     AbstractTypeResolver[] all = (AbstractTypeResolver[])ArrayBuilders.insertInListNoDup((Object[])this._abstractTypeResolvers, resolver);
/* 154 */     return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, all, this._valueInstantiators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeserializerFactoryConfig withValueInstantiators(ValueInstantiators instantiators) {
/* 170 */     if (instantiators == null) {
/* 171 */       throw new IllegalArgumentException("Cannot pass null resolver");
/*     */     }
/* 173 */     ValueInstantiators[] all = (ValueInstantiators[])ArrayBuilders.insertInListNoDup((Object[])this._valueInstantiators, instantiators);
/* 174 */     return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, all);
/*     */   }
/*     */   
/*     */   public boolean hasDeserializers() {
/* 178 */     return (this._additionalDeserializers.length > 0);
/*     */   } public boolean hasKeyDeserializers() {
/* 180 */     return (this._additionalKeyDeserializers.length > 0);
/*     */   } public boolean hasDeserializerModifiers() {
/* 182 */     return (this._modifiers.length > 0);
/*     */   } public boolean hasAbstractTypeResolvers() {
/* 184 */     return (this._abstractTypeResolvers.length > 0);
/*     */   } public boolean hasValueInstantiators() {
/* 186 */     return (this._valueInstantiators.length > 0);
/*     */   }
/*     */   public Iterable<Deserializers> deserializers() {
/* 189 */     return (Iterable<Deserializers>)new ArrayIterator((Object[])this._additionalDeserializers);
/*     */   }
/*     */   
/*     */   public Iterable<KeyDeserializers> keyDeserializers() {
/* 193 */     return (Iterable<KeyDeserializers>)new ArrayIterator((Object[])this._additionalKeyDeserializers);
/*     */   }
/*     */   
/*     */   public Iterable<BeanDeserializerModifier> deserializerModifiers() {
/* 197 */     return (Iterable<BeanDeserializerModifier>)new ArrayIterator((Object[])this._modifiers);
/*     */   }
/*     */   
/*     */   public Iterable<AbstractTypeResolver> abstractTypeResolvers() {
/* 201 */     return (Iterable<AbstractTypeResolver>)new ArrayIterator((Object[])this._abstractTypeResolvers);
/*     */   }
/*     */   
/*     */   public Iterable<ValueInstantiators> valueInstantiators() {
/* 205 */     return (Iterable<ValueInstantiators>)new ArrayIterator((Object[])this._valueInstantiators);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\cfg\DeserializerFactoryConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */