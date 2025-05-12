package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardDeckBuilding extends AbstractMerchantAliceCard {
    public CardDeckBuilding() {
        super(CardDeckBuilding.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, true));
        addToBot(new DrawCardAction(this.magicNumber));
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
        return new CardDeckBuilding();
    }
}
