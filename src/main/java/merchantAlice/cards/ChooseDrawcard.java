package merchantAlice.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChooseDrawcard extends AbstractMerchantAliceCard {

    public ChooseDrawcard() {
        super(ChooseDrawcard.class.getSimpleName(), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        this.steal(new ChooseCalm());
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.render(sb);
        }
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChooseDrawcard();
    }
}
