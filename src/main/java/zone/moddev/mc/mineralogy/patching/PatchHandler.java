package zone.moddev.mc.mineralogy.patching;

import zone.moddev.mc.mineralogy.Mineralogy;
import zone.moddev.mc.mineralogy.MineralogyConfig;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Registers the exact historical IDs as NeoForge registry aliases. */
public final class PatchHandler {
    private static final Identifier SAPROLITE = mineralogyId("saprolite");
    private static final Identifier PUMMICE = mineralogyId("pummice");
    private static final Identifier LIMESTONE = mineralogyId("limestone");
    private static final Identifier PUMICE = mineralogyId("pumice");
    private static final Identifier GRASS_PATH = minecraftId("grass_path");
    private static final Identifier DIRT_PATH = minecraftId("dirt_path");
    private static final Identifier SWEET_BERRIES_PICK = minecraftId("item.sweet_berries.pick_from_bush");
    private static final Identifier SWEET_BERRY_BUSH_PICK = minecraftId("block.sweet_berry_bush.pick_berries");

    private static final DeferredRegister<Block> BLOCK_ALIASES =
            DeferredRegister.create(BuiltInRegistries.BLOCK, Mineralogy.MODID);
    private static final DeferredRegister<Item> ITEM_ALIASES =
            DeferredRegister.create(BuiltInRegistries.ITEM, Mineralogy.MODID);
    private static final DeferredRegister<SoundEvent> SOUND_ALIASES =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Mineralogy.MODID);

    private PatchHandler() {
        throw new IllegalAccessError("Not an instantiable class");
    }

    public static void registerAliases(IEventBus modBus) {
        if (MineralogyConfig.patchUpdate()) {
            BLOCK_ALIASES.addAlias(SAPROLITE, LIMESTONE);
            BLOCK_ALIASES.addAlias(PUMMICE, PUMICE);
            BLOCK_ALIASES.addAlias(GRASS_PATH, DIRT_PATH);
            ITEM_ALIASES.addAlias(SAPROLITE, LIMESTONE);
            ITEM_ALIASES.addAlias(PUMMICE, PUMICE);
            ITEM_ALIASES.addAlias(GRASS_PATH, DIRT_PATH);
            SOUND_ALIASES.addAlias(SWEET_BERRIES_PICK, SWEET_BERRY_BUSH_PICK);
        }
        BLOCK_ALIASES.register(modBus);
        ITEM_ALIASES.register(modBus);
        SOUND_ALIASES.register(modBus);
    }

    private static Identifier mineralogyId(String path) {
        return Identifier.fromNamespaceAndPath(Mineralogy.MODID, path);
    }

    private static Identifier minecraftId(String path) {
        return Identifier.fromNamespaceAndPath("minecraft", path);
    }
}
