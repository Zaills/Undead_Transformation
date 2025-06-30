package net.zaills.undead_transformation.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public abstract class burn_zombie {

	@Shadow protected abstract void convertTo(EntityType<? extends ZombieEntity> entityType);

	@Inject(at = @At("HEAD"), method = "tick()V")
	private void tick(CallbackInfo info) {
		ZombieEntity entity = (ZombieEntity) (Object) this;
		if (entity.isOnFire() && entity.getHealth() <= 0) {
			this.convertTo(EntityType.HUSK);
			entity.setHealth(entity.getMaxHealth());
		}
	}
}


