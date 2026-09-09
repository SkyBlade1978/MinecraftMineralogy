package zone.moddev.mc.mineralogy.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.moddev.mc.mineralogy.patching.LegacyWorldDataHook;

/** Prevents generation from rewriting blocks in chunks imported from pre-flattening worlds. */
@Mixin(WorldGenRegion.class)
abstract class WorldGenRegionMixin {
	@Inject(method = "ensureCanWrite", at = @At("HEAD"), cancellable = true)
	private void mineralogy$protectLegacyChunk(BlockPos position, CallbackInfoReturnable<Boolean> callback) {
		if (LegacyWorldDataHook.shouldBlockWorldgenWrite(position)) {
			callback.setReturnValue(false);
		}
	}
}
