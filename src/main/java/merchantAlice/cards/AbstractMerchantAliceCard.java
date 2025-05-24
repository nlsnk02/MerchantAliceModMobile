package merchantAlice.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import merchantAlice.MerchantAlice;
import merchantAlice.helper.ModHelper;
import merchantAlice.modCore.Enums;
import merchantAlice.powers.ResourcefullyPower;

public abstract class AbstractMerchantAliceCard extends CustomCard {
    private static final TextureAtlas cardAtlas = new TextureAtlas(Gdx.files.internal("cards/cards.atlas"));

    public CardStrings cardStrings;
    public String DESCRIPTION;
    public String DESCRIPTION_UPG;
    public String[] EXTENDED_DESCRIPTION;

    public AbstractMerchantAliceCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET
    ) {
        this(NAME, COST, TYPE, RARITY, TARGET, Enums.MerchantAliceColor);
    }

    public AbstractMerchantAliceCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET,
            CardColor color
    ) {
        super("MerchantAlice:" + NAME, getCardStrings(NAME).NAME, getPicPath(NAME, TYPE),
                COST, getCardStrings(NAME).DESCRIPTION, TYPE, color, RARITY, TARGET);
        cardStrings = getCardStrings(NAME);
        DESCRIPTION = cardStrings.DESCRIPTION;
        DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


    public static String getPicPath(String name, CardType type) {
        if(ModHelper.isFileExist(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/cards/" + name + ".png"))
            return "MerchantAliceModResources/img/cards/" + name + ".png";

//        if (Gdx.files.internal("MerchantAliceModResources/img/cards/" + name + ".png").exists())
//            return "MerchantAliceModResources/img/cards/" + name + ".png";

        if (type == CardType.ATTACK)
            return "MerchantAliceModResources/img/cards/attack.png";
        else if (type == CardType.SKILL)
            return "MerchantAliceModResources/img/cards/skill.png";
        else if (type == CardType.POWER)
            return "MerchantAliceModResources/img/cards/power.png";
        else
            return "MerchantAliceModResources/img/cards/temp.png";
    }

    public static CardStrings getCardStrings(String name) {
        return CardCrawlGame.languagePack.
                getCardStrings("MerchantAlice:" + name);
    }

    //我自己用的一个功能，在暂缺卡图的时候可以使用原版卡牌的卡图
    public void steal(AbstractCard c) {
        String img = c.assetUrl;
        this.portrait = AbstractMerchantAliceCard.cardAtlas.findRegion(img);
        this.assetUrl = img;
    }

    @Override
    public void triggerOnManualDiscard() {
        int times = 1;
        if (AbstractDungeon.player.hasPower(ResourcefullyPower.POWER_ID))
            times += AbstractDungeon.player.getPower(ResourcefullyPower.POWER_ID).amount;
        for (int i = 0; i < times; i++) {
            triggerOnManualDiscardOwn();
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c != this && c instanceof AbstractMerchantAliceCard) {
                ((AbstractMerchantAliceCard) c).triggerOnOtherManualDiscard(this);
            }
        }
    }

    public void triggerOnManualDiscardOwn() {

    }

    public void triggerOnOtherManualDiscard(AbstractCard c) {

    }

    @Override
    public abstract void use(AbstractPlayer p, AbstractMonster m);

    @Override
    public abstract void upgrade();
}

