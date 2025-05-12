package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.DrawFromDiscardpileAction;

public class TheFinalBlow extends AbstractMerchantAliceCard {
    public TheFinalBlow() {
        super(TheFinalBlow.class.getSimpleName(), 2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawFromDiscardpileAction(p.discardPile.size(), new AbstractGameAction() {
            @Override
            public void update() {
                for(AbstractCard c : DrawFromDiscardpileAction.drawnCards){
//                    CardModifierManager.addModifier(c, new ExhaustMod());
//                    CardModifierManager.addModifier(c, new EtherealMod());
                    c.modifyCostForCombat(-99);
                }
                this.isDone = true;
            }
        }));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheFinalBlow();
    }
}
