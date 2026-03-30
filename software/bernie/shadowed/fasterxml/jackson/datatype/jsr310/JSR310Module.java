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
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
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
/*     */ import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeWithZoneIdSerializer;
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
/*     */ @Deprecated
/*     */ public final class JSR310Module
/*     */   extends SimpleModule
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public JSR310Module() {
/* 118 */     super(PackageVersion.VERSION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     addDeserializer(Instant.class, (JsonDeserializer)InstantDeserializer.INSTANT);
/* 124 */     addDeserializer(OffsetDateTime.class, (JsonDeserializer)InstantDeserializer.OFFSET_DATE_TIME);
/* 125 */     addDeserializer(ZonedDateTime.class, (JsonDeserializer)InstantDeserializer.ZONED_DATE_TIME);
/*     */ 
/*     */     
/* 128 */     addDeserializer(Duration.class, (JsonDeserializer)DurationDeserializer.INSTANCE);
/* 129 */     addDeserializer(LocalDateTime.class, (JsonDeserializer)LocalDateTimeDeserializer.INSTANCE);
/* 130 */     addDeserializer(LocalDate.class, (JsonDeserializer)LocalDateDeserializer.INSTANCE);
/* 131 */     addDeserializer(LocalTime.class, (JsonDeserializer)LocalTimeDeserializer.INSTANCE);
/* 132 */     addDeserializer(MonthDay.class, (JsonDeserializer)MonthDayDeserializer.INSTANCE);
/* 133 */     addDeserializer(OffsetTime.class, (JsonDeserializer)OffsetTimeDeserializer.INSTANCE);
/* 134 */     addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
/* 135 */     addDeserializer(Year.class, (JsonDeserializer)YearDeserializer.INSTANCE);
/* 136 */     addDeserializer(YearMonth.class, (JsonDeserializer)YearMonthDeserializer.INSTANCE);
/* 137 */     addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
/* 138 */     addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
/*     */ 
/*     */     
/* 141 */     addSerializer(Duration.class, (JsonSerializer)DurationSerializer.INSTANCE);
/* 142 */     addSerializer(Instant.class, (JsonSerializer)InstantSerializer.INSTANCE);
/* 143 */     addSerializer(LocalDateTime.class, (JsonSerializer)LocalDateTimeSerializer.INSTANCE);
/* 144 */     addSerializer(LocalDate.class, (JsonSerializer)LocalDateSerializer.INSTANCE);
/* 145 */     addSerializer(LocalTime.class, (JsonSerializer)LocalTimeSerializer.INSTANCE);
/* 146 */     addSerializer(MonthDay.class, (JsonSerializer)MonthDaySerializer.INSTANCE);
/* 147 */     addSerializer(OffsetDateTime.class, (JsonSerializer)OffsetDateTimeSerializer.INSTANCE);
/* 148 */     addSerializer(OffsetTime.class, (JsonSerializer)OffsetTimeSerializer.INSTANCE);
/* 149 */     addSerializer(Period.class, (JsonSerializer)new ToStringSerializer(Period.class));
/* 150 */     addSerializer(Year.class, (JsonSerializer)YearSerializer.INSTANCE);
/* 151 */     addSerializer(YearMonth.class, (JsonSerializer)YearMonthSerializer.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     addSerializer(ZonedDateTime.class, _zonedWithZoneId());
/*     */     
/* 158 */     addSerializer(ZoneId.class, (JsonSerializer)new ToStringSerializer(ZoneId.class));
/*     */     
/* 160 */     addSerializer(ZoneOffset.class, (JsonSerializer)new ToStringSerializer(ZoneOffset.class));
/*     */ 
/*     */     
/* 163 */     addKeySerializer(ZonedDateTime.class, (JsonSerializer)ZonedDateTimeKeySerializer.INSTANCE);
/*     */ 
/*     */     
/* 166 */     addKeyDeserializer(Duration.class, (KeyDeserializer)DurationKeyDeserializer.INSTANCE);
/* 167 */     addKeyDeserializer(Instant.class, (KeyDeserializer)InstantKeyDeserializer.INSTANCE);
/* 168 */     addKeyDeserializer(LocalDateTime.class, (KeyDeserializer)LocalDateTimeKeyDeserializer.INSTANCE);
/* 169 */     addKeyDeserializer(LocalDate.class, (KeyDeserializer)LocalDateKeyDeserializer.INSTANCE);
/* 170 */     addKeyDeserializer(LocalTime.class, (KeyDeserializer)LocalTimeKeyDeserializer.INSTANCE);
/* 171 */     addKeyDeserializer(MonthDay.class, (KeyDeserializer)MonthDayKeyDeserializer.INSTANCE);
/* 172 */     addKeyDeserializer(OffsetDateTime.class, (KeyDeserializer)OffsetDateTimeKeyDeserializer.INSTANCE);
/* 173 */     addKeyDeserializer(OffsetTime.class, (KeyDeserializer)OffsetTimeKeyDeserializer.INSTANCE);
/* 174 */     addKeyDeserializer(Period.class, (KeyDeserializer)PeriodKeyDeserializer.INSTANCE);
/* 175 */     addKeyDeserializer(Year.class, (KeyDeserializer)YearKeyDeserializer.INSTANCE);
/* 176 */     addKeyDeserializer(YearMonth.class, (KeyDeserializer)YearMothKeyDeserializer.INSTANCE);
/* 177 */     addKeyDeserializer(ZonedDateTime.class, (KeyDeserializer)ZonedDateTimeKeyDeserializer.INSTANCE);
/* 178 */     addKeyDeserializer(ZoneId.class, (KeyDeserializer)ZoneIdKeyDeserializer.INSTANCE);
/* 179 */     addKeyDeserializer(ZoneOffset.class, (KeyDeserializer)ZoneOffsetKeyDeserializer.INSTANCE);
/*     */   }
/*     */   
/*     */   private static JsonSerializer<ZonedDateTime> _zonedWithZoneId() {
/* 183 */     return (JsonSerializer<ZonedDateTime>)ZonedDateTimeWithZoneIdSerializer.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupModule(Module.SetupContext context) {
/* 188 */     super.setupModule(context);
/* 189 */     context.addValueInstantiators((ValueInstantiators)new ValueInstantiators.Base()
/*     */         {
/*     */           
/*     */           public ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc, ValueInstantiator defaultInstantiator)
/*     */           {
/* 194 */             JavaType type = beanDesc.getType();
/* 195 */             Class<?> raw = type.getRawClass();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 200 */             if (ZoneId.class.isAssignableFrom(raw))
/*     */             {
/* 202 */               if (defaultInstantiator instanceof StdValueInstantiator) {
/* 203 */                 AnnotatedClass ac; StdValueInstantiator inst = (StdValueInstantiator)defaultInstantiator;
/*     */ 
/*     */                 
/* 206 */                 if (raw == ZoneId.class) {
/* 207 */                   ac = beanDesc.getClassInfo();
/*     */                 }
/*     */                 else {
/*     */                   
/* 211 */                   ac = AnnotatedClass.construct(config.constructType(ZoneId.class), (MapperConfig)config);
/*     */                 } 
/* 213 */                 if (!inst.canCreateFromString()) {
/* 214 */                   AnnotatedMethod factory = JSR310Module.this._findFactory(ac, "of", new Class[] { String.class });
/* 215 */                   if (factory != null) {
/* 216 */                     inst.configureFromStringCreator((AnnotatedWithParams)factory);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/* 223 */             return defaultInstantiator;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnnotatedMethod _findFactory(AnnotatedClass cls, String name, Class<?>... argTypes) {
/* 231 */     int argCount = argTypes.length;
/* 232 */     for (AnnotatedMethod method : cls.getStaticMethods()) {
/* 233 */       if (!name.equals(method.getName()) || method
/* 234 */         .getParameterCount() != argCount) {
/*     */         continue;
/*     */       }
/* 237 */       for (int i = 0; i < argCount; i++) {
/* 238 */         Class<?> argType = method.getParameter(i).getRawType();
/* 239 */         if (!argType.isAssignableFrom(argTypes[i]));
/*     */       } 
/*     */ 
/*     */       
/* 243 */       return method;
/*     */     } 
/* 245 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\datatype\jsr310\JSR310Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */