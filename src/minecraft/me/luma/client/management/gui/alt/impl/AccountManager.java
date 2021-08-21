package me.luma.client.management.gui.alt.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountManager {
    private ArrayList<Account> accounts = new ArrayList();
    private final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
    private File altsFile;
    private String alteningKey;
    private String lastAlteningAlt;
    private Account lastAlt;

    public AccountManager(File parent) {
        this.altsFile = new File(parent.toString() + File.separator + "alts.json");
        this.load();
    }

    public void save() {
        if (this.altsFile != null) {
            try {
                if (!this.altsFile.exists()) {
                    this.altsFile.createNewFile();
                }

                PrintWriter printWriter = new PrintWriter(this.altsFile);
                printWriter.write(this.gson.toJson(this.toJson()));
                printWriter.close();
            } catch (IOException var2) {
            }

        }
    }

    public void load() {
        if (!this.altsFile.exists()) {
            this.save();
        } else {
            try {
                JsonObject json = (new JsonParser()).parse(new FileReader(this.altsFile)).getAsJsonObject();
                this.fromJson(json);
            } catch (IOException var2) {
            }

        }
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        this.getAccounts().forEach((account) -> {
            jsonArray.add(account.toJson());
        });

        if (this.lastAlt != null) {
            jsonObject.add("lastalt", this.lastAlt.toJson());
        }

        jsonObject.add("accounts", jsonArray);
        return jsonObject;
    }

    public void fromJson(JsonObject json) {
        if (json.has("lastalt")) {
            Account account = new Account();
            account.fromJson(json.get("lastalt").getAsJsonObject());
            this.lastAlt = account;
        }

        JsonArray jsonArray = json.get("accounts").getAsJsonArray();
        jsonArray.forEach((jsonElement) -> {
            JsonObject jsonObject = (JsonObject)jsonElement;
            Account account = new Account();
            account.fromJson(jsonObject);
            this.getAccounts().add(account);
        });
    }

    public void remove(String username) {
        Iterator var2 = this.getAccounts().iterator();

        while(var2.hasNext()) {
            Account account = (Account)var2.next();
            if (account.getName().equalsIgnoreCase(username)) {
                this.getAccounts().remove(account);
            }
        }

    }

    public Account getAccountByEmail(String email) {
        Iterator var2 = this.getAccounts().iterator();

        Account account;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            account = (Account)var2.next();
        } while(!account.getEmail().equalsIgnoreCase(email));

        return account;
    }

    public String getLastAlteningAlt() {
        return this.lastAlteningAlt;
    }

    public void setLastAlteningAlt(String lastAlteningAlt) {
        this.lastAlteningAlt = lastAlteningAlt;
    }

    public String getAlteningKey() {
        return this.alteningKey;
    }

    public void setAlteningKey(String alteningKey) {
        this.alteningKey = alteningKey;
    }

    public Account getLastAlt() {
        return this.lastAlt;
    }

    public void setLastAlt(Account lastAlt) {
        this.lastAlt = lastAlt;
    }

    public ArrayList<Account> getNotBannedAccounts() {
        List<Account> accounts = new ArrayList(this.accounts);

        for(int i = 0; i < accounts.size(); ++i) {
            if (((Account)accounts.get(i)).isBanned()) {
                accounts.remove(i);
            }
        }

        return this.accounts;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
}