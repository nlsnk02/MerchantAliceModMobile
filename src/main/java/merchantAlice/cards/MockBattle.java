package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.helper.CardTagHelper;

public class MockBattle extends AbstractMerchantAliceCard {
    public MockBattle() {
        super(MockBattle.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(5));
        addToBot(new AbstractGameAction() {
            {
                this.duration = 0.3F;
            }

            @Override
            public void update() {
                if (this.duration == 0.3F) {
                    if (p.hand.isEmpty()) {
                        this.isDone = true;
                        return;
                    }

                    int n = Math.min(magicNumber, AbstractDungeon.player.hand.size());

                    AbstractDungeon.handCardSelectScreen.open(DESCRIPTION_UPG, n, false, false);
                    AbstractDungeon.player.hand.applyPowers();
                    tickDuration();
                    return;
                }

                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
//                        CardModifierManager.addModifier(c, new RetainMod());
                        CardTagHelper.retianedCards.add(c);
                        c.selfRetain = true;
                        AbstractDungeon.player.hand.addToHand(c);
                    }
                    AbstractDungeon.handCardSelectScreen.selectedCards.clear();
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
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MockBattle();
    }
}
