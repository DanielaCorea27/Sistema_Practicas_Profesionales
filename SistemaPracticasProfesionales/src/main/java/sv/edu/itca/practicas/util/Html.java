package sv.edu.itca.practicas.util;

import java.util.Locale;

/**
 * Utilidades para JSP.
 *   Html.esc(valor)   -> escapa texto (evita XSS)
 *   Html.horas(valor) -> 8.0 se muestra "8", 7.5 se muestra "7.5"
 */
public final class Html {

    private Html() {
    }

    public static String esc(Object valor) {

        if (valor == null) {
            return "";
        }

        String s = valor.toString();
        StringBuilder sb = new StringBuilder(s.length() + 16);

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            switch (c) {
                case '&':  sb.append("&amp;");  break;
                case '<':  sb.append("&lt;");   break;
                case '>':  sb.append("&gt;");   break;
                case '"':  sb.append("&quot;"); break;
                case '\'': sb.append("&#39;");  break;
                default:   sb.append(c);
            }
        }

        return sb.toString();
    }

    /** Formatea horas sin decimales innecesarios. */
    public static String horas(double h) {

        if (h == Math.rint(h)) {
            return String.valueOf((long) h);
        }

        String s = String.format(Locale.US, "%.2f", h);

        // quitar ceros finales: 7.50 -> 7.5
        if (s.endsWith("0")) {
            s = s.substring(0, s.length() - 1);
        }

        return s;
    }
}

