package zone.moddev.mc.mineralogy.blocks;

import zone.moddev.mc.mineralogy.init.RegistrationProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class DryWall extends IronBarsBlock implements NamedMineralogyBlock {
	private final int toolHardnessLevel;
	private final String registryPath;

	public DryWall(String color) {
		super(RegistrationProperties.block(
				BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STONE)
						.strength(0.75F, 1.0F).sound(SoundType.STONE).requiresCorrectToolForDrops(),
				"drywall_" + color));
		this.toolHardnessLevel = 0;
		this.registryPath = "drywall_" + color;
	}

	@Override
	public String mineralogyRegistryPath() {
		return registryPath;
	}
}
