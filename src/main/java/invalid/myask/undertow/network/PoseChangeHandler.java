package invalid.myask.undertow.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;

public class PoseChangeHandler implements IMessageHandler<PoseChangeMessage, IMessage> {
    @Override
    public IMessage onMessage(PoseChangeMessage message, MessageContext ctx) {
        EntityPlayer player = ((NetHandlerPlayServer) ctx.netHandler).playerEntity;
        IUndertowPosableEntity mcClane = (IUndertowPosableEntity) player;
        mcClane.undertow$eatPacket(message);
        return null;
    }
}
