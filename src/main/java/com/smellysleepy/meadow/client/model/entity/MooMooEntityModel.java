//package com.smellysleepy.grassy.client.model.entity;// Made with Blockbench 4.10.4
//// Exported for Minecraft version 1.17 or later with Mojang mappings
//// Paste this class into your mod and generate all required imports
//
//
//public class MooMooEntityModel<T extends Entity> extends EntityModel<T> {
//	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
//	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "moomoo_- converted"), "main");
//	private final ModelPart head;
//	private final ModelPart horn1;
//	private final ModelPart horn2;
//	private final ModelPart body;
//	private final ModelPart leg1;
//	private final ModelPart leg2;
//	private final ModelPart leg3;
//	private final ModelPart leg4;
//	private final ModelPart tail;
//
//	public MooMooEntityModel(ModelPart root) {
//		this.head = root.getChild("head");
//		this.horn1 = root.getChild("horn1");
//		this.horn2 = root.getChild("horn2");
//		this.body = root.getChild("body");
//		this.leg1 = root.getChild("leg1");
//		this.leg2 = root.getChild("leg2");
//		this.leg3 = root.getChild("leg3");
//		this.leg4 = root.getChild("leg4");
//		this.tail = root.getChild("tail");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(45, 48).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.25F))
//		.texOffs(45, 33).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
//		.texOffs(35, 33).addBox(-3.0F, 0.0F, -7.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -8.0F));
//
//		PartDefinition horn1 = head.addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(0, 8).addBox(4.0F, -23.0F, -12.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(39, 0).addBox(6.0F, -26.0F, -12.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 8.0F));
//
//		PartDefinition horn2 = head.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(51, 63).addBox(-6.0F, -23.0F, -12.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 33).addBox(-8.0F, -26.0F, -12.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 8.0F));
//
//		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 33).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(-6.0F, -10.0F, -11.0F, 12.0F, 18.0F, 14.0F, new CubeDeformation(0.25F))
//		.texOffs(0, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
//
//		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 62).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
//		.texOffs(47, 69).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-4.0F, 12.0F, 7.0F));
//
//		PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 62).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
//		.texOffs(47, 69).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(4.0F, 12.0F, 7.0F));
//
//		PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 62).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
//		.texOffs(47, 69).addBox(-2.0F, 9.0F, -1.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-4.0F, 12.0F, -6.0F));
//
//		PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 62).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
//		.texOffs(47, 69).addBox(-2.0F, 9.0F, -1.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(4.0F, 12.0F, -6.0F));
//
//		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(53, 23).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
//		.texOffs(53, 17).addBox(-3.0F, 0.1F, 5.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 11.0F));
//
//		return LayerDefinition.create(meshdefinition, 128, 128);
//	}
//
//	@Override
//	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//	}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}