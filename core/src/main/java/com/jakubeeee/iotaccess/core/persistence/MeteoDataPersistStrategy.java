package com.jakubeeee.iotaccess.core.persistence;

import com.jakubeeee.iotaccess.core.data.entry.meteoentry.MeteoEntry;
import com.jakubeeee.iotaccess.core.data.entry.meteoentry.MeteoEntryService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.pluginapi.converter.DataType;
import com.jakubeeee.iotaccess.pluginapi.property.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

import static com.jakubeeee.iotaccess.pluginapi.converter.DataType.METEO;
import static com.jakubeeee.iotaccess.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class MeteoDataPersistStrategy extends BaseDataPersistStrategy<MeteoEntry> {

    private final MeteoEntryService meteoEntryService;

    public MeteoDataPersistStrategy(ProcessMetadataService processMetadataService,
                                    MeteoEntryService meteoEntryService) {
        super(processMetadataService);
        this.meteoEntryService = meteoEntryService;
    }

    @Override
    protected MeteoEntry generateEntry(FetchedVector vector, ProcessMetadata processMetadata) {
        var newEntry = new MeteoEntry(processMetadata);
        setEntryField(newEntry::setIdentifier, vector, IDENTIFIER, FetchedText.class);
        setEntryField(newEntry::setTemperature, vector, TEMPERATURE, FetchedNumber.class);
        setEntryField(newEntry::setHumidity, vector, HUMIDITY, FetchedNumber.class);
        setEntryField(newEntry::setPressure, vector, PRESSURE, FetchedNumber.class);
        setEntryField(newEntry::setLuminance, vector, LUMINANCE, FetchedNumber.class);
        setEntryField(newEntry::setRainDigital, vector, RAIN_DIGITAL, FetchedNumber.class);
        setEntryField(newEntry::setRainAnalog, vector, RAIN_ANALOG, FetchedNumber.class);
        setEntryField(newEntry::setWindPower, vector, WIND_POWER, FetchedNumber.class);
        setEntryField(newEntry::setWindDirection, vector, WIND_DIRECTION, FetchedText.class);
        setEntryField(newEntry::setGpsAltitude, vector, GPS_ALTITUDE, FetchedNumber.class);
        setEntryField(newEntry::setGpsLongitude, vector, GPS_LONGITUDE, FetchedNumber.class);
        setEntryField(newEntry::setGpsLatitude, vector, GPS_LATITUDE, FetchedNumber.class);
        setEntryField(newEntry::setMoment, vector, MOMENT, FetchedDate.class);
        return newEntry;
    }

    private <T> void setEntryField(Consumer<T> entrySetter,
                                   FetchedVector vector,
                                   String propertyKey,
                                   Class<? extends FetchedProperty<T>> propertyType) {
        var property = findByKey(vector, propertyKey, propertyType);
        entrySetter.accept(property.getValue());
    }

    private <T extends FetchedProperty<?>> T findByKey(FetchedVector vector, String filteredKey, Class<T> type) {
        List<T> matchingProperties = vector.getFetchedProperties()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .filter(property -> property.getKey().equals(filteredKey))
                .collect(toUnmodifiableList());
        validateExactlyOnePropertyFound(matchingProperties, filteredKey, type);
        return matchingProperties.get(0);
    }

    private <T extends FetchedProperty<?>> void validateExactlyOnePropertyFound(List<T> matchingProperties,
                                                                                String filteredKey,
                                                                                Class<T> type) {
        int matchingPropertiesSize = matchingProperties.size();
        if (matchingPropertiesSize != 1)
            throw new IllegalStateException(
                    format("There must be exactly one property for key \"{0}\" and type \"{1}\". Instead found: \"{2}\"",
                            filteredKey, type.getSimpleName(), matchingPropertiesSize));
    }

    @Override
    protected void persistEntry(MeteoEntry newEntry) {
        meteoEntryService.save(newEntry);
    }

    @Override
    public DataType getSupportedDataType() {
        return METEO;
    }

}
