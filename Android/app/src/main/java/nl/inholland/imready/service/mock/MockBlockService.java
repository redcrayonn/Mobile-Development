package nl.inholland.imready.service.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.BlockPartStatus;
import nl.inholland.imready.model.blocks.Component;
import nl.inholland.imready.service.rest.BlockService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockBlockService implements BlockService {
    private BehaviorDelegate<BlockService> delegate;

    private List<Block> blocks;

    public MockBlockService(BehaviorDelegate<BlockService> delegate) {
        this.delegate = delegate;
        init();
    }

    private void init() {
        blocks = new ArrayList<Block>() {{
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
                                            "",
                                            ""
                                    );

                                    add(activity1);
                                }}
                        );

                        add(living_space);
                        add(social_rent);
                    }}
            );

            add(block_living);
        }};
        for (int i = 0; i < 10; i++) {
            Block b = new Block("Block #" + i);
            blocks.add(b);
        }
    }

    @Override
    public Call<List<Block>> getBlocks() {
        return delegate.returningResponse(blocks).getBlocks();
    }

    @Override
    public Call<Block> getBlock(String blockId) {
        Iterator<Block> iterator = blocks.iterator();
        Block block = new Block();
        while (iterator.hasNext()) {
            Block b = iterator.next();
            if (b.getId().equals(blockId)) {
                block = b;
                break;
            }
        }
        return delegate.returningResponse(block).getBlock(blockId);
    }
}
