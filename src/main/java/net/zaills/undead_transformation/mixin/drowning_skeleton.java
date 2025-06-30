package net.zaills.undead_transformation.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.entity.mob.SkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public class drowning_skeleton {

	@Unique
	private int inWaterTime;

	@Inject(at = @At("TAIL"), method = "tick()V")
	private void tick(CallbackInfo info) {
		SkeletonEntity entity = (SkeletonEntity) (Object) this;
		if (entity.isTouchingWater()) {
			inWaterTime++;
			if (inWaterTime > 500) {
				entity.convertTo(EntityType.BOGGED, EntityConversionContext.create(entity, true, true), (bogged) -> {
					if (!entity.isSilent()) {
						entity.getWorld().syncWorldEvent((Entity) null, 1048, entity.getBlockPos(), 0);
					}
				});
				inWaterTime = 0;
			}
		} else {
			inWaterTime = 0;
		}
	}


}
