package me.bronzeisunbreakable.login.commands;

import me.bronzeisunbreakable.login.LoginMod;
import me.bronzeisunbreakable.login.PlayerLogin;
import me.bronzeisunbreakable.login.RegisteredPlayersJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class LoginCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("login")
                .then(argument("password", StringArgumentType.word())
                    .executes(ctx -> {
                        String password = StringArgumentType.getString(ctx, "password");
                        String username = ctx.getSource().getPlayer().getEntityName();
                        ServerPlayerEntity player = ctx.getSource().getPlayer();

                        if (!RegisteredPlayersJson.isPlayerRegistered(username)) {
                            ctx.getSource().sendFeedback(() -> Text.literal("§cNo estás registrado! Usá /register."), false);
                        } else if (RegisteredPlayersJson.isCorrectPassword(username, password)) {
                            PlayerLogin playerLogin = LoginMod.getPlayer(ctx.getSource().getPlayer());
                            playerLogin.setLoggedIn(true);
                            ctx.getSource().sendFeedback(() -> Text.literal("§aSesión iniciada."), false);
                            if (!player.isCreative()) {
                                player.setInvulnerable(false);
                            }
                            //player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("minecraft:block.note_block.pling"), SoundCategory.MASTER, player.getPos(), 100f, 0f));
                        } else {
                            //player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("minecraft:entity.zombie.attack_iron_door"), SoundCategory.MASTER, player.getPos(), 100f, 0.5f));
                            ctx.getSource().sendFeedback(() -> Text.literal("§cContraseña incorrecta boludito!"), false);
                        }
                        return 1;
        })));
    }
}
