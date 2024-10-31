package com.smellysleepy.meadow.client.model.entity;

import com.smellysleepy.meadow.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.*;

public class MooMooModel<T extends Entity> extends QuadrupedModel<T> {

   public MooMooModel(ModelPart pRoot) {
      super(pRoot, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(45, 48).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.25F))
              .texOffs(45, 33).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
              .texOffs(35, 33).addBox(-3.0F, 0.0F, -7.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -8.0F));

      head.addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(0, 8).addBox(4.0F, -23.0F, -12.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
              .texOffs(39, 0).addBox(6.0F, -26.0F, -12.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 8.0F));

      head.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(51, 63).addBox(-6.0F, -23.0F, -12.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
              .texOffs(0, 33).addBox(-8.0F, -26.0F, -12.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 8.0F));

      partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 33).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).addBox(-6.0F, -10.0F, -11.0F, 12.0F, 18.0F, 14.0F, new CubeDeformation(0.25F))
              .texOffs(0, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

      var cubeListBuilder = CubeListBuilder.create()
              .texOffs(0, 62).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
              .texOffs(47, 69).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F));
      partdefinition.addOrReplaceChild("right_hind_leg", cubeListBuilder, PartPose.offset(-4.0F, 12.0F, 7.0F));
      partdefinition.addOrReplaceChild("left_hind_leg", cubeListBuilder, PartPose.offset(4.0F, 12.0F, 7.0F));
      partdefinition.addOrReplaceChild("right_front_leg", cubeListBuilder, PartPose.offset(-4.0F, 12.0F, -6.0F));
      partdefinition.addOrReplaceChild("left_front_leg", cubeListBuilder, PartPose.offset(4.0F, 12.0F, -6.0F));

//		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(53, 23).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
//		.texOffs(53, 17).addBox(-3.0F, 0.1F, 5.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 11.0F));
//

      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public ModelPart getHead() {
      return this.head;
   }
}