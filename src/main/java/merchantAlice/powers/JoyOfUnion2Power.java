package merchantAlice.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.helper.ModHelper;

public class JoyOfUnion2Power extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(JoyOfUnion2Power.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(JoyOfUnion2Power.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(JoyOfUnion2Power.class.getSimpleName(), false);

    public JoyOfUnion2Power(AbstractCreature owner, int amount) {
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

    public void onGainDesire(int n) {
        addToBot(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(this.amount * n, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}