package merchantAlice.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.GainDesireAction;

import java.lang.reflect.Field;

public class PerfectHarmony extends AbstractMerchantAliceCard {
    public PerfectHarmony() {
        super(PerfectHarmony.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 0;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (mo != null) {
            try {
                Field privateField = AbstractMonster.class.getDeclaredField("intentDmg");
                privateField.setAccessible(true);
                int intentDmg = (int) privateField.get(mo);
                if (intentDmg > 0) {
                    this.baseMagicNumber = intentDmg / 2;
                } else {
                    this.baseMagicNumber = 0;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.baseMagicNumber > 0) {
            addToBot(new GainDesireAction(this.baseMagicNumber));
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PerfectHarmony();
    }
}
