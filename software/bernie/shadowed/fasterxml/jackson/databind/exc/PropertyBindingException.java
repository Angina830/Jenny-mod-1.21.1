/*     */ package software.bernie.shadowed.fasterxml.jackson.databind.exc;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PropertyBindingException
/*     */   extends MismatchedInputException
/*     */ {
/*     */   protected final Class<?> _referringClass;
/*     */   protected final String _propertyName;
/*     */   protected final Collection<Object> _propertyIds;
/*     */   protected transient String _propertiesAsString;
/*     */   private static final int MAX_DESC_LENGTH = 1000;
/*     */   
/*     */   protected PropertyBindingException(JsonParser p, String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
/*  52 */     super(p, msg, loc);
/*  53 */     this._referringClass = referringClass;
/*  54 */     this._propertyName = propName;
/*  55 */     this._propertyIds = propertyIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected PropertyBindingException(String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
/*  66 */     this(null, msg, loc, referringClass, propName, propertyIds);
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
/*     */ 
/*     */   
/*     */   public String getMessageSuffix() {
/*  84 */     String suffix = this._propertiesAsString;
/*  85 */     if (suffix == null && this._propertyIds != null) {
/*  86 */       StringBuilder sb = new StringBuilder(100);
/*  87 */       int len = this._propertyIds.size();
/*  88 */       if (len == 1) {
/*  89 */         sb.append(" (one known property: \"");
/*  90 */         sb.append(String.valueOf(this._propertyIds.iterator().next()));
/*  91 */         sb.append('"');
/*     */       } else {
/*  93 */         sb.append(" (").append(len).append(" known properties: ");
/*  94 */         Iterator<Object> it = this._propertyIds.iterator();
/*  95 */         while (it.hasNext()) {
/*  96 */           sb.append('"');
/*  97 */           sb.append(String.valueOf(it.next()));
/*  98 */           sb.append('"');
/*     */           
/* 100 */           if (sb.length() > 1000) {
/* 101 */             sb.append(" [truncated]");
/*     */             break;
/*     */           } 
/* 104 */           if (it.hasNext()) {
/* 105 */             sb.append(", ");
/*     */           }
/*     */         } 
/*     */       } 
/* 109 */       sb.append("])");
/* 110 */       this._propertiesAsString = suffix = sb.toString();
/*     */     } 
/* 112 */     return suffix;
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
/*     */   public Class<?> getReferringClass() {
/* 126 */     return this._referringClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/* 135 */     return this._propertyName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Object> getKnownPropertyIds() {
/* 140 */     if (this._propertyIds == null) {
/* 141 */       return null;
/*     */     }
/* 143 */     return Collections.unmodifiableCollection(this._propertyIds);
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\exc\PropertyBindingException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */