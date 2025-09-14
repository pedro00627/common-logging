package com.github.pedro00627.commonlogging;

import java.util.regex.Pattern;

/**
 * Clase de utilidad para operaciones relacionadas con logging seguro.
 * Proporciona métodos estáticos para enmascarar información sensible
 * como correos electrónicos y números de documento antes de ser registrados.
 */
public final class LogHelper {
    /**
     * Patrón de expresión regular para validar direcciones de correo electrónico.
     * Utilizado internamente para determinar si un String es un correo electrónico válido
     * antes de intentar enmascararlo.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]++(?:\\\\.[a-zA-Z0-9_+&*-]++)*+@(?:[a-zA-Z0-9-]++\\\\.)++[a-zA-Z]{2,7}$");

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     */
    private LogHelper() {
        // Private constructor for utility class
    }

    /**
     * Enmascara una dirección de correo electrónico para logging seguro.
     * Si el correo electrónico es nulo o no tiene un formato válido, retorna "invalid-email-format".
     * De lo contrario, enmascara la parte local del correo, mostrando el primer y el último carácter,
     * y reemplazando los caracteres intermedios con asteriscos.
     * Ejemplo: "test.user@pragma.com.co" -> "t***r@pragma.com.co"
     *
     * @param email El correo electrónico a enmascarar. Puede ser nulo.
     * @return El correo electrónico enmascarado si es válido, o "invalid-email-format" en caso contrario.
     */
    public static String maskEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return "invalid-email-format";
        }
        int atIndex = email.indexOf('@');
        String localPart = email.substring(0, atIndex);
        if (localPart.length() <= 2) {
            return "***" + email.substring(atIndex);
        }
        // Muestra el primer y último caracter de la parte local para mayor seguridad, como indica el comentario.
        return localPart.charAt(0) + "***" + localPart.charAt(localPart.length() - 1) + email.substring(atIndex);
    }

    /**
     * Enmascara un número de documento de identidad para logging seguro.
     * Si el número de documento es nulo o tiene menos de 6 caracteres, retorna "***".
     * De lo contrario, muestra el primer dígito y los últimos cuatro dígitos,
     * reemplazando los dígitos intermedios con asteriscos.
     * Ejemplo: "1234567890" -> "1****7890"
     *
     * @param documentId El número de documento a enmascarar. Puede ser nulo.
     * @return El documento enmascarado si cumple con la longitud mínima, o "***" en caso contrario.
     */
    public static String maskDocument(String documentId) {
        if (documentId == null || documentId.length() < 6) { // Requiere al menos 6 caracteres para aplicar la regla de forma segura.
            return "***";
        }
        return documentId.charAt(0) + "****" + documentId.substring(documentId.length() - 4);
    }
}
