package merchantAlice.helper;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
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
        return AbstractDungeon.currMapNode != null &&
                AbstractDungeon.currMapNode.room != null &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
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

    private static final Matrix4 mx4 = new Matrix4();
    private static final Matrix4 rotatedTextMatrix = new Matrix4();
    public static void renderRotateTexture(SpriteBatch sb, Texture t, float x, float y, float offsetX, float offsetY, float scale, float angle){
        mx4.setToRotation(0.0F, 0.0F, 1.0F, angle);

        Vector2 vec = new Vector2(offsetX, offsetY);
        ModHelper.rotate(vec, angle);
        mx4.trn(x + vec.x,
                y + vec.y, 0.0F);
        sb.end();
        sb.setTransformMatrix(mx4);
        sb.begin();
        sb.draw(t, 0, 0, 0, 0, t.getWidth(), t.getHeight(), scale, scale, 0,
                0, 0, t.getWidth(), t.getHeight(), false, false);
        sb.end();
        sb.setTransformMatrix(rotatedTextMatrix);
        sb.begin();
    }

    //旋转
    public static void rotate(Vector2 vec, float radians) {
        float cos = (float) Math.cos((double) radians * 0.017453292F);
        float sin = (float) Math.sin((double) radians * 0.017453292F);
        float newX = vec.x * cos - vec.y * sin;
        float newY = vec.x * sin + vec.y * cos;
        vec.x = newX;
        vec.y = newY;
    }
}
