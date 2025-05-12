package merchantAlice.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import merchantAlice.helper.ModHelper;

import java.util.ArrayList;

public class DrawFromDiscardpileAction extends AbstractGameAction {

    public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();

    private int amount;
    private AbstractGameAction action;

    public DrawFromDiscardpileAction(int amount, AbstractGameAction action) {
        this.duration = 0.3F;
        this.amount = amount;
        this.action = action;
    }

    public DrawFromDiscardpileAction(int amount) {
        this(amount, null);
    }

    private int getMin(int a, int b, int c) {
        if (a > b) a = b;
        if (a > c) a = c;
        return a;
    }

    @Override
    public void update() {
        if (this.duration == 0.3F) {
            drawnCards.clear();
            int formal = this.amount;
            this.amount = getMin(this.amount, 10 - AbstractDungeon.player.hand.size(), AbstractDungeon.player.drawPile.size());
            if (this.amount > 0) {
                drawnCards = ModHelper.getRandomElements(AbstractDungeon.player.discardPile.group, this.amount);
                for (AbstractCard c : drawnCards) {
                    AbstractDungeon.player.discardPile.group.remove(c);
                    AbstractDungeon.player.drawPile.group.add(c);
                }
                if (this.action != null) addToTop(action);
                if (formal > this.amount) addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractDungeon.player.createHandIsFullDialog();
                        this.isDone = true;
                    }
                });
                addToTop(new DrawCardAction(this.amount));
            }
        }
        tickDuration();
    }
}
