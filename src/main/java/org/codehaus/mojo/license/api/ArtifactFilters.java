package org.codehaus.mojo.license.api;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.maven.artifact.Artifact;

/**
 * Artifact filtering by
 * <ul>
 * <li>{@code groupId:artifactId} regular expression includes/excludes</li>
 * <li>Scope includes/excludes</li>
 * <li>Type includes/excludes</li>
 * </ul>
 *
 * @author <a href="https://github.com/ppalaga">Peter Palaga</a>
 * @since 1.18
 */
public class ArtifactFilters
{

    public static Builder buidler()
    {
        return new Builder();
    }

    public static ArtifactFilters of( MavenProjectDependenciesConfigurator config )
    {
        Builder builder = new Builder();

        builder.includeGa( toGaPattern( config.getIncludedGroups(), true ) );
        builder.excludeGa( toGaPattern( config.getExcludedGroups(), true ) );

        builder.includeGa( toGaPattern( config.getIncludedArtifacts(), false ) );
        builder.excludeGa( toGaPattern( config.getExcludedArtifacts(), false ) );

        builder.includeScopes( config.getIncludedScopes() );
        builder.excludeScopes( config.getExcludedScopes() );
        builder.includeTypes( config.getIncludedTypes() );
        builder.excludeTypes( config.getExcludedTypes() );

        return builder.build();
    }

    private static String toGaPattern( String rawPattern, boolean isGroup )
    {
        if ( rawPattern == null )
        {
            return null;
        }
        else
        {
            rawPattern = "[^:]*(" + rawPattern + ")[^:]*";

            if ( isGroup )
            {
                return rawPattern + ":[^:]+";
            }
            else
            {
                return "[^:]+:" + rawPattern;
            }
        }
    }

    private final IncludesExcludes gaFilters;

    private final IncludesExcludes scopeFilters;

    private final IncludesExcludes typeFilters;

    public ArtifactFilters( IncludesExcludes scopeFilters, IncludesExcludes typeFilters, IncludesExcludes gaFilters )
    {
        super();
        this.scopeFilters = scopeFilters;
        this.typeFilters = typeFilters;
        this.gaFilters = gaFilters;
    }

    public boolean isIncluded( Artifact artifact )
    {
        return scopeFilters.isIncluded( artifact ) && typeFilters.isIncluded( artifact )
            && gaFilters.isIncluded( artifact );
    }

    interface ArtifactFilter
    {
        boolean matches( Artifact artifact );
    }

    /**
     * An {@link ArtifactFilters} builder.
     */
    public static class Builder
    {
        private final IncludesExcludes.Builder gaFilters = new IncludesExcludes.Builder();

        private final IncludesExcludes.Builder scopeFilters = new IncludesExcludes.Builder();

        private final IncludesExcludes.Builder typeFilters = new IncludesExcludes.Builder();

        public ArtifactFilters build()
        {
            return new ArtifactFilters( scopeFilters.build(), typeFilters.build(), gaFilters.build() );
        }

        public Builder excludeGa( String pattern )
        {
            if ( pattern != null )
            {
                gaFilters.exclude( new GaFilter( Pattern.compile( pattern ) ) );
            }
            return this;
        }

        public Builder excludeGas( String... patterns )
        {
            if ( patterns != null )
            {
                for ( String pattern : patterns )
                {
                    gaFilters.exclude( new GaFilter( Pattern.compile( pattern ) ) );
                }
            }
            return this;
        }

        public Builder excludeScope( String scope )
        {
            scopeFilters.exclude( new ScopeFilter( scope ) );
            return this;
        }

        public Builder excludeScopes( String... scopes )
        {
            if ( scopes != null )
            {
                for ( String scope : scopes )
                {
                    scopeFilters.exclude( new ScopeFilter( scope ) );
                }
            }
            return this;
        }

        public Builder excludeScopes( Collection<String> scopes )
        {
            if ( scopes != null )
            {
                for ( String scope : scopes )
                {
                    scopeFilters.exclude( new ScopeFilter( scope ) );
                }
            }
            return this;
        }

        public Builder excludeType( String type )
        {
            typeFilters.exclude( new TypeFilter( type ) );
            return this;
        }

