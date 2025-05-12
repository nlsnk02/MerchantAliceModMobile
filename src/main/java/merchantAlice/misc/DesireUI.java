package merchantAlice.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import merchantAlice.actions.ChangeHeatStateAction;
import merchantAlice.characters.Char;
import merchantAlice.helper.GIFAnimationHelper;
import merchantAlice.helper.ImageHelper;
import merchantAlice.helper.ModHelper;

import java.util.ArrayList;

public class DesireUI {
    public static final int HeatStanceCriticalValue = 10;
    private static final float X = -100F * Settings.scale;
    private static final float Y = 250F * Settings.scale;
    private static final float SCALE = 0.5F;
    private static final Texture UI = ImageHelper.DesireUI;
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("MerchantAlice:DesireTip");
    public static DesireUI inst = new DesireUI();

    public static final Color Transparency = new Color(1.0F, 0.5F, 0.7F, 0.8F);
    private ArrayList<PowerTip> tips = new ArrayList();
    private boolean shouldRenderTip = false;
    public boolean isBlocked = false;
    public int desireAmount;
    private ShapeRenderer shapeRenderer;
    public float current_x;
    public float current_y;
    private Hitbox hb;

    public void init() {
        this.desireAmount = 0;
        this.isHeat = false;
        this.isBlocked = false;
        ModHelper.initSPState();
        GIFAnimationHelper.clearGif();
    }

    DesireUI() {
        this.current_x = X + AbstractDungeon.overlayMenu.energyPanel.current_x;
        this.current_y = Y + AbstractDungeon.overlayMenu.energyPanel.current_y;

        hb = new Hitbox(current_x, current_y, UI.getWidth() * SCALE, UI.getHeight() * SCALE);

        this.tips.add(new PowerTip(tutorialStrings.TEXT[0],
                tutorialStrings.LABEL[0]));

        shapeRenderer = new ShapeRenderer();

        init();
    }

    public boolean isHeat = false;

    public static float atDamageGive(Object o, float damage, DamageInfo.DamageType damageType) {
        if (o instanceof AbstractStance && AbstractDungeon.player instanceof Char && damageType == DamageInfo.DamageType.NORMAL) {
            if (DesireUI.inst.isHeat)
                return damage + (DesireUI.inst.desireAmount / 2 * 2);
            else
                return damage + (DesireUI.inst.desireAmount / 2);
        }
        return damage;
    }

    public void update() {
        this.current_x = X + AbstractDungeon.overlayMenu.energyPanel.current_x;
        this.current_y = Y + AbstractDungeon.overlayMenu.energyPanel.current_y;
        hb.move(current_x, current_y);
        hb.update();

        shouldRenderTip = hb.hovered && !AbstractDungeon.isScreenUp;

        DesireBarChart.update();

        if (ModHelper.isInGame() && !isBlocked) {
            if (this.desireAmount >= HeatStanceCriticalValue && !this.isHeat) {
                isBlocked = true;
                AbstractDungeon.actionManager.addToBottom(new ChangeHeatStateAction(true));
            } else if (this.desireAmount < HeatStanceCriticalValue && this.isHeat) {
                isBlocked = true;
                AbstractDungeon.actionManager.addToBottom(new ChangeHeatStateAction(false));
            }
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(UI,
                this.current_x - UI.getWidth() * Settings.scale / 2.0F,
                this.current_y - UI.getHeight() * Settings.scale / 2.0F,
                UI.getWidth() * Settings.scale / 2.0F, UI.getHeight() * Settings.scale / 2.0F,
                UI.getWidth() * Settings.scale, UI.getHeight() * Settings.scale,
                SCALE, SCALE, 0,
                0, 0,
                UI.getWidth(), UI.getHeight(),
                false, false);

        sb.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        DesireBarChart.render(shapeRenderer);
        shapeRenderer.end();
        sb.begin();

        FontHelper.renderWrappedText(sb, FontHelper.energyNumFontBlue,
                Integer.toString(desireAmount), this.current_x - 20 * Settings.scale, this.current_y + 170 * Settings.scale,
                1.0F, Color.WHITE, 0.8F);

        if (this.desireAmount >= HeatStanceCriticalValue)
            FontHelper.renderWrappedText(sb, FontHelper.energyNumFontBlue,
                    "/" + Integer.toString(desireAmount / 2 * 2), this.current_x + 20 * Settings.scale, this.current_y + 170 * Settings.scale,
                    1.0F, Transparency, 0.8F);
        else
            FontHelper.renderWrappedText(sb, FontHelper.energyNumFontBlue,
                    "/" + Integer.toString(desireAmount / 2), this.current_x + 20 * Settings.scale, this.current_y + 170 * Settings.scale,
                    1.0F, Transparency, 0.8F);

        this.hb.render(sb);

        if (shouldRenderTip)
            TipHelper.queuePowerTips(this.hb.cX + 20.0F * Settings.scale, this.hb.cY + 20.0F * Settings.scale + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
    }
}
