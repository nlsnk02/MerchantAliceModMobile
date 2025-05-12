package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RelaxedAndJoyful2 extends AbstractMerchantAliceCard {

    private boolean avil;

    public RelaxedAndJoyful2() {
        super(RelaxedAndJoyful2.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 7;
        this.avil = false;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (this.baseMagicNumber <= AbstractDungeon.player.hand.size()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            this.avil = true;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            this.avil = false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.avil) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2)));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2)));
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RelaxedAndJoyful2();
    }
}
