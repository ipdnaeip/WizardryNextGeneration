package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.WizardryNextGeneration;
import com.ipdnaeip.wizardrynextgeneration.network.c2s.C2SPacketMultijump;
import com.ipdnaeip.wizardrynextgeneration.network.s2c.S2CPacketPhotosynthesis;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class WNGPackets {

	public static SimpleNetworkWrapper net;
	private static int nextPacketId = 0;

	public static void initPackets() {
		net = NetworkRegistry.INSTANCE.newSimpleChannel(WizardryNextGeneration.MODID.toUpperCase());
		registerMessage(C2SPacketMultijump.class, C2SPacketMultijump.Message.class);
		registerMessage(S2CPacketPhotosynthesis.class, S2CPacketPhotosynthesis.Message.class);
	}

	private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> packet, Class<REQ> message) {
		net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
		net.registerMessage(packet, message, nextPacketId, Side.SERVER);
		nextPacketId++;
	}

}
