package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.powers.SuperficialGlancePower;

import java.util.Iterator;

public class SuperficialGlance extends AbstractMerchantAliceCard {
    public SuperficialGlance() {
        super(SuperficialGlance.class.getSimpleName(), 0, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new AbstractGameAction() {

                private AbstractPlayer player;
                private int numberOfCards;

                {
                    this.duration = 0.2F;
                    this.player = p;
                    this.numberOfCards = 1;
                }

                public void update() {
                    if (this.duration == 0.2F) {
                        if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                            AbstractCard c;
                            Iterator var6;
                            if (this.player.drawPile.size() > this.numberOfCards) {
                                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                                var6 = this.player.drawPile.group.iterator();

                                while (var6.hasNext()) {
                                    c = (AbstractCard) var6.next();
                                    temp.addToTop(c);
                                }

                                temp.sortAlphabetically(true);
                                temp.sortByRarityPlusStatusCardType(false);
                                AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, EXTENDED_DESCRIPTION[0], false);

                                this.tickDuration();
                            } else this.isDone = true;
                        } else this.isDone = true;

                    } else {
                        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                                this.player.drawPile.removeCard(c);
                                this.player.drawPile.group.add(c);
                            }

                            AbstractDungeon.gridSelectScreen.selectedCards.clear();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        }

                        this.tickDuration();
                    }
                }
            });
        addToBot(new ApplyPowerAction(p, p, new SuperficialGlancePower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperficialGlance();
    }
}
