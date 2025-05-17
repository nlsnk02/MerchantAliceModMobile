package merchantAlice.relics;

import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import merchantAlice.MerchantAlice;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.characters.Char;
import merchantAlice.helper.ModHelper;

public class PrivatePhotos extends CustomRelic {
    public static final String ID = ModHelper.makeID(PrivatePhotos.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/PrivatePhotos.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/PrivatePhotos.png";


    public PrivatePhotos() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.COMMON, LandingSound.MAGICAL);
    }


    @Override
    public void atBattleStartPreDraw() {
        if (AbstractDungeon.player instanceof Char) {
            addToBot(new GainDesireAction(6, false));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PrivatePhotos();
    }
}
