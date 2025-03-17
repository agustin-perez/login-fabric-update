package me.bronzeisunbreakable.login.listeners;

import me.bronzeisunbreakable.login.LoginMod;
import me.bronzeisunbreakable.login.PlayerLogin;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerAction {
    public static boolean canInteract(ServerPlayNetworkHandler networkHandler) {
        ServerPlayerEntity player = networkHandler.player;
        PlayerLogin playerLogin = LoginMod.getPlayer(player);
        return playerLogin.isLoggedIn();
    }
}
