package invalid.myask.undertow.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RiptideHitHandler implements IMessageHandler<RiptideHitMessage, IMessage> {
    @Override
    public IMessage onMessage(RiptideHitMessage message, MessageContext ctx) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        player.setVelocity(message.movX, message.movY, message.movZ);
        player.setPosition(message.posX, message.posY, message.posZ);
        ((IUndertowPosableEntity)player).undertow$setTideRipping(false);
        return null;
    }
}
