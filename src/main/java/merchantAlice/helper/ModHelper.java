package merchantAlice.helper;

import com.badlogic.gdx.files.FileHandle;
import com.megacrit.cardcrawl.android.mods.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;

public class ModHelper {
    public static final Logger logger = LogManager.getLogger(ModHelper.class.getName());

    public static boolean isInGame() {
        return (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                AbstractDungeon.player != null;
    }


    public static String makeID(String s) {
        return "MerchantAlice:" + s;
    }

    public static String makeRelicAd(String name, boolean isPortrait) {
        String isP = "32";
        if (isPortrait) isP = "84";

        return "MerchantAliceModResources/img/powers/" + name + isP + ".png";
    }

    public static int CardsHaveDrawnThisTurn;


    //衣服状态
    public static BodyState SPState = BodyState.Normally;

    public static void initSPState() {
        if ((double) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth < 0.7) {
            if ((double) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth < 0.5)
                SPState = BodyState.Embarrassment;
            else SPState = BodyState.Injury;
        } else
            SPState = BodyState.Normally;
    }

    public static BodyState getSPStateNormally() {
        if ((double) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth < 0.7) {
            if ((double) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth < 0.5)
                return BodyState.Embarrassment;
            return BodyState.Injury;
        }
        return BodyState.Normally;
    }

    public enum BodyState {
        Normally, Injury, Embarrassment, Tentacle, Self_Comfort, Die
    }

    public static ArrayList<AbstractCard> getRandomElements(ArrayList<AbstractCard> list, int n) {
        ArrayList<AbstractCard> result = new ArrayList<>();
        ArrayList<AbstractCard> tempList = new ArrayList<>(list);

        n = Math.min(n, list.size());

        for (int i = 0; i < n; i++) {
            int randomIndex = AbstractDungeon.cardRandomRng.random(tempList.size() - 1);
            result.add(tempList.get(randomIndex));
            tempList.remove(randomIndex);
        }

        return result;
    }

    public static boolean isFileExist(String modId, String internalPath) {
        int i;
        for (i = 0; i < Loader.MODINFOS.length && !Loader.MODINFOS[i].modId.equals(modId); ++i) {
        }
        if (i >= Loader.MODINFOS.length) {
            return false;
        } else {
            ZipEntry entry = Loader.MOD_JARS[i].getEntry(internalPath);
            return entry != null;
        }
    }

    public static void deckMoveToHand(AbstractCard c) {
        if (!AbstractDungeon.player.drawPile.contains(c)) return;

        if (AbstractDungeon.player.hand.size() >= 10) {
            AbstractDungeon.player.drawPile.moveToDiscardPile(c);
            AbstractDungeon.player.createHandIsFullDialog();
        } else {
            c.unhover();
            c.lighten(true);
            c.setAngle(0.0F);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;
            c.current_x = CardGroup.DRAW_PILE_X;
            c.current_y = CardGroup.DRAW_PILE_Y;
            c.flash();
            AbstractDungeon.player.drawPile.removeCard(c);
            AbstractDungeon.player.hand.addToTop(c);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
        }
    }
}
