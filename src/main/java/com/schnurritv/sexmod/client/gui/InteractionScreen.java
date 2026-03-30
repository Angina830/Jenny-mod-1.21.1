package com.schnurritv.sexmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.networking.NetworkHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.List;

public class InteractionScreen extends Screen {
    private final BaseGirlEntity girl;
    private final List<String> actions;
    private int hoveredIndex = -1;

    private static final int RADIUS = 80;
    private static final int BUTTON_W = 80;
    private static final int BUTTON_H = 20;

    // Colors
    private static final int BG_NORMAL = 0x90000000;
    private static final int BG_HOVER = 0xC0442266;
    private static final int BG_SCENE = 0xA0662244;
    private static final int BG_SCENE_HOVER = 0xD0993366;
    private static final int BG_STOP = 0xA0662222;
    private static final int BG_STOP_HOVER = 0xD0993333;
    private static final int BORDER_NORMAL = 0xFF886688;
    private static final int BORDER_HOVER = 0xFFCC88CC;
    private static final int TEXT_NORMAL = 0xFFCCCCCC;
    private static final int TEXT_HOVER = 0xFFFFFFFF;

    public InteractionScreen(BaseGirlEntity girl, List<String> actions) {
        super(Component.literal("Interaction"));
        this.girl = girl;
        this.actions = actions;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        // Semi-transparent dark overlay
        g.fill(0, 0, this.width, this.height, 0x60000000);
        super.render(g, mouseX, mouseY, partialTick);

        int midX = this.width / 2;
        int midY = this.height / 2;

        // Title — girl's name
        String title = girl.getGirlName().substring(0, 1).toUpperCase() + girl.getGirlName().substring(1);
        int titleW = this.font.width(title);
        g.fill(midX - titleW / 2 - 6, midY - RADIUS - 28, midX + titleW / 2 + 6, midY - RADIUS - 8, 0xC0331133);
        g.drawString(this.font, title, midX - titleW / 2, midY - RADIUS - 24, 0xFFFF88CC, true);

        hoveredIndex = -1;
        RenderSystem.enableBlend();

        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i);
            double angle = -Math.PI / 2 + (2 * Math.PI * i) / actions.size();
            int cx = midX + (int) (Math.cos(angle) * RADIUS);
            int cy = midY + (int) (Math.sin(angle) * RADIUS);

            int x1 = cx - BUTTON_W / 2;
            int y1 = cy - BUTTON_H / 2;
            int x2 = cx + BUTTON_W / 2;
            int y2 = cy + BUTTON_H / 2;

            boolean hovered = mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
            if (hovered) hoveredIndex = i;

            boolean isScene = isSceneAction(action);
            boolean isStop = "Stop".equals(action);

            // Background
            int bg;
            if (isStop) {
                bg = hovered ? BG_STOP_HOVER : BG_STOP;
            } else if (isScene) {
                bg = hovered ? BG_SCENE_HOVER : BG_SCENE;
            } else {
                bg = hovered ? BG_HOVER : BG_NORMAL;
            }
            g.fill(x1, y1, x2, y2, bg);

            // Border
            int border = hovered ? BORDER_HOVER : BORDER_NORMAL;
            g.fill(x1, y1, x2, y1 + 1, border);
            g.fill(x1, y2 - 1, x2, y2, border);
            g.fill(x1, y1, x1 + 1, y2, border);
            g.fill(x2 - 1, y1, x2, y2, border);

            // Text centered
            String label = getLabel(action);
            int textW = this.font.width(label);
            int color = hovered ? TEXT_HOVER : TEXT_NORMAL;
            g.drawString(this.font, label, cx - textW / 2, cy - 4, color, true);
        }
    }

    private boolean isSceneAction(String action) {
        return "Missionary".equals(action) || "Doggy".equals(action) ||
               "Blowjob".equals(action) || "Boobjob".equals(action);
    }

    private String getLabel(String action) {
        return switch (action) {
            case "Follow" -> "\u2764 Follow";
            case "Stay" -> "\u2716 Stay";
            case "Stop" -> "\u25A0 Stop";
            case "Missionary" -> "\u2665 Missionary";
            case "Doggy" -> "\u2665 Doggy";
            case "Blowjob" -> "\u2665 Blowjob";
            case "Boobjob" -> "\u2665 Boobjob";
            default -> action;
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (hoveredIndex >= 0 && hoveredIndex < actions.size()) {
            handleAction(actions.get(hoveredIndex));
            this.onClose();
            return true;
        }
        this.onClose();
        return true;
    }

    private void handleAction(String action) {
        if ("Follow".equals(action)) {
            NetworkHandler.sendMovementStateUpdate(girl.getId(), "FOLLOW");
            girl.getEntityData().set(SexEntity.MASTER_UUID, net.minecraft.client.Minecraft.getInstance().player.getUUID().toString());
        } else if ("Stay".equals(action)) {
            NetworkHandler.sendMovementStateUpdate(girl.getId(), "STAY");
        } else if ("Missionary".equals(action) || "Doggy".equals(action) || 
                   "Blowjob".equals(action) || "Boobjob".equals(action) || "Stop".equals(action)) {
            NetworkHandler.sendSceneAction(girl.getId(), action);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
