package merchantAlice.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import merchantAlice.helper.ModHelper;

import java.util.ArrayList;

public class ShowingWeaknessPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(ShowingWeaknessPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(ShowingWeaknessPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(ShowingWeaknessPower.class.getSimpleName(), false);

    public ShowingWeaknessPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.owner = owner;

        this.loadRegion("tools");

//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (this.amount > 0) {
            this.amount--;
            if (this.amount == 0) addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));

            if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
                flash();
                addToBot(new DrawCardAction(3, new AbstractGameAction() {
                    @Override
                    public void update() {
                        ArrayList<AbstractCard> list = new ArrayList<>(AbstractDungeon.player.masterDeck.getPurgeableCards().group);
                        for (AbstractCard c : DrawCardAction.drawnCards) {
                            c.modifyCostForCombat(-2);
//                            CardModifierManager.addModifier(c, new RetainMod());

                            if (c.type == AbstractCard.CardType.CURSE) {
                                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                                for (AbstractCard cc : list) {
                                    if (cc.uuid == c.uuid) {
                                        AbstractDungeon.player.masterDeck.removeCard(cc);
                                    }
                                }
                            }
                        }
                        this.isDone = true;
                    }
                }));
            }
        }
        return damageAmount;
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}