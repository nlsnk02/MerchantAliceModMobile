package merchantAlice.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;

import java.util.ArrayList;
import java.util.Collections;

public class GIFAnimationHelper {
    private static Texture aniImg;
    private static Texture aniTopPanel;
    public static ArrayList<GIFAnimationItem> GIFImgs = new ArrayList<>();
    public static ArrayList<GIFAnimationItem> GIFTopPanels = new ArrayList<>();

    public static void clearGif() {
        GIFImgs.clear();
        GIFTopPanels.clear();
        aniTopPanel = null;
        idle(ModHelper.SPState);
        idle(ModHelper.SPState);
    }

    public static void baoyi() {
        if (ModHelper.SPState != ModHelper.BodyState.Tentacle && ModHelper.SPState != ModHelper.BodyState.Self_Comfort) {
            ModHelper.SPState = ModHelper.BodyState.Injury;

            GIFAnimationItem BaoyiTopPanel = new GIFAnimationItem(ImageHelper.Baoyi, 1 / 8F, 1);
            addTopLever(BaoyiTopPanel);
        }
    }

    public static void baoyi2() {
        if (ModHelper.SPState != ModHelper.BodyState.Tentacle && ModHelper.SPState != ModHelper.BodyState.Self_Comfort) {
            ModHelper.SPState = ModHelper.BodyState.Embarrassment;

            GIFAnimationItem TopPanel = new GIFAnimationItem(ImageHelper.Hit, 1 / 24F, 1);
            addTopLever(TopPanel, -1000);

            AbstractDungeon.player.useFastShakeAnimation(0.7F);
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.LONG, true);
        }
    }

    public static void tentacle() {
        ModHelper.SPState = ModHelper.BodyState.Tentacle;
        GIFAnimationItem TopPanel = new GIFAnimationItem(ImageHelper.Wrap, 1 / 16F, 1);
        addTopLever(TopPanel, -1000);
    }

    public static void selfComfort() {
        ModHelper.SPState = ModHelper.BodyState.Self_Comfort;
        GIFAnimationItem TopPanel = new GIFAnimationItem(ImageHelper.Hit, 1 / 24F, 1);
        addTopLever(TopPanel, -1000);

        AbstractDungeon.player.useFastShakeAnimation(0.7F);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.LONG, true);
    }


    //小工具
    private static void addTopLever(GIFAnimationItem item, float delayTime) {
        delayTime += GIFImgs.get(0).timeLeft;
        for (GIFAnimationItem i : GIFTopPanels) {
            delayTime -= i.timeLeft;
        }
        if (delayTime > 0) {
            ArrayList<Texture> tmp = new ArrayList<>();
            tmp.add(ImageHelper.NullTopLever);
            GIFTopPanels.add(new GIFAnimationItem(tmp, delayTime, 1));
        }
        GIFTopPanels.add(item);
    }

    private static void addTopLever(GIFAnimationItem item) {
        addTopLever(item, 0);
    }

    private static void addItem(GIFAnimationItem item) {
        GIFImgs.retainAll(Collections.singleton(GIFImgs.get(0)));
        GIFImgs.add(item);
    }

    private static void idle(ModHelper.BodyState state) {
        if (state == ModHelper.BodyState.Normally)
            GIFImgs.add(new GIFAnimationItem(ImageHelper.SP, 1 / 4F, 1));
        else if (state == ModHelper.BodyState.Injury)
            GIFImgs.add(new GIFAnimationItem(ImageHelper.SPNudity, 1 / 4F, 1));
        else if (state == ModHelper.BodyState.Tentacle)
            GIFImgs.add(new GIFAnimationItem(ImageHelper.SPTentacle, 1 / 4F, 1));
        else if (state == ModHelper.BodyState.Embarrassment)
            GIFImgs.add(new GIFAnimationItem(ImageHelper.SPEmbarrassment, 1 / 4F, 1));
        else if (state == ModHelper.BodyState.Self_Comfort)
            GIFImgs.add(new GIFAnimationItem(ImageHelper.SPSelfComfort, 1 / 4F, 1));
        else GIFImgs.add(new GIFAnimationItem(ImageHelper.SPDie, 1 / 4F, 1));
    }


    //核心
    public static void update() {
        //立绘
        if (GIFImgs.size() < 2) {
            idle(ModHelper.SPState);
        }
        if (GIFImgs.get(0).isDone) {
            GIFImgs.remove(0);
        }
        aniImg = GIFImgs.get(0).update();

        //动画
        if (!GIFTopPanels.isEmpty()) {
            if (GIFTopPanels.get(0).isDone) {
                GIFTopPanels.remove(0);
            }
        }

        if (!GIFTopPanels.isEmpty()) {
            aniTopPanel = GIFTopPanels.get(0).update();
        } else aniTopPanel = null;
    }

    public static void render(SpriteBatch sb, float drawX, float animX, float drawY, boolean flipHorizontal, boolean flipVertical) {
        if (aniImg != null) {
            sb.setColor(Color.WHITE);
            sb.draw(aniImg,
                    drawX - (float) aniImg.getWidth() * Settings.scale / 2.0F + animX, drawY,
                    (float) aniImg.getWidth() * Settings.scale,
                    (float) aniImg.getHeight() * Settings.scale,
                    0, 0,
                    aniImg.getWidth(), aniImg.getHeight(),
                    flipHorizontal, flipVertical);
        }
        if (aniTopPanel != null) {
            sb.setColor(Color.WHITE);
            sb.draw(aniTopPanel,
                    drawX - (float) aniTopPanel.getWidth() * Settings.scale / 2.0F + animX, drawY,
                    (float) aniTopPanel.getWidth() * Settings.scale,
                    (float) aniTopPanel.getHeight() * Settings.scale,
                    0, 0,
                    aniTopPanel.getWidth(), aniTopPanel.getHeight(),
                    flipHorizontal, flipVertical);
        }
    }


    public static class GIFAnimationItem {
        private final float frameCount;
        private final ArrayList<Texture> frames;
        private final float frameAbroadTime;
        private int loopCount;
        private boolean isDone;
        private float currentTimer;
        private int currentFrame;
        public float timeLeft;


        public GIFAnimationItem(ArrayList<Texture> frames, float frameAbroadTime, int loopCount) {
            this.frames = frames;
            this.frameCount = frames.size();
            this.frameAbroadTime = frameAbroadTime;
            this.loopCount = loopCount;
            this.isDone = false;
            this.currentTimer = 0;
            this.currentFrame = 0;
            this.timeLeft = frameAbroadTime * frameCount;
        }

        public Texture update() {
            currentTimer += Gdx.graphics.getDeltaTime();

            timeLeft = frameAbroadTime * (frameCount - currentFrame);

            if (currentTimer > frameAbroadTime) {
                if (currentFrame + 1 == frameCount) {//最后一帧播放完毕
                    if (this.loopCount > 0) {
                        this.loopCount -= 1;
                    }
                    if (this.loopCount == 0) {
                        this.isDone = true;
                        return frames.get(currentFrame);
                    }
                }

                int temp = (int) (currentTimer / frameAbroadTime);
                currentTimer -= temp * frameAbroadTime;
                currentFrame = (currentFrame + temp) % (int) frameCount;
            }
            return frames.get(currentFrame);
        }
    }
}
