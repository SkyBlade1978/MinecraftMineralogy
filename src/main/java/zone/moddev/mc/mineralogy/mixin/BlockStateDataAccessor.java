package zone.moddev.mc.mineralogy.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.BlockStateData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Typed access to the table expanded by {@link BlockStateDataMixin}. */
@Mixin(BlockStateData.class)
public interface BlockStateDataAccessor {
	@Accessor("MAP")
	static Dynamic<?>[] mineralogy$getLegacyStateMap() {
		throw new AssertionError("Mixin accessor was not applied");
	}
}
