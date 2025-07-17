package invalid.myask.undertow.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class RiptideHitMessage implements IMessage {
    double posX, posY, posZ;
    double movX, movY, movZ;
    public RiptideHitMessage() {}

    public RiptideHitMessage (double posX, double posY, double posZ, double movX, double movY, double movZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.movX = movX;
        this.movY = movY;
        this.movZ = movZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
        movX = buf.readDouble();
        movY = buf.readDouble();
        movZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(movX);
        buf.writeDouble(movY);
        buf.writeDouble(movZ);
    }
}
