package net.xianyu.prinegorerouse.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mods.flammpfeil.slashblade.client.renderer.model.BladeModelManager;
import mods.flammpfeil.slashblade.client.renderer.model.obj.WavefrontObject;
import mods.flammpfeil.slashblade.client.renderer.util.BladeRenderState;
import mods.flammpfeil.slashblade.client.renderer.util.MSAutoCloser;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xianyu.prinegorerouse.entity.EntityNRJudgementCut;

import javax.annotation.Nullable;
import java.awt.Color;

@OnlyIn(Dist.CLIENT)
public class NRJudgementCutRenderer<T extends EntityNRJudgementCut> extends EntityRenderer<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("slashblade", "model/util/slashdim.png");
    private static final ResourceLocation MODEL = new ResourceLocation("slashblade", "model/util/slashdim.obj");

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }

    public NRJudgementCutRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        try (MSAutoCloser msac = MSAutoCloser.pushMatrix(matrixStackIn)) {
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.rotLerp(partialTicks, entity.xRotO, entity.getXRot())));
            
            WavefrontObject model = BladeModelManager.getInstance().getModel(MODEL);
            int lifetime = entity.getLifetime();
            double deathTime = lifetime;
            double baseAlpha = Math.min(deathTime, Math.max(0.0F, (lifetime - entity.tickCount) - partialTicks)) / deathTime;
            baseAlpha = -Math.pow(baseAlpha - 1.0D, 4.0D) + 1.0D;
            
            int seed = entity.getSeed();
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(seed));
            
            float scale = entity.getRenderScale();
            matrixStackIn.scale(scale, scale, scale);
            
            int color = entity.getColor() & 0xFFFFFF;
            Color col = new Color(color);
            float[] hsb = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null);
            int baseColor = Color.HSBtoRGB(0.5F + hsb[0], hsb[1], 0.2F) & 0xFFFFFF;
            
            try (MSAutoCloser msacB = MSAutoCloser.pushMatrix(matrixStackIn)) {
                for (int j = 0; j < 5; j++) {
                    matrixStackIn.scale(0.95F, 0.95F, 0.95F);
                    BladeRenderState.setCol(baseColor | ((0xFF & (int)(102.0D * baseAlpha)) << 24));
                    BladeRenderState.renderOverridedReverseLuminous(ItemStack.EMPTY, model, "base", 
                            getTextureLocation(entity), matrixStackIn, bufferIn, packedLightIn);
                }
            }
            
            int loop = 3;
            for (int l = 0; l < loop; l++) {
                try (MSAutoCloser mSAutoCloser = MSAutoCloser.pushMatrix(matrixStackIn)) {
                    float cycleTicks = 15.0F;
                    float wave = (entity.tickCount + cycleTicks / loop * l + partialTicks) % cycleTicks;
                    float waveScale = 1.0F + 0.03F * wave;
                    matrixStackIn.scale(waveScale, waveScale, waveScale);
                    BladeRenderState.setCol(baseColor | ((int)((136.0F * (cycleTicks - wave) / cycleTicks) * baseAlpha) << 24));
                    BladeRenderState.renderOverridedReverseLuminous(ItemStack.EMPTY, model, "base", 
                            getTextureLocation(entity), matrixStackIn, bufferIn, packedLightIn);
                }
            }
            
            int windCount = 5;
            for (int i = 0; i < windCount; i++) {
                try (MSAutoCloser mSAutoCloser = MSAutoCloser.pushMatrix(matrixStackIn)) {
                    matrixStackIn.mulPose(Axis.XP.rotationDegrees(360.0F / windCount * i));
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(30.0F));
                    
                    double rotWind = 18.0D;
                    double offsetBase = 7.0D;
                    double offset = i * offsetBase;
                    double motionLen = offsetBase * (windCount - 1);
                    double ticks = (entity.tickCount + partialTicks + seed);
                    double offsetTicks = ticks + offset;
                    double progress = offsetTicks % motionLen / motionLen;
                    double rad = 6.283185307179586D * progress;
                    
                    float windScale = (float)(0.4D + progress);
                    matrixStackIn.scale(windScale, windScale, windScale);
                    matrixStackIn.mulPose(Axis.ZP.rotationDegrees((float)(rotWind * offsetTicks)));
                    
                    Color cc = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0xFF & (int)Math.min(255.0D, 255.0D * Math.sin(rad) * baseAlpha));
                    BladeRenderState.setCol(cc.getRGB());
                    BladeRenderState.renderOverridedColorWrite(ItemStack.EMPTY, model, "wind", 
                            getTextureLocation(entity), matrixStackIn, bufferIn, 15728864);
                }
            }
        }
    }
}