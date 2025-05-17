package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.helper.CardTagHelper;

public class CardShaping extends AbstractMerchantAliceCard {
    public CardShaping() {
        super(CardShaping.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
        this.block = this.baseBlock = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DrawCardAction(this.magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                for(AbstractCard c : DrawCardAction.drawnCards){
//                    CardModifierManager.addModifier(c, new RetainMod());
                    CardTagHelper.retianedCards.add(c);
                    c.selfRetain = true;
                }
                this.isDone = true;
            }
        }));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardShaping();
    }
}
