package me.padej.entitynbtviewer.screens;

import me.padej.entitynbtviewer.EntityNBTViewer;
import me.padej.entitynbtviewer.config.ConfigManager;
import me.padej.entitynbtviewer.widgets.CheckCrossCheckboxWidget;
import me.padej.entitynbtviewer.widgets.FolderWidget;
import me.padej.entitynbtviewer.widgets.TitleWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class OptionsScreen extends Screen {

    // --- check boxes --- //
    private CheckCrossCheckboxWidget motionCB;
    private CheckCrossCheckboxWidget movementDirectionCB;
    private CheckCrossCheckboxWidget hitboxHeightCB;
    private CheckCrossCheckboxWidget hitboxWidthCB;
    private CheckCrossCheckboxWidget eyeHeightCB;
    private CheckCrossCheckboxWidget eyeYCB;
    private CheckCrossCheckboxWidget yawCB;
    private CheckCrossCheckboxWidget pitchCB;
    private CheckCrossCheckboxWidget headYawCB;
    private CheckCrossCheckboxWidget bodyYawCB;
    private CheckCrossCheckboxWidget posXCB;
    private CheckCrossCheckboxWidget posYCB;
    private CheckCrossCheckboxWidget posZCB;
    private CheckCrossCheckboxWidget dimensionsCB;
    private CheckCrossCheckboxWidget armorItemsCB;
    private CheckCrossCheckboxWidget handItemsCB;
    private CheckCrossCheckboxWidget itemEquippedCB;
    private CheckCrossCheckboxWidget entityNameCB;
    private CheckCrossCheckboxWidget nameCB;
    private CheckCrossCheckboxWidget fireTicksCB;
    private CheckCrossCheckboxWidget frozenTicksCB;
    private CheckCrossCheckboxWidget airCB;
    private CheckCrossCheckboxWidget maxAirCB;
    private CheckCrossCheckboxWidget healthCB;
    private CheckCrossCheckboxWidget maxHealthCB;
    private CheckCrossCheckboxWidget entityIdCB;
    private CheckCrossCheckboxWidget teamInfoCB;
    private CheckCrossCheckboxWidget leashPosCB;
    private CheckCrossCheckboxWidget uuidCB;
    private CheckCrossCheckboxWidget itemEntityInfoCB;
    private CheckCrossCheckboxWidget projectileEntityInfoCB;
    private CheckCrossCheckboxWidget helpInfoCB;
    private CheckCrossCheckboxWidget luckCB;
    private CheckCrossCheckboxWidget inventoryCB;
    private CheckCrossCheckboxWidget hurtTimeInfoCB;
    private CheckCrossCheckboxWidget headRotationCB;
    private CheckCrossCheckboxWidget bodyRotationCB;
    private CheckCrossCheckboxWidget leftArmRotationCB;
    private CheckCrossCheckboxWidget rightArmRotationCB;
    private CheckCrossCheckboxWidget leftLegRotationCB;
    private CheckCrossCheckboxWidget rightLegRotationCB;
    private CheckCrossCheckboxWidget NoGravityCB;
    private CheckCrossCheckboxWidget InvulnerableCB;
    private CheckCrossCheckboxWidget CollidableCB;
//    private CheckCrossCheckboxWidget CrawlingCB;
    private CheckCrossCheckboxWidget GlowingCB;
    private CheckCrossCheckboxWidget ImmuneToExplosionCB;
    private CheckCrossCheckboxWidget CustomNameVisibleCB;
    private CheckCrossCheckboxWidget FireImmuneCB;
    private CheckCrossCheckboxWidget InLavaCB;
    private CheckCrossCheckboxWidget InsideWallCB;
//    private CheckCrossCheckboxWidget InSneakingPoseCB;
//    private CheckCrossCheckboxWidget InSwimmingPoseCB;
    private CheckCrossCheckboxWidget InvisibleCB;
    private CheckCrossCheckboxWidget OnFireCB;
    private CheckCrossCheckboxWidget OnGroundCB;
    private CheckCrossCheckboxWidget OnRailCB;
    private CheckCrossCheckboxWidget PushableCB;
    private CheckCrossCheckboxWidget PushedByFluidsCB;
    private CheckCrossCheckboxWidget SilentCB;
    private CheckCrossCheckboxWidget SpectatorCB;
//    private CheckCrossCheckboxWidget SprintingCB;
    private CheckCrossCheckboxWidget TouchingWaterCB;
    private CheckCrossCheckboxWidget TouchingWaterOrRainCB;

    private FolderWidget posFolder;
    private FolderWidget moveFolder;
    private FolderWidget statsFolder;
    private FolderWidget armorStandFolder;
    private FolderWidget booleanFolder;
    private boolean posFolderIsOpen = false;
    private boolean moveFolderIsOpen = false;
    private boolean statsFolderIsOpen = false;
    private boolean armorStandFolderIsOpen = false;
    private boolean booleanFolderIsOpen = false;

    // --- textures -- //
    Identifier TITLE = new Identifier(EntityNBTViewer.MODID, "textures/gui/options_title.png");

    public OptionsScreen() {
        super(Text.literal("OptionsScreen"));
    }

    @Override
    public void init() {
        // Создаем widgets

        // Title
        TitleWidget title = new TitleWidget(width / 2 - 128, 10, 256, 58, TITLE);
        this.addDrawableChild(title);

        int startY = 96;

        // CheckBoxes

        // POS FOLDER
        hitboxHeightCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 0, 20, 20,Text.of(""), ConfigManager.hitboxHeight);
        hitboxWidthCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 1, 20, 20,Text.of(""), ConfigManager.hitboxWidth);
        eyeHeightCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 2, 20, 20,Text.of(""), ConfigManager.eyeHeight);
        eyeYCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 3, 20, 20,Text.of(""), ConfigManager.eyeY);
        yawCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 4, 20, 20,Text.of(""), ConfigManager.yaw);
        pitchCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 5, 20, 20,Text.of(""), ConfigManager.pitch);
        bodyYawCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 6, 20, 20,Text.of(""), ConfigManager.bodyYaw);
        headYawCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 7, 20, 20,Text.of(""), ConfigManager.headYaw);
        posXCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 8, 20, 20,Text.of(""), ConfigManager.posX);
        posYCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 9, 20, 20,Text.of(""), ConfigManager.posY);
        posZCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 10, 20, 20,Text.of(""), ConfigManager.posZ);
        dimensionsCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 11, 20, 20,Text.of(""), ConfigManager.dimensions);
        leashPosCB = new CheckCrossCheckboxWidget(32 + 128 * 0, startY + 20 * 12, 20, 20, Text.of(""), ConfigManager.leashPos);

        // MOVE FOLDER
        motionCB = new CheckCrossCheckboxWidget(32 + 128 * 1, startY + 20 * 0, 20, 20,Text.of(""), ConfigManager.motion);
        movementDirectionCB = new CheckCrossCheckboxWidget(32 + 128 * 1, startY + 20 * 1, 20, 20,Text.of(""), ConfigManager.movementDirection);

        // STATS FOLDER
        armorItemsCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 0, 20, 20,Text.of(""), ConfigManager.armorItems);
        handItemsCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 1, 20, 20,Text.of(""), ConfigManager.handItems);
        itemEquippedCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 2, 20, 20, Text.of(""), ConfigManager.itemEquipped);
        entityNameCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 3, 20, 20, Text.of(""), ConfigManager.entityName);
        nameCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 4, 20, 20, Text.of(""), ConfigManager.name);
        fireTicksCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 5, 20, 20, Text.of(""), ConfigManager.fireTicks);
        frozenTicksCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 6, 20, 20, Text.of(""), ConfigManager.frozenTicks);
        airCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 7, 20, 20, Text.of(""), ConfigManager.air);
        maxAirCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 8, 20, 20, Text.of(""), ConfigManager.maxAir);
        healthCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 9, 20, 20, Text.of(""), ConfigManager.health);
        maxHealthCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 10, 20, 20, Text.of(""), ConfigManager.maxHealth);
        entityIdCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 11, 20, 20, Text.of(""), ConfigManager.entityId);
        teamInfoCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 12, 20, 20, Text.of(""), ConfigManager.teamInfo);
        uuidCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 13, 20, 20, Text.of(""), ConfigManager.uuid);
        itemEntityInfoCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 14, 20, 20, Text.of(""), ConfigManager.itemEntityInfo);
        projectileEntityInfoCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 15, 20, 20, Text.of(""), ConfigManager.projectileEntityInfo);
        luckCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 16, 20, 20, Text.of(""), ConfigManager.luck);
        inventoryCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 17, 20, 20, Text.of(""), ConfigManager.inventory);
        hurtTimeInfoCB = new CheckCrossCheckboxWidget(32 + 128 * 2, startY + 20 * 18, 20, 20, Text.of(""), ConfigManager.hurtTimeInfo);

        // ARMOR STAND FOLDER
        headRotationCB = new CheckCrossCheckboxWidget(32 + 128 * 3, startY + 20 * 0, 20, 20,Text.of(""), ConfigManager.headRotation);
        bodyRotationCB = new CheckCrossCheckboxWidget(32 + 128 * 3, startY + 20 * 1, 20, 20,Text.of(""), ConfigManager.bodyRotation);
        leftArmRotationCB = new CheckCrossCheckboxWidget(32 + 128 * 3, startY + 20 * 2, 20, 20,Text.of(""), ConfigManager.leftArmRotation);
        rightArmRotationCB = new CheckCrossCheckboxWidget(32 + 128 * 3, startY + 20 * 3, 20, 20,Text.of(""), ConfigManager.rightArmRotation);
        leftLegRotationCB = new CheckCrossCheckboxWidget(32 + 128 * 3, startY + 20 * 4, 20, 20,Text.of(""), ConfigManager.leftLegRotation);
        rightLegRotationCB = new CheckCrossCheckboxWidget(32 + 128 * 3, startY + 20 * 5, 20, 20,Text.of(""), ConfigManager.rightLegRotation);

        // BOOLEAN FOLDER
        NoGravityCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 0, 20, 20,Text.of(""), ConfigManager.NoGravity);
        InvulnerableCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 1, 20, 20,Text.of(""), ConfigManager.Invulnerable);
        CollidableCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 2, 20, 20,Text.of(""), ConfigManager.Collidable);
