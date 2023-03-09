/*
package com.ipdnaeip.wizardrynextgeneration.client.gui;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.block.BlockBookshelf;
import electroblob.wizardry.client.DrawingUtils;
import electroblob.wizardry.client.gui.GuiButtonInvisible;
import electroblob.wizardry.client.gui.GuiButtonSpellSort;
import electroblob.wizardry.client.gui.GuiButtonTurnPage;
import electroblob.wizardry.client.gui.GuiSpellInfo;
import electroblob.wizardry.data.SpellGlyphData;
import electroblob.wizardry.item.ItemSpellBook;
import electroblob.wizardry.packet.PacketLectern;
import electroblob.wizardry.packet.WizardryPacketHandler;
import electroblob.wizardry.registry.Spells;
import electroblob.wizardry.registry.WizardrySounds;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.tileentity.TileEntityLectern;
import electroblob.wizardry.util.GeometryUtils;
import electroblob.wizardry.util.ISpellSortable;
import electroblob.wizardry.util.ParticleBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GuiSpellEncyclopedia extends GuiSpellInfo implements ISpellSortable {
    private static final ResourceLocation TEXTURE = new ResourceLocation("wizardrynextgeneration", "textures/gui/container/spell_encyclopedia.png");
    private static final int PAGE_BUTTON_INSET_X = 22;
    private static final int PAGE_BUTTON_INSET_Y = 13;
    private static final int PAGE_BUTTON_SPACING = 20;
    private static final int SORT_BUTTON_INSET_X = 96;
    private static final int SORT_BUTTON_INSET_Y = 20;
    private static final int SORT_BUTTON_SPACING = 13;
    private static final int SPELL_BUTTON_INSET_X = 23;
    private static final int SPELL_BUTTON_INSET_Y = 44;
    private static final int SPELL_BUTTON_SPACING = 38;
    private static final int SPELL_ROWS = 3;
    private static final int SPELL_COLUMNS = 3;
    public static final int SPELL_BUTTON_COUNT = 18;
    private static final int SEARCH_TOOLTIP_HOVER_TIME = 20;
    private static final Style TOOLTIP_SYNTAX;
    private static final Style TOOLTIP_BODY;
    private GuiButton nextPageButton;
    private GuiButton prevPageButton;
    private GuiButton lastPageButton;
    private GuiButton firstPageButton;
    private GuiButton indexButton;
    private GuiButton locateButton;
    private GuiButton[] sortButtons = new GuiButton[3];
    private GuiButtonSpell[] spellButtons = new GuiButtonSpell[18];
    private Spell currentSpell;
    private List<Spell> availableSpells = new ArrayList();
    private List<Spell> matchingSpells;
    private ISpellSortable.SortType sortType;
    private boolean sortDescending;
    private GuiTextField searchField;
    private boolean searchNeedsClearing;
    private int searchBarHoverTime;
    private int currentPage;

    public GuiSpellEncyclopedia(TileEntityLectern lectern) {
        super(288, 180);
        this.sortType = SortType.TIER;
        this.sortDescending = false;
        this.currentPage = 0;
        this.lectern = lectern;
        this.currentSpell = lectern.currentSpell;
        this.setTextureSize(512, 512);
    }

    public Spell getSpell() {
        return this.currentSpell;
    }

    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public ISpellSortable.SortType getSortType() {
        return this.sortType;
    }

    public boolean isSortDescending() {
        return this.sortDescending;
    }

    private int getPageCount() {
        return MathHelper.ceil((float)this.matchingSpells.size() / 18.0F);
    }

    private Spell getSpellForButton(GuiButtonSpell button) {
        return (Spell)this.matchingSpells.get(this.currentPage * 18 + button.index);
    }

    public void initGui() {
        super.initGui();
        int left = this.width / 2 - this.xSize / 2;
        int top = this.height / 2 - this.ySize / 2;
        int buttonID = 0;
        this.buttonList.add(this.nextPageButton = new GuiButtonTurnPage(buttonID++, left + this.xSize - 22 - 20, top + this.ySize - 13 - 12, GuiButtonTurnPage.Type.NEXT_PAGE, TEXTURE, this.textureWidth, this.textureHeight));
        this.buttonList.add(this.prevPageButton = new GuiButtonTurnPage(buttonID++, left + 22, top + this.ySize - 13 - 12, GuiButtonTurnPage.Type.PREVIOUS_PAGE, TEXTURE, this.textureWidth, this.textureHeight));
        this.buttonList.add(this.lastPageButton = new GuiButtonTurnPage(buttonID++, left + this.xSize - 22 - 20 - 20, top + this.ySize - 13 - 12, GuiButtonTurnPage.Type.NEXT_SECTION, TEXTURE, this.textureWidth, this.textureHeight));
        this.buttonList.add(this.firstPageButton = new GuiButtonTurnPage(buttonID++, left + 22 + 20, top + this.ySize - 13 - 12, GuiButtonTurnPage.Type.PREVIOUS_SECTION, TEXTURE, this.textureWidth, this.textureHeight));
        this.buttonList.add(this.indexButton = new GuiButtonTurnPage(buttonID++, left + this.xSize / 2 - 23, top + this.ySize - 13 - 12, GuiButtonTurnPage.Type.CONTENTS, TEXTURE, this.textureWidth, this.textureHeight));
        this.buttonList.add(this.locateButton = new GuiButtonLocateBook(buttonID++, left + this.xSize / 2 - 34, top + this.ySize - 13 - 12));
        ISpellSortable.SortType[] var4 = SortType.values();
        int row = var4.length;

        int column;
        for(column = 0; column < row; ++column) {
            ISpellSortable.SortType sortType = var4[column];
            this.buttonList.add(this.sortButtons[sortType.ordinal()] = new GuiButtonSpellSort(buttonID++, left + 96 + 13 * sortType.ordinal(), top + 20, sortType, this, this));
        }

        for(int i = 0; i < 18; ++i) {
            row = i % 3;
            column = i / 3 % 3;
            int x = i < 9 ? 23 + row * 38 : this.xSize - 23 - 34 - (2 - row) * 38;
            int y = 44 + column * 38;
            this.buttonList.add(this.spellButtons[i] = new GuiButtonSpell(buttonID++, left + x, top + y, i));
        }

        this.searchField = new GuiTextField(0, this.fontRenderer, left + 157, top + 21, 106, this.fontRenderer.FONT_HEIGHT);
        this.searchField.setMaxStringLength(50);
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setVisible(true);
        this.searchField.setTextColor(16777215);
        this.searchField.setCanLoseFocus(false);
        this.searchField.setFocused(true);
        this.refreshAvailableSpells();
    }

    public void updateScreen() {
        super.updateScreen();
        if (this.searchBarHoverTime > 0 && this.searchBarHoverTime < 20) {
            ++this.searchBarHoverTime;
        }

    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void drawBackgroundLayer(int left, int top, int mouseX, int mouseY) {
        if (this.currentSpell == Spells.none) {
            this.drawIndexPage(left, top);
        } else {
            super.drawBackgroundLayer(left, top, mouseX, mouseY);
        }

    }

    protected void drawForegroundLayer(int left, int top, int mouseX, int mouseY) {
        if (this.currentSpell == Spells.none) {
            this.fontRenderer.drawString(I18n.format("gui.ebwizardry:lectern.title", new Object[0]), left + 20, top + 20, 0);
        } else {
            super.drawForegroundLayer(left, top, mouseX, mouseY);
        }

        this.buttonList.forEach((b) -> {
            b.drawButtonForegroundLayer(mouseX, mouseY);
        });
        if (DrawingUtils.isPointInRegion(this.searchField.x, this.searchField.y, this.searchField.width, this.searchField.height, mouseX, mouseY)) {
            if (this.searchBarHoverTime == 0) {
                ++this.searchBarHoverTime;
            } else if (this.searchBarHoverTime == 20) {
                this.drawHoveringText(I18n.format("container.ebwizardry:arcane_workbench.search_tooltip", new Object[]{TOOLTIP_SYNTAX.getFormattingCode(), TOOLTIP_BODY.getFormattingCode()}), mouseX, mouseY);
            }
        } else {
            this.searchBarHoverTime = 0;
        }

    }

    private void drawIndexPage(int left, int top) {
        for(int i = 0; i < 18; ++i) {
            int index = this.currentPage * 18 + i;
            Spell spell = index < this.matchingSpells.size() ? (Spell)this.matchingSpells.get(index) : Spells.none;
            boolean discovered = Wizardry.proxy.shouldDisplayDiscovered(spell, (ItemStack)null);
            Minecraft.getMinecraft().renderEngine.bindTexture(discovered ? spell.getIcon() : Spells.none.getIcon());
            int row = i % 3;
            int column = i / 3 % 3;
            int x = i < 9 ? 23 + row * 38 : this.xSize - 23 - 34 - (2 - row) * 38;
            int y = 44 + column * 38;
            DrawingUtils.drawTexturedRect(left + x + 1, top + y + 1, 0, 0, 32, 32, 32, 32);
        }

        this.mc.renderEngine.bindTexture(this.getTexture());
        DrawingUtils.drawTexturedRect(left, top, 0, 256, this.xSize, this.ySize, this.textureWidth, this.textureHeight);
        this.searchField.drawTextBox();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(this.getTexture());
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.searchNeedsClearing = true;
    }

    protected void actionPerformed(GuiButton button) {
        int lastPage = this.getPageCount() - 1;
        if (button == this.indexButton) {
            this.currentSpell = Spells.none;
        } else if (button == this.locateButton) {
            this.mc.player.connection.sendPacket(new CPacketCloseWindow(this.mc.player.openContainer.windowId));
            this.mc.displayGuiScreen((GuiScreen)null);
            Iterator var3 = BlockBookshelf.findNearbyBookshelves(this.lectern.getWorld(), this.lectern.getPos(), new TileEntity[0]).iterator();

            label72:
            while(true) {
                while(true) {
                    if (!var3.hasNext()) {
                        break label72;
                    }

                    IInventory bookshelf = (IInventory)var3.next();

                    for(int i = 0; i < bookshelf.getSizeInventory(); ++i) {
                        ItemStack stack = bookshelf.getStackInSlot(i);
                        if (stack.getItem() instanceof ItemSpellBook) {
                            Spell spell = Spell.byMetadata(stack.getMetadata());
                            if (spell == this.currentSpell) {
                                BlockPos pos = ((TileEntity)bookshelf).getPos();
                                EnumFacing[] var9 = EnumFacing.VALUES;
                                int var10 = var9.length;

                                for(int var11 = 0; var11 < var10; ++var11) {
                                    EnumFacing side = var9[var11];
                                    ParticleBuilder.create(electroblob.wizardry.util.ParticleBuilder.Type.BLOCK_HIGHLIGHT).pos(GeometryUtils.getFaceCentre(pos, side).add((new Vec3d(side.getDirectionVec())).scale(0.005))).face(side).clr(0.9F, 0.5F, 0.8F).fade(0.7F, 0.0F, 1.0F).spawn(this.mc.world);
                                }

                                this.mc.world.playSound(pos, WizardrySounds.BLOCK_LECTERN_LOCATE_SPELL, SoundCategory.BLOCKS, 1.0F, 0.7F, false);
                                break;
                            }
                        }
                    }
                }
            }
        } else if (button == this.nextPageButton) {
            if (this.currentPage < lastPage) {
                ++this.currentPage;
            }
        } else if (button == this.prevPageButton) {
            if (this.currentPage > 0) {
                --this.currentPage;
            }
        } else if (button == this.lastPageButton) {
            this.currentPage = lastPage;
        } else if (button == this.firstPageButton) {
            this.currentPage = 0;
        } else if (button instanceof GuiButtonSpell) {
            this.currentSpell = this.getSpellForButton((GuiButtonSpell)button);
        } else if (button instanceof GuiButtonSpellSort) {
            ISpellSortable.SortType sortType = ((GuiButtonSpellSort)button).sortType;
            if (this.sortType == sortType) {
                this.sortDescending = !this.sortDescending;
            } else {
                this.sortType = sortType;
                this.sortDescending = false;
            }

            this.updateMatchingSpells();
        }

        this.updateButtonVisiblity();
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.searchNeedsClearing) {
            this.searchNeedsClearing = false;
            this.searchField.setText("");
            this.currentPage = 0;
        }

        if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
            this.currentPage = 0;
            this.updateMatchingSpells();
            this.updateButtonVisiblity();
        } else {
            super.keyTyped(typedChar, keyCode);
        }

    }

    private void updateButtonVisiblity() {
        if (this.currentSpell == Spells.none) {
            this.searchField.setVisible(true);
            int lastPage = this.getPageCount() - 1;
            this.prevPageButton.visible = this.currentPage > 0;
            this.firstPageButton.visible = this.currentPage > 0;
            this.nextPageButton.visible = this.currentPage < lastPage;
            this.lastPageButton.visible = this.currentPage < lastPage;
            this.indexButton.visible = false;
            this.locateButton.visible = false;
            GuiButton[] var2 = this.sortButtons;
            int var3 = var2.length;

            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
                GuiButton button = var2[var4];
                button.visible = true;
            }

            GuiButtonSpell[] var6 = this.spellButtons;
            var3 = var6.length;

            for(var4 = 0; var4 < var3; ++var4) {
                GuiButtonSpell button = var6[var4];
                button.visible = this.currentPage * 18 + button.index < this.matchingSpells.size();
            }
        } else {
            this.searchField.setVisible(false);
            this.buttonList.forEach((b) -> {
                b.visible = false;
            });
            this.indexButton.visible = true;
            this.locateButton.visible = true;
        }

    }

    private void updateMatchingSpells() {
        this.matchingSpells = (List)this.availableSpells.stream().filter((s) -> {
            return s.matches(this.searchField.getText().toLowerCase(Locale.ROOT));
        }).sorted(this.sortDescending ? this.sortType.comparator.reversed() : this.sortType.comparator).collect(Collectors.toList());
    }

    public void refreshAvailableSpells() {
        this.availableSpells.clear();
        Iterator var1 = BlockBookshelf.findNearbyBookshelves(this.lectern.getWorld(), this.lectern.getPos(), new TileEntity[0]).iterator();

        while(var1.hasNext()) {
            IInventory bookshelf = (IInventory)var1.next();

            for(int i = 0; i < bookshelf.getSizeInventory(); ++i) {
                ItemStack stack = bookshelf.getStackInSlot(i);
                if (stack.getItem() instanceof ItemSpellBook) {
                    Spell spell = Spell.byMetadata(stack.getMetadata());
                    if (spell != Spells.none && !this.availableSpells.contains(spell)) {
                        this.availableSpells.add(spell);
                    }
                }
            }
        }

        if (!this.availableSpells.contains(this.currentSpell)) {
            this.currentSpell = Spells.none;
        }

        this.updateMatchingSpells();
        this.updateButtonVisiblity();
    }

    static {
        TOOLTIP_SYNTAX = (new Style()).setColor(TextFormatting.YELLOW);
        TOOLTIP_BODY = (new Style()).setColor(TextFormatting.WHITE);
    }

    private class GuiButtonSpell extends GuiButtonInvisible {
        private static final int WIDTH = 34;
        private static final int HEIGHT = 34;
        private final int index;

        public GuiButtonSpell(int id, int x, int y, int index) {
            super(id, x, y, 34, 34);
            this.index = index;
        }

        public void playPressSound(SoundHandler soundHandler) {
            soundHandler.playSound(PositionedSoundRecord.getMasterRecord(WizardrySounds.MISC_PAGE_TURN, 1.0F));
        }

        public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                super.drawButton(minecraft, mouseX, mouseY, partialTicks);
                if (this.hovered) {
                    this.mc.renderEngine.bindTexture(this.getTexture());
                    DrawingUtils.drawTexturedRect(this.x, this.y, 40, 180, this.width, this.height, this.textureWidth, this.textureHeight);
                }
            }

        }

        public void drawButtonForegroundLayer(int mouseX, int mouseY) {
            if (this.visible && this.hovered) {
                Spell spell = this.getSpellForButton(this);
                if (Wizardry.proxy.shouldDisplayDiscovered(spell, (ItemStack)null)) {
                    this.drawHoveringText(Collections.singletonList(spell.getDisplayName()), mouseX, mouseY, this.fontRenderer);
                } else {
                    this.drawHoveringText(Collections.singletonList(SpellGlyphData.getGlyphName(spell, this.mc.world)), mouseX, mouseY, this.mc.standardGalacticFontRenderer);
                }
            }

        }
    }

    private class GuiButtonLocateBook extends GuiButton {
        public GuiButtonLocateBook(int id, int x, int y) {
            super(id, x, y, 12, 12, "");
        }

        public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                minecraft.getTextureManager().bindTexture(TEXTURE);
                DrawingUtils.drawTexturedRect(this.x, this.y, flag ? this.width : 0, 184, this.width, this.height, this.textureWidth, this.textureHeight);
            }

        }
    }
}

*/
