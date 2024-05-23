package com.aluracursos.Challenge_Literalura_BackEnd.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
