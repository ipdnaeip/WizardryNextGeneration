package com.ipdnaeip.wizardrynextgeneration.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ITickableArtefact {

    void onWornTick(ItemStack itemstack, EntityLivingBase player);

}