package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.misc.DesireUI;

public class JoyOfUnion extends AbstractMerchantAliceCard {
    public JoyOfUnion() {
        super(JoyOfUnion.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = DesireUI.inst.desireAmount * this.baseMagicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
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
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new JoyOfUnion();
    }
}
