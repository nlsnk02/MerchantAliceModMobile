package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.characters.Char;
import merchantAlice.misc.DesireUI;

public class BurstingWithJoy extends AbstractMerchantAliceCard {
    public BurstingWithJoy() {
        super(BurstingWithJoy.class.getSimpleName(), 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 0;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player instanceof Char) {
            this.baseBlock = DesireUI.inst.desireAmount;
            if (upgraded) this.baseDamage += baseMagicNumber;
        }
        super.applyPowers();

        if (upgraded)
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        else
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new GainDesireAction(this.magicNumber));
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BurstingWithJoy();
    }
}
