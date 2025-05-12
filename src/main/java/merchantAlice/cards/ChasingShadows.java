package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChasingShadows extends AbstractMerchantAliceCard {
    public ChasingShadows() {
        super(ChasingShadows.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
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
        return new ChasingShadows();
    }
}
