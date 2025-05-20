package merchantAlice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.android.mods.helpers.CardColorBundle;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import merchantAlice.actions.GainDesireAction;
import merchantAlice.cards.*;
import merchantAlice.characters.Char;
import merchantAlice.helper.CardTagHelper;
import merchantAlice.helper.ImageHelper;
import merchantAlice.helper.ModHelper;
import merchantAlice.misc.DesireUI;
import merchantAlice.modCore.Enums;
import merchantAlice.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.core.Settings.language;
import static merchantAlice.modCore.Enums.MerchantAliceColor;

public class MerchantAlice implements
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        OnCardUseSubscriber,
        EditKeywordsSubscriber,
        PostRenderSubscriber,
        StartGameSubscriber,
        PostBattleSubscriber {
    public static final String MOD_ID = "MerchantAlice";

    public static final Logger logger = LogManager.getLogger(MerchantAlice.class.getName());
    public static final String ATTACK = "MerchantAliceModResources/img/512/bg_attack_temp.png";
    public static final String SKILL = "MerchantAliceModResources/img/512/bg_skill_temp.png";
    public static final String POWER = "MerchantAliceModResources/img/512/bg_power_temp.png";

    public static final String ATTACK_PORTRAIT = "MerchantAliceModResources/img/1024/bg_attack_temp.png";
    public static final String SKILL_PORTRAIT = "MerchantAliceModResources/img/1024/bg_skill_temp.png";
    public static final String POWER_PORTRAIT = "MerchantAliceModResources/img/1024/bg_power_temp.png";

    public static final String ENERGY_ORB = "MerchantAliceModResources/img/512/CardOrb.png";
    public static final String ENERGY_ORB_PORTRAIT = "MerchantAliceModResources/img/1024/CardOrb.png";
    public static final String CARD_ENERGY_ORB = "MerchantAliceModResources/img/UI/EnergyOrb.png";

    private static final String BUTTON = "MerchantAliceModResources/img/charSelect/Button.png";
    private static final String PORTRAIT = "MerchantAliceModResources/img/charSelect/Portrait.png";
    public static final Color Purple = CardHelper.getColor(191, 64, 191);

    private ArrayList<CustomCard> cardsToAdd = new ArrayList<>();


    public MerchantAlice() {


        BaseMod.subscribe(this);
        CardColorBundle bundle = new CardColorBundle();
        bundle.cardColor = MerchantAliceColor;
        bundle.modId = MOD_ID;
        bundle.bgColor =
                bundle.cardBackColor =
                        bundle.frameColor =
                                bundle.frameOutlineColor =
                                        bundle.descBoxColor =
                                                bundle.trailVfxColor =
                                                        bundle.glowColor = Purple;
        bundle.libraryType = Enums.MerchantAlice;
        bundle.attackBg = ATTACK;
        bundle.skillBg = SKILL;
        bundle.powerBg = POWER;
        bundle.cardEnergyOrb = ENERGY_ORB;
        bundle.energyOrb = CARD_ENERGY_ORB;
        bundle.attackBgPortrait = ATTACK_PORTRAIT;
        bundle.skillBgPortrait = SKILL_PORTRAIT;
        bundle.powerBgPortrait = POWER_PORTRAIT;
        bundle.energyOrbPortrait = ENERGY_ORB_PORTRAIT;
        bundle.setEnergyPortraitWidth(164);
        bundle.setEnergyPortraitHeight(164);
        BaseMod.addColor(bundle);
    }

    public static void initialize() {
        new MerchantAlice();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Char("MerchantAlice"), BUTTON, PORTRAIT, Enums.MerchantAlice_CLASS);
    }

    @Override
    public void receiveEditCards() {
        logger.info("================加入卡牌=============");
        loadCardsToAdd();
        for (CustomCard c : cardsToAdd) {
            logger.info("Adding card : " + c.name);
            BaseMod.addCard(c);
        }
        logger.info("================加入卡牌=============");
    }

    private void loadCardsToAdd() {
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new StartStrike());
        this.cardsToAdd.add(new StartDefend());
        this.cardsToAdd.add(new RenewAndReplace());
        this.cardsToAdd.add(new MerchantsInstinct());
        this.cardsToAdd.add(new Raid());
        this.cardsToAdd.add(new Solemn());
        this.cardsToAdd.add(new Overambitious());
        this.cardsToAdd.add(new WinThroughCleverness());
        this.cardsToAdd.add(new CorneredBeast());
        this.cardsToAdd.add(new BadManners());
        this.cardsToAdd.add(new BurnOnesBoats());
        this.cardsToAdd.add(new Pursue());
        this.cardsToAdd.add(new Azure());
        this.cardsToAdd.add(new LiveFromHandToMouth());
        this.cardsToAdd.add(new SwiftShadow());
        this.cardsToAdd.add(new SeverelyPunish());
        this.cardsToAdd.add(new Unstoppable());
        this.cardsToAdd.add(new Abandon());
        this.cardsToAdd.add(new Annihilate());
        this.cardsToAdd.add(new DesireSurged());
        this.cardsToAdd.add(new ReviewAndLearn());
        this.cardsToAdd.add(new OneServesTwo());
        this.cardsToAdd.add(new HiddenCard());
        this.cardsToAdd.add(new CardExperiment());
        this.cardsToAdd.add(new Resourcefully());
        this.cardsToAdd.add(new CardDeckBuilding());
        this.cardsToAdd.add(new ChasingShadows());
        this.cardsToAdd.add(new PiercingThroughClouds());
        this.cardsToAdd.add(new CardResearch());
        this.cardsToAdd.add(new SelfCultivation());
        this.cardsToAdd.add(new Sketch());
        this.cardsToAdd.add(new DirectHit());
        this.cardsToAdd.add(new Responsibility());
        this.cardsToAdd.add(new DesireImpact());
        this.cardsToAdd.add(new DoubleStrength());
        this.cardsToAdd.add(new TheFinalBlow());
        this.cardsToAdd.add(new CardRealm());
        this.cardsToAdd.add(new AromaticBrew());
        this.cardsToAdd.add(new TheOtherShore());
        this.cardsToAdd.add(new ShowingWeakness());
        this.cardsToAdd.add(new OneByOne());
        this.cardsToAdd.add(new Exploit());
        this.cardsToAdd.add(new WitnessTheEnemy());
        this.cardsToAdd.add(new RecoverMaterials());
        this.cardsToAdd.add(new SuperficialGlance());
        this.cardsToAdd.add(new TheStormIsBrewing());
        this.cardsToAdd.add(new ShufflingDemonstration());
        this.cardsToAdd.add(new GlareWithFury());
        this.cardsToAdd.add(new HalftimeAdjustment());
        this.cardsToAdd.add(new AGamble());
        this.cardsToAdd.add(new HighStakesGamble());
        this.cardsToAdd.add(new SelfCultivation());
        this.cardsToAdd.add(new TreasureSmuggling());
        this.cardsToAdd.add(new BurstingWithJoy());
        this.cardsToAdd.add(new AFleetingRomance());
        this.cardsToAdd.add(new FlushedWithEmbarrassment());
        this.cardsToAdd.add(new AccelerationOfDesire());
        this.cardsToAdd.add(new ClenchTheTeeth());
        this.cardsToAdd.add(new ForcedClim());
