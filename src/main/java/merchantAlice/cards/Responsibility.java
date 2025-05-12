package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.DrawFromDiscardpileAction;

public class Responsibility extends AbstractMerchantAliceCard {
    public Responsibility() {
        super(Responsibility.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.block = this.baseBlock = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DrawFromDiscardpileAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Responsibility();
    }
}
