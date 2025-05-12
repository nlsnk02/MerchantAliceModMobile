package merchantAlice.relics;

import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import merchantAlice.helper.ModHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class BrandNewMarkerPen extends CustomRelic {
    public static final String ID = ModHelper.makeID(BrandNewMarkerPen.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/BrandNewMarkerPen.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/BrandNewMarkerPen.png";

    private boolean cardsSelected = true;

    public BrandNewMarkerPen() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void onEquip() {
        this.cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.getPurgeableCards().group.iterator();

        while (var2.hasNext()) {
            AbstractCard card = (AbstractCard) var2.next();
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            if (tmp.group.size() == 1) {
                this.giveCards(tmp.group);
            } else if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1], false, false, false, false);
            } else {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
                AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1], false, false, false, false);
            }

        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
        }

    }

    public void giveCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        Iterator<AbstractCard> i = group.iterator();

        while (i.hasNext()) {
            AbstractCard card = i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM && AbstractDungeon.transformedCard != null) {
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F, false));
                displayCount += (float) Settings.WIDTH / 6.0F;
            }
        }

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BrandNewMarkerPen();
    }
}
