# **AnimalRPs plugin**

## **Animal listing**
1. Bee
Doubleclicking shift you will float (levitation 5 for 1 second), allowing for better movement up hills as example. **Your superfood is the honeybottle.**
2. Cat
You take significantly less damage when falling (5 hearts). You can still die, don't count on your cat-powers catching you every time. **Your superfood is all types of eatable fish.**
3. Fox
You do more damage to mobs (25%). **Your superfood is all types of berry.**
4. Phantom
You can use elytras, however you burn in daylight and your elytra speed is capped at half of the normal one. **Your superfoods are all uncooked meats.**

## **Chat**
Chat while you are a animal is very different. When you speak, your words will become furry-ified and every time you talk you'll have animal sounds come out of you.

## **Superfoods**
Superfoods are items that when eaten, give you stackable **Speed II** and insane amounts of saturation (9.4 points) and hunger (4 points).

## **Cooldowns & animals**
Cooldowns are stored in game. Animal/cooldowns are stored in a json database.

## **How to add a new animal?**

1. Create a new class in animals/MyCoolAnimal.java
2. Use this template
```
public class MyCoolAnimal extends Animal {
    TextDestroyer destroyer = new TextDestroyer(new String[]{
        "I'm Cool!"
    }, new String[][]{
        {"g", "z"}
    });

    public MyCoolAnimal() {
        super("coolanimal", "awsome!", "#00ff00");
        this.moodSounds.put(Mood.HAPPY, Sound.ENTITY_FOX_EAT);
        this.moodSounds.put(Mood.CUTE, Sound.ENTITY_FOX_SLEEP);
        this.moodSounds.put(Mood.SAD, Sound.ENTITY_FOX_SNIFF);
        this.moodSounds.put(Mood.STRESSED, Sound.ENTITY_FOX_AGGRO);
        this.moodSounds.put(Mood.ANGRY, Sound.ENTITY_FOX_BITE);

        this.superfoods.add(Material.POTATO);
    }

    @Override
    public String chatTransformations(String message) {
        return this.destroyer.destroy(message);
    }
}
```
3. Change the info in the TextDestroyer initialization
4. Change the info in the super() call, it is written as "name", "catchphrase", "color".
5. Change the moodSounds and implement all sounds for the moods.
6. Add superfoods (foods that the animal likes)