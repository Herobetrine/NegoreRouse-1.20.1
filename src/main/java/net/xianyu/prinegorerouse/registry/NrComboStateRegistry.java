package net.xianyu.prinegorerouse.registry;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.ability.StunManager;
import mods.flammpfeil.slashblade.init.DefaultResources;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import mods.flammpfeil.slashblade.util.AttackManager;
import mods.flammpfeil.slashblade.util.KnockBacks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.xianyu.prinegorerouse.prinegorerouse;
import net.xianyu.prinegorerouse.specialattack.BurningFireSA;
import net.xianyu.prinegorerouse.specialattack.CosmicLine;
import net.xianyu.prinegorerouse.specialattack.DivineCrossSA;
import net.xianyu.prinegorerouse.specialattack.MagneticStormSword;
import net.xianyu.prinegorerouse.specialattack.OverTheHorizon;
import net.xianyu.prinegorerouse.specialattack.Zenith12th;

public class NrComboStateRegistry {
    public static final DeferredRegister<ComboState> NR_COMBO_STATE = DeferredRegister.create(ComboState.REGISTRY_KEY, "prinegorerouse");

    public static final RegistryObject<ComboState> ZENITH12TH;
    public static final RegistryObject<ComboState> ZENITH12TH_END;
    public static final RegistryObject<ComboState> STORM_SWORDS;
    public static final RegistryObject<ComboState> STORM_SWORDS_END;
    public static final RegistryObject<ComboState> DIVINE_CROSS_SA;
    public static final RegistryObject<ComboState> DIVINE_CROSS_SA_END;
    public static final RegistryObject<ComboState> BURNING_FIRE_SA;
    public static final RegistryObject<ComboState> BURNING_FIRE_SA_END;
    public static final RegistryObject<ComboState> COSMIC_LINE;
    public static final RegistryObject<ComboState> COSMIC_LINE_END;
    public static final RegistryObject<ComboState> OVER_THE_HORIZON;
    public static final RegistryObject<ComboState> OVER_THE_HORIZON_END;

