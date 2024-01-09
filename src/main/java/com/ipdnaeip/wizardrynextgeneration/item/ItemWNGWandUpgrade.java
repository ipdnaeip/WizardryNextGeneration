package com.ipdnaeip.wizardrynextgeneration.item;

import com.ipdnaeip.wizardrynextgeneration.registry.WNGItems;
import com.ipdnaeip.wizardrynextgeneration.registry.WNGTabs;
import electroblob.wizardry.item.ItemWandUpgrade;
import electroblob.wizardry.util.WandHelper;

public class ItemWNGWandUpgrade extends ItemWandUpgrade {

    public ItemWNGWandUpgrade() {
        super();
        this.setCreativeTab(WNGTabs.WIZARDRYNEXTGENERATION);
    }

    public static void init() {
        WandHelper.registerSpecialUpgrade(WNGItems.upgrade_chargeup, "chargeup_upgrade");
        WandHelper.registerSpecialUpgrade(WNGItems.upgrade_looting, "looting_upgrade");
        WandHelper.registerSpecialUpgrade(WNGItems.upgrade_movement, "movement_upgrade");
    }


}
