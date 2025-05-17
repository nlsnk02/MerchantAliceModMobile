package merchantAlice.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import merchantAlice.MerchantAlice;
import merchantAlice.characters.Char;
import merchantAlice.helper.ModHelper;
import merchantAlice.misc.DesireUI;

public class TinyToy extends CustomRelic {
    public static final String ID = ModHelper.makeID(TinyToy.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/TinyToy.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/TinyToy.png";


    public TinyToy() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player instanceof Char && DesireUI.inst.isHeat) {
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TinyToy();
    }
}
