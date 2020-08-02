package com.iwillfailyou.plugin;

import java.net.URL;

public final class PublicFailures implements Failures {

    private final Urls urls;
    private final Failures origin;
    private final Inspection inspection;

    public PublicFailures(
        final Urls urls,
        final Failures origin,
        final Inspection inspection
    ) {
        this.urls = urls;
        this.origin = origin;
        this.inspection = inspection;
    }


    @Override
    public void failIfRed() throws IwfyException {
        origin.failIfRed();
    }

    @Override
    public void show(final Ui ui) throws IwfyException {
        origin.show(ui);
        publish(urls.forInspection(inspection));
    }

    @Override
    public void publish(final URL url) throws IwfyException {
        origin.publish(url);
    }
}
