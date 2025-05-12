package merchantAlice.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.misc.DesireUI;

public class ForcedClim extends AbstractMerchantAliceCard {
    public ForcedClim() {
        super(ForcedClim.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.isEthereal = true;
    }


    @Override
    public void triggerOnGlowCheck() {
        if (DesireUI.inst.desireAmount < DesireUI.HeatStanceCriticalValue) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (DesireUI.inst.desireAmount < DesireUI.HeatStanceCriticalValue)
            addToBot(new GainDesireAction(DesireUI.HeatStanceCriticalValue - DesireUI.inst.desireAmount));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ForcedClim();
    }
}
