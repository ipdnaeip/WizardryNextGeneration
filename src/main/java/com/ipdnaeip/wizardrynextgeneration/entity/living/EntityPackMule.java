package com.ipdnaeip.wizardrynextgeneration.entity.living;

import com.ipdnaeip.wizardrynextgeneration.util.WNGUtils;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.util.AllyDesignationSystem;
import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityPackMule extends EntityMule {

    public static final IAttribute JUMP_STRENGTH = AbstractHorse.JUMP_STRENGTH;

    public EntityPackMule(World worldIn) {
        super(worldIn);
    }

    @Override
    protected int getInventorySize() {
        return 17;
    }

    @Override
    public boolean isHorseSaddled() {
        return true;
    }

    @Override
    public boolean isTame() {
        return true;
    }

    @Override
    public boolean hasChest() {
        return this.isEntityAlive();
    }

    @Override
    public boolean isChild() {
        return false;
    }

    private boolean canInteract(EntityPlayer player) {
        if (this.getOwner() == null || this.getOwner().equals(player) || AllyDesignationSystem.isAllied(this.getOwner(), player)) {
            return true;
        } else {
            WNGUtils.sendMessage(player, "spell.wizardrynextgeneration:call_pack_mule.no_access", true);
            return false;
        }
    }

    @Override
    public void openGUI(EntityPlayer player) {
        if (this.canInteract(player)) {
            super.openGUI(player);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (this.canInteract(player)) {
            return super.processInteract(player, hand);
        } else {
            return false;
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        this.setChested(false);
        super.onDeath(cause);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player){
        return 0;
    }

    @Override
    protected boolean canDropLoot(){
        return false;
    }

    @Override
    protected Item getDropItem(){
        return null;
    }

    @Override
    protected ResourceLocation getLootTable(){
        return null;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2){
    }

    private EntityLivingBase getOwner(){

        // I think the DataManager stores any objects, so it now stores the UUID instead of its string representation.
        Entity owner = EntityUtils.getEntityByUUID(world, this.getOwnerUniqueId());

        if(owner instanceof EntityLivingBase){
            return (EntityLivingBase)owner;
        }else{
            return null;
        }
    }

    @Override
    public ITextComponent getDisplayName(){
        if(getOwner() != null){
            return new TextComponentTranslation(ISummonedCreature.NAMEPLATE_TRANSLATION_KEY, getOwner().getName(),
                    new TextComponentTranslation("entity." + this.getEntityString() + ".name"));
        }else{
            return super.getDisplayName();
        }
    }

    @Override
    public boolean hasCustomName(){
        // If this returns true, the renderer will show the nameplate when looking directly at the entity
        return Wizardry.settings.summonedCreatureNames && getOwner() != null;
    }
}
