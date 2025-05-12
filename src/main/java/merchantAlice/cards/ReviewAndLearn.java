package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.ReviewAndLearnPower;

public class ReviewAndLearn extends AbstractMerchantAliceCard {
    public ReviewAndLearn() {
        super(ReviewAndLearn.class.getSimpleName(), 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ReviewAndLearnPower(p, this.magicNumber)));
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
        return new ReviewAndLearn();
    }
}
