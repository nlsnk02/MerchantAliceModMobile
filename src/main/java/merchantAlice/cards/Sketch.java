package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;

public class Sketch extends AbstractMerchantAliceCard {
    public Sketch() {
        super(Sketch.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 0;
        this.exhaust = true;
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
                    this.baseDamage = intentDmg;
                }else {
                    this.baseDamage = 0;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        super.calculateCardDamage(mo);

        if (this.damage > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MakeTempCardInHandAction(new Sketch()));
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
        return new Sketch();
    }
}
