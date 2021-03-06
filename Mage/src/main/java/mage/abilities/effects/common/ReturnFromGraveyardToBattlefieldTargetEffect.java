package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect {

    private boolean tapped;
    private boolean showUnderControlText = false;

    public ReturnFromGraveyardToBattlefieldTargetEffect() {
        this(false);
    }

    public ReturnFromGraveyardToBattlefieldTargetEffect(boolean tapped) {
        this(tapped, true);
    }

    public ReturnFromGraveyardToBattlefieldTargetEffect(boolean tapped, boolean showUnderControlText) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.showUnderControlText = showUnderControlText;
    }

    public ReturnFromGraveyardToBattlefieldTargetEffect(final ReturnFromGraveyardToBattlefieldTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.showUnderControlText = effect.showUnderControlText;
    }

    @Override
    public ReturnFromGraveyardToBattlefieldTargetEffect copy() {
        return new ReturnFromGraveyardToBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToMove = new HashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                    cardsToMove.add(card);
                }
            }
            controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, tapped, false, false, null);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();

        if (mode.getTargets().isEmpty()) {
            sb.append("return target creature to the battlefield");
        } else {
            Target target = mode.getTargets().get(0);
            sb.append("return ");
            if (target.getMaxNumberOfTargets() > 1) {
                if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
                    sb.append("up to ");
                }
                sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
            }
            sb.append("target ").append(mode.getTargets().get(0).getTargetName()).append(" to the battlefield");
            if (tapped) {
                sb.append(" tapped");
            }
        }
        if (showUnderControlText) {
            sb.append(" under your control");
        }
        return sb.toString();
    }
}