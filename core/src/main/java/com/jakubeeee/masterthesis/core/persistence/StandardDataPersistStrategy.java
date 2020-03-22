package com.jakubeeee.masterthesis.core.persistence;

import com.jakubeeee.masterthesis.core.data.entry.standardentry.StandardEntry;
import com.jakubeeee.masterthesis.core.data.entry.standardentry.StandardEntryService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataType.STANDARD;
import static java.text.MessageFormat.format;

@Component
public class StandardDataPersistStrategy extends BaseDataPersistStrategy<StandardEntry> {

    private final StandardEntryService standardEntryService;

    public StandardDataPersistStrategy(ProcessMetadataService processMetadataService,
                                       StandardEntryService standardEntryService) {
        super(processMetadataService);
        this.standardEntryService = standardEntryService;
    }

    @Override
    protected StandardEntry generateEntry(FetchedVector vector, ProcessMetadata processMetadata) {
        var newEntry = new StandardEntry(processMetadata);
        populateEntryFields(newEntry, vector);
        return newEntry;
    }

    private void populateEntryFields(StandardEntry entry, FetchedVector vector) {
        for (var property : vector.getFetchedProperties())
            populateEntryField(entry, property);
    }

    private void populateEntryField(StandardEntry entry, FetchedProperty<?> property) {
        if (property instanceof FetchedText textProperty)
            tryInsertTextProperty(entry, textProperty);
        else if (property instanceof FetchedNumber numberProperty)
            tryInsertNumberProperty(entry, numberProperty);
        else if (property instanceof FetchedDate dateProperty)
            tryInsertDateProperty(entry, dateProperty);
        else
            throw new UnsupportedOperationException(format("Property type \"{0}\" is not supported by \"{1}\"",
                    property.getClass().getSimpleName(), this.getClass().getSimpleName()));
    }

    private void tryInsertTextProperty(StandardEntry entry, FetchedText property) {
        var delegates = List.of(
                delegate(entry::getTextKey1, entry::setTextKey1, entry::setText1),
                delegate(entry::getTextKey2, entry::setTextKey2, entry::setText2),
                delegate(entry::getTextKey3, entry::setTextKey3, entry::setText3),
                delegate(entry::getTextKey4, entry::setTextKey4, entry::setText4),
                delegate(entry::getTextKey5, entry::setTextKey5, entry::setText5)
        );
        tryInsertProperties(delegates, property);
    }

    private void tryInsertNumberProperty(StandardEntry entry, FetchedNumber property) {
        var delegates = List.of(
                delegate(entry::getNumberKey1, entry::setNumberKey1, entry::setNumber1),
                delegate(entry::getNumberKey2, entry::setNumberKey2, entry::setNumber2),
                delegate(entry::getNumberKey3, entry::setNumberKey3, entry::setNumber3),
                delegate(entry::getNumberKey4, entry::setNumberKey4, entry::setNumber4),
                delegate(entry::getNumberKey5, entry::setNumberKey5, entry::setNumber5),
                delegate(entry::getNumberKey6, entry::setNumberKey6, entry::setNumber6),
                delegate(entry::getNumberKey7, entry::setNumberKey7, entry::setNumber7),
                delegate(entry::getNumberKey8, entry::setNumberKey8, entry::setNumber8),
                delegate(entry::getNumberKey9, entry::setNumberKey9, entry::setNumber9),
                delegate(entry::getNumberKey10, entry::setNumberKey10, entry::setNumber10)
        );
        tryInsertProperties(delegates, property);
    }

    private void tryInsertDateProperty(StandardEntry entry, FetchedDate property) {
        var delegates = List.of(
                delegate(entry::getDateKey1, entry::setDateKey1, entry::setDate1),
                delegate(entry::getDateKey2, entry::setDateKey2, entry::setDate2),
                delegate(entry::getDateKey3, entry::setDateKey3, entry::setDate3),
                delegate(entry::getDateKey4, entry::setDateKey4, entry::setDate4),
                delegate(entry::getDateKey5, entry::setDateKey5, entry::setDate5)
        );
        tryInsertProperties(delegates, property);
    }

    private <T> void tryInsertProperties(List<EntryFieldDelegate<T>> delegates, FetchedProperty<T> property) {
        boolean inserted = false;
        for (var delegate : delegates) {
            inserted = tryInsertProperty(delegate, property);
            if (inserted)
                break;
        }
        if (!inserted)
            throw new IllegalStateException(format("There is no enough space for another value of type \"{0}\"",
                    property.getClass().getSimpleName()));
    }

    private <T> boolean tryInsertProperty(EntryFieldDelegate<T> delegate,
                                          FetchedProperty<T> property) {
        if (delegate.keyGetter.get() != null) {
            return false;
        } else {
            delegate.keySetter.accept(property.getKey());
            delegate.valueSetter.accept(property.getValue());
            return true;
        }
    }

    @Override
    protected void persistEntry(StandardEntry newEntry) {
        standardEntryService.save(newEntry);
    }

    @Override
    public DataType getSupportedDataType() {
        return STANDARD;
    }

    private <T> EntryFieldDelegate<T> delegate(Supplier<String> keyGetter,
                                               Consumer<String> keySetter,
                                               Consumer<T> valueSetter) {
        return new EntryFieldDelegate<>(keyGetter, keySetter, valueSetter);
    }

    @RequiredArgsConstructor
    private static class EntryFieldDelegate<T> {
        private final Supplier<String> keyGetter;
        private final Consumer<String> keySetter;
        private final Consumer<T> valueSetter;
    }

}
