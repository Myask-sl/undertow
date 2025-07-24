package invalid.myask.undertow.client.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.UndertowItems;

public class RenderDrowned extends RenderBiped {
    //Does not extend RenderZombie because it does nothing except switch in ZombieVillager, using a private function.
    //That is not desired, and would be annoying to remove, and with nothing needed therefrom...

    private static final ResourceLocation DROWNED_TEXTURE = new ResourceLocation(Undertow.MODID, "textures/entity/drowned.png");
    private static final ItemStack NAUTILUS = new ItemStack(UndertowItems.NAUTILUS);

    public RenderDrowned(ModelBiped onlyAModel, float shadowsize) {
        this(onlyAModel, shadowsize, 1);
    }

    public RenderDrowned(ModelBiped onlyAModel, float shadowsize, float scaleQ) {
        super(onlyAModel, shadowsize, scaleQ);
    }

    @Override
    protected void func_82421_b() {
        this.field_82423_g = new ModelDrowned(1.0F, false);
        this.field_82425_h = new ModelDrowned(0.5F, false);
    }

    //field_82423_g is adult model; field_82425_h is child model
    /** ModelBiped##func_82420_a:
     * Adjusts heldItem, isSneaking values of models.
     * Here we override to allow nautilus in offhand.
     * @param subject The entity we're looking at.
     * @param mainHandItem The [right/main] held item.
     */
    @Override
    protected void func_82420_a(EntityLiving subject, ItemStack mainHandItem) {
        super.func_82420_a(subject, mainHandItem);
        if (!(subject instanceof EntityDrowned drowned)) return;
        this.field_82423_g.heldItemLeft = this.field_82425_h.heldItemLeft = this.modelBipedMain.heldItemLeft = drowned.hasNautilus() ? 1 : 0;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity zomB) {
        if (zomB instanceof EntityDrowned) return DROWNED_TEXTURE;
        return super.getEntityTexture(zomB);
    }

    @Override
    protected void renderEquippedItems(EntityLiving zomB, float fracTick) {
        super.renderEquippedItems(zomB, fracTick);
        if (zomB instanceof EntityDrowned d && d.hasNautilus()) {
            renderLeftHandItem(d, NAUTILUS, fracTick);
        }
    }

    protected void renderLeftHandItem(EntityDrowned d, ItemStack lhStack, float fracTick) {
        Item lHItem = lhStack.getItem();
        GL11.glPushMatrix();
        float scale;

        if (this.mainModel.isChild)
        {
            scale = 0.5F;
            GL11.glTranslatef(0.0F, 0.625F, 0.0F);
            GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
            GL11.glScalef(scale, scale, scale);
        }

        this.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

        net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(lhStack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
        boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, lhStack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

        if (lHItem instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(lHItem).getRenderType())))
        {
            scale = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            scale *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-scale, -scale, scale);
        }
        else if (lHItem == Items.bow)
        {
            scale = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(scale, -scale, scale);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        }
        else if (lHItem.isFull3D())
        {
            scale = 0.625F;

            if (lHItem.shouldRotateAroundWhenRendering())
            {
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.0F, -0.125F, 0.0F);
            }

            this.func_82422_c();
            GL11.glScalef(scale, -scale, scale);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        }
        else
        {
            scale = 0.375F;
            GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
        }

        float f2;
        int i;
        float f5;

        if (lHItem.requiresMultipleRenderPasses())
        {
            for (i = 0; i < lHItem.getRenderPasses(lhStack.getItemDamage()); ++i)
            {
                int j = lHItem.getColorFromItemStack(lhStack, i);
                f5 = (float)(j >> 16 & 255) / 255.0F;
                f2 = (float)(j >> 8 & 255) / 255.0F;
                float f3 = (float)(j & 255) / 255.0F;
                GL11.glColor4f(f5, f2, f3, 1.0F);
                this.renderManager.itemRenderer.renderItem(d, lhStack, i);
            }
        }
        else
        {
            i = lHItem.getColorFromItemStack(lhStack, 0);
            float f4 = (float)(i >> 16 & 255) / 255.0F;
            f5 = (float)(i >> 8 & 255) / 255.0F;
            f2 = (float)(i & 255) / 255.0F;
            GL11.glColor4f(f4, f5, f2, 1.0F);
            this.renderManager.itemRenderer.renderItem(d, lhStack, 0);
        }

        GL11.glPopMatrix();
    }
}
