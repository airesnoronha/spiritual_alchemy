package com.lazydragonstudios.spiritual_alchemy.block;

import com.lazydragonstudios.spiritual_alchemy.block.entity.SpiritualTransmutatorEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpiritualTransmutator extends BaseEntityBlock {

	private final BigDecimal maxStoredEssence;

	public static final VoxelShape VOXEL_SHAPE = Shapes.or(
			Block.box(3, 0, 3, 13, 1, 13), //legs
			Block.box(5, 1, 5, 11, 12, 11),
			Block.box(0, 12, 0, 16, 14, 16),// tabletop
			Block.box(8, 14, 1, 12, 16, 5), // bows
			Block.box(11, 14, 6, 15, 16, 10),
			Block.box(1, 14, 3, 5, 16, 7),
			Block.box(8, 14, 11, 12, 16, 15),
			Block.box(1, 14, 9, 5, 16, 13)
	);

	public SpiritualTransmutator(Properties pProperties, BigDecimal maxStoredEssence) {
		super(pProperties);
		this.maxStoredEssence = maxStoredEssence;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpiritualTransmutatorEntity(pos, state, this.maxStoredEssence);
	}

	public BigDecimal getMaxStoredEssence() {
		return maxStoredEssence;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			this.openContainer(level, pos, (ServerPlayer) player);
			return InteractionResult.CONSUME;
		}
	}

	protected void openContainer(Level level, BlockPos pos, ServerPlayer player) {
		BlockEntity blockentity = level.getBlockEntity(pos);
		if (blockentity instanceof SpiritualTransmutatorEntity) {
			NetworkHooks.openScreen(player, (MenuProvider) blockentity, pos);
		}
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return VOXEL_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return VOXEL_SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

}
