package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import merchantAlice.patches.cards.RestoreRetainedCardsActionPatch;

public class Abandon extends AbstractMerchantAliceCard {
    public Abandon() {
        super(Abandon.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 7;
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

//                        int count = RestoreRetainedCardsActionPatch.retainCounts.get(abstractCard.cardID) == null ?
//                                0 : RestoreRetainedCardsActionPatch.retainCounts.get(abstractCard.cardID);
                        int count = ((AbstractMerchantAliceCard)abstractCard).retainAmount;

                        baseDamage = baseDamage + baseMagicNumber * count;
                        calculateCardDamage(m);
                        baseDamage = baseDamage - baseMagicNumber * count;

                        if (baseDamage != damage) isDamageModified = true;
                        addToTop(new DamageAction(m, new DamageInfo(p, Abandon.this.damage, Abandon.this.damageTypeForTurn),
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
            upgradeDamage(2);
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Abandon();
    }
}
