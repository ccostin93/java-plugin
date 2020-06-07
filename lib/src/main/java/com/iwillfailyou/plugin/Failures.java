package com.iwillfailyou.plugin;

import java.net.URL;

public interface Failures {
    void failIfRed() throws IwfyException;
    void show(Ui ui) throws IwfyException;
    void publish(URL url) throws IwfyException;
}