//        this.cardsToAdd.add(new RelaxedAndJoyful());
        this.cardsToAdd.add(new RelaxedAndJoyful2());
//        this.cardsToAdd.add(new JoyOfUnion());
        this.cardsToAdd.add(new JoyOfUnion2());
        this.cardsToAdd.add(new BlissfulParadise());
        this.cardsToAdd.add(new DesireRising());
        this.cardsToAdd.add(new Tentacle());
        this.cardsToAdd.add(new DesireManifestation());
        this.cardsToAdd.add(new MockBattle());
        this.cardsToAdd.add(new ClearClouds());
        this.cardsToAdd.add(new SelfComfort());
    }


    @Override
    public void receiveEditKeywords() {
        logger.info("===============加载关键字===============");
        Gson gson = new Gson();
        String lang = "zh";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zh";
        }

        String json = AssetLoader.getString(MOD_ID,
                "MerchantAliceModResources/localization/keywords_" + lang + ".json");
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
        logger.info("===============加载关键字===============");
    }


    @Override
    public void receivePostInitialize() {
        logger.info("===============加载事件与其他东西===============");
        BaseMod.getColorBundleMap().get(MerchantAliceColor).loadRegion();
//        ImageHelper.init();
        logger.info("===============加载事件与其他东西===============");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("===============加载遗物===============");
        BaseMod.addRelicToCustomPool(new FlowerCard2(), MerchantAliceColor);
        BaseMod.addRelicToCustomPool(new ArtAssets(), MerchantAliceColor);
        BaseMod.addRelicToCustomPool(new ArtCollection(), MerchantAliceColor);
        BaseMod.addRelicToCustomPool(new BrandNewMarkerPen(), MerchantAliceColor);
        BaseMod.addRelicToCustomPool(new EvilPencil(), MerchantAliceColor);
        BaseMod.addRelicToCustomPool(new PrivatePhotos(), MerchantAliceColor);
        BaseMod.addRelicToCustomPool(new TinyToy(), MerchantAliceColor);
        logger.info("===============加载遗物===============");
    }

    @Override
    public void receiveEditStrings() {

        String relic = "", card = "", chr = "", power = "", potion = "", event = "", ui = "", tutorial = "";
        logger.info("===============加载文字信息===============");

        String lang;
        if (language == Settings.GameLanguage.ZHS || language == Settings.GameLanguage.ZHT) {
            lang = "zh";
        } else lang = "zh";

        //keywords在receiveEditKeywords里单独判断
        card = "MerchantAliceModResources/localization/cards_" + lang + ".json";
        chr = "MerchantAliceModResources/localization/characters_" + lang + ".json";
        relic = "MerchantAliceModResources/localization/relics_" + lang + ".json";
        power = "MerchantAliceModResources/localization/powers_" + lang + ".json";
        event = "MerchantAliceModResources/localization/event_" + lang + ".json";
        ui = "MerchantAliceModResources/localization/ui_" + lang + ".json";
        tutorial = "MerchantAliceModResources/localization/tutorial_" + lang + ".json";

        String cardStrings = AssetLoader.getString(MOD_ID, card);
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        String chrStrings = AssetLoader.getString(MOD_ID, chr);
        BaseMod.loadCustomStrings(CharacterStrings.class, chrStrings);

        String relicStrings = AssetLoader.getString(MOD_ID, relic);
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
//
        String powerStrings = AssetLoader.getString(MOD_ID, power);
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        String uiStrings = AssetLoader.getString(MOD_ID, ui);
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
//
        String tutorialStrings = AssetLoader.getString(MOD_ID, tutorial);
        BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        logger.info("===============加载文字信息===============");
    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
        if (AbstractDungeon.player instanceof Char) {
            if (DesireUI.inst.isHeat) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToBottom(new GainDesireAction(-3, false));
                }
            } else {
                if (c.type != AbstractCard.CardType.ATTACK)
                    AbstractDungeon.actionManager.addToBottom(new GainDesireAction(1, false));
            }

        }
    }

    @Override
    public void receivePostRender(SpriteBatch spriteBatch) {
        CardTagHelper.render(spriteBatch);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        CardTagHelper.init();
    }

    @Override
    public void receiveStartGame() {
        CardTagHelper.init();
    }
}