//        CrawlingCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 3, 20, 20,Text.of(""), ConfigManager.Crawling);
        GlowingCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 3, 20, 20,Text.of(""), ConfigManager.Glowing);
        ImmuneToExplosionCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 4, 20, 20,Text.of(""), ConfigManager.ImmuneToExplosion);
        CustomNameVisibleCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 5, 20, 20,Text.of(""), ConfigManager.CustomNameVisible);
        FireImmuneCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 6, 20, 20,Text.of(""), ConfigManager.FireImmune);
        InLavaCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 7, 20, 20,Text.of(""), ConfigManager.InLava);
        InsideWallCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 8, 20, 20,Text.of(""), ConfigManager.InsideWall);
//        InSneakingPoseCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 10, 20, 20,Text.of(""), ConfigManager.InSneakingPose);
//        InSwimmingPoseCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 11, 20, 20,Text.of(""), ConfigManager.InSwimmingPose);
        InvisibleCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 9, 20, 20,Text.of(""), ConfigManager.Invisible);
        OnFireCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 10, 20, 20,Text.of(""), ConfigManager.OnFire);
        OnGroundCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 11, 20, 20,Text.of(""), ConfigManager.OnGround);
        OnRailCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 12, 20, 20,Text.of(""), ConfigManager.OnRail);
        PushableCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 13, 20, 20,Text.of(""), ConfigManager.Pushable);
        PushedByFluidsCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 14, 20, 20,Text.of(""), ConfigManager.PushedByFluids);
        SilentCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 15, 20, 20,Text.of(""), ConfigManager.Silent);
        SpectatorCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 16, 20, 20,Text.of(""), ConfigManager.Spectator);
