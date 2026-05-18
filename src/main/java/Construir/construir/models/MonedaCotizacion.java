package Construir.construir.models;

/**
 * Enum que define las monedas permitidas para una cotización.
 *
 * Se usa para evitar que el profesional escriba texto libre como:
 * "pesos", "$", "dolar", "usd", etc.
 *
 * Valores disponibles:
 *  - ARS: pesos argentinos
 *  - USD: dólares estadounidenses
 */
public enum MonedaCotizacion {

    // Pesos argentinos
    ARS,

    // Dólares estadounidenses
    USD
}
