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
        WandHelper.registerSpecialUpgrade(WNGItems.UPGRADE_CHARGEUP, "chargeup_upgrade");
        WandHelper.registerSpecialUpgrade(WNGItems.UPGRADE_LOOTING, "looting_upgrade");
        WandHelper.registerSpecialUpgrade(WNGItems.UPGRADE_MOVEMENT, "movement_upgrade");
    }


}
