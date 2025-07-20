package invalid.myask.undertow.events;

import java.lang.reflect.InvocationTargetException;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.client.PoseHelper;
import invalid.myask.undertow.client.RenderFramedMap;
import invalid.myask.undertow.entities.EntityCalledLightning;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.entities.ProjectileTrident;
import invalid.myask.undertow.item.IBackportedMap;
import invalid.myask.undertow.item.ItemShield;
import invalid.myask.undertow.item.ItemTrident;
import invalid.myask.undertow.UndertowItems;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class HearTheOcean {
    public static final HearTheOcean instance = new HearTheOcean();
    public static final int TICKS_UNTIL_CONVERSION = 600;
    public static final int TICKS_BEFORE_DROWN = 300;
    public static final int TICKS_TO_CONVERT = 300;

    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(instance);
    }

    @SubscribeEvent
    public void handleTransitionsAndBlocks(LivingAttackEvent event) {//TODO: mixin EntityZombie##decreaseAirSupply instead
        EntityLivingBase vic = event.entityLiving;
        if (event.source.damageType == "drown" && event.entityLiving instanceof EntityZombie zombie) {
            if (zombie instanceof EntityDrowned) {
                event.setCanceled(true); //shouldn't be a thing...
                return;
            } else if (zombie.isVillager()) return;
            int t = zombie.getEntityData().getInteger("drowntime") + 1;
            if (t > TICKS_UNTIL_CONVERSION - TICKS_BEFORE_DROWN) //TODO: mixin EntityZombie##decreaseAirSupply instead
            {
                zombie.getDataWatcher().updateObject(14, (byte) 3);
            } else zombie.getEntityData().setInteger("drowntime", t);
        } else if (!event.source.isDamageAbsolute() && !event.source.isUnblockable()
            && event.source instanceof EntityDamageSource eds
            && event.entityLiving instanceof EntityPlayer p && p.isUsingItem()) {
            ItemStack stack = p.getItemInUse(); //oh hey, free Backhand compat

            if (stack.getItem() instanceof ItemShield shield) {
                Entity hitWith = eds.getSourceOfDamage();
                Vec3 lookVec = vic.getLookVec();
                double dX = hitWith.posX - vic.posX, dY = hitWith.posY - vic.posY, dZ = hitWith.posZ - vic.posZ,
                    dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                        + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                boolean fromAhead = dotProduct >= 0;
                if (!fromAhead) {
                    if (eds instanceof EntityDamageSourceIndirect indirSource && indirSource.isProjectile() && hitWith.ticksExisted < 5) {
                        //hack for arrows and ghast fireballs, since they add delta-X to init pos in skeleton-used ctor that can make them start "past" you
                        //ctors are not as mixinable as most things
                        dotProduct = hitWith.motionX * lookVec.xCoord + hitWith.motionZ * lookVec.zCoord
                            + (Config.shield_pitch_matters ? hitWith.motionY * lookVec.yCoord : 0);
                        fromAhead = dotProduct >= 0;
                        if (!fromAhead) {
                            dX = indirSource.getEntity().posX - vic.posX;
                            dY = indirSource.getEntity().posY - vic.posY;
                            dZ = indirSource.getEntity().posZ - vic.posZ;
                            dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                                + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                            fromAhead = dotProduct >= 0;
                        }
                    }
                    if (!fromAhead) {
                        dX = hitWith.prevPosX - vic.posX;
                        dY = hitWith.prevPosY - vic.posY;
                        dZ = hitWith.prevPosZ - vic.posZ;
                        dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                            + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                        fromAhead = dotProduct >= 0;
                    }
                }
                if (fromAhead) { // within 90 degrees of facing, block
                    stack.damageItem(event.ammount >= shield.getMinDamageToDamage() ? MathHelper.ceiling_float_int(event.ammount) : 0, vic);

                    p.worldObj.playSoundAtEntity(p, "undertow:shield.block", 1.0F, p.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    if (hitWith instanceof EntityArrow ea && !(hitWith instanceof ProjectileTrident dent && dent.isLoyal()))
                        ea.shootingEntity = vic;
                    //p.worldObj.playSoundAtEntity(p,  "shield.break", 1.0F, p.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    if (Config.shield_knockback_enabled && hitWith instanceof EntityLivingBase hitter) {
                        hitter.knockBack(vic, 1.0F, -dX, -dZ);
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void handleVariantZombies(ZombieEvent.SummonAidEvent event) {
        if (event.entity instanceof EntityZombie z && z.getClass() != EntityZombie.class) {
            try {
                event.customSummonedAid = (z.getClass()).getDeclaredConstructor(World.class).newInstance(z.worldObj);
                //event.setResult(Event.Result.ALLOW); //Oops, that meant runaway summons.
            } catch (NoSuchMethodException nsmX) {
                Undertow.LOG.error("Somehow, subclass of EntityZombie " + z.getClass().getName() + " didn't have a (world) constructor.", nsmX);
                event.customSummonedAid = null;
                event.setResult(Event.Result.DENY);
            } catch (InvocationTargetException e) {
                Undertow.LOG.error("Somehow, subclass of EntityZombie " + z.getClass().getName() + "'s constructor threw an exception.", e);
                event.customSummonedAid = null;
                event.setResult(Event.Result.DENY);
            } catch (InstantiationException e) {
                Undertow.LOG.error("Somehow, subclass of EntityZombie " + z.getClass().getName() + "didn't finish a (world) constructor.", e);
                event.customSummonedAid = null;
                event.setResult(Event.Result.DENY);
            } catch (IllegalAccessException e) {
                Undertow.LOG.error("Somehow, subclass of EntityZombie " + z.getClass().getName() + " didn't allow us to access it from ZombieEvent.", e);
                event.customSummonedAid = null;
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void assignLightningBlame(EntityStruckByLightningEvent event) {
        if (event.lightning instanceof EntityCalledLightning bolt && bolt.caller != null) {
            if (Config.channeling_owner_safe && bolt.caller == event.entity) {
                event.setCanceled(true);
            } else {
                Entity responsible = Config.channeling_aggro ? bolt.caller : bolt;
                event.entity.attackEntityFrom(new EntityDamageSourceIndirect("channeled_lightning", responsible, bolt).setFireDamage(), 5);
            }
            //TODO: make damage 5+ attackdamage so it stacks up properly
            //don't cancel because there are other effects of being hit by lightning
            //Natural hurt resist time means it won't take a second damage packet from the bolt.
        }
    }

    @SubscribeEvent
    public void noTridentCreativeMine(BlockEvent.BreakEvent event) {
        EntityPlayer p = event.getPlayer();
        if (Config.trident_disable_creative_block_break && p.capabilities.isCreativeMode && p.getHeldItem() != null
            && p.getHeldItem().getItem() instanceof ItemTrident) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void renderAsRegularMap(RenderItemInFrameEvent event) {
        if (event.item.getItem() instanceof IBackportedMap iBPM && iBPM.renderLikeVanillaMapInFrame()) {
            EntityItem entitiedItem = new EntityItem(event.entityItemFrame.worldObj, 0, 0, 0, event.item);
            RenderFramedMap.instance.theFrame = event.entityItemFrame;
            RenderFramedMap.instance.doRender(entitiedItem,
                0, 0, 0, 0, 0);
            if (iBPM.renderMoreStuff()) {
                RenderFramedMap.instance.doFurtherRender(entitiedItem);
            }
        }
    }

    private double lastPosX = 0, lastPosZ = 0;

    @SubscribeEvent
    public void decorateMap(PlayerInteractEvent event) {
        if (!Config.enable_deco_maps) return;
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || !event.entityPlayer.isClientWorld())
            return; //reminder that isClientWorld is named wrong: inverted from reality.
        EntityPlayer user = event.entityPlayer;
        if (user.posX != lastPosX && user.posZ != lastPosZ) { //cut the doubled event for singleplayer
            ItemStack heldStack = user.getCurrentEquippedItem();
            Item heldItem = (heldStack != null) ? heldStack.getItem() : null;
            if (heldItem == Items.filled_map || heldItem == UndertowItems.DECO_MAP) {
                MapData mapData = ((ItemMap) heldItem).getMapData(heldStack, user.worldObj);
                if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {

                } else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR && Config.map_mark_free) {
                    int scaleMult = 1 << mapData.scale;
                    float flex = (float) user.posX - mapData.xCenter + 0.5F, flez = (float) user.posZ - mapData.zCenter + 0.5F;
                    flex *= 2 / (float) scaleMult;
                    flez *= 2 / (float) scaleMult;

                    if ((int) max(flex, flez) <= Byte.MAX_VALUE && (int) min(flex, flez) >= Byte.MIN_VALUE) {
                        byte mapX = (byte) flex, mapZ = (byte) flez;

                        mapData.playersVisibleOnMap.put("__POI__", mapData.new MapCoord((byte) 5, (byte) mapX, (byte) mapZ, (byte) 8));
                    }
                }
            }
            lastPosX = user.posX;
            lastPosZ = user.posZ;
        }
    }

    @SubscribeEvent
    public void rotatePosables(RenderPlayerEvent.Specials.Pre event) {
        GL11.glPushMatrix();
        PoseHelper.poseRotate((EntityLivingBase) event.entity, true);
    }

    @SubscribeEvent
    public void popPoses(RenderPlayerEvent.Specials.Post event) {
        GL11.glPopMatrix();
    }
}
