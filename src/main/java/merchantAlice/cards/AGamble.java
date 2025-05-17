package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.helper.CardTagHelper;
import merchantAlice.powers.AGamblePower;

public class AGamble extends AbstractMerchantAliceCard {
    public AGamble() {
        super(AGamble.class.getSimpleName(), 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {

            {
                this.duration = 0.2F;
            }

            @Override
            public void update() {
                if(this.duration == 0.2F){
                    for(AbstractCard c : AbstractDungeon.player.hand.group){
                        c.modifyCostForCombat(-99);
//                        CardModifierManager.addModifier(c, new ExhaustMod());
//                        CardModifierManager.addModifier(c, new EtherealMod());
                        CardTagHelper.etherealedCards.add(c);
                        CardTagHelper.exhaustCards.add(c);
                        c.exhaust = true;
                        c.isEthereal = true;
                        c.flash();
                    }
                }
                tickDuration();
            }
        });
        addToBot(new ApplyPowerAction(p, p, new AGamblePower(p, this.magicNumber)));
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
        return new AGamble();
    }
}
