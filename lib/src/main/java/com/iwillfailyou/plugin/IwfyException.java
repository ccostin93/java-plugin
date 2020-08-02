package com.iwillfailyou.plugin;

public final class IwfyException extends Exception {
    public IwfyException(final String message) {
        super(message);
    }

    public IwfyException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
