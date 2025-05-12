package merchantAlice.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.characters.Char;
import merchantAlice.effect.FloatLoveEffect;
import merchantAlice.misc.DesireUI;
import merchantAlice.powers.JoyOfUnion2Power;

public class GainDesireAction extends AbstractGameAction {

    private final int amount;
    private final boolean isFromCard;

    public GainDesireAction(int amount) {
        this.amount = amount;
        this.isFromCard = true;
    }

    public GainDesireAction(int amount, boolean isFromCard) {
        this.amount = amount;
        this.isFromCard = isFromCard;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player instanceof Char) {
            DesireUI.inst.desireAmount += amount;

            DesireUI.inst.desireAmount = Math.max(0, DesireUI.inst.desireAmount);

//        AbstractDungeon.effectList.add(new AbstractGameEffect() {
//            @Override
//            public void update(){
//                CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
//                this.isDone = true;
//            }
//            @Override
//            public void render(SpriteBatch spriteBatch) {}
//            @Override
//            public void dispose() {}
//        });

            if (amount > 0) {

                if (isFromCard) for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof JoyOfUnion2Power) {
                        ((JoyOfUnion2Power) p).onGainDesire(amount);
                    }
                }

                for (int i = 0; i < 3; i++) {
                    AbstractDungeon.effectList.add(new FloatLoveEffect(DesireUI.inst.current_x - 30 * Settings.scale, DesireUI.inst.current_y - 100 * Settings.scale));
                    AbstractDungeon.effectList.add(new FloatLoveEffect(DesireUI.inst.current_x + 30 * Settings.scale, DesireUI.inst.current_y - 100 * Settings.scale));
                }
            }
        }
        this.isDone = true;
    }
}
