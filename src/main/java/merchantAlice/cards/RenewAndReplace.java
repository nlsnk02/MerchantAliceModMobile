package merchantAlice.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import merchantAlice.helper.CardTagHelper;

public class RenewAndReplace extends AbstractMerchantAliceCard {
    public RenewAndReplace() {
        super(RenewAndReplace.class.getSimpleName(), 0, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            private boolean isFirst = true;

            @Override
            public void update() {
                if (this.isFirst) {
                    this.isFirst = false;
                    this.duration = Settings.ACTION_DUR_FAST;
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard c : p.discardPile.group) {
                        temp.addToBottom(c);
                    }

                    if (temp.isEmpty()) {
                        this.isDone = true;
                        return;
                    }

                    AbstractDungeon.gridSelectScreen.open(temp, 1, false, cardStrings.EXTENDED_DESCRIPTION[0]);
                }

                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        AbstractCard c1 = c.makeStatEquivalentCopy();
                        AbstractCard c2 = c.makeStatEquivalentCopy();
//                        CardModifierManager.addModifier(c1, new ExhaustMod());
//                        CardModifierManager.addModifier(c2, new ExhaustMod());
                        CardTagHelper.exhaustCards.add(c1);
                        c1.exhaust = true;
                        CardTagHelper.exhaustCards.add(c2);
                        c2.exhaust = true;

                        if (AbstractDungeon.player.hand.size() == 10) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c1, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c2, (float) Settings.WIDTH / 2.0F + 25.0F * Settings.scale + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                        } else if (AbstractDungeon.player.hand.size() == 9) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c1, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c2));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c1));
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c2));
                        }
                    }

                    AbstractDungeon.gridSelectScreen.selectedCards.clear();

                    this.isDone = true;

                }
                tickDuration();
            }
        });
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new RenewAndReplace();
    }
}
