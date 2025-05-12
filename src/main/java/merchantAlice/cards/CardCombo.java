package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardCombo extends AbstractMerchantAliceCard {
    public CardCombo() {
        super(CardCombo.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.magicNumber = this.baseMagicNumber = 0;
        this.isMultiDamage = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                baseDamage = p.hand.size() + baseMagicNumber;
                calculateCardDamage(null);
                addToTop(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardCombo();
    }
}
