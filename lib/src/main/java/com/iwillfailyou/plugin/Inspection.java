package com.iwillfailyou.plugin;

import java.io.File;

public interface Inspection {
    String name();
    void accept(File file) throws IwfyException;
    Failures failures();
}
