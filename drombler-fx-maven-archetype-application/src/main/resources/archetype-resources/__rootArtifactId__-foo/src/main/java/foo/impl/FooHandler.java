#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )


package ${package}.foo.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.drombler.acp.core.data.AbstractDocumentHandler;
import org.drombler.acp.core.data.DocumentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DocumentHandler(mimeType = FooUtils.MIME_TYPE, icon = "foo.png")
public class FooHandler extends AbstractDocumentHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FooHandler.class);
    private final StringProperty text = new SimpleStringProperty(this, "text");

    public FooHandler() {
        this(null);
    }

    public FooHandler(Path path) {
        super(FooUtils.FILE_EXTENSION, path);
        markDirty();
    }

    public final String getText() {
        return textProperty().get();
    }

    public final void setText(String text) {
        textProperty().set(text);
    }

    public StringProperty textProperty() {
        if (isDirty()) {
            markClean();
            loadContent();
        }
        return text;
    }

    public void loadContent() {
        if (getPath() != null) {
            try {
                setText(String.join("\n", Files.readAllLines(getPath())));
            } catch (IOException ex) {
                LOG.error("Could not read from file: " + getPath(), ex);
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(FooHandler.class.getResourceAsStream("fooTemplate.txt")))) {
                setText(br.lines().collect(Collectors.joining("\n")));
            } catch (IOException ex) {
                LOG.error("Could not read initial content!", ex);
            }
        }
    }

    @Override
    protected void writeContent() throws IOException {
        Files.write(getPath(), Collections.singletonList(getText()));
    }

}
