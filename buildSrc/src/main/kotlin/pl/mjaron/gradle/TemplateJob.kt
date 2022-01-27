/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pl.mjaron.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import java.io.File
import java.nio.file.Paths

/**
 * Describes single file template processing.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class TemplateJob(val jobSet: TemplateJobSet, var source: File, var destination: File) {
    var name = source.name
    var engineName = BuildInEngines.REPLACE
    val params = HashMap<String, String>()
    var sourceText = ""
    var destinationText = ""

    @Suppress("unused")
    fun engine(what: String): TemplateJob {
        engineName = what
        return this
    }

    /**
     * Sets source file location related to job set's base directory.
     */
    fun src(what: String): TemplateJob {
        source = File(what)
        return this
    }

    /**
     * Sets destination file location related to job set's base directory.
     */
    fun dst(what: String): TemplateJob {
        destination = File(what)
        return this
    }

    /**
     * Sets parameter of template.
     */
    fun param(what: String, value: String): TemplateJob {
        params[what] = value
        return this
    }

    /**
     * Sets parameter of template.
     */
    fun <T> param(what: String, value: T): TemplateJob {
        params[what] = value.toString()
        return this
    }

    /**
     * Human-readable description of this job.
     */
    override fun toString(): String {
        return "${source}->${destination}:${params}"
    }

    fun getAbsoluteSrc(project: Project): String {
        if (this.source.isAbsolute) {
            return this.source.absolutePath
        }
        return Paths.get(this.jobSet.absoluteSrcBase(project), this.source.path).toString()
    }

    fun getAbsoluteDst(project: Project): String {
        if (this.destination.isAbsolute) {
            return this.destination.absolutePath
        }
        return Paths.get(this.jobSet.absoluteDstBase(project), this.destination.path).toString()
    }

    open fun configure(jobRunner: JobRunner) : Task {
        return jobRunner.project.task(jobRunner.generateName(name)) {
            doLast {
                jobRunner.doJob(this@TemplateJob)
            }
        }
    }
}
