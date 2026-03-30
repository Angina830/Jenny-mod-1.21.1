package com.schnurritv.sexmod;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class bn {
  public static SimpleNetworkWrapper a;
  
  private static int b = 0;
  
  private static int a() {
    return b++;
  }
  
  public static void b() {
    a = NetworkRegistry.INSTANCE.newSimpleChannel("sexmodchannel");
    a.registerMessage(aG.a.class, aG.class, a(), Side.CLIENT);
    a.registerMessage(aG.a.class, aG.class, a(), Side.SERVER);
    a.registerMessage(b5.a.class, b5.class, a(), Side.CLIENT);
    a.registerMessage(bJ.a.class, bJ.class, a(), Side.SERVER);
    a.registerMessage(ax.a.class, ax.class, a(), Side.SERVER);
    a.registerMessage(p.a.class, p.class, a(), Side.SERVER);
    a.registerMessage(e.a.class, e.class, a(), Side.SERVER);
    a.registerMessage(bt.a.class, bt.class, a(), Side.CLIENT);
    a.registerMessage(bt.a.class, bt.class, a(), Side.SERVER);
    a.registerMessage(aZ.a.class, aZ.class, a(), Side.SERVER);
    a.registerMessage(b.a.class, b.class, a(), Side.SERVER);
    a.registerMessage(bA.a.class, bA.class, a(), Side.SERVER);
    a.registerMessage(l.a.class, l.class, a(), Side.SERVER);
    a.registerMessage(c9.a.class, c9.class, a(), Side.SERVER);
    a.registerMessage(ao.a.class, ao.class, a(), Side.SERVER);
    a.registerMessage(ah.a.class, ah.class, a(), Side.SERVER);
    a.registerMessage(aW.a.class, aW.class, a(), Side.SERVER);
    a.registerMessage(cj.a.class, cj.class, a(), Side.SERVER);
    a.registerMessage(ap.a.class, ap.class, a(), Side.SERVER);
    a.registerMessage(a8.a.class, a8.class, a(), Side.SERVER);
    a.registerMessage(bu.a.class, bu.class, a(), Side.SERVER);
    a.registerMessage(bu.a.class, bu.class, a(), Side.CLIENT);
    a.registerMessage(a.b.class, a.class, a(), Side.SERVER);
    a.registerMessage(I.a.class, I.class, a(), Side.SERVER);
    a.registerMessage(cy.a.class, cy.class, a(), Side.SERVER);
    a.registerMessage(aR.a.class, aR.class, a(), Side.SERVER);
    a.registerMessage(a9.a.class, a9.class, a(), Side.SERVER);
    a.registerMessage(bH.a.class, bH.class, a(), Side.SERVER);
    a.registerMessage(aL.a.class, aL.class, a(), Side.SERVER);
    a.registerMessage(aL.a.class, aL.class, a(), Side.CLIENT);
    a.registerMessage(be.a.class, be.class, a(), Side.SERVER);
    a.registerMessage(ak.a.class, ak.class, a(), Side.SERVER);
    a.registerMessage(bg.a.class, bg.class, a(), Side.CLIENT);
    a.registerMessage(bg.a.class, bg.class, a(), Side.SERVER);
    a.registerMessage(H.a.class, H.class, a(), Side.SERVER);
    a.registerMessage(al.a.class, al.class, a(), Side.CLIENT);
    a.registerMessage(A.a.class, A.class, a(), Side.SERVER);
    a.registerMessage(t.a.class, t.class, a(), Side.SERVER);
    a.registerMessage(a3.a.class, a3.class, a(), Side.CLIENT);
    a.registerMessage(O.a.class, O.class, a(), Side.CLIENT);
    a.registerMessage(bU.a.class, bU.class, a(), Side.SERVER);
  }
}


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\com\schnurritv\sexmod\bn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */