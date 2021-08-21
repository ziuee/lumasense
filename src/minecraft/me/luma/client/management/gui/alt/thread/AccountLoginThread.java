package me.luma.client.management.gui.alt.thread;

import java.net.Proxy;
import java.util.UUID;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.alt.GuiAltManager;
import me.luma.client.management.gui.alt.impl.Account;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.Session;

public class AccountLoginThread extends Thread {
    private String email;
    private String password;
    public static boolean unknownBoolean1;
    private String status = "&eWaiting for login...";

    public AccountLoginThread(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void run() {
        if (this.password != null && !this.password.isEmpty()) {
            unknownBoolean1 = true;
            this.status = "&6Logging in...";
            YggdrasilAuthenticationService yService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
            UserAuthentication userAuth = yService.createUserAuthentication(Agent.MINECRAFT);
            if (userAuth == null) {
                this.status = "&4Unknown error.";
            } else {
                userAuth.setUsername(this.email);
                userAuth.setPassword(this.password);

                try {
                    userAuth.logIn();
                    Session session = new Session(userAuth.getSelectedProfile().getName(), userAuth.getSelectedProfile().getId().toString(), userAuth.getAuthenticatedToken(), this.email.contains("@") ? "mojang" : "legacy");
                    Minecraft.getMinecraft().session = session;
                    Account account = ClientLoader.loaderInstance.getAccountManager().getAccountByEmail(this.email);
                    account = account == null ? new Account(this.email, this.password, session.getUsername()) : account;
                    account.setName(session.getUsername());
                    if (!(Minecraft.getMinecraft().currentScreen instanceof GuiDisconnected)) {
                        ClientLoader.loaderInstance.getAccountManager().setLastAlt(account);
                    }

                    ClientLoader.loaderInstance.getAccountManager().save();
                    GuiAltManager.INSTANCE.currentAccount = account;
                    if (unknownBoolean1) {
                        this.status = String.format("&aLogged in as %s.", account.getName());
                    }
                } catch (AuthenticationException var5) {
                    this.status = "&4Login failed.";
                } catch (NullPointerException var6) {
                    this.status = "&4Unknown error.";
                }

            }
        } else {
            Minecraft.getMinecraft().session = new Session(this.email, "", "", "mojang");
            this.status = "&aLogged in as &ecracked&a.";
        }
    }

    public String getStatus() {
        return this.status;
    }
}
