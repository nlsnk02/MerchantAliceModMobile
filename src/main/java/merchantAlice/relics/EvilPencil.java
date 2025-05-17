package merchantAlice.relics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import merchantAlice.MerchantAlice;
import merchantAlice.cards.Sketch;
import merchantAlice.helper.ModHelper;

public class EvilPencil extends CustomRelic {
    public static final String ID = ModHelper.makeID(EvilPencil.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/EvilPencil.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/EvilPencil.png";

    private AbstractCard cardToPreView;

    public EvilPencil() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 0;
        this.cardToPreView = new Sketch();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == AbstractCard.CardType.ATTACK) {
            this.counter++;
        }

        if (this.counter >= 5) {
            this.counter = this.counter % 5;
            AbstractCard c = new Sketch();
//            CardModifierManager.addModifier(c, new EtherealMod());
            addToBot(new MakeTempCardInHandAction(c));
        }
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        super.renderTip(sb);
        if (this.hb.hovered) {
            this.cardToPreView.current_x = this.cardToPreView.target_x = this.currentX;
            this.cardToPreView.current_y = this.cardToPreView.target_y =
                    this.currentY - AbstractCard.IMG_HEIGHT_S / 2F - 100 * Settings.scale;
            this.cardToPreView.render(sb);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EvilPencil();
    }
}
