package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;

public class TheStormIsBrewing extends AbstractMerchantAliceCard {
    public TheStormIsBrewing() {
        super(TheStormIsBrewing.class.getSimpleName(), 2, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        this.block = this.baseBlock = 0;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (mo != null) {
            try {
                Field privateField = AbstractMonster.class.getDeclaredField("intentDmg");
                privateField.setAccessible(true);
                int intentDmg = (int) privateField.get(mo);
                if (intentDmg > 0) {
                    this.baseBlock = intentDmg;
                } else {
                    this.baseBlock = 0;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        super.calculateCardDamage(mo);

        if (this.block > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
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
        return new TheStormIsBrewing();
    }
}
