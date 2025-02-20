package br.dev.ferreiras.calculatorweb.entity;

import java.lang.annotation.Documented;

@Documented
public @interface ClassPreamble {
    String author();
    String date();
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    String[] reviewers();
    String description() default "N/A";
  }
