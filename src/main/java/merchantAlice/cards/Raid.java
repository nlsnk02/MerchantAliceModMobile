package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Raid extends AbstractMerchantAliceCard {
    public Raid() {
        super(Raid.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 8;
    }

    @Override
    public void triggerOnManualDiscardOwn() {
        int tempDamage = this.baseDamage;
        this.baseDamage = this.baseMagicNumber;
        calculateCardDamage(null);
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.baseDamage = tempDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeMagicNumber(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Raid();
    }
}