//        SprintingCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 20, 20, 20,Text.of(""), ConfigManager.Sprinting);
        TouchingWaterCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 17, 20, 20,Text.of(""), ConfigManager.TouchingWater);
        TouchingWaterOrRainCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 18, 20, 20,Text.of(""), ConfigManager.TouchingWaterOrRain);
        helpInfoCB = new CheckCrossCheckboxWidget(32 + 128 * 4, startY + 20 * 19, 20, 20, Text.of(""), ConfigManager.helpInfo);

        // FOLDERS
        posFolder = new FolderWidget(32 + 128 * 0, 80, 20, 20, Text.of("Pos & Scale"), posFolderIsOpen) {
            @Override
            public void onPress() {
                super.onPress();
                posFolderIsOpen = isChecked();
                UpdatePosFolder();
            }
        };
        this.addDrawableChild(posFolder);

        moveFolder = new FolderWidget(32 + 128 * 1, 80, 20, 20, Text.of("Move"), moveFolderIsOpen) {
            @Override
            public void onPress() {
                super.onPress();
                moveFolderIsOpen = isChecked();
                UpdateMoveFolder();
            }
        };
        this.addDrawableChild(moveFolder);

        statsFolder = new FolderWidget(32 + 128 * 2, 80, 20, 20, Text.of("States"), statsFolderIsOpen) {
            @Override
            public void onPress() {
                super.onPress();
                statsFolderIsOpen = isChecked();
                UpdateStatesFolder();
            }
        };
        this.addDrawableChild(statsFolder);

        armorStandFolder = new FolderWidget(32 + 128 * 3, 80, 20, 20, Text.of("Armor Stand"), armorStandFolderIsOpen) {
            @Override
            public void onPress() {
                super.onPress();
                armorStandFolderIsOpen = isChecked();
                UpdateArmorStandFolder();
            }
        };
        this.addDrawableChild(armorStandFolder);

        booleanFolder = new FolderWidget(32 + 128 * 4, 80, 20, 20, Text.of("Boolean NBT's"), booleanFolderIsOpen) {
            @Override
            public void onPress() {
                super.onPress();
                booleanFolderIsOpen = isChecked();
                UpdateBoolFolder();
            }
        };
        this.addDrawableChild(booleanFolder);
    }

    @Override
    public void tick() {
        super.tick();
        UpdateAllCheckBoxes();
        UpdateName();

        ConfigManager.updateConfig();
    }

    private void UpdateAllCheckBoxes() {
        ConfigManager.motion = motionCB.isChecked();
        ConfigManager.movementDirection = movementDirectionCB.isChecked();
        ConfigManager.hitboxHeight = hitboxHeightCB.isChecked();
        ConfigManager.hitboxWidth = hitboxWidthCB.isChecked();
        ConfigManager.eyeHeight = eyeHeightCB.isChecked();
        ConfigManager.eyeY = eyeYCB.isChecked();
        ConfigManager.yaw = yawCB.isChecked();
        ConfigManager.pitch = pitchCB.isChecked();
        ConfigManager.headYaw = headYawCB.isChecked();
        ConfigManager.bodyYaw = bodyYawCB.isChecked();
        ConfigManager.posX = posXCB.isChecked();
        ConfigManager.posY = posYCB.isChecked();
        ConfigManager.posZ = posZCB.isChecked();
        ConfigManager.dimensions = dimensionsCB.isChecked();
        ConfigManager.armorItems = armorItemsCB.isChecked();
        ConfigManager.handItems = handItemsCB.isChecked();
        ConfigManager.itemEquipped = itemEquippedCB.isChecked();
        ConfigManager.entityName = entityNameCB.isChecked();
        ConfigManager.name = nameCB.isChecked();
        ConfigManager.fireTicks = fireTicksCB.isChecked();
        ConfigManager.frozenTicks = frozenTicksCB.isChecked();
        ConfigManager.air = airCB.isChecked();
        ConfigManager.maxAir = maxAirCB.isChecked();
        ConfigManager.health = healthCB.isChecked();
        ConfigManager.maxHealth = maxHealthCB.isChecked();
        ConfigManager.entityId = entityIdCB.isChecked();
        ConfigManager.teamInfo = teamInfoCB.isChecked();
        ConfigManager.leashPos = leashPosCB.isChecked();
        ConfigManager.uuid = uuidCB.isChecked();
        ConfigManager.itemEntityInfo = itemEntityInfoCB.isChecked();
        ConfigManager.projectileEntityInfo = projectileEntityInfoCB.isChecked();
        ConfigManager.helpInfo = helpInfoCB.isChecked();
        ConfigManager.luck = luckCB.isChecked();
        ConfigManager.inventory = inventoryCB.isChecked();
        ConfigManager.hurtTimeInfo = hurtTimeInfoCB.isChecked();
        ConfigManager.NoGravity = NoGravityCB.isChecked();
        ConfigManager.Invulnerable = InvulnerableCB.isChecked();
        ConfigManager.Collidable = CollidableCB.isChecked();
//        ConfigManager.Crawling = CrawlingCB.isChecked();
        ConfigManager.Glowing = GlowingCB.isChecked();
        ConfigManager.ImmuneToExplosion = ImmuneToExplosionCB.isChecked();
        ConfigManager.CustomNameVisible = CustomNameVisibleCB.isChecked();
        ConfigManager.FireImmune = FireImmuneCB.isChecked();
        ConfigManager.InLava = InLavaCB.isChecked();
        ConfigManager.InsideWall = InsideWallCB.isChecked();
//        ConfigManager.InSneakingPose = InSneakingPoseCB.isChecked();
//        ConfigManager.InSwimmingPose = InSwimmingPoseCB.isChecked();
        ConfigManager.Invisible = InvisibleCB.isChecked();
        ConfigManager.OnFire = OnFireCB.isChecked();
        ConfigManager.OnGround = OnGroundCB.isChecked();
        ConfigManager.OnRail = OnRailCB.isChecked();
        ConfigManager.Pushable = PushableCB.isChecked();
        ConfigManager.PushedByFluids = PushedByFluidsCB.isChecked();
        ConfigManager.Silent = SilentCB.isChecked();
        ConfigManager.Spectator = SpectatorCB.isChecked();
//        ConfigManager.Sprinting = SprintingCB.isChecked();
        ConfigManager.TouchingWater = TouchingWaterCB.isChecked();
        ConfigManager.TouchingWaterOrRain = TouchingWaterOrRainCB.isChecked();

        ConfigManager.headRotation = headRotationCB.isChecked();
        ConfigManager.bodyRotation = bodyRotationCB.isChecked();
        ConfigManager.leftArmRotation = leftArmRotationCB.isChecked();
        ConfigManager.rightArmRotation = rightArmRotationCB.isChecked();
        ConfigManager.leftLegRotation = leftLegRotationCB.isChecked();
        ConfigManager.rightLegRotation = rightLegRotationCB.isChecked();

        posFolderIsOpen = posFolder.isChecked();
        moveFolderIsOpen = moveFolder.isChecked();
        statsFolderIsOpen = statsFolder.isChecked();
        armorStandFolderIsOpen = armorStandFolder.isChecked();
        booleanFolderIsOpen = booleanFolder.isChecked();
    }

    private void UpdateName() {
        motionCB.setMessage(motionCB.isChecked() ? Text.of("§aMotion") : Text.of("§cMotion"));
        movementDirectionCB.setMessage(movementDirectionCB.isChecked() ? Text.of("§aMovementDirection") : Text.of("§cMovementDirection"));
        hitboxHeightCB.setMessage(hitboxHeightCB.isChecked() ? Text.of("§aHitboxHeight") : Text.of("§cHitboxHeight"));
        hitboxWidthCB.setMessage(hitboxWidthCB.isChecked() ? Text.of("§aHitboxWidth") : Text.of("§cHitboxWidth"));
        eyeHeightCB.setMessage(eyeHeightCB.isChecked() ? Text.of("§aEyeHeight") : Text.of("§cEyeHeight"));
        eyeYCB.setMessage(eyeYCB.isChecked() ? Text.of("§aEyeY") : Text.of("§cEyeY"));
        yawCB.setMessage(yawCB.isChecked() ? Text.of("§aYaw") : Text.of("§cYaw"));
        pitchCB.setMessage(pitchCB.isChecked() ? Text.of("§aPitch") : Text.of("§cPitch"));
        headYawCB.setMessage(headYawCB.isChecked() ? Text.of("§aHeadYaw") : Text.of("§cHeadYaw"));
        bodyYawCB.setMessage(bodyYawCB.isChecked() ? Text.of("§aBodyYaw") : Text.of("§cBodyYaw"));
        posXCB.setMessage(posXCB.isChecked() ? Text.of("§aPosX") : Text.of("§cPosX"));
        posYCB.setMessage(posYCB.isChecked() ? Text.of("§aPosY") : Text.of("§cPosY"));
        posZCB.setMessage(posZCB.isChecked() ? Text.of("§aPosZ") : Text.of("§cPosZ"));
        dimensionsCB.setMessage(dimensionsCB.isChecked() ? Text.of("§aDimensions") : Text.of("§cDimensions"));
        armorItemsCB.setMessage(armorItemsCB.isChecked() ? Text.of("§aArmorItems") : Text.of("§cArmorItems"));
        handItemsCB.setMessage(handItemsCB.isChecked() ? Text.of("§aHandItems") : Text.of("§cHandItems"));
        itemEquippedCB.setMessage(itemEquippedCB.isChecked() ? Text.of("§aItemEquipped") : Text.of("§cItemEquipped"));
        entityNameCB.setMessage(entityNameCB.isChecked() ? Text.of("§aEntityName") : Text.of("§cEntityName"));
        nameCB.setMessage(nameCB.isChecked() ? Text.of("§aName") : Text.of("§cName"));
        fireTicksCB.setMessage(fireTicksCB.isChecked() ? Text.of("§aFireTicks") : Text.of("§cFireTicks"));
        frozenTicksCB.setMessage(frozenTicksCB.isChecked() ? Text.of("§aFrozenTicks") : Text.of("§cFrozenTicks"));
        airCB.setMessage(airCB.isChecked() ? Text.of("§aAir") : Text.of("§cAir"));
        maxAirCB.setMessage(maxAirCB.isChecked() ? Text.of("§aMaxAir") : Text.of("§cMaxAir"));
        healthCB.setMessage(healthCB.isChecked() ? Text.of("§aHealth") : Text.of("§cHealth"));
        maxHealthCB.setMessage(maxHealthCB.isChecked() ? Text.of("§aMaxHealth") : Text.of("§cMaxHealth"));
        entityIdCB.setMessage(entityIdCB.isChecked() ? Text.of("§aEntityID") : Text.of("§cEntityID"));
        teamInfoCB.setMessage(teamInfoCB.isChecked() ? Text.of("§aTeamInfo") : Text.of("§cTeamInfo"));
        leashPosCB.setMessage(leashPosCB.isChecked() ? Text.of("§aLeashPos") : Text.of("§cLeashPos"));
        uuidCB.setMessage(uuidCB.isChecked() ? Text.of("§aUUID") : Text.of("§cUUID"));
        itemEntityInfoCB.setMessage(itemEntityInfoCB.isChecked() ? Text.of("§aItemEntityInfo") : Text.of("§cItemEntityInfo"));
        projectileEntityInfoCB.setMessage(projectileEntityInfoCB.isChecked() ? Text.of("§aProjectileEntityInfo") : Text.of("§cProjectileEntityInfo"));
        helpInfoCB.setMessage(helpInfoCB.isChecked() ? Text.of("§aHelpInfo") : Text.of("§cHelpInfo"));
        luckCB.setMessage(luckCB.isChecked() ? Text.of("§aLuck") : Text.of("§cLuck"));
        inventoryCB.setMessage(inventoryCB.isChecked() ? Text.of("§aInventory") : Text.of("§cInventory"));
        hurtTimeInfoCB.setMessage(hurtTimeInfoCB.isChecked() ? Text.of("§aHurtTime") : Text.of("§cHurtTime"));
        headRotationCB.setMessage(headRotationCB.isChecked() ? Text.of("§aHeadRotation") : Text.of("§cHeadRotation"));
        bodyRotationCB.setMessage(bodyRotationCB.isChecked() ? Text.of("§aBodyRotation") : Text.of("§cBodyRotation"));
        leftArmRotationCB.setMessage(leftArmRotationCB.isChecked() ? Text.of("§aLeftArmRotation") : Text.of("§cLeftArmRotation"));
        rightArmRotationCB.setMessage(rightArmRotationCB.isChecked() ? Text.of("§aRightArmRotation") : Text.of("§cRightArmRotation"));
        leftLegRotationCB.setMessage(leftLegRotationCB.isChecked() ? Text.of("§aLeftLegRotation") : Text.of("§cLeftLegRotation"));
        rightLegRotationCB.setMessage(rightLegRotationCB.isChecked() ? Text.of("§aRightLegRotation") : Text.of("§cRightLegRotation"));

        NoGravityCB.setMessage(NoGravityCB.isChecked() ? Text.of("§aNoGravity") : Text.of("§cNoGravity"));
        InvulnerableCB.setMessage(InvulnerableCB.isChecked() ? Text.of("§aInvulnerable") : Text.of("§cInvulnerable"));
        CollidableCB.setMessage(CollidableCB.isChecked() ? Text.of("§aCollidable") : Text.of("§cCollidable"));
//        CrawlingCB.setMessage(CrawlingCB.isChecked() ? Text.of("§aCrawling") : Text.of("§cCrawling"));
        GlowingCB.setMessage(GlowingCB.isChecked() ? Text.of("§aGlowing") : Text.of("§cGlowing"));
        ImmuneToExplosionCB.setMessage(ImmuneToExplosionCB.isChecked() ? Text.of("§aImmuneToExplosion") : Text.of("§cImmuneToExplosion"));
        CustomNameVisibleCB.setMessage(CustomNameVisibleCB.isChecked() ? Text.of("§aCustomNameVisible") : Text.of("§cCustomNameVisible"));
        FireImmuneCB.setMessage(FireImmuneCB.isChecked() ? Text.of("§aFireImmune") : Text.of("§cFireImmune"));
        InLavaCB.setMessage(InLavaCB.isChecked() ? Text.of("§aInLava") : Text.of("§cInLava"));
        InsideWallCB.setMessage(InsideWallCB.isChecked() ? Text.of("§aInsideWall") : Text.of("§cInsideWall"));
//        InSneakingPoseCB.setMessage(InSneakingPoseCB.isChecked() ? Text.of("§aInSneakingPose") : Text.of("§cInSneakingPose"));
//        InSwimmingPoseCB.setMessage(InSwimmingPoseCB.isChecked() ? Text.of("§aInSwimmingPose") : Text.of("§cInSwimmingPose"));
        InvisibleCB.setMessage(InvisibleCB.isChecked() ? Text.of("§aInvisible") : Text.of("§cInvisible"));
        OnFireCB.setMessage(OnFireCB.isChecked() ? Text.of("§aOnFire") : Text.of("§cOnFire"));
        OnGroundCB.setMessage(OnGroundCB.isChecked() ? Text.of("§aOnGround") : Text.of("§cOnGround"));
        OnRailCB.setMessage(OnRailCB.isChecked() ? Text.of("§aOnRail") : Text.of("§cOnRail"));
        PushableCB.setMessage(PushableCB.isChecked() ? Text.of("§aPushable") : Text.of("§cPushable"));
        PushedByFluidsCB.setMessage(PushedByFluidsCB.isChecked() ? Text.of("§aPushedByFluids") : Text.of("§cPushedByFluids"));
        SilentCB.setMessage(SilentCB.isChecked() ? Text.of("§aSilent") : Text.of("§cSilent"));
        SpectatorCB.setMessage(SpectatorCB.isChecked() ? Text.of("§aSpectator") : Text.of("§cSpectator"));
//        SprintingCB.setMessage(SprintingCB.isChecked() ? Text.of("§aSprinting") : Text.of("§cSprinting"));
        TouchingWaterCB.setMessage(TouchingWaterCB.isChecked() ? Text.of("§aTouchingWater") : Text.of("§cTouchingWater"));
        TouchingWaterOrRainCB.setMessage(TouchingWaterOrRainCB.isChecked() ? Text.of("§aTouchingWaterOrRain") : Text.of("§cTouchingWaterOrRain"));
    }

    private void UpdatePosFolder() {
        if (posFolderIsOpen) {
            this.addDrawableChild(hitboxHeightCB);
            this.addDrawableChild(hitboxWidthCB);
            this.addDrawableChild(eyeHeightCB);
            this.addDrawableChild(eyeYCB);
            this.addDrawableChild(yawCB);
            this.addDrawableChild(pitchCB);
            this.addDrawableChild(bodyYawCB);
            this.addDrawableChild(headYawCB);
            this.addDrawableChild(posXCB);
            this.addDrawableChild(posYCB);
            this.addDrawableChild(posZCB);
            this.addDrawableChild(dimensionsCB);
            this.addDrawableChild(leashPosCB);
        } else {
            this.remove(hitboxHeightCB);
            this.remove(hitboxWidthCB);
            this.remove(eyeHeightCB);
            this.remove(eyeYCB);
            this.remove(yawCB);
            this.remove(pitchCB);
            this.remove(bodyYawCB);
            this.remove(headYawCB);
            this.remove(posXCB);
            this.remove(posYCB);
            this.remove(posZCB);
            this.remove(dimensionsCB);
            this.remove(leashPosCB);
        }
    }
    private void UpdateMoveFolder() {
        if (moveFolderIsOpen) {
            this.addDrawableChild(motionCB);
            this.addDrawableChild(movementDirectionCB);
        } else {
            this.remove(motionCB);
            this.remove(movementDirectionCB);
        }
    }
    private void UpdateStatesFolder() {
        if (statsFolderIsOpen) {
            this.addDrawableChild(armorItemsCB);
            this.addDrawableChild(handItemsCB);
            this.addDrawableChild(itemEquippedCB);
            this.addDrawableChild(entityNameCB);
            this.addDrawableChild(nameCB);
            this.addDrawableChild(fireTicksCB);
            this.addDrawableChild(frozenTicksCB);
            this.addDrawableChild(airCB);
            this.addDrawableChild(maxAirCB);
            this.addDrawableChild(healthCB);
            this.addDrawableChild(maxHealthCB);
            this.addDrawableChild(entityIdCB);
            this.addDrawableChild(teamInfoCB);
            this.addDrawableChild(uuidCB);
            this.addDrawableChild(itemEntityInfoCB);
            this.addDrawableChild(projectileEntityInfoCB);
            this.addDrawableChild(luckCB);
            this.addDrawableChild(inventoryCB);
            this.addDrawableChild(hurtTimeInfoCB);
        } else {
            this.remove(armorItemsCB);
            this.remove(handItemsCB);
            this.remove(itemEquippedCB);
            this.remove(entityNameCB);
            this.remove(nameCB);
            this.remove(fireTicksCB);
            this.remove(frozenTicksCB);
            this.remove(airCB);
            this.remove(maxAirCB);
            this.remove(healthCB);
            this.remove(maxHealthCB);
            this.remove(entityIdCB);
            this.remove(teamInfoCB);
            this.remove(uuidCB);
            this.remove(itemEntityInfoCB);
            this.remove(projectileEntityInfoCB);
            this.remove(luckCB);
            this.remove(inventoryCB);
            this.remove(hurtTimeInfoCB);
        }
    }
    private void UpdateArmorStandFolder() {
        if (armorStandFolderIsOpen) {
            this.addDrawableChild(headRotationCB);
            this.addDrawableChild(bodyRotationCB);
            this.addDrawableChild(leftArmRotationCB);
            this.addDrawableChild(rightArmRotationCB);
            this.addDrawableChild(leftLegRotationCB);
            this.addDrawableChild(rightLegRotationCB);
        } else {
            this.remove(headRotationCB);
            this.remove(bodyRotationCB);
            this.remove(leftArmRotationCB);
            this.remove(rightArmRotationCB);
            this.remove(leftLegRotationCB);
            this.remove(rightLegRotationCB);
        }
    }
    private void UpdateBoolFolder() {
        if (booleanFolderIsOpen) {
            this.addDrawableChild(NoGravityCB);
            this.addDrawableChild(InvulnerableCB);
            this.addDrawableChild(CollidableCB);
//            this.addDrawableChild(CrawlingCB);
            this.addDrawableChild(GlowingCB);
            this.addDrawableChild(ImmuneToExplosionCB);
            this.addDrawableChild(CustomNameVisibleCB);
            this.addDrawableChild(FireImmuneCB);
            this.addDrawableChild(InLavaCB);
            this.addDrawableChild(InsideWallCB);
//            this.addDrawableChild(InSneakingPoseCB);
//            this.addDrawableChild(InSwimmingPoseCB);
            this.addDrawableChild(InvisibleCB);
            this.addDrawableChild(OnFireCB);
            this.addDrawableChild(OnGroundCB);
            this.addDrawableChild(OnRailCB);
            this.addDrawableChild(PushableCB);
            this.addDrawableChild(PushedByFluidsCB);
            this.addDrawableChild(SilentCB);
            this.addDrawableChild(SpectatorCB);
//            this.addDrawableChild(SprintingCB);
            this.addDrawableChild(TouchingWaterCB);
            this.addDrawableChild(TouchingWaterOrRainCB);
            this.addDrawableChild(helpInfoCB);
        } else {
            this.remove(NoGravityCB);
            this.remove(InvulnerableCB);
            this.remove(CollidableCB);
//            this.remove(CrawlingCB);
            this.remove(GlowingCB);
            this.remove(ImmuneToExplosionCB);
            this.remove(CustomNameVisibleCB);
            this.remove(FireImmuneCB);
            this.remove(InLavaCB);
            this.remove(InsideWallCB);
//            this.remove(InSneakingPoseCB);
//            this.remove(InSwimmingPoseCB);
            this.remove(InvisibleCB);
            this.remove(OnFireCB);
            this.remove(OnGroundCB);
            this.remove(OnRailCB);
            this.remove(PushableCB);
            this.remove(PushedByFluidsCB);
            this.remove(SilentCB);
            this.remove(SpectatorCB);
//            this.remove(SprintingCB);
            this.remove(TouchingWaterCB);
            this.remove(TouchingWaterOrRainCB);
            this.remove(helpInfoCB);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }
}