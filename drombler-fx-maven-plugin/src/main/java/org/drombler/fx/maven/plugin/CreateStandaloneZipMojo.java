/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.maven.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.artifact.repository.ArtifactRepositoryFactory;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.dependency.AbstractDependencyFilterMojo;
import org.apache.maven.plugin.dependency.AbstractDependencyMojo;
import org.apache.maven.plugin.dependency.AbstractFromDependenciesMojo;
import org.apache.maven.plugin.dependency.CopyDependenciesMojo;
import org.apache.maven.plugin.dependency.fromConfiguration.AbstractFromConfigurationMojo;
import org.apache.maven.plugin.dependency.fromConfiguration.ArtifactItem;
import org.apache.maven.plugin.dependency.fromConfiguration.CopyMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.drombler.acp.startup.main.DromblerACPConfiguration;
import org.drombler.fx.startup.main.DromblerFXApplication;
import org.drombler.fx.startup.main.DromblerFXConfiguration;
import org.ops4j.pax.construct.util.ReflectMojo;
import org.softsmithy.lib.nio.file.CopyFileVisitor;
import org.softsmithy.lib.nio.file.JarFiles;

@Mojo(name = "standalone-zip", defaultPhase = LifecyclePhase.PACKAGE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class CreateStandaloneZipMojo extends AbstractMojo {

    private static final Path RELATIVE_CONFIG_PROPERTIES_FILE_PATH
            = Paths.get(DromblerACPConfiguration.CONFIG_DIRECTORY, DromblerACPConfiguration.CONFIG_PROPERTIES_FILE_NAME);

    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;
    /**
     * The application title.
     */
    @Parameter(property = "dromblerfx.title", required = true)
    private String title;
    /**
     * The default application width.
     */
    @Parameter(property = "dromblerfx.width", required = true)
    private double width;
    /**
     * The default application height.
     */
    @Parameter(property = "dromblerfx.height", required = true)
    private double height;
    /**
     * The user directory.
     */
    // TODO: good solution using "${dollar}"?
    @Parameter(property = "dromblerfx.userdir", defaultValue = "${dollar}{user.home}/.${brandingId}/${project.version}",
            required = true)
    private String userdir;
    /**
     * The target directory.
     */
    @Parameter(property = "dromblerfx.targetDirectory",
            defaultValue = "${project.build.directory}/deployment/standalone", required = true)
    private File targetDirectory;
    /**
     * The application resource source directory.
     */
    @Parameter(property = "dromblerfx.appSourceDir", defaultValue = "${basedir}/src/main/app", required = true)
    private File appSourceDir;

    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Component
    private ArtifactRepositoryFactory artifactRepositoryFactory;

    @Component(role = ArtifactRepositoryLayout.class)
    private Map<String, ArtifactRepositoryLayout> artifactRepositoryLayouts;

    @Component
    private ArtifactInstaller artifactInstaller;

    @Component
    private ArtifactFactory artifactFactory;

    @Component
    private ArtifactResolver artifactResolver;

    @Component(role = Archiver.class, hint = "zip")
    private ZipArchiver zipArchiver;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = targetDirectory.toPath();

            ensureDirExists(targetDirPath);

            Path binDirPath = targetDirPath.resolve("bin");

            ensureDirExists(binDirPath);

            createMainJar(binDirPath);

            copyLibs(binDirPath);

            copyAppDir(targetDirPath);

            ensureDirExists(targetDirPath.resolve(DromblerACPConfiguration.CONFIG_DIRECTORY));
            ensureFileExists(targetDirPath, Paths.get(DromblerACPConfiguration.CONFIG_DIRECTORY,
                    DromblerACPConfiguration.SYSTEM_PROPERTIES_FILE_NAME));
            ensureConfigPropertiesFileExists(targetDirPath);

//            URI confURI = CreateStandaloneZipMojo.class.getResource("/conf").toURI();
//            try (FileSystem jarFS = FileSystems.newFileSystem(JarFiles.getJarURI(confURI),
//                            new HashMap<String, Object>())) {
//                CopyFileVisitor.copy(Paths.get(confURI), confPath);
//            }
            copyBundles(targetDirPath);

        } catch (URISyntaxException | IOException ex) {
            throw new MojoExecutionException("Creating standalone zip failed!", ex);
        }
    }

    private void ensureDirExists(Path dirPath) throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    private void ensureFileExists(Path targetDirPath, final Path relativeFilePath) throws IOException {
        Path targetFilePath = targetDirPath.resolve(relativeFilePath);
        if (!Files.exists(targetFilePath)) {
            Files.createFile(targetFilePath);
        }
    }

    private void ensureConfigPropertiesFileExists(Path targetDirPath) throws IOException {
        Path targetFilePath = targetDirPath.resolve(RELATIVE_CONFIG_PROPERTIES_FILE_PATH);
        if (!Files.exists(targetFilePath)) {
            writeConfigPropertiesFile(targetFilePath);
        }
    }

    private void writeConfigPropertiesFile(Path targetConfigPropertiesFile) throws IOException {
        userdir = userdir.replace("${brandingId}", brandingId);
        Properties configProperties = new Properties();
        configProperties.setProperty(DromblerACPConfiguration.USER_DIR_PROPERTY, userdir);
        Path configPropertiesFilePath = getAppSourceDirPath().resolve(RELATIVE_CONFIG_PROPERTIES_FILE_PATH);
        if (Files.exists(configPropertiesFilePath)) {
            try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(configPropertiesFilePath))) {
                configProperties.load(bis);
            }
        }
        writeProperties(configProperties, targetConfigPropertiesFile);
    }

    private void writeProperties(Properties properties, Path propertiesFilePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(propertiesFilePath))) {
            properties.store(bos, "");
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
        Path mainJarPath = JarFiles.getJarPath(DromblerFXApplication.class);
        if (!Files.exists(jarPath)) {
            Files.copy(mainJarPath, jarPath);

            try (FileSystem jarFS = FileSystems.newFileSystem(jarPath, null)) {
                createApplicationConfigProperties(jarFS);
            }
        }
    }

    private void createApplicationConfigProperties(FileSystem jarFS) throws IOException {
        Properties applicationConfigProperties = new Properties();
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_TITLE_PROPERTY_NAME, title);
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_WIDTH_PROPERTY_NAME,
                Double.toString(width));
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_HEIGHT_PROPERTY_NAME,
                Double.toString(height));
        Path applicationConfigPropertiesPath = jarFS.getPath(DromblerACPConfiguration.APPLICATION_PROPERTIES_FILE_PATH);

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

    private void copyArtifacts(Path outputDirPath, boolean useRepositoryLayout, String excludeScope,
            List<ArtifactItem> artifactItems) throws MojoExecutionException {
        CopyMojo copyMojo = new CopyMojo();

        ReflectMojo reflectAbstractFromConfigurationMojo = new ReflectMojo(copyMojo, AbstractFromConfigurationMojo.class);
        reflectAbstractFromConfigurationMojo.setField("outputDirectory", outputDirPath.toFile());
        reflectAbstractFromConfigurationMojo.setField("artifactItems", artifactItems);
        reflectAbstractFromConfigurationMojo.setField("artifactRepositoryManager", artifactRepositoryFactory);

        ReflectMojo reflectAbstractDependencyMojo = new ReflectMojo(copyMojo, AbstractDependencyMojo.class);
        reflectAbstractDependencyMojo.setField("factory", artifactFactory);
        reflectAbstractDependencyMojo.setField("project", project);
        reflectAbstractDependencyMojo.setField("resolver", artifactResolver);

        copyMojo.execute();
    }

    private void copyAppDir(Path targetDirPath) throws IOException {
        Path appSourceDirPath = getAppSourceDirPath();
        if (Files.exists(appSourceDirPath) && Files.isDirectory(appSourceDirPath)) {
            FileVisitor<Path> copyFileVisitor = new CopyFileVisitor(appSourceDirPath, targetDirPath) {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (appSourceDirPath.relativize(file).equals(RELATIVE_CONFIG_PROPERTIES_FILE_PATH)) {
                        writeConfigPropertiesFile(targetDirPath.resolve(RELATIVE_CONFIG_PROPERTIES_FILE_PATH));
                        return FileVisitResult.CONTINUE;
                    } else {
                        return super.visitFile(file, attrs);
                    }
                }

            };
            Files.walkFileTree(appSourceDirPath, copyFileVisitor);
        }
    }

    private Path getAppSourceDirPath() {
        return appSourceDir.toPath();
    }

    private void copyLibs(Path binDirPath) throws IOException, MojoExecutionException {
//        Path libDirPath = binDirPath.resolve("lib");
//        if (!Files.exists(libDirPath)) {
//            Files.createDirectories(libDirPath);
//        }
//        List<Dependency> dependencies = project.getDependencies();
//        Dependency dromblerFXStartupMainDependency = dependencies.stream().filter(dependency
//                -> dependency.getGroupId().equals("org.drombler.fx")
//                && dependency.getArtifactId().equals("drombler-fx-startup-main")).
//                findFirst().
//                orElseThrow(() -> new MojoExecutionException("???"));
//
//        Set<Artifact> artifacts = project.getArtifacts();
//        Artifact dromblerFXStartupMainArtifact = artifacts.stream().
//                filter(artifact
//                        -> artifact.getGroupId().equals("org.drombler.fx")
//                        && artifact.getArtifactId().equals("drombler-fx-startup-main")).
//                findFirst().
//                orElseThrow(() -> new MojoExecutionException("???"));
//        List<Artifact> libs = new ArrayList<>();
//        libs.add(dromblerFXStartupMainArtifact);
//        dromblerFXStartupMainArtifact.getDependencyTrail();
//        copyArtifacts(libDirPath, true, "system", createEndorsedArtifactsList());
    }

//    private List<ArtifactItem> createEndorsedArtifactsList() {
//        ArtifactItem javaxAnnotationArtifactItem = new ArtifactItem();
//        javaxAnnotationArtifactItem.setGroupId("javax.annotation");
//        javaxAnnotationArtifactItem.setArtifactId("javax.annotation-api");
//        javaxAnnotationArtifactItem.setVersion("1.2");
//
//        return Arrays.asList(javaxAnnotationArtifactItem);
//    }
}
