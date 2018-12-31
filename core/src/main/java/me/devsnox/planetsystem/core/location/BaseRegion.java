package me.devsnox.planetsystem.core.location;

import lombok.Data;
import me.devsnox.planetsystem.api.location.PlanetLocation;
import me.devsnox.planetsystem.api.location.Region;

@Data
public class BaseRegion implements Region {

    private final PlanetLocation min;
    private final PlanetLocation max;

}
