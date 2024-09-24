package com.cardanoj.jna.annotation.processor;

import com.cardanoj.jna.annotation.processor.model.ClassDefinition;
import com.squareup.javapoet.TypeSpec;

/**
 * Interface for code generator
 */
public interface CodeGenerator {
    TypeSpec generate(ClassDefinition classDefinition);
}
