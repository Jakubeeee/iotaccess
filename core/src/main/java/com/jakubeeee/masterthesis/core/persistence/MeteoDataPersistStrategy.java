package com.jakubeeee.masterthesis.core.persistence;

import com.jakubeeee.masterthesis.core.data.entry.meteoentry.MeteoEntry;
import com.jakubeeee.masterthesis.core.data.entry.meteoentry.MeteoEntryService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataType.METEO;
import static com.jakubeeee.masterthesis.pluginapi.meteo.MeteoPropertyKeyConstants.*;
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
        setEntryField(newEntry::setIdentifier, record, IDENTIFIER, FetchedText.class);
        setEntryField(newEntry::setTemperature, record, TEMPERATURE, FetchedNumber.class);
        setEntryField(newEntry::setHumidity, record, HUMIDITY, FetchedNumber.class);
        setEntryField(newEntry::setPressure, record, PRESSURE, FetchedNumber.class);
        setEntryField(newEntry::setLuminance, record, LUMINANCE, FetchedNumber.class);
        setEntryField(newEntry::setRainDigital, record, RAIN_DIGITAL, FetchedNumber.class);
        setEntryField(newEntry::setRainAnalog, record, RAIN_ANALOG, FetchedNumber.class);
        setEntryField(newEntry::setWindPower, record, WIND_POWER, FetchedNumber.class);
        setEntryField(newEntry::setWindDirection, record, WIND_DIRECTION, FetchedText.class);
        setEntryField(newEntry::setGpsAltitude, record, GPS_ALTITUDE, FetchedNumber.class);
        setEntryField(newEntry::setGpsLongitude, record, GPS_LONGITUDE, FetchedNumber.class);
        setEntryField(newEntry::setGpsLatitude, record, GPS_LATITUDE, FetchedNumber.class);
        setEntryField(newEntry::setMoment, record, MOMENT, FetchedDate.class);
        return newEntry;
    }

    private <T> void setEntryField(Consumer<T> entrySetter,
                                   FetchedRecord record,
                                   String propertyKey,
                                   Class<? extends FetchedProperty<T>> propertyType) {
        var property = findByKey(record, propertyKey, propertyType);
        entrySetter.accept(property.getValue());
    }

    private <T extends FetchedProperty<?>> T findByKey(FetchedRecord record, String filteredKey, Class<T> type) {
        List<T> matchingProperties = record.getFetchedProperties()
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
