package com.avicario.fasterelytras.mixin;

import com.avicario.fasterelytras.config.FlightConfig;
import com.avicario.fasterelytras.utility.MathUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin {

    @Shadow
    private @Nullable LivingEntity attachedToEntity;

    @Unique
    private FlightConfig configInstance = FlightConfig.getOrCreateInstance();

    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V",
                    ordinal = 0
            )
    )
    private Vec3 modifyRocketBoostVelocity(Vec3 originalVelocity) {
        if (attachedToEntity != null && attachedToEntity.isFallFlying()) {
            Vec3 shooterRotation = attachedToEntity.getLookAngle();
            Vec3 shooterVelocity = attachedToEntity.getDeltaMovement();

            double speedMultiplier;

            if (configInstance.isAltitudeDeterminesSpeed()) {
                Vec3 positionVector = attachedToEntity.position();
                double shooterAltitude = positionVector.y;

                double minSpeed = configInstance.getMinSpeed();
                double maxSpeed = configInstance.getMaxSpeed();
                double curveStart = configInstance.getMinHeight();
                double curveEnd = configInstance.getMaxHeight();

                double altitudeCalculatedSpeed = Mth.clamp(
                        MathUtil.getLinealValue(curveStart, minSpeed, curveEnd, maxSpeed, shooterAltitude),
                        minSpeed,
                        maxSpeed
                );

                speedMultiplier = 0.0000006453840919839 * Math.pow(altitudeCalculatedSpeed, 2) +
                        0.0508467 * altitudeCalculatedSpeed -
                        0.202377;
            } else {
                double fixedSpeed = configInstance.getMaxSpeed();

                speedMultiplier = 0.0000006453840919839 * Math.pow(fixedSpeed, 2) +
                        0.0508467 * fixedSpeed -
                        0.202377;
            }

            return shooterVelocity.add(
                    shooterRotation.x * 0.1 + (shooterRotation.x * speedMultiplier - shooterVelocity.x) * 0.5,
                    shooterRotation.y * 0.1 + (shooterRotation.y * speedMultiplier - shooterVelocity.y) * 0.5,
                    shooterRotation.z * 0.1 + (shooterRotation.z * speedMultiplier - shooterVelocity.z) * 0.5
            );
        } else {
            return originalVelocity;
        }
    }
}