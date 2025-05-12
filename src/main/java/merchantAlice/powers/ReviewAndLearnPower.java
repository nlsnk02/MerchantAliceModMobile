package merchantAlice.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.helper.ModHelper;
import merchantAlice.interfaces.OnManualDiscardPower;

public class ReviewAndLearnPower extends AbstractPower implements OnManualDiscardPower {
    public static final String POWER_ID = ModHelper.makeID(ReviewAndLearnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(ReviewAndLearnPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(ReviewAndLearnPower.class.getSimpleName(), false);

    public ReviewAndLearnPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.owner = owner;

        this.loadRegion("tools");

//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onManualDiscard() {
        addToTop(new GainDesireAction(this.amount, false));
    }
}