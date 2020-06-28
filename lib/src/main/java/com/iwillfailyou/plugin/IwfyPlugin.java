package com.iwillfailyou.plugin;

import org.cactoos.Scalar;
import org.cactoos.list.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.scalar.Solid;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IwfyPlugin implements Plugin {
    private final Ui ui;
    private final Scalar<List<File>> files;
    private final List<Inspection> inspections;

    public IwfyPlugin(
        final Ui ui,
        final File root,
        final List<Inspection> inspections
    ) {
        this(
            ui,
            new Solid<>(() -> {
                final List<File> files = new ArrayList<>();
                try {
                    Files.walkFileTree(
                        root.toPath(),
                        new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult visitFile(
                                final Path file,
                                final BasicFileAttributes attrs
                            ) {
                                files.add(file.toFile());
                                return FileVisitResult.CONTINUE;
                            }
                        }
                    );
                } catch (IOException e) {
                    throw new IwfyException(
                        String.format(
                            "Could not enumerate files in the %s",
                            root
                        ),
                        e
                    );
                }
                return files;
            }),
            inspections
        );
    }

    public IwfyPlugin(
        final Ui ui,
        final Scalar<List<File>> files,
        final List<Inspection> inspections
    ) {
        this.ui = ui;
        this.files = files;
        this.inspections = inspections;
    }

    @Override
    public void run() throws IwfyException {
        try {
            for (final File file : files.value()) {
                for (final Inspection inspection : inspections) {
                    inspection.accept(file);
                }
            }
        } catch (Exception e) {
            throw new IwfyException("Could not inspect files. ", e);
        }
        final List<Map.Entry<String, Failures>> failuresList = new Mapped<>(
            inspection -> new MapEntry<>(
                inspection.name(), inspection.failures()
            ),
            inspections
        );
        for (final Map.Entry<String, Failures> failures : failuresList) {
            ui.println(failures.getKey());
            failures.getValue().show(ui);
            ui.println("");
        }
        for (final Map.Entry<String, Failures> failures : failuresList) {
            failures.getValue().failIfRed();
        }
    }
}
