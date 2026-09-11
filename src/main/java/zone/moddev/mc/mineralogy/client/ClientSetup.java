package zone.moddev.mc.mineralogy.client;

import zone.moddev.mc.mineralogy.Mineralogy;
import zone.moddev.mc.mineralogy.init.MineralogyFluids;

import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

@EventBusSubscriber(modid = Mineralogy.MODID, value = Dist.CLIENT)
public final class ClientSetup {
	private static final Identifier CRUDE_OIL_STILL =
			Identifier.fromNamespaceAndPath(Mineralogy.MODID, "blocks/crude_oil_still");
	private static final Identifier CRUDE_OIL_FLOW =
			Identifier.fromNamespaceAndPath(Mineralogy.MODID, "blocks/crude_oil_flow");

    private ClientSetup() {
    }

    @SubscribeEvent
	public static void registerFluidModels(RegisterFluidModelsEvent event) {
		FluidModel.Unbaked model = new FluidModel.Unbaked(
				new Material(CRUDE_OIL_STILL, true),
				new Material(CRUDE_OIL_FLOW, true),
				null,
				null);
		event.register(model, MineralogyFluids.CRUDE_OIL, MineralogyFluids.FLOWING_CRUDE_OIL);
    }
}
