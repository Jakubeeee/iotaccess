package com.jakubeeee.iotaccess.core.data.entry;

import com.jakubeeee.iotaccess.core.data.DataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;

public interface EntryService<T extends EntryEntity> extends DataService<T> {

    void disconnectFromProcessMetadata(ProcessMetadata processMetadata);

}
