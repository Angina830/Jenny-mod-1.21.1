package com.schnurritv.sexmod;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class u extends GuiScreen {
  public static final ResourceLocation f = new ResourceLocation("sexmod", "textures/gui/clothing_icons.png");
  
  static final int a = 20;
  
  static final float m = 0.25F;
  
  int i = 0;
  
  int g = 0;
  
  float k = 0.0F;
  
  public static float p = 0.0F;
  
  protected static List<Integer> j = new ArrayList<>();
  
  protected static int h = 0;
  
  protected static int d = 0;
  
  Q q;
  
  boolean l = false;
  
  G c;
  
  public static List<Map.Entry<y, Map.Entry<List<String>, Integer>>> o = new ArrayList<>();
  
  int b;
  
  int n = 0;
  
  int e = 1;
  
  public u(n paramn) {
    if (paramn == null)
      paramn = n.JENNY; 
    try {
      Constructor<? extends Q> constructor = paramn.npcClass.getConstructor(new Class[] { World.class });
      this.q = constructor.newInstance(new Object[] { this.field_146297_k.field_71441_e });
      this.q.y = true;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    d();
  }
  
  public u(U paramU) {
    this(n.a(paramU));
    if (paramU == null)
      return; 
    String str = paramU.s();
    this.q.func_184212_Q().func_187227_b(Q.i, str);
    byte b = 0;
    for (String str1 : this.q.I()) {
      y y = b_.f(str1);
      try {
        if (y.CUSTOM_BONE.equals(y))
          b++; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      Map.Entry<y, Map.Entry<List<String>, Integer>> entry = null;
      try {
        if (y.CUSTOM_BONE.equals(y) && b > 1) {
          entry = a(this.q);
        } else {
          for (Map.Entry<y, Map.Entry<List<String>, Integer>> entry1 : o) {
            if (((y)entry1.getKey()).equals(y))
              entry = entry1; 
          } 
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      try {
        if (entry == null)
          continue; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      o.remove(entry);
      int i = ((List)((Map.Entry)entry.getValue()).getKey()).indexOf(str1);
      if (i == -1)
        i = 0; 
      ((Map.Entry)entry.getValue()).setValue(Integer.valueOf(i));
      o.add(entry);
    } 
  }
  
  public void func_146274_d() throws IOException {
    super.func_146274_d();
    this.c.func_178039_p();
  }
  
  public static HashSet<String> c() {
    HashSet<String> hashSet = new HashSet();
    for (Map.Entry<y, Map.Entry<List<String>, Integer>> entry : o) {
      try {
        if (((List)((Map.Entry)entry.getValue()).getKey()).size() == 1)
          continue; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      Map.Entry entry1 = (Map.Entry)entry.getValue();
      List list = (List)entry1.getKey();
      Integer integer = (Integer)entry1.getValue();
      hashSet.add(list.get(integer.intValue()));
    } 
    return hashSet;
  }
  
  public static Map.Entry<y, Map.Entry<List<String>, Integer>> a(Q paramQ) {
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("cross");
    arrayList.addAll(b_.a(paramQ).get(y.CUSTOM_BONE));
    return new AbstractMap.SimpleEntry<>(y.CUSTOM_BONE, new AbstractMap.SimpleEntry<>(arrayList, Integer.valueOf(0)));
  }
  
  void d() {
    o.clear();
    for (y y : y.values()) {
      ArrayList<String> arrayList = new ArrayList();
      arrayList.add("cross");
      o.add(new AbstractMap.SimpleEntry<>(y, new AbstractMap.SimpleEntry<>(arrayList, Integer.valueOf(0))));
    } 
    for (Map.Entry<y, List<String>> entry : b_.a(this.q).entrySet()) {
      Map.Entry<y, Map.Entry<List<String>, Integer>> entry1 = null;
      for (Map.Entry<y, Map.Entry<List<String>, Integer>> entry2 : o) {
        if (((y)entry.getKey()).equals(entry2.getKey()))
          entry1 = entry2; 
      } 
      try {
        if (entry1 == null)
          continue; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      o.remove(entry1);
      ((List)((Map.Entry)entry1.getValue()).getKey()).addAll((Collection)entry.getValue());
      o.add(entry1);
    } 
  }
  
  public void func_73866_w_() {
    this.c = new G(this.field_146297_k, this);
  }
  
  public void func_146280_a(Minecraft paramMinecraft, int paramInt1, int paramInt2) {
    super.func_146280_a(paramMinecraft, paramInt1, paramInt2);
    this.i = b(76.0F);
    this.g = a(89.0F);
    this.k = 90.0F;
  }
  
  boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    try {
      if (paramInt1 < paramInt3)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramInt1 > paramInt5)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramInt2 < paramInt4)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramInt2 > paramInt6)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    return true;
  }
  
  public void func_73863_a(int paramInt1, int paramInt2, float paramFloat) {
    try {
      super.func_73863_a(paramInt1, paramInt2, paramFloat);
      if (this.l)
        p += bZ.a(d, h, paramFloat); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    a();
    this.field_146297_k.field_71446_o.func_110577_a(f);
    int i = this.i - b(10.0F);
    int j = this.g - 20;
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    func_73729_b(i, j, 100, a(paramInt1, paramInt2, i, j, i + 20, j + 20) ? 40 : 20, 20, 20);
    int k = this.g - 40;
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    func_73729_b(i, k, 120, a(paramInt1, paramInt2, i, k, i + 20, k + 20) ? 40 : 20, 20, 20);
    a(this.i, this.g, this.k, (EntityLivingBase)this.q, 1.2345679F);
    this.q.func_70071_h_();
    this.c.func_148128_a(paramInt1, paramInt2, paramFloat);
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  void b() {
    this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
    HashSet<String> hashSet = new HashSet();
    for (Map.Entry<y, Map.Entry<List<String>, Integer>> entry : o) {
      Map.Entry entry1 = (Map.Entry)entry.getValue();
      Integer integer = (Integer)entry1.getValue();
      try {
        if (integer.intValue() == 0)
          continue; 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      String str = ((List<String>)entry1.getKey()).get(integer.intValue());
      hashSet.add(str);
    } 
    bn.a.sendToServer(new bU(Q.a(hashSet)));
    this.field_146297_k.field_71439_g.func_71053_j();
  }
  
  public void a(y paramy, boolean paramBoolean, int paramInt) {
    Map.Entry<y, Map.Entry<List<String>, Integer>> entry;
    int i;
    int m;
    this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
    ArrayList<Map.Entry> arrayList = new ArrayList();
    ArrayList<Integer> arrayList1 = new ArrayList();
    byte b = 0;
    for (Map.Entry<y, Map.Entry<List<String>, Integer>> entry2 : o) {
      try {
        if (((y)entry2.getKey()).equals(paramy)) {
          arrayList.add(entry2);
          arrayList1.add(Integer.valueOf(b));
        } 
      } catch (NullPointerException nullPointerException) {
        throw a(null);
      } 
      b++;
    } 
    try {
      if (arrayList.size() == 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    if (arrayList.size() == 1) {
      entry = arrayList.get(0);
      i = ((Integer)arrayList1.get(0)).intValue();
    } else {
      int n = paramInt - (y.values()).length + 1;
      entry = arrayList.get(n);
      i = ((Integer)arrayList1.get(n)).intValue();
    } 
    try {
      if (entry == null)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    o.remove(i);
    Map.Entry entry1 = (Map.Entry)entry.getValue();
    int j = ((Integer)entry1.getValue()).intValue();
    int k = ((List)entry1.getKey()).size();
    if (paramBoolean) {
      m = j + 1;
      if (m >= k)
        m = 0; 
    } else {
      m = j - 1;
      if (m < 0)
        m = k - 1; 
    } 
    entry1.setValue(Integer.valueOf(m));
    entry.setValue(entry1);
    o.add(i, entry);
  }
  
  public void a(int paramInt1, int paramInt2, float paramFloat, bI parambI) {
    a(paramInt1, paramInt2, paramFloat, parambI, 1.876945F);
  }
  
  public void a(bI parambI) {
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    a(this.i, this.g, this.k, parambI, 2.876945F, parambI.d ? 1 : 0, true);
  }
  
  public void a(String paramString, int paramInt1, int paramInt2) {
    func_146279_a(paramString, paramInt1, paramInt2);
  }
  
  protected void func_146273_a(int paramInt1, int paramInt2, int paramInt3, long paramLong) {
    try {
      super.func_146273_a(paramInt1, paramInt2, paramInt3, paramLong);
      if (paramInt3 != 0)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    int i = paramInt1 - this.b;
    j.add(Integer.valueOf(i));
    this.b = paramInt1;
  }
  
  protected void func_73864_a(int paramInt1, int paramInt2, int paramInt3) throws IOException {
    try {
      super.func_73864_a(paramInt1, paramInt2, paramInt3);
      this.c.func_148179_a(paramInt1, paramInt2, paramInt3);
      if (paramInt3 != 0)
        return; 
    } catch (IOException iOException) {
      throw a(null);
    } 
    this.l = true;
    this.b = paramInt1;
    int i = this.i - b(10.0F);
    int j = this.g - 20;
    try {
      if (a(paramInt1, paramInt2, i, j, i + 20, j + 20))
        b(); 
    } catch (IOException iOException) {
      throw a(null);
    } 
    int k = this.g - 40;
    if (a(paramInt1, paramInt2, i, k, i + 20, k + 20)) {
      this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
      this.field_146297_k.field_71439_g.func_71053_j();
      int m = b_.b();
      try {
        if (m == 0) {
          this.field_146297_k.func_152344_a(() -> this.field_146297_k.func_147108_a(new u(U.b(this.field_146297_k.field_71439_g.getPersistentID()))));
        } else {
          b_.c = true;
        } 
      } catch (IOException iOException) {
        throw a(null);
      } 
    } 
  }
  
  protected void func_146286_b(int paramInt1, int paramInt2, int paramInt3) {
    try {
      super.func_146286_b(paramInt1, paramInt2, paramInt3);
      if (paramInt3 == 0)
        this.l = false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.n = d;
  }
  
  int b(float paramFloat) {
    return Math.round(this.field_146294_l * paramFloat / 100.0F);
  }
  
  int a(float paramFloat) {
    return Math.round(this.field_146295_m * paramFloat / 100.0F);
  }
  
  public void func_146281_b() {
    super.func_146281_b();
    this.q.field_70170_p.func_72973_f((Entity)this.q);
    j.clear();
    o.clear();
  }
  
  public Q e() {
    return this.q;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.field_146297_k.field_71446_o.func_110577_a(f);
    func_73729_b(paramInt1, paramInt2, paramInt3, paramInt4, 20, 20);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3) {
    a(paramInt1, paramInt2, paramInt3, 0);
  }
  
  void a(int paramInt1, int paramInt2, float paramFloat1, EntityLivingBase paramEntityLivingBase, float paramFloat2) {
    a(paramInt1, paramInt2, paramFloat1, paramEntityLivingBase, paramFloat2, 0, true);
  }
  
  void a(int paramInt1, int paramInt2, float paramFloat1, EntityLivingBase paramEntityLivingBase, float paramFloat2, int paramInt3, boolean paramBoolean) {
    float f1 = paramEntityLivingBase.field_70761_aq;
    float f2 = paramEntityLivingBase.field_70177_z;
    float f3 = paramEntityLivingBase.field_70125_A;
    float f4 = paramEntityLivingBase.field_70758_at;
    float f5 = paramEntityLivingBase.field_70759_as;
    try {
      paramEntityLivingBase.field_70761_aq = 0.0F;
      paramEntityLivingBase.field_70177_z = 0.0F;
      paramEntityLivingBase.field_70125_A = 0.0F;
      paramEntityLivingBase.field_70758_at = 0.0F;
      paramEntityLivingBase.field_70759_as = 0.0F;
      GlStateManager.func_179142_g();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(paramInt1, paramInt2, 50.0F);
      GlStateManager.func_179152_a(-paramFloat1, paramFloat1, paramFloat1);
      GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GlStateManager.func_179114_b(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, 0.0F, paramInt3);
      if (paramBoolean)
        GlStateManager.func_179114_b(p, 0.0F, 1.0F, 0.0F); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    GlStateManager.func_179114_b(0.25F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, 0.0F, 0.0F);
    RenderManager renderManager = Minecraft.func_71410_x().func_175598_ae();
    try {
      renderManager.func_178631_a(180.0F);
      renderManager.func_178633_a(false);
      renderManager.func_188391_a((Entity)paramEntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, paramFloat2, false);
      renderManager.func_178633_a(true);
      GlStateManager.func_179121_F();
      RenderHelper.func_74518_a();
      GlStateManager.func_179101_C();
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179090_x();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
      paramEntityLivingBase.field_70761_aq = f1;
      paramEntityLivingBase.field_70177_z = f2;
      paramEntityLivingBase.field_70125_A = f3;
      paramEntityLivingBase.field_70758_at = f4;
      paramEntityLivingBase.field_70759_as = f5;
      if (paramEntityLivingBase instanceof T)
        this.field_146297_k.field_71441_e.func_72973_f((Entity)paramEntityLivingBase); 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void a() {
    try {
      if (this.l)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    float f = Minecraft.func_175610_ah();
    if (f == 0.0F)
      f = 0.1F; 
    try {
      if (this.n == 0) {
        p += (this.e * 10) / f;
        return;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      p += this.n / f;
      this.n = (int)(this.n * (1.0F - 0.25F / f));
      if (Math.abs(this.n) > 10)
        return; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
    
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    this.e = (this.n > 0) ? 1 : -1;
    this.n = 0;
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  public static class a {}
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmo\\u.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */