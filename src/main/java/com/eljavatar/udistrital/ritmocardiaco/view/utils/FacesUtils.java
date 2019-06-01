package com.eljavatar.udistrital.ritmocardiaco.view.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.eljavatar.udistrital.ritmocardiaco.logfactory.Log;

@Named("FacesUtils")
@RequestScoped
public class FacesUtils implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4783822462255654661L;

    @Inject
    private Log logger;

    @Inject
    private FacesMessagesUtil facesMessagesUtil;

    @Inject
    private FacesContext facesContext;

    private static final String MSG_MISSED_FACESCONTEXT = "El contexto faces no puede ser null.";

    public HttpServletRequest getHttpServletRequest() {
        return getHttpServletRequest(this.facesContext);
    }

    public HttpServletRequest getHttpServletRequest(FacesContext facesContext) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);
        return ((HttpServletRequest) facesContext.getExternalContext().getRequest());
    }

    public HttpServletResponse getHttpServletResponse() {
        return getHttpServletResponse(this.facesContext);
    }

    public HttpServletResponse getHttpServletResponse(FacesContext facesContext) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);
        return ((HttpServletResponse) facesContext.getExternalContext().getResponse());
    }

    public HttpSession getHttpSession() {
        return getHttpSession(this.facesContext);
    }

    public HttpSession getHttpSession(FacesContext facesContext) {
        return getHttpSession(facesContext, false);
    }

    public HttpSession getHttpSession(FacesContext facesContext, boolean create) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);
        return ((HttpSession) facesContext.getExternalContext().getSession(create));
    }

    public ExternalContext getExternalContext(FacesContext facesContext) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);
        return facesContext.getExternalContext();
    }

    public ExternalContext getExternalContext() {
        return getExternalContext(this.facesContext);
    }

    public void urlRedirect(String url) {
        try {
            if (url.substring(0, 1).equals("/")) {
                String applicationContextPath = getExternalContext().getApplicationContextPath();
                getExternalContext().redirect(applicationContextPath + url);
            } else {
                getExternalContext().redirect(url);
            }
        } catch (IOException e) {
            facesMessagesUtil.addError("No se ha podido redirigir");
            logger.error("No se ha podido redirigir a la ruta: " + url, e);
            throw new IllegalArgumentException("La url indicada presento problemas en su procesamiento", e);
        }
    }

//    public void redirect(String ruta) {
//        try {
//            getExternalContext().redirect(ruta);
//        } catch (IOException ex) {
//            logger.error("No se ha podido redirigir a la ruta: " + ruta, ex);
//            facesMessagesUtil.addError("No se ha podido redirigir");
//        }
//    }
    public static String redirection(String navigationCase) {
        return navigationCase + "?faces-redirect=true";
    }

    public static String redirection(String navigationCase, String[] params) {
        if (StringUtils.isBlank(navigationCase)) {
            throw new IllegalArgumentException("The navigation case can not be blank");
        }
        StringBuilder sb = new StringBuilder(navigationCase).append("?faces-redirect=true");

        if (params != null) {
            for (String param : params) {
                sb.append("&").append(param);
            }
        }

        return sb.toString();
    }

    public String grtRedirection(String navigationCase) {
        return redirection(navigationCase);
    }

    public String getRedirection(String navigationCase, String param) {
        return redirection(navigationCase, new String[]{param});
    }

    public <T> T evaluate(String elExpression, Class<T> clazz) {
        return evaluate(elExpression, clazz, this.facesContext);
    }

    public <T> T evaluate(String elExpression, Class<T> clazz, FacesContext facesContext) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);

        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();

        ValueExpression valueExpression = expressionFactory.createValueExpression(elContext, elExpression, clazz);

        return clazz.cast(valueExpression.getValue(elContext));
    }

    public MethodExpression createVoidMethodExpression(String elExpression) {
        return createVoidMethodExpression(elExpression, this.facesContext);
    }

    public MethodExpression createVoidMethodExpression(String elExpression, FacesContext facesContext) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);

        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();

        return expressionFactory.createMethodExpression(elContext, elExpression, null, new Class[0]);
    }

    public MethodExpression createStringMethodExpression(String elExpression) {
        return createStringMethodExpression(elExpression, this.facesContext);
    }

    public MethodExpression createStringMethodExpression(String elExpression, FacesContext facesContext) {
        Objects.requireNonNull(facesContext, MSG_MISSED_FACESCONTEXT);

        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();

        return expressionFactory.createMethodExpression(elContext, elExpression, String.class, new Class[0]);
    }

    public boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

}
