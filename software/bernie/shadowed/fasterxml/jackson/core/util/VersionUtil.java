/*     */ package software.bernie.shadowed.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Pattern;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Version;
/*     */ import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
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
/*     */ public class VersionUtil
/*     */ {
/*  28 */   private static final Pattern V_SEP = Pattern.compile("[-_./;:]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Version version() {
/*  39 */     return Version.unknownVersion();
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
/*     */   public static Version versionFor(Class<?> cls) {
/*  58 */     Version version = packageVersionFor(cls);
/*  59 */     return (version == null) ? Version.unknownVersion() : version;
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
/*     */   public static Version packageVersionFor(Class<?> cls) {
/*  71 */     Version v = null;
/*     */     try {
/*  73 */       String versionInfoClassName = cls.getPackage().getName() + ".PackageVersion";
/*  74 */       Class<?> vClass = Class.forName(versionInfoClassName, true, cls.getClassLoader());
/*     */       
/*     */       try {
/*  77 */         v = ((Versioned)vClass.newInstance()).version();
/*  78 */       } catch (Exception e) {
/*  79 */         throw new IllegalArgumentException("Failed to get Versioned out of " + vClass);
/*     */       } 
/*  81 */     } catch (Exception e) {}
/*     */ 
/*     */     
/*  84 */     return (v == null) ? Version.unknownVersion() : v;
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
/*     */   @Deprecated
/*     */   public static Version mavenVersionFor(ClassLoader cl, String groupId, String artifactId) {
/* 105 */     InputStream pomProperties = cl.getResourceAsStream("META-INF/maven/" + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/pom.properties");
/*     */     
/* 107 */     if (pomProperties != null) {
/*     */       try {
/* 109 */         Properties props = new Properties();
/* 110 */         props.load(pomProperties);
/* 111 */         String versionStr = props.getProperty("version");
/* 112 */         String pomPropertiesArtifactId = props.getProperty("artifactId");
/* 113 */         String pomPropertiesGroupId = props.getProperty("groupId");
/* 114 */         return parseVersion(versionStr, pomPropertiesGroupId, pomPropertiesArtifactId);
/* 115 */       } catch (IOException e) {
/*     */       
/*     */       } finally {
/* 118 */         _close(pomProperties);
/*     */       } 
/*     */     }
/* 121 */     return Version.unknownVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Version parseVersion(String s, String groupId, String artifactId) {
/* 129 */     if (s != null && (s = s.trim()).length() > 0) {
/* 130 */       String[] parts = V_SEP.split(s);
/* 131 */       return new Version(parseVersionPart(parts[0]), (parts.length > 1) ? parseVersionPart(parts[1]) : 0, (parts.length > 2) ? parseVersionPart(parts[2]) : 0, (parts.length > 3) ? parts[3] : null, groupId, artifactId);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return Version.unknownVersion();
/*     */   }
/*     */   
/*     */   protected static int parseVersionPart(String s) {
/* 141 */     int number = 0;
/* 142 */     for (int i = 0, len = s.length(); i < len; i++) {
/* 143 */       char c = s.charAt(i);
/* 144 */       if (c > '9' || c < '0')
/* 145 */         break;  number = number * 10 + c - 48;
/*     */     } 
/* 147 */     return number;
/*     */   }
/*     */   
/*     */   private static final void _close(Closeable c) {
/*     */     try {
/* 152 */       c.close();
/* 153 */     } catch (IOException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void throwInternal() {
/* 163 */     throw new RuntimeException("Internal error: this code path should never get executed");
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\cor\\util\VersionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */