package com.smellysleepy.meadow.common.block.meadow.flora;

import com.smellysleepy.meadow.common.block.meadow.wood.ThinMeadowLogBlock;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.systems.easing.Easing;

import java.awt.*;

public class MeadowGrassBlock extends Block {

    public static final EnumProperty<MeadowGrassType> GRASS_TYPE = EnumProperty.create("grass_type", MeadowGrassType.class);

    public MeadowGrassBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(GRASS_TYPE, MeadowGrassType.DEFAULT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(GRASS_TYPE);
    }

    public enum MeadowGrassType implements StringRepresentable {
        DEFAULT("default", null),
        SNOWY("snowy", null),
        GREEN_0("green_0", ColorHelper.colorLerp(Easing.LINEAR, 0.25f, Color.YELLOW, Color.GREEN)),
        GREEN_1("green_1", ColorHelper.colorLerp(Easing.LINEAR, 0.5f, Color.YELLOW, Color.GREEN)),
        GREEN_2("green_2", ColorHelper.colorLerp(Easing.LINEAR, 0.75f, Color.YELLOW, Color.GREEN)),
        BLUE_0("blue_0", new Color(67, 201, 184)),
        BLUE_1("blue_1", new Color(67, 114, 201)),
        BLUE_2("blue_2", new Color(23, 47, 158));

        private final String name;
        public final Color color;
        MeadowGrassType(String pName, Color color) {
            this.name = pName;
            this.color = color;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
