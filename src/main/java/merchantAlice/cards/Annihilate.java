package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Annihilate extends AbstractMerchantAliceCard {
    public Annihilate() {
        super(Annihilate.class.getSimpleName(), -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 5;
        this.selfRetain = true;
    }

    @Override
    public void triggerOnOtherManualDiscard(AbstractCard c) {
        AbstractCreature m = AbstractDungeon.getRandomMonster();
        if (m != null) {
            addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Annihilate();
    }
}
