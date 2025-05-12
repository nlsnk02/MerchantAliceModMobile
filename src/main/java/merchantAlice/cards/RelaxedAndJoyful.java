//package merchantAlice.cards;
//
//import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import merchantAlice.powers.RelaxedAndJoyfulPower;
//import merchantAlice.powers.WinThroughClevernessPower;
//
//public class RelaxedAndJoyful extends AbstractMerchantAliceCard {
//    public RelaxedAndJoyful() {
//        super(RelaxedAndJoyful.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
//        this.magicNumber = this.baseMagicNumber = 1;
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new ApplyPowerAction(p, p, new RelaxedAndJoyfulPower(p, this.magicNumber)));
//    }
//
//
//    @Override
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            upgradeBaseCost(0);
//        }
//    }
//
//    @Override
//    public AbstractCard makeCopy() {
//        return new RelaxedAndJoyful();
//    }
//}
