/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx.maven.plugins.pxb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.artifact.repository.ArtifactRepositoryFactory;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.dependency.AbstractDependencyFilterMojo;
import org.apache.maven.plugin.dependency.AbstractDependencyMojo;
import org.apache.maven.plugin.dependency.AbstractFromDependenciesMojo;
import org.apache.maven.plugin.dependency.CopyDependenciesMojo;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.ops4j.pax.construct.util.ReflectMojo;
import org.javafxplatform.core.application.impl.FXApplicationLauncher;
import org.richclientplatform.startup.main.impl.ApplicationConfigProviderImpl;
import org.richclientplatform.startup.main.impl.Main;
import org.softsmithy.lib.nio.file.JarFiles;
import projectx.maven.plugins.pxb.util.FXUtils;

/**
 *
 * @goal standalone-zip
 *
 * @phase package
 *
 * @requiresDependencyResolution compile+runtime
 */
public class CreateStandaloneZipMojo extends AbstractMojo {

    /**
     * @parameter expression="${platform.brandingId}" @required
     */
    private String brandingId;
    /**
     * @parameter expression="${platform.title}" @required
     */
    private String title;
    /**
     * @parameter expression="${platform.width}" @required
     */
    private double width;
    /**
     * @parameter expression="${platform.height}" @required
     */
    private String height;
    /**
     * @parameter expression="${platform.userdir}" default-value="${user.home}/.${brandingId}/${project.version}"
     * @required
     */
    private String userdir;
    /**
     * @parameter expression="${pxb.targetDirectory}" default-value="${project.build.directory}/deployment/standalone"
     * @required
     */
    private File targetDirectory;
    /**
     * @parameter default-value="${maven.dependency.org.apache.felix.org.apache.felix.main.jar.path}" @required
     * @readonly
     */
    private String apacheFelixMainJarPathString;
    /**
     * The Maven project.
     *
     * @parameter default-value="${project}" @required @readonly
     */
    private MavenProject project;
    /**
     * @parameter expression="${platform.configProperties}"
     * default-value="${basedir}/src/main/resources/config.properties"
     */
    private File configPropertiesFile;
    /**
     * @parameter expression="${platform.systemProperties}"
     * default-value="${basedir}/src/main/resources/system.properties"
     */
    private File systemPropertiesFile;
    /**
     * @component
     */
    private ArtifactRepositoryFactory artifactRepositoryFactory;
    /**
     * @component role="org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout"
     */
    private Map<String, ArtifactRepositoryLayout> artifactRepositoryLayouts;
    /**
     * @component
     */
    private ArtifactInstaller artifactInstaller;
    /**
     * @component role="org.codehaus.plexus.archiver.Archiver" roleHint="zip"
     */
    private ZipArchiver zipArchiver;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = targetDirectory.toPath();
            if (!Files.exists(targetDirPath)) {
                Files.createDirectories(targetDirPath);
            }

            Path binDirPath = targetDirPath.resolve("bin");
            if (!Files.exists(binDirPath)) {
                Files.createDirectories(binDirPath);
            }

            createMainJar(binDirPath);

            Path confPath = targetDirPath.resolve(Main.CONFIG_DIRECTORY);
            if (!Files.exists(confPath)) {
                Files.createDirectories(confPath);
            }
            Properties systemProperties = new Properties();
            if (systemPropertiesFile != null && systemPropertiesFile.exists()) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(systemPropertiesFile))) {
                    systemProperties.load(bis);
                }
            }
            writeProperties(systemProperties, confPath, Main.SYSTEM_PROPERTIES_FILE_VALUE);

            userdir = userdir.replace("${brandingId}", brandingId);
            Properties configProperties = new Properties();
            System.out.println("Userdir:" + userdir);
            configProperties.setProperty(Main.USER_DIR_PROPERTY, userdir);
            if (configPropertiesFile != null && configPropertiesFile.exists()) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(configPropertiesFile))) {
                    configProperties.load(bis);
                }
            }
            writeProperties(configProperties, confPath, Main.CONFIG_PROPERTIES_FILE_VALUE);

