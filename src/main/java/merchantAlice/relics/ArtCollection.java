package merchantAlice.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import merchantAlice.MerchantAlice;
import merchantAlice.helper.ModHelper;

public class ArtCollection extends CustomRelic {
    public static final String ID = ModHelper.makeID(ArtCollection.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/ArtCollection.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/ArtCollection.png";


    public ArtCollection() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.SHOP, LandingSound.MAGICAL);
    }


    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.hand.size() >= 5) {
                    addToTop(new GainBlockAction(AbstractDungeon.player, 1));
                }
                this.isDone = true;
            }
        });
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArtCollection();
    }
}
