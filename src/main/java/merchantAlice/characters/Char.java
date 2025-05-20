package merchantAlice.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import merchantAlice.helper.GIFAnimationHelper;
import merchantAlice.helper.ModHelper;
import merchantAlice.misc.DesireUI;
import merchantAlice.MerchantAlice;
import merchantAlice.modCore.Enums;
import merchantAlice.relics.FlowerCard2;

import java.util.ArrayList;


public class Char extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("MerchantAlice:MyCharacter");
    private static final String SHOULDER_2 = "MerchantAliceModResources/img/char/shoulder2.png";
    private static final String SHOULDER_1 = "MerchantAliceModResources/img/char/shoulder1.png";
    private static final String CORPSE = "MerchantAliceModResources/img/char/null.png";

    private static final String ORB_VFX = "MerchantAliceModResources/img/UI/orb/vfx.png";

    private static final String[] ORB_TEXTURES = new String[]{
            "MerchantAliceModResources/img/UI/orb/layer5.png",
            "MerchantAliceModResources/img/UI/orb/layer4.png",
            "MerchantAliceModResources/img/UI/orb/layer3.png",
            "MerchantAliceModResources/img/UI/orb/layer2.png",
            "MerchantAliceModResources/img/UI/orb/layer1.png",
            "MerchantAliceModResources/img/UI/orb/layer6.png",
            "MerchantAliceModResources/img/UI/orb/layer5d.png",
            "MerchantAliceModResources/img/UI/orb/layer4d.png",
            "MerchantAliceModResources/img/UI/orb/layer3d.png",
            "MerchantAliceModResources/img/UI/orb/layer2d.png",
            "MerchantAliceModResources/img/UI/orb/layer1d.png"
    };

    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};


    private static final int STARTING_HP = 60;
    private static final int MAX_HP = 60;
    private static final int STARTING_GOLD = 150;
    private static final int DRAW_SIZE = 5;
    private static final int ASCENSION_MAX_HP_LOSS = 6;

    public static final Color My_COLOR = CardHelper.getColor(0, 0, 191);

    public Char(String name) {
//        super(MerchantAlice.MOD_ID, name, Enums.MerchantAlice_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);
        super(MerchantAlice.MOD_ID, name, Enums.MerchantAlice_CLASS, ORB_TEXTURES, null, null, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;

        myInitializeClass("MerchantAliceModResources/img/char/null.png", SHOULDER_2, SHOULDER_1, CORPSE,
                getLoadout(),
                0F, 5.0F, 240.0F, 300.0F,
                new EnergyManager(ENERGY_PER_TURN));
    }

    protected void myInitializeClass(String imgUrl, String shoulder2ImgUrl, String shouldImgUrl, String corpseImgUrl, CharSelectInfo info, float hb_x, float hb_y, float hb_w, float hb_h, EnergyManager energy) {
        if (imgUrl != null) {
            this.img = AssetLoader.getTexture(MerchantAlice.MOD_ID,imgUrl);
        }

        if (this.img != null) {
            this.atlas = null;
        }

        this.shoulderImg = AssetLoader.getTexture(MerchantAlice.MOD_ID,shouldImgUrl);
        this.shoulder2Img = AssetLoader.getTexture(MerchantAlice.MOD_ID,shoulder2ImgUrl);
        this.corpseImg = AssetLoader.getTexture(MerchantAlice.MOD_ID,corpseImgUrl);


        if (Settings.isMobile) {
            hb_w *= 1.17F;
        }

        this.maxHealth = info.maxHp;
        this.startingMaxHP = this.maxHealth;
        this.currentHealth = info.currentHp;
        this.masterMaxOrbs = info.maxOrbs;
        this.energy = energy;
        this.masterHandSize = info.cardDraw;
        this.gameHandSize = this.masterHandSize;
        this.gold = info.gold;
        this.displayGold = this.gold;
        this.hb_h = hb_h * Settings.xScale;
        this.hb_w = hb_w * Settings.scale;
        this.hb_x = hb_x * Settings.scale;
        this.hb_y = hb_y * Settings.scale;
        this.hb = new Hitbox(this.hb_w, this.hb_h);
        this.healthHb = new Hitbox(this.hb.width, 72.0F * Settings.scale);
        this.refreshHitboxLocation();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add("MerchantAlice:StartStrike");
        retVal.add("MerchantAlice:StartStrike");
        retVal.add("MerchantAlice:StartStrike");
        retVal.add("MerchantAlice:StartStrike");

        retVal.add("MerchantAlice:StartDefend");
        retVal.add("MerchantAlice:StartDefend");
        retVal.add("MerchantAlice:StartDefend");
        retVal.add("MerchantAlice:StartDefend");

        retVal.add("MerchantAlice:MerchantsInstinct");
        retVal.add("MerchantAlice:RenewAndReplace");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(FlowerCard2.ID);
        UnlockTracker.markRelicAsSeen(FlowerCard2.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        String title = characterStrings.NAMES[0];
        String flavor = characterStrings.TEXT[0];

        return new CharSelectInfo(
                title, // 人物名字
                flavor, // 人物介绍
                STARTING_HP, // 当前血量
                MAX_HP, // 最大血量
                0, // 初始充能球栏位
                STARTING_GOLD, // 初始携带金币
                DRAW_SIZE, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return Enums.MerchantAliceColor;
    }

    @Override
    public Color getCardRenderColor() {
        return My_COLOR;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new IronWave();
    }

    @Override
    public Color getCardTrailColor() {
        return My_COLOR;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //这里时选择人物时的特效
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "选择人物时的音效";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Char(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return My_COLOR;
    }


    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

//    @Override
//    public ArrayList<CutscenePanel> getCutscenePanels() {
//        ArrayList<CutscenePanel> panels = new ArrayList<>();
//        // 有两个参数的，第二个参数表示出现图片时播放的音效
//        panels.add(new CutscenePanel("MerchantAliceModResources/img/event/epcg/cut1.png"));
//        panels.add(new CutscenePanel("MerchantAliceModResources/img/event/epcg/cut2.png"));
//        panels.add(new CutscenePanel("MerchantAliceModResources/img/event/epcg/cut3.png"));
//
//        return panels;
//    }

    @Override
    public void damage(DamageInfo info) {
        ModHelper.BodyState formalState = ModHelper.getSPStateNormally();
        super.damage(info);
        if (formalState == ModHelper.BodyState.Normally && ModHelper.getSPStateNormally() == ModHelper.BodyState.Injury)
            GIFAnimationHelper.baoyi();
        if (formalState == ModHelper.BodyState.Normally && ModHelper.getSPStateNormally() == ModHelper.BodyState.Embarrassment)
            GIFAnimationHelper.baoyi2();
        if (formalState == ModHelper.BodyState.Injury && ModHelper.getSPStateNormally() == ModHelper.BodyState.Embarrassment)
            GIFAnimationHelper.baoyi2();
    }

    @Override
    public void update() {
        super.update();
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            DesireUI.inst.update();

        if (this.isDead) ModHelper.SPState = ModHelper.BodyState.Die;
        GIFAnimationHelper.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
            if (this.isDead) update();
            GIFAnimationHelper.render(sb, this.drawX, this.animX, this.drawY, this.flipHorizontal, this.flipVertical);
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            DesireUI.inst.render(sb);
        super.renderOrb(sb, enabled, current_x, current_y);
    }

    @Override
    public void applyStartOfTurnPostDrawRelics() {
        super.applyStartOfTurnPostDrawRelics();
    }

    @Override
    public void applyPreCombatLogic() {
        super.applyPreCombatLogic();
        DesireUI.inst.init();
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
    }
}

