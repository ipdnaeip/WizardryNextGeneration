package com.ipdnaeip.wizardrynextgeneration.packet.s2c;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPhotosynthesis implements IMessageHandler<PacketPhotosynthesis.Message, IMessage> {

    @Override
    public IMessage onMessage(Message message, MessageContext ctx){
        if(ctx.side.isClient()){
            net.minecraft.client.Minecraft.getMinecraft().addScheduledTask(() -> WizardryNextGeneration.proxy.handlePhotosynthesisPacket(message));
        }

        return null;
    }

    public static class Message implements IMessage {

        public BlockPos blockPos;

        // This constructor is required otherwise you'll get errors (used somewhere in fml through reflection)
        public Message(){
        }

        public Message(BlockPos destination){
            this.blockPos = destination;
        }

        @Override
        public void fromBytes(ByteBuf buf){
            // The order is important
            this.blockPos = BlockPos.fromLong(buf.readLong());
        }

        @Override
        public void toBytes(ByteBuf buf){
            buf.writeLong(blockPos.toLong());
        }
    }
}
