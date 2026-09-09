package zone.moddev.mc.mineralogy.blocks;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;

public class MineralogyLiquidBlock extends LiquidBlock {
	public MineralogyLiquidBlock(java.util.function.Supplier<? extends FlowingFluid> fluid,
			BlockBehaviour.Properties properties) {
		super(fluid.get(), properties);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return !state.getFluidState().is(FluidTags.LAVA);
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
		return adjacentState.getFluidState().getType().isSame(fluid);
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
		scheduleFluidTick(world, world, pos, state);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess ticks,
			BlockPos pos, Direction direction, BlockPos adjacentPos, BlockState adjacentState,
			RandomSource random) {
		if (state.getFluidState().isSource() || adjacentState.getFluidState().isSource()) {
			scheduleFluidTick(world, ticks, pos, state);
		}
		return state;
	}

	@Override
	protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block block,
			@Nullable Orientation orientation, boolean isMoving) {
		scheduleFluidTick(world, world, pos, state);
	}

	@Override
	public ItemStack pickupBlock(@Nullable LivingEntity player, LevelAccessor world, BlockPos pos, BlockState state) {
		if (state.getValue(LEVEL) == 0) {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
			return new ItemStack(fluid.getBucket());
		}
		return ItemStack.EMPTY;
	}

	@Override
	public Optional<SoundEvent> getPickupSound() {
		return fluid.getPickupSound();
	}

	private void scheduleFluidTick(LevelReader world, ScheduledTickAccess ticks, BlockPos pos, BlockState state) {
		ticks.scheduleTick(pos, state.getFluidState().getType(), fluid.getTickDelay(world));
	}
}
