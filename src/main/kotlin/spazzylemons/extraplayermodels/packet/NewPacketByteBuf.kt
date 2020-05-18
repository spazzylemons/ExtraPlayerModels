package spazzylemons.extraplayermodels.packet

import io.netty.buffer.Unpooled
import net.minecraft.util.PacketByteBuf

fun newPacketByteBuf(): PacketByteBuf = PacketByteBuf(Unpooled.buffer())