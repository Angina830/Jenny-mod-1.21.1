/*     */ package software.bernie.shadowed.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version
/*     */   implements Comparable<Version>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  21 */   private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _majorVersion;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int _minorVersion;
/*     */ 
/*     */   
/*     */   protected final int _patchLevel;
/*     */ 
/*     */   
/*     */   protected final String _groupId;
/*     */ 
/*     */   
/*     */   protected final String _artifactId;
/*     */ 
/*     */   
/*     */   protected final String _snapshotInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Version(int major, int minor, int patchLevel, String snapshotInfo) {
/*  47 */     this(major, minor, patchLevel, snapshotInfo, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Version(int major, int minor, int patchLevel, String snapshotInfo, String groupId, String artifactId) {
/*  53 */     this._majorVersion = major;
/*  54 */     this._minorVersion = minor;
/*  55 */     this._patchLevel = patchLevel;
/*  56 */     this._snapshotInfo = snapshotInfo;
/*  57 */     this._groupId = (groupId == null) ? "" : groupId;
/*  58 */     this._artifactId = (artifactId == null) ? "" : artifactId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Version unknownVersion() {
/*  65 */     return UNKNOWN_VERSION;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnknownVersion() {
/*  70 */     return (this == UNKNOWN_VERSION);
/*     */   } public boolean isSnapshot() {
/*  72 */     return (this._snapshotInfo != null && this._snapshotInfo.length() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isUknownVersion() {
/*  78 */     return isUnknownVersion();
/*     */   }
/*  80 */   public int getMajorVersion() { return this._majorVersion; }
/*  81 */   public int getMinorVersion() { return this._minorVersion; } public int getPatchLevel() {
/*  82 */     return this._patchLevel;
/*     */   }
/*  84 */   public String getGroupId() { return this._groupId; } public String getArtifactId() {
/*  85 */     return this._artifactId;
/*     */   }
/*     */   public String toFullString() {
/*  88 */     return this._groupId + '/' + this._artifactId + '/' + toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  92 */     StringBuilder sb = new StringBuilder();
/*  93 */     sb.append(this._majorVersion).append('.');
/*  94 */     sb.append(this._minorVersion).append('.');
/*  95 */     sb.append(this._patchLevel);
/*  96 */     if (isSnapshot()) {
/*  97 */       sb.append('-').append(this._snapshotInfo);
/*     */     }
/*  99 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 103 */     return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 109 */     if (o == this) return true; 
/* 110 */     if (o == null) return false; 
/* 111 */     if (o.getClass() != getClass()) return false; 
/* 112 */     Version other = (Version)o;
/* 113 */     return (other._majorVersion == this._majorVersion && other._minorVersion == this._minorVersion && other._patchLevel == this._patchLevel && other._artifactId.equals(this._artifactId) && other._groupId.equals(this._groupId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Version other) {
/* 124 */     if (other == this) return 0;
/*     */     
/* 126 */     int diff = this._groupId.compareTo(other._groupId);
/* 127 */     if (diff == 0) {
/* 128 */       diff = this._artifactId.compareTo(other._artifactId);
/* 129 */       if (diff == 0) {
/* 130 */         diff = this._majorVersion - other._majorVersion;
/* 131 */         if (diff == 0) {
/* 132 */           diff = this._minorVersion - other._minorVersion;
/* 133 */           if (diff == 0) {
/* 134 */             diff = this._patchLevel - other._patchLevel;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 139 */     return diff;
/*     */   }
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\core\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */