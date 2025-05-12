//package merchantAlice.relics;
//
//import basemod.abstracts.CustomRelic;
//import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.ImageMaster;
//import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
//import merchantAlice.helper.ModHelper;
//
//public class FlowerCard extends CustomRelic {
//    public static final String ID = ModHelper.makeID(FlowerCard.class.getSimpleName());
//    private static final String IMG = "MerchantAliceModResources/img/relics/FlowerCard.png";
//    private static final String IMG_OTL = "MerchantAliceModResources/img/relics/outline/FlowerCard.png";
//
//
//    public FlowerCard() {
//        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.STARTER, LandingSound.MAGICAL);
//        this.counter = 0;
//    }
//
//
//    @Override
//    public void atBattleStartPreDraw() {
//        AbstractDungeon.player.gameHandSize -= 2;
//        this.counter = 0;
//    }
//
//    @Override
//    public void onVictory() {
//        AbstractDungeon.player.gameHandSize += 2 - this.counter;
//        this.counter = 0;
//    }
//
//    @Override
//    public void onPlayerEndTurn() {
//        if (this.counter < 6 - 2) {
//            this.counter++;
//            AbstractDungeon.player.gameHandSize++;
//        }
//    }
//
//    @Override
//    public String getUpdatedDescription() {
//        return this.DESCRIPTIONS[0];
//    }
//
//    @Override
//    public AbstractRelic makeCopy() {
//        return new FlowerCard();
//    }
//}
