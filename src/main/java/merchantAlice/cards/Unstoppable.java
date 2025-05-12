package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import merchantAlice.actions.GainDesireAction;

public class Unstoppable extends AbstractMerchantAliceCard {
    public Unstoppable() {
        super(Unstoppable.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            private boolean isFirstTime = true;
            @Override
            public void update() {
                if(isFirstTime){
                    this.isFirstTime = false;
                    this.duration = Settings.ACTION_DUR_FAST;

                    if(p.hand.isEmpty()){
                        this.isDone = true;
                        return;
                    }

                    int n = Math.min(baseMagicNumber, p.hand.size());

                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], n, false, false);
                    AbstractDungeon.player.hand.applyPowers();
                    return;
                }

                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

                    for (AbstractCard abstractCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                        AbstractDungeon.player.hand.moveToDiscardPile(abstractCard);
                        abstractCard.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                        int cost = abstractCard.costForTurn;
                        if(cost>0) {
                            addToTop(new GainDesireAction(cost));
                            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, cost)));
                            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, cost)));
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
            upgradeMagicNumber(1);
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Unstoppable();
    }
}
