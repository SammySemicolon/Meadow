package com.smellysleepy.meadow.client.renderer.entity;

import com.smellysleepy.meadow.*;
import com.smellysleepy.meadow.client.model.entity.*;
import com.smellysleepy.meadow.common.entity.*;
import com.smellysleepy.meadow.registry.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class MooMooCowRenderer extends MobRenderer<MooMooCow, MooMooModel<MooMooCow>> {
    private static final ResourceLocation MOO_MOO_LOCATION = MeadowMod.meadowModPath("textures/entity/moo_moo/moo_moo.png");

    public MooMooCowRenderer(EntityRendererProvider.Context p_173956_) {
        super(p_173956_, new MooMooModel<>(p_173956_.bakeLayer(MeadowModelLayers.MOO_MOO)), 0.7F);
    }

    public ResourceLocation getTextureLocation(MooMooCow pEntity) {
        return MOO_MOO_LOCATION;
    }
}
