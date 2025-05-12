package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.helper.ModHelper;

import java.util.ArrayList;

public class Overambitious extends AbstractMerchantAliceCard {
    public Overambitious() {
        super(Overambitious.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            private boolean isFirstTime = true;

            @Override
            public void update() {
                if (isFirstTime) {
                    this.isFirstTime = false;
                    this.duration = Settings.ACTION_DUR_FAST;

                    if (p.hand.isEmpty()) {
                        this.isDone = true;
                        return;
                    }

                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                    AbstractDungeon.player.hand.applyPowers();
                    return;
                }

                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

                    for (AbstractCard abstractCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                        AbstractDungeon.player.hand.moveToDiscardPile(abstractCard);
                        abstractCard.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                        int cost = abstractCard.costForTurn;

                        ArrayList<AbstractCard> list = new ArrayList<>();
                        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                            if (c.costForTurn > 0 && c.costForTurn > cost)
                                list.add(c);
                        }
                        list = ModHelper.getRandomElements(list, 2);
                        if (!list.isEmpty()) {
                            for (AbstractCard cc : list) {
                                AbstractDungeon.player.drawPile.group.remove(cc);
                                AbstractDungeon.player.drawPile.group.add(cc);
                            }
                            addToTop(new DrawCardAction(list.size()));
                        }
                    }

                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    this.isDone = true;
                }

                tickDuration();
            }
        });
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
        return new Overambitious();
    }
}
