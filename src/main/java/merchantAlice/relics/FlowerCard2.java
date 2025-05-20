package merchantAlice.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import merchantAlice.MerchantAlice;
import merchantAlice.cards.ChooseDiscard;
import merchantAlice.cards.ChooseDrawcard;
import merchantAlice.characters.Char;
import merchantAlice.helper.ModHelper;
import merchantAlice.interfaces.OnManualDiscardPower;
import merchantAlice.misc.DesireUI;
import merchantAlice.powers.ShufflingDemonstrationPower;

import java.util.ArrayList;

public class FlowerCard2 extends CustomRelic {
    public static final String ID = ModHelper.makeID(FlowerCard2.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/FlowerCard.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/FlowerCard.png";


    public FlowerCard2() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 3;
    }


    @Override
    public void atBattleStartPreDraw() {
        this.counter = 3;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (this.counter > 0) {
            counter--;
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new ChooseDiscard());
            stanceChoices.add(new ChooseDrawcard());
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    addToBot(new ChooseOneAction(stanceChoices));
                    this.isDone = true;
                }
            });
        }

        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                ModHelper.CardsHaveDrawnThisTurn = 0;
                this.isDone = true;
            }
        });
    }

    @Override
    public void onShuffle() {
        if (AbstractDungeon.player.hasPower(ShufflingDemonstrationPower.POWER_ID)) {
            int amount = AbstractDungeon.player.getPower(ShufflingDemonstrationPower.POWER_ID).amount;
            addToBot(new DrawCardAction(amount));
        }
    }

    @Override
    public void onManualDiscard() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnManualDiscardPower) {
                ((OnManualDiscardPower) p).onManualDiscard();
            }
        }
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        ModHelper.CardsHaveDrawnThisTurn++;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (AbstractDungeon.player instanceof Char) {
            if (DesireUI.inst.isHeat)
                return damage + (DesireUI.inst.desireAmount / 2 * 2);
            else
                return damage + (DesireUI.inst.desireAmount / 2);
        }
        return damage;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FlowerCard2();
    }
}