    static {
        ZENITH12TH = NR_COMBO_STATE.register("zenith12th", () -> ComboState.Builder.newInstance()
            .startAndEnd(400, 459)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(ComboState.TimeoutNext.buildFromFrame(15, entity -> SlashBlade.prefix("none")))
            .nextOfTimeout(entity -> prinegorerouse.prefix("zenith12th_end"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(2, entityIn -> AttackManager.doSlash(entityIn, -50.0F, Vec3.ZERO, false, false, 2.0D))
                .put(3, entityIn -> Zenith12th.doSlash(entityIn, false, 4.0F))
                .build())
            .addHitEffect(StunManager::setStun)
            .build());

        ZENITH12TH_END = NR_COMBO_STATE.register("zenith12th_end", () -> ComboState.Builder.newInstance()
            .startAndEnd(459, 488)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(entity -> SlashBlade.prefix("none"))
            .nextOfTimeout(entity -> SlashBlade.prefix("none"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(0, AttackManager::playQuickSheathSoundAction)
                .build())
            .releaseAction(ComboState::releaseActionQuickCharge)
            .build());

        STORM_SWORDS = NR_COMBO_STATE.register("magnetic_storm_sword", () -> ComboState.Builder.newInstance()
            .startAndEnd(400, 459)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(ComboState.TimeoutNext.buildFromFrame(15, entity -> SlashBlade.prefix("none")))
            .nextOfTimeout(entity -> prinegorerouse.prefix("magnetic_storm_sword_end"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(2, entityIn -> AttackManager.doSlash(entityIn, -30.0F, Vec3.ZERO, false, false, 2.0D))
                .put(3, entityIn -> MagneticStormSword.doSlash(entityIn, false, 4.0F))
                .build())
            .addHitEffect(StunManager::setStun)
            .build());

        STORM_SWORDS_END = NR_COMBO_STATE.register("magnetic_storm_sword_end", () -> ComboState.Builder.newInstance()
            .startAndEnd(459, 488)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(entity -> SlashBlade.prefix("none"))
            .nextOfTimeout(entity -> SlashBlade.prefix("none"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(0, AttackManager::playQuickSheathSoundAction)
                .build())
            .releaseAction(ComboState::releaseActionQuickCharge)
            .build());

        DIVINE_CROSS_SA = NR_COMBO_STATE.register("divine_cross_sa", () -> ComboState.Builder.newInstance()
            .startAndEnd(400, 459)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(ComboState.TimeoutNext.buildFromFrame(15, entity -> SlashBlade.prefix("none")))
            .nextOfTimeout(entity -> prinegorerouse.prefix("divine_cross_sa_end"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(2, entityIn -> AttackManager.doSlash(entityIn, -80.0F, Vec3.ZERO, false, false, 0.1D))
                .put(3, entityIn -> DivineCrossSA.doSlash(entityIn, 0.0F, 15, Vec3.ZERO, false, false, 2.0D, 3.0F))
                .build())
            .addHitEffect(StunManager::setStun)
            .build());

        DIVINE_CROSS_SA_END = NR_COMBO_STATE.register("divine_cross_sa_end", () -> ComboState.Builder.newInstance()
            .startAndEnd(459, 488)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(entity -> SlashBlade.prefix("none"))
            .nextOfTimeout(entity -> SlashBlade.prefix("none"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(0, AttackManager::playQuickSheathSoundAction)
                .build())
            .releaseAction(ComboState::releaseActionQuickCharge)
            .build());

        BURNING_FIRE_SA = NR_COMBO_STATE.register("burning_fire_sa", () -> ComboState.Builder.newInstance()
            .startAndEnd(400, 459)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(ComboState.TimeoutNext.buildFromFrame(15, entity -> SlashBlade.prefix("none")))
            .nextOfTimeout(entity -> prinegorerouse.prefix("burning_fire_sa_end"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(2, entityIn -> AttackManager.doSlash(entityIn, -80.0F, Vec3.ZERO, false, false, 0.1D))
                .put(3, entityIn -> BurningFireSA.doSlash(entityIn, 90.0F, 60, Vec3.ZERO, false, false, 2.0F))
                .build())
            .addHitEffect(StunManager::setStun)
            .build());

        BURNING_FIRE_SA_END = NR_COMBO_STATE.register("burning_fire_sa_end", () -> ComboState.Builder.newInstance()
            .startAndEnd(459, 488)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(entity -> SlashBlade.prefix("none"))
            .nextOfTimeout(entity -> SlashBlade.prefix("none"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(0, AttackManager::playQuickSheathSoundAction)
                .build())
            .releaseAction(ComboState::releaseActionQuickCharge)
            .build());

        COSMIC_LINE = NR_COMBO_STATE.register("cosmic_line", () -> ComboState.Builder.newInstance()
            .startAndEnd(400, 459)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(ComboState.TimeoutNext.buildFromFrame(15, entity -> SlashBlade.prefix("none")))
            .nextOfTimeout(entity -> prinegorerouse.prefix("cosmic_line_end"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(2, entityIn -> AttackManager.doSlash(entityIn, -80.0F, Vec3.ZERO, false, false, 0.1D))
                .put(3, entityIn -> CosmicLine.doSlash(entityIn, 90.0F, 100, Vec3.ZERO, false, false, 2.0F))
                .build())
            .addHitEffect(StunManager::setStun)
            .build());

        COSMIC_LINE_END = NR_COMBO_STATE.register("cosmic_line_end", () -> ComboState.Builder.newInstance()
            .startAndEnd(459, 488)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(entity -> SlashBlade.prefix("none"))
            .nextOfTimeout(entity -> SlashBlade.prefix("none"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(0, AttackManager::playQuickSheathSoundAction)
                .build())
            .releaseAction(ComboState::releaseActionQuickCharge)
            .build());

        OVER_THE_HORIZON = NR_COMBO_STATE.register("over_the_horizon", () -> ComboState.Builder.newInstance()
            .startAndEnd(400, 459)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(ComboState.TimeoutNext.buildFromFrame(15, entity -> SlashBlade.prefix("none")))
            .nextOfTimeout(entity -> prinegorerouse.prefix("over_the_horizon_end"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(2, entityIn -> AttackManager.doSlash(entityIn, -80.0F, Vec3.ZERO, false, false, 0.1D))
                .put(3, entityIn -> OverTheHorizon.doSlash(entityIn, 0.0F, 100, 2.0D, KnockBacks.cancel, 2.0F))
                .build())
            .addHitEffect(StunManager::setStun)
            .build());

        OVER_THE_HORIZON_END = NR_COMBO_STATE.register("over_the_horizon_end", () -> ComboState.Builder.newInstance()
            .startAndEnd(459, 488)
            .priority(50)
            .motionLoc(DefaultResources.ExMotionLocation)
            .next(entity -> SlashBlade.prefix("none"))
            .nextOfTimeout(entity -> SlashBlade.prefix("none"))
            .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                .put(0, AttackManager::playQuickSheathSoundAction)
                .build())
            .releaseAction(ComboState::releaseActionQuickCharge)
            .build());
    }
}