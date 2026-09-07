package zone.moddev.mc.mineralogy.mixin;

import net.minecraft.server.ReloadableServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zone.moddev.mc.mineralogy.compat.CobblestoneTagPolicy;

/**
 * Forge 61.1 leaves the server-side TagsUpdatedEvent call disabled. Rebind the
 * affected named sets immediately after vanilla commits the pending tag load,
 * which also covers every later /reload.
 */
@Mixin(ReloadableServerResources.class)
public abstract class ReloadableServerResourcesMixin {
    @Inject(method = "updateStaticRegistryTags", at = @At("TAIL"))
    private void mineralogy$rebindCobblestoneTags(CallbackInfo callback) {
        ReloadableServerResources resources = (ReloadableServerResources) (Object) this;
        CobblestoneTagPolicy.apply(resources.fullRegistries().lookup());
    }
}
