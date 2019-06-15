package org.crandor.game.node.entity.player.ai.general;

import org.crandor.game.node.entity.player.Player;
import org.crandor.game.node.entity.player.ai.AIPBuilder;
import org.crandor.game.node.entity.player.ai.AIPlayer;
import org.crandor.game.node.entity.player.ai.general.scriptrepository.Script;
import org.crandor.game.node.item.Item;
import org.crandor.game.system.task.Pulse;
import org.crandor.game.world.GameWorld;
import org.crandor.game.world.map.Location;
import org.crandor.game.world.repository.Repository;

public class GeneralBotCreator {

    private Script botScript;

    //org/crandor/net/packet/in/InteractionPacket.java <<< This is a very useful class for learning to code bots
    public GeneralBotCreator(String botName, Location loc, Script botScript)
    {
        this.botScript = botScript;

        botScript.bot = AIPBuilder.create(botName, loc);
        Repository.getPlayers().add(botScript.bot);
        botScript.init();

        startBot();
    }

    public GeneralBotCreator(Player player, Script botScript)
    {
        this.botScript = botScript;
        botScript.bot = player;
        botScript.init();
        startBot();
    }

    private void startBot() {
        GameWorld.submit(new Pulse(1, botScript.bot) {
            @Override
            public boolean pulse() {
                if (!botScript.bot.getPulseManager().hasPulseRunning())
                {
                    botScript.runLoop();
                }
                return false;
            }
        });
    }
}
