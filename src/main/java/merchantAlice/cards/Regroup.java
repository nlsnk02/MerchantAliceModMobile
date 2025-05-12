package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Regroup extends AbstractMerchantAliceCard {
    public Regroup() {
        super(Regroup.class.getSimpleName(), 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 15;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInDrawPileAction(this, 1, true, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(7);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Regroup();
    }
}