//            URI confURI = CreateStandaloneZipMojo.class.getResource("/conf").toURI();
//            try (FileSystem jarFS = FileSystems.newFileSystem(JarFiles.getJarURI(confURI),
//                            new HashMap<String, Object>())) {
//                CopyFileVisitor.copy(Paths.get(confURI), confPath);
//            }
            copyBundles(targetDirPath);

        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
            throw new MojoExecutionException("Creating standalone zip failed!", ex);
        }
    }

    private void createMainJar(Path binDirPath) throws IOException, URISyntaxException {
        Path jarPath = binDirPath.resolve(brandingId + ".jar");

        //            CopyMojo copyMojo = new CopyMojo();
        //
        //            ReflectMojo reflectAbstractFromDependenciesMojo = new ReflectMojo(copyMojo,
        //                    AbstractFromDependenciesMojo.class);
        //            reflectAbstractFromDependenciesMojo.setField("artifactItems",
        //                    Arrays.asList(new ArtifactItem(new Artifact() {
        //            })))
        //            copyMojo.execute();
        Path mainJarPath = JarFiles.getJarPath(Main.class);
        if (!Files.exists(jarPath)) {
            Files.copy(mainJarPath, jarPath);

            try (FileSystem jarFS = FileSystems.newFileSystem(jarPath, null)) {
                FXUtils.copyMainClasses(jarFS.getPath("/"));
                createApplicationConfigProperties(jarFS);
            }
        }
    }

    private void createApplicationConfigProperties(FileSystem jarFS) throws IOException {
        Properties applicationConfigProperties = new Properties();
        applicationConfigProperties.setProperty(FXApplicationLauncher.APPLICATION_TITLE_PROPERTY_NAME, title);
        Path applicationConfigPropertiesPath = jarFS.getPath(
                ApplicationConfigProviderImpl.APPLICATION_PROPERTIES_FILE_PATH);

        try (OutputStream os = Files.newOutputStream(applicationConfigPropertiesPath)) {
            applicationConfigProperties.store(os, null);
        }
    }

    private void copyBundles(Path targetDirPath) throws MojoExecutionException, IOException {
        Path bundleDirPath = targetDirPath.resolve("bundle");
        if (!Files.exists(bundleDirPath)) {
            Files.createDirectories(bundleDirPath);
        }

        CopyDependenciesMojo copyDependenciesMojo = new CopyDependenciesMojo();

        ReflectMojo reflectCopyDependenciesMojo = new ReflectMojo(copyDependenciesMojo, CopyDependenciesMojo.class);
        reflectCopyDependenciesMojo.setField("repositoryFactory", artifactRepositoryFactory);
        reflectCopyDependenciesMojo.setField("repositoryLayouts", artifactRepositoryLayouts);
        reflectCopyDependenciesMojo.setField("installer", artifactInstaller);

        ReflectMojo reflectAbstractFromDependenciesMojo = new ReflectMojo(copyDependenciesMojo,
                AbstractFromDependenciesMojo.class);
        reflectAbstractFromDependenciesMojo.setField("outputDirectory", bundleDirPath.toFile());
        reflectAbstractFromDependenciesMojo.setField("useRepositoryLayout", true);
        reflectAbstractFromDependenciesMojo.setField("copyPom", false);

        ReflectMojo reflectAbstractDependencyFilterMojoMojo = new ReflectMojo(copyDependenciesMojo,
                AbstractDependencyFilterMojo.class);
        reflectAbstractDependencyFilterMojoMojo.setField("excludeScope", "system");

        ReflectMojo reflectAbstractDependencyMojo = new ReflectMojo(copyDependenciesMojo,
                AbstractDependencyMojo.class);
        reflectAbstractDependencyMojo.setField("project", project);

        copyDependenciesMojo.execute();
    }

    private void writeProperties(Properties properties, Path confPath, String propertiesFileName) throws IOException {
        Path propertiesFilePath = confPath.resolve(propertiesFileName);
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(propertiesFilePath))) {
            properties.store(bos, "");
        }
    }
}
