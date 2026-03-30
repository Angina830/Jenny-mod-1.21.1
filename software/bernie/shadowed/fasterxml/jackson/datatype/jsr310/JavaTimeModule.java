/*     */ package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310;
/*     */ 
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.LocalTime;
/*     */ import java.time.MonthDay;
/*     */ import java.time.OffsetDateTime;
/*     */ import java.time.OffsetTime;
/*     */ import java.time.Period;
/*     */ import java.time.Year;
/*     */ import java.time.YearMonth;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.ZonedDateTime;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.Module;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.module.SimpleModule;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.DurationKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalTimeKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.MonthDayKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.PeriodKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.YearKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.YearMothKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZoneIdKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZoneOffsetKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.MonthDaySerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;
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
/*     */ public final class JavaTimeModule
/*     */   extends SimpleModule
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public JavaTimeModule() {
/* 134 */     super(PackageVersion.VERSION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     addDeserializer(Instant.class, (JsonDeserializer)InstantDeserializer.INSTANT);
/* 140 */     addDeserializer(OffsetDateTime.class, (JsonDeserializer)InstantDeserializer.OFFSET_DATE_TIME);
/* 141 */     addDeserializer(ZonedDateTime.class, (JsonDeserializer)InstantDeserializer.ZONED_DATE_TIME);
/*     */ 
/*     */     
/* 144 */     addDeserializer(Duration.class, (JsonDeserializer)DurationDeserializer.INSTANCE);
/* 145 */     addDeserializer(LocalDateTime.class, (JsonDeserializer)LocalDateTimeDeserializer.INSTANCE);
/* 146 */     addDeserializer(LocalDate.class, (JsonDeserializer)LocalDateDeserializer.INSTANCE);
/* 147 */     addDeserializer(LocalTime.class, (JsonDeserializer)LocalTimeDeserializer.INSTANCE);
/* 148 */     addDeserializer(MonthDay.class, (JsonDeserializer)MonthDayDeserializer.INSTANCE);
/* 149 */     addDeserializer(OffsetTime.class, (JsonDeserializer)OffsetTimeDeserializer.INSTANCE);
/* 150 */     addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
/* 151 */     addDeserializer(Year.class, (JsonDeserializer)YearDeserializer.INSTANCE);
/* 152 */     addDeserializer(YearMonth.class, (JsonDeserializer)YearMonthDeserializer.INSTANCE);
/* 153 */     addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
/* 154 */     addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
/*     */ 
/*     */ 
/*     */     
/* 158 */     addSerializer(Duration.class, (JsonSerializer)DurationSerializer.INSTANCE);
/* 159 */     addSerializer(Instant.class, (JsonSerializer)InstantSerializer.INSTANCE);
/* 160 */     addSerializer(LocalDateTime.class, (JsonSerializer)LocalDateTimeSerializer.INSTANCE);
/* 161 */     addSerializer(LocalDate.class, (JsonSerializer)LocalDateSerializer.INSTANCE);
/* 162 */     addSerializer(LocalTime.class, (JsonSerializer)LocalTimeSerializer.INSTANCE);
/* 163 */     addSerializer(MonthDay.class, (JsonSerializer)MonthDaySerializer.INSTANCE);
/* 164 */     addSerializer(OffsetDateTime.class, (JsonSerializer)OffsetDateTimeSerializer.INSTANCE);
/* 165 */     addSerializer(OffsetTime.class, (JsonSerializer)OffsetTimeSerializer.INSTANCE);
/* 166 */     addSerializer(Period.class, (JsonSerializer)new ToStringSerializer(Period.class));
/* 167 */     addSerializer(Year.class, (JsonSerializer)YearSerializer.INSTANCE);
/* 168 */     addSerializer(YearMonth.class, (JsonSerializer)YearMonthSerializer.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     addSerializer(ZonedDateTime.class, (JsonSerializer)ZonedDateTimeSerializer.INSTANCE);
/*     */ 
/*     */     
/* 178 */     addSerializer(ZoneId.class, (JsonSerializer)new ToStringSerializer(ZoneId.class));
/*     */     
/* 180 */     addSerializer(ZoneOffset.class, (JsonSerializer)new ToStringSerializer(ZoneOffset.class));
/*     */ 
/*     */     
/* 183 */     addKeySerializer(ZonedDateTime.class, (JsonSerializer)ZonedDateTimeKeySerializer.INSTANCE);
/*     */ 
/*     */     
/* 186 */     addKeyDeserializer(Duration.class, (KeyDeserializer)DurationKeyDeserializer.INSTANCE);
/* 187 */     addKeyDeserializer(Instant.class, (KeyDeserializer)InstantKeyDeserializer.INSTANCE);
/* 188 */     addKeyDeserializer(LocalDateTime.class, (KeyDeserializer)LocalDateTimeKeyDeserializer.INSTANCE);
/* 189 */     addKeyDeserializer(LocalDate.class, (KeyDeserializer)LocalDateKeyDeserializer.INSTANCE);
/* 190 */     addKeyDeserializer(LocalTime.class, (KeyDeserializer)LocalTimeKeyDeserializer.INSTANCE);
/* 191 */     addKeyDeserializer(MonthDay.class, (KeyDeserializer)MonthDayKeyDeserializer.INSTANCE);
/* 192 */     addKeyDeserializer(OffsetDateTime.class, (KeyDeserializer)OffsetDateTimeKeyDeserializer.INSTANCE);
/* 193 */     addKeyDeserializer(OffsetTime.class, (KeyDeserializer)OffsetTimeKeyDeserializer.INSTANCE);
/* 194 */     addKeyDeserializer(Period.class, (KeyDeserializer)PeriodKeyDeserializer.INSTANCE);
/* 195 */     addKeyDeserializer(Year.class, (KeyDeserializer)YearKeyDeserializer.INSTANCE);
/* 196 */     addKeyDeserializer(YearMonth.class, (KeyDeserializer)YearMothKeyDeserializer.INSTANCE);
/* 197 */     addKeyDeserializer(ZonedDateTime.class, (KeyDeserializer)ZonedDateTimeKeyDeserializer.INSTANCE);
/* 198 */     addKeyDeserializer(ZoneId.class, (KeyDeserializer)ZoneIdKeyDeserializer.INSTANCE);
/* 199 */     addKeyDeserializer(ZoneOffset.class, (KeyDeserializer)ZoneOffsetKeyDeserializer.INSTANCE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupModule(Module.SetupContext context) {
/* 204 */     super.setupModule(context);
/* 205 */     context.addValueInstantiators((ValueInstantiators)new ValueInstantiators.Base()
/*     */         {
/*     */           
/*     */           public ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator)
/*     */           {
/* 210 */             JavaType type = beanDesc.getType();
/* 211 */             Class<?> raw = type.getRawClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 217 */             if (ZoneId.class.isAssignableFrom(raw))
/*     */             {
/* 219 */               if (defaultInstantiator instanceof StdValueInstantiator) {
/* 220 */                 AnnotatedClass ac; StdValueInstantiator inst = (StdValueInstantiator)defaultInstantiator;
/*     */ 
/*     */                 
/* 223 */                 if (raw == ZoneId.class) {
/* 224 */                   ac = beanDesc.getClassInfo();
/*     */                 }
/*     */                 else {
/*     */                   
/* 228 */                   ac = AnnotatedClassResolver.resolve((MapperConfig)config, config
/* 229 */                       .constructType(ZoneId.class), (ClassIntrospector.MixInResolver)config);
/*     */                 } 
/* 231 */                 if (!inst.canCreateFromString()) {
/* 232 */                   AnnotatedMethod factory = JavaTimeModule.this._findFactory(ac, "of", new Class[] { String.class });
/* 233 */                   if (factory != null) {
/* 234 */                     inst.configureFromStringCreator((AnnotatedWithParams)factory);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/* 241 */             return defaultInstantiator;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMethod _findFactory(AnnotatedClass cls, String name, Class<?>... argTypes) {
/* 249 */     int argCount = argTypes.length;
/* 250 */     for (AnnotatedMethod method : cls.getFactoryMethods()) {
/* 251 */       if (!name.equals(method.getName()) || method
/* 252 */         .getParameterCount() != argCount) {
/*     */         continue;
/*     */       }
/* 255 */       for (int i = 0; i < argCount; i++) {
/* 256 */         Class<?> argType = method.getParameter(i).getRawType();
/* 257 */         if (!argType.isAssignableFrom(argTypes[i]));
/*     */       } 
/*     */ 
/*     */       
/* 261 */       return method;
/*     */     } 
/* 263 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\JavaTimeModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */