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

import java.util.*;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.engine.PlayerList;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.PrivateTextEvent;

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
			player.sendPrivateText(NotificationType.RESPONSE, "You read: This newspaper is the property of " + getOwner() + ". Please return it to its rightful owner.");
			return false;
		}

		final List<Creature> standardEnemies = new ArrayList<>();
		final List<Creature> rareEnemies = new ArrayList<>();
		final List<Creature> abnormalEnemies = new ArrayList<>();
		final EntityManager em = SingletonRepository.getEntityManager();
		PlayerList list = StendhalRPRuleProcessor.get().getOnlinePlayers();


		player.addEvent(new PrivateTextEvent(NotificationType.CLIENT, String.valueOf(list.getAllPlayers())));

		// level
		int HighestLevel = 0;
		String HighestLevelPlayer = "";

		// most interesting daily news (simple)

		Player NewsPlayer = null;
		Creature creature = null;




		for (Player p : list.getAllPlayers()) {

			if(p.getLevel()>=HighestLevel)
			{
				HighestLevel = p.getLevel();
				HighestLevelPlayer = p.getName();
			}


			for (final Creature e : em.getCreatures()) {
				if(creature == null) {
					NewsPlayer = p;
					creature = e;
				}
				if (!p.hasKilledSolo(e.getName()))
					continue;
				if (e.isRare()) {
					rareEnemies.add(e);
				} else if (e.isAbnormal()) {
					abnormalEnemies.add(e);
				} else {
					standardEnemies.add(e);
					{
						if(e.getLevel() >= creature.getLevel())
						{
							NewsPlayer = p;
							creature = e ;
						}

					}
				}
			}
		}

		player.addEvent(new PrivateTextEvent(NotificationType.WARNING, "Daily News"+ "\n"));
		player.addEvent(new PrivateTextEvent(NotificationType.SUPPORT, "the highest level of player in the world is "+
				HighestLevelPlayer +"! his level is " +  HighestLevel +  "！\n"));
		if (NewsPlayer != null)
			player.addEvent(new PrivateTextEvent(NotificationType.EMOTE, "A powerful creature was defeated by"
					+NewsPlayer.getName() + "! " + NewsPlayer.describe().substring(8 + NewsPlayer.getName().length())
					+"\n" + "The monster he killed is : " + creature.getName() + "\n"+
					"the moster information : " +
					creature.getDescription() + "\n Although the monster is so powerful, " +
					NewsPlayer.getName() + "'s ATK : " + NewsPlayer.getAtk() + "  his DEF: " + NewsPlayer.getDef() +
					"  After a hard battle , He kill the " +creature.getName() + " alone! "
			));


		   // player.addEvent(new PrivateTextEvent(NotificationType.INFORMATION, e.getName() + "\n"));

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

