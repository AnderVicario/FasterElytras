package com.avicario.fasterelytras.mixin;

import com.avicario.fasterelytras.config.FlightConfig;
import com.avicario.fasterelytras.utility.EanMath;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin {

    @Shadow
    private @Nullable LivingEntity shooter;

    private FlightConfig configInstance = FlightConfig.getOrCreateInstance();

    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                    ordinal = 0
            )
    )
    private Vec3d modifyRocketBoostVelocity(Vec3d originalVelocity) {
        if (configInstance.isAltitudeDeterminesSpeed() && shooter != null) {
            // Obtener valores de movimiento y posición del jugador
            Vec3d positionVector = shooter.getPos();
            Vec3d shooterRotation = shooter.getRotationVector();
            Vec3d shooterVelocity = shooter.getVelocity();
            double shooterAltitude = positionVector.y;

            // Leer valores de configuración
            double minSpeed = configInstance.getMinSpeed();
            double maxSpeed = configInstance.getMaxSpeed();
            double curveStart = configInstance.getMinHeight();
            double curveEnd = configInstance.getMaxHeight();

            // Calcular velocidad basada en altitud
            double altitudeCalculatedSpeed = MathHelper.clamp(
                    EanMath.getLinealValue(curveStart, minSpeed, curveEnd, maxSpeed, shooterAltitude),
                    minSpeed,
                    maxSpeed
            );

            // Ecuación que determina el multiplicador de velocidad
            double speedMultiplier = 0.0000006453840919839 * Math.pow(altitudeCalculatedSpeed, 2) +
                    0.0508467 * altitudeCalculatedSpeed -
                    0.202377;

            // Aplicar velocidad adicional al movimiento del jugador
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