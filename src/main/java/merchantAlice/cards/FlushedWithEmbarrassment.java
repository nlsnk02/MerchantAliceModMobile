package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.actions.GainDesireAction;

public class FlushedWithEmbarrassment extends AbstractMerchantAliceCard {
    public FlushedWithEmbarrassment() {
        super(FlushedWithEmbarrassment.class.getSimpleName(), 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new GainDesireAction(AbstractDungeon.player.currentBlock));
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlushedWithEmbarrassment();
    }
}
