package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.DesireSurgedPower;

public class DesireSurged extends AbstractMerchantAliceCard {
    public DesireSurged() {
        super(DesireSurged.class.getSimpleName(), 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DesireSurgedPower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DesireSurged();
    }
}
