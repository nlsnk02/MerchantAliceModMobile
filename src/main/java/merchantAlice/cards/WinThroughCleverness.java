package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.WinThroughClevernessPower;

public class WinThroughCleverness extends AbstractMerchantAliceCard {
    public WinThroughCleverness() {
        super(WinThroughCleverness.class.getSimpleName(), 1, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WinThroughClevernessPower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WinThroughCleverness();
    }
}
