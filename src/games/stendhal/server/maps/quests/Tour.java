/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAndModifyKarmaAction;
//import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
//import games.stendhal.server.entity.npc.condition.AndCondition;
//import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
//import games.stendhal.server.entity.npc.condition.NotCondition;
//import games.stendhal.server.entity.npc.condition.OrCondition;
//import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
//import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
//import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
//import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
//import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStateStartsWithCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;


public class Tour extends AbstractQuest {
	private static final String QUEST_SLOT = "tour";


	private void offerQuestStep() {
		final SpeakerNPC npc = npcs.get("Truman");
		
		npc.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new GreetingMatchesNameCondition(npc.getName()),
				ConversationStates.ATTENDING,
				"Hello, say #quest if you would like to embark on a tour",
				null
				);
			
		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.QUEST_MESSAGES,
				null,
				ConversationStates.QUEST_OFFERED,
				"Tour?",
				null);


		npc.add(ConversationStates.QUEST_OFFERED,
				ConversationPhrases.YES_MESSAGES,
				null,
				ConversationStates.QUEST_STARTED,
				null,
				new MultipleActions(
						new SetQuestAndModifyKarmaAction(QUEST_SLOT, "start", 0),
						new ChatAction() {
							@Override
							public void fire(final Player player,
									final Sentence sentence,
									final EventRaiser npc) {
								StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_amazon_island_nw");
								player.teleport(zone, 32, 41, null, null);
								StendhalRPAction.placeat(zone, npc.getEntity(), 34, 41);
								player.setInvisible(true);
								npc.say("Welcome to the Amazon Islands, enter #end to end the tour");
								}
							}
						));

		npc.add(ConversationStates.QUEST_OFFERED,
				ConversationPhrases.NO_MESSAGES, null, ConversationStates.IDLE,
				"Bye",
				new SetQuestAndModifyKarmaAction(QUEST_SLOT, "rejected", 0));
		
		
		npc.add(ConversationStates.QUEST_STARTED,
				Arrays.asList("end"),
				null,
				ConversationStates.ATTENDING,
				"Bye",
				new MultipleActions(
						new SetQuestAndModifyKarmaAction(QUEST_SLOT, "end", 0),
						new ChatAction() {
							@Override
							public void fire(final Player player,
									final Sentence sentence,
									final EventRaiser npc) {
								StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_semos_city");
								player.teleport(zone, 33, 45, null, null);
								StendhalRPAction.placeat(zone, npc.getEntity(), 33, 44);
								player.setInvisible(false);
								SingletonRepository.getNPCList().get(npc.getName()).setDirection(Direction.DOWN);
								}
							}
						));
	}



	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Tour",
				"Take a tour of the Amazon Island",
				true);
		offerQuestStep();
	}


	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		res.add("You spoke to Truman.");
		final String questState = player.getQuest(QUEST_SLOT);
		if ("rejected".equals(questState)) {
			res.add("You declined his tour.");
		}
		if (player.isQuestInState(QUEST_SLOT, "start") || isCompleted(player)) {
			res.add("You went on the tour.");
		}
		return res;
	}

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public String getName() {
		return "Tour";
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public boolean isRepeatable(final Player player) {
		return true;
	}

	@Override
	public String getRegion() {
		return Region.SEMOS_CITY;
	}
	
	@Override
	public boolean isCompleted(final Player player) {
		return new QuestStateStartsWithCondition(QUEST_SLOT,"end").fire(player, null, null);
	}

	@Override
	public String getNPCName() {
		return "Truman";
	}
}