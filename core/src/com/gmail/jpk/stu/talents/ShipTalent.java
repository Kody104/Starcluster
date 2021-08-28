package com.gmail.jpk.stu.talents;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class ShipTalent extends BaseTalent {

	private Sprite talentIcon; // The icon of this ability
	private float modDamage; // The main stat of this ability
	private float cooldown; // The cooldown of this talent
	private String modTextType;
	private String modTextExtra;

	public ShipTalent(BoilerPlateTalents key) {
		super(key);
	}

	@Override
	protected void initTalent() {
		BoilerPlateTalents key = getKey();
		if (key == null) {
			return;
		}
		cooldown = -1;
		switch (key) {
			case DOMINATOR_1: {
				setName("Fission");
				modTextType = "fission";
				setDescription("Your weapons add a stack of" + modTextType + " to the target." + " \nAfter 3 seconds the "
						+ modTextType + " explodes, dealing bonus" + modTextType + " damage per stack.");
				break;
			}
			case DOMINATOR_2: {
				setName("Explosives Expert");
				modDamage = 0.5f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				setDescription("Explosive weapons deal " + modTextDamage + " more damage!");
				break;
			}
			case DOMINATOR_3: {
				setName("Fission Wasteland");
				modTextType = "fission";
				setDescription("Using bomb weapons leaves a " + modTextType + "-inducing toxic cloud!");
				break;
			}
			case DUELER_1: {
				setName("Feast");
				modDamage = 0.35f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				setDescription("Attacking the same enemy in succession gives you health back equal to " + modTextDamage
						+ " of the damage dealt.");
				break;
			}
			case DUELER_2: {
				setName("Photon Mark");
				modDamage = 0.05f;
				cooldown = 10.0f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				modTextType = "photon";
				setDescription("Photon weapons apply a mark for 5 seconds to target." + " While marked the target takes "
						+ modTextDamage + " of their max health as bonus " + modTextType + " damage. "
						+ "Occurs once per " + cooldown + " seconds.");
				break;
			}
			case DUELER_3: {
				setName("Anti-Shield Blast");
				cooldown = 45.0f;
				setDescription(
						"Press Z to use a shockwave that disables nearby enemy shields for 4 seconds. Usable once per " + cooldown + " seconds.");
				break;
			}
			case DUELER_4: {
				setName("Weapon Mastery");
				modDamage = 0.75f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				setDescription("Gain " + modTextDamage + " attack speed.");
				break;
			}
			case IMMOBILIZATION_1: {
				setName("Accurate Torpedoes");
				modDamage = 0.2f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				setDescription("Your torpedoes have a " + modTextDamage + " chance to immobilize your target.");
				break;
			}
			case IMMOBILIZATION_2: {
				setName("Exploit Weakness");
				modDamage = 0.35f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				setDescription(
						"Your attacks on enemies that have a status effect deals " + modTextDamage + " more damage.");
				break;
			}
			case IMMOBILIZATION_3: {
				setName("Grime");
				modDamage = 0.75f;
				cooldown = 45.0f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				setDescription("Can press Z to send out a fast-moving grime ball that slows the enemy hit by "
						+ modTextDamage + " for 8 seconds. Usable once per " + cooldown + " seconds.");
				break;
			}
			case IMMOBILIZATION_4: {
				setName("Disabling Torpedoes");
				modDamage = 0.3f;
				String modTextDamage = "" + modDamage * 100.0f + "%%";
				modTextExtra = "40%%";
				setDescription("Your torpedoes have a " + modTextDamage + " chance to disable the target,  reducing their damage by " + modTextExtra + " for 4 seconds.");
				break;
			}
			case BULWARK_1: {
				setName("");
				setDescription("");
				break;
			}
			case BULWARK_2: {
				setName("");
				setDescription("");
				break;
			}
			case BULWARK_3: {
				setName("");
				setDescription("");
				break;
			}
			case BULWARK_4: {
				setName("");
				setDescription("");
				break;
			}
			case BULWARK_5: {
				setName("");
				setDescription("");
				break;
			}
			case RETALIATION_1: {
				setName("");
				setDescription("");
				break;
			}
			case RETALIATION_2: {
				setName("");
				setDescription("");
				break;
			}
			case RETALIATION_3: {
				setName("");
				setDescription("");
				break;
			}
			case PARRY_1: {
				setName("");
				setDescription("");
				break;
			}
			case PARRY_2: {
				setName("");
				setDescription("");
				break;
			}
			case PARRY_3: {
				setName("");
				setDescription("");
				break;
			}
			case PARRY_4: {
				setName("");
				setDescription("");
				break;
			}
			case PARRY_5: {
				setName("");
				setDescription("");
				break;
			}
		}
	}
}
