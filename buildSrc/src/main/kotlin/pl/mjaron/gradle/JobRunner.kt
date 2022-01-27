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
import java.io.File

/**
 * Defines how to perform any job.
 */
class JobRunner(val project: Project, val taskPrefix: String, private val engines : HashMap<String, ITemplateEngine>) {

    fun generateName(base: String) : String {
        var idx = 0
        while (true) {
            val candidate = if (idx == 0) "$taskPrefix-$base" else "$taskPrefix-$base-$idx"
            if (project.tasks.findByName(candidate) == null) {
                return candidate
            }
            ++idx
        }
    }

    private fun preJob(job: TemplateJob) {
        val sourcePath = job.getAbsoluteSrc(project)
        println("Job source path: $sourcePath")
        job.sourceText = File(sourcePath).readText()
    }

    private fun postJob(job: TemplateJob) {
        val destinationPath = job.getAbsoluteDst(project)
        val destinationFile = File(destinationPath)
        println("Job destination path: $destinationPath")
        destinationFile.parentFile.mkdirs()
        destinationFile.writeText(job.destinationText)
    }

    fun doJob(job: TemplateJob) {
        preJob(job)
        val engine: ITemplateEngine =
            engines[job.engineName] ?: throw Exception("No such template engine: [${job.engineName}].")
        engine.process(job)
        postJob(job)
    }

}
