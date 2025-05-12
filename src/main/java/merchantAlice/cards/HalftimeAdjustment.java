package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HalftimeAdjustment extends AbstractMerchantAliceCard {
    public HalftimeAdjustment() {
        super(HalftimeAdjustment.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new HealAction(p, p, this.magicNumber));
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
        return new HalftimeAdjustment();
    }
}
