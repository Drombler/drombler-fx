package projectx.maven.plugins.pxb;

import com.sun.javafx.tools.ant.Application;
import com.sun.javafx.tools.ant.FXJar;
import java.io.File;
import java.nio.file.Path;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

/**
 *
 * @goal pxb
 *
 * @phase package
 */
public class PxbMojo extends AbstractMojo {

//    private String destFile;
//    private Application app;
//    private Platform platform;
//    private Resources resources;
//    private List<FileSet> filesets;
//    //private Manifest manifest;
//    private PackagerLib packager;
//    private CreateJarParams createJarParams;
    //
    /**
     * @parameter expression="${pxb.classesDirectory}"
     * default-value="${project.build.outputDirectory}" @required
     */
    private File classesDirectory;
    //TODO: finaleName useful? (Not respected when deploying to a Maven repository.)
    /**
     * @parameter expression="${pxb.finalName}"
     * default-value="${project.build.finalName}" @required
     */
    private String finalName;
    /**
     * @parameter expression="${pxb.outputDirectory}"
     * default-value="${project.build.directory}" @required
     */
    private File outputDirectory;
    /**
     * @parameter expression="${pxb.mainClass}" @required
     */
    private String mainClass;
    /**
     * The Maven project.
     *
     * @parameter default-value="${project}" @required @readonly
     */
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        Project antProject = new Project();
        FXJar fxJarTask = new FXJar();

        Path jarPath = outputDirectory.toPath().resolve(finalName + ".jar");
        fxJarTask.setDestfile(jarPath.toString());
        Application application = fxJarTask.createApplication();
        application.setMainClass(mainClass);

        FileSet fileSet = fxJarTask.createFileSet();
        fileSet.setDir(classesDirectory);
        fileSet.setProject(antProject);
        fxJarTask.execute();

        project.getArtifact().setFile(jarPath.toFile());

        
//        File f = outputDirectory;
//
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//
//        File touch = new File(f, "touch.txt");
//
//        FileWriter w = null;
//        try {
//            w = new FileWriter(touch);
//
//            w.write("touch.txt");
//        } catch (IOException e) {
//            throw new MojoExecutionException("Error creating file " + touch, e);
//        } finally {
//            if (w != null) {
//                try {
//                    w.close();
//                } catch (IOException e) {
//                    // ignore
//                }
//            }
//        }
    }
}
