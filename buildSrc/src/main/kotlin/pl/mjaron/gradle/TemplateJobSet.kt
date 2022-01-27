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
 * Represents jobs related to single base source and base destination directories.
 * Creates job entries.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class TemplateJobSet {

    /**
     * Name of job set. Related task name will be created.
     */
    var name = "custom"

    /**
     * Base directory of each job source file.
     */
    var srcBase: String? = null

    /**
     * Base directory of each job destination file.
     */
    var dstBase: String? = null

    /**
     * Creates a new job.
     * If destination is empty, it means the same destination as source.
     * Note that if JobSet  has different source and destination bases, it will cause different paths, so source will not be overridden.
     */
    fun job(src: String, dst: String): TemplateJob {
        val j = TemplateJob(this, File(src), File(dst))
        jobs.add(j)
        return j
    }

    fun job(src: String): TemplateJob {
        val j = TemplateJob(this, File(src), File(src))
        jobs.add(j)
        return j
    }

    fun absoluteSrcBase(project: Project): String {
        if (srcBase == null) {
            return project.projectDir.absolutePath
        } else if (File(srcBase!!).isAbsolute) {
            return srcBase!!
        } else {
            return Paths.get(project.projectDir.absolutePath, srcBase!!).toString()
        }
    }

    fun absoluteDstBase(project: Project): String {
        if (dstBase == null) {
            return project.buildDir.absolutePath
        } else if (File(dstBase!!).isAbsolute) {
            return dstBase!!
        } else {
            return Paths.get(project.buildDir.absolutePath, dstBase!!).toString()
        }
    }

    open fun configure(jobRunner: JobRunner) : Task {
        val jobSetTask = jobRunner.project.task(jobRunner.generateName(name))
        for (job in jobs) {
            val jobTask = job.configure(jobRunner)
            jobSetTask.dependsOn(jobTask)
        }
        return jobSetTask
    }

    val jobs = ArrayList<TemplateJob>()
}

