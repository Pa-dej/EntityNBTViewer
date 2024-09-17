package me.padej.entitynbtviewer.widgets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TitleWidget extends ClickableWidget {
    private final Identifier texture;

    public TitleWidget(int width, int height, Identifier texture) {
        this(0, 0, width, height, texture);
    }

    public TitleWidget(int x, int y, int width, int height, Identifier texture) {
        super(x, y, width, height, Text.empty());
        this.texture = texture;
    }

    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int i = this.getWidth();
        int j = this.getHeight();
        context.drawTexture(this.texture, this.getX(), this.getY(), 0.0F, 0.0F, i, j, i, j);
    }
}
