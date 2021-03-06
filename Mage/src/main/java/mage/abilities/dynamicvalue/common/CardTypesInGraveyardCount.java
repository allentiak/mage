package mage.abilities.dynamicvalue.common;

import java.util.ArrayList;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author JayDi85
 */
public enum CardTypesInGraveyardCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            ArrayList<CardType> foundCardTypes = new ArrayList<>();
            for (Card card : controller.getGraveyard().getCards(game)) {
                foundCardTypes.addAll(card.getCardType());
            }
            return foundCardTypes.size();
        }
        return 0;
    }

    @Override
    public CardTypesInGraveyardCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of opponents you attacked this turn";
    }
}
