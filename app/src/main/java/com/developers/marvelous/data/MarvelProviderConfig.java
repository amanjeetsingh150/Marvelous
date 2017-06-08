package com.developers.marvelous.data;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Amanjeet Singh on 07-Jun-17.
 */

@SimpleSQLConfig(
        name="MarvelProvider",
        authority = "com.developers.marvelous",
        database = "marvel.db",
        version =1)

public class MarvelProviderConfig implements ProviderConfig{

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