        public Builder excludeTypes( String... types )
        {
            if ( types != null )
            {
                for ( String type : types )
                {
                    typeFilters.exclude( new TypeFilter( type ) );
                }
            }
            return this;
        }

        public Builder excludeTypes( Collection<String> types )
        {
            if ( types != null )
            {
                for ( String type : types )
                {
                    typeFilters.exclude( new TypeFilter( type ) );
                }
            }
            return this;
        }

        public Builder includeGa( String pattern )
        {
            if ( pattern != null )
            {
                gaFilters.include( new GaFilter( Pattern.compile( pattern ) ) );
            }
            return this;
        }

        public Builder includeGas( String... patterns )
        {
            if ( patterns != null )
            {
                for ( String pattern : patterns )
                {
                    gaFilters.include( new GaFilter( Pattern.compile( pattern ) ) );
                }
            }
            return this;
        }

        public Builder includeScope( String scope )
        {
            scopeFilters.include( new ScopeFilter( scope ) );
            return this;
        }

        public Builder includeScopes( String... scopes )
        {
            if ( scopes != null )
            {
                for ( String scope : scopes )
                {
                    scopeFilters.include( new ScopeFilter( scope ) );
                }
            }
            return this;
        }

        public Builder includeScopes( Collection<String> scopes )
        {
            if ( scopes != null )
            {
                for ( String scope : scopes )
                {
                    scopeFilters.include( new ScopeFilter( scope ) );
                }
            }
            return this;
        }

        public Builder includeType( String type )
        {
            typeFilters.include( new TypeFilter( type ) );
            return this;
        }

        public Builder includeTypes( String... types )
        {
            if ( types != null )
            {
                for ( String type : types )
                {
                    typeFilters.include( new TypeFilter( type ) );
                }
            }
            return this;
        }

        public Builder includeTypes( Collection<String> types )
        {
            if ( types != null )
            {
                for ( String type : types )
                {
                    typeFilters.include( new TypeFilter( type ) );
                }
            }
            return this;
        }

    }

    static class GaFilter
        implements ArtifactFilter
    {
        private final Pattern pattern;

        public GaFilter( Pattern pattern )
        {
            super();
            this.pattern = pattern;
        }

        public boolean matches( Artifact artifact )
        {
            return pattern.matcher( artifact.getGroupId() + ":" + artifact.getArtifactId() ).matches();
        }
    }

    static class IncludesExcludes
    {
        static class Builder
        {
            private int includesCount = 0;

            private List<ArtifactFilter> includesExcludes = new ArrayList<>();

            public IncludesExcludes build()
            {
                List<ArtifactFilter> ie = includesExcludes;
                this.includesExcludes = null;
                return new IncludesExcludes( ie, includesCount );
            }

            public Builder exclude( ArtifactFilter filter )
            {
                this.includesExcludes.add( filter );
                return this;
            }

            public Builder include( ArtifactFilter filter )
            {
                this.includesExcludes.add( includesCount, filter );
                this.includesCount++;
                return this;
            }
        }

        private final int includesCount;

        private final List<ArtifactFilter> includesExcludes;

        public IncludesExcludes( List<ArtifactFilter> includesExcludes, int includesCount )
        {
            super();
            this.includesExcludes = includesExcludes;
            this.includesCount = includesCount;
        }

        public boolean isIncluded( Artifact artifact )
        {
            return ( includesCount == 0 || matchesAny( artifact, 0, includesCount ) )
                && !matchesAny( artifact, includesCount, includesExcludes.size() );
        }

        boolean matchesAny( Artifact artifact, int start, int end )
        {
            if ( start < includesExcludes.size() )
            {
                for ( int i = start; i < end; i++ )
                {
                    if ( includesExcludes.get( i ).matches( artifact ) )
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class ScopeFilter
        implements ArtifactFilter
    {
        private final String scope;

        public ScopeFilter( String scope )
        {
            super();
            this.scope = scope;
        }

        public boolean matches( Artifact artifact )
        {
            return this.scope.equals( artifact.getScope() );
        }
    }

    static class TypeFilter
        implements ArtifactFilter
    {
        private final String type;

        public TypeFilter( String type )
        {
            super();
            this.type = type;
        }

        public boolean matches( Artifact artifact )
        {
            return this.type.equals( artifact.getType() );
        }
    }
}
