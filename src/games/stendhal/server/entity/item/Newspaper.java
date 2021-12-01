/***************************************************************************
 *                     Copyright © 2020 - Arianne                          *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.common.NotificationType;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.BestiaryEvent;
import games.stendhal.server.events.NewspaperEvent;


/**
 * Item class that shows some basic information about enemies around Faiumoni.
 */
public class Newspaper extends OwnedItem {

	public Newspaper(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		setMenu("Read|Use");
	}

	public Newspaper(final Newspaper item) {
		super(item);
	}


	@Override
	public boolean onUsed(final RPEntity user) {
		if (!(user instanceof Player)) {
			return false;
		}

		final Player player = (Player) user;
		if (!super.onUsed(player)) {
			player.sendPrivateText(NotificationType.RESPONSE, "You read: This newspaper is not yours " + getOwner() + ". Please return it to its rightful owner , if you want to read, just buy one from newspaper man. ");
			return false;
		}

		player.notifyWorldAboutChanges();
		return true;
	}

	@Override
	public void setOwner(final String name) {
		put("infostring", name);
	}

	@Override
	public String getOwner() {
		return get("infostring");
	}
}
