package merchantAlice.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.helper.ModHelper;

public class TheOtherShorePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(TheOtherShorePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(TheOtherShorePower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(TheOtherShorePower.class.getSimpleName(), false);

    private boolean turnHasBegin;

    public TheOtherShorePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.turnHasBegin = true;
        this.loadRegion("tools");

//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                turnHasBegin = true;
                this.isDone = true;
            }
        });
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        turnHasBegin = false;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (turnHasBegin)
            addToBot(new DamageAllEnemiesAction(null,
                    DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}