package com.lambdai.gradle.tasks.thrift

import collection.JavaConversions._
import com.twitter.scrooge.{Main, Compiler}
import java.io.File

object ScroogeCompiler {
  def compile(files: java.lang.Iterable[File], dest: File, opts: java.lang.Iterable[String]) {
    val destination = dest.getAbsolutePath
    val thriftFiles = files.map {_.getAbsolutePath}
    if (thriftFiles.size > 0) {
      val compiler = new Compiler()
      compiler.destFolder = destination

      val args: Seq[String] = opts.toSeq ++ thriftFiles.toSeq
      Main.parseOptions(compiler, args)
      compiler.run()
    }
  }
}
