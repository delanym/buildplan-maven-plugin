/**
 * Copyright (C) 2012 Jean-Christophe Gay (contact@jeanchristophegay.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.jcgay.maven.plugin.buildplan;

import org.apache.maven.plugin.MojoExecution;

public class NoMojoExecution extends MojoExecution {

    public static final NoMojoExecution INSTANCE = new NoMojoExecution();

    private NoMojoExecution() {
        super(null);
    }

    @Override
    public String getArtifactId() {
        return "";
    }

    @Override
    public String getExecutionId() {
        return "";
    }

    @Override
    public String getGoal() {
        return "";
    }

    @Override
    public String getLifecyclePhase() {
        return "";
    }
}
