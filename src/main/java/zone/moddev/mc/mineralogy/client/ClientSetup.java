package zone.moddev.mc.mineralogy.client;

import zone.moddev.mc.mineralogy.Mineralogy;
import zone.moddev.mc.mineralogy.init.MineralogyFluids;

import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Mineralogy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
	private static final Identifier CRUDE_OIL_STILL =
			Identifier.fromNamespaceAndPath(Mineralogy.MODID, "blocks/crude_oil_still");
	private static final Identifier CRUDE_OIL_FLOW =
			Identifier.fromNamespaceAndPath(Mineralogy.MODID, "blocks/crude_oil_flow");

    private ClientSetup() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
		ModelEvent.BakeFluidModels.BUS.addListener(ClientSetup::bakeFluidModels);
    }

	private static void bakeFluidModels(ModelEvent.BakeFluidModels event) {
		FluidModel model = new FluidModel.Unbaked(
				new Material(CRUDE_OIL_STILL),
				new Material(CRUDE_OIL_FLOW),
				null,
				null)
				.bake(event.materials(), () -> "Mineralogy crude oil");
		event.register(MineralogyFluids.CRUDE_OIL.get(), model);
		event.register(MineralogyFluids.FLOWING_CRUDE_OIL.get(), model);
	}
}
