package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.GainDesireAction;

public class DesireRising extends AbstractMerchantAliceCard {
    public DesireRising() {
        super(DesireRising.class.getSimpleName(), 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.block = this.baseBlock = 10;
        this.magicNumber = this.baseMagicNumber = 2;
        this.selfRetain = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainDesireAction(this.magicNumber, true));
        AbstractCard c = new DesireRising();
        c.isEthereal = true;
        addToBot(new MakeTempCardInHandAction(c));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DesireRising();
    }
}
