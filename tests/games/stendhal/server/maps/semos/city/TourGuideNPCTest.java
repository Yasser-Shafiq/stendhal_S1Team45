/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.semos.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.quests.AbstractQuest;
import games.stendhal.server.maps.quests.Tour;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;


public class TourGuideNPCTest {

    private SpeakerNPC npc;
	private Player player;
	private Engine en;
	private StendhalRPZone destination;
	private StendhalRPZone srpz;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
		StendhalRPZone zone = new StendhalRPZone("admin_test");
		new TourGuideNPC().configureZone(zone, null);
		new DatabaseFactory().initializeDatabase();
	}

	@Before
	public void setUp() {
		final StendhalRPWorld world = SingletonRepository.getRPWorld();
		final AbstractQuest quest = new Tour();
		quest.addToWorld();
		srpz = new StendhalRPZone("0_semos_city", 100, 100);
		destination = new StendhalRPZone("0_amazon_island_nw", 100, 100);
		world.addRPZone(srpz);
		world.addRPZone(destination);
		npc = SingletonRepository.getNPCList().get("Truman");
		en = npc.getEngine();
		srpz.add(npc);
		npc.setPosition(15, 10);
		player = PlayerTestHelper.createPlayer("bob");
		player.teleport(srpz, 10, 10, null, null);
	}
	
	@After
	public void tearDown() {
		SingletonRepository.getNPCList().clear();
	}

	@Test
	public void createDialogTest() {
		assertEquals(player.getZone().getName(), srpz.getName());
		assertEquals(npc.getZone().getName(), srpz.getName());
		assertTrue(en.step(player, "hi"));
		assertEquals("Hello, say #quest if you would like to embark on a tour", getReply(npc));
		assertTrue(en.step(player, "quest"));
		assertEquals("Tour?", getReply(npc));
		assertTrue(en.step(player, "yes"));
		assertEquals(player.getZone().getName(), destination.getName());
		assertEquals(npc.getZone().getName(), destination.getName());
		assertTrue(player.isInvisibleToCreatures());
		assertTrue(en.step(player, "end"));
		assertEquals(player.getZone().getName(), srpz.getName());
		assertEquals(npc.getZone().getName(), srpz.getName());
	}
}
