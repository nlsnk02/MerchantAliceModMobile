package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EverChanging extends AbstractMerchantAliceCard {
    public EverChanging() {
        super(EverChanging.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber, new AbstractGameAction() {

            {
                this.duration = 0.3F;
            }

            @Override
            public void update() {
                tickDuration();
                if (this.isDone) {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        if (c.type == CardType.POWER) {
                            AbstractDungeon.player.hand.moveToDiscardPile(c);
                            c.triggerOnManualDiscard();
                            GameActionManager.incrementDiscard(false);
                        }
                    }
                }
            }
        }));
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
        return new EverChanging();
    }
}
