package me.padej.entitynbtviewer;

import me.padej.entitynbtviewer.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EntityNBTViewer implements ModInitializer {

    public static final String MODID = "entitynbtviewer";

    public static final KeyBinding NBT_VIEWER_INFO_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "§7Get entity NBT info",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "NBT viewer"
    ));

    public static final KeyBinding SELF_NBT_VIEWER_INFO_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "§7Get your NBT info",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_U,
            "NBT viewer"
    ));

    public static final KeyBinding OPEN_OPTIONS_SCREEN = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "§7Open options screen",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "NBT viewer"
    ));

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
    }
}