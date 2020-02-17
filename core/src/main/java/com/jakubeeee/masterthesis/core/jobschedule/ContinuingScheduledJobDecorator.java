package com.jakubeeee.masterthesis.core.jobschedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
final class ContinuingScheduledJobDecorator implements ScheduledJob {

    private final ScheduledJob wrapped;

    @Override
    public void run() {
        try {
            wrapped.run();
        } catch (Exception e) {
            LOG.error(
                    "An exception has occurred during scheduled job execution. Aborting current execution.", e);
        } catch (Throwable t) {
            LOG.error(
                    "A fatal error has occurred during scheduled job execution. Aborting current and all future executions",
                    t);
            throw t;
        }
    }

}
