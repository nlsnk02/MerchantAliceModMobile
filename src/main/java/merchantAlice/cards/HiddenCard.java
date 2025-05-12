package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class HiddenCard extends AbstractMerchantAliceCard {
    public HiddenCard() {
        super(HiddenCard.class.getSimpleName(), 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.player.decreaseMaxHealth(baseMagicNumber);
                this.isDone = true;
            }
        });

        if (m.type != AbstractMonster.EnemyType.BOSS) {
            addToBot(new SuicideAction(m, true));
        } else {
            addToBot(new TalkAction(true, EXTENDED_DESCRIPTION[0], 1, 1));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> list = (ArrayList<AbstractCard>) AbstractDungeon.player.hand.group.clone();
                for (AbstractCard abstractCard : list) {
                    AbstractDungeon.player.hand.moveToExhaustPile(abstractCard);

                    abstractCard.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);

                    abstractCard.triggerOnExhaust();
                    CardCrawlGame.dungeon.checkForPactAchievement();
                    abstractCard.exhaustOnUseOnce = false;
                    abstractCard.freeToPlayOnce = false;
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HiddenCard();
    }
}
