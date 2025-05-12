package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Azure extends AbstractMerchantAliceCard {
    public Azure() {
        super(Azure.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.block = this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void triggerOnManualDiscardOwn() {
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Azure();
    }
}
