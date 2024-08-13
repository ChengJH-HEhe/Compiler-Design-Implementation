package util;

import util.error.semanticError;

import java.util.HashMap;

public class globalScope extends Scope {
    private HashMap<String, typeinfo> types = new HashMap<>();
    public globalScope(Scope parentScope) {
        super(parentScope);
    }
    public void addType(String name, typeinfo t) {
        if (types.containsKey(name))
            throw new semanticError("multiple definition of " + name);
        types.put(name, t);
    }
    public typeinfo getTypeFromName(String name) {
        if (types.containsKey(name)) return types.get(name);
        throw new semanticError("no such type: " + name);
    }
}
