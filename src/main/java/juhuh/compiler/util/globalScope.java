package juhuh.compiler.util;

import juhuh.compiler.util.error.semanticError;
import juhuh.compiler.util.info.Info;

import java.util.HashMap;

public class globalScope extends Scope {
    private HashMap<String, Info> types = new HashMap<>();
    public globalScope(Scope parentScope, Info info) {
        super(parentScope, info, ScopeType.GLOBAL);
    }
    public void addType(String name, Info t) {
        if (types.containsKey(name))
            throw new semanticError("multiple definition of " + name);
        types.put(name, t);
    }
    public Info getTypeFromName(String name) {
        if (types.containsKey(name)) return types.get(name);
        throw new semanticError("no such type: " + name);
    }
    public Info getSafeTypeFromName(String name) {
        if (types.containsKey(name)) return types.get(name);
        else return null;
        // throw new semanticError("no such type: " + name);
    }
}
