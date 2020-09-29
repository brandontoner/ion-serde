package com.brandontoner.ion.serde;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public final class Serde<T> {
    private final Class<T> clazz;
    private final Constructor<?> constructor;
    private final List<String> paramNames;
    private final List<Method> methods;

    public Serde(final Class<T> clazz,
                 final Constructor<T> constructor,
                 final List<String> paramNames,
                 final List<Method> methods) {
        this.clazz = clazz;
        this.constructor = constructor;
        this.paramNames = paramNames;
        this.methods = methods;
    }

    public static <T> Serde<T> discover(final Class<T> input, final SerializationConfig serializationConfig) {
        Class<T> clazz = serializationConfig.getImplementation(input);
        List<Serde<T>> candidates = new ArrayList<>();
        for (final Constructor constructor : clazz.getConstructors()) {
            List<String> paramNames = getParameterNames(constructor);
            java.lang.reflect.Type[] types = constructor.getParameterTypes();
            List<Method> methods = new ArrayList<>();
            boolean allFound = true;
            for (int i = 0; i < paramNames.size(); i++) {
                Method getter = findGetter(clazz, paramNames.get(i), types[i]);
                if (getter == null) {
                    allFound = false;
                    break;
                }
                methods.add(getter);
            }
            if (allFound) {
                candidates.add(new Serde<T>(clazz, constructor, paramNames, methods));
            }
        }
        return candidates.stream()
                         .max(Comparator.comparingInt(v -> v.methods.size()))
                         .orElseThrow(() -> new NoSuchElementException(clazz.getName()));
    }

    private static <T> Method findGetter(final Class<T> clazz, final String name, final java.lang.reflect.Type type) {
        List<Method> methods = new ArrayList<>();
        for (final Method method : clazz.getMethods()) {
            if (method.getParameterCount() != 0) {
                continue;
            }
            if (!method.getReturnType().equals(type)) {
                continue;
            }
            methods.add(method);
        }
        if (methods.size() == 1) {
            return methods.get(0);
        }

        for (final Method method : methods) {
            if (!method.getName().equalsIgnoreCase("get" + name)) {
                continue;
            }
            return method;
        }
        return null;
    }

    /**
     * Returns a list containing one parameter name for each argument accepted by the given constructor. If the class
     * was compiled with debugging symbols, the parameter names will match those provided in the Java source code.
     * Otherwise, a generic "arg" parameter name is generated ("arg0" for the first argument, "arg1" for the
     * second...).
     * <p>
     * This method relies on the constructor's class loader to locate the bytecode resource that defined its class.
     *
     * @param constructor
     * @return
     * @throws IOException
     */
    public static List<String> getParameterNames(final Constructor<?> constructor) {
        Class<?> declaringClass = constructor.getDeclaringClass();
        ClassLoader declaringClassLoader = declaringClass.getClassLoader();
        if (declaringClassLoader == null) {
            System.err.println(constructor);
        }

        Type declaringType = Type.getType(declaringClass);
        String constructorDescriptor = Type.getConstructorDescriptor(constructor);
        String url = declaringType.getInternalName() + ".class";
        ClassNode classNode;
        try (InputStream classFileInputStream = declaringClassLoader.getResourceAsStream(url)) {
            classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classFileInputStream);
            classReader.accept(classNode, 0);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        List<MethodNode> methods = classNode.methods;
        for (final MethodNode method : methods) {
            if ("<init>".equals(method.name) && method.desc.equals(constructorDescriptor)) {
                Type[] argumentTypes = Type.getArgumentTypes(method.desc);
                List<String> parameterNames = new ArrayList<>(argumentTypes.length);
                List<LocalVariableNode> localVariables = method.localVariables;
                for (int i = 0; i < argumentTypes.length; i++) {
                    // The first local variable actually represents the "this" object
                    parameterNames.add(localVariables.get(i + 1).name);
                }
                return parameterNames;
            }
        }
        return null;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public List<Method> getMethods() {
        return methods;
    }
}

