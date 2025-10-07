package com.avicario.fasterelytras.mixin;

import com.avicario.fasterelytras.Fasterelytras;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void modifyElytraSpeed(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Solo aplicar a jugadores volando con elytra
        if (entity instanceof PlayerEntity player && entity.isGliding()) {
            Vec3d velocity = player.getVelocity();

            // Aplicar multiplicador de velocidad base
            Vec3d newVelocity = velocity.multiply(Fasterelytras.SPEED_MULTIPLIER);

            // Boost adicional al mirar hacia abajo
            float pitch = player.getPitch();
            if (pitch > 0) {
                double pitchBoost = (pitch / 90.0) * Fasterelytras.BOOST_STRENGTH;
                newVelocity = newVelocity.multiply(1.0 + pitchBoost);
            }

            // Limitar velocidad máxima
            if (newVelocity.length() > Fasterelytras.MAX_SPEED) {
                newVelocity = newVelocity.normalize().multiply(Fasterelytras.MAX_SPEED);
            }

            player.setVelocity(newVelocity);
            player.velocityModified = true;
        }
    }
}