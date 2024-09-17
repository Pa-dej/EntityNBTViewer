package me.padej.entitynbtviewer.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import me.padej.entitynbtviewer.EntityNBTViewer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CheckCrossCheckboxWidget extends PressableWidget {
    private static final Identifier TEXTURE = new Identifier(EntityNBTViewer.MODID, "textures/gui/flip_flop_checkbox.png");
    private boolean checked;
    private final boolean showMessage;
    private boolean visible = true; // Добавляем переменную для управления видимостью

    public CheckCrossCheckboxWidget(int x, int y, int width, int height, Text message, boolean checked) {
        this(x, y, width, height, message, checked, true);
    }

    public CheckCrossCheckboxWidget(int x, int y, int width, int height, Text message, boolean checked, boolean showMessage) {
        super(x, y, width, height, message);
        this.checked = checked;
        this.showMessage = showMessage;
    }

    public void onPress() {
        this.checked = !this.checked;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setVisibility(boolean visible) { // Метод для установки видимости
        this.visible = visible;
    }

    public boolean isVisible() { // Метод для проверки видимости
        return this.visible;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, this.getNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.checkbox.usage.focused"));
            } else {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.checkbox.usage.hovered"));
            }
        }

    }

    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!isVisible()) return; // Проверяем видимость перед отрисовкой

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.enableDepthTest();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        context.drawTexture(TEXTURE, this.getX(), this.getY(), this.isHovered() ? 20.0F : 0.0F, this.checked ? 20.0F : 0.0F, 20, this.height, 64, 64);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.showMessage) {
            context.drawTextWithShadow(textRenderer, this.getMessage(), this.isHovered() ? this.getX() + 24 : this.getX() + 20, this.getY() + (this.height - 8) / 2, 14737632 | MathHelper.ceil(this.alpha * 255.0F) << 24);
        }

    }
}
