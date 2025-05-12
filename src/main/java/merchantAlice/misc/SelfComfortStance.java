package merchantAlice.misc;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.helper.GIFAnimationHelper;
import merchantAlice.helper.ModHelper;

public class SelfComfortStance extends AbstractStance {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MerchantAlice:SelfComfortStance");

    public int duration;

    public SelfComfortStance() {
        this.ID = "MerchantAlice:SelfComfortStance";
        this.name = powerStrings.NAME;
        this.duration = 4;
        this.updateDescription();
    }

    @Override
    public void onEnterStance() {
        GIFAnimationHelper.selfComfort();
    }

    @Override
    public void onExitStance() {
        ModHelper.initSPState();
    }

    @Override
    public void atStartOfTurn(){
        if(duration>0){
            duration--;
            updateDescription();
            AbstractDungeon.actionManager.addToBottom(new GainDesireAction(4, false));
        }else{
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.duration + powerStrings.DESCRIPTIONS[1];
    }
}
