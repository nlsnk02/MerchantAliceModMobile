package merchantAlice.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import merchantAlice.MerchantAlice;
import merchantAlice.cards.ChooseDiscard;
import merchantAlice.cards.ChooseDrawcard;
import merchantAlice.helper.ModHelper;

import java.util.ArrayList;

public class FlowerCard2 extends CustomRelic {
    public static final String ID = ModHelper.makeID(FlowerCard2.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/FlowerCard.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/FlowerCard.png";


    public FlowerCard2() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 3;
    }


    @Override
    public void atBattleStartPreDraw() {
        this.counter = 3;
    }

    @Override
    public void atTurnStart() {
        if (this.counter > 0) {
            counter--;
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new ChooseDiscard());
            stanceChoices.add(new ChooseDrawcard());
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    addToBot(new ChooseOneAction(stanceChoices));
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FlowerCard2();
    }
}
