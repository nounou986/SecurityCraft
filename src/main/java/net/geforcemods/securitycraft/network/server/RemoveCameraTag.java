package net.geforcemods.securitycraft.network.server;

import java.util.function.Supplier;

import net.geforcemods.securitycraft.items.CameraMonitorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class RemoveCameraTag
{
	private ItemStack heldItem;
	private int camID;

	public RemoveCameraTag(){}

	public RemoveCameraTag(ItemStack stack, int cid)
	{
		heldItem = stack;
		camID = cid;
	}

	public static void encode(RemoveCameraTag message, PacketBuffer buf)
	{
		buf.writeItemStack(message.heldItem);
		buf.writeInt(message.camID);
	}

	public static RemoveCameraTag decode(PacketBuffer buf)
	{
		RemoveCameraTag message = new RemoveCameraTag();

		message.heldItem = buf.readItemStack();
		message.camID = buf.readInt();
		return message;
	}

	public static void onMessage(RemoveCameraTag message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			ItemStack monitor = ctx.get().getSender().inventory.getCurrentItem();
			int id = message.camID;

			monitor.getTag().remove(CameraMonitorItem.getTagNameFromPosition(monitor.getTag(), ((CameraMonitorItem)monitor.getItem()).getCameraPositions(monitor.getTag()).get(id - 1)));
		});

		ctx.get().setPacketHandled(true);
	}
}
