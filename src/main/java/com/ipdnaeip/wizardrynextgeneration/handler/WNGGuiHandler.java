package com.ipdnaeip.wizardrynextgeneration.handler;

import com.ipdnaeip.wizardrynextgeneration.client.gui.GuiSpellEncyclopedia;
import com.ipdnaeip.wizardrynextgeneration.item.ItemSpellEncyclopedia;
import electroblob.wizardry.client.gui.*;
import electroblob.wizardry.client.gui.handbook.GuiWizardHandbook;
import electroblob.wizardry.inventory.ContainerArcaneWorkbench;
import electroblob.wizardry.inventory.ContainerBookshelf;
import electroblob.wizardry.inventory.ContainerPortableWorkbench;
import electroblob.wizardry.item.ItemSpellBook;
import electroblob.wizardry.item.ItemWizardHandbook;
import electroblob.wizardry.tileentity.TileEntityArcaneWorkbench;
import electroblob.wizardry.tileentity.TileEntityBookshelf;
import electroblob.wizardry.tileentity.TileEntityLectern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class WNGGuiHandler implements IGuiHandler {

    private static int nextGuiId = 0;
    public static final int SPELL_ENCYCLOPEDIA;

    public WNGGuiHandler() {
    }

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == SPELL_ENCYCLOPEDIA && (player.getHeldItemMainhand().getItem() instanceof ItemSpellEncyclopedia || player.getHeldItemOffhand().getItem() instanceof ItemWizardHandbook)) {
            return new GuiSpellEncyclopedia();
        }
        return null;
    }

    static {
        SPELL_ENCYCLOPEDIA = nextGuiId++;
    }
}
