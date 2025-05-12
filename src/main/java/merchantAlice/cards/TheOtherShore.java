package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.TheOtherShorePower;

public class TheOtherShore extends AbstractMerchantAliceCard {
    public TheOtherShore() {
        super(TheOtherShore.class.getSimpleName(), 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TheOtherShorePower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheOtherShore();
    }
}
