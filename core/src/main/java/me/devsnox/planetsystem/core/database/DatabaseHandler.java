package me.devsnox.planetsystem.core.database;

import com.mongodb.MongoClient;
import me.devsnox.planetsystem.api.holder.Holder;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHandler implements me.devsnox.planetsystem.api.handler.DatabaseHandler {

    private final MongoClient mongoClient;
    private final Morphia morphia;

    private final Datastore datastore;

    private final PlanetDAO planetDAO;
    private final PlayerDAO playerDAO;

    public DatabaseHandler() {
        this.mongoClient = new MongoClient("127.0.0.1", 27017); //TODO: Initialization of MongoClient

        this.morphia = new Morphia();
        this.datastore = this.morphia.createDatastore(this.mongoClient, "cosmic");
        this.datastore.ensureIndexes();

        this.planetDAO = new PlanetDAO(DatabasePlanet.class, this.datastore);
        //this.planetDAO.exists("uuid", UUID.randomUUID());
        this.playerDAO = new PlayerDAO(DatabasePlayer.class, this.datastore);
    }

    @Override
    public DatabasePlanet getPlanet(final UUID uuid) {
        return this.planetDAO.findOne("uuid", uuid);
    }

    @Override
    public void savePlanet(final me.devsnox.planetsystem.api.database.DatabasePlanet databasePlanet) {
        this.planetDAO.save((DatabasePlanet) databasePlanet); //TODO FUCK YOU CAST!
    }

    @Override
    public void create(UUID planet, UUID player) {
        if (!this.playerDAO.exists("uuid", player)) {
            DatabasePlayer databasePlayer = new DatabasePlayer(player, planet, new ArrayList<>());
            DatabasePlanet databasePlanet = new DatabasePlanet(planet, "Alpha Centauri", player, new ArrayList<>(), (byte) 8, new PlanetLocation() {
                @Override
                public UUID getPlanetID() {
                    return planet;
                }

                @Override
                public double getX() {
                    return 0;
                }

                @Override
                public void setX(double x) {

                }

                @Override
                public double getY() {
                    return 0;
                }

                @Override
                public void setY(double y) {

                }

                @Override
                public double getZ() {
                    return 0;
                }

                @Override
                public void setZ(double z) {

                }

                @Override
                public float getYaw() {
                    return 0;
                }

                @Override
                public void setYaw(float yaw) {

                }

                @Override
                public float getPitch() {
                    return 0;
                }

                @Override
                public void setPitch(float pitch) {

                }
            });
            playerDAO.save(databasePlayer);
            planetDAO.save(databasePlanet);
        }
    }

    @Override
    public DatabasePlayer getPlayer(final UUID uuid) {
        return this.playerDAO.findOne("uuid", uuid);
    }

    @Override
    public void savePlayer(final me.devsnox.planetsystem.api.database.DatabasePlayer databasePlayer) {
        this.playerDAO.save((DatabasePlayer) databasePlayer); //TODO FUCK YOU CAST!
    }

}
