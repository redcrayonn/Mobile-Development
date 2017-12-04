package nl.inholland.imready.service.mock;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.BlockPartStatus;
import nl.inholland.imready.model.blocks.Component;

public class MockData {

    public static List<Block> blocks = initBlocks();

    private static List<Block> initBlocks() {
        List<Block> blocks = new ArrayList<Block>() {{
            // WONEN
            Block block_living = new Block(
                    null,
                    "Wonen",
                    "Description",
                    "ImageUrl",
                    new ArrayList<Component>() {{
                        // WOONRUIMTE ZOEKEN
                        Component living_space = new Component(
                                null,
                                "Woonruimte zoeken",
                                BlockPartStatus.ONGOING,
                                new ArrayList<Activity>() {{
                                    // REAGEER OP 5 WONINGEN
                                    Activity respond_to_houses = new Activity(
                                            null,
                                            "Reageer op 5 woningen",
                                            "Dit is een beschrijving van de taak.\nHierin staan tips en verdere informatie voor de gebruiker/client",
                                            6,
                                            BlockPartStatus.ONGOING,
                                            "",
                                            ""
                                    );
                                    // SCHRIJF JE IN BIJ DE GEMEENTE
                                    Activity register_town = new Activity(
                                            null,
                                            "Schrijf je in bij de gemeente",
                                            "Dit is een beschrijving van de taak.\nHierin staan tips en verdere informatie voor de gebruiker/client",
                                            3,
                                            BlockPartStatus.COMPLETE,
                                            "",
                                            ""
                                    );

                                    add(respond_to_houses);
                                    add(register_town);
                                }}
                        );

                        Component social_rent = new Component(
                                null,
                                "Sociale huur",
                                BlockPartStatus.ONGOING,
                                new ArrayList<Activity>() {{
                                    Activity activity1 = new Activity(
                                            null,
                                            "Activity #1",
                                            "Dit is een beschrijving van de taak.\nHierin staan tips en verdere informatie voor de gebruiker/client",
                                            1,
                                            BlockPartStatus.PENDING,
                                            "Dit is de ingevulde content door de gebruiker",
                                            ""
                                    );

                                    add(activity1);
                                }}
                        );

                        add(living_space);
                        add(social_rent);
                        add(living_space);
                        add(social_rent);
                        add(living_space);
                        add(social_rent);
                    }}
            );

            add(block_living);
        }};
        for (int i = 0; i < 6; i++) {
            Block b = new Block("Block #" + i);
            blocks.add(b);
        }
        return blocks;
    }
}
