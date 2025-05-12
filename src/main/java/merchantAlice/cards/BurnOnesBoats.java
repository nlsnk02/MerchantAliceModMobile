package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BurnOnesBoats extends AbstractMerchantAliceCard {
    public BurnOnesBoats() {
        super(BurnOnesBoats.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardsToPreview = new Pursue();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> list = (ArrayList<AbstractCard>) AbstractDungeon.player.hand.group.clone();
                int i = 0;
                for (AbstractCard c : list) {
                    if (!(c.type == CardType.ATTACK)) {
                        i++;
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                    }
                }

                AbstractCard tmp = new Pursue();
                if (upgraded)
                    tmp.upgrade();
                if (i > 0)
                    addToTop(new MakeTempCardInHandAction(new Pursue(), i));

                this.isDone = true;
            }
        });
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            AbstractCard tmp = new Pursue();
            tmp.upgrade();
            this.cardsToPreview = tmp;
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BurnOnesBoats();
    }
}
