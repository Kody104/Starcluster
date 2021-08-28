package com.gmail.jpk.stu.utils;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.items.Equipment;
import com.gmail.jpk.stu.items.EquipmentType;
import com.gmail.jpk.stu.spaceships.SpaceshipBlueprints;

public class GlobalUtility {
	
	private static int SCREEN_HEIGHT;
	private static int SCREEN_WIDTH;
	
	private static Array<Equipment> allHulls;
	private static Array<Equipment> allThrusters;
	private static Array<Equipment> allShields;
	private static Array<Equipment> allFuelTanks;
	private static Array<SpaceshipBlueprints> allShipBlueprints;
	
	public static void init() {
		allHulls = new Array<Equipment>();
		allThrusters = new Array<Equipment>();
		allShields = new Array<Equipment>();
		allFuelTanks = new Array<Equipment>();
		allShipBlueprints = new Array<SpaceshipBlueprints>();
		
		/* Create all stock equipment */
		Equipment terranHullMk1 = new Equipment(EquipmentType.HULL, "Terran Hull Mk1");
		terranHullMk1.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4, 100.0f, 0.0f, 0.0f);
		allHulls.add(terranHullMk1);
		Equipment terranHullMk2 = new Equipment(EquipmentType.HULL, "Terran Hull Mk2");
		terranHullMk2.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 8, 350.0f, 0.0f, 0.0f);
		allHulls.add(terranHullMk2);
		Equipment terranHullMk3 = new Equipment(EquipmentType.HULL, "Terran Hull Mk3");
		terranHullMk3.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 12, 600.0f, 0.0f, 0.0f);
		allHulls.add(terranHullMk3);
		Equipment terranHullMk4 = new Equipment(EquipmentType.HULL, "Terran Hull Mk4");
		terranHullMk4.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 16, 1000.0f, 0.0f, 0.0f);
		allHulls.add(terranHullMk4);
		Equipment terranAgileThruster = new Equipment(EquipmentType.THRUSTER, "Terran TRB Mk1");
		terranAgileThruster.set(11.0f, 40.0f, 0.15f, 0.15f, 0.0f, 0, 0.0f, 0.0f, 0.8f);
		allThrusters.add(terranAgileThruster);
		Equipment terranAgedThruster = new Equipment(EquipmentType.THRUSTER, "Terran TRB Mk2");
		terranAgedThruster.set(10.0f, 15.0f, 0.0125f, 0.15f, 0.0f, 0, 0.0f, 0.0f, 0.6f);
		allThrusters.add(terranAgedThruster);
		Equipment terranBoosterThruster = new Equipment(EquipmentType.THRUSTER, "Terran TRB Mk3");
		terranBoosterThruster.set(20.0f, 35.0f, 0.02f, 0.15f, 0.0f, 0, 0.0f, 0.0f, 0.65f);
		allThrusters.add(terranBoosterThruster);
		Equipment terranAlphaThruster = new Equipment(EquipmentType.THRUSTER, "Terran TRB Mk4");
		terranAlphaThruster.set(22.0f, 35.0f, 0.03f, 0.15f, 0.0f, 0, 0.0f, 0.0f, 0.68f);
		allThrusters.add(terranAlphaThruster);
		Equipment terranShieldMk1 = new Equipment(EquipmentType.SHIELD, "Terran Shield Mk1");
		terranShieldMk1.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 150.0f, 0.0f);
		allShields.add(terranShieldMk1);
		Equipment terranShieldMk2 = new Equipment(EquipmentType.SHIELD, "Terran Shield Mk2");
		terranShieldMk2.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 300.0f, 0.0f);
		allShields.add(terranShieldMk2);
		Equipment terranShieldMk3 = new Equipment(EquipmentType.SHIELD, "Terran Shield Mk3");
		terranShieldMk3.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 700.0f, 0.0f);
		allShields.add(terranShieldMk3);
		Equipment terranShieldMk4 = new Equipment(EquipmentType.SHIELD, "Terran Shield Mk4");
		terranShieldMk4.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 1200.0f, 0.0f);
		allShields.add(terranShieldMk4);
		Equipment terranFuelTankMk1 = new Equipment(EquipmentType.FUEL_TANK, "Terran Fueltank Mk1");
		terranFuelTankMk1.set(0.0f, 0.0f, 0.0f, 0.0f, 100.0f, 0, 0.0f, 0.0f, 0.0f);
		allFuelTanks.add(terranFuelTankMk1);
		Equipment terranFuelTankMk2 = new Equipment(EquipmentType.FUEL_TANK, "Terran Fueltank Mk2");
		terranFuelTankMk2.set(0.0f, 0.0f, 0.0f, 0.0f, 250.0f, 0, 0.0f, 0.0f, 0.0f);
		allFuelTanks.add(terranFuelTankMk2);
		Equipment terranFuelTankMk3 = new Equipment(EquipmentType.FUEL_TANK, "Terran Fueltank Mk3");
		terranFuelTankMk3.set(0.0f, 0.0f, 0.0f, 0.0f, 400.0f, 0, 0.0f, 0.0f, 0.0f);
		allFuelTanks.add(terranFuelTankMk3);
		Equipment terranFuelTankMk4 = new Equipment(EquipmentType.FUEL_TANK, "Terran Fueltank Mk4");
		terranFuelTankMk4.set(0.0f, 0.0f, 0.0f, 0.0f, 700.0f, 0, 0.0f, 0.0f, 0.0f);
		allFuelTanks.add(terranFuelTankMk4);
		
		/* Create all spaceship blueprints */
		SpaceshipBlueprints terranScout = new SpaceshipBlueprints(terranHullMk1, terranAgileThruster, terranShieldMk1, terranFuelTankMk1);
		allShipBlueprints.add(terranScout);
		SpaceshipBlueprints terranFreight = new SpaceshipBlueprints(terranHullMk2, terranAgedThruster, terranShieldMk2, terranFuelTankMk2);
		allShipBlueprints.add(terranFreight);
		SpaceshipBlueprints terranVoyager = new SpaceshipBlueprints(terranHullMk2, terranBoosterThruster, terranShieldMk1, terranFuelTankMk3);
		allShipBlueprints.add(terranVoyager);
		SpaceshipBlueprints terranWarship = new SpaceshipBlueprints(terranHullMk3, terranBoosterThruster, terranShieldMk3, terranFuelTankMk3);
		allShipBlueprints.add(terranWarship);
	}
	
	public static void setScreenDimension(int height, int width) {
		SCREEN_HEIGHT = height;
		SCREEN_WIDTH = width;
	}
	
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}
	
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}
	
	public static Equipment[] getAllHulls() {
		Equipment[] hulls = new Equipment[allHulls.size];
		for(int i = 0; i < hulls.length; i++) {
			Equipment clone = new Equipment(allHulls.get(i));
			hulls[i] = clone;
		}
		return hulls;
	}
	
	public static Equipment getHullAt(int index) {
		Equipment clone = new Equipment(allHulls.get(index));
		return clone;
	}
	
	public static Equipment[] getAllThrusters() {
		Equipment[] thrusters = new Equipment[allThrusters.size];
		for(int i = 0; i < thrusters.length; i++) {
			Equipment clone = new Equipment(allThrusters.get(i));
			thrusters[i] = clone;
		}
		return thrusters;
	}
	
	public static Equipment getThrusterAt(int index) {
		Equipment clone = new Equipment(allThrusters.get(index));
		return clone;
	}
	
	public static Equipment[] getAllShields() {
		Equipment[] shields = new Equipment[allShields.size];
		for(int i = 0; i < shields.length; i++) {
			Equipment clone = new Equipment(allShields.get(i));
			shields[i] = clone;
		}
		return shields;
	}
	
	public static Equipment getShieldAt(int index) {
		Equipment clone = new Equipment(allShields.get(index));
		return clone;
	}
	
	public static Equipment[] getAllFueltanks() {
		Equipment[] fuelTanks = new Equipment[allFuelTanks.size];
		for(int i = 0; i < fuelTanks.length; i++) {
			Equipment clone = new Equipment(allFuelTanks.get(i));
			fuelTanks[i] = clone;
		}
		return fuelTanks;
	}
	
	public static Equipment getFuelTankAt(int index) {
		Equipment clone = new Equipment(allFuelTanks.get(index));
		return clone;
	}
	
	public static SpaceshipBlueprints[] getAllShipBlueprints() {
		SpaceshipBlueprints[] blueprints = new SpaceshipBlueprints[allShipBlueprints.size];
		for(int i = 0; i < blueprints.length; i++) {
			SpaceshipBlueprints clone = new SpaceshipBlueprints(allShipBlueprints.get(i));
			blueprints[i] = clone;
		}
		return blueprints;
	}
	
	public static SpaceshipBlueprints getShipBlueprintAt(int index) {
		SpaceshipBlueprints clone = new SpaceshipBlueprints(allShipBlueprints.get(index));
		return clone;
	}
}
