package zone.moddev.mc.mineralogy.patching;

import java.util.List;

import zone.moddev.mc.mineralogy.Mineralogy;
import zone.moddev.mc.mineralogy.MineralogyConfig;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.resources.Identifier;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Mineralogy.MODID)
public final class PatchHandler {
	private static final Logger LOGGER = LogManager.getLogger();

	private static final Identifier SAPROLITE = mineralogyId("saprolite");
	private static final Identifier PUMMICE = mineralogyId("pummice");
	private static final Identifier LIMESTONE = mineralogyId("limestone");
	private static final Identifier PUMICE = mineralogyId("pumice");
	private static final Identifier GRASS_PATH = minecraftId("grass_path");
	private static final Identifier DIRT_PATH = minecraftId("dirt_path");
	private static final Identifier SWEET_BERRIES_PICK = minecraftId("item.sweet_berries.pick_from_bush");
	private static final Identifier SWEET_BERRY_BUSH_PICK = minecraftId("block.sweet_berry_bush.pick_berries");

	private PatchHandler() {
		throw new IllegalAccessError("Not an instantiable class");
	}

	@SubscribeEvent
	public static void remapMissingMappings(MissingMappingsEvent event) {
		if (!MineralogyConfig.patchUpdate()) {
			return;
		}

		remapMissing(event.getMappings(ForgeRegistries.Keys.BLOCKS, Mineralogy.MODID),
				SAPROLITE, ForgeRegistries.BLOCKS.getValue(LIMESTONE));
		remapMissing(event.getMappings(ForgeRegistries.Keys.BLOCKS, Mineralogy.MODID),
				PUMMICE, ForgeRegistries.BLOCKS.getValue(PUMICE));
		remapMissing(event.getMappings(ForgeRegistries.Keys.ITEMS, Mineralogy.MODID),
				SAPROLITE, ForgeRegistries.ITEMS.getValue(LIMESTONE));
		remapMissing(event.getMappings(ForgeRegistries.Keys.ITEMS, Mineralogy.MODID),
				PUMMICE, ForgeRegistries.ITEMS.getValue(PUMICE));
		remapMissing(event.getMappings(ForgeRegistries.Keys.BLOCKS, "minecraft"),
				GRASS_PATH, ForgeRegistries.BLOCKS.getValue(DIRT_PATH));
		remapMissing(event.getMappings(ForgeRegistries.Keys.ITEMS, "minecraft"),
				GRASS_PATH, ForgeRegistries.ITEMS.getValue(DIRT_PATH));
		remapMissing(event.getMappings(ForgeRegistries.Keys.SOUND_EVENTS, "minecraft"),
				SWEET_BERRIES_PICK, ForgeRegistries.SOUND_EVENTS.getValue(SWEET_BERRY_BUSH_PICK));
	}

	private static <T> void remapMissing(List<MissingMappingsEvent.Mapping<T>> mappings,
			Identifier oldId, T replacement) {
		for (MissingMappingsEvent.Mapping<T> mapping : mappings) {
			if (!oldId.equals(mapping.getKey())) {
				continue;
			}

			if (replacement != null) {
				mapping.remap(replacement);
				LOGGER.info("Remapped legacy Mineralogy id '{}'", oldId);
			} else {
				mapping.warn();
				LOGGER.warn("Could not remap legacy Mineralogy id '{}' because the replacement is not registered", oldId);
			}
		}
	}

	private static Identifier mineralogyId(String path) {
		return Identifier.fromNamespaceAndPath(Mineralogy.MODID, path);
	}

	private static Identifier minecraftId(String path) {
		return Identifier.fromNamespaceAndPath("minecraft", path);
	}
}
