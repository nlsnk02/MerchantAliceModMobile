package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import merchantAlice.helper.ModHelper;

public class HighStakesGamble extends AbstractMerchantAliceCard {
    public HighStakesGamble() {
        super(HighStakesGamble.class.getSimpleName(), -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 8;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int drawNum = EnergyPanel.totalCount;
                if (AbstractDungeon.player.hasRelic("Chemical X")) drawNum += 2;

                if ((p.drawPile.size() + p.discardPile.size()) > 0)
                    addToTop(new DrawCardAction(drawNum, new AbstractGameAction() {

                        {
                            this.duration = 0.35F;
                        }

                        @Override
                        public void update() {
                            tickDuration();
                            if (this.isDone) {
                                int times = 0;
                                for (AbstractCard c : DrawCardAction.drawnCards) {
                                    times += Math.max(c.costForTurn, 0);
                                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                                    c.triggerOnManualDiscard();
                                    GameActionManager.incrementDiscard(false);

                                    ModHelper.logger.info("========" + times + "========");
                                }
                                addToTop(new DamageAction(m, new DamageInfo(p, damage * times, damageTypeForTurn),
                                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                            }
                        }
                    }));
                else
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

                if (!freeToPlayOnce)
                    AbstractDungeon.player.energy.use(EnergyPanel.totalCount);

                this.isDone = true;
            }
        });
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HighStakesGamble();
    }
}
