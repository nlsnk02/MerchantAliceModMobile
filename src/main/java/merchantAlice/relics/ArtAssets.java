package merchantAlice.relics;

import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import merchantAlice.MerchantAlice;
import merchantAlice.effect.SmithEffect;
import merchantAlice.helper.ModHelper;

public class ArtAssets extends CustomRelic {
    public static final String ID = ModHelper.makeID(ArtAssets.class.getSimpleName());
    private static final String IMG = "MerchantAliceModResources/img/relics/ArtAssets.png";
    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/ArtAssets.png";


    public ArtAssets() {
        super(ID, AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), AssetLoader.getTexture(MerchantAlice.MOD_ID, IMG), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectsQueue.add(new SmithEffect());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArtAssets();
    }
}
