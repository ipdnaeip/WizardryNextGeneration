package com.ipdnaeip.wizardrynextgeneration.network.c2s;

import electroblob.wizardry.registry.WizardrySounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class C2SPacketSolarWinds implements IMessageHandler<C2SPacketSolarWinds.Message, IMessage> {

	@Override
	public IMessage onMessage(Message message, MessageContext ctx){
		if(ctx.side.isServer()){
			final EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> {
				player.fallDistance = message.fallDistance;
				//change later
				player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_ELYTRA_FLYING, WizardrySounds.SPELLS, 0.5f, 1.5f);
			});
		}
		return null;
	}

	public static class Message implements IMessage {

		float fallDistance;

		public Message() {
		}

		public Message(float fallDistance) {
			this.fallDistance = fallDistance;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			this.fallDistance = buf.readFloat();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeFloat(this.fallDistance);
		}
	}
}
