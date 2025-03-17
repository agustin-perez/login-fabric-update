package me.bronzeisunbreakable.login.listeners;

import me.bronzeisunbreakable.login.LoginMod;
import me.bronzeisunbreakable.login.PlayerLogin;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class OnPlayerConnect {
    public static void listen(ServerPlayerEntity player) {
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        playerLogin.setLoggedIn(false);
        player.setInvulnerable(true);
        player.sendMessage(Text.literal("§9Bienvenido.\n§eLogueate con /login y registrate con /register"), false);
        player.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("§aLogueate")));
    }
}
