package me.padej.entitynbtviewer.client;

import me.padej.entitynbtviewer.EntityNBTViewer;
import me.padej.entitynbtviewer.config.ConfigManager;
import me.padej.entitynbtviewer.screens.OptionsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EntityNBTViewerClient implements ClientModInitializer {

    public static final MinecraftClient mc = MinecraftClient.getInstance();

    private Entity targetEntity = null;
    private long animationStartTime = 0;
    private boolean closing = false;
    private static final long ANIMATION_DURATION = 150; // Длительность анимации в миллисекундах

    @Override
    public void onInitializeClient() {
        // Регистрируем обработчик нажатия клавиши
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && client.player != null) {
                // Проверяем нажатие клавиши для просмотра информации о выделенной сущности
                if (EntityNBTViewer.OPEN_OPTIONS_SCREEN.wasPressed()) {
                    mc.setScreen(new OptionsScreen());
                }
                if (EntityNBTViewer.NBT_VIEWER_INFO_KEY.wasPressed()) {
                    HitResult hitResult = client.crosshairTarget;
                    if (hitResult instanceof EntityHitResult entityHitResult) {
                        targetEntity = entityHitResult.getEntity();
                        animationStartTime = System.currentTimeMillis(); // Запускаем анимацию при выборе сущности
                        closing = false;
                    } else {
                        if (targetEntity != null) {
                            animationStartTime = System.currentTimeMillis(); // Запускаем анимацию закрытия
                            closing = true;
                        }
                    }
                }
                // Проверяем нажатие клавиши для просмотра информации о самом игроке
                if (EntityNBTViewer.SELF_NBT_VIEWER_INFO_KEY.wasPressed()) {
                    targetEntity = client.player;
                    animationStartTime = System.currentTimeMillis(); // Запускаем анимацию при выборе сущности
                    closing = false;
                }
            }
        });

        // Регистрируем обработчик рендеринга HUD
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (targetEntity != null || closing) {
                MinecraftClient mc = MinecraftClient.getInstance();
                TextRenderer textRenderer = mc.textRenderer;
                int x = 10;
                int y = 10;
                int padding = 5;

                List<String> lines = new ArrayList<>();

                if (targetEntity != null) {

                    lines.add("§bEntity §3{§bNBT§3}§b viewer§r: {");

                    // Основная информация о скорости
                    Vec3d velocity = targetEntity.getVelocity();
                    if (velocity != null && ConfigManager.motion) {
                        lines.add(String.format("  §bMotion§r: [§6%.2f§cf§r, §6%.2f§cf§r, §6%.2f§cf§r]", velocity.x, velocity.y, velocity.z));
                    }

                    // Движение
                    Vec3d movementDirection = Vec3d.of(targetEntity.getMovementDirection().getVector());
                    if (ConfigManager.movementDirection) {
                        lines.add(String.format("  §bMovementDirection§r: [§6%.2f§cf§r, §6%.2f§cf§r, §6%.2f§cf§r]", movementDirection.x, movementDirection.y, movementDirection.z));
                    }

                    // Hitbox
                    if (ConfigManager.hitboxHeight) {
                        lines.add("  §bHitboxHeight§r: §6" + targetEntity.getHeight() + "§cf§r");
                    }
                    if (ConfigManager.hitboxWidth) {
                        lines.add("  §bHitboxWidth§r: §6" + targetEntity.getWidth() + "§cf§r");
                    }

                    // Eye info
                    if (ConfigManager.eyeHeight) {
                        lines.add("  §bEyeHeight§r: §6" + targetEntity.getEyeHeight(targetEntity.getPose()) + "§cf§r");
                    }
                    if (ConfigManager.eyeY) {
                        lines.add("  §bEyeY§r: §6" + targetEntity.getEyeY() + "§cf§r");
                    }

                    // Orientation
                    if (ConfigManager.yaw) {
                        lines.add("  §bYaw§r: §6" + targetEntity.getYaw() + "§cf§r");
                    }
                    if (ConfigManager.pitch) {
                        lines.add("  §bPitch§r: §6" + targetEntity.getPitch() + "§cf§r");
                    }
                    if (ConfigManager.bodyYaw) {
                        lines.add("  §bBodyYaw§r: §6" + targetEntity.getBodyYaw() + "§cf§r");
                    }
                    if (ConfigManager.headYaw) {
                        lines.add("  §bHeadYaw§r: §6" + targetEntity.getHeadYaw() + "§cf§r");
                    }

                    // Position
                    if (ConfigManager.posX) {
                        lines.add("  §bPosX§r: §6" + targetEntity.getX() + "§cd§r");
                    }
                    if (ConfigManager.posY) {
                        lines.add("  §bPosY§r: §6" + targetEntity.getY() + "§cd§r");
                    }
                    if (ConfigManager.posZ) {
                        lines.add("  §bPosZ§r: §6" + targetEntity.getZ() + "§cd§r");
                    }

                    // Dimensions
                    if (ConfigManager.dimensions) {
                        lines.add("  §bDimensions§r: " + "§a" + targetEntity.getDimensions(targetEntity.getPose()));
                    }

                    // Armor items
                    String armorItems = StreamSupport.stream(targetEntity.getArmorItems().spliterator(), false)
                            .map(ItemStack::toString)
                            .collect(Collectors.joining(", ", "[", "]"));
                    if (ConfigManager.armorItems) {
                        lines.add("  §bArmorItems§r: " + "§a" + armorItems);
                    }

                    // Hand items
                    String handItems = StreamSupport.stream(targetEntity.getHandItems().spliterator(), false)
                            .map(ItemStack::toString)
                            .collect(Collectors.joining(", ", "[", "]"));
                    if (ConfigManager.handItems) {
                        lines.add("  §bHandItems§r: " + "§a" + handItems);
                    }

                    // Equipped items
                    if (ConfigManager.itemEquipped) {
                        String equippedItems = StreamSupport.stream(targetEntity.getItemsEquipped().spliterator(), false)
                                .map(ItemStack::toString)
                                .collect(Collectors.joining("§r, ", "§r[", "§r]"));
                        lines.add("  §bItemsEquipped§r: " + "§a" + equippedItems);
                    }
                    if (ConfigManager.NoGravity) {
                        lines.add("  §bNoGravity§r: " + "§6" + targetEntity.hasNoGravity() + "§cb");
                    }
                    if (ConfigManager.Invulnerable) {
                        lines.add("  §bInvulnerable:§r " + "§6" + targetEntity.isInvulnerable() + "§cb");
                    }
                    if (ConfigManager.Collidable) {
                        lines.add("  §bCollidable:§r " + "§6" + targetEntity.isCollidable() + "§cb");
                    }
//                    lines.add("  §bCrawling:§r " + "§6" + targetEntity.isCrawling() + "§cb");
                    if (ConfigManager.Glowing) {
                        lines.add("  §bGlowing:§r " + "§6" + targetEntity.isGlowing() + "§cb");
                    }
                    if (ConfigManager.ImmuneToExplosion) {
                        lines.add("  §bImmuneToExplosion:§r " + "§6" + targetEntity.isImmuneToExplosion() + "§cb");
                    }
                    if (ConfigManager.CustomNameVisible) {
                        lines.add("  §bCustomNameVisible:§r " + "§6" + targetEntity.isCustomNameVisible() + "§cb");
                    }
                    if (ConfigManager.FireImmune) {
                        lines.add("  §bFireImmune:§r " + "§6" + targetEntity.isFireImmune() + "§cb");
                    }
                    if (ConfigManager.InLava) {
                        lines.add("  §bInLava:§r " + "§6" + targetEntity.isInLava() + "§cb");
                    }
                    if (ConfigManager.InsideWall) {
                        lines.add("  §bInsideWall:§r " + "§6" + targetEntity.isInsideWall() + "§cb");
                    }
//                    lines.add("  §bInSneakingPose:§r " + "§6" + targetEntity.isInSneakingPose() + "§cb");
//                    lines.add("  §bInSwimmingPose:§r " + "§6" + targetEntity.isInSwimmingPose() + "§cb");
                    if (ConfigManager.Invisible) {
                        lines.add("  §bInvisible:§r " + "§6" + targetEntity.isInvisible() + "§cb");
                    }
                    if (ConfigManager.OnFire) {
                        lines.add("  §bOnFire:§r " + "§6" + targetEntity.isOnFire() + "§cb");
                    }
                    if (ConfigManager.OnGround) {
                        lines.add("  §bOnGround:§r " + "§6" + targetEntity.isOnGround() + "§cb");
                    }
                    if (ConfigManager.OnRail) {
                        lines.add("  §bOnRail:§r " + "§6" + targetEntity.isOnRail() + "§cb");
                    }
                    if (ConfigManager.Pushable) {
                        lines.add("  §bPushable:§r " + "§6" + targetEntity.isPushable() + "§cb");
                    }
                    if (ConfigManager.PushedByFluids) {
                        lines.add("  §bPushedByFluids:§r " + "§6" + targetEntity.isPushedByFluids() + "§cb");
                    }
                    if (ConfigManager.Silent) {
                        lines.add("  §bSilent:§r " + "§6" + targetEntity.isSilent() + "§cb");
                    }
                    if (ConfigManager.Spectator) {
                        lines.add("  §bSpectator:§r " + "§6" + targetEntity.isSpectator() + "§cb");
                    }
//                    lines.add("  §bSprinting:§r " + "§6" + targetEntity.isSprinting() + "§cb");
                    if (ConfigManager.TouchingWater) {
                        lines.add("  §bTouchingWater:§r " + "§6" + targetEntity.isTouchingWater() + "§cb");
                    }
                    if (ConfigManager.TouchingWaterOrRain) {
                        lines.add("  §bTouchingWaterOrRain:§r " + "§6" + targetEntity.isTouchingWaterOrRain() + "§cb");
                    }

                    // Ticks
                    if (ConfigManager.entityName && targetEntity.getEntityName() != null) {
                        lines.add("  §bEntityName§r: " + "§a" + targetEntity.getEntityName());
                    }
                    if (ConfigManager.name && targetEntity.getName() != null) {
                        lines.add("  §bName§r: " + "§a" + targetEntity.getName().getString());
                    }
                    if (ConfigManager.fireTicks) {
                        lines.add("  §bFireTicks§r: " + "§a" + targetEntity.getFireTicks());
                    }
                    if (ConfigManager.frozenTicks) {
                        lines.add("  §bFrozenTicks§r: " + "§a" + targetEntity.getFrozenTicks());
                    }
                    if (ConfigManager.air) {
                        lines.add("  §bAir§r: " + "§a" + targetEntity.getAir());
                    }
                    if (ConfigManager.maxAir) {
                        lines.add("  §bMaxAir§r: " + "§a" + targetEntity.getMaxAir());
                    }
                    if (targetEntity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) targetEntity;
                        if (ConfigManager.health) {
                            lines.add("  §bHealth§r: " + "§a" + livingEntity.getHealth());
                        }
                        if (ConfigManager.maxHealth) {
                            lines.add("  §bMaxHealth§r: " + "§a" + livingEntity.getMaxHealth());
                        }
                    }
                    if (ConfigManager.entityId) {
                        lines.add("  §bId§r: §6" + targetEntity.getId());
                    }

                    if (ConfigManager.teamInfo) {
                        if (targetEntity.getScoreboardTeam() != null) {
                            lines.add("  §bTeam§r: " + "§a" + targetEntity.getScoreboardTeam().getName());
                            lines.add("  §bTeamColor§r: " + "§a" + targetEntity.getScoreboardTeam().getColor().getName());
                            lines.add("  §bCollisionRule§r: " + "§a" + targetEntity.getScoreboardTeam().getCollisionRule().name());
                            lines.add("  §bDeathMessageVisibilityRule§r: " + "§a" + targetEntity.getScoreboardTeam().getDeathMessageVisibilityRule().name());
                            lines.add("  §bNameTagVisibilityRule§r: " + "§a" + targetEntity.getScoreboardTeam().getNameTagVisibilityRule().name());
                        } else {
                            lines.add("  §bTeam§r: " + "§c" + "null");
                        }
                    }

                    if (ConfigManager.leashPos) {
                        Vec3d leashPos = targetEntity.getLeashPos(tickDelta);
                        if (leashPos != null) {
                            lines.add(String.format("  §bLeashPos§r: [§6%.2f§cf§r, §6%.2f§cf§r, §6%.2f§cf§r]", leashPos.x, leashPos.y, leashPos.z));
                        }
                    }

                    if (ConfigManager.uuid && targetEntity.getUuid() != null) {
                        lines.add("  §bUUID§r: " + "§a" + targetEntity.getUuid().toString());
                    }

                    if (ConfigManager.itemEntityInfo && targetEntity instanceof ItemEntity itemEntity) {
                        lines.add("  §bItem§r: " + itemEntity.getStack().getItem().toString());
                        lines.add("  §bCount§r: §6" + itemEntity.getStack().getCount());
                    }

                    if (ConfigManager.projectileEntityInfo && targetEntity instanceof ProjectileEntity projectileEntity) {
                        lines.add("  §bProjectileOwner§r: " + "§a" + (projectileEntity.getOwner() != null ? projectileEntity.getOwner().getName().getString() : "null"));
                        lines.add("  §bProjectileType§r: " + "§a" + projectileEntity.getType().toString());
                    }

                    if (targetEntity instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) targetEntity;
                        DefaultedList<ItemStack> inventory = player.getInventory().main;

                        if (ConfigManager.inventory) {
                            lines.add("  §bInventory§r: §r[");

                            // Проход по всем слотам инвентаря
                            for (int i = 0; i < inventory.size(); i++) {
                                ItemStack stack = inventory.get(i);

                                // Проверка, что предмет в слоту не пустой
                                if (!stack.isEmpty()) {
                                    // Получение информации о предмете и его количестве
                                    Item item = stack.getItem();
                                    int count = stack.getCount();

                                    lines.add("  §r{§bSlot " + "§6" + i + "§r: " + "§a" + item + " §bCount§r: §6" + count + "§r}");
                                }
                            }
                        }
                        if (ConfigManager.luck) {
                            lines.add("  §bLuck§r: " + "§6" + ((PlayerEntity) targetEntity).getLuck() + "§cf");
                        }
                    }
                    if (ConfigManager.hurtTimeInfo && targetEntity instanceof LivingEntity) {
                        lines.add("  §bHurtTime§r: " + "§6" + ((LivingEntity) targetEntity).hurtTime);
                    }
                    // Armor stand folder
                    if (targetEntity instanceof ArmorStandEntity) {
                        if (ConfigManager.headRotation) {
                            lines.add("  §bHeadRotation§r: [" + "§6" + ((ArmorStandEntity) targetEntity).getHeadRotation().getPitch() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getHeadRotation().getYaw() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getHeadRotation().getRoll() + "§cf" + "§r]");
                        }
                        if (ConfigManager.bodyRotation) {
                            lines.add("  §bBodyRotation§r: [" + "§6" + ((ArmorStandEntity) targetEntity).getBodyRotation().getPitch() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getBodyRotation().getYaw() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getBodyRotation().getRoll() + "§cf" + "§r]");
                        }
                        if (ConfigManager.leftArmRotation) {
                            lines.add("  §bLeftArmRotation§r: [" + "§6" + ((ArmorStandEntity) targetEntity).getLeftArmRotation().getPitch() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getLeftArmRotation().getYaw() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getLeftArmRotation().getRoll() + "§cf" + "§r]");
                        }
                        if (ConfigManager.rightArmRotation) {
                            lines.add("  §bRightArmRotation§r: [" + "§6" + ((ArmorStandEntity) targetEntity).getRightArmRotation().getPitch() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getRightArmRotation().getYaw() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getRightArmRotation().getRoll() + "§cf" + "§r]");
                        }
                        if (ConfigManager.leftLegRotation) {
                            lines.add("  §bLeftLegRotation§r: [" + "§6" + ((ArmorStandEntity) targetEntity).getLeftLegRotation().getPitch() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getLeftLegRotation().getYaw() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getLeftLegRotation().getRoll() + "§cf" + "§r]");
                        }
                        if (ConfigManager.rightLegRotation) {
                            lines.add("  §bRightLegRotation§r: [" + "§6" + ((ArmorStandEntity) targetEntity).getRightLegRotation().getPitch() + "§cf" + "§r " + "§6" + ((ArmorStandEntity) targetEntity).getRightLegRotation().getYaw() + "§cf" + "§r, " + "§6" + ((ArmorStandEntity) targetEntity).getRightLegRotation().getRoll() + "§cf" + "§r]");
                        }
                    }
                    lines.add("§r}");
                    if (ConfigManager.helpInfo) {
                        lines.add("  §7Press: " + "[§e" + EntityNBTViewer.NBT_VIEWER_INFO_KEY.getBoundKeyLocalizedText().getString() + "§7]" + " for close");
                        lines.add("  §7Press: " + "[§e" + EntityNBTViewer.OPEN_OPTIONS_SCREEN.getBoundKeyLocalizedText().getString() + "§7]" + " for open options screen");
                        lines.add("  §7Press: " + "[§e" + EntityNBTViewer.SELF_NBT_VIEWER_INFO_KEY.getBoundKeyLocalizedText().getString() + "§7]" + " for open self info");
                    }
                }

                // Calculate the maximum width of all lines
                int maxWidth = lines.stream().mapToInt(line -> textRenderer.getWidth(line)).max().orElse(0);

                // Calculate animation progress
                long currentTime = System.currentTimeMillis();
                float progress = Math.min(1.0f, (currentTime - animationStartTime) / (float) ANIMATION_DURATION);
                if (closing) {
                    progress = 1.0f - progress;
                }
                float scale = progress;

                // Apply scale transformation
                drawContext.getMatrices().push();
                drawContext.getMatrices().scale(scale, scale, 1.0f);

                // Draw background rectangle
                drawContext.fill(x - padding, y - padding, x + maxWidth + padding, y + lines.size() * 10 + padding, 0x80000000);

                // Draw each line of text
                for (String line : lines) {
                    drawContext.drawText(textRenderer, Text.of(line), x, y, 0xFFFFFFFF, true);
                    y += 10;
                }

                drawContext.getMatrices().pop();

                // Reset the state when the closing animation is finished
                if (closing && progress <= 0.0f) {
                    targetEntity = null;
                    closing = false;
                }
            }
        });
    }
}