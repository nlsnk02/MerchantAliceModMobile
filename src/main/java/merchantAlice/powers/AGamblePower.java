package merchantAlice.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.helper.ModHelper;

public class AGamblePower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makeID(AGamblePower.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(AGamblePower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(AGamblePower.class.getSimpleName(), false);

    public AGamblePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = amount;
        this.isTurnBased = true;

        this.loadRegion("tools");
        //        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        //        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.addToBot(new LoseHPAction(this.owner, this.owner, this.amount, AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.player.loseGold(this.amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new ReducePowerAction(this.owner, this.owner, AGamblePower.POWER_ID, this.amount));
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}