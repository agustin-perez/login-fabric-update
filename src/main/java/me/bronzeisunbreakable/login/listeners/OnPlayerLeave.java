package me.bronzeisunbreakable.login.listeners;

import me.bronzeisunbreakable.login.LoginMod;
import me.bronzeisunbreakable.login.PlayerLogin;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerLeave {
    public static void listen(ServerPlayerEntity player) {
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        playerLogin.setLoggedIn(false);
    }
}
