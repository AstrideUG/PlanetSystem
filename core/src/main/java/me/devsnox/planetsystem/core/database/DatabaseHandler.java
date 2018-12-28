package me.devsnox.planetsystem.core.database;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.UUID;

public class DatabaseHandler {

    private final MongoClient mongoClient;
    private final Morphia morphia;

    private final Datastore datastore;

    private final PlanetDAO planetDAO;
    private final PlayerDAO playerDAO;

    public DatabaseHandler() {
        this.mongoClient = null; //TODO: Initialization of MongoClient

        this.morphia = new Morphia();
        this.datastore = this.morphia.createDatastore(this.mongoClient, "cosmic");
        this.datastore.ensureIndexes();

        this.planetDAO = new PlanetDAO(DatabasePlanet.class, this.datastore);
        this.playerDAO = new PlayerDAO(DatabasePlayer.class, this.datastore);
    }

    public DatabasePlanet getPlanet(final UUID uuid) {
        return this.planetDAO.findOne("uuid", uuid);
    }

    public void savePlanet(final DatabasePlanet databasePlanet) {
        this.planetDAO.save(databasePlanet);
    }

    public DatabasePlayer getPlayer(final UUID uuid) {
        return this.playerDAO.findOne("uuid", uuid);
    }

    public void savePlayer(DatabasePlayer databasePlayer) {
        this.playerDAO.save(databasePlayer);
    }
}
