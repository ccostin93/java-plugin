package com.iwillfailyou.plugin;

import java.net.URL;

public interface Failures {
    void failIfRed() throws IwfyException;
    void show(Ui ui) throws IwfyException;
    void publish(URL url) throws IwfyException;

    final class Fake implements Failures {

        @Override
        public void failIfRed() {
            // ignored
        }

        @Override
        public void show(final Ui ui) {
            // ignored
        }

        @Override
        public void publish(final URL url) {
            // ignored
        }
    }
}
