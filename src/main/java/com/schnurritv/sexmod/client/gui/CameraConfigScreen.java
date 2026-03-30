package com.schnurritv.sexmod.client.gui;

import com.schnurritv.sexmod.SexModConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CameraConfigScreen extends Screen {

    public CameraConfigScreen() {
        super(Component.literal("Camera Configuration"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 24;

        // Doggy
        addRenderableWidget(new OffsetSlider(centerX - 105, startY, 100, 20, "Doggy Y", SexModConfig.DOGGY_Y));
        addRenderableWidget(new OffsetSlider(centerX + 5, startY, 100, 20, "Doggy Z", SexModConfig.DOGGY_Z));

        // Missionary
        addRenderableWidget(new OffsetSlider(centerX - 105, startY + spacing, 100, 20, "Missionary Y", SexModConfig.MISSIONARY_Y));
        addRenderableWidget(new OffsetSlider(centerX + 5, startY + spacing, 100, 20, "Missionary Z", SexModConfig.MISSIONARY_Z));

        // Blowjob
        addRenderableWidget(new OffsetSlider(centerX - 105, startY + spacing * 2, 100, 20, "Blowjob Y", SexModConfig.BLOWJOB_Y));
        addRenderableWidget(new OffsetSlider(centerX + 5, startY + spacing * 2, 100, 20, "Blowjob Z", SexModConfig.BLOWJOB_Z));

        // Paizuri
        addRenderableWidget(new OffsetSlider(centerX - 105, startY + spacing * 3, 100, 20, "Paizuri Y", SexModConfig.PAIZURI_Y));
        addRenderableWidget(new OffsetSlider(centerX + 5, startY + spacing * 3, 100, 20, "Paizuri Z", SexModConfig.PAIZURI_Z));

        // Default
        addRenderableWidget(new OffsetSlider(centerX - 105, startY + spacing * 4, 100, 20, "Default Y", SexModConfig.DEFAULT_Y));
        addRenderableWidget(new OffsetSlider(centerX + 5, startY + spacing * 4, 100, 20, "Default Z", SexModConfig.DEFAULT_Z));

        // Save & Close
        addRenderableWidget(Button.builder(Component.literal("Save & Close"), (button) -> {
            SexModConfig.SPEC.save();
            this.onClose();
        }).bounds(centerX - 50, startY + spacing * 6, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class OffsetSlider extends AbstractSliderButton {
        private final String label;
        private final net.minecraftforge.common.ForgeConfigSpec.DoubleValue configValue;

        public OffsetSlider(int x, int y, int width, int height, String label, net.minecraftforge.common.ForgeConfigSpec.DoubleValue configValue) {
            super(x, y, width, height, Component.empty(), (configValue.get() / 5.0));
            this.label = label;
            this.configValue = configValue;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(Component.literal(label + ": " + String.format("%.2f", value * 5.0)));
        }

        @Override
        protected void applyValue() {
            configValue.set(value * 5.0);
        }
    }
}
