package zone.moddev.mc.mineralogy.client;

import zone.moddev.mc.mineralogy.Mineralogy;
import zone.moddev.mc.mineralogy.init.MineralogyFluids;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Mineralogy.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
    private ClientSetup() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Block render types are declared by their model JSON on NeoForge 21.1.
        // Fluid render layers still use the target-native client registration API.
        ItemBlockRenderTypes.setRenderLayer(MineralogyFluids.CRUDE_OIL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MineralogyFluids.FLOWING_CRUDE_OIL.get(), RenderType.translucent());
    }
}
