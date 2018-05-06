package net.geforcemods.securitycraft.renderers;

import org.lwjgl.opengl.GL11;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.models.ModelClaymore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityClaymoreRenderer extends TileEntitySpecialRenderer {

	private ModelClaymore claymoreModel;
	private ResourceLocation texture = new ResourceLocation("securitycraft:textures/blocks/claymore.png");

	public TileEntityClaymoreRenderer() {
		claymoreModel = new ModelClaymore();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
		claymoreModel.setActive(te.hasWorldObj() && te.blockType != null && te.blockType == SCContent.claymoreActive);
		int meta = te.hasWorldObj() ? te.getBlockMetadata() : te.blockMetadata;
		float rotation = 0F;

		if(te.hasWorldObj()){
			Tessellator tessellator = Tessellator.instance;
			float brightness = te.getWorld().getLightBrightness(te.xCoord, te.yCoord, te.zCoord);
			int skyBrightness = te.getWorld().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
			int l1 = skyBrightness % 65536;
			int l2 = skyBrightness / 65536;
			tessellator.setColorOpaque_F(brightness, brightness, brightness);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glPushMatrix();

		if(meta == 1)
			rotation = 0F;
		else if(meta == 2)
			rotation = 1F;
		else if(meta == 3)
			rotation = -10000F;
		else if(meta == 4)
			rotation = -1F;

		GL11.glRotatef(180F, rotation, 0.0F, 1.0F);

		claymoreModel.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
