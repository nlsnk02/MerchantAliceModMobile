package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChooseDiscard extends AbstractMerchantAliceCard {

    public ChooseDiscard() {
        super(ChooseDiscard.class.getSimpleName(), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        this.steal(new ChooseWrath());
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChooseDiscard();
    }
}
