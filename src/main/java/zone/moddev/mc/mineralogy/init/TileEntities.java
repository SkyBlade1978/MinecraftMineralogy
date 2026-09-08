package zone.moddev.mc.mineralogy.init;

import java.util.ArrayList;
import java.util.List;

import zone.moddev.mc.mineralogy.Mineralogy;
import zone.moddev.mc.mineralogy.blocks.RockFurnace;
import zone.moddev.mc.mineralogy.tileentity.TileEntityRockFurnace;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.minecraft.resources.ResourceLocation;

@EventBusSubscriber(modid = Mineralogy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TileEntities {
	public static BlockEntityType<TileEntityRockFurnace> rock_furnace;

	@SubscribeEvent
	public static void registerTileEntities(RegisterEvent event) {
		if (!Registries.BLOCK_ENTITY_TYPE.equals(event.getRegistryKey())) {
			return;
		}
		List<Block> furnaceBlocks = new ArrayList<Block>();
		for (Block block : BuiltInRegistries.BLOCK.stream().toList()) {
			if (block instanceof RockFurnace) {
				furnaceBlocks.add(block);
			}
		}

		rock_furnace = BlockEntityType.Builder
				.of(TileEntityRockFurnace::new, furnaceBlocks.toArray(new Block[furnaceBlocks.size()]))
				.build(null);
		event.register(Registries.BLOCK_ENTITY_TYPE,
				new ResourceLocation(Mineralogy.MODID, "rock_furnace"),
				() -> rock_furnace);
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, rock_furnace,
				(furnace, side) -> furnace.getItemHandler(side));
	}

	private TileEntities() {
		throw new IllegalAccessError("Not an instantiable class");
	}
}
