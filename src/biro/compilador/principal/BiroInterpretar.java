package biro.compilador.principal;

import java.util.ArrayList;
import java.util.HashMap;

public class BiroInterpretar {
    String code;
    private HashMap<String, String> variables;
    private ArrayList<String> errors;

    public BiroInterpretar() {
        variables = new HashMap<String, String>();
        errors = new ArrayList<String>();
    }

    public void interpret(String code) {
        this.code = code;
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith("//")) {
                // Ignorar comentarios
                continue;
            }
            if (line.contains("=")) {
                // Interpretar asignaciones de variables
                String[] parts = line.split("=");
                String variable = parts[0].trim();

                String value = parts[1].trim();

                if (value.startsWith("\"")) {
                    // Si es un valor de tipo *cad
                    if (variable.startsWith(".")) {
                        String type = getType(variable);
                        if (!type.equals("*cad")) {
                            errors.add("Error de compatibilidad de tipos en la línea " + (i + 1) + ": la variable " + variable + " no es de tipo *cad");
                        } else {
                            variables.put(variable, value);
                        }
                    } else {
                        errors.add("Error de sintaxis en la línea " + (i + 1) + ": falta el punto al inicio de la variable");
                    }
                } else if (value.startsWith("1") && value.endsWith("1")) {
                    // Si es un valor de tipo *ent
                    if (variable.startsWith(".")) {
                        String type = getType(variable);
                        if (!type.equals("*ent")) {
                            errors.add("Error de compatibilidad de tipos en la línea " + (i + 1) + ": la variable " + variable + " no es de tipo *ent");
                        } else {
                            variables.put(variable, value);
                        }
                    } else {
                        errors.add("Error de sintaxis en la línea " + (i + 1) + ": falta el punto al inicio de la variable");
                    }
                } else if (value.contains(".") && value.startsWith("1") && value.endsWith("1")) {
                    // Si es un valor de tipo *dec
                    if (variable.startsWith(".")) {
                        String type = getType(variable);
                        if (!type.equals("*dec")) {
                            errors.add("Error de compatibilidad de tipos en la línea " + (i + 1) + ": la variable " + variable + " no es de tipo *dec");
                        } else {
                            variables.put(variable, value);
                        }
                    } else {
                        errors.add("Error de sintaxis en la línea " + (i + 1) + ": falta el punto al inicio de la variable " + variable);
                    }
                } else if (value.startsWith(".")) {
                    // Si es una variable existente
                    if (variable.startsWith(".")) {
                        String type = getType(variable);
                        String valueOfVariable = variables.get(value);
                        if (valueOfVariable == null) {
                            errors.add("Error de variable no definida en la línea " + (i + 1) + ": la variable " + value + " no ha sido definida");
                        } else if (!isCompatibleType(type, getType(valueOfVariable))) {
                            errors.add("Error de compatibilidad de tipos en la línea " + (i + 1) + ": la variable " + value + " no es compatible con el tipo de la variable " + variable);
} else {
variables.put(variable, valueOfVariable);
}
} else {
errors.add("Error de sintaxis en la línea " + (i + 1) + ": falta el punto al inicio de la variable " + variable);
}
} else {
errors.add("Error de sintaxis en la línea " + (i + 1) + ": valor no válido para la asignación");
}
}
}
}
    private String getType(String variable) {
        if (variable.startsWith("*cad")) {
            return "*cad";
        } else if (variable.startsWith("*ent")) {
            return "*ent";
        } else if (variable.startsWith("*dec")) {
            return "*dec";
        } else {
            return "unknown";
        }
    }

    private boolean isCompatibleType(String type1, String type2) {
        return type1.equals(type2);
    }

    public HashMap<String, String> getVariables() {
        return variables;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public static void main(String[] args) {
        BiroInterpretar interpreter = new BiroInterpretar();
        interpreter.interpret(".ent.a=11\n.cad.b=\"Hello\"\n.cad.c=.cad.b\n.ent.d=1.23\n");
        System.out.println(interpreter.getVariables());
        System.out.println(interpreter.getErrors());
    }
}