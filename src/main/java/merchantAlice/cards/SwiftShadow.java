package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwiftShadow extends AbstractMerchantAliceCard {
    public SwiftShadow() {
        super(SwiftShadow.class.getSimpleName(), -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.block = this.baseBlock = 3;
        this.selfRetain = true;
    }

    @Override
    public void triggerOnOtherManualDiscard(AbstractCard c) {
        calculateCardDamage(null);
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
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
            upgradeBlock(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SwiftShadow();
    }
}
