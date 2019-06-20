package org.codehaus.mojo.license;

/*
 * #%L
 * License Maven Plugin
 * %%
 * Copyright (C) 2019 MojoHaus
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.mojo.license.api.ArtifactFilters;
import org.codehaus.mojo.license.api.MavenProjectDependenciesConfigurator;
import org.codehaus.mojo.license.api.ResolvedProjectDependencies;
import org.codehaus.mojo.license.download.LicenseSummaryReader;
import org.codehaus.mojo.license.download.LicensedArtifact;
import org.codehaus.mojo.license.download.ProjectLicenseInfo;
import org.codehaus.mojo.license.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert versions into a {@code licenses.xml} file that might have been generated by a {@code *download-licenses} mojo
 * with {@code writeVersions} set to {@code false}.
 *
 * @since 1.19
 */
@Mojo( name = "licenses-xml-insert-versions", requiresDependencyResolution = ResolutionScope.TEST,
    defaultPhase = LifecyclePhase.PACKAGE )
public class LicensesXmlInsertVersionsMojo
    extends AbstractLicensesXmlMojo
{
    private static final Logger LOG = LoggerFactory.getLogger(LicensesXmlInsertVersionsMojo.class);

    /**
     * The file whose XML content will be used as a base for adding versions. Defaults to
     * {@link AbstractLicensesXmlMojo#licensesOutputFile}
     *
     * @since 1.19
     */
    @Parameter( property = "license.licensesInputFile" )
    protected File licensesInputFile;

    /**
     * A flag to skip the goal.
     *
     * @since 1.19
     */
    @Parameter( property = "license.skipDownloadLicenses", defaultValue = "false" )
    private boolean skipDownloadLicenses;

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {

        if ( skipDownloadLicenses )
        {
            LOG.info( "Skipping due to skipDownloadLicenses = true" );
            return;
        }

        if ( licensesInputFile == null )
        {
            licensesInputFile = licensesOutputFile;
        }

        try
        {
            FileUtil.createDirectoryIfNecessary( licensesOutputFile.getParentFile() );

            final List<ProjectLicenseInfo> projectLicenseInfos =
                LicenseSummaryReader.parseLicenseSummary( licensesInputFile );

            if ( projectLicenseInfos.isEmpty() && licensesInputFile.equals( licensesOutputFile ) )
            {
                LOG.info( "Nothing to do. The licensesInputFile \"{}\" is either empty or does not exist.",
                        licensesInputFile );
                return;
            }

            final ArtifactFilters.Builder artifactFiltersBuilder = ArtifactFilters.buidler();
            for ( ProjectLicenseInfo dep : projectLicenseInfos )
            {
                artifactFiltersBuilder.includeGa( "\\Q" + dep.getGroupId() + ":" + dep.getArtifactId() + "\\E" );
            }
            final ArtifactFilters artifactFilters = artifactFiltersBuilder.build();

            final MavenProjectDependenciesConfigurator config = new MavenProjectDependenciesConfigurator()
            {

                @Override
                public boolean isVerbose()
                {
                    return getLog().isDebugEnabled();
                }

                @Override
                public boolean isIncludeTransitiveDependencies()
                {
                    return true;
                }

                @Override
                public boolean isExcludeTransitiveDependencies()
                {
                    return false;
                }

                @Override
                public ArtifactFilters getArtifactFilters()
                {
                    return artifactFilters;
                }
            };
            final Map<String, LicensedArtifact> resolvedDeps = new TreeMap<String, LicensedArtifact>();
            licensedArtifactResolver.loadProjectDependencies(
                    new ResolvedProjectDependencies( project.getArtifacts(), project.getDependencyArtifacts() ),
                                                     config, remoteRepositories, resolvedDeps );
            final Map<String, LicensedArtifact> resolvedDepsMap = new HashMap<>( resolvedDeps.size() );
            for ( LicensedArtifact dep : resolvedDeps.values() )
            {
                resolvedDepsMap.put( dep.getGroupId() + ":" + dep.getArtifactId(), dep );
            }

            for ( ProjectLicenseInfo dependencyLicenseInfo : projectLicenseInfos )
            {
                LOG.debug( "Checking licenses for project {}", dependencyLicenseInfo.toString() );
                final String id = dependencyLicenseInfo.getId();
                final LicensedArtifact dependency = resolvedDepsMap.get( id );
                if ( dependency == null )
                {
                    throw new MojoFailureException( "Could not resolve version of " + id + " in file "
                        + licensesOutputFile );
                }
                dependencyLicenseInfo.setVersion( dependency.getVersion() );
            }

            writeLicenseSummary( projectLicenseInfos, licensesOutputFile, true );
        }
        catch ( MojoFailureException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Unable to write license summary file: " + licensesOutputFile, e );
        }
    }

    protected Path[] getAutodetectEolFiles()
    {
        return new Path[] { licensesInputFile.toPath(), licensesOutputFile.toPath() };
    }

}
