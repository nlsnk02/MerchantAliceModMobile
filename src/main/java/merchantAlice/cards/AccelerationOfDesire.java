package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.AccelerationOfDesirePower;

public class AccelerationOfDesire extends AbstractMerchantAliceCard {
    public AccelerationOfDesire() {
        super(AccelerationOfDesire.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AccelerationOfDesirePower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AccelerationOfDesire();
    }
}
