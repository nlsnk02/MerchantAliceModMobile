package merchantAlice.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.HashSet;

public class CardTagHelper {

    public static HashSet<AbstractCard> retianedCards = new HashSet<>();
    public static HashSet<AbstractCard> etherealedCards = new HashSet<>();
    public static HashSet<AbstractCard> exhaustCards = new HashSet<>();

    public static void render(SpriteBatch sb) {
        if (ModHelper.isInGame() && AbstractDungeon.player.hand != null && !AbstractDungeon.isScreenUp) {
            sb.setColor(Color.WHITE);
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                float x = -90.0F,
                        y = 180.0F;
                if (retianedCards.contains(c)) {
                    Texture t = ImageHelper.Retain;
                    ModHelper.renderRotateTexture(sb, t, c.current_x, c.current_y,
                            x * Settings.scale * c.drawScale, y * Settings.scale * c.drawScale,
                            c.drawScale, c.angle);
                    x += 90.0F;
                }
                if (etherealedCards.contains(c)) {
                    Texture t = ImageHelper.Ethereal;
                    ModHelper.renderRotateTexture(sb, t, c.current_x, c.current_y,
                            x * Settings.scale * c.drawScale, y * Settings.scale * c.drawScale,
                            c.drawScale, c.angle);
                    x += 90.0F;
                }
                if (exhaustCards.contains(c)) {
                    Texture t = ImageHelper.Exhaust;
                    ModHelper.renderRotateTexture(sb, t, c.current_x, c.current_y,
                            x * Settings.scale * c.drawScale, y * Settings.scale * c.drawScale,
                            c.drawScale, c.angle);
                }
            }
        }
    }

    public static void init() {
        retianedCards = new HashSet<>();
        etherealedCards = new HashSet<>();
        exhaustCards = new HashSet<>();
    }
}
