/*     */ package software.bernie.shadowed.eliotlash.mclib.math;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Operation
/*     */ {
/*  16 */   ADD("+", 1)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  19 */       return a + b;
/*     */     }
/*     */   },
/*  22 */   SUB("-", 1)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  25 */       return a - b;
/*     */     }
/*     */   },
/*  28 */   MUL("*", 2)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  31 */       return a * b;
/*     */     }
/*     */   },
/*  34 */   DIV("/", 2)
/*     */   {
/*     */     public double calculate(double a, double b)
/*     */     {
/*  38 */       return a / ((b == 0.0D) ? 1.0D : b);
/*     */     }
/*     */   },
/*  41 */   MOD("%", 2)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  44 */       return a % b;
/*     */     }
/*     */   },
/*  47 */   POW("^", 3)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  50 */       return Math.pow(a, b);
/*     */     }
/*     */   },
/*  53 */   AND("&&", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  56 */       return (a != 0.0D && b != 0.0D) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  59 */   OR("||", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  62 */       return (a != 0.0D || b != 0.0D) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  65 */   LESS("<", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  68 */       return (a < b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  71 */   LESS_THAN("<=", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  74 */       return (a <= b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  77 */   GREATER_THAN(">=", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  80 */       return (a >= b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  83 */   GREATER(">", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  86 */       return (a > b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  89 */   EQUALS("==", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  92 */       return null.equals(a, b) ? 1.0D : 0.0D;
/*     */     }
/*     */   },
/*  95 */   NOT_EQUALS("!=", 5)
/*     */   {
/*     */     public double calculate(double a, double b) {
/*  98 */       return !null.equals(a, b) ? 1.0D : 0.0D;
/*     */     } };
/*     */   
/*     */   static {
/* 102 */     OPERATORS = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     for (Operation op : values()) {
/* 110 */       OPERATORS.add(op.sign);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Set<String> OPERATORS;
/*     */   
/*     */   public final String sign;
/*     */   
/*     */   public final int value;
/*     */   
/*     */   public static boolean equals(double a, double b) {
/*     */     return (Math.abs(a - b) < 1.0E-5D);
/*     */   }
/*     */   
/*     */   Operation(String sign, int value) {
/* 126 */     this.sign = sign;
/* 127 */     this.value = value;
/*     */   }
/*     */   
/*     */   public abstract double calculate(double paramDouble1, double paramDouble2);
/*     */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\eliotlash\mclib\math\Operation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */