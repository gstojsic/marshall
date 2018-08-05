package com.skunkworks.rpc;

import com.skunkworks.rpc.template.FieldData;
import com.skunkworks.rpc.tool.Tools;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * stole on 06.04.17.
 */
class RpcSerializerGenerator {
    private final ProcessingEnvironment processingEnv;
    private Messager messager;
    private Filer filer;

    RpcSerializerGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    /**
     * Find complex object used by the interface and generate their serializers
     */
    void generate(Element rpcInterface) throws Exception {
        for (Element enclosedElement : rpcInterface.getEnclosedElements()) {
            if (ElementKind.METHOD.equals(enclosedElement.getKind())) {
                processMethod(enclosedElement);
            }
        }
    }

    private void processMethod(Element methodElement) throws Exception {
        ExecutableElement method = (ExecutableElement) methodElement;
//        MethodData methodData = new MethodData();
//        methodData.setName(method.getSimpleName().toString());

        //Return type
//        warn(method.getSimpleName().toString());
//        warn(method.getReturnType().toString());
//        warn(method.getReturnType().getKind().toString());

        TypeElement returnElement = processingEnv.getElementUtils().getTypeElement(method.getReturnType().toString());
        if (isCandidate(returnElement)) {
            generateSerializer(returnElement);
        }

        for (VariableElement param : method.getParameters()) {
            TypeElement paramElement = processingEnv.getElementUtils().getTypeElement(param.asType().toString());
            if (isCandidate(paramElement)) {
                generateSerializer(paramElement);
            }
        }

        //        TypeElement returnTypeElement = (TypeElement) processingEnv.getTypeUtils().asElement(method.getReturnType());
//        DeclaredType declaredReturnType = (DeclaredType) method.getReturnType();
//        methodData.setReturnType(getTypeString(returnTypeElement, declaredReturnType));
//
//        //method parameters
//        ArrayList<String> parameters = new ArrayList<>();
//        List<QueryParameter> queryParameters = new ArrayList<>();
//        int index = 0;
//        for (VariableElement param : method.getParameters()) {
//            TypeElement paramElement = processingEnv.getElementUtils().getTypeElement(param.asType().toString());
//            String methodParameterName = param.getSimpleName().toString();
//            parameters.add(paramElement.getSimpleName().toString() + " " + methodParameterName);
//
//            //Tip
//            String queryParameterType = Tools.getRecordsetType(param, messager);
//            queryParameters.add(new QueryParameter(++index, methodParameterName, queryParameterType));
//        }
//        //warn(parameters.toString());
//        methodData.setParameters(String.join(", ", parameters));
//        methodData.setQueryParameters(queryParameters);
//
//        //query method
//        processQueryMethod(methodData, declaredReturnType);
    }

    private void generateSerializer(TypeElement type) throws Exception {
        final JavaFileObject jfo = filer.createSourceFile(
                type.getSimpleName() + "RpcSerializer");

        try (Writer writer = jfo.openWriter()) {

            final Properties props = new Properties();
            final URL url = this.getClass().getClassLoader().getResource("velocity.properties");
            props.load(url.openStream());

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
            cfg.setClassForTemplateLoading(getClass(), "/freemarker");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);

            Map<String, Object> context = new HashMap<>();
//            final VelocityEngine ve = new VelocityEngine(props);
//            ve.init();
//
//            final VelocityContext context = new VelocityContext();

            PackageElement packageElement = (PackageElement) type.getEnclosingElement();

            context.put("packageName", packageElement.getQualifiedName().toString());
            context.put("className", type.getSimpleName());
            context.put("endObjectHashCode", Integer.toString("this".hashCode()));

            final List<FieldData> fields = new ArrayList<>();
            for (Element enclosedElement : type.getEnclosedElements()) {
                String name = enclosedElement.getSimpleName().toString();
                if (enclosedElement.getKind().isField() && !"DEFAULT_VALUE".equals(name)) {
                    fields.add(processField(enclosedElement, name));
                }
            }
            context.put("fields", fields);

//            final Template vt = ve.getTemplate("velocity/rmi/rpcSerializer.vm");
//            warn("merging");
//            vt.merge(context, writer);
            freemarker.template.Template temp = cfg.getTemplate("rmi/rpcSerializer.ftl");
            temp.process(context, writer);
        }
    }

    private boolean isCandidate(TypeElement type) {
        if (type == null)
            return false;
        warn(type.toString());
        warn(type.getKind().toString());

        return true;
    }

    private FieldData processField(Element field, String name) {
        //warn("element type:" + field.asType());
        //warn("element kind:" + field.asType().getKind());

        String getterPrefix = field.asType().getKind().equals(TypeKind.BOOLEAN) ? "is" : "get";
        String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
        String getter = getterPrefix + capitalizedName;
        String setter = "set" + capitalizedName;
        return new FieldData(name, "Long", getter, setter, false, "read" + capitalizedName, Integer.toString(name.hashCode()));
    }

    private void warn(String message) {
        Tools.warn(messager, message);
    }
}
