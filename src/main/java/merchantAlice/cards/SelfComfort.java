package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;
import merchantAlice.misc.SelfComfortStance;

public class SelfComfort extends AbstractMerchantAliceCard {
    public SelfComfort() {
        super(SelfComfort.class.getSimpleName(), 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new SelfComfortStance()));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(p.stance instanceof SelfComfortStance){
                    ((SelfComfortStance)p.stance).duration = 4;
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(makeCopy()));
    }

    @Override
    public void onRemoveFromMasterDeck() {
        AbstractDungeon.effectsQueue.add(new NecronomicurseEffect(makeCopy(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SelfComfort();
    }
}
