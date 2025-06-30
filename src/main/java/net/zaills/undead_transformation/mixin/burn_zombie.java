package net.zaills.undead_transformation.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class burn_zombie {

	@Unique
	private boolean attackedByPlayer = false;

	@Shadow protected abstract void convertTo(EntityType<? extends ZombieEntity> entityType);

	@Inject(at = @At("HEAD"), method = "damage")
	private void onAttackedByPlayer(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!attackedByPlayer && source.getAttacker() instanceof PlayerEntity) {
			attackedByPlayer = true;
		}
	}

	@Inject(at = @At("HEAD"), method = "tick()V")
	private void tick(CallbackInfo info) {
		ZombieEntity entity = (ZombieEntity) (Object) this;
		if (entity.isOnFire() && entity.getHealth() <= 1 && !attackedByPlayer) {
			this.convertTo(EntityType.HUSK);
			entity.setHealth(entity.getMaxHealth());
		}
	}
}


