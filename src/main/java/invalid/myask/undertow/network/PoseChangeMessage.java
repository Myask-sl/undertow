package invalid.myask.undertow.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PoseChangeMessage implements IMessage {
    public boolean crawl, swim, ripping;
    public int ripticks, max_ripticks;
    public PoseChangeMessage () {
        this.crawl = false; this.swim = false;
        this.ripping = false; this.ripticks = 2; this.max_ripticks = 25;
    }
    public PoseChangeMessage (boolean crawl, boolean swim, boolean ripping, int ripticks, int max_ripticks) {
        this.crawl = crawl; this.swim = swim;
        this.ripping = ripping; this.ripticks = ripticks; this.max_ripticks = max_ripticks;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        crawl = buf.readBoolean();
        swim = buf.readBoolean();
        ripping = buf.readBoolean();
        ripticks = buf.readInt();
        max_ripticks = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(crawl);
        buf.writeBoolean(swim);
        buf.writeBoolean(ripping);
        buf.writeInt(ripticks);
        buf.writeInt(max_ripticks);
    }
}
