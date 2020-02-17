package com.jakubeeee.masterthesis.core.persistence;

import com.jakubeeee.masterthesis.core.data.entry.meteoentry.MeteoEntry;
import com.jakubeeee.masterthesis.core.data.entry.meteoentry.MeteoEntryService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import org.springframework.stereotype.Component;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataType.METEO;
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
    protected MeteoEntry generateEntry(FetchedRecord record, ProcessMetadata processMetadata) {
        var newEntry = new MeteoEntry(processMetadata);

        var identifier = findByKey(record, "identifier", FetchedText.class);
        newEntry.setIdentifier(identifier.getValue());

        var temperature = findByKey(record, "temperature", FetchedNumber.class);
        newEntry.setTemperature(temperature.getValue());

        var humidity = findByKey(record, "humidity", FetchedNumber.class);
        newEntry.setHumidity(humidity.getValue());

        var pressure = findByKey(record, "pressure", FetchedNumber.class);
        newEntry.setPressure(pressure.getValue());

        var luminance = findByKey(record, "luminance", FetchedNumber.class);
        newEntry.setLuminance(luminance.getValue());

        var rainDigital = findByKey(record, "rainDigital", FetchedNumber.class);
        newEntry.setRainDigital(rainDigital.getValue());

        var rainAnalog = findByKey(record, "rainAnalog", FetchedNumber.class);
        newEntry.setRainAnalog(rainAnalog.getValue());

        var windPower = findByKey(record, "windPower", FetchedNumber.class);
        newEntry.setWindPower(windPower.getValue());

        var windDirection = findByKey(record, "windDirection", FetchedText.class);
        newEntry.setWindDirection(windDirection.getValue());

        var gpsAltitude = findByKey(record, "gpsAltitude", FetchedNumber.class);
        newEntry.setGpsAltitude(gpsAltitude.getValue());

        var gpsLongitude = findByKey(record, "gpsLongitude", FetchedNumber.class);
        newEntry.setGpsLongitude(gpsLongitude.getValue());

        var gpsLatitude = findByKey(record, "gpsLatitude", FetchedNumber.class);
        newEntry.setGpsLatitude(gpsLatitude.getValue());

        var dateTime = findByKey(record, "dateTime", FetchedDate.class);
        newEntry.setDateTime(dateTime.getValue());

        return newEntry;
    }

    private <T extends FetchedProperty> T findByKey(FetchedRecord record, String filteredKey, Class<T> type) {
        var matchingProperties = record.getFetchedProperties()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .filter(property -> property.getKey().equals(filteredKey))
                .collect(toUnmodifiableList());
        int matchingPropertiesSize = matchingProperties.size();
        if (matchingPropertiesSize != 1)
            throw new IllegalStateException(
                    format("There must be exactly one property for key \"{0}\" and type \"{1}\". Instead found: \"{2}\"",
                            filteredKey, type.getSimpleName(), matchingPropertiesSize));
        return matchingProperties.get(0);
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
