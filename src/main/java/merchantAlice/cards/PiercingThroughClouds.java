package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.helper.ModHelper;

public class PiercingThroughClouds extends AbstractMerchantAliceCard {
    public PiercingThroughClouds() {
        super(PiercingThroughClouds.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = 6;
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = ModHelper.CardsHaveDrawnThisTurn;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.baseMagicNumber; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
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
        return new PiercingThroughClouds();
    }
}
