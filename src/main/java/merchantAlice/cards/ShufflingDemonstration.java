package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.ShufflingDemonstrationPower;

public class ShufflingDemonstration extends AbstractMerchantAliceCard {
    public ShufflingDemonstration() {
        super(ShufflingDemonstration.class.getSimpleName(), 0, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ShufflingDemonstrationPower(p, this.magicNumber)));
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
        return new ShufflingDemonstration();
    }
}
