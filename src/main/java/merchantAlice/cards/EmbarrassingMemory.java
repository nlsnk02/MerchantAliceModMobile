package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class EmbarrassingMemory extends AbstractMerchantAliceCard {
    public EmbarrassingMemory() {
        super(EmbarrassingMemory.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.block = this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void triggerOnManualDiscardOwn() {
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber)));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, 2, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EmbarrassingMemory();
    }
}
