package games.stendhal.server.entity.creature;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.server.game.db.DatabaseFactory;
import utilities.RPClass.CreatureTestHelper;

public class CentaurSupTest {
	@Before
	public void setUpBcl(){
		
		MockStendlRPWorld.get();
		CreatureTestHelper.generateRPClasses();
		Log4J.init();
		new DatabaseFactory().initializeDatabase();
		
	}
	@After 
	public void tearDownAcl() {
		MockStendlRPWorld.reset();
		
	}
	@Test
	public void SolarCentaurSusceptibilityTest(){
		Creature one = SingletonRepository.getEntityManager().getCreature("solar centaur");
		assertTrue(one.getSusceptibility(Nature.ICE) > 1);
		assertTrue(one.getSusceptibility(Nature.FIRE) < 1);
	}
	@Test
	public void GlacierCentaurSusceptibilityTest() {
		Creature two = SingletonRepository.getEntityManager().getCreature("glacier centaur");
		assertTrue(two.getSusceptibility(Nature.ICE) < 1);
		assertTrue(two.getSusceptibility(Nature.FIRE) > 1);
	}
	
	
	
	

}
