package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;
import merchantAlice.misc.TentacleStance;

public class Tentacle extends AbstractMerchantAliceCard {
    public Tentacle() {
        super(Tentacle.class.getSimpleName(), 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(new TentacleStance()));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(p.stance instanceof TentacleStance){
                    ((TentacleStance)p.stance).duration = 3;
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
    public void onRemoveFromMasterDeck() {
        AbstractDungeon.effectsQueue.add(new NecronomicurseEffect(makeCopy(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(makeCopy()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tentacle();
    }
}
