/*     */ package software.bernie.shadowed.fasterxml.jackson.databind;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
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
/*     */ public class PropertyNamingStrategy
/*     */   implements Serializable
/*     */ {
/*  40 */   public static final PropertyNamingStrategy SNAKE_CASE = new SnakeCaseStrategy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final PropertyNamingStrategy UPPER_CAMEL_CASE = new UpperCamelCaseStrategy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final PropertyNamingStrategy LOWER_CAMEL_CASE = new PropertyNamingStrategy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final PropertyNamingStrategy LOWER_CASE = new LowerCaseStrategy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final PropertyNamingStrategy KEBAB_CASE = new KebabCaseStrategy();
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
/*     */   public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
/* 101 */     return defaultName;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
/* 122 */     return defaultName;
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
/*     */ 
/*     */   
/*     */   public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
/* 142 */     return defaultName;
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
/*     */   public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
/* 160 */     return defaultName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class PropertyNamingStrategyBase
/*     */     extends PropertyNamingStrategy
/*     */   {
/*     */     public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
/* 174 */       return translate(defaultName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
/* 180 */       return translate(defaultName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
/* 186 */       return translate(defaultName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
/* 193 */       return translate(defaultName);
/*     */     }
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
/*     */     public abstract String translate(String param1String);
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
/*     */   public static class SnakeCaseStrategy
/*     */     extends PropertyNamingStrategyBase
/*     */   {
/*     */     public String translate(String input) {
/* 262 */       if (input == null) return input; 
/* 263 */       int length = input.length();
/* 264 */       StringBuilder result = new StringBuilder(length * 2);
/* 265 */       int resultLength = 0;
/* 266 */       boolean wasPrevTranslated = false;
/* 267 */       for (int i = 0; i < length; i++) {
/*     */         
/* 269 */         char c = input.charAt(i);
/* 270 */         if (i > 0 || c != '_') {
/*     */           
/* 272 */           if (Character.isUpperCase(c)) {
/*     */             
/* 274 */             if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
/*     */               
/* 276 */               result.append('_');
/* 277 */               resultLength++;
/*     */             } 
/* 279 */             c = Character.toLowerCase(c);
/* 280 */             wasPrevTranslated = true;
/*     */           }
/*     */           else {
/*     */             
/* 284 */             wasPrevTranslated = false;
/*     */           } 
/* 286 */           result.append(c);
/* 287 */           resultLength++;
/*     */         } 
/*     */       } 
/* 290 */       return (resultLength > 0) ? result.toString() : input;
/*     */     }
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
/*     */   public static class UpperCamelCaseStrategy
/*     */     extends PropertyNamingStrategyBase
/*     */   {
/*     */     public String translate(String input) {
/* 322 */       if (input == null || input.length() == 0) {
/* 323 */         return input;
/*     */       }
/*     */       
/* 326 */       char c = input.charAt(0);
/* 327 */       char uc = Character.toUpperCase(c);
/* 328 */       if (c == uc) {
/* 329 */         return input;
/*     */       }
/* 331 */       StringBuilder sb = new StringBuilder(input);
/* 332 */       sb.setCharAt(0, uc);
/* 333 */       return sb.toString();
/*     */     }
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
/*     */   public static class LowerCaseStrategy
/*     */     extends PropertyNamingStrategyBase
/*     */   {
/*     */     public String translate(String input) {
/* 349 */       return input.toLowerCase();
/*     */     }
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
/*     */   public static class KebabCaseStrategy
/*     */     extends PropertyNamingStrategyBase
/*     */   {
/*     */     public String translate(String input) {
/* 365 */       if (input == null) return input; 
/* 366 */       int length = input.length();
/* 367 */       if (length == 0) {
/* 368 */         return input;
/*     */       }
/*     */       
/* 371 */       StringBuilder result = new StringBuilder(length + (length >> 1));
/*     */       
/* 373 */       int upperCount = 0;
/*     */       
/* 375 */       for (int i = 0; i < length; i++) {
/* 376 */         char ch = input.charAt(i);
/* 377 */         char lc = Character.toLowerCase(ch);
/*     */         
/* 379 */         if (lc == ch) {
/*     */ 
/*     */           
/* 382 */           if (upperCount > 1)
/*     */           {
/* 384 */             result.insert(result.length() - 1, '-');
/*     */           }
/* 386 */           upperCount = 0;
/*     */         } else {
/*     */           
/* 389 */           if (upperCount == 0 && i > 0) {
/* 390 */             result.append('-');
/*     */           }
/* 392 */           upperCount++;
/*     */         } 
/* 394 */         result.append(lc);
/*     */       } 
/* 396 */       return result.toString();
/*     */     }
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
/*     */   @Deprecated
/* 410 */   public static final PropertyNamingStrategy CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES = SNAKE_CASE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 416 */   public static final PropertyNamingStrategy PASCAL_CASE_TO_CAMEL_CASE = UPPER_CAMEL_CASE;
/*     */   
/*     */   @Deprecated
/*     */   public static class LowerCaseWithUnderscoresStrategy extends SnakeCaseStrategy {}
/*     */   
/*     */   @Deprecated
/*     */   public static class PascalCaseStrategy extends UpperCamelCaseStrategy {}
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\PropertyNamingStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */