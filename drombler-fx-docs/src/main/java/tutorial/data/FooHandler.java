/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package tutorial.data;

import java.io.IOException;
import java.nio.file.Path;
import org.drombler.acp.core.data.AbstractDocumentHandler;
import org.drombler.acp.core.data.DocumentHandler;

/**
 *
 * @author puce
 */
@DocumentHandler(mimeType = "text/foo", icon = "foo.png")
public class FooHandler extends AbstractDocumentHandler {

    private static final String FILE_EXTENSION = "foo";

    public FooHandler() {
        super(FILE_EXTENSION);
    }

    public FooHandler(Path path) {
        super(FILE_EXTENSION, path);
    }

    @Override
    protected void writeContent() throws IOException {
        // Files.write(getPath(), <some content> ...);
    }

}
