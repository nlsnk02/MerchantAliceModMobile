package merchantAlice.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardExperiment extends AbstractMerchantAliceCard {
    public CardExperiment() {
        super(CardExperiment.class.getSimpleName(), -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        return false;
    }

    @Override
    public void triggerOnOtherManualDiscard(AbstractCard c) {
        c.freeToPlayOnce = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardExperiment();
    }
}
