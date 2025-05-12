package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class WitnessTheEnemy extends AbstractMerchantAliceCard {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("WitnessTheEnemy");

    public WitnessTheEnemy() {
        super(WitnessTheEnemy.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 28;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m != null && m.getIntentBaseDmg() <= 0) {
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                } else {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, uiStrings.TEXT[0], true));
                }
                this.isDone = true;
            }
        });
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.selfRetain = true;
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WitnessTheEnemy();
    }
}
