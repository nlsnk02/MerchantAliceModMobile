package merchantAlice.misc;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.helper.GIFAnimationHelper;
import merchantAlice.helper.ModHelper;

public class TentacleStance extends AbstractStance {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MerchantAlice:TentacleStance");

    public int duration;

    public TentacleStance() {
        this.ID = "MerchantAlice:TentacleStance";
        this.name = powerStrings.NAME;
        this.duration = 3;
        this.updateDescription();
    }

    @Override
    public void onEnterStance() {
        GIFAnimationHelper.tentacle();
    }

    @Override
    public void onExitStance() {
        ModHelper.initSPState();
    }

    @Override
    public void atStartOfTurn() {
        if (duration > 0) {
            duration--;
            updateDescription();
            AbstractDungeon.actionManager.addToBottom(new GainDesireAction(2, false));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, 6));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.duration + powerStrings.DESCRIPTIONS[1];
    }
}
