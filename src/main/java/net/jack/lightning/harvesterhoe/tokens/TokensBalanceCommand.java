package net.jack.lightning.harvesterhoe.tokens;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TokensBalanceCommand implements CommandExecutor {



    private final LightningCore core;
    private final Tokens tokens;

    public TokensBalanceCommand(LightningCore core) {
        this.core = core;
        this.tokens = new Tokens(core);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (args.length == 0) {
            int toks = tokens.getTokens(player);
            player.sendMessage(CC.translate("&8Tokens: &6" + String.valueOf(toks)));
            return true;
        }


        return false;
    }
}
