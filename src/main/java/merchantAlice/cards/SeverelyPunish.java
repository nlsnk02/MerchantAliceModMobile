package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SeverelyPunish extends AbstractMerchantAliceCard {
    public SeverelyPunish() {
        super(SeverelyPunish.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.block = this.baseBlock = 0;
        this.damage = this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.baseDamage = AbstractDungeon.player.hand.group.size();
        if (this.upgraded) {
            this.baseBlock += this.baseMagicNumber;
            this.baseDamage += this.baseMagicNumber;
        }
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeverelyPunish();
    }
}
