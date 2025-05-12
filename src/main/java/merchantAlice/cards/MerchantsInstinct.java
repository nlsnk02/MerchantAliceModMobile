package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class MerchantsInstinct extends AbstractMerchantAliceCard {
    public MerchantsInstinct() {
        super(MerchantsInstinct.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.damage = this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 10;
        this.exhaust = true;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {

            {
                this.actionType = AbstractGameAction.ActionType.DAMAGE;
            }

            @Override
            public void update() {
                if (m != null) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect, false));
                    m.damage(new DamageInfo(p, damage, damageTypeForTurn));
//                    if (((m).isDying || m.currentHealth <= 0) && !m.halfDead) {
                    if (((m).isDying || m.currentHealth <= 0)) {

                        //斩杀效果
                        CardCrawlGame.sound.play("GOLD_JINGLE");
                        AbstractDungeon.player.gold += MerchantsInstinct.this.magicNumber;
                        for (int i = 0; i < MerchantsInstinct.this.magicNumber; ++i) {
                            AbstractDungeon.effectList.add(new GainPennyEffect(m.hb.cX, m.hb.cY));
                        }
                        addToTop(new WaitAction(0.25F));

                    }
                    if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                        AbstractDungeon.actionManager.clearPostCombatActions();
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MerchantsInstinct();
    }
}
