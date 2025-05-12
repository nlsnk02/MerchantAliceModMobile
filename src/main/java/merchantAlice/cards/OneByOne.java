package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class OneByOne extends AbstractMerchantAliceCard {
    public OneByOne() {
        super(OneByOne.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    private AbstractMonster getEnemyWithTheLowestHealth() {
        int lowestHp = 99999;
        AbstractMonster m = null;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                if (mo.currentHealth < lowestHp) {
                    lowestHp = mo.currentHealth;
                    m = mo;
                }
            }
        }
        return m;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractMonster m = getEnemyWithTheLowestHealth();
        if (m != null) calculateCardDamage(m);
        if (this.damage != this.baseDamage) this.isDamageModified = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        AbstractMonster m = getEnemyWithTheLowestHealth();
        if (m != null) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
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
        return new OneByOne();
    }
}
