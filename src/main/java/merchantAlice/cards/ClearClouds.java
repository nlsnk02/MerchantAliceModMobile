package merchantAlice.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.GainDesireAction;

public class ClearClouds extends AbstractMerchantAliceCard {
    public ClearClouds() {
        super(ClearClouds.class.getSimpleName(), -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        this.selfRetain = true;
    }

    @Override
    public void triggerOnOtherManualDiscard(AbstractCard c) {
        addToBot(new GainDesireAction(this.magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
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
        return new ClearClouds();
    }
}
