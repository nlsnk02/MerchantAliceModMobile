package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CorneredBeast extends AbstractMerchantAliceCard {
    public CorneredBeast() {
        super(CorneredBeast.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 9;
        this.block = this.baseBlock = 9;
        this.magicNumber = this.baseMagicNumber = 9;
    }

    @Override
    public void triggerOnManualDiscardOwn() {
        int tempBlock = this.baseBlock;
        this.baseBlock = this.baseMagicNumber;
        calculateCardDamage(null);
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        this.baseBlock = tempBlock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            upgradeBlock(2);
            upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CorneredBeast();
    }
}
