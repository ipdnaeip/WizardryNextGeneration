package com.ipdnaeip.wizardrynextgeneration.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SPacketMultijump implements IMessageHandler<C2SPacketMultijump.Message, IMessage> {

	@Override
	public IMessage onMessage(Message message, MessageContext ctx){
		if(ctx.side.isServer()){
			final EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> player.fallDistance = 0);
		}
		return null;
	}

	public static class Message implements IMessage {

		private float fallDistance;

		public Message(){
		}

		public Message(float fallDistance){
			this.fallDistance = fallDistance;
		}

		@Override
		public void fromBytes(ByteBuf buf){
			this.fallDistance = buf.readFloat();
		}

		@Override
		public void toBytes(ByteBuf buf){
			buf.writeFloat(this.fallDistance);
		}
	}
}
