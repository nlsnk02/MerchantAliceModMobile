package merchantAlice.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.helper.ModHelper;

public class WinThroughClevernessPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(WinThroughClevernessPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(WinThroughClevernessPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(WinThroughClevernessPower.class.getSimpleName(), false);

    public WinThroughClevernessPower(AbstractCreature owner, int amount) {
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


    public void atStartOfTurnOwn() {
        addToBot(new DrawCardAction(this.amount, new AbstractGameAction() {

            {
                this.duration = 0.5F;
            }

            @Override
            public void update() {
                tickDuration();
                if (this.isDone) {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                    }
                }
            }
        }));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}