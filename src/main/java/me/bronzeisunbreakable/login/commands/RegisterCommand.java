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

public class RegisterCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("register")
                .then(argument("newPassword", StringArgumentType.word())
                    .then(argument("confirmPassword", StringArgumentType.word())
                        .executes(ctx -> {
                            String password = StringArgumentType.getString(ctx, "newPassword");
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String username = player.getEntityName();
                            if (RegisteredPlayersJson.isPlayerRegistered(username)) {
                                ctx.getSource().sendFeedback(() -> Text.literal("§cYa estás registrado! Usá /login."), false);
                                return 1;
                            }
                            if (!password.equals(StringArgumentType.getString(ctx, "confirmPassword"))) {
                                ctx.getSource().sendFeedback(() -> Text.literal("§cLas contraseñas no coinciden, escribilas bien, pelotudo."), false);
                                return 1;
                            }
                            String uuid = ctx.getSource().getPlayer().getUuidAsString();
                            RegisteredPlayersJson.save(uuid, username, password);
                            PlayerLogin playerLogin = LoginMod.getPlayer(ctx.getSource().getPlayer());
                            playerLogin.setLoggedIn(true);
                            player.setInvulnerable(false);
                            ctx.getSource().sendFeedback(() -> Text.literal("§aRegistrado!"), false);
                            return 1;
        }))));
    }
}
