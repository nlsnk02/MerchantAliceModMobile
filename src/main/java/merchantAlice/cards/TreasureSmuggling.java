package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.powers.TreasureSmugglingPower;

public class TreasureSmuggling extends AbstractMerchantAliceCard {
    public TreasureSmuggling() {
        super(TreasureSmuggling.class.getSimpleName(), 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
        this.magicNumber = this.baseMagicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(TreasureSmugglingPower.POWER_ID))
            addToBot(new ApplyPowerAction(p, p, new TreasureSmugglingPower(p, this.magicNumber)));
        else {
            AbstractPower tmp = p.getPower(TreasureSmugglingPower.POWER_ID);
            tmp.amount = Math.max(tmp.amount, this.magicNumber);
            tmp.updateDescription();
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TreasureSmuggling();
    }
}
