package com.ipdnaeip.wizardrynextgeneration.registry;

import com.ipdnaeip.wizardrynextgeneration.advancement.CustomAdvancementTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public final class WNGAdvancementTriggers {

    public static final CustomAdvancementTrigger max_out_tablet = new CustomAdvancementTrigger("new_max_out_tablet");
    public static final CustomAdvancementTrigger accelerated_mass_max_damage = new CustomAdvancementTrigger("accelerated_mass_max_damage");

    private WNGAdvancementTriggers() {

    }

    public static void register() {

        CriteriaTriggers.register(max_out_tablet);
        CriteriaTriggers.register(accelerated_mass_max_damage);
    }


}
