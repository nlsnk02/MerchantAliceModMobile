package merchantAlice.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import merchantAlice.misc.DesireUI;
import merchantAlice.powers.BlissfulParadisePower;

public class ChangeHeatStateAction extends AbstractGameAction {

    private boolean into;

    public ChangeHeatStateAction(boolean into) {
        this.into = into;
    }

    @Override
    public void update() {
        if (into && !DesireUI.inst.isHeat) {
            AbstractDungeon.player.energy.energy += 1;
            if (AbstractDungeon.player.hasPower(BlissfulParadisePower.POWER_ID))
                ((BlissfulParadisePower) AbstractDungeon.player.getPower(BlissfulParadisePower.POWER_ID)).onEnterOrLeaveHeat();
        }
        if (!into && DesireUI.inst.isHeat) {
            AbstractDungeon.player.energy.energy -= 1;
            if (AbstractDungeon.player.hasPower(BlissfulParadisePower.POWER_ID))
                ((BlissfulParadisePower) AbstractDungeon.player.getPower(BlissfulParadisePower.POWER_ID)).onEnterOrLeaveHeat();
        }

        DesireUI.inst.isHeat = into;
        DesireUI.inst.isBlocked = false;

        this.isDone = true;
    }
}
