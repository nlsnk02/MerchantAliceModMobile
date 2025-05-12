package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StartDefend extends AbstractMerchantAliceCard {
    public StartDefend() {
        super(StartDefend.class.getSimpleName(), 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = this.block = 0;
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void applyPowers() {
        this.baseBlock = AbstractDungeon.player.hand.group.size();
        if (this.upgraded) this.baseBlock += this.baseMagicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(this.baseMagicNumber);
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new StartDefend();
    }
}
