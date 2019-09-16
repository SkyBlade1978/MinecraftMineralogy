package cyano.mineralogy.blocks;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RockRelief extends RockSlab {
private static float thickness = 0.07f;

	
	private static final AxisAlignedBB[] BOXES = new AxisAlignedBB[EnumFacing.values().length];
	static {
		for(int i = 0; i < EnumFacing.values().length; i++) {
			EnumFacing orientation = EnumFacing.values()[i];
			float x1 = 0, x2 = 1, y1 = 0,y2 = 1, z1 = 0, z2 = 1;
			switch(orientation) {
				case DOWN:
					y1 = 1f - thickness;
					break;
				case SOUTH:
					z2 = thickness;
					break;
				case NORTH:
					z1 = 1f - thickness;
					break;
				case EAST:
					x2 = thickness;
					break;
				case WEST:
					x1 = 1f - thickness;
					break;
				case UP:
				default:
					y2 = thickness;
					break;
			}
			BOXES[orientation.ordinal()] = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
		}
	}
	
	public RockRelief(float hardness, float blastResistance, int toolHardnessLevel, SoundType sound) {
		super(hardness, blastResistance, toolHardnessLevel, sound);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState bs, final IBlockAccess world, final BlockPos coord) {
		final EnumFacing orientation = (EnumFacing) bs.getValue(FACING);
		return BOXES[orientation.ordinal()];
	}

	@Override
	public void addCollisionBoxToList(final IBlockState bs, final World world, final BlockPos coord,
									  final AxisAlignedBB box, final List<AxisAlignedBB> collisionBoxList,
									  final Entity entity) {

		final EnumFacing orientation = (EnumFacing) world.getBlockState(coord).getValue(FACING);
		super.addCollisionBoxToList(coord, box, collisionBoxList, BOXES[orientation.ordinal()]);
	}
}
